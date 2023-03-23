package gov.kcg.kgo.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.kcg.kgo.activiti.entity.TpiFlowDto;
import gov.kcg.kgo.activiti.entity.TpiFlowTaskDto;
import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.enums.backend.ActFlowEnableEnum;
import gov.kcg.kgo.enums.backend.ActFlowTypeEnum;
import gov.kcg.kgo.enums.backend.ActTaskSubTypeEnum;
import gov.kcg.kgo.enums.backend.ActTaskTypeEnum;
import gov.kcg.kgo.enums.backend.CaseStatusEnum;
import gov.kcg.kgo.enums.backend.KgoRoleEnum;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.model.KgoCaseMain;
import gov.kcg.kgo.model.KgoCaseMainResendFlow;
import gov.kcg.kgo.model.KgoCaseset;
import gov.kcg.kgo.model.TpiFlow;
import gov.kcg.kgo.model.TpiFlowTask;
import gov.kcg.kgo.repository.KgoCaseMainRepository;
import gov.kcg.kgo.repository.KgoCaseMainResendFlowRepository;
import gov.kcg.kgo.repository.KgoCasesetRepository;
import gov.kcg.kgo.repository.KgoUserRepository;
import gov.kcg.kgo.repository.TpiFlowRepository;
import gov.kcg.kgo.repository.TpiFlowTaskRepository;
import gov.kcg.kgo.service.ActivitiService;
import gov.kcg.kgo.service.BackEndFlowManagerService;
import gov.kcg.kgo.service.TpiFlowService;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.JsonUtil;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.util.RandomUtil;
import gov.kcg.kgo.util.SpringUtil;
import gov.kcg.kgo.viewModel.backend.actFlowManager.common.rs.bean.TpiFlowTaskBean;
import gov.kcg.kgo.viewModel.backend.actFlowManager.delete.rq.ActFlowManagerDeleteRq;
import gov.kcg.kgo.viewModel.backend.actFlowManager.delete.rs.ActFlowManagerDeleteRs;
import gov.kcg.kgo.viewModel.backend.actFlowManager.home.rs.ActFlowManagerHomeActionRs;
import gov.kcg.kgo.viewModel.backend.actFlowManager.home.rs.bean.ActFlowManagerHomeActionDataGrid;
import gov.kcg.kgo.viewModel.backend.actFlowManager.home.rs.bean.ActFlowManagerHomeActionViewForm;
import gov.kcg.kgo.viewModel.backend.actFlowManager.resendFlow.rq.ActFlowManagerResendFlowRq;
import gov.kcg.kgo.viewModel.backend.actFlowManager.save.rq.ActFlowManagerSaveRq;
import gov.kcg.kgo.viewModel.backend.actFlowManager.save.rq.bean.TpiFlowBean;
import gov.kcg.kgo.viewModel.backend.actFlowManager.save.rs.ActFlowManagerSaveFlowRs;
import gov.kcg.kgo.viewModel.backend.actFlowManager.save.rs.bean.ActFlowManagerSaveFlowViewForm;
import gov.kcg.kgo.viewModel.backend.actFlowManager.taskDetail.rq.ActFlowManagerTaskDetailActionRq;
import gov.kcg.kgo.viewModel.backend.actFlowManager.taskDetail.rs.ActFlowManagerTaskDetailActionRs;
import gov.kcg.kgo.viewModel.backend.actFlowManager.taskDetail.rs.bean.ActFlowManagerTaskDetailActionViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;

/**
 * 後台 - 動態流程管理 Service.
 */
