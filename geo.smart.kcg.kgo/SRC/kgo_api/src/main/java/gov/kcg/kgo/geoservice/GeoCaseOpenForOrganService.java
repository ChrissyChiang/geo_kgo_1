package gov.kcg.kgo.geoservice;

import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.dto.CaseManagementQueryDto;
import gov.kcg.kgo.enums.backend.ApplyTypeEnum;
import gov.kcg.kgo.enums.backend.KgoRoleEnum;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.georepository.custom.GeoBaseReposCustom;
import gov.kcg.kgo.georepository.custom.GeoCaseOpenForOrganReposCustom;
import gov.kcg.kgo.geoviewmodel.backend.rs.GeoCaseOpenForOrganQueryActionRs;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoCaseOpenForOrganQueryActionViewForm;
import gov.kcg.kgo.model.*;
import gov.kcg.kgo.repository.*;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.viewModel.backend.caseManagement.query.rq.CaseManagementQueryRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.query.rs.bean.CaseManagementQueryDataGrid;
import gov.kcg.kgo.viewModel.frontend.bidCaseList.home.rs.bean.BidCaseListQueryDataGrid;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

/**
 * GEO 20211019 add
 * 後台-府內線上服務 API Service.
 */
@Repository
public class GeoCaseOpenForOrganService extends GeoBaseReposCustom {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoCaseOpenForOrganService.class);


    @Autowired
    GeoCaseOpenForOrganReposCustom geoCaseOpenForOrganReposCustom;

    @Autowired
    private KgoCaseMainRepository kgoCaseMainRepository;

    @Autowired
    private TpiFlowRepository tpiFlowRepository;

    @Autowired
    private RepositoryService repositoryService;

    private static final String DEFAULT_FONT = "新細明體";

    /**
     * 服務案件清單-案件查詢
     */
    public GeoCaseOpenForOrganQueryActionRs caseManagementQuery(CaseManagementQueryRq rq) {
        GeoCaseOpenForOrganQueryActionViewForm viewForm = new GeoCaseOpenForOrganQueryActionViewForm();
        GeoCaseOpenForOrganQueryActionRs rs = new GeoCaseOpenForOrganQueryActionRs();
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
            }

            // 案件資料
            List<CaseManagementQueryDto> dtoList = geoCaseOpenForOrganReposCustom.findAllCase(rq.getOrganId(),
                    rq.getOwnerOrganId(), rq.getCaseSetId(), rq.getCaseSetName(), rq.getManager());

            // When caseset with data in CaseMain, Not allowed user to delete caseset data,
            // add by Jay 20201207
            List<String> caseSetIds = dtoList.stream().map(CaseManagementQueryDto::getCaseSetId).collect(toList());
            List<KgoCaseMain> caseMainList = kgoCaseMainRepository.findByCaseSetIdIn(caseSetIds);
            Map<String, ArrayList<KgoCaseMain>> KgoCaseMainGroupByCaseSetId = caseMainList.stream()
                    .collect(Collectors.groupingBy(KgoCaseMain::getCaseSetId, toCollection(ArrayList::new)));

            List<CaseManagementQueryDataGrid> dataGridList = transferQueryDataToDataGrid(dtoList,
                    KgoCaseMainGroupByCaseSetId);
            ProcessDefinition pf = repositoryService.createProcessDefinitionQuery().processDefinitionKey("CaseApply")
                    .latestVersion().singleResult();
            ProcessDefinition auhtPf = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionKey("AuthApply").latestVersion().singleResult();
            String caseImage = getProcessImgStream(pf.getId());
            String authImage = getProcessImgStream(auhtPf.getId());

            List<BidCaseListQueryDataGrid> dataGrid = new ArrayList<>();
            dataGrid.addAll(queryKgoCasesetData(rq));

            viewForm.setAuthImage(authImage);
            viewForm.setCaseImage(caseImage);
            viewForm.setGrid(dataGridList);
            viewForm.setDataGrid(dataGrid);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error("Exception occurred in caseManagementQuery", e);
            throw new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SEARCH));
        }

        return rs;
    } //caseManagementQuery

    /**
     * transform data from CaseManagementQueryDto to CaseManagementQueryDataGrid
     *
     * @param dtoList
     * @return
     */
    private List<CaseManagementQueryDataGrid> transferQueryDataToDataGrid(List<CaseManagementQueryDto> dtoList,
                                                                          Map<String, ArrayList<KgoCaseMain>> kgoCaseMain) {
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
            }
            return grid;
        }).collect(Collectors.toList());
    } //transferQueryDataToDataGrid

    public String getProcessImgStream(String flowDefId) {
        LOGGER.info("getProcessImgStream start");

        LOGGER.info("flowDefId:{}", flowDefId);

        // 獲取流程圖
        BpmnModel bpmnModel = repositoryService.getBpmnModel(flowDefId);

        // 這個類在5.22.0往上的版本中才有
        DefaultProcessDiagramGenerator diagramGenerator = new DefaultProcessDiagramGenerator();

        // 繪制bpmnModel代表的流程的流程圖
        InputStream inputStream = diagramGenerator.generateDiagram(bpmnModel, new ArrayList<>(), new ArrayList<>(),
                DEFAULT_FONT, DEFAULT_FONT, DEFAULT_FONT);

        LOGGER.info("getProcessImgStream complete");
        String encoded = null;
        try {
            byte[] bytes = IOUtils.toByteArray(inputStream);
            encoded = Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encoded;
    } //getProcessImgStream

    private List<BidCaseListQueryDataGrid> queryKgoCasesetData(CaseManagementQueryRq rq) {
        return geoCaseOpenForOrganReposCustom.getBidCaseListData(rq).stream().map(l -> {
            String applyType = StringUtils.isBlank(l.getApplyType()) ? StringUtils.EMPTY : l.getApplyType();
            boolean isApplyTypeCActive = applyType.contains(ApplyTypeEnum.C.getValue()) ? true : false;
            boolean isApplyTypeEActive = applyType.contains(ApplyTypeEnum.E.getValue()) ? true : false;
            boolean isApplyTypeLActive = applyType.contains(ApplyTypeEnum.L.getValue()) ? true : false;

            BidCaseListQueryDataGrid dg = new BidCaseListQueryDataGrid();
            dg.setCaseSetId(l.getCaseSetId());
            dg.setCaseSetName(l.getCaseSetName());
            dg.setCaseFlowType(l.getCaseFlowType());
            dg.setIsApplyTypeCActive(isApplyTypeCActive);
            dg.setIsApplyTypeEActive(isApplyTypeEActive);
            dg.setIsApplyTypeLActive(isApplyTypeLActive);

            return dg;
        }).collect(Collectors.toList());
    }
}
