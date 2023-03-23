package gov.kcg.kgo.geocontroller.backend;

import gov.kcg.kgo.geoservice.GeoCaseSetApplyCountService;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoCaseSetApplyCountRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoCaseSetApplyRankRq;
import gov.kcg.kgo.geoviewmodel.backend.rs.GeoCaseSetApplyCountRs;
import gov.kcg.kgo.geoviewmodel.backend.rs.GeoCaseSetApplyRankRs;
import gov.kcg.kgo.geoviewmodel.backend.rs.GeoCaseSetApplyRankRuleRs;
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
 * GEO 202101005 add
 * <p>
 * 後台-服務申辦統計 API Controller.
 */
@Controller
@RequestMapping("/backend/caseSetApplyCount")
@Api(tags = {"geo-case-set-apply-count-controller 後台-服務申辦統計"})
public class GeoCaseSetApplyCountController {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoCaseSetApplyCountController.class);

    @Autowired
    GeoCaseSetApplyCountService geoCaseSetApplyCountService;

    /**
     * 後台-服務申辦統計-取得申辦服務名次列表
     */
    @ApiOperation(value = "後台-服務申辦統計:取得申辦服務名次列表")
    @RequestMapping(value = {"/getApplyCountList"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoCaseSetApplyCountRs questionnaireList(@RequestBody GeoCaseSetApplyCountRq rq, HttpServletRequest request) {
        GeoCaseSetApplyCountRs rs = geoCaseSetApplyCountService.getApplyCountList(rq);
        return rs;
    } //getApplyCountList

    /**
     * GEO 202111123 add
     * 後台-服務申辦統計:設定前台統計頻率，儲存排序
     */
    @ApiOperation(value = "後台-服務申辦統計:設定前台統計頻率，儲存排序")
    @RequestMapping(value = {"/saveApplyCountRank"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoCaseSetApplyRankRuleRs saveApplyCountRank(@RequestBody GeoCaseSetApplyCountRq rq, HttpServletRequest request) {
        GeoCaseSetApplyRankRuleRs rs = geoCaseSetApplyCountService.saveApplyCountRankRule(rq);
        return rs;
    } //getApplyCountList

}
