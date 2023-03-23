package gov.kcg.kgo.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.CommentEntityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.dto.Activiti.HistoryDataDto;
import gov.kcg.kgo.enums.backend.CaseFlowTypeEnum;
import gov.kcg.kgo.enums.backend.CaseMainStatusEnum;
import gov.kcg.kgo.enums.backend.CaseStatusEnum;
import gov.kcg.kgo.enums.backend.KgoRoleEnum;
import gov.kcg.kgo.enums.common.AcceptSetEnum;
import gov.kcg.kgo.enums.common.CaseTypeEnum;
import gov.kcg.kgo.enums.error.ActivitiApiError;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.enums.error.KgoFrontEndApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.model.KgoCaseMain;
import gov.kcg.kgo.model.KgoCaseset;
import gov.kcg.kgo.model.KgoCasesetOfficer;
import gov.kcg.kgo.model.KgoScaApply;
import gov.kcg.kgo.model.KgoUraApply;
import gov.kcg.kgo.model.TpiFlow;
import gov.kcg.kgo.repository.KgoCaseMainRepository;
import gov.kcg.kgo.repository.KgoCasesetOfficerRepository;
import gov.kcg.kgo.repository.KgoCasesetRepository;
import gov.kcg.kgo.repository.KgoCasesetUnitRepository;
import gov.kcg.kgo.repository.KgoScaApplyRepository;
import gov.kcg.kgo.repository.KgoUraApplyRepository;
import gov.kcg.kgo.repository.TpiFlowRepository;
import gov.kcg.kgo.service.ActivitiService;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.JsonUtil;

/**
 * SSO Test method
 * 
 * @author TPIuser
 *
 */
