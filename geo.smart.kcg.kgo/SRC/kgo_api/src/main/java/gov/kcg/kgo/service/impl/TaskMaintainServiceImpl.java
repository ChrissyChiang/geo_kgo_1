package gov.kcg.kgo.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import gov.kcg.kgo.dto.PushMsgDto;
import gov.kcg.kgo.dto.TaskAndAnnounceQueryDto;
import gov.kcg.kgo.enums.backend.BackendFunctionCodeEnum;
import gov.kcg.kgo.enums.backend.IsPublishEnum;
import gov.kcg.kgo.enums.backend.PublishStateEnum;
import gov.kcg.kgo.enums.backend.ReleaseToEnum;
import gov.kcg.kgo.enums.common.AttFileEnum;
import gov.kcg.kgo.enums.common.SuccessMsg;
import gov.kcg.kgo.enums.common.SysLogExeType;
import gov.kcg.kgo.enums.common.SystemTypeEnum;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.enums.error.KgoFrontEndApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.geoentity.GeoKgoCityMember;
import gov.kcg.kgo.georepository.GeoCityMemberRepos;
import gov.kcg.kgo.model.*;
import gov.kcg.kgo.repository.*;
import gov.kcg.kgo.service.CityCoinAPIService;
import gov.kcg.kgo.service.CommonService;
import gov.kcg.kgo.service.PushService;
import gov.kcg.kgo.service.TaskMaintainService;
import gov.kcg.kgo.service.impl.helper.CommonServiceHelper;
import gov.kcg.kgo.service.impl.helper.OrganUnitManagementServiceHelper;
import gov.kcg.kgo.service.operationmemo.OperationApiMemo;
import gov.kcg.kgo.service.operationmemo.OperationColumn;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.FileUtil;
import gov.kcg.kgo.util.JsonUtil;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.viewModel.backend.taskMaintain.cityCoinSearch.rq.TaskMaintainCityCoinSearchRq;
import gov.kcg.kgo.viewModel.backend.taskMaintain.cityCoinSearch.rs.TaskMaintainCityCoinSearchRs;
import gov.kcg.kgo.viewModel.backend.taskMaintain.cityCoinSearch.rs.bean.TaskMaintainCityCoinSearchViewForm;
import gov.kcg.kgo.viewModel.backend.taskMaintain.delete.rq.TaskMaintainDeleteRq;
import gov.kcg.kgo.viewModel.backend.taskMaintain.delete.rs.TaskMaintainDeleteRs;
import gov.kcg.kgo.viewModel.backend.taskMaintain.delete.rs.bean.TaskMaintainDeleteViewForm;
import gov.kcg.kgo.viewModel.backend.taskMaintain.download.rq.TaskMaintainDownloadRq;
import gov.kcg.kgo.viewModel.backend.taskMaintain.editHome.rq.TaskMaintainEditHomeRq;
import gov.kcg.kgo.viewModel.backend.taskMaintain.editHome.rs.TaskMaintainEditHomeRs;
import gov.kcg.kgo.viewModel.backend.taskMaintain.editHome.rs.bean.TaskMaintainEditHomeFileViewForm;
import gov.kcg.kgo.viewModel.backend.taskMaintain.editHome.rs.bean.TaskMaintainEditHomeViewForm;
import gov.kcg.kgo.viewModel.backend.taskMaintain.home.rs.TaskMaintainHomeRs;
import gov.kcg.kgo.viewModel.backend.taskMaintain.home.rs.bean.TaskMaintainHomeViewForm;
import gov.kcg.kgo.viewModel.backend.taskMaintain.onOff.rq.TaskMaintainOnOffRq;
import gov.kcg.kgo.viewModel.backend.taskMaintain.onOff.rs.TaskMaintainOnOffRs;
import gov.kcg.kgo.viewModel.backend.taskMaintain.onOff.rs.bean.TaskMaintainOnOffViewForm;
import gov.kcg.kgo.viewModel.backend.taskMaintain.organCase.rq.TaskMaintainOrganCaseRq;
import gov.kcg.kgo.viewModel.backend.taskMaintain.organCase.rs.TaskMaintainOrganCaseRs;
import gov.kcg.kgo.viewModel.backend.taskMaintain.organCase.rs.bean.TaskMaintainOrganCaseViewForm;
import gov.kcg.kgo.viewModel.backend.taskMaintain.query.rq.TaskMaintainQueryRq;
import gov.kcg.kgo.viewModel.backend.taskMaintain.query.rs.TaskMaintainQueryRs;
import gov.kcg.kgo.viewModel.backend.taskMaintain.query.rs.bean.TaskMaintainQueryDataGrid;
import gov.kcg.kgo.viewModel.backend.taskMaintain.query.rs.bean.TaskMaintainQueryViewForm;
import gov.kcg.kgo.viewModel.backend.taskMaintain.save.rq.TaskMaintainSaveRq;
import gov.kcg.kgo.viewModel.backend.taskMaintain.save.rs.TaskMaintainSaveRs;
import gov.kcg.kgo.viewModel.backend.taskMaintain.save.rs.bean.TaskMaintainSaveViewForm;
import gov.kcg.kgo.viewModel.cityCoinApi.mission.search.rs.SearchRs;
import gov.kcg.kgo.viewModel.cityCoinApi.mission.search.rs.bean.SearchViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Transactional(rollbackFor = Exception.class)
@Service("TaskMaintainService")
public class TaskMaintainServiceImpl extends KgoBackEndServiceImpl implements TaskMaintainService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskMaintainServiceImpl.class);

    private CommonServiceHelper commonServiceHelper = CommonServiceHelper.getInstance();

    private OrganUnitManagementServiceHelper organUnitManagementServiceHelper = OrganUnitManagementServiceHelper
            .getInstance();

    @Autowired
    private KgoTaskSetRepository kgoTaskSetRepository;

    @Autowired
    private KgoAnnounceRepository kgoAnnounceRepository;

    @Autowired
    private KgoCasesetTaskRepository kgoCasesetTaskRepository;

    @Autowired
    private KgoCasesetRepository kgoCasesetRepository;

    @Autowired
    private KgoAttFileRepository kgoAttFileRepository;

    @Autowired
    private CityCoinAPIService cityCoinAPIService;
    @Autowired
    private CommonService commonService;
    @Autowired
    PushService pushService;
    @Autowired
    GeoCityMemberRepos geoCityMemberRepos;

    private static final String ACTIVITY_ITEM_COMBOBOX_LABEL_FIELD = "Title";
    private static final String ACTIVITY_ITEM_COMBOBOX_VALUE_FIELD = "Seq";
    private static final String CASESET_COMBOBOX_LABEL_FIELD = "CaseSetName";
    private static final String CASESET_COMBOBOX_VALUE_FIELD = "CaseSetId";

    private static final String PFDATE_SYMBOL = "~";

    /**
     * 任務及公告管理-初始畫面
     *
     * @return
     */
    @Override
    public TaskMaintainHomeRs taskMaintainHome() {

        TaskMaintainHomeRs rs = new TaskMaintainHomeRs();
        TaskMaintainHomeViewForm viewForm = new TaskMaintainHomeViewForm();
        try {

            List<TaskMaintainQueryDataGrid> grid = queryTaskAndAnnounce();
            ComboBox releaseObjectComboBox = commonServiceHelper.getComboBoxWithEnum(ReleaseToEnum.class);
            viewForm.setRoles(KgoUtil.getBackendLoginUser().getRoles());
            viewForm.setReleaseObejctComboBox(releaseObjectComboBox);
            viewForm.setGrid(grid);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(">>>>>>>>" + e.getMessage(), e);
            throw new KgoApiException("taskMaintainHome error " + e.getMessage(), e);
        }
        return rs;
    }

    /**
     * 任務及公告管理-任務以及公告查詢
     *
     * @param rq
     * @return
     */
    @Override
    public TaskMaintainQueryRs taskMaintainQuery(TaskMaintainQueryRq rq) {
        TaskMaintainQueryRs rs = new TaskMaintainQueryRs();
        TaskMaintainQueryViewForm viewForm = new TaskMaintainQueryViewForm();

        try {
            String titleName = rq.getTitleName();
            String[] publishTimeSet = rq.getPublishTime();
            String releaseObject = rq.getReleaseObject();
            String publishTimeStart = StringUtils.EMPTY;
            String publishTimeEnd = StringUtils.EMPTY;

            if (!ObjectUtils.isEmpty(publishTimeSet)) {
                publishTimeStart = publishTimeSet[0];
                publishTimeEnd = publishTimeSet[1];
            }

            viewForm.setGrid(queryTaskAndAnnounce(titleName, releaseObject, publishTimeStart, publishTimeEnd));
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(">>>>>>>>" + e.getMessage(), e);
            throw new KgoApiException("taskMaintainQuery error " + e.getMessage(), e);
        }
        return rs;
    }

    /**
     * 任務及公告管理-任務維護(新增/修改)初始畫面
     *
     * @param rq
     * @return
     */
    @Override
    public TaskMaintainEditHomeRs taskMaintainEditHome(TaskMaintainEditHomeRq rq) {
        TaskMaintainEditHomeRs rs = new TaskMaintainEditHomeRs();
        TaskMaintainEditHomeViewForm viewForm = new TaskMaintainEditHomeViewForm();

        try {
            /** rq **/
            Integer seq = rq.getSeq();
            String type = rq.getReleaseObject();

            /** rs **/
            // common
            String activityContent = StringUtils.EMPTY;
            String activityName = StringUtils.EMPTY;
            // only KGO_TASK_SET
            String[] pfDate = new String[]{StringUtils.EMPTY, StringUtils.EMPTY};
            String totalPoint = StringUtils.EMPTY;
            String point = StringUtils.EMPTY;
            String organId = StringUtils.EMPTY;
            List<String> caseSetIds = new ArrayList<String>();
            ComboBox organNameOptions = organUnitManagementServiceHelper.getOrganComboBox(); // TODO:
            ComboBox caseNameOptions = commonServiceHelper.getDefaultComboBox(); // TODO:
            ComboBox activityItemOptions = commonServiceHelper.getDefaultComboBox();

            String taskSeq = StringUtils.EMPTY;

            /** main logic **/
            BackendFunctionCodeEnum backendFunctionCodeEnum = BackendFunctionCodeEnum
                    .getBackendFunctionEnum(rq.getFunctionCode());
            ReleaseToEnum releaseToEnum = ReleaseToEnum.getReleaseToEnum(rq.getReleaseObject());

            if (backendFunctionCodeEnum == BackendFunctionCodeEnum.TaskM) {

                if (releaseToEnum != ReleaseToEnum.FRONT) {
                    throw new KgoApiException(new ErrorResult(KgoFrontEndApiError.RQ_NOT_COMPLETED));
                }

                if (ObjectUtils.isNotEmpty(seq)) {

                    Optional<KgoTaskSet> kgoTaskSet = kgoTaskSetRepository.findById(seq);
                    if (kgoTaskSet.isPresent()) {
                        /** =========== KGO_TASK_SET ============= **/

                        taskSeq = kgoTaskSet.get().getTaskSeq();
                        // 活動名稱
                        activityName = kgoTaskSet.get().getActivityName();
                        // 活動內容
                        activityContent = kgoTaskSet.get().getContentHtml();
                        // 活動日期
                        String activityDate = kgoTaskSet.get().getActivityDate();
                        if (StringUtils.isNotBlank(activityDate)) {
                            pfDate = activityDate.split(PFDATE_SYMBOL);
                        }

                        /** 城市幣API呼叫 **/
                        SearchViewForm searchViewForm = getCityCoinSearch(taskSeq);
                        // 城市幣總點數
                        totalPoint = searchViewForm.getTotalAmount() == null ? null
                                : searchViewForm.getTotalAmount().toString();
                        // 每次核發點數
                        point = searchViewForm.getRewardAmount() == null ? null
                                : searchViewForm.getRewardAmount().toString();
                    }
                }

            } else if (backendFunctionCodeEnum == BackendFunctionCodeEnum.AnnounceM) {

                if (ObjectUtils.isNotEmpty(seq) && kgoAnnounceRepository.existsById(seq)) {
                    /** =========== KGO_ANNOUNCE ============= **/
                    KgoAnnounce kgoAnnounce = kgoAnnounceRepository.getOne(seq);
                    // 活動名稱
                    activityName = kgoAnnounce.getName();
                    // 活動內容
                    activityContent = kgoAnnounce.getContentHtml();
                }
            } else {
                throw new KgoApiException(new ErrorResult(KgoFrontEndApiError.RQ_NOT_COMPLETED));
            }

            if (ObjectUtils.isNotEmpty(seq)) {
                /** =========== KGO_CASESET_TASK ============= **/
                List<KgoCaseset> kgoCasesetList = kgoCasesetRepository.findByIdActivitySeq(seq);
                if (ObjectUtils.isNotEmpty(kgoCasesetList)) {
                    for (KgoCaseset kgoCaseset : kgoCasesetList) {
                        organId = kgoCaseset.getOwnerOrgan();
                        caseSetIds.add(kgoCaseset.getCaseSetId());
                    }

                    organNameOptions.setSelectedVal(organId);
                }
            }

            if (StringUtils.isNotBlank(organId)) {
                caseNameOptions = getCaseSetComboBoxByOrganId(organId, caseSetIds);
            }

            /** set return data **/
            // common
            viewForm.setSeq(seq);
            viewForm.setReleaseObject(type);
            viewForm.setActivityName(activityName);
            viewForm.setActivityContent(activityContent);
            // only KGO_TASK_SET
            viewForm.setPfDate(pfDate);
            viewForm.setActivityItemOptions(activityItemOptions);
            viewForm.setTotalPoint(totalPoint);
            viewForm.setPoint(point);

            viewForm.setOrganNameOptions(organNameOptions);
            viewForm.setCaseNameOptions(caseNameOptions);

            /** 附件 */
            AttFileEnum attFileEnum = AttFileEnum.getEnum(backendFunctionCodeEnum);
            List<KgoAttFile> KgoAttFiles = this.kgoAttFileRepository.findByfSeqAndTypeAndStatus(seq,
                    attFileEnum.getValue(), "S");
            List<TaskMaintainEditHomeFileViewForm> files = KgoAttFiles.stream()
                    .map(x -> new TaskMaintainEditHomeFileViewForm(x.getGuid(), x.getFileName()))
                    .collect(Collectors.toList());
            viewForm.setFiles(files);

            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(">>>>>>>>" + e.getMessage(), e);
            throw new KgoApiException("taskMaintainEditHome error " + e.getMessage(), e);
        }
        return rs;
    }

    /**
     * 任務及公告管理-任務維護儲存
     *
     * @param rq
     * @return
     */
    @Override
    public TaskMaintainSaveRs taskMaintainSave(TaskMaintainSaveRq rq) {
        TaskMaintainSaveRs rs = new TaskMaintainSaveRs();
        TaskMaintainSaveViewForm viewForm = new TaskMaintainSaveViewForm();

        String loginUserId = KgoUtil.getLoginUserId(); // TODO: 改由 session 取得
        KgoApiException error = null;
        OperationApiMemo memo = null;
        String taskOrAnnounce = null;

        Integer seq = rq.getSeq();
        String activityContent = rq.getActivityContent();
        String activityName = rq.getActivityName();
        List<String> caseSetIds = rq.getCaseSetId();
        String[] pfDate = rq.getPfDate();
        String releaseObject = rq.getReleaseObject();
        String taskSeq = rq.getTaskSeq();
        try {
            BackendFunctionCodeEnum backendFunctionCodeEnum = BackendFunctionCodeEnum
                    .getBackendFunctionEnum(rq.getFunctionCode());
            ReleaseToEnum releaseToEnum = ReleaseToEnum.getReleaseToEnum(rq.getReleaseObject());
            SysLogExeType sysLogExeType = ObjectUtils.isEmpty(rq.getSeq()) ? SysLogExeType.TYPE_A
                    : SysLogExeType.TYPE_U;
            memo = super.genOperationMemo(SystemTypeEnum.B, sysLogExeType, backendFunctionCodeEnum);

            if (backendFunctionCodeEnum == BackendFunctionCodeEnum.TaskM) {

                taskOrAnnounce = "任務";

                if (releaseToEnum != ReleaseToEnum.FRONT) {
                    throw new KgoApiException(new ErrorResult(KgoFrontEndApiError.RQ_NOT_COMPLETED));
                }

                /** 城市幣API呼叫 **/
                SearchViewForm searchViewForm = getCityCoinSearch(taskSeq);

                /** =========== KGO_TASK_SET ============= **/
                KgoTaskSet kgoTaskSet = null;
                if (ObjectUtils.isEmpty(seq)) {
                    kgoTaskSet = new KgoTaskSet();
                    kgoTaskSet.setCreateUser(loginUserId);
                    kgoTaskSet.setCreateTime(DateUtil.getCurrentTimestamp());
                    kgoTaskSet.setIsPublish(false);
                } else {
                    kgoTaskSet = kgoTaskSetRepository.getOne(seq);

                    // 刪除後新增
                    if (kgoCasesetTaskRepository.existsActivitySeqCustomQuery(seq)) {
                        kgoCasesetTaskRepository.deleteByActivitySeq(seq);
                    }
                }

                String dateStart = StringUtils.EMPTY;
                String dateEnd = StringUtils.EMPTY;

                if (ObjectUtils.isNotEmpty(pfDate)) {
                    dateStart = pfDate[0];
                    dateEnd = pfDate[1];
                }

                kgoTaskSet.setActivityDate(dateStart + PFDATE_SYMBOL + dateEnd);
                kgoTaskSet.setActivityName(activityName);
                kgoTaskSet.setContentHtml(activityContent);
                kgoTaskSet.setTaskSeq(taskSeq);
                kgoTaskSet.setAppKey(searchViewForm.getAppKey());
                kgoTaskSet.setUpdateTime(DateUtil.getCurrentTimestamp());
                kgoTaskSet.setUpdateUser(loginUserId);
                kgoTaskSet = kgoTaskSetRepository.save(kgoTaskSet);

                if (ObjectUtils.isNotEmpty(caseSetIds)) {
                    /** =========== KGO_CASESET_TASK ============= **/
                    for (String caseSetId : caseSetIds) {
                        KgoCasesetTaskPK id = new KgoCasesetTaskPK();
                        id.setActivitySeq(kgoTaskSet.getActivitySeq());
                        id.setCaseSetId(caseSetId);
                        KgoCasesetTask kgoCasesetTask = new KgoCasesetTask();
                        kgoCasesetTask.setId(id);
                        kgoCasesetTaskRepository.save(kgoCasesetTask);
                    }
                }

                seq = kgoTaskSet.getActivitySeq();

            } else if (backendFunctionCodeEnum == BackendFunctionCodeEnum.AnnounceM) {
                taskOrAnnounce = "公告";

                /** =========== KGO_ANNOUNCE ============= **/
                KgoAnnounce kgoAnnounce = null;
                if (ObjectUtils.isEmpty(seq)) {
                    kgoAnnounce = new KgoAnnounce();
                    kgoAnnounce.setCreateUser(loginUserId);
                    kgoAnnounce.setCreateTime(DateUtil.getCurrentTimestamp());
                    kgoAnnounce.setIsPublish(false);
                } else {
                    kgoAnnounce = kgoAnnounceRepository.getOne(seq);
                }
                kgoAnnounce.setContentHtml(activityContent);
                kgoAnnounce.setName(activityName);
                kgoAnnounce.setUpdateTime(DateUtil.getCurrentTimestamp());
                kgoAnnounce.setUpdateUser(loginUserId);
                kgoAnnounce.setReleaseTo(releaseToEnum.getValue());

                kgoAnnounceRepository.save(kgoAnnounce);
                seq = kgoAnnounce.getSeq();

            } else {
                throw new KgoApiException(new ErrorResult(KgoFrontEndApiError.RQ_NOT_COMPLETED));
            }
            /** =========== KGO_ATT_FILE ============= **/
            if (ObjectUtils.isNotEmpty(rq.getFiles())) {
                AttFileEnum attFileEnum = AttFileEnum.getEnum(backendFunctionCodeEnum);
                List<TaskMaintainEditHomeFileViewForm> files = saveKgoAttFile(seq, attFileEnum, rq.getFiles());
            }

            viewForm.setMsg(SuccessMsg.SAVE.getMsg());
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(">>>> taskMaintainSave >>>>>" + e.getMessage(), e);
            error = new KgoApiException("taskMaintainSave error " + e.getMessage(), e);
        } finally {
            /** === 儲存操作紀錄 === */
            List<OperationColumn> memoList = new ArrayList<>();
            memoList.add(new OperationColumn(taskOrAnnounce, rq.getActivityName()));
            memo.setMemoList(memoList);
            super.saveOperationLog(memo);
            /** === 儲存操作紀錄 === */

            if (error != null) {
                throw error;
            }

        }
        return rs;
    }

    /**
     * 任務及公告管理-任務上下架
     *
     * @param rq
     * @return
     */
    @Override
    public TaskMaintainOnOffRs taskMaintainOnOff(TaskMaintainOnOffRq rq) {
        TaskMaintainOnOffRs rs = new TaskMaintainOnOffRs();
        TaskMaintainOnOffViewForm viewForm = new TaskMaintainOnOffViewForm();

        String updateUser = KgoUtil.getBackendLoginUser().getUserId();
        Integer seq = rq.getSeq();
        String releaseObject = rq.getReleaseObject();
        boolean isPublish = rq.getIsPublish();
        String msg = isPublish ? SuccessMsg.ON.getMsg() : SuccessMsg.OFF.getMsg();
        Timestamp currentTime = DateUtil.getCurrentTimestamp();
        String subject = Strings.EMPTY;
        String content = Strings.EMPTY;
        try {
            BackendFunctionCodeEnum backendFunctionCodeEnum = BackendFunctionCodeEnum
                    .getBackendFunctionEnum(rq.getFunctionCode());
            ReleaseToEnum releaseToEnum = ReleaseToEnum.getReleaseToEnum(rq.getReleaseObject());

            if (backendFunctionCodeEnum == BackendFunctionCodeEnum.TaskM) {

                if (releaseToEnum != ReleaseToEnum.FRONT) {
                    throw new KgoApiException(new ErrorResult(KgoFrontEndApiError.RQ_NOT_COMPLETED));
                }
                /** =========== KGO_TASK_SET ============= **/
                KgoTaskSet kgoTaskSet = kgoTaskSetRepository.getOne(seq);
                subject = kgoTaskSet.getActivityName() + " 上架";
                content = kgoTaskSet.getContentHtml();
                kgoTaskSet.setIsPublish(isPublish);
                kgoTaskSet.setPublishTime(currentTime);
                kgoTaskSet.setUpdateTime(currentTime);
                kgoTaskSet.setUpdateUser(updateUser);
                kgoTaskSetRepository.save(kgoTaskSet);
            } else if (backendFunctionCodeEnum == BackendFunctionCodeEnum.AnnounceM) {
                /** =========== KGO_ANNOUNCE ============= **/
                KgoAnnounce kgoAnnounce = kgoAnnounceRepository.getOne(seq);
                subject = kgoAnnounce.getName() + " 上架";
                content = kgoAnnounce.getContentHtml();
                kgoAnnounce.setIsPublish(isPublish);
                kgoAnnounce.setPublishTime(currentTime);
                kgoAnnounce.setUpdateTime(currentTime);
                kgoAnnounce.setUpdateUser(updateUser);
                kgoAnnounceRepository.save(kgoAnnounce);
            } else {
                throw new KgoApiException(new ErrorResult(KgoFrontEndApiError.RQ_NOT_COMPLETED));
            }

            if (isPublish) {
                List<GeoKgoCityMember> members = geoCityMemberRepos.findGeoKgoCityMemberWithRealName();
                LOGGER.info("members");
                LOGGER.info(JSON.toJSONString(members));
                List<List<GeoKgoCityMember>> membersSubList = Lists.partition(new ArrayList<>(members), 1000);
                for (List<GeoKgoCityMember> memberList : membersSubList) {
                    LOGGER.info(JSON.toJSONString(memberList));
                    List<String> uuids = memberList.stream().map(e -> e.getId().toLowerCase()).collect(Collectors.toList());
                    LOGGER.info(JSON.toJSONString(uuids));
                    LOGGER.info("UUIDS");
                    LOGGER.info(String.join(",", uuids));
                    List<PushMsgDto> pushDataList = new ArrayList<>();
                    PushMsgDto pushMsg = new PushMsgDto();
                    pushMsg.setAccount(String.join(",", uuids));
                    pushMsg.setMsgTitle(subject);
                    pushMsg.setMsgBody(content);
                    pushMsg.setClickDetail(content);
                    pushDataList.add(pushMsg);
                    pushService.pushMessage(pushDataList, "397220100A");
                }
            }
            viewForm.setMsg(msg);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(">>>>>>>>" + e.getMessage(), e);
            throw new KgoApiException("taskMaintainOnOff error " + e.getMessage(), e);
        }
        return rs;
    }

    /**
     * 任務及公告管理-任務刪除
     *
     * @param rq
     * @return
     */
    @Override
    public TaskMaintainDeleteRs taskMaintainDelete(TaskMaintainDeleteRq rq) {
        TaskMaintainDeleteRs rs = new TaskMaintainDeleteRs();
        TaskMaintainDeleteViewForm viewForm = new TaskMaintainDeleteViewForm();
        KgoApiException error = null;
        OperationApiMemo memo = null;
        String taskOrAnnounce = null;
        String title = null;
        try {
            Integer seq = rq.getSeq();
            BackendFunctionCodeEnum backendFunctionCodeEnum = BackendFunctionCodeEnum
                    .getBackendFunctionEnum(rq.getFunctionCode());
            ReleaseToEnum releaseToEnum = ReleaseToEnum.getReleaseToEnum(rq.getReleaseObject());
            memo = super.genOperationMemo(SystemTypeEnum.B, SysLogExeType.TYPE_D, backendFunctionCodeEnum);

            // 後台、刪除、任務或公告
            if (backendFunctionCodeEnum == BackendFunctionCodeEnum.TaskM) {
                taskOrAnnounce = "任務";
                if (releaseToEnum != ReleaseToEnum.FRONT) {
                    throw new KgoApiException(new ErrorResult(KgoFrontEndApiError.RQ_NOT_COMPLETED));
                }
                title = kgoTaskSetRepository.findById(rq.getSeq()).get().getActivityName();

                /** =========== KGO_TASK_SET ============= **/
                kgoTaskSetRepository.deleteById(seq);
                /** =========== KGO_CASESET_TASK ============= **/
                kgoCasesetTaskRepository.deleteByActivitySeq(seq);
            } else if (backendFunctionCodeEnum == BackendFunctionCodeEnum.AnnounceM) {
                taskOrAnnounce = "公告";
                title = kgoAnnounceRepository.findById(rq.getSeq()).get().getName();

                /** =========== KGO_ANNOUNCE ============= **/
                kgoAnnounceRepository.deleteById(seq);
            } else {
                throw new KgoApiException(new ErrorResult(KgoFrontEndApiError.RQ_NOT_COMPLETED));
            }

            /** =========== KGO_ATT_FILE ============= **/
            AttFileEnum attFileEnum = AttFileEnum.getEnum(backendFunctionCodeEnum);
            kgoAttFileRepository.updateStatus("D", KgoUtil.getLoginUserId(), DateUtil.getCurrentTimestamp(), seq,
                    attFileEnum.getValue());

            viewForm.setMsg(SuccessMsg.DELETE.getMsg());
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(">>>>>>>>" + e.getMessage(), e);
            error = new KgoApiException("taskMaintainDelete error " + e.getMessage(), e);
        } finally {
            /** === 儲存操作紀錄 === */

            List<OperationColumn> memoList = new ArrayList<>();
            memoList.add(new OperationColumn(taskOrAnnounce, title));
            memo.setMemoList(memoList);
            super.saveOperationLog(memo);
            /** === 儲存操作紀錄 === */

            if (error != null) {
                throw error;
            }
        }
        return rs;
    }

    /**
     * 任務及公告管理– 機關帶出案件
     *
     * @param rq
     * @return
     */
    @Override
    public TaskMaintainOrganCaseRs taskMaintainOrganCase(TaskMaintainOrganCaseRq rq) {
        TaskMaintainOrganCaseRs rs = new TaskMaintainOrganCaseRs();
        TaskMaintainOrganCaseViewForm viewForm = new TaskMaintainOrganCaseViewForm();
        try {
            String organId = rq.getOrganId();
            ComboBox caseNameOptions = getCaseSetComboBoxByOrganId(organId, new ArrayList<String>());
            // 活動項目
//			ComboBox activityItemOptions = getActivityItemComboBox(organId, null);

            viewForm.setCaseNameOptions(caseNameOptions);
//			viewForm.setActivityItemOptions(activityItemOptions);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(">>>>>>>>" + e.getMessage(), e);
            throw new KgoApiException("taskMaintainOrganCase error " + e.getMessage(), e);
        }
        return rs;
    }

    /**
     * 任務及公告管理– 城市幣任務查詢
     *
     * @param rq
     * @return
     */
    @Override
    public TaskMaintainCityCoinSearchRs taskMaintainCityCoinSearch(TaskMaintainCityCoinSearchRq rq) {
        TaskMaintainCityCoinSearchRs rs = new TaskMaintainCityCoinSearchRs();
        TaskMaintainCityCoinSearchViewForm viewForm = new TaskMaintainCityCoinSearchViewForm();
        try {
            if (ObjectUtils.isEmpty(rq.getTaskSeq())) {

            }

            String taskSeq = rq.getTaskSeq();

            /** 城市幣API呼叫 **/
            SearchViewForm searchViewForm = getCityCoinSearch(taskSeq);
            // 城市幣總點數
            String totalPoint = searchViewForm.getTotalAmount() == null ? null
                    : searchViewForm.getTotalAmount().toString();
            // 每次核發點數
            String point = searchViewForm.getRewardAmount() == null ? null
                    : searchViewForm.getRewardAmount().toString();

            viewForm.setPoint(point);
            viewForm.setTotalPoint(totalPoint);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(">>>>>>>>" + e.getMessage(), e);
            throw new KgoApiException("taskMaintainCityCoinSearch error " + e.getMessage(), e);
        }
        return rs;
    }

    @Override
    public void taskMaintainDownload(TaskMaintainDownloadRq rq) {
        try {
            Integer seq = rq.getSeq();
            BackendFunctionCodeEnum backendFunctionCodeEnum = BackendFunctionCodeEnum
                    .getBackendFunctionEnum(rq.getFunctionCode());
            ReleaseToEnum releaseToEnum = ReleaseToEnum.getReleaseToEnum(rq.getReleaseObject());
            if (ObjectUtils.isEmpty(seq) || ObjectUtils.isEmpty(backendFunctionCodeEnum)
                    || ObjectUtils.isEmpty(releaseToEnum) || StringUtils.isBlank(rq.getFileKey())) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_DOWNLOAD));
            }
            // 案件附件 檔案上傳路徑
            String folderPath = KgoUtil.getFunctionCodeDownloadUploadBasePath(backendFunctionCodeEnum, seq);
            Optional<KgoAttFile> kgoAttFile = kgoAttFileRepository.findById(rq.getFileKey());
            if (kgoAttFile.isPresent() && kgoAttFile.get().getStatus().equalsIgnoreCase("S")) {

                File file = new File(folderPath + kgoAttFile.get().getFileName());

                if (!file.exists()) {
                    throw new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_DOWNLOAD));
                } else {
                    // 檔案下載
                    commonService.downloadFileAction(file);
                }
            } else {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_DOWNLOAD));
            }
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            ErrorResult errorResult = new ErrorResult(KgoBackEndApiError.FAIL_TO_DOWNLOAD);
            LOGGER.error("caseHandleCaseViewScaCaseDownload: {}", errorResult.getErrorDesc());
            throw new KgoApiException(errorResult);
        }
    }

    /**
     * 儲存附件
     *
     * @param fSeq
     * @param attFileEnum
     * @param filse
     * @return
     */
    private List<TaskMaintainEditHomeFileViewForm> saveKgoAttFile(Integer fSeq, AttFileEnum attFileEnum,
                                                                  List<TaskMaintainEditHomeFileViewForm> filse) {
        List<KgoAttFile> kgoAttFiles = this.kgoAttFileRepository.findByfSeqAndTypeAndStatus(fSeq,
                attFileEnum.getValue(), "S");

        // 先設定刪除
        for (KgoAttFile kgoAttFile : kgoAttFiles) {
            kgoAttFile.setStatus("D");
        }

        for (TaskMaintainEditHomeFileViewForm item : filse) {
            KgoAttFile kgoAttFile = null;
            if (StringUtils.isNoneBlank(item.getKey())) {
                Optional<KgoAttFile> okgoAttFile = kgoAttFiles.stream()
                        .filter(x -> x.getGuid().equalsIgnoreCase(item.getKey())).findAny();
                if (okgoAttFile.isPresent()) {
                    kgoAttFile = okgoAttFile.get();
                }
            }

            if (ObjectUtils.isEmpty(kgoAttFile)) {
                kgoAttFile = new KgoAttFile();
                kgoAttFiles.add(kgoAttFile);
                kgoAttFile.setCreateUser(KgoUtil.getLoginUserId());
                kgoAttFile.setCreateTime(DateUtil.getCurrentTimestamp());
            } else {
                kgoAttFile.setUpdateUser(KgoUtil.getLoginUserId());
                kgoAttFile.setUpdateTime(DateUtil.getCurrentTimestamp());
            }

            kgoAttFile.setFileName(item.getName());
            kgoAttFile.setfSeq(fSeq);
            kgoAttFile.setStatus("S");
            kgoAttFile.setType(attFileEnum.getValue());

            saveUploadFile(fSeq, attFileEnum, item);

        }

        kgoAttFileRepository.saveAllBatch(kgoAttFiles);

        return kgoAttFiles.stream().filter(x -> x.getStatus().equalsIgnoreCase("S"))
                .map(x -> new TaskMaintainEditHomeFileViewForm(x.getGuid(), x.getFileName()))
                .collect(Collectors.toList());
    }

    private void saveUploadFile(Integer fSeq, AttFileEnum attFileEnum, TaskMaintainEditHomeFileViewForm item) {
        if (StringUtils.isNoneBlank(item.getBase64Str())) {
            try {
                String base64Str = item.getBase64Str();

                String[] dataString = item.getBase64Str().split(",");
                if (dataString.length > 1) {
                    base64Str = dataString[1];
                }
                byte[] decoder = Base64.getDecoder().decode(base64Str);
                File fileFolder = new File(
                        KgoUtil.getFunctionCodeDownloadUploadBasePath(attFileEnum.getFunctionCode(), fSeq));
                FileUtil.createFile(fileFolder, item.getName(), decoder);

            } catch (Exception e) {
                LOGGER.error("\n >>>>>>>saveUploadFile>>> " + e.getMessage(), e);
                LOGGER.error("\n file:  " + JsonUtil.toJSONString(item), e);
            }
        }
    }

    /**
     * Query KGO_TASK_SET & KGO_ANNOUNCE without conditions
     *
     * @return
     */
    private List<TaskMaintainQueryDataGrid> queryTaskAndAnnounce() {
        return queryTaskAndAnnounce(StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
    }

    /**
     * Query KGO_TASK_SET & KGO_ANNOUNCE data By conditions
     *
     * @param name
     * @param type
     * @param publishTimeStart
     * @param publishTimeEnd
     * @return
     */
    private List<TaskMaintainQueryDataGrid> queryTaskAndAnnounce(String name, String type, String publishTimeStart,
                                                                 String publishTimeEnd) {
        List<TaskMaintainQueryDataGrid> gridList = new LinkedList<TaskMaintainQueryDataGrid>();
        try {
            List<TaskAndAnnounceQueryDto> list = kgoTaskSetRepository.findTaskAndAnnounceData(name, type,
                    publishTimeStart, publishTimeEnd);
            gridList = list.stream().map(l -> {
                TaskMaintainQueryDataGrid grid = new TaskMaintainQueryDataGrid();
                try {
                    String titleName = l.getName();
                    String publishTime = StringUtils.isBlank(l.getPublishTime()) ? StringUtils.EMPTY
                            : DateUtil.strDateFormat(l.getPublishTime(), DateUtil.PATTEN_FULL_TIME_MS,
                            DateUtil.PATTEN_FULL_TIME_SLASH);

                    int seq = l.getId().getSeq();
                    grid.setSeq(seq);

                    grid.setTitleName(titleName);
                    grid.setPublishTime(publishTime);

                    String releaseObjectName = StringUtils.EMPTY;
                    ReleaseToEnum releaseToEnum = ReleaseToEnum.getReleaseToEnum(l.getId().getType());
                    if (ObjectUtils.isNotEmpty(releaseToEnum)) {
                        releaseObjectName = releaseToEnum.getLabel();
                    }
                    grid.setReleaseObject(l.getId().getType());
                    grid.setReleaseObjectName(releaseObjectName);

                    String functionCodeName = StringUtils.EMPTY;
                    BackendFunctionCodeEnum backendFunctionCodeEnum = BackendFunctionCodeEnum
                            .getBackendFunctionEnum(l.getId().getFunCode());
                    if (ObjectUtils.isNotEmpty(backendFunctionCodeEnum)) {
                        functionCodeName = messageUtil.getMessage(backendFunctionCodeEnum.getFunctionNameI18n());
                    }
                    grid.setFunctionCode(l.getId().getFunCode());
                    grid.setFunctionCodeName(functionCodeName);

                    String state = StringUtils.EMPTY;
                    IsPublishEnum isPublishEnum = IsPublishEnum.getIsPublishEnum(l.getIsPublish());
                    if (ObjectUtils.isNotEmpty(isPublishEnum)) {
                        state = isPublishEnum.getLabel();
                    }
                    grid.setState(state);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return grid;
            }).collect(Collectors.toList());
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey(), e);
            throw new KgoApiException("queryTaskAndAnnounce error " + e.getMessage(), e);
        }
        return gridList;
    }

    /**
     * 取得服務案件下拉式選單
     *
     * @param organId
     * @return
     */
    private ComboBox getCaseSetComboBoxByOrganId(String organId, List<String> caseSetIds) {
        List<KgoCaseset> kgoCasesetList = kgoCasesetRepository.findByOwnerOrganAndStatus(organId,
                PublishStateEnum.ON.getValue());
        ComboBox comboBox = new ComboBox();
        for (KgoCaseset kgoCaseset : kgoCasesetList) {

            boolean selected = caseSetIds.contains(kgoCaseset.getCaseSetId());
            comboBox.add(kgoCaseset.getCaseSetName(), kgoCaseset.getCaseSetId(), selected);
        }

        return comboBox;
    }

    private SearchViewForm getCityCoinSearch(String taskSeq) {

        try {
            SearchRs searchRs = cityCoinAPIService.search(NumberUtils.toInt(taskSeq));
            if (searchRs.getRtnCode().equals("0000")) {
                return searchRs.getResult();
            } else {
                LOGGER.info(">>>>>>>> getCityCoinSearch: " + JsonUtil.toJSONString(searchRs));
            }
        } catch (Exception e) {
            LOGGER.error(">>>>>>>> getCityCoinSearch: " + e.getMessage(), e);
        }

        return new SearchViewForm();
    }

}
