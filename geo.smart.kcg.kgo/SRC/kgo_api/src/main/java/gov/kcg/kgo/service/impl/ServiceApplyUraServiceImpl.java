package gov.kcg.kgo.service.impl;


import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.dto.Activiti.HistoryDataDto;
import gov.kcg.kgo.enums.backend.ActivitiTaskNameEnum;
import gov.kcg.kgo.enums.backend.ApplyCaseStatusEnum;
import gov.kcg.kgo.enums.backend.CaseStatusEnum;
import gov.kcg.kgo.enums.backend.KgoRoleEnum;
import gov.kcg.kgo.enums.common.ComboBoxStatusEnum;
import gov.kcg.kgo.enums.common.sso.LoginAuthTokenType;
import gov.kcg.kgo.geoentity.GeoKgoAgent;
import gov.kcg.kgo.geoenum.GeoBooleanType;
import gov.kcg.kgo.georepository.GeoKgoAgentRepository;
import gov.kcg.kgo.geoservice.GeoAgentService;
import gov.kcg.kgo.geoservice.GeoTaskCommentService;
import gov.kcg.kgo.model.*;
import gov.kcg.kgo.repository.*;
import gov.kcg.kgo.service.ActivitiService;
import gov.kcg.kgo.service.ServiceApplyUraService;
import gov.kcg.kgo.service.impl.helper.CommonServiceHelper;
import gov.kcg.kgo.service.impl.transaction.ProcessTransaction;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.util.MessageUtil;
import gov.kcg.kgo.viewModel.backend.caseHadle.base.rs.bean.CaseHandleCaseViewCaseHistoryDataGrid;
import gov.kcg.kgo.viewModel.backend.serviceApplyUra.pendingReview.rq.*;
import gov.kcg.kgo.viewModel.backend.serviceApplyUra.pendingReview.rs.*;
import gov.kcg.kgo.viewModel.compoent.checkBox.CheckBox;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.bean.CaseProcessSearchHistoryDataGrid;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServiceApplyUraServiceImpl implements ServiceApplyUraService {

    @Autowired
    private ActivitiService activitiService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private KgoUraApplyRepository kgoUraApplyRepository;

    @Autowired
    private KgoUserRoleRepository kgoUserRoleRepository;

    @Autowired
    private KgoUserRepository kgoUserRepository;

    private CommonServiceHelper commonServiceHelper = CommonServiceHelper.getInstance();

    @Autowired
    private KgoRoleRepository kgoRoleRepository;

    private static final String COMBOX_REVIEW_USER_LABEL_STR = "name";

    private static final String COMBOX_REVIEW_USER_VALUE_STR = "userId";

    @Autowired
    private KgoOrganRepository kgoOrganRepository;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private ProcessTransaction processTransaction;

    @Autowired
    private KgoUnitRepository kgoUnitRepository;

    @Autowired
    private GeoAgentService geoAgentService;

    @Autowired
    GeoKgoAgentRepository geoKgoAgentRepository;

    @Autowired
    GeoTaskCommentService geoTaskCommentService;

    @Override
    public ServiceApplyUraPendingReviewM1ApproveToM2Rs serviceApplyUraPendingReviewM1ApproveToM2(ServiceApplyUraPendingReviewM1ApproveToM2Rq rq) {
        Task task = taskService.createTaskQuery().taskId(rq.getTaskId()).singleResult();
        String userId = KgoUtil.getBackendLoginUser().getUserId();
        KgoUser kgoUser = kgoUserRepository.getOne(userId);
        List<KgoUraApply> kgoUraApplyList = kgoUraApplyRepository.findByProcessId(task.getProcessInstanceId());
        KgoUraApply kgoUraApply = null;
        if (!CollectionUtils.isEmpty(kgoUraApplyList)) {
            kgoUraApply = kgoUraApplyList.get(0);
            kgoUraApply.setStatus(ApplyCaseStatusEnum.D.getValue());
            kgoUraApply.setResult(rq.getResult());
            kgoUraApply.setManager2(rq.getManager());
        }
        KgoOrgan kgoOrgan = kgoOrganRepository.getOne(kgoUser.getOrgan());
        activitiService.addCommentByTaskId(kgoUraApply.getApplyUser(), rq.getTaskId(), ApplyCaseStatusEnum.D.getLabel(), ActivitiTaskNameEnum.M1.getLabel() + kgoUser.getName() + messageUtil.getMessage("kgo.backend.ura.send") + ActivitiTaskNameEnum.M2.getLabel(), kgoOrgan.getOrganName(), kgoUser.getName(), null);
        activitiService.completeFlowByTaskIdApproveAndPassManager2(rq.getTaskId(), true, false, rq.getManager());
        return new ServiceApplyUraPendingReviewM1ApproveToM2Rs();
    }

    @Override
    public ServiceApplyUraPendingReviewM1ApproveToOMRs serviceApplyUraPendingReviewM1ApproveToOM(ServiceApplyUraPendingReviewM1ApproveToOMRq rq) {
        Task task = taskService.createTaskQuery().taskId(rq.getTaskId()).singleResult();
        String userId = KgoUtil.getBackendLoginUser().getUserId();
        KgoUser kgoUser = kgoUserRepository.getOne(userId);
        List<KgoUraApply> kgoUraApplyList = kgoUraApplyRepository.findByProcessId(task.getProcessInstanceId());
        KgoUraApply kgoUraApply = null;
        if (!CollectionUtils.isEmpty(kgoUraApplyList)) {
            kgoUraApply = kgoUraApplyList.get(0);
            kgoUraApply.setResult(rq.getResult());
            kgoUraApply.setStatus(ApplyCaseStatusEnum.P.getValue());
        }
        KgoOrgan kgoOrgan = kgoOrganRepository.getOne(kgoUser.getOrgan());
        activitiService.addCommentByTaskId(kgoUraApply.getApplyUser(), rq.getTaskId(), ApplyCaseStatusEnum.P.getLabel(), ActivitiTaskNameEnum.M1.getLabel() + kgoUser.getName() + messageUtil.getMessage("kgo.backend.ura.send") + ActivitiTaskNameEnum.UNIT_M.getLabel(), kgoOrgan.getOrganName(), kgoUser.getName(), null);
        activitiService.completeFlowByTaskIdApproveAndPass(rq.getTaskId(), true, true);
        return new ServiceApplyUraPendingReviewM1ApproveToOMRs();
    }

    @Override
    public ServiceApplyUraPendingReviewM2ApproveToOMRs serviceApplyUraPendingReviewM2ApproveToOM(ServiceApplyUraPendingReviewM2ApproveToOMRq rq) {
        Task task = taskService.createTaskQuery().taskId(rq.getTaskId()).singleResult();
        String userId = KgoUtil.getBackendLoginUser().getUserId();
        KgoUser kgoUser = kgoUserRepository.getOne(userId);
        List<KgoUraApply> kgoUraApplyList = kgoUraApplyRepository.findByProcessId(task.getProcessInstanceId());
        KgoUraApply kgoUraApply = null;
        if (!CollectionUtils.isEmpty(kgoUraApplyList)) {
            kgoUraApply = kgoUraApplyList.get(0);
            kgoUraApply.setResult(rq.getResult());
            kgoUraApply.setStatus(ApplyCaseStatusEnum.P.getValue());
        }
        KgoOrgan kgoOrgan = kgoOrganRepository.getOne(kgoUser.getOrgan());
        activitiService.addCommentByTaskId(kgoUraApply.getApplyUser(), rq.getTaskId(), ApplyCaseStatusEnum.P.getLabel(), ActivitiTaskNameEnum.M2.getLabel() + kgoUser.getName() + messageUtil.getMessage("kgo.backend.ura.send") + ActivitiTaskNameEnum.UNIT_M.getLabel(), kgoOrgan.getOrganName(), kgoUser.getName(), null);
        activitiService.completeFlowByTaskIdApproveAndPass(rq.getTaskId(), true, false);
        return new ServiceApplyUraPendingReviewM2ApproveToOMRs();
    }

    @Override
    public ServiceApplyUraPendingReviewM2ApproveEndRs serviceApplyUraPendingReviewM2ApproveEnd(ServiceApplyUraPendingReviewM2ApproveEndRq rq) {
        String userId = KgoUtil.getBackendLoginUser().getUserId();
        KgoUser kgoUser = kgoUserRepository.getOne(userId);
        Task task = taskService.createTaskQuery().taskId(rq.getTaskId()).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        List<KgoUraApply> kgoUraApplyList = kgoUraApplyRepository.findByProcessId(processInstanceId);
        KgoUraApply kgoUraApply = null;
        if (!CollectionUtils.isEmpty(kgoUraApplyList)) {
            kgoUraApply = kgoUraApplyList.get(0);
            kgoUraApply.setStatus(ApplyCaseStatusEnum.F.getValue());
            kgoUraApply.setResult(rq.getResult());
            String applyRole = kgoUraApply.getApplyRole();
            if (!StringUtils.isEmpty(applyRole)) {
                List<String> roles = Arrays.asList(applyRole.split(","));
                List<KgoUserRole> kgoUserRoles = new ArrayList<>();
                KgoUraApply finalKgoUraApply = kgoUraApply;
                roles.forEach(item -> {
                    KgoUserRole kgoUserRole = new KgoUserRole();
                    KgoUserRolePK kgoUserRolePK = new KgoUserRolePK();
                    kgoUserRolePK.setRoleId(item);
                    kgoUserRolePK.setUserId(finalKgoUraApply.getApplyUser());
                    kgoUserRole.setId(kgoUserRolePK);
                    kgoUserRoles.add(kgoUserRole);
                });
                kgoUserRoleRepository.saveAllBatch(kgoUserRoles);
            }
        }
        KgoOrgan kgoOrgan = kgoOrganRepository.getOne(kgoUser.getOrgan());
        activitiService.addCommentByTaskId(kgoUraApply.getApplyUser(), rq.getTaskId(), ApplyCaseStatusEnum.F.getLabel(), ActivitiTaskNameEnum.M2.getLabel() + kgoUser.getName() + messageUtil.getMessage("kgo.backend.ura.agree"), kgoOrgan.getOrganName(), kgoUser.getName(), null);
        activitiService.completeFlowByTaskIdApproveAndPass(rq.getTaskId(), true, true);
        return new ServiceApplyUraPendingReviewM2ApproveEndRs();
    }

    @Override
    public ServiceApplyUraPendingReviewNotApproveRs serviceApplyUraPendingReviewNotApprove(ServiceApplyUraPendingReviewNotApproveRq rq) {
        Task task = taskService.createTaskQuery().taskId(rq.getTaskId()).singleResult();
        String userId = KgoUtil.getBackendLoginUser().getUserId();
        KgoUser kgoUser = kgoUserRepository.getOne(userId);
        List<KgoUraApply> kgoUraApplyList = kgoUraApplyRepository.findByProcessId(task.getProcessInstanceId());
        KgoUraApply kgoUraApply = null;
        if (!CollectionUtils.isEmpty(kgoUraApplyList)) {
            kgoUraApply = kgoUraApplyList.get(0);
            kgoUraApply.setStatus(ApplyCaseStatusEnum.J.getValue());
            kgoUraApply.setResult(rq.getResult());
        }
        KgoOrgan kgoOrgan = kgoOrganRepository.getOne(kgoUser.getOrgan());
        activitiService.addCommentByTaskId(kgoUraApply.getApplyUser(), rq.getTaskId(), ApplyCaseStatusEnum.J.getLabel(), task.getName() + kgoUser.getName() + messageUtil.getMessage("kgo.backend.ura.not.agree"), kgoOrgan.getOrganName(), kgoUser.getName(), null);
        activitiService.completeFlowByTaskIdApprove(rq.getTaskId(), "false");
        return new ServiceApplyUraPendingReviewNotApproveRs();
    }

    @Override
    public ServiceApplyUraPendingReviewOMApproveEndRs serviceApplyUraPendingReviewOMApproveEnd(ServiceApplyUraPendingReviewOMApproveEndRq rq) {
        Task task = taskService.createTaskQuery().taskId(rq.getTaskId()).singleResult();
        String userId = KgoUtil.getBackendLoginUser().getUserId();
        KgoUser kgoUser = kgoUserRepository.getOne(userId);
        String processInstanceId = task.getProcessInstanceId();
        List<KgoUraApply> kgoUraApplyList = kgoUraApplyRepository.findByProcessId(processInstanceId);
        KgoUraApply kgoUraApply = null;
        if (!CollectionUtils.isEmpty(kgoUraApplyList)) {
            kgoUraApply = kgoUraApplyList.get(0);
            kgoUraApply.setStatus(ApplyCaseStatusEnum.F.getValue());
            kgoUraApply.setResult(rq.getResult());
            String applyRole = kgoUraApply.getApplyRole();
            if (!StringUtils.isEmpty(applyRole)) {
                List<String> roles = Arrays.asList(applyRole.split(","));
                List<KgoUserRole> kgoUserRoles = new ArrayList<>();
                KgoUraApply finalKgoUraApply = kgoUraApply;
                roles.forEach(item -> {
                    KgoUserRole kgoUserRole = new KgoUserRole();
                    KgoUserRolePK kgoUserRolePK = new KgoUserRolePK();
                    kgoUserRolePK.setRoleId(item);
                    kgoUserRolePK.setUserId(finalKgoUraApply.getApplyUser());
                    kgoUserRole.setId(kgoUserRolePK);
                    kgoUserRoles.add(kgoUserRole);
                });
                kgoUserRoleRepository.saveAllBatch(kgoUserRoles);
            } // if (!StringUtils.isEmpty(applyRole))
            /**  GEO 20211115 add 非市府員工登入後台 */
            KgoUser applyUserEntity = kgoUserRepository.getOne(kgoUraApply.getApplyUser());
            if (!Objects.equals(LoginAuthTokenType.getLoginAuthType(applyUserEntity.getUserLoginType()), LoginAuthTokenType.BASIC)) {
                applyUserEntity.setOrgan(kgoUraApply.getApplyOrgan());
                applyUserEntity.setUnit(kgoUraApply.getApplyUnit());
                kgoUserRepository.save(applyUserEntity);
            } // if (!Objects.equals
        } // if (!CollectionUtils.isEmpty(kgoUraApplyList))
        KgoOrgan kgoOrgan = kgoOrganRepository.getOne(kgoUser.getOrgan());

        /** GEO 2021115 add 簽核意見*/
        String comment = ActivitiTaskNameEnum.UNIT_M.getLabel() + kgoUser.getName() + messageUtil.getMessage("kgo.backend.ura.agree");
        GeoKgoAgent agentData = null;
        List<GeoKgoAgent> principalList = geoAgentService.getPrincipalList(userId, DateUtil.getCurrentTimestamp(), GeoBooleanType.DISABLED.getCode());
        if (principalList != null && principalList.size() > 0) {
            for (GeoKgoAgent agent : principalList) {
                List<Task> taskList = getPrincipalTaskList(agent, task.getId());
                if (taskList != null && taskList.size() > 0) {
                    agentData = agent;
                }
            }
        } //if (principalList != null &&
        if (agentData != null) {
            KgoUser user = kgoUserRepository.findByUserId(agentData.getPrincipalUserId());
            comment = ActivitiTaskNameEnum.UNIT_M.getLabel() + "由" + kgoUser.getName() + "代理" + user.getName() + messageUtil.getMessage("kgo.backend.ura.agree");
            agentData.setIsSigned(GeoBooleanType.ENABLED.getCode());
            geoKgoAgentRepository.save(agentData);
        }
        Comment commentEntity = activitiService.addCommentByTaskId(kgoUraApply.getApplyUser(), rq.getTaskId(), ApplyCaseStatusEnum.F.getLabel(), comment, kgoOrgan.getOrganName(), kgoUser.getName(), null);
        geoTaskCommentService.saveTaskComment(commentEntity.getId(), rq.getTaskComment(),  kgoUser.getOrgan(),  kgoUser.getUserId());
        /** GEO 2021115 add 簽核意見*/

        activitiService.completeFlowByTaskIdApprove(rq.getTaskId(), "true");
        return new ServiceApplyUraPendingReviewOMApproveEndRs();
    } //serviceApplyUraPendingReviewOMApproveEnd

    private List<Task> getPrincipalTaskList(GeoKgoAgent agent, String taskId) {
        KgoUser user = kgoUserRepository.findByUserId(agent.getPrincipalUserId());
//        LOGGER.info("caseHandlePendingSignDispatch agent :" + agent.getPrincipalUserId());
        List<Task> taskList = taskService.createTaskQuery().taskCandidateUser(agent.getPrincipalUserId()).taskId(taskId).list();
        List<Task> assignTaskList = taskService.createTaskQuery().taskAssignee(agent.getPrincipalUserId()).taskId(taskId).list();
        List<String> formattedRolesScaUra = getUserRoleList(agent.getPrincipalUserId()).stream().map(items -> String.format("%s-%s", items, user.getOrgan())).collect(Collectors.toList());
        List<String> formattedRoles = getUserRoleList(agent.getPrincipalUserId()).stream().map(items -> String.format("%s;%s", items, user.getOrgan())).collect(Collectors.toList());
        formattedRoles.addAll(formattedRolesScaUra);
        List<Task> groupTask = taskService.createTaskQuery().taskCandidateGroupIn(formattedRoles).taskId(taskId).list();
        taskList.addAll(assignTaskList);
        taskList.addAll(groupTask);
        return taskList;
    }
    
    private List<String> getUserRoleList(String userId) {
        return kgoUserRoleRepository.findAllByIdUserId(userId).stream().map(l -> {
            return l.getId().getRoleId();
        }).collect(Collectors.toList());
    }

    @Override
    public ServiceApplyUraPendingReviewViewRs serviceApplyUraPendingReviewView(ServiceApplyUraPendingReviewViewRq rq) {
        ServiceApplyUraPendingReviewViewRs serviceApplyUraPendingReviewViewRs = new ServiceApplyUraPendingReviewViewRs();
        KgoUraApply kgoUraApply = kgoUraApplyRepository.findById(rq.getCaseId()).get();
        ServiceApplyUraPendingReviewViewForm serviceApplyUraPendingReviewViewForm = new ServiceApplyUraPendingReviewViewForm();
        BackendLoginUserInfo backendLoginUser = KgoUtil.getBackendLoginUser();
        String applyOrgan = backendLoginUser.getOrgan();
        List<KgoUser> kgoUsers = kgoUserRepository.findByOrgan(applyOrgan);
        kgoUsers = kgoUsers.stream().filter(item -> !item.getUserId().equals(backendLoginUser.getUserId())).collect(Collectors.toList());
        ComboBox reviewerComboBox = commonServiceHelper.getComboBox(kgoUsers, COMBOX_REVIEW_USER_LABEL_STR,
                COMBOX_REVIEW_USER_VALUE_STR, StringUtils.EMPTY, ComboBoxStatusEnum.ALL.getCode(), false);

        /** role checkbox list **/
        List<String> roles = null;
        if (null != kgoUraApply && StringUtils.isNotEmpty(kgoUraApply.getApplyRole())) {
            roles = Arrays.asList(kgoUraApply.getApplyRole().split(","));
        }
        List<String> finalRoles = roles;
        List<CheckBox> applyRoleCheckBox = kgoRoleRepository.findAll().stream().map(l -> {
            boolean flag;
            if (!CollectionUtils.isEmpty(finalRoles) && finalRoles.contains(l.getRoleId())) {
                flag = true;
            } else {
                flag = false;

            }
            CheckBox checkBox = commonServiceHelper.getCheckBox(l.getRoleName(), l.getRoleId(), flag);
            return checkBox;
        }).collect(Collectors.toList());
        serviceApplyUraPendingReviewViewForm.setApplyRoles(applyRoleCheckBox);
        serviceApplyUraPendingReviewViewForm.setReviewer(reviewerComboBox);
        KgoOrgan kgoOrgan = kgoOrganRepository.getOne(kgoUraApply.getApplyOrgan());
        serviceApplyUraPendingReviewViewForm.setApplyOrgan(kgoOrgan.getOrganName());
        KgoUnitPK kgoUnitPK = new KgoUnitPK();
        kgoUnitPK.setUnitId(kgoUraApply.getApplyUnit());
        kgoUnitPK.setOrganId(kgoUraApply.getApplyOrgan());
        Optional<KgoUnit> kgoUnitOptional = kgoUnitRepository.findById(kgoUnitPK);
        if (kgoUnitOptional.isPresent()) {
            KgoUnit kgoUnit = kgoUnitOptional.get();
            serviceApplyUraPendingReviewViewForm.setApplyUnit(kgoUnit.getUnitName());
        }
        KgoUser kgoUser = kgoUserRepository.getOne(kgoUraApply.getApplyUser());
        String image = processTransaction.showProcessImg(kgoUraApply.getProcessId());
        serviceApplyUraPendingReviewViewForm.setImage(image);
        serviceApplyUraPendingReviewViewForm.setApplyUser(kgoUser.getName());
        serviceApplyUraPendingReviewViewForm.setCaseId(kgoUraApply.getCaseId());
        serviceApplyUraPendingReviewViewForm.setEmail(kgoUraApply.getEmail());
        serviceApplyUraPendingReviewViewForm.setPhone(kgoUraApply.getPhone());
        List<HistoryDataDto> historyDataDto = activitiService.getHistoryData(kgoUraApply.getProcessId());
        List<ServiceApplyUraPendingReviewHistoryDataGrid> historyData = castToHistoryDataGrid(historyDataDto);
        serviceApplyUraPendingReviewViewForm.setServiceApplyUraPendingReviewHistoryDataGrid(historyData);

        String taskId = rq.getTaskId();
        if (StringUtils.isNotEmpty(taskId)) {
            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
            if (null != task) {
                serviceApplyUraPendingReviewViewForm.setUraStage(task.getName());
            } else {
                serviceApplyUraPendingReviewViewForm.setUraStage("");
            }
        } else {
            serviceApplyUraPendingReviewViewForm.setUraStage("");
        }
        KgoUser kgoUserManager1 = kgoUserRepository.findByUserId(kgoUraApply.getManager1());
        if (null != kgoUserManager1) {
            serviceApplyUraPendingReviewViewForm.setManager1(kgoUserManager1.getName());
        }
//        KgoUser kgoUserManager2 = kgoUserRepository.findByUserId(kgoUraApply.getManager2());
//        if (null != kgoUserManager2) {
//            serviceApplyUraPendingReviewViewForm.setManager2(kgoUserManager2.getName());
//        }
        serviceApplyUraPendingReviewViewRs.setData(serviceApplyUraPendingReviewViewForm);
        return serviceApplyUraPendingReviewViewRs;
    }

    private List<ServiceApplyUraPendingReviewHistoryDataGrid> castToHistoryDataGrid(List<HistoryDataDto> historyDataDto) {
        List<ServiceApplyUraPendingReviewHistoryDataGrid> histories = new ArrayList<>();
        historyDataDto.forEach(item -> {
            ServiceApplyUraPendingReviewHistoryDataGrid grid = new ServiceApplyUraPendingReviewHistoryDataGrid();
            grid.setStatus(item.getStatus());
            grid.setOrgan(item.getOrganName());
            grid.setContent(item.getContent());
            grid.setTaker(item.getOfficer());
            grid.setDealTime(item.getDealTime());
            histories.add(grid);
        });
        // sort by dealTime(DESC)
        histories.sort(Collections.reverseOrder(Comparator.comparing(ServiceApplyUraPendingReviewHistoryDataGrid::getDealTime)));
        return histories;
    }
}
