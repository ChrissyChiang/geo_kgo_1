package gov.kcg.kgo.geocontroller.frontend;

import gov.kcg.kgo.geoservice.GeoFrontendUserService;
import gov.kcg.kgo.geoservice.GeoMyDataQueryService;
import gov.kcg.kgo.geoviewmodel.frontend.rq.GeoFrontendUserRegisterRq;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoFrontendUserInfoQueryRs;
import gov.kcg.kgo.repository.KgoMydataFileRepository;
import gov.kcg.kgo.service.CommonService;
import gov.kcg.kgo.service.MyDataService;
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
 * GEO 20211113 add 前台使用者註冊
 * 前台-使用者帳號 API Controller.
 *
 * Geo 2022104 前台頁面會員專區移除，估該Controller不會再被使用
 */
@Controller
@RequestMapping("/frontend/user")
@Api(tags = {"geo-frontend-user-controller 前台-使用者帳號"})
public class GeoFrontendUserController {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoFrontendUserController.class);

    @Autowired
    GeoFrontendUserService geoFrontendUserService;
    @Autowired
    KgoMydataFileRepository kgoMydataFileRepository;
    @Autowired
    GeoMyDataQueryService geoMyDataQueryService;
    @Autowired
    MyDataService myDataService;
    @Autowired
    CommonService commonService;

    /**
     * 前台-使用者帳號:取得註冊後使用者資訊
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "前台-使用者帳號:取得註冊後使用者資訊")
    @RequestMapping(value = {"/infoQuery"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoFrontendUserInfoQueryRs infoQuery(@RequestBody ApiRequest rq, HttpServletRequest request) {
        GeoFrontendUserInfoQueryRs rs = geoFrontendUserService.queryInfo();
        return rs;
    }

    /**
     * 前台-使用者帳號:註冊
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "前台-使用者帳號:註冊")
    @RequestMapping(value = {"/register"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoFrontendUserInfoQueryRs register(@RequestBody GeoFrontendUserRegisterRq rq, HttpServletRequest request) {
        GeoFrontendUserInfoQueryRs rs = geoFrontendUserService.insertUser(rq);
        return rs;
    }

    /**
     * 前台-使用者帳號:編輯資訊
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "前台-使用者帳號:編輯資訊")
    @RequestMapping(value = {"/editUser"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoFrontendUserInfoQueryRs editUser(@RequestBody GeoFrontendUserRegisterRq rq, HttpServletRequest request) {
        GeoFrontendUserInfoQueryRs rs = geoFrontendUserService.editUser(rq);
        return rs;
    }

}
