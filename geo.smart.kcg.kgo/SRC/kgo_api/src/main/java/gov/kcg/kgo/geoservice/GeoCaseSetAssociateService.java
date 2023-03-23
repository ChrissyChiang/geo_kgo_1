package gov.kcg.kgo.geoservice;

import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.dto.CaseManagementQueryDto;
import gov.kcg.kgo.enums.backend.KgoRoleEnum;
import gov.kcg.kgo.enums.common.SuccessMsg;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.enums.error.KgoFrontEndApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.geoentity.GeoKgoCasesetAssociate;
import gov.kcg.kgo.geoentity.GeoKgoCasesetAssociatePK;
import gov.kcg.kgo.geomodel.GeoKgoCasesetAssociateModel;
import gov.kcg.kgo.georepository.GeoKgoCasesetAssociateRepository;
import gov.kcg.kgo.georepository.custom.GeoBaseReposCustom;
import gov.kcg.kgo.georepository.custom.GeoCaseSetAssociateReposCustom;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoCaseSetAssociateDeleteRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoCaseSetAssociateInsertRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoCaseSetAssociateListQueryRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoCaseSetAssociateQueryRq;
import gov.kcg.kgo.geoviewmodel.backend.rs.GeoCaseSetAssociateDeleteRs;
import gov.kcg.kgo.geoviewmodel.backend.rs.GeoCaseSetAssociateQueryActionRs;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoCaseSetAssociateDeleteViewForm;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoCaseSetAssociateQueryViewForm;
import gov.kcg.kgo.model.KgoCaseMain;
import gov.kcg.kgo.model.TpiFlow;
import gov.kcg.kgo.repository.KgoCaseMainRepository;
import gov.kcg.kgo.repository.TpiFlowRepository;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.viewModel.backend.caseManagement.query.rs.bean.CaseManagementQueryDataGrid;
import org.apache.commons.collections.list.AbstractLinkedList;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

/**
 * GEO 20211019 add
 * 後台-案件關聯服務 API Service.
 */