@Service("ActivitiService")
public class ActivitiServiceImpl extends KgoBackEndServiceImpl implements ActivitiService {
	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiServiceImpl.class);

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private KgoCasesetUnitRepository kgoCasesetUnitRepository;

	@Autowired
	private KgoCasesetRepository kgoCasesetRepository;

	@Autowired
	private KgoCaseMainRepository kgoCaseMainRepository;

	@Autowired
	private KgoCasesetOfficerRepository kgoCasesetOfficerRepository;

	@Autowired
	private KgoScaApplyRepository kgoScaApplyRepository;

	@Autowired
	private KgoUraApplyRepository kgoUraApplyRepository;

	@Autowired
	private TpiFlowRepository tpiFlowRepository;

	private final String FLOW_TEMPLATE_NAME_SA = "CaseApply";
	private final String FLOW_TEMPLATE_NAME_SCA_URA = "AuthApply"; // TODO:待確認

	private final static String BUSINESS_PARAMETER_SCENARIO = "scenario";
	private final static String BUSINESS_PARAMETER_ASSIGN_GROUP = "assignGroup";
	private final static String BUSINESS_PARAMETER_ASSIGN_USER = "assignUser";
	private final static String BUSINESS_PARAMETER_ASSIGN_USERS = "assignUsers";

	private final static String SCENARIO_FIELD_ONE = "1";
	private final static String SCENARIO_FIELD_TWO = "2";
	private final static String SCENARIO_FIELD_THREE = "3";

	private final static String GROUP_FIELD_DIVISION = "分文群組";
	private final static String GROUP_FIELD_UNDERTAKER = "承辦群組";

	private final static String USER_FIELD_CASE_OFFICER = "承辦人";

	/**
	 * Activiti流程部屬. TODO：deploy flow這個部分，可以設定server on
	 * load的時候自動佈署，不要跟啟動流程綁在一起，會一直在佈署
	 */
	@Override
	public void deployFlow() {
		Deployment deployment = repositoryService.createDeployment().addClasspathResource("process/AuthApply.bpmn").addClasspathResource("process/CaseApply.bpmn")
				// Activiti流程部屬 重複資源 不會部屬 (先每次都重新deploy)
				// .enableDuplicateFiltering()
				.deploy();
		LOGGER.info("部屬id為:{}, 部屬Name為:{}", deployment.getId(), deployment.getName());
	}

	/**
	 * SA 流程啟動
	 *
	 * @param caseId
	 * @return
	 */
	@Override
	public String processCaseStart(String caseId) {
		KgoCaseMain kgoCaseMain = getKgoCaseMain(caseId);
		String processId = processCaseStart(kgoCaseMain);

		saveProcessId(caseId, processId);

		return processId;
	}

	@Override
	public String processCaseStart(String caseId, String caseOrganName) {
		KgoCaseMain kgoCaseMain = getKgoCaseMain(caseId);
		String processId = processCaseStart(kgoCaseMain, caseOrganName);

		saveProcessId(caseId, processId);

		return processId;
	}

	@Override
	public String processCaseStart(KgoCaseMain kgoCaseMain) {
		return processCaseStart(kgoCaseMain, "");
	}

	@Override
	public String processCaseStart(KgoCaseMain kgoCaseMain, String caseOrganName) {
		LOGGER.info("ActivitiServiceImpl processCaseStart...");
		KgoCaseset kgoCaseset = getKgoCaseset(kgoCaseMain.getCaseSetId());
		CaseFlowTypeEnum caseFlowTypeEnum = CaseFlowTypeEnum.getCaseFlowTypeEnum(kgoCaseset.getCaseFlowType());
		HashMap<String, Object> businessMap = new HashMap<>();
		String processId = StringUtils.EMPTY;
		String processKey = StringUtils.EMPTY;
		try {
			AcceptSetEnum acceptSetEnum = AcceptSetEnum.getEnum(kgoCaseset.getAcceptSet());
			// 你起案的時候 可以判斷一下 如果 不是 B3 就不用呼叫activit 起案 By @Kelly Tsou
			switch (caseFlowTypeEnum) {
			case B3:

				/** === 2020.12.06 有flowId 走新流程 動態流程 start === */
				String flowId = kgoCaseset.getFlowId();
				if (StringUtils.isNotBlank(flowId)) {
					Optional<TpiFlow> optFlow = tpiFlowRepository.findById(flowId);
					if (optFlow.isPresent()) {
						TpiFlow flow = optFlow.get();
						processKey = flow.getFlowId();
						ProcessInstance process = runtimeService.startProcessInstanceByKey(processKey);
						processId = process.getId();
					}
				}
				/** === 2020.12.06 有flowId 走新流程 動態流程 end === */

				else {
					if (acceptSetEnum == AcceptSetEnum.OFFICER) {
						List<KgoCasesetOfficer> list = this.kgoCasesetOfficerRepository.findByIdCaseSetId(kgoCaseMain.getCaseSetId());
						String officers = StringUtils.join(list.stream().map(x -> x.getId().getCaseOfficer()).collect(Collectors.toList()), ",");
						businessMap.put(BUSINESS_PARAMETER_SCENARIO, SCENARIO_FIELD_TWO);
						businessMap.put(BUSINESS_PARAMETER_ASSIGN_USERS, officers);
					} else {
						businessMap.put(BUSINESS_PARAMETER_SCENARIO, SCENARIO_FIELD_ONE);
						businessMap.put(BUSINESS_PARAMETER_ASSIGN_GROUP, String.format("%s;%s", KgoRoleEnum.UNIT_A.getValue(), kgoCaseMain.getCaseOrgan()));
					}

					processKey = FLOW_TEMPLATE_NAME_SA;
					ProcessInstance process = runtimeService.startProcessInstanceByKey(processKey, businessMap);
					processId = process.getId();
					LOGGER.info("startProcess - caseId = {}, processId = {}, businessMap = {}", kgoCaseMain.getCaseId(), processId, JsonUtil.toJSONString(businessMap));
				}
				// addComment
				if (StringUtils.isNoneBlank(processId)) {
					CaseMainStatusEnum statusEnum = CaseMainStatusEnum.getCaseMainStatusEnum(kgoCaseMain.getStatus());
					try {
						this.addComment(kgoCaseMain.getApplyUserName(), processId, statusEnum.getLabel(), "民眾申請", caseOrganName, "");
					} catch (Exception e) {
						throw new KgoApiException("processStart addComment error " + e.getMessage(), e);
					}
				}

				break;
			case B1:
			case B2:
				if (businessMap.containsKey(BUSINESS_PARAMETER_ASSIGN_USERS)) {
					kgoCaseMain.setCaseOfficer((String) businessMap.get(BUSINESS_PARAMETER_ASSIGN_USERS));
				}
			default:
				break;
			}

			return processId;
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(ActivitiApiError.PROCESS_START_ERROR.getErrorMsgKey());
			throw new KgoApiException(new ErrorResult(ActivitiApiError.PROCESS_START_ERROR), e);
		}
	} //processCaseStart

	/**
	 * 流程啟動
	 *
	 * @param caseId
	 * @return
	 */
	@Override
	public String processStart(String caseId, String manager, String scenario) {
		LOGGER.info("startProcess");
		try {
			String processKey = FLOW_TEMPLATE_NAME_SCA_URA;
			HashMap<String, Object> businessMap = new HashMap<>();
			businessMap.put("assignUser", manager);
			businessMap.put("scenario", scenario);
			LOGGER.info("businessMap: {} processKey: {}", businessMap, processKey);
			ProcessInstance process = runtimeService.startProcessInstanceByKey(processKey, businessMap);
			String processId = process.getId();
			LOGGER.info("startProcess - caseId = {}, processId = {}", caseId, processId);
			return processId;
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(ActivitiApiError.PROCESS_START_ERROR.getErrorMsgKey());
			throw new KgoApiException("processStart error " + e.getMessage(), e);
		}
	}

	public List<Map<String, Object>> getTaskProcessIdsForCaseUpdate(List<String> userIds, List<String> taskNames) {
		List<Task> userTasks = taskService.createTaskQuery().taskAssigneeIds(userIds).list();
		List<Map<String, Object>> pidList = new ArrayList<>();
		userTasks.forEach(item -> {
			String currentTaskName = item.getName();
			if (!CollectionUtils.isEmpty(taskNames) && taskNames.contains(currentTaskName)) {
				HashMap<String, Object> map = new HashMap<>();
				map.put("processId", item.getProcessInstanceId());
				map.put("task", item);
				pidList.add(map);
			}
		});
		return pidList;
	}

	public List<Map<String, Object>> getTaskProcessIds(String userId, String role, String taskName1, String taskName2) {
		List<Task> userTasks = taskService.createTaskQuery().taskCandidateUser(userId).list();
		List<Task> assigneeTasks = taskService.createTaskQuery().taskAssignee(userId).list();
		userTasks.addAll(assigneeTasks);
		if (!role.isEmpty()) {
			List<Task> groupTasks = taskService.createTaskQuery().taskCandidateGroup(role).list();// UNIT_A;397120000J
			userTasks.addAll(groupTasks);
		}

		List<Map<String, Object>> pidList = new ArrayList<>();
		userTasks.forEach(item -> {
			String currentTaskName = item.getName();
			if (currentTaskName.equalsIgnoreCase(taskName1) || currentTaskName.equalsIgnoreCase(taskName2)) {
				HashMap<String, Object> map = new HashMap<>();
				map.put("processId", item.getProcessInstanceId());
				map.put("task", item);
//				pidList.add(item.getProcessInstanceId());
//				LOGGER.info("currentTaskName: " + currentTaskName);
//				LOGGER.info("taskId: " + item.getProcessInstanceId());
				pidList.add(map);
			}
		});
		return pidList;
	}

	@Override
	public List<Map<String, Object>> getPendingCaseProcessIdsSign(String organId, String loginUserId, BackendLoginUserInfo backendLoginUser, List<String> taskNames) {
		List<String> roles = backendLoginUser.getRoles();
		return getPendingCaseProcessIdsSign(organId, loginUserId, roles, taskNames);
	} //getPendingCaseProcessIdsSign

	/**
	 * GEO 20211026 add
	 */
	@Override
	public List<Map<String, Object>> getPendingCaseProcessIdsSign(String organId, String loginUserId, List<String> taskNames ,List<String> roles) {
		List<String> formattedRolesScaUra = roles.stream().map(item -> String.format("%s-%s", item, organId)).collect(Collectors.toList());
		List<String> formattedRoles = roles.stream().map(item -> String.format("%s;%s", item, organId)).collect(Collectors.toList());
		formattedRoles.addAll(formattedRolesScaUra);
		List<Map<String, Object>> processIdSet = getTaskProcessIdsSign(loginUserId, formattedRoles, taskNames);
		return processIdSet;
	} //getPendingCaseProcessIdsSign

	public List<Map<String, Object>> getTaskProcessIdsSign(String userId, List<String> formattedRoles, List<String> taskNames) {
		List<Task> userTasks = taskService.createTaskQuery().taskCandidateUser(userId).list();
		List<Task> assigneeTasks = taskService.createTaskQuery().taskAssignee(userId).list();
		userTasks.addAll(assigneeTasks);
		if (!CollectionUtils.isEmpty(formattedRoles)) {
			List<Task> groupTasks = taskService.createTaskQuery().taskCandidateGroupIn(formattedRoles).list();// UNIT_A;397120000J
			userTasks.addAll(groupTasks);
		}

		List<Map<String, Object>> pidList = new ArrayList<>();
		userTasks.forEach(item -> {
			String currentTaskName = item.getName();
			if (!CollectionUtils.isEmpty(taskNames) && taskNames.contains(currentTaskName)) {
				HashMap<String, Object> map = new HashMap<>();
				map.put("processId", item.getProcessInstanceId());
				map.put("task", item);
				pidList.add(map);
			}
		});
		return pidList;
	}

	/**
	 * 傳入user物件 roleId
	 *
	 * @param organId, loginUserId, taskName
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getPendingCaseProcessIds(String organId, String loginUserId, BackendLoginUserInfo backendLoginUser, List<String> taskNames) {
		List<String> roles = backendLoginUser.getRoles();
		return getPendingCaseProcessIds(organId, loginUserId, taskNames, roles);
	} //getPendingCaseProcessIds

	/**
	 * GEO 20211026 add
	 */
	@Override
	public List<Map<String, Object>> getPendingCaseProcessIds(String organId, String loginUserId, List<String> taskNames, List<String> roles) {
		//LOGGER.info("ActivitiServiceImpl getPendingCaseProcessIds roles.size(): "+roles.size());
		List<String> formattedRolesScaUra = roles.stream().map(item -> String.format("%s-%s", item, organId)).collect(Collectors.toList());
		List<String> formattedRoles = roles.stream().map(item -> String.format("%s;%s", item, organId)).collect(Collectors.toList());
		formattedRoles.addAll(formattedRolesScaUra);
		//LOGGER.info("ActivitiServiceImpl getPendingCaseProcessIds formattedRoles.size(): "+formattedRoles.size());
		List<Map<String, Object>> processIdSet = getTaskProcessIds(loginUserId, formattedRoles, taskNames);
		return processIdSet;
	} //getPendingCaseProcessIds

	public List<Map<String, Object>> getTaskProcessIds(String userId, List<String> formattedRoles, List<String> taskNames) {
		//LOGGER.info("ActivitiServiceImpl getTaskProcessIds userId: "+userId);
		List<Task> userTasks = taskService.createTaskQuery().taskCandidateUser(userId).list();
		//LOGGER.info("ActivitiServiceImpl getTaskProcessIds userTasks.size(): "+userTasks.size());
		List<Task> assigneeTasks = taskService.createTaskQuery().taskAssignee(userId).list();
		//LOGGER.info("ActivitiServiceImpl getTaskProcessIds assigneeTasks.size(): "+assigneeTasks.size());
		userTasks.addAll(assigneeTasks);
		if (!CollectionUtils.isEmpty(formattedRoles)) {
			List<Task> groupTasks = taskService.createTaskQuery().taskCandidateGroupIn(formattedRoles).list();// UNIT_A;397120000J
			userTasks.addAll(groupTasks);
		}

		List<Map<String, Object>> pidList = new ArrayList<>();
		userTasks.forEach(item -> {
			String currentTaskName = item.getName();
			if (!CollectionUtils.isEmpty(taskNames) && !taskNames.contains(currentTaskName)) {
				HashMap<String, Object> map = new HashMap<>();
				map.put("processId", item.getProcessInstanceId());
				map.put("task", item);
				pidList.add(map);
			}
		});
		return pidList;
	} //getTaskProcessIds

	/**
	 * 傳入user物件 roleId
	 * 
	 * @param taskNames
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getPendingCaseProcessIdsForCaseUpdate(List<String> userIds, List<String> taskNames) {
		List<Map<String, Object>> processIdSet = getTaskProcessIdsForCaseUpdate(userIds, taskNames);
		return processIdSet;
	}

	/**
	 * 傳入user物件 roleId
	 * 
	 * @param organId, loginUserId, taskName
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getPendingCaseProcessIds(String organId, String loginUserId, String taskName1, String taskName2) {
		String role = String.format("%s;%s", KgoRoleEnum.UNIT_A.getValue(), organId);
		List<Map<String, Object>> processIdSet = getTaskProcessIds(loginUserId, role, taskName1, taskName2);
		return processIdSet;
	}

	/**
	 * 
	 * @param loginUserId, taskName
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getPendingCaseProcessIds(String loginUserId, String taskName1, String taskName2) {
		List<Map<String, Object>> processIdSet = getTaskProcessIds(loginUserId, "", taskName1, taskName2);
		return processIdSet;
	}

	/**
	 * 
	 * @param loginUserId, taskName
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getPendingCaseProcessIds(String loginUserId, String taskName) {
		List<Map<String, Object>> processIdSet = getTaskProcessIds(loginUserId, StringUtils.EMPTY, taskName, "");
		return processIdSet;
	}

	@Autowired
	private HistoryService historyService;

	/**
	 * TODO:部分待確認
	 * 
	 * 取得歷史資料
	 * 
	 * @param processId
	 * @return
	 */
	@Override
	public List<HistoryDataDto> getHistoryData(String processId) {
		if (StringUtils.isEmpty(processId)) {
			return new ArrayList<>();
		}
//		Task task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
//		if (task == null) {
//			return new ArrayList<HistoryDataDto>();
//		}
		// 取得審核歷程
		List<Comment> processCommentList = taskService.getProcessInstanceComments(processId);


		/** GEO 20210831 add --> 來自 ACT_HI_COMMENT, 資料主要存在 MESSAGE_ 欄位 **/
		LOGGER.info("ActivitiServiceImpl getHistoryData processId/processCommentList.size(): "+processId+"/"+processCommentList.size());


		List<HistoryDataDto> historyList = new ArrayList<>();
		processCommentList.forEach(item -> {
			CommentEntityImpl comment;
			if (item instanceof CommentEntityImpl) {
				comment = (CommentEntityImpl) item;
				String fullMessage = comment.getMessage();
				String commentId = comment.getId();
				String[] messageArray = fullMessage.split(";");
				String pId = messageArray[0]; // processId
				String caseStatus = messageArray[1]; // 案件狀態
				String caseComment = messageArray[2]; // 案件內容
				String caseOrganName = messageArray[3]; // 承辦的機關名稱
				String caseOfficer = messageArray[4]; // 承辦人
				String dateString = messageArray[5]; // 處理時間

				List<HistoricTaskInstance> historicTaskInstanceList = historyService.createHistoricTaskInstanceQuery().taskId(comment.getTaskId()).list();
				LOGGER.info("ActivitiServiceImpl getHistoryData processCommentList historicTaskInstanceList.size(): "+historicTaskInstanceList.size());

				if (historicTaskInstanceList.size() > 0) {
					HistoricTaskInstance historicTaskInstance = historicTaskInstanceList.get(0);

					String nodeTaskString = StringUtils.EMPTY;
					if (ObjectUtils.isNotEmpty(historicTaskInstance)) {
						nodeTaskString = historicTaskInstance.getName();// messageArray[6];//工作節點名稱
					}
					HistoryDataDto historyDto = new HistoryDataDto();
					try {
						String newCaseStatus = new String(caseStatus.getBytes("UTF-8"));
						String newaseComment = new String(caseComment.getBytes("UTF-8"));
						String newCcaseOrganName = new String(caseOrganName.getBytes("UTF-8"));
						String newCaseOfficer = new String(caseOfficer.getBytes("UTF-8"));
						String newTaskNode = new String(nodeTaskString.getBytes("UTF-8"));
						historyDto.setStatus(newCaseStatus);
						historyDto.setContent(newaseComment);
						historyDto.setOrganName(newCcaseOrganName);
						historyDto.setOfficer(newCaseOfficer);
						historyDto.setDealTime(dateString);
						historyDto.setTaskNode(newTaskNode);
						/** GEO 20211101 add 增加簽核意見*/
						historyDto.setCommentId(comment.getId());
						historyList.add(historyDto);
					} catch (Exception ex) {
						LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
						throw new KgoApiException("getComment error " + ex.getMessage(), ex);
					}
				}

			}
		});
		historyList.sort(Collections.reverseOrder((item1, item2) -> item2.getDealTime().compareTo(item1.getDealTime())));
		return historyList;
	} //getHistoryData

	/**
	 * 取得歷史資料
	 *
	 * @param taskId
	 * @return
	 */
	@Override
	public List<HistoryDataDto> getHistoryDataByTaskId(String taskId) {
		if (StringUtils.isNotEmpty(taskId)) {
			return new ArrayList<>();
		}
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) {
			return new ArrayList<>();
		}
		// 取得審核歷程
		List<Comment> processCommentList = taskService.getProcessInstanceComments(task.getProcessInstanceId());
		List<HistoryDataDto> historyList = new ArrayList<>();
		processCommentList.forEach(item -> {
			CommentEntityImpl comment = null;
			if (item instanceof CommentEntityImpl) {
				comment = (CommentEntityImpl) item;
				String fullMessage = comment.getMessage();
				String[] messageArray = fullMessage.split(";");
				String pId = messageArray[0]; // processId
				String caseStatus = messageArray[1]; // 案件狀態
				String caseComment = messageArray[2]; // 案件內容
				String caseOrganName = messageArray[3]; // 承辦的機關名稱
				String caseOfficer = messageArray[4]; // 承辦人
				String dateString = messageArray[5]; // 處理時間

				HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(comment.getTaskId()).singleResult();
				String nodeTaskString = StringUtils.EMPTY;
				if (ObjectUtils.isNotEmpty(historicTaskInstance)) {
					nodeTaskString = historicTaskInstance.getName();// messageArray[6];//工作節點名稱

				}
				HistoryDataDto historyDto = new HistoryDataDto();
				try {
					String newCaseStatus = new String(caseStatus.getBytes("UTF-8"));
					String newaseComment = new String(caseComment.getBytes("UTF-8"));
					String newCcaseOrganName = new String(caseOrganName.getBytes("UTF-8"));
					String newCaseOfficer = new String(caseOfficer.getBytes("UTF-8"));
					String newTaskNode = new String(nodeTaskString.getBytes("UTF-8"));
					historyDto.setStatus(newCaseStatus);
					historyDto.setContent(newaseComment);
					historyDto.setOrganName(newCcaseOrganName);
					historyDto.setOfficer(newCaseOfficer);
					historyDto.setDealTime(dateString);
					historyDto.setTaskNode(newTaskNode);
					historyList.add(historyDto);
				} catch (Exception ex) {
					LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
					throw new KgoApiException("getComment error " + ex.getMessage(), ex);
				}
			}
		});
		historyList.sort(Collections.reverseOrder((item1, item2) -> item1.getDealTime().compareTo(item2.getDealTime())));
		return historyList;
	}

	public List<HistoryDataDto> getHistoryData(List<String> processIdList) {
		List<HistoryDataDto> historyList = new ArrayList<>();
		processIdList.forEach(processId -> {
			List<HistoryDataDto> dtoList = getHistoryData(processId);
			historyList.addAll(dtoList);
		});
		return historyList;
	}

	/**
	 * 
	 * 新增案件歷程
	 * 
	 * @param loginUserId:   當前登入者
	 * @param processId
	 * @param caseStatus:    案件狀態
	 * @param caseComment:   案件內容
	 * @param caseOrganName: 承辦的機關名稱(Optional)
	 * @param caseOfficer:   承辦人(Optional)
	 * 
	 */
	@Override
	public void addComment(String loginUserId, String processId, String caseStatus, String caseComment, String caseOrganName, String caseOfficer) {

		/** GEO 20210831 add --> 將資料寫入 ACT_HI_COMMENT, 資料主要存在 MESSAGE_ 欄位 **/
		LOGGER.info("ActivitiServiceImpl addComment loginUserId/processId/caseStatus/caseComment: "+loginUserId+"/"+processId+"/"+caseStatus+"/"+caseComment);


		try {
			// 判斷是否為 Null
			caseStatus = StringUtils.defaultString(caseStatus, "");
			caseComment = StringUtils.defaultString(caseComment, "");
			caseOrganName = StringUtils.defaultString(caseOrganName, "");
			caseOfficer = StringUtils.defaultString(caseOfficer, "");

			// Task task =
			// taskService.createTaskQuery().processInstanceId(processId).singleResult();
			String taskId = null;// task.getId();
			Date date = new Date();
			String dateString = DateUtil.dateToString(date, DateUtil.PATTEN_FULL_TIME_MS_SLASH);
			String newCaseStatus = new String(caseStatus.getBytes("UTF-8"), "UTF-8");
			String newaseComment = new String(caseComment.getBytes("UTF-8"), "UTF-8");
			String newCcaseOrganName = new String(caseOrganName.getBytes("UTF-8"), "UTF-8");
			String newCaseOfficer = new String(caseOfficer.getBytes("UTF-8"), "UTF-8");
			StringBuilder commentBuilder = new StringBuilder();
			commentBuilder.append(processId).append(";").append(newCaseStatus).append(";").append(newaseComment).append(";").append(newCcaseOrganName).append(";").append(newCaseOfficer).append(";")
					.append(dateString);
			Authentication.setAuthenticatedUserId(loginUserId);
			taskService.addComment(taskId, processId, commentBuilder.toString());
		} catch (Exception ex) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("addComment error " + ex.getMessage(), ex);
		}
	} //addComment

	/**
	 * 
	 * @param caseId
	 * @return
	 */
	private CaseTypeEnum checkCaseType(String caseId) {
		return CaseTypeEnum.getEnum(caseId.chars().findFirst().toString());
	}

	/**
	 * 從案件編號取得案件種類ID
	 * 
	 * @param caseId
	 * @return
	 */
	private String getCaseSetId(String caseId) {
		String caseSetId = StringUtils.EMPTY;
		if (CaseTypeEnum.SA.equals(checkCaseType(caseId))) {
			KgoCaseMain kgoCaseMain = kgoCaseMainRepository.getOne(caseId);
			caseSetId = kgoCaseMain.getCaseSetId();
		} else if (CaseTypeEnum.SCA.equals(checkCaseType(caseId))) {
			KgoScaApply kgoScaApply = kgoScaApplyRepository.getOne(caseId);
			caseSetId = kgoScaApply.getCaseSetId();
		} else if (CaseTypeEnum.URA.equals(checkCaseType(caseId))) {
			KgoUraApply kgoUraApply = kgoUraApplyRepository.getOne(caseId);
			caseSetId = kgoUraApply.getCaseSetId();
		}
		return caseSetId;
	}

	/**
	 * 儲存 ProcessId
	 * 
	 * @param caseId
	 * @param processId
	 */
	private void saveProcessId(String caseId, String processId) {
		if (CaseTypeEnum.SA.equals(checkCaseType(caseId))) {
			KgoCaseMain kgoCaseMain = kgoCaseMainRepository.getOne(caseId);
			kgoCaseMain.setProcessId(processId);
			kgoCaseMainRepository.save(kgoCaseMain);
		} else if (CaseTypeEnum.SCA.equals(checkCaseType(caseId))) {
			KgoScaApply kgoScaApply = kgoScaApplyRepository.getOne(caseId);
			kgoScaApply.setProcessId(processId);
			kgoScaApplyRepository.save(kgoScaApply);
		} else if (CaseTypeEnum.URA.equals(checkCaseType(caseId))) {
			KgoUraApply kgoUraApply = kgoUraApplyRepository.getOne(caseId);
			kgoUraApply.setProcessId(processId);
			kgoUraApplyRepository.save(kgoUraApply);
		}
	}

	/**
	 * 取得流程啟動時需要的參數集合
	 * 
	 * TODO:流程參數設定細節待確認
	 * 
	 * @param caseId
	 * @return
	 */
	private HashMap<String, Object> getBusinessDataMap(String caseId, String manager, String assignGroup) {
		HashMap<String, Object> businessMap = new HashMap<>();
		// if (CaseTypeEnum.SA.equals(checkCaseType(caseId))) {
		// String caseSetId = getCaseSetId(caseId);
		// KgoCaseset kgoCaseset = kgoCasesetRepository.getOne(caseSetId);
		// String caseFlowType = kgoCaseset.getCaseFlowType();
		// if (caseFlowType.equalsIgnoreCase(CaseFlowTypeEnum.B3.getValue())
		// // || caseFlowType.equalsIgnoreCase(CaseFlowTypeEnum.C3.getValue()) TODO :
		// // 案件流程變動需確認
		// ) {
		// if
		// (ObjectUtils.isNotEmpty(kgoCasesetUnitRepository.findByIdCaseSetId(caseSetId)))
		// {
		// businessMap.put(BUSINESS_PARAMETER_SCENARIO, SCENARIO_FIELD_ONE);
		// businessMap.put(BUSINESS_PARAMETER_ASSIGN_GROUP, GROUP_FIELD_DIVISION);
		// businessMap.put(BUSINESS_PARAMETER_ASSIGN_USER, USER_FIELD_CASE_OFFICER);
		// } else if (ObjectUtils
		// .isNotEmpty(kgoCasesetOfficerRepository.getCasesetOfficerDataByCaseSetId(caseSetId)))
		// {
		// businessMap.put(BUSINESS_PARAMETER_SCENARIO, SCENARIO_FIELD_TWO);
		// businessMap.put(BUSINESS_PARAMETER_ASSIGN_GROUP, GROUP_FIELD_UNDERTAKER);
		// businessMap.put(BUSINESS_PARAMETER_ASSIGN_USER, USER_FIELD_CASE_OFFICER);
		// } else {
		// throw new
		// KgoApiException(ActivitiApiError.UNKNOWN_EXCEPTION.getErrorMsgKey());
		// }
		// }
		// } else if (CaseTypeEnum.SCA.equals(checkCaseType(caseId)) ||
		// CaseTypeEnum.URA.equals(checkCaseType(caseId))) {
		businessMap.put("assignManager1", manager); // 接收者的主管 dropdown
		businessMap.put("assignGroup", assignGroup);
		// }

		return businessMap;
	}

	private KgoCaseMain getKgoCaseMain(String caseId) {
		Optional<KgoCaseMain> kgoCaseMain = this.kgoCaseMainRepository.findById(caseId);
		if (kgoCaseMain.isPresent()) {
			return kgoCaseMain.get();
		} else {
			throw new KgoApiException(KgoFrontEndApiError.DATA_NOT_EXIST.getErrorMsgKey());
		}
	}

	private KgoCaseset getKgoCaseset(String caseSetId) {
		Optional<KgoCaseset> kgoCaseset = kgoCasesetRepository.findById(caseSetId);
		if (kgoCaseset.isPresent()) {
			return kgoCaseset.get();
		} else {
			throw new KgoApiException(KgoFrontEndApiError.DATA_NOT_EXIST.getErrorMsgKey());
		}
	}

	@Deprecated
	@Override
	public void nextFlow(String processId, String asigner) {
		LOGGER.info("nextFlow: processId:{} , asigner:{}", processId, asigner);
		Map<String, Object> approveMap = new HashMap<>();
		approveMap.put(BUSINESS_PARAMETER_ASSIGN_USER, asigner);
		Task task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
		String taskId = task.getId();
		taskService.complete(taskId, approveMap);
	}

	@Override
	@Deprecated
	public void nextFlowNew(String processId, String asigner) {
		Map<String, Object> approveMap = new HashMap<>();
		approveMap.put(BUSINESS_PARAMETER_ASSIGN_USER, asigner);
		approveMap.put("approve", true);
		Task task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
		String taskId = task.getId();
		taskService.complete(taskId, approveMap);
	}

	@Override
	public void nextFlowByTaskIdApprove(String taskId, String asigner) {
		LOGGER.info("nextFlowByTaskIdApprove: taskId:{} , asigner:{}", taskId, asigner);
		Map<String, Object> approveMap = new HashMap<>();
		approveMap.put(BUSINESS_PARAMETER_ASSIGN_USER, asigner);
		approveMap.put("approve", "true");
		taskService.complete(taskId, approveMap);
	}

	@Override
	public void nextFlowByTaskId(String taskId, String asigner) {
		LOGGER.info("nextFlowByTaskId: taskId:{} , asigner:{}", taskId, asigner);
		Map<String, Object> approveMap = new HashMap<>();
		approveMap.put(BUSINESS_PARAMETER_ASSIGN_USER, asigner);
		taskService.complete(taskId, approveMap);
	}

	@Override
	public void dispatchFlowByTaskId(String taskId, String organ, String assignee, String acceptType) {
		LOGGER.info("dispatchFlowByTaskId: taskId:{} , organ:{}, assignee:{}, acceptType:{}", taskId, organ, assignee, acceptType);
		if (acceptType.equalsIgnoreCase(AcceptSetEnum.UNIT.getValue())) { // 機關
			String oldGroup = String.format("%s;%s", KgoRoleEnum.UNIT_A.getValue(), organ);
			String newGroup = String.format("%s;%s", KgoRoleEnum.UNIT_A.getValue(), assignee);
			// 新增別的群組之後再移除舊的群組
			taskService.addCandidateGroup(taskId, newGroup);
			taskService.deleteCandidateGroup(taskId, oldGroup);
			taskService.setVariable(taskId, "assignGroup", newGroup);
		} else if (acceptType.equalsIgnoreCase(AcceptSetEnum.OFFICER.getValue())) { // 承辦人
			taskService.setAssignee(taskId, assignee);
		}
	}

	@Override
	@Deprecated
	public void dispatchFlow(String processId, String assignor, String assignee, String acceptType) {
		LOGGER.info("dispatchFlow: processId:{} , assignor:{}, rejectTo:{}, acceptType:{}", processId, assignor, assignee, acceptType);
		Task task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
		String taskId = task.getId();
		if (acceptType.equalsIgnoreCase(AcceptSetEnum.UNIT.getValue())) { // 機關
			String oldGroup = String.format("%s;%s", KgoRoleEnum.UNIT_A.getValue(), assignor);
			String newGroup = String.format("%s;%s", KgoRoleEnum.UNIT_A.getValue(), assignee);
			// 新增別的群組之後再移除舊的群組
			taskService.addCandidateGroup(taskId, newGroup);
			taskService.deleteCandidateGroup(taskId, oldGroup);
		} else if (acceptType.equalsIgnoreCase(AcceptSetEnum.OFFICER.getValue())) { // 承辦人
			taskService.setAssignee(taskId, assignee);
		}
	}

	@Override
	@Deprecated
	public void nextFlowCorrect(String processId, boolean isApprove, String rejectTo) {
		LOGGER.info("nextFlowCorrect: processId:{} , isApprove:{}, rejectTo:{}", processId, isApprove, rejectTo);
		Map<String, Object> approveMap = new HashMap<>();
		if (isApprove) {
			approveMap.put("approve", "true");
		} else {
			approveMap.put("approve", "false");
			approveMap.put("rejectTo", rejectTo);
		}
		Task task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
		String taskId = task.getId();
		taskService.complete(taskId, approveMap);
	}

	@Override
	@Deprecated
	public void nextFlow(String processId, boolean isApprove, String rejectTo) {
		LOGGER.info("nextFlow: processId:{} , isApprove:{}, rejectTo:{}", processId, isApprove, rejectTo);
		Map<String, Object> approveMap = new HashMap<>();
		if (isApprove) {
			approveMap.put("Approved", "true");
		} else {
			approveMap.put("Approved", "false");
			approveMap.put("RejectTo", rejectTo);
		}
		Task task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
		String taskId = task.getId();
		taskService.complete(taskId, approveMap);
	}

	@Override
	public void previousFlowByTaskId(String taskId, String rejectTo) {
		LOGGER.info("previousFlowByTaskId1: taskId:{} , rejectTo:{}", taskId, rejectTo);
		Map<String, Object> approveMap = new HashMap<>();
		approveMap.put("approve", "false");
		approveMap.put("rejectTo", rejectTo);
		LOGGER.info("previousFlowByTaskId2: approveMap:{}", approveMap);
		taskService.complete(taskId, approveMap);
	}

	@Deprecated
	@Override
	public void previousFlow(String processId, String rejectTo) {
		LOGGER.info("previousFlow: processId:{} , rejectTo:{}", processId, rejectTo);
		Map<String, Object> approveMap = new HashMap<>();
		approveMap.put("approve", "false");
		approveMap.put("rejectTo", rejectTo);
		Task task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
		String taskId = task.getId();
		taskService.complete(taskId, approveMap);
	}

	@Override
	@Deprecated
	public void completeFlow(String processId) {
		LOGGER.info("completeFlow: processId:{} ", processId);
		Map<String, Object> approveMap = new HashMap<>();
		approveMap.put("approve", true);
		Task task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
		String taskId = task.getId();
		taskService.complete(taskId, approveMap);

	}

	@Override
	public void completeFlowByTaskId(String taskId) {
		LOGGER.info("completeFlowByTaskId: taskId:{} ", taskId);
		Map<String, Object> approveMap = new HashMap<>();
		approveMap.put("approve", true);
		taskService.complete(taskId, approveMap);
	}

	@Override
	public void completeFlowByTaskIdApproveAndPass(String taskId, boolean approve, boolean pass) {
		LOGGER.info("completeFlowByTaskIdApproveAndPass: taskId:{}, approve: {}, pass:{} ", taskId, approve, pass);
		Map<String, Object> approveMap = new HashMap<>();
		approveMap.put("approve", approve);
		approveMap.put("pass", pass);
		taskService.complete(taskId, approveMap);
	}

	@Override
	public void completeFlowByTaskIdApprove(String taskId, String approve) {
		LOGGER.info("completeFlowByTaskIdApproveAndPass: taskId:{}, approve: {} ", taskId, approve);
		Map<String, Object> approveMap = new HashMap<>();
		approveMap.put("approve", approve);
		taskService.complete(taskId, approveMap);
	}

	@Override
	public void completeFlowByTaskIdApprove(String taskId, boolean approve) {
		LOGGER.info("completeFlowByTaskIdApproveAndPass: taskId:{}, approve: {} ", taskId, approve);
		Map<String, Object> approveMap = new HashMap<>();
		approveMap.put("approve", approve);
		taskService.complete(taskId, approveMap);
	}

	@Override
	public void completeFlowByTaskIdApproveAndPassManager2(String taskId, boolean approve, boolean pass, String manager2) {
		LOGGER.info("completeFlowByTaskIdApproveAndPassManager2: taskId:{}, approve: {}, pass:{}: manager2:{} ", taskId, approve, pass, manager2);
		Map<String, Object> approveMap = new HashMap<>();
		approveMap.put("approve", approve);
		approveMap.put("pass", pass);
		approveMap.put("assignManager2", manager2);
		taskService.complete(taskId, approveMap);
	}

	@Override
	public void completeFlowByTaskIdNotApprove(String taskId) {
		LOGGER.info("completeFlowByTaskIdNotApprove: taskId:{} ", taskId);
		Map<String, Object> approveMap = new HashMap<>();
		approveMap.put("approve", false);
		taskService.complete(taskId, approveMap);
	}

	@Override
	public void correctWithTaskId(String taskId) {
		LOGGER.info("completeFlowByTaskId: taskId:{} ", taskId);
		Map<String, Object> approveMap = new HashMap<>();
		approveMap.put("approve", "true");
		taskService.complete(taskId, approveMap);
	}

	/**
	 * 
	 * 新增案件歷程
	 * 
	 * @param loginUserId:   當前登入者
	 * @param processId
	 * @param caseStatus:    案件狀態
	 * @param caseComment:   案件內容
	 * @param caseOrganName: 承辦的機關名稱(Optional)
	 * @param caseOfficer:   承辦人(Optional)
	 * @param taskNode:      任務節點名稱
	 * 
	 */
	@Override
	public void addComment(String loginUserId, String processId, String caseStatus, String caseComment, String caseOrganName, String caseOfficer, CaseStatusEnum taskNode) {
		try {
			LOGGER.info(" addComment loginUserId:{}, processId: {}, caseStatus:{}, caseComment:{}, caseOrganName:{}, caseOfficer:{}, taskNode:{}", loginUserId, processId, caseStatus, caseComment,
					caseOrganName, caseOfficer, taskNode);
			// 判斷是否為 Null
			caseStatus = StringUtils.defaultString(caseStatus, "");
			caseComment = StringUtils.defaultString(caseComment, "");
			caseOrganName = StringUtils.defaultString(caseOrganName, "");
			caseOfficer = StringUtils.defaultString(caseOfficer, "");
			taskNode = taskNode == null ? CaseStatusEnum.UNKNOW : taskNode;
			List<Task> tasks = taskService.createTaskQuery().processInstanceId(processId).list();
			if (tasks.size() > 0) {
				String taskId = tasks.get(0).getId();
				Date date = new Date();
				String newCaseStatus = new String(caseStatus.getBytes("UTF-8"), "UTF-8");
				String newaseComment = new String(caseComment.getBytes("UTF-8"), "UTF-8");
				String newCcaseOrganName = new String(caseOrganName.getBytes("UTF-8"), "UTF-8");
				String newCaseOfficer = new String(caseOfficer.getBytes("UTF-8"), "UTF-8");
				String dateString = DateUtil.dateToString(date, DateUtil.PATTEN_FULL_TIME_MS_SLASH);
				String taskNodeName = CaseStatusEnum.getApplyTypeEnum(taskNode.getValue()).getLabel();
				String newTaskNodeName = new String(taskNodeName.getBytes("UTF-8"), "UTF-8");
				StringBuilder commentBuilder = new StringBuilder();
				commentBuilder.append(processId).append(";").append(newCaseStatus).append(";").append(newaseComment).append(";").append(newCcaseOrganName).append(";").append(newCaseOfficer)
						.append(";").append(dateString).append(";").append(newTaskNodeName);
				Authentication.setAuthenticatedUserId(loginUserId);
				taskService.addComment(taskId, processId, commentBuilder.toString());
			}
		} catch (Exception ex) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("addComment error " + ex.getMessage(), ex);
		}

		// tset print

	}

	/**
	 *
	 * 新增案件歷程
	 *
	 * @param loginUserId:   當前登入者
	 * @param taskId
	 * @param caseStatus:    案件狀態
	 * @param caseComment:   案件內容
	 * @param caseOrganName: 承辦的機關名稱(Optional)
	 * @param caseOfficer:   承辦人(Optional)
	 * @param taskNode:      任務節點名稱
	 *
	 */
	@Override
	public Comment addCommentByTaskId(String loginUserId, String taskId, String caseStatus, String caseComment, String caseOrganName, String caseOfficer, CaseStatusEnum taskNode) {
		LOGGER.info(" addCommentByTaskId loginUserId:{}, taskId:{}, caseStatus:{}, caseComment:{}, caseOrganName:{}, caseOfficer:{}, taskNode:{}", loginUserId, taskId, caseStatus, caseComment,
				caseOrganName, caseOfficer, taskNode);
		try {
			// 判斷是否為 Null
			caseStatus = StringUtils.defaultString(caseStatus, "");
			caseComment = StringUtils.defaultString(caseComment, "");
			caseOrganName = StringUtils.defaultString(caseOrganName, "");
			caseOfficer = StringUtils.defaultString(caseOfficer, "");
			taskNode = taskNode == null ? CaseStatusEnum.UNKNOW : taskNode;
			List<Task> tasks = taskService.createTaskQuery().taskId(taskId).list();

			if (tasks.size() > 0) {
				Date date = new Date();
				String newCaseStatus = new String(caseStatus.getBytes("UTF-8"), "UTF-8");
				String newaseComment = new String(caseComment.getBytes("UTF-8"), "UTF-8");
				String newCcaseOrganName = new String(caseOrganName.getBytes("UTF-8"), "UTF-8");
				String newCaseOfficer = new String(caseOfficer.getBytes("UTF-8"), "UTF-8");
				String dateString = DateUtil.dateToString(date, DateUtil.PATTEN_FULL_TIME_MS_SLASH);
				String taskNodeName = CaseStatusEnum.getApplyTypeEnum(taskNode.getValue()).getLabel();
				String newTaskNodeName = new String(taskNodeName.getBytes("UTF-8"), "UTF-8");
				StringBuilder commentBuilder = new StringBuilder();
				commentBuilder.append(taskId).append(";").append(newCaseStatus).append(";").append(newaseComment).append(";").append(newCcaseOrganName).append(";").append(newCaseOfficer).append(";")
						.append(dateString).append(";").append(newTaskNodeName);
				Authentication.setAuthenticatedUserId(loginUserId);
				Comment comment = taskService.addComment(taskId, tasks.get(0).getProcessInstanceId(), commentBuilder.toString());
				return comment;
			}
			return null;
		} catch (Exception ex) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("addComment error " + ex.getMessage(), ex);
		}

		// tset print

	}
}