@Transactional(rollbackFor = Exception.class)
@Service("BackEndFlowManagerService")
public class BackEndFlowManagerServiceImpl extends KgoBackEndServiceImpl implements BackEndFlowManagerService {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BackEndFlowManagerServiceImpl.class);
	
	/** UUID_PREFIX. */
	private static final String UUID_PREFIX = "_";
	
	@Autowired
	private TpiFlowRepository tpiFlowRepository;
	
	@Autowired
	private TpiFlowTaskRepository tpiFlowTaskRepository;
	
	@Autowired
	private TpiFlowService tpiFlowService;
	
	@Autowired
	private KgoCasesetRepository kgoCasesetRepository;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private KgoCaseMainRepository kgoCaseMainRepository;
	
	@Autowired
	private KgoCaseMainResendFlowRepository kgoCaseMainResendFlowRepository;
	
	@Autowired
	private KgoUserRepository kgoUserRepository;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private RuntimeService runtimeService;

	/**
	 * 動態流程管理 - 畫面初始.
	 *
	 * @return the param set home action rs
	 */
	@Override
	public ActFlowManagerHomeActionRs homeAction() {
		ActFlowManagerHomeActionRs rs = new ActFlowManagerHomeActionRs();
		ActFlowManagerHomeActionViewForm viewFrom = new ActFlowManagerHomeActionViewForm();
		List<ActFlowManagerHomeActionDataGrid> grid = new ArrayList<>();
		viewFrom.setGrid(grid);
		rs.setData(viewFrom);
		try {
			BackendLoginUserInfo  beUser = KgoUtil.getBackendLoginUser();
			String organId = beUser.getOrgan();
			String enable = ActFlowEnableEnum.Y.getCode();
			Boolean isDefault = Boolean.TRUE;
			List<String> roles = beUser.getRoles(); 
			Boolean canEditDefault = false;
			// 系統管理者
			if(roles.stream().anyMatch(new String(KgoRoleEnum.ADMIN.getValue())::equalsIgnoreCase)) {
				canEditDefault = true;
			}
			List<TpiFlow> tpiFlowList = tpiFlowRepository.findByOrganIdAndFlowEnableAndIsDefaultOrderByCreateTimeDesc(organId, enable, isDefault);
			for (TpiFlow tpiFlow : tpiFlowList) {
				ActFlowManagerHomeActionDataGrid data = new ActFlowManagerHomeActionDataGrid(tpiFlow, canEditDefault);
				grid.add(data);
			}
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new KgoApiException("homeAction error " + e.getMessage(), e);
		}
		return rs;
	}
	
	/**
	 * 取得任務明細.
	 *
	 * @param rq the rq
	 * @return the act flow manager task detail action rs
	 */
	@Override
	public ActFlowManagerTaskDetailActionRs taskDetailAction(ActFlowManagerTaskDetailActionRq rq) {
		ActFlowManagerTaskDetailActionRs rs = new ActFlowManagerTaskDetailActionRs();
		ActFlowManagerTaskDetailActionViewForm viewFrom = new ActFlowManagerTaskDetailActionViewForm();
		TpiFlowBean tpiFlowBean = new TpiFlowBean();
		List<TpiFlowTaskBean> tasks = new ArrayList<>();
		tpiFlowBean.setFlowTasks(tasks);
		viewFrom.setTpiFlow(tpiFlowBean);
		rs.setData(viewFrom);
		try {
			Optional<TpiFlow> OptTpiFlow = tpiFlowRepository.findById(rq.getFlowId());
			if (OptTpiFlow.isPresent()) {
				// master
				TpiFlow tpiFlow = OptTpiFlow.get();
				tpiFlowBean.setFlowId(tpiFlow.getFlowId());
				tpiFlowBean.setFlowName(tpiFlow.getFlowName());
				tpiFlowBean.setFlowDesc(tpiFlow.getFlowDesc());
				tpiFlowBean.setOrganId(tpiFlow.getOrganId());
				tpiFlowBean.setJsonData(tpiFlow.getJsonData());
				tpiFlowBean.setIsDefault(tpiFlow.getIsDefault());

				// detail
				List<TpiFlowTask> tasksQueryList = tpiFlowTaskRepository.findByFlowIdOrderByTaskOrder(rq.getFlowId());
				
				// 取得使用者姓名Map.
				Map<String, String> userNameMap = getUserNameMap(tasksQueryList);
		
				for (TpiFlowTask t : tasksQueryList) {
					TpiFlowTaskBean taskBean = new TpiFlowTaskBean();
					taskBean.setTaskSeq(t.getTaskSeq());
					taskBean.setTaskName(t.getTaskName());
					taskBean.setTaskOrder(t.getTaskOrder());
					taskBean.setTaskType(t.getTaskType());
					taskBean.setNodeId(t.getNodeId());
					
					ComboBox taskAssigneesCombox = new ComboBox();
					if(StringUtils.isNotBlank(t.getTasAssignees())) {
						for (String userId : StringUtils.split(t.getTasAssignees(), ",")) {
							taskAssigneesCombox.add(userNameMap.get(userId), userId);
						}
						taskBean.setTaskAssigneesCombox(taskAssigneesCombox);
					}
					tasks.add(taskBean);
				}
			} else {
				// 查無此流程. 
				throw new KgoApiException(new ErrorResult(KgoBackEndApiError.CAN_NOT_FIND_FLOW_ERROR));
			}
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new KgoApiException("taskDetailAction error " + e.getMessage(), e);
		}
		return rs;
	}
	
	/**
	 * 取得使用者姓名Map.
	 *
	 * @param tasksQueryList the tasks query list
	 * @return the user name map
	 */
	private Map<String, String> getUserNameMap(List<TpiFlowTask> tasksQueryList) {
		Map<String, String> userNameMap = new HashMap<>(); 
		if(CollectionUtils.isNotEmpty(tasksQueryList)) {
			List<String> assignessList = tasksQueryList.stream().map(t -> t.getTasAssignees()).collect(Collectors.toList());
			List<String> userIdList = new ArrayList<>();
			
			for(String assiness : assignessList) {
				if(StringUtils.isNotBlank(assiness)) {
					userIdList.addAll(Arrays.asList(StringUtils.split(assiness, ",")));
				}
			}
			userNameMap = kgoUserRepository.findAllById(userIdList).stream().collect(Collectors.toMap(u -> u.getUserId(), u -> u.getName()));
		}
		return userNameMap;
	}

	/**
	 * 動態流程管理 - 儲存, 新增流程 && deploy.
	 *
	 * @param rq the rq
	 * @return the api base response
	 */
	@Override
	public ActFlowManagerSaveFlowRs saveAction(ActFlowManagerSaveRq rq, ActFlowEnableEnum enableEnum) {
		ActFlowManagerSaveFlowRs rs = new ActFlowManagerSaveFlowRs();
		ActFlowManagerSaveFlowViewForm viewForm = new ActFlowManagerSaveFlowViewForm();
		rs.setData(viewForm);
		try {
			TpiFlowBean flowBean = rq.getTpiFlow();
			
			TpiFlow tpiFlow = new TpiFlow();
            Timestamp now = DateUtil.getCurrentTimestamp();
            String flowId= flowBean.getFlowId();
            Boolean isDeploy = true;

            LOGGER.info(">>>>> saveAction rq:{}", JsonUtil.toJSONString(rq));
            /** === 判斷是否執行activiti deploy === */
            // 預設流程不執行deploy 或 無task流程
            if(rq.getTpiFlow().getIsDefault() || CollectionUtils.isEmpty(flowBean.getFlowTasks())) {
            	isDeploy = false;
            }
            
            /** === 1. 儲存TpiFlow 主檔  === */
            // 案件新增
            if(StringUtils.isBlank(flowId)) {
            	// TODO確認格式
            	String newFlowId = UUID_PREFIX + RandomUtil.getUUID();
            	tpiFlow.setFlowId(newFlowId);

            	// 是否預設
            	tpiFlow.setIsDefault(flowBean.getIsDefault() != true ? false : true);
            	tpiFlow.setFlowEnable(enableEnum.getCode());
            	tpiFlow.setCreateTime(now);
            	tpiFlow.setUpdateTime(now);
            // 案件編輯
            } else {
            	Optional<TpiFlow> otf = tpiFlowRepository.findById(flowId);
            	if(otf.isPresent()) {
            		tpiFlow = otf.get();
                	tpiFlow.setUpdateTime(now);
            	}
            }
            tpiFlow.setFlowName(flowBean.getFlowName());
        	tpiFlow.setFlowDesc(flowBean.getFlowDesc());
        	tpiFlow.setOrganId(flowBean.getOrganId());
        	tpiFlow.setJsonData(flowBean.getJsonData());
        	
        	// modify: 2020.12.24   無tasks -> 暫存流程, 否則為一般流程 (執行deploy)
        	tpiFlow.setFlowType(CollectionUtils.isEmpty(flowBean.getFlowTasks()) ? ActFlowTypeEnum.T.getType() : ActFlowTypeEnum.N.getType());
            
            tpiFlow = tpiFlowRepository.save(tpiFlow);

            /** === 2. 刪除 已存在TpiFlowTask 任務節點 === */ 
            // 編輯案件 delete and insert FlowTask
            if(StringUtils.isNotBlank(flowId)) {
//            	List<Integer> deleteSeqList = flowBean.getFlowTasks().stream().map(t -> t.getTaskSeq()).collect(Collectors.toList());
            	int delCount = tpiFlowTaskRepository.deleteByFlowId(flowId);
            	LOGGER.info("刪除 已存在TpiFlowTask 任務節點數 :{}", delCount);
            }
            /** === 3. 儲存TpiFlowTask 任務節點 === */ 
            String savedFlowId = tpiFlow.getFlowId();
            List<TpiFlowTaskBean> taskBeanList = flowBean.getFlowTasks();
            List<TpiFlowTask> flowTaskList = new ArrayList<>();
            for(TpiFlowTaskBean bean : taskBeanList) {
            	TpiFlowTask flowTask = new TpiFlowTask();
            	flowTask.setTaskSeq(StringUtils.isNotBlank(flowId) ? bean.getTaskSeq() : null);
            	flowTask.setFlowId(savedFlowId);
            	
//            	List<String> userIdList = bean.getTaskAssigneesCombox().getOptions().stream().map(a -> a.getValue()).collect(Collectors.toList());
//            	String taskAssignees = StringUtils.join(userIdList, ",");
            	flowTask.setTasAssignees(bean.getTaskAssignees());
            	// TODO 確認用不到group 
            	// flowTask.setTaskGroups(taskGroups);
            	flowTask.setTaskName(bean.getTaskName());
            	flowTask.setTaskOrder(bean.getTaskOrder());
            	flowTask.setTaskType(bean.getTaskType());
            	flowTask.setNodeId(bean.getNodeId());
            	flowTask.setUpdateTime(now);
            	flowTask.setCreateTime(now);

            	flowTaskList.add(flowTask);
            }
            tpiFlowTaskRepository.saveAll(flowTaskList);

			 /** === 5. 新增流程 && deploy activiti === */
			// 是否 deploy
			if (isDeploy) {
				 /** === Transfer to Dto === */
				TpiFlowDto tpiFlowDto = new TpiFlowDto();
				tpiFlowDto.setFlowId(tpiFlow.getFlowId());
				tpiFlowDto.setFlowName(tpiFlow.getFlowName());
				tpiFlowDto.setFlowDesc(tpiFlow.getFlowDesc());
				tpiFlowDto.setFlowEnable(tpiFlow.getFlowEnable());

				List<TpiFlowTaskDto> flowTaskDtoList = new ArrayList<>();

				// 2020.12.14 忽略type = "3" 節點 (返回) 不走activiti deploy
				flowTaskList = flowTaskList.stream().filter(t -> t.getTaskType() != "3").collect(Collectors.toList());
				for (TpiFlowTask flowTask : flowTaskList) {
					TpiFlowTaskDto flowTaskDto = new TpiFlowTaskDto();
					flowTaskDto.setTaskSeq(flowTask.getTaskSeq());
					
					/** === 2021.01.07 底線區隔taskName taskType (taskType_taskName), 追蹤原taskType用  避免髒資料-> 預設陳核 === */
					ActTaskSubTypeEnum taskSubTypeE = ActTaskSubTypeEnum.getActTaskSubType(flowTask.getTaskType());
					String subTaskType = taskSubTypeE != null ? taskSubTypeE.getSubType() : ActTaskSubTypeEnum.A.getSubType();
					flowTaskDto.setTaskName(String.format("%s_%s", subTaskType, flowTask.getTaskName()));
					/** === 2021.01.07 底線區隔 taskType taskName (taskType_taskName), 追蹤原taskType用 === */
					
					flowTaskDto.setTaskOrder(flowTask.getTaskOrder());
					
	            	// modify: 2020.12.28 任務節點類型  D(分文)、A(陳核)、F(結案)  -> 2 (一般任務類型)
					ActTaskSubTypeEnum[] type2SubTypes = ActTaskTypeEnum.getSubTypes(ActTaskTypeEnum.TASK_TYPE_2);
					if(ArrayUtils.contains(type2SubTypes, ActTaskSubTypeEnum.getActTaskSubType(flowTask.getTaskType()))) {
						flowTaskDto.setTaskType(Integer.valueOf(ActTaskTypeEnum.TASK_TYPE_2.getType()));
					
					// modify: 2020.12.28  else -> 會簽 :1(會簽) 
					} else {
						flowTaskDto.setTaskType(Integer.valueOf(flowTask.getTaskType()));
					}

					flowTaskDto.setTaskAssignees(flowTask.getTasAssignees());
					flowTaskDtoList.add(flowTaskDto);
				}
				tpiFlowDto.setFlowTasks(flowTaskDtoList);
				
				// 執行流程deploy
				String flowDefId = tpiFlowService.addFlowDeployment(tpiFlowDto);

				/** === 6 更新資料庫 processDefinitionId、flowEnable 及 updateTime */
				/** 動態流程 save always Y, 案件流程重送 N (服務表單過濾 N, 表單流程不被服務單選到) */
				String enableFlag = enableEnum.getCode();
				tpiFlowRepository.updateFlowByFlowId(flowDefId, enableFlag, tpiFlow.getFlowId());

				viewForm.setFlowId(savedFlowId);
				viewForm.setFlowDefId(flowDefId);
			}
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new KgoApiException("saveAction error " + e.getMessage(), e);
		}
		return rs;
	}
	
