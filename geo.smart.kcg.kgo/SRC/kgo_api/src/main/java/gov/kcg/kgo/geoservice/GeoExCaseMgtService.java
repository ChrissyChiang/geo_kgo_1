package gov.kcg.kgo.geoservice;

import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.enums.backend.*;
import gov.kcg.kgo.enums.common.SuccessMsg;
import gov.kcg.kgo.enums.common.SysLogExeType;
import gov.kcg.kgo.enums.common.SystemTypeEnum;
import gov.kcg.kgo.enums.common.sso.LoginAuthTokenType;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.geomodel.dto.GeoExCaseQueryDto;
import gov.kcg.kgo.georepository.custom.GeoCaseSetReposCustom;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoExCaseSaveRq;
import gov.kcg.kgo.geoviewmodel.backend.rs.GeoExCaseMgtEditHomeRs;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoExCaseMgtEditHomeViewForm;
import gov.kcg.kgo.model.*;
import gov.kcg.kgo.repository.*;
import gov.kcg.kgo.service.impl.helper.*;
import gov.kcg.kgo.service.operationmemo.OperationApiMemo;
import gov.kcg.kgo.service.operationmemo.OperationColumn;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.viewModel.backend.caseManagement.caseSave.rs.CaseManagementCaseSaveRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.caseSave.rs.bean.CaseManagementCaseSaveViewForm;
import gov.kcg.kgo.viewModel.backend.caseManagement.delete.rq.CaseManagementDeleteRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.delete.rs.CaseManagementDeleteRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.delete.rs.bean.CaseManagementDeleteViewForm;
import gov.kcg.kgo.viewModel.backend.caseManagement.editHome.rq.CaseManagementEditHomeRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.home.rs.CaseManagementHomeRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.home.rs.bean.CaseManagementHomeViewForm;
import gov.kcg.kgo.viewModel.backend.caseManagement.query.rq.CaseManagementQueryRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.query.rs.CaseManagementQueryRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.query.rs.bean.CaseManagementQueryDataGrid;
import gov.kcg.kgo.viewModel.backend.caseManagement.query.rs.bean.CaseManagementQueryViewForm;
import gov.kcg.kgo.viewModel.compoent.SelectListItem;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import java.util.stream.Collectors;

