package gov.kcg.kgo.geocontroller.backend;

import gov.kcg.kgo.geoservice.GeoCaseInspectService;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoAppointmentMainSearchRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoCaseInspectExcelQueryRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoCaseInspectQueryRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoQuestionnaireResultAnswerQueryRq;
import gov.kcg.kgo.geoviewmodel.backend.rs.GeoAppointmentMainSearchRs;
import gov.kcg.kgo.geoviewmodel.backend.rs.GeoCaseInspectQueryRs;
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
 * GEO 20211030 add
 * 後台-案件稽核管理  API Controller.
 */
@Controller
@RequestMapping("/backend/caseInspect")
@Api(tags = {"geo-case-inspect-controller 後台-案件稽核管理"})
public class GeoCaseInspectController {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoCaseInspectController.class);

    @Autowired
    private GeoCaseInspectService geoCaseInspectService;

    /**
     * 後台-案件稽核管理:查詢服務清單
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "後台-案件稽核管理:查詢服務清單")
    @RequestMapping(value = {"/query"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoCaseInspectQueryRs searchAppointmentMain(@RequestBody GeoCaseInspectQueryRq rq, HttpServletRequest request) {
        GeoCaseInspectQueryRs rs = geoCaseInspectService.getRandomCaseList(rq);
        return rs;
    }

    /**
     * GEO 20211102 add
     * 後台-案件稽核管理:excel匯出
     *
     */
    @ApiOperation(value = "後台-案件稽核管理:excel匯出")
    @RequestMapping(value = {"/exportExcelAction"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public void exportExcelAction(@RequestBody GeoCaseInspectExcelQueryRq rq, HttpServletRequest request) {
        geoCaseInspectService.exportExcelAction(rq);
    }

    /**
     * GEO 20211104 add
     * 後台-案件稽核管理:pdf匯出
     *
     */
    @ApiOperation(value = "後台-案件稽核管理:pdf匯出")
    @RequestMapping(value = {"/exportPdfAction"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public void exportPdfAction(@RequestBody GeoCaseInspectExcelQueryRq rq, HttpServletRequest request) {
        geoCaseInspectService.exportPdfAction(rq);
    }
}
