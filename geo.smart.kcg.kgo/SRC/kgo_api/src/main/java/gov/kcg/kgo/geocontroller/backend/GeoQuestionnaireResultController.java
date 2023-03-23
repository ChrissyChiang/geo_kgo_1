package gov.kcg.kgo.geocontroller.backend;

import gov.kcg.kgo.geoservice.GeoQuestionnaireResultService;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoQuestionnaireQueryCaseAnswerRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoQuestionnaireResultAnswerQueryRq;
import gov.kcg.kgo.geoviewmodel.backend.rs.GeoQuestionnaireQueryAnswerListRs;
import gov.kcg.kgo.geoviewmodel.backend.rs.GeoQuestionnaireResultScoreQueryRs;
import gov.kcg.kgo.geoviewmodel.frontend.rq.GeoQuestionnaireResultQueryRq;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoQuestionnaireResultHomeRs;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoQuestionnaireResultQueryRs;
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
 * GEO 20211011 add
 * 後台-問卷結果查詢 API Controller.
 */
@Controller
@RequestMapping("/backend/questionnaire")
@Api(tags = {"geo-questionnaire-result-controller 後台-問卷結果查詢"})
public class GeoQuestionnaireResultController {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoQuestionnaireResultController.class);

    @Autowired
    GeoQuestionnaireResultService geoQuestionnaireResultService;

    /**
     * 後台-問卷結果查詢:初始畫面
     *
     * @param rq
     */
    @ApiOperation(value = "後台-問卷結果查詢:初始畫面(取得機關下拉式選單，沿用舊做法，可參照/backend/CaseManagement/homeAction)")
    @RequestMapping(value = {"/homeAction"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoQuestionnaireResultHomeRs geoQuestionnaireResultHomeAction(@RequestBody ApiRequest rq) {
        GeoQuestionnaireResultHomeRs rs = geoQuestionnaireResultService.questionnaireResultHome();
        return rs;
    }

    /**
     * 後台-問卷結果查詢:服務問卷查詢
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "後台-問卷結果查詢:服務問卷查詢")
    @RequestMapping(value = {"/queryAction"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoQuestionnaireResultQueryRs geoQuestionnaireResultQueryAction(@RequestBody GeoQuestionnaireResultQueryRq rq, HttpServletRequest request) {
        GeoQuestionnaireResultQueryRs rs = geoQuestionnaireResultService.getQuestionnaireResultQueryList(rq);
        return rs;
    }

    /**
     * 後台-問卷結果查詢:問卷結果(配分)
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "後台-問卷結果查詢:問卷結果(配分)")
    @RequestMapping(value = {"/queryScoreList"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoQuestionnaireResultScoreQueryRs getQuestionnaireResultQueryList(@RequestBody GeoQuestionnaireResultAnswerQueryRq rq, HttpServletRequest request) {
        GeoQuestionnaireResultScoreQueryRs rs = geoQuestionnaireResultService.getQuestionnaireResultScoreList(rq);
        return rs;
    }

    /**
     * 後台-檢視填寫:取得答案清單
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "後台-檢視填寫:取得答案清單")
    @RequestMapping(value = {"/queryAnswerList"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoQuestionnaireQueryAnswerListRs queryAnswerListByCaseSetIdAndVersion(@RequestBody GeoQuestionnaireResultAnswerQueryRq rq, HttpServletRequest request) {
        GeoQuestionnaireQueryAnswerListRs rs = geoQuestionnaireResultService.getQuestionnaireAnswerList(rq);
        return rs;
    }

    /**
     * 後台-檢視填寫:檢視該問卷題目答案
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "後台-檢視填寫:檢視該問卷題目答案")
    @RequestMapping(value = {"/queryCaseAnswer"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoQuestionnaireResultScoreQueryRs getQuestionnaireAnswer(@RequestBody GeoQuestionnaireQueryCaseAnswerRq rq, HttpServletRequest request) {
        GeoQuestionnaireResultScoreQueryRs rs = geoQuestionnaireResultService.getQuestionnaireAnswer(rq);
        return rs;
    }

    /**
     * GEO 20211030 add
     * 後台-問卷結果:excel匯出
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "後台-問卷結果:excel匯出")
    @RequestMapping(value = {"/exportExcelAction"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public void exportExcelAction(@RequestBody GeoQuestionnaireResultAnswerQueryRq rq, HttpServletRequest request) {
        geoQuestionnaireResultService.exportExcelAction(rq);
    }
}