@Transactional(rollbackFor = Exception.class)
@Service
public class GeoExCaseMgtService extends GeoBaseService{
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoExCaseMgtService.class);

    private static final String SPLIT_SYMBOLE = ",";

    private CaseManagementServiceHelper caseManagementServiceHelper = CaseManagementServiceHelper.getInstance();

    private OrganUnitManagementServiceHelper organUnitManagementServiceHelper = OrganUnitManagementServiceHelper
            .getInstance();

    @Autowired
    private KgoCasesetRepository kgoCasesetRepository;

    @Autowired
    private KgoCasesetGroupLevelRepository kgoCasesetGroupLevelRepository;

    @Autowired
    private KgoCasesetTypeRepository kgoCasesetTypeRepository;

    @Autowired
    private GeoCaseSetReposCustom geoCaseSetReposCustom;

    /**
     * 查詢服務案件管理-初始畫面 案件清單
     *
     * @param
     * @return 僅回傳機關下拉資料
     */
    public CaseManagementHomeRs searchExCaseHome() {
        CaseManagementHomeViewForm viewForm = new CaseManagementHomeViewForm();
        CaseManagementHomeRs rs = new CaseManagementHomeRs();

        try {
            BackendLoginUserInfo loginUser = getLoginUser();

            /** 機關下拉式選單 */
            // 調整為依登入者角色，若為系統管理者則取得所有機關清單，其餘角色則取得所屬及底下機關
            ComboBox organComboBox = null;
            if (loginUser.getRoles().stream().anyMatch(new String(KgoRoleEnum.ADMIN.getValue())::equalsIgnoreCase)) {
                organComboBox = caseManagementServiceHelper.getGroupLevelOrganComboBox(StringUtils.EMPTY);
            } else {
                organComboBox = caseManagementServiceHelper.getCasesetOrganByLoginUserOrgan(loginUser.getOrgan(),
                        StringUtils.EMPTY);
            }

            /** 案件資料 **/
            List<CaseManagementQueryDataGrid> dataGridList = new ArrayList<>();

            viewForm.setOrganComboBox(organComboBox);
            viewForm.setGrid(dataGridList);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            throw new KgoApiException("search excasemgt error " + e.getMessage(), e);
        }

        return rs;
    }
    /**
     * 服務案件清單-案件查詢
     *
     */
    public CaseManagementQueryRs caseManagementQuery(CaseManagementQueryRq rq) {
        CaseManagementQueryViewForm viewForm = new CaseManagementQueryViewForm();
        CaseManagementQueryRs rs = new CaseManagementQueryRs();
        try {

            try {
                BackendLoginUserInfo backendLoginUser = getLoginUser();
                List<String> roles = backendLoginUser.getRoles();
                if (!CollectionUtils.isEmpty(roles)) {
                    if (roles.contains(KgoRoleEnum.CASE_M.getValue()) && !roles.contains(KgoRoleEnum.UNIT_M.getValue())
                            && !roles.stream().anyMatch(new String(KgoRoleEnum.ADMIN.getValue())::equalsIgnoreCase)) {
                        rq.setManager(backendLoginUser.getUserId());
                    }
                }
            } catch (Exception e) {
                LOGGER.error("search excasemgt", e);
            }

            // 案件資料
            List<GeoExCaseQueryDto> dtoList = geoCaseSetReposCustom.findAllCase(rq.getOrganId(), rq.getCaseSetName());

            List<CaseManagementQueryDataGrid> dataGridList = dtoList.stream().map( dto -> {
                CaseManagementQueryDataGrid grid = new CaseManagementQueryDataGrid();
                grid.setCaseSetId(dto.getCaseSetId());
                grid.setCaseSetName(dto.getCaseSetName());
                grid.setCaseType(dto.getCaseType());
                //前台顯示上下架文字
                PublishStateEnum status = PublishStateEnum.getPublishStateEnum(dto.getStatus());
                grid.setStatus(status.getLabel());
                grid.setOrganId(dto.getOrganId());
                grid.setOrganName(dto.getOrganName());
                grid.setOwnerOrganId(dto.getOwnerOrganId());
                grid.setOwnerOrganName(dto.getOwnerOrganName());
                return grid;
            }).collect(Collectors.toList());
            viewForm.setGrid(dataGridList);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error("Exception occurred in search excasemgt", e);
            throw new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SEARCH));
        }

        return rs;
    }


    /**
     * 服務案件清單-案件維護-儲存
     * 外部服務案件判別方式 : CaseType = null / FlowType = A / Apply_type = S
     */

    public CaseManagementCaseSaveRs caseManagementCaseSave(GeoExCaseSaveRq rq) {
        CaseManagementCaseSaveViewForm viewForm = new CaseManagementCaseSaveViewForm();
        CaseManagementCaseSaveRs rs = new CaseManagementCaseSaveRs();
        KgoApiException error = null;
        OperationApiMemo memo = null;

        try {

            // 後台、新增或儲存、案件維護
            SysLogExeType sysLogExeType = StringUtils.isBlank(rq.getCaseSetId()) ? SysLogExeType.TYPE_A
                    : SysLogExeType.TYPE_U;
            memo = super.genOperationMemo(SystemTypeEnum.B, sysLogExeType, BackendFunctionCodeEnum.CaseM);
            String loginUserId = KgoUtil.getLoginUserId(); // TODO:修改成從 session 取得

            String msg = StringUtils.isBlank(rq.getCaseSetId()) ? SuccessMsg.INSERT.getMsg()
                    : SuccessMsg.UPDATE.getMsg();
            String caseSetId = StringUtils.isBlank(rq.getCaseSetId()) ? KgoUtil.getNextCaseSetId() : rq.getCaseSetId();
            String caseSetName = rq.getCaseSetName();
            String linkUrl = rq.getLinkUrl();
            String role = rq.getRole();
            String service = rq.getService();
            String organ = rq.getOrgan();


            String nextOrder = kgoCasesetRepository.findNextOrderByOrganId(organ);
            boolean isExist = kgoCasesetRepository.existsById(caseSetId);
            /**
             * KGO_CASESET 資料儲存
             */
            KgoCaseset kgoCaseset = isExist ? kgoCasesetRepository.getOne(caseSetId) : new KgoCaseset();

            /** 清空並帶入新增所選身分別 */
            kgoCasesetGroupLevelRepository.deleteByCaseSetIdAndMainType(caseSetId, "Role");
            if (StringUtils.isNotEmpty(role)) {
                List<String> roleList = Arrays.asList(role.split(","));
                if (!CollectionUtils.isEmpty(roleList)) {
                    List<KgoCasesetGroupLevel> objects = new ArrayList<>();
                    for (String roleItem : roleList) {
                        KgoCasesetGroupLevel kgoCasesetGroupLevel = new KgoCasesetGroupLevel();
                        kgoCasesetGroupLevel.setCaseSetId(caseSetId);
                        kgoCasesetGroupLevel.setGroupLevelSeq(roleItem);
                        kgoCasesetGroupLevel.setMainType("Role");
                        objects.add(kgoCasesetGroupLevel);
                    }
                    kgoCasesetGroupLevelRepository.saveAllBatch(objects);
                }
            }
            /** 清空並帶入新增所選服務別 */
            kgoCasesetGroupLevelRepository.deleteByCaseSetIdAndMainType(caseSetId, "Service");
            if (StringUtils.isNotEmpty(service)) {
                List<String> serviceList = Arrays.asList(service.split(","));
                if (!CollectionUtils.isEmpty(serviceList)) {
                    List<KgoCasesetGroupLevel> objects = new ArrayList<>();
                    for (String roleItem : serviceList) {
                        KgoCasesetGroupLevel kgoCasesetGroupLevel = new KgoCasesetGroupLevel();
                        kgoCasesetGroupLevel.setCaseSetId(caseSetId);
                        kgoCasesetGroupLevel.setGroupLevelSeq(roleItem);
                        kgoCasesetGroupLevel.setMainType("Service");
                        objects.add(kgoCasesetGroupLevel);
                    }
                    kgoCasesetGroupLevelRepository.saveAllBatch(objects);
                }
            }

            kgoCaseset.setCaseSetId(caseSetId);
            kgoCaseset.setCaseSetName(caseSetName);
            kgoCaseset.setFlowId("");
            kgoCaseset.setCaseType(null);
            kgoCaseset.setCaseFlowType(CaseFlowTypeEnum.A.getValue());
            kgoCaseset.setLinkUrl(linkUrl);
            kgoCaseset.setRole(role);
            kgoCaseset.setService(service);
            kgoCaseset.setOrgan(organ);
            kgoCaseset.setMail(StringUtils.EMPTY);
            kgoCaseset.setSort(StringUtils.isBlank(nextOrder) ? 1 : Integer.valueOf(nextOrder));
            kgoCaseset.setUpdateTime(DateUtil.getCurrentTimestamp());
            kgoCaseset.setUpdateUser(loginUserId);
            //保留原本上下架狀態
            if (isExist){
                kgoCaseset.setStatus(kgoCaseset.getStatus());
            }else {
                //新建預設下架
                kgoCaseset.setStatus(PublishStateEnum.OFF.getValue());
            }
            /** 20221015 查詢服務 服務類別: excase */
            kgoCaseset.setCasesetCategory("excase");
            kgoCaseset.setIsOpenForOrgan(0);
            if (StringUtils.isBlank(rq.getCaseSetId())) {
                kgoCaseset.setCreateTime(DateUtil.getCurrentTimestamp());
                kgoCaseset.setCreateUser(loginUserId);
            }
            kgoCasesetRepository.save(kgoCaseset);
            //查詢服務管理 - 熱門案件搜尋 type = X
            kgoCasesetTypeRepository.deleteByIdCaseSetId(caseSetId);
            KgoCasesetType kgoCasesetType = new KgoCasesetType();
            KgoCasesetTypePK KgoCasesetTypePK = new KgoCasesetTypePK();
            KgoCasesetTypePK.setApplyType("X");
            KgoCasesetTypePK.setCaseSetId(caseSetId);
            kgoCasesetType.setId(KgoCasesetTypePK);
            kgoCasesetTypeRepository.save(kgoCasesetType);

            viewForm.setCaseSetId(caseSetId);
            viewForm.setMsg(msg);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SAVE.getErrorMsgKey());
            error = new KgoApiException("search excasemgt error " + e.getMessage(), e);
        } finally {
            /** === 儲存操作紀錄 === */
            List<OperationColumn> memoList = new ArrayList<>();
            memoList.add(new OperationColumn("案件編號", rs.getData().getCaseSetId()));
            memo.setMemoList(memoList);
            super.saveOperationLog(memo);
            /** === 儲存操作紀錄 === */

            if (error != null) {
                throw error;
            }
        }

        return rs;
    }

    @Transactional
    public CaseManagementDeleteRs caseManagementDelete(CaseManagementDeleteRq rq) {
        CaseManagementDeleteViewForm viewForm = new CaseManagementDeleteViewForm();
        CaseManagementDeleteRs rs = new CaseManagementDeleteRs();
        KgoApiException error = null;
        OperationApiMemo memo = null;

        try {
            // 後台、刪除、SA
            memo = super.genOperationMemo(SystemTypeEnum.B, SysLogExeType.TYPE_D, BackendFunctionCodeEnum.SA);

            String caseSetId = rq.getCaseSetId();



            // 服務啟動清單刪除, delete from KGO_CASESET_TYPE where CaseSetId =(刪除CaseSetId);
            kgoCasesetTypeRepository.deleteByIdCaseSetId(caseSetId);

            LOGGER.info("caseSetId="+caseSetId);

            // 主檔資料刪除, delete from KGO_CASESET where CaseSetId =(刪除CaseSetId);
            kgoCasesetRepository.deleteById(caseSetId);
            viewForm.setMsg(SuccessMsg.DELETE.getMsg());
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_DELETE.getErrorMsgKey());
            error = new KgoApiException("search excasemgt error " + e.getMessage(), e);
        } finally {
            /** === 儲存操作紀錄 === */
            List<OperationColumn> memoList = new ArrayList<>();
            memoList.add(new OperationColumn("案件編號", rq.getCaseSetId()));
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
     * 查詢服務管理-案件新增/編輯畫面
     */
    public GeoExCaseMgtEditHomeRs exCaseMgtEditHome(CaseManagementEditHomeRq rq) {
        GeoExCaseMgtEditHomeViewForm viewForm = new GeoExCaseMgtEditHomeViewForm();
        GeoExCaseMgtEditHomeRs rs = new GeoExCaseMgtEditHomeRs();

        BackendLoginUserInfo backendLoginUser = getLoginUser();
        try {
            String caseSetId = rq.getCaseSetId();
            String caseSetName = StringUtils.EMPTY;
            String linkUrl = null;
            BackendLoginUserInfo beUser = KgoUtil.getBackendLoginUser();
            ComboBox organComboBox = null;
            if (backendLoginUser.getRoles().stream().anyMatch(new String(KgoRoleEnum.ADMIN.getValue())::equalsIgnoreCase)) {
                organComboBox = caseManagementServiceHelper.getGroupLevelOrganComboBox(StringUtils.EMPTY);
            } else {
                organComboBox = caseManagementServiceHelper.getOrganByLoginUserOrgan(backendLoginUser.getOrgan(),
                        StringUtils.EMPTY);
            }
            ComboBox roleComboBox = caseManagementServiceHelper.getGroupLevelRoleComboBox(StringUtils.EMPTY);
            if (StringUtils.isNotEmpty(rq.getCaseSetId())) {
                List<KgoCasesetGroupLevel> role = kgoCasesetGroupLevelRepository
                        .findByCaseSetIdAndMainType(rq.getCaseSetId(), "Role");
                for (SelectListItem option : roleComboBox.getOptions()) {
                    KgoCasesetGroupLevel kgoCasesetGroupLevel = role.stream()
                            .filter(item -> item.getGroupLevelSeq().equals(option.getValue())).findAny().orElse(null);
                    if (null != kgoCasesetGroupLevel) {
                        option.setSelected(true);
                    }
                }
            }
            ComboBox serviceComboBox = caseManagementServiceHelper.getGroupLevelServiceComboBox(StringUtils.EMPTY);
            if (StringUtils.isNotEmpty(rq.getCaseSetId())) {
                List<KgoCasesetGroupLevel> service = kgoCasesetGroupLevelRepository
                        .findByCaseSetIdAndMainType(rq.getCaseSetId(), "Service");
                for (SelectListItem option : serviceComboBox.getOptions()) {
                    KgoCasesetGroupLevel kgoCasesetGroupLevel = service.stream()
                            .filter(item -> item.getGroupLevelSeq().equals(option.getValue())).findAny().orElse(null);
                    if (null != kgoCasesetGroupLevel) {
                        option.setSelected(true);
                    }
                }
            }

            if (StringUtils.isNotBlank(caseSetId)) {
                // 取得 KgoCaseset 資料
                KgoCaseset kgoCaseset = kgoCasesetRepository.getOne(caseSetId);
                caseSetName = kgoCaseset.getCaseSetName();
                organComboBox.setSelectedVal(kgoCaseset.getOrgan());
                roleComboBox.setSelectedVal(kgoCaseset.getRole());
                serviceComboBox.setSelectedVal(kgoCaseset.getService());
                linkUrl = kgoCaseset.getLinkUrl();
            }

            viewForm.setCaseSetId(caseSetId); // 服務案件編號
            viewForm.setCaseSetName(caseSetName); // 案件名稱
            viewForm.setOrganComboBox(organComboBox); // 機關分類
            viewForm.setRoleComboBox(roleComboBox); // 角色分類
            viewForm.setServiceComboBox(serviceComboBox); // 服務分類
            viewForm.setLinkUrl(linkUrl);//外部連結
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            throw new KgoApiException("caseManagementEditHome error " + e.getMessage(), e);
        }
        return rs;
    }

    private BackendLoginUserInfo getLoginUser(){
        BackendLoginUserInfo loginUser = KgoUtil.getBackendLoginUser();
//        if(KgoServiceHelper.getInstance().isDevMode()){
//            loginUser = new BackendLoginUserInfo();
//            loginUser.setUserId("chance");
//            loginUser.setOrgan("397140100P");
//            loginUser.setName("朱科安");
//            loginUser.setUnit("A890");
//            List<String> roles = new ArrayList<>();
//            roles.add("ADMIN");
//            roles.add("CASE_M");
//            roles.add("UNIT_A");
//            roles.add("UNIT_M");
//            roles.add("UNIT_U");
//            loginUser.setRoles(roles);
//            loginUser.setJwtToken("");
//            loginUser.setLoginAuthTokenType(LoginAuthTokenType.BASIC);
//            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
//            Date date = new Date();
//            sdFormat.format(date);
//            loginUser.setLoginTime(date);
//        }
        return loginUser;
    }
}