//	public static void main(String[] args) {
//		
//		if(ActTaskTypeEnum.TASK_TYPE_2.getSubTypes().contains(ActTaskSubTypeEnum.getActTaskSubType("D"))) {
//			System.out.print(true);
//		} else {
//			System.out.print(false);
//		}
//
//	}

	/**
	 * 動態流程管理 - 修改重送流程(待處理匣案件修改送流程).
	 *
	 * @param rq the rq
	 * @return the api base response
	 */
	@Override
	public ApiBaseResponse<BaseViewForm> resendFlowAction(ActFlowManagerResendFlowRq rq) {
		String caseId = rq.getCaseId();
		LOGGER.info(">>>>> resendFlowAction() caseId : {} ", caseId);
		ApiBaseResponse<BaseViewForm> rs = new ApiBaseResponse<BaseViewForm>();
		KgoCaseMain caseMain = null;
		try {
			BackendLoginUserInfo beUser = KgoUtil.getBackendLoginUser();
			if(StringUtils.isNotBlank(caseId)) {
				Optional<KgoCaseMain> caseMainOpt = kgoCaseMainRepository.findById(caseId);
				if(caseMainOpt.isPresent()) {
					caseMain = caseMainOpt.get();
				}
			} else {
				// 案件編號錯誤. 
				throw new KgoApiException(new ErrorResult(KgoBackEndApiError.CASE_ID_ERROR));
			}

			if(caseMain != null) {
				String processId = caseMain.getProcessId();
				LOGGER.info(">>>>> caseMain oldProcessId : {} ", processId);
				// 增加流程備註
				List<Task> taskList = taskService.createTaskQuery().processInstanceId(processId).list();

				//正常應該會取到至少一筆，取第一筆task來寫備註即可(請做好錯誤判斷，如果取不到，表示流程已經結束了)
				if(CollectionUtils.isNotEmpty(taskList) ) {
					// 異動前 - 儲存流程異動表.
					saveResendFlowBefore(caseMain.getCaseId(), processId);
					
					/** == 案件流程重送刪除流程 &&　紀錄歷程 == */
					// add comment
					String loginUserName = KgoUtil.getBackendLoginUser().getName();
					String caseOrganId = caseMain.getCaseOrgan();
					String caseStatus = CaseStatusEnum.RESEND_FLOW.getLabel();// 案件狀態: 修改重送流程
					String comment = String.format("由%s%s", loginUserName, "修改重送流程"); // 內容
					String organName = KgoUtil.getOrganName(caseOrganId);// 承辦的機關名稱
					activitiService.addCommentByTaskId(beUser.getUserId(), taskList.get(0).getId(), caseStatus, comment, organName, loginUserName, CaseStatusEnum.RESEND_FLOW);
					runtimeService.deleteProcessInstance(processId, "修改重送流程");
					/** == 案件流程重送刪除流程 &&　紀錄歷程 == */
					
					/** == 儲存flow == */
					ActFlowManagerSaveRq saveRq = new ActFlowManagerSaveRq();
					saveRq.setTpiFlow(rq.getTpiFlow());
					// 案件流程重送 N (服務表單過濾 N, 表單流程不被服務單選到)
					BackEndFlowManagerService backEndFlowManagerService = SpringUtil.getBean(BackEndFlowManagerServiceImpl.class);
					ActFlowManagerSaveFlowRs saveRs = backEndFlowManagerService.saveAction(saveRq, ActFlowEnableEnum.N);
					
					/** === 啟動新流程、回寫 caseMain ===*/
					String flowId = saveRs.getData().getFlowId();
					String flowDefId = saveRs.getData().getFlowDefId();
					// 啟動新流程
					ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(flowId);
					String newProcessId = processInstance.getId();
					caseMain.setProcessId(newProcessId);
					kgoCaseMainRepository.save(caseMain);
					
					LOGGER.info(">>>>> caseMain newProcessId : {} ", newProcessId);
					/** == 啟動新流程、回寫 caseMain == */
					
					/** == 儲存caseMain flow 異動紀錄 == */
//					#更新案件的流程資訊
//					原本案件的表格只用一個欄位來記錄processId，這邊建議要多一個表格來記錄該案件所有的流程編號(一對多，紀錄caseId,processId, createTime)
//					原欄位只記錄最新的那一筆
					
					// 異動後 - 儲存流程異動表.
					saveResendFlowAfter(caseMain.getCaseId(), newProcessId, flowId, flowDefId);
	
					LOGGER.info(">>>>> resendFlowAction() save kgoCaseMainResendFlow finish !");
					/** == 儲存caseMain flow 異動紀錄 == */
				}
			}else {
				// 查無此案件. 
				throw new KgoApiException(new ErrorResult(KgoBackEndApiError.CAN_NOT_FIND_CASE));
			}
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new KgoApiException("resendFlowAction error " + e.getMessage(), e);
		}
		return rs;
	}
	
	/**
	 * 儲存流程異動表 - 異動前.
	 *
	 * @param caseId the case id
	 * @param processId the process id
	 * @param flowId the flow id
	 * @param flowDefId the flow def id
	 */
	private void saveResendFlowBefore(String caseId, String processId) {
		KgoCaseMainResendFlow resendFlow = new KgoCaseMainResendFlow();
		resendFlow.setCaseId(caseId);
		resendFlow.setProcessId(processId);
		resendFlow.setFlowId(StringUtils.EMPTY);
		resendFlow.setFlowDefId(StringUtils.EMPTY);
		resendFlow.setCreateTime(DateUtil.getCurrentTimestamp());
		kgoCaseMainResendFlowRepository.save(resendFlow);
	}
	
	/**
	 * 儲存流程異動表 - 異動後.
	 *
	 * @param caseId the case id
	 * @param processId the process id
	 * @param flowId the flow id
	 * @param flowDefId the flow def id
	 */
	private void saveResendFlowAfter(String caseId, String processId, String flowId, String flowDefId) {
		KgoCaseMainResendFlow resendFlow = new KgoCaseMainResendFlow();
		resendFlow.setCaseId(caseId);
		resendFlow.setProcessId(processId);
		resendFlow.setFlowId(flowId);
		resendFlow.setFlowDefId(flowDefId);
		resendFlow.setCreateTime(DateUtil.getCurrentTimestamp());
		kgoCaseMainResendFlowRepository.save(resendFlow);
	}

	/**
	 * 刪除流程.
	 *
	 * @param rq the rq
	 * @return the act flow manager delete rs
	 */
	@Override
	public ActFlowManagerDeleteRs deleteAction(ActFlowManagerDeleteRq rq) {
		ActFlowManagerDeleteRs rs = new ActFlowManagerDeleteRs();
		try {
			String flowId = rq.getFlowId();
			if(StringUtils.isNotBlank(flowId)) {
				List<KgoCaseset> casesetList = kgoCasesetRepository.findByFlowId(flowId);
				
				if(CollectionUtils.isEmpty(casesetList)) {
					// 刪除flow
					tpiFlowRepository.deleteById(flowId);
					
					tpiFlowTaskRepository.deleteByFlowId(flowId);
				} else {
					// 此流程已有服務綁定. 
					throw new KgoApiException(new ErrorResult(KgoBackEndApiError.FLOW_IS_USED));
				}
			} else {
				throw new KgoApiException(new ErrorResult(KgoBackEndApiError.CAN_NOT_FIND_FLOW_ERROR));
			}
			
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new KgoApiException("deleteAction error " + e.getMessage(), e);
		}
		return rs;
	}
}
