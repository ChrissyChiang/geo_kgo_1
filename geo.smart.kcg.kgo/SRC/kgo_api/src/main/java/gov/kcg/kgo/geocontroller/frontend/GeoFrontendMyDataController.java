package gov.kcg.kgo.geocontroller.frontend;

import gov.kcg.kgo.geoservice.GeoMyDataQueryService;
import gov.kcg.kgo.geoviewmodel.frontend.rq.MyDataQueryRq;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoMyDataQueryRs;
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


/**
 * GEO 20221009 add_Jim 前台MyData紀錄查詢
 * 前台-MyData紀錄查詢 API Controller
 */
@Controller
@RequestMapping("/frontend/myData")
@Api(tags = {"geo-frontend-myData-controller 前台-MyData紀錄查詢"})
public class GeoFrontendMyDataController {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoFrontendMyDataController.class);

    @Autowired
    GeoMyDataQueryService geoMyDataQueryService;

    /**
     * GEO 20221009 add_Jim
     * 前台-mydata:MyData紀錄查詢
     *
     * @param rq
     * @return
     */
    @ApiOperation(value = "MyData紀錄查詢")
    @RequestMapping(value = {"/myDataSearch"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoMyDataQueryRs myDataCaseQuery(@RequestBody MyDataQueryRq rq) {
        GeoMyDataQueryRs rs = geoMyDataQueryService.queryMyDataCase(rq);
        return rs;
    }
}
