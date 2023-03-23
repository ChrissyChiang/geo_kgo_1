package gov.kcg.kgo.geocontroller.backend;

import gov.kcg.kgo.controller.backend.CaseManagementController;
import gov.kcg.kgo.controller.base.BaseController;
import gov.kcg.kgo.geoservice.GeoExCaseMgtService;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoExCaseSaveRq;
import gov.kcg.kgo.geoviewmodel.backend.rs.GeoExCaseMgtEditHomeRs;
import gov.kcg.kgo.service.BackEndCommonService;
import gov.kcg.kgo.service.CaseManagementService;
import gov.kcg.kgo.viewModel.backend.caseManagement.caseOrder.home.rq.CaseManagementCaseOrderHomeRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.caseOrder.home.rs.CaseManagementCaseOrderHomeRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.caseOrder.save.rq.CaseManagementCaseOrderSaveRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.caseOrder.save.rs.CaseManagementCaseOrderSaveRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.caseSave.rq.CaseManagementCaseSaveRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.caseSave.rs.CaseManagementCaseSaveRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.delete.rq.CaseManagementDeleteRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.delete.rs.CaseManagementDeleteRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.editHome.rq.CaseManagementEditHomeRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.editHome.rs.CaseManagementEditHomeRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.home.rs.CaseManagementHomeRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.managerComboBox.rq.CaseManagerComboBoxQueryRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.managerComboBox.rs.CaseManagerComboBoxQueryRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.organSelectQuery.rs.CaseManagementOrganSelectQueryRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.query.rq.CaseManagementQueryRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.query.rs.CaseManagementQueryRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.stateUpdate.rq.CaseManagementStatusUpdateRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.stateUpdate.rs.CaseManagementStatusUpdateRs;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * GEO 20221014 查詢服務管理Controller
 * Oberyn
 * */
@Controller
@RequestMapping("/backend/searchExCaseMgt")
@Api(tags = {"search-excase-management-controller 後台-查詢服務管理"})
public class GeoExCaseManagementController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoExCaseManagementController.class);
    @Autowired
    CaseManagementService caseManagementService;
    @Autowired
    GeoExCaseMgtService geoExCaseMgtService;

    /**
     * 查詢服務管理-初始畫面
     */
    @ApiOperation(value = "#查詢服務管理-初始畫面")
    @RequestMapping(value = { "/homeAction" }, method = { RequestMethod.POST }, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public CaseManagementHomeRs CaseManagementHomeAction() {
        CaseManagementHomeRs rs = geoExCaseMgtService.searchExCaseHome();
        return rs;
    }

    /**
     * 服務案件清單-案件查詢
     *
     * @param organId - 機關分類代碼
     * @param caseSetName - 案件名稱
     * @return
     */
    @ApiOperation(value = "#查詢服務管理-案件查詢")
    @RequestMapping(value = { "/queryAction" }, method = { RequestMethod.POST }, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public CaseManagementQueryRs CaseManagementQueryAction(@RequestBody CaseManagementQueryRq rq,
                                                           HttpServletRequest request) {
        CaseManagementQueryRs rs = geoExCaseMgtService.caseManagementQuery(rq);
        return rs;
    }

    /**
     * 查詢服務案件清單-案件狀態更改 (上架/下架/立即受理)
     *
     * @param
     * @return
     */
    @ApiOperation(value = "#查詢服務管理-案件狀態更改 (上架/下架/立即受理)")
    @RequestMapping(value = { "/statusUpdateAction" }, method = { RequestMethod.POST }, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public CaseManagementStatusUpdateRs StatusUpdateGetByOrganIdAction(@RequestBody CaseManagementStatusUpdateRq rq,
                                                                       HttpServletRequest request) {
        CaseManagementStatusUpdateRs rs = caseManagementService.statusUpdate(rq);
        return rs;
    }

    /**
     * 查詢服務管理-案件刪除
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "#查詢服務管理-案件維護-刪除")
    @RequestMapping(value = { "/deleteAction" }, method = { RequestMethod.POST }, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public CaseManagementDeleteRs classDeleteAction(@RequestBody CaseManagementDeleteRq rq,
                                                    HttpServletRequest request) {
        CaseManagementDeleteRs rs = caseManagementService.caseManagementDelete(rq);
        return rs;
    }




    /**
     * 查詢服務管理-案件維護-編輯
     *
     * @param
     * @return
     */
    @ApiOperation(value = "#查詢服務管理-案件維護-編輯")
    @RequestMapping(value = { "/editHomeAction" }, method = { RequestMethod.POST }, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public GeoExCaseMgtEditHomeRs CaseManagementEditHomeAction(@RequestBody CaseManagementEditHomeRq rq,
                                                               HttpServletRequest request) {
        GeoExCaseMgtEditHomeRs rs = geoExCaseMgtService.exCaseMgtEditHome(rq);
        return rs;
    }

    /**
     * 服務案件清單-案件維護-儲存
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "#查詢服務管理-案件維護-儲存")
    @RequestMapping(value = { "/caseSaveAction" }, method = { RequestMethod.POST }, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public CaseManagementCaseSaveRs CaseManagementCaseSaveAction(@RequestBody GeoExCaseSaveRq rq,
                                                                 HttpServletRequest request) {
        LOGGER.info("CaseManagementController CaseManagementCaseSaveAction...");
        CaseManagementCaseSaveRs rs = geoExCaseMgtService.caseManagementCaseSave(rq);
        return rs;
    }

}
