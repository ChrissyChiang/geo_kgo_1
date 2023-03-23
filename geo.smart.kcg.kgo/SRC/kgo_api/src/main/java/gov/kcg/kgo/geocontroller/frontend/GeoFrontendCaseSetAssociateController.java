package gov.kcg.kgo.geocontroller.frontend;

import gov.kcg.kgo.geoservice.GeoCaseSetApplyCountService;
import gov.kcg.kgo.geoviewmodel.frontend.rq.GeoFrontendCaseSetAssociateQueryRq;
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
 * 前台-關聯服務 API Controller.
 */
@Controller
@RequestMapping("/frontend/caseSetAssociate")
@Api(tags = {"geo-frontend-case-set-associate-controller 前台-關聯服務"})
public class GeoFrontendCaseSetAssociateController {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoFrontendCaseSetAssociateController.class);

    @Autowired
    GeoCaseSetApplyCountService geoCaseSetApplyCountService;

    /**
     * 前台-關聯服務: 取得關聯服務
     *
     * @param rq
     * @param request
     */
    @ApiOperation(value = "前台-關聯服務: 取得關聯服務")
    @RequestMapping(value = {"/query"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoCaseSetApplyRankDetailListRs questionnaireList(@RequestBody GeoFrontendCaseSetAssociateQueryRq rq, HttpServletRequest request) {
        GeoCaseSetApplyRankDetailListRs rs = geoCaseSetApplyCountService.getCaseSetAssociateList(rq);
        return rs;
    } //getApplyCountList
}
