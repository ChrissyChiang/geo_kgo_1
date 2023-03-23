package gov.kcg.kgo.geocontroller.backend;

import gov.kcg.kgo.controller.base.BaseController;
import gov.kcg.kgo.geoservice.GeoCaseSetAssociateService;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoCaseSetAssociateDeleteRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoCaseSetAssociateInsertRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoCaseSetAssociateListQueryRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoCaseSetAssociateQueryRq;
import gov.kcg.kgo.geoviewmodel.backend.rs.GeoCaseSetAssociateDeleteRs;
import gov.kcg.kgo.geoviewmodel.backend.rs.GeoCaseSetAssociateQueryActionRs;
import gov.kcg.kgo.service.BackEndCommonService;
import gov.kcg.kgo.service.CaseManagementService;
import gov.kcg.kgo.viewModel.backend.caseManagement.home.rs.CaseManagementHomeRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.managerComboBox.rq.CaseManagerComboBoxQueryRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.managerComboBox.rs.CaseManagerComboBoxQueryRs;
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
 * GEO 20211019 add
 * 後台-案件關聯服務  API Controller.
 */
@Controller
@RequestMapping("/backend/caseSetAssociate")
@Api(tags = {"geo-case-set-associate-setting-controller 後台-案件關聯服務"})
public class GeoCaseSetAssociateSettingController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeoCaseSetAssociateSettingController.class);

    @Autowired
    CaseManagementService caseManagementService;

    @Autowired
    BackEndCommonService backEndCommonService;

    @Autowired
    GeoCaseSetAssociateService geoCaseSetAssociateService;

    /**
     * 後台-案件關聯服務:初始畫面
     *
     * @return
     */
    @ApiOperation(value = "後台-案件關聯服務:機關分類，權責機關選單")
    @RequestMapping(value = {"/homeAction"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public CaseManagementHomeRs caseSetAssociateHome(@RequestBody ApiRequest rq, HttpServletRequest request) {
        CaseManagementHomeRs rs = caseManagementService.caseManagementHome();
        return rs;
    }

    /**
     * 後台-案件關聯服務:服務管理者下拉式選單查詢
     *
     * @param rq
     * @return
     */
    @ApiOperation(value = "後台-案件關聯服務:服務管理者下拉式選單查詢")
    @RequestMapping(value = {"/comboBoxQueryAction"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public CaseManagerComboBoxQueryRs caseSetAssociateComboBoxQuery(@RequestBody CaseManagerComboBoxQueryRq rq, HttpServletRequest request) {
        return caseManagementService.caseManagerComboBoxQuery(rq);
    }

    /**
     * 後台-案件關聯服務:案件查詢
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "後台-案件關聯服務:可關聯案件查詢")
    @RequestMapping(value = {"/queryAction"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoCaseSetAssociateQueryActionRs caseSetAssociateQuery(@RequestBody GeoCaseSetAssociateQueryRq rq, HttpServletRequest request) {
        GeoCaseSetAssociateQueryActionRs rs = geoCaseSetAssociateService.caseSetQuery(rq);
        return rs;
    }

    /**
     * 後台-案件關聯服務:取得已關聯案件清單
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "後台-案件關聯服務:取得已關聯案件清單")
    @RequestMapping(value = {"/queryAssociateList"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoCaseSetAssociateQueryActionRs caseSetAssociateListQuery(@RequestBody GeoCaseSetAssociateListQueryRq rq, HttpServletRequest request) {
        GeoCaseSetAssociateQueryActionRs rs = geoCaseSetAssociateService.associateCaseSetQuery(rq);
        return rs;
    }

    /**
     * 後台-案件關聯服務:新增關聯案件
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "後台-案件關聯服務:新增關聯案件")
    @RequestMapping(value = {"/insertAssociate"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoCaseSetAssociateQueryActionRs caseSetAssociateInsert(@RequestBody GeoCaseSetAssociateInsertRq rq, HttpServletRequest request) {
        GeoCaseSetAssociateQueryActionRs rs = geoCaseSetAssociateService.insertAssociateCaseSett(rq);
        return rs;
    }

    /**
     * 後台-案件關聯服務:刪除關聯案件
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "後台-案件關聯服務:刪除關聯案件")
    @RequestMapping(value = {"/deleteAssociate"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoCaseSetAssociateDeleteRs caseSetAssociateDelete(@RequestBody GeoCaseSetAssociateDeleteRq rq, HttpServletRequest request) {
        GeoCaseSetAssociateDeleteRs rs = geoCaseSetAssociateService.deleteAssociate(rq);
        return rs;
    }
}
