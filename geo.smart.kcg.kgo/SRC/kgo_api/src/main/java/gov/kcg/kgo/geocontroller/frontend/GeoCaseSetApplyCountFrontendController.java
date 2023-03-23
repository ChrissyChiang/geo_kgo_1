package gov.kcg.kgo.geocontroller.frontend;

import gov.kcg.kgo.geoservice.GeoCaseSetApplyCountService;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoCaseSetApplyRankDetailListRq;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoCaseSetApplyRankDetailListRs;
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
 * 前台-服務申辦統計 API Controller.
 */
@Controller
@RequestMapping("/frontend/caseSetApplyCount")
@Api(tags = {"geo-case-set-apply-count-frontend-controller 前台-服務申辦統計"})
public class GeoCaseSetApplyCountFrontendController {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoCaseSetApplyCountFrontendController.class);

    @Autowired
    GeoCaseSetApplyCountService geoCaseSetApplyCountService;

    /**
     * 前台-服務申辦統計: 取得申辦服務名次列表
     *
     * @param rq
     * @param request
     */
    @ApiOperation(value = "前台-服務申辦統計: 取得申辦服務名次列表")
    @RequestMapping(value = {"/getCaseSetRankList"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoCaseSetApplyRankDetailListRs questionnaireList(@RequestBody GeoCaseSetApplyRankDetailListRq rq, HttpServletRequest request) {
        GeoCaseSetApplyRankDetailListRs rs = geoCaseSetApplyCountService.getCaseSetRankList(rq);
        return rs;
    } //getApplyCountList
}