@Repository
public class GeoCaseSetAssociateService extends GeoBaseReposCustom {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoCaseSetAssociateService.class);

    @Autowired
    GeoCaseSetAssociateReposCustom geoCaseSetAssociateReposCustom;

    @Autowired
    private KgoCaseMainRepository kgoCaseMainRepository;

    @Autowired
    private TpiFlowRepository tpiFlowRepository;

    @Autowired
    GeoKgoCasesetAssociateRepository geoKgoCasesetAssociateRepository;


    /**
     * 服務案件清單-案件查詢
     */
    public GeoCaseSetAssociateQueryActionRs caseSetQuery(GeoCaseSetAssociateQueryRq rq) {
        GeoCaseSetAssociateQueryViewForm viewForm = new GeoCaseSetAssociateQueryViewForm();
        GeoCaseSetAssociateQueryActionRs rs = new GeoCaseSetAssociateQueryActionRs();
        try {
            try {
                BackendLoginUserInfo backendLoginUser = KgoUtil.getBackendLoginUser();
                List<String> roles = backendLoginUser.getRoles();
                if (!CollectionUtils.isEmpty(roles)) {
                    if (roles.contains(KgoRoleEnum.CASE_M.getValue()) && !roles.contains(KgoRoleEnum.UNIT_M.getValue())
                            && !roles.stream().anyMatch(new String(KgoRoleEnum.ADMIN.getValue())::equalsIgnoreCase)) {
                        rq.setManager(backendLoginUser.getUserId());
                    }
                }
            } catch (Exception e) {
                LOGGER.error("caseManagementQuery", e);
            } //try
            // 案件資料
            List<CaseManagementQueryDto> dtoList = geoCaseSetAssociateReposCustom.findAllCaseSet(rq.getOrganId(),
                    rq.getOwnerOrganId(), rq.getCaseSetId(), rq.getCaseSetName(), rq.getManager(),
                    rq.getThisCaseSetId(), geoCaseSetAssociateReposCustom.checkIsOpenForOrgan(rq.getThisCaseSetId()));
            List<CaseManagementQueryDataGrid> dataGridList = findCaseSetAssociateList(dtoList);
            if (dataGridList != null && dataGridList.size() > 0) {
                for (CaseManagementQueryDataGrid grid : dataGridList) {
                    List<GeoKgoCasesetAssociateModel> list = geoCaseSetAssociateReposCustom.findAllCaseAssociateByPK(rq.getThisCaseSetId(), grid.getCaseSetId());
//                    LOGGER.info("associateCaseSetQuery :" + ((list.size() > 0) ? list.get(0).getAssociateCasesetId() : null) + " / " + grid.getCaseSetId());
                    grid.setSelected((list.size() > 0));
                } //for (CaseManagementQueryDataGri
            } //if (dataGridList...
            viewForm.setGrid(dataGridList);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error("Exception occurred in caseSetQuery", e);
            throw new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SEARCH));
        } //try
        return rs;
    } //associateCaseSetQuery

    /**
     * 後台-案件關聯服務:取得已關聯案件清單
     */
    public GeoCaseSetAssociateQueryActionRs associateCaseSetQuery(GeoCaseSetAssociateListQueryRq rq) {
        GeoCaseSetAssociateQueryViewForm viewForm = new GeoCaseSetAssociateQueryViewForm();
        GeoCaseSetAssociateQueryActionRs rs = new GeoCaseSetAssociateQueryActionRs();
        try {
            List<CaseManagementQueryDto> dtoList = geoCaseSetAssociateReposCustom.findAllAssociateCaseSetByCaseSetId(rq.getCaseSetId());
            List<CaseManagementQueryDataGrid> dataGridList = findCaseSetAssociateList(dtoList);
            viewForm.setGrid(dataGridList);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error("Exception occurred in associateCaseSetQuery", e);
            throw new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SEARCH));
        } //try
        return rs;
    } //associateCaseSetQuery

    /**
     * 後台-案件關聯服務:新增關聯案件
     */
    public GeoCaseSetAssociateQueryActionRs insertAssociateCaseSett(GeoCaseSetAssociateInsertRq rq) {
        GeoCaseSetAssociateQueryViewForm viewForm = new GeoCaseSetAssociateQueryViewForm();
        GeoCaseSetAssociateQueryActionRs rs = new GeoCaseSetAssociateQueryActionRs();
        try {
            List<GeoKgoCasesetAssociateModel> modelList = rq.getDataList();
            List<GeoKgoCasesetAssociate> entityList = new ArrayList<>();
            for (GeoKgoCasesetAssociateModel model: modelList) {
                GeoKgoCasesetAssociate entity = new GeoKgoCasesetAssociate();
                GeoKgoCasesetAssociatePK pk = new GeoKgoCasesetAssociatePK(model.getCasesetId(),model.getAssociateCasesetId());
                entity.setId(pk);
                entityList.add(entity);
            } //for (GeoKgoCasesetAssociateModel
            geoKgoCasesetAssociateRepository.saveAll(entityList);
            List<CaseManagementQueryDto> dtoList = geoCaseSetAssociateReposCustom.findAllAssociateCaseSetByCaseSetId(rq.getDataList().get(0).getCasesetId());
            List<CaseManagementQueryDataGrid> dataGridList = findCaseSetAssociateList(dtoList);
            viewForm.setGrid(dataGridList);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error("Exception occurred in insertAssociateCaseSett", e);
            throw new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SAVE));
        } //try
        return rs;
    } //associateCaseSetQuery

    /**
     * 刪除關聯案件
     */
    public GeoCaseSetAssociateDeleteRs deleteAssociate(GeoCaseSetAssociateDeleteRq rq) {
        GeoCaseSetAssociateDeleteViewForm viewForm = new GeoCaseSetAssociateDeleteViewForm();
        GeoCaseSetAssociateDeleteRs rs = new GeoCaseSetAssociateDeleteRs();
        try {
            GeoKgoCasesetAssociatePK pk = new GeoKgoCasesetAssociatePK(rq.getCasesetId(),rq.getAssociateCasesetId());
            GeoKgoCasesetAssociate entity = geoKgoCasesetAssociateRepository.findGeoKgoCasesetAssociateById(pk);
            if (entity == null) throw new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_DELETE));
            geoKgoCasesetAssociateRepository.delete(entity);
            List<CaseManagementQueryDto> dtoList = geoCaseSetAssociateReposCustom.findAllAssociateCaseSetByCaseSetId(rq.getCasesetId());
            List<CaseManagementQueryDataGrid> dataGridList = findCaseSetAssociateList(dtoList);
            viewForm.setGrid(dataGridList);
            viewForm.setMsg(SuccessMsg.DELETE.getMsg());
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error("Exception occurred in deleteAssociate", e);
            throw new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_DELETE));
        } //try
        return rs;
    } //deleteAssociate

    private  List<CaseManagementQueryDataGrid> findCaseSetAssociateList(List<CaseManagementQueryDto> dtoList) {
        List<String> caseSetIds = dtoList.stream().map(CaseManagementQueryDto::getCaseSetId).collect(toList());
        List<KgoCaseMain> caseMainList = kgoCaseMainRepository.findByCaseSetIdIn(caseSetIds);
        Map<String, ArrayList<KgoCaseMain>> KgoCaseMainGroupByCaseSetId = caseMainList.stream()
                .collect(Collectors.groupingBy(KgoCaseMain::getCaseSetId, toCollection(ArrayList::new)));
        List<CaseManagementQueryDataGrid> dataGridList = transferQueryDataToDataGrid(dtoList, KgoCaseMainGroupByCaseSetId);
        return dataGridList;
    } //findCaseSetAssociateList

    /**
     * transform data from CaseManagementQueryDto to CaseManagementQueryDataGrid
     *
     * @param dtoList
     * @return
     */
    private List<CaseManagementQueryDataGrid> transferQueryDataToDataGrid(List<CaseManagementQueryDto> dtoList, Map<String, ArrayList<KgoCaseMain>> kgoCaseMain) {
        Map<String, TpiFlow> map = null;
        List<String> flowIds = dtoList.stream().filter(d -> StringUtils.isNotBlank(d.getFlowId()))
                .map(CaseManagementQueryDto::getFlowId).collect(toList());
        List<TpiFlow> tpiFlows = tpiFlowRepository.findAllById(flowIds);
        map = tpiFlows.stream().collect(Collectors.toMap(c -> c.getFlowId(), c -> c));
        Map<String, TpiFlow> finalMap = map;
        return dtoList.stream().map(l -> {
            CaseManagementQueryDataGrid grid = new CaseManagementQueryDataGrid();
            grid.setCaseSetId(l.getCaseSetId());
            grid.setCaseSetName(l.getCaseSetName());
            grid.setCaseType(l.getCaseType());
            grid.setManagerName(l.getManagerName());
            grid.setServiceTo(l.getServiceTo());
            grid.setStatus(l.getStatus());
            grid.setOrganId(l.getOrganId());
            grid.setOrganName(l.getOrganName());
            grid.setOwnerOrganId(l.getOwnerOrganId());
            grid.setOwnerOrganName(l.getOwnerOrganName());
            grid.setAllowDelete(
                    kgoCaseMain.get(l.getCaseSetId()) == null ? Boolean.TRUE.toString() : Boolean.FALSE.toString());
            if (!CollectionUtils.isEmpty(finalMap) && StringUtils.isNotEmpty(l.getFlowId())) {
                // flowId 取taskName
                TpiFlow tpiFlow = finalMap.get(l.getFlowId());
                if (null != tpiFlow) {
                    grid.setTaskName(tpiFlow.getFlowName());
                    grid.setFlowId(tpiFlow.getFlowId());
                }
            } // if (!CollectionUtils.isEmpty(finalMap)..
            return grid;
        }).collect(Collectors.toList());
    } //transferQueryDataToDataGrid
}
