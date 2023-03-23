package gov.kcg.kgo.geocontroller.backend;

import gov.kcg.kgo.controller.base.BaseController;
import gov.kcg.kgo.geoservice.GeoCaseOpenForOrganService;
import gov.kcg.kgo.geoviewmodel.backend.rs.GeoCaseOpenForOrganQueryActionRs;
import gov.kcg.kgo.service.BackEndCommonService;
import gov.kcg.kgo.service.CaseManagementService;
import gov.kcg.kgo.viewModel.backend.caseManagement.home.rs.CaseManagementHomeRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.managerComboBox.rq.CaseManagerComboBoxQueryRq;
import gov.kcg.kgo.viewModel.backend.caseManagement.managerComboBox.rs.CaseManagerComboBoxQueryRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.query.rq.CaseManagementQueryRq;
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
 * 後台-府內線上服務  API Controller.
 */
@Controller
@RequestMapping("/backend/caseOpenForOrgan")
@Api(tags = {"geo-case-open-for-organ-controller 後台-府內線上服務"})
public class GeoCaseOpenForOrganController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeoCaseOpenForOrganController.class);

    @Autowired
    CaseManagementService caseManagementService;

    @Autowired
    BackEndCommonService backEndCommonService;

    @Autowired
    GeoCaseOpenForOrganService geoCaseOpenForOrganService;

    /**
     * 後台-府內線上服務:初始畫面
     *
     * @return
     */
    @ApiOperation(value = "後台-府內線上服務:初始畫面（機關分類organComboBox，權責機關下拉選單ownerOrganComboBox")
    @RequestMapping(value = {"/homeAction"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public CaseManagementHomeRs caseOpenForOrganHomeAction(@RequestBody ApiRequest rq, HttpServletRequest request) {
        CaseManagementHomeRs rs = caseManagementService.caseManagementHome();
        return rs;
    }

    /**
     * 後台-府內線上服務:服務管理者下拉式選單查詢
     *
     * @param rq
     * @return
     */
    @ApiOperation(value = "後台-府內線上服務:服務管理者下拉式選單查詢")
    @RequestMapping(value = {"/comboBoxQueryAction"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public CaseManagerComboBoxQueryRs caseOpenForOrganComboBoxQueryAction(@RequestBody CaseManagerComboBoxQueryRq rq) {
        return caseManagementService.caseManagerComboBoxQuery(rq);
    }

    /**
     * 後台-府內線上服務:案件查詢
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "後台-府內線上服務:案件查詢")
    @RequestMapping(value = {"/queryAction"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoCaseOpenForOrganQueryActionRs caseOpenForOrganQueryAction(@RequestBody CaseManagementQueryRq rq,
                                                                      HttpServletRequest request) {
        GeoCaseOpenForOrganQueryActionRs rs = geoCaseOpenForOrganService.caseManagementQuery(rq);
        return rs;
    }

}
