package gov.kcg.kgo.geocontroller.backend;

import gov.kcg.kgo.geoservice.GeoAgentService;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoAgentDeleteRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoAgentInsertRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoAgentQueryRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoAgentUserInfoQueryRq;
import gov.kcg.kgo.geoviewmodel.backend.rs.*;
import gov.kcg.kgo.service.BackEndCommonService;
import gov.kcg.kgo.service.OrganUnitManagementService;
import gov.kcg.kgo.viewModel.backend.common.organUnitUserSelect.query.rq.OrganUnitUserSelectQueryRq;
import gov.kcg.kgo.viewModel.backend.common.organUnitUserSelect.query.rs.OrganUnitUserSelectQueryRs;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.comboBox.rq.OrganUnitManagementUnitComboBoxRq;
import gov.kcg.kgo.viewModel.backend.organUnitManagement.comboBox.rs.OrganUnitManagementUnitComboBoxRs;
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
 * GEO 20211026 add
 * 後台-設定代理人  API Controller.
 */
@Controller
@RequestMapping("/backend/agent")
@Api(tags = {"geo-agent-setting-controller 後台-設定代理人"})
public class GeoAgentSettingController {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoAgentSettingController.class);

    @Autowired
    BackEndCommonService backEndCommonService;

    @Autowired
    private GeoAgentService geoAgentService;

    @Autowired
    OrganUnitManagementService organUnitManagementService;


    /**
     * 後台-設定代理人:取得機關與科室預設下拉式選單
     *
     * @param rq
     * @return
     */
    @ApiOperation(value = "後台-設定代理人:初始畫面-取得機關與科室預設下拉式選單")
    @RequestMapping(value = {"/homeAction"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoAgentHomeRs unitComboBoxGetByOrganIdAction(@RequestBody ApiRequest rq, HttpServletRequest request) {
        GeoAgentHomeRs rs = geoAgentService.agentSettingHome();
        return rs;
    }

    /**
     * 後台-設定代理人:取得單位下拉式選單
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "後台-設定代理人:取得單位下拉式選單")
    @RequestMapping(value = {"/unitComboBoxGetByOrganIdAction"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public OrganUnitManagementUnitComboBoxRs unitComboBoxGetByOrganIdAction(@RequestBody OrganUnitManagementUnitComboBoxRq rq, HttpServletRequest request) {
        OrganUnitManagementUnitComboBoxRs rs = organUnitManagementService.unitComboBoxByOrganId(rq);
        return rs;
    }

    /**
     * 後台-設定代理人:取得該登入人員資料
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "後台-設定代理人:取得該人員資料")
    @RequestMapping(value = {"/userInfo"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoAgentUserInfoQueryRs getUserInfo(@RequestBody GeoAgentUserInfoQueryRq rq, HttpServletRequest request) {
        GeoAgentUserInfoQueryRs rs = geoAgentService.getUserInfo(rq);
        return rs;
    }

    /**
     * 共用-人員清單Popup-查詢
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "人員清單Popup-查詢，以organId, UnitId查詢")
    @RequestMapping(value = {"/organUnitUserSelectQueryAction"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public OrganUnitUserSelectQueryRs organUnitUserSelectQueryAction(@RequestBody OrganUnitUserSelectQueryRq rq,
                                                                     HttpServletRequest request) {
        OrganUnitUserSelectQueryRs rs = backEndCommonService.organUnitUserSelectQueryAction(rq);
        return rs;
    }

    /**
     * 後台-設定代理人:新增代理人
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "後台-設定代理人:新增代理人")
    @RequestMapping(value = {"/agentInsert"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoAgentInsertRs getUserInfo(@RequestBody GeoAgentInsertRq rq, HttpServletRequest request) {
        GeoAgentInsertRs rs = geoAgentService.insertAgent(rq);
        return rs;
    }

    /**
     * 後台-設定代理人:查詢代理人清單
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "後台-設定代理人:查詢代理人清單")
    @RequestMapping(value = {"/query"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoAgentQueryRs getAgentList(@RequestBody GeoAgentQueryRq rq, HttpServletRequest request) {
        GeoAgentQueryRs rs = geoAgentService.getAgentList(rq);
        return rs;
    }

    /**
     * 後台-設定代理人:刪除代理人
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "後台-設定代理人:刪除代理人")
    @RequestMapping(value = {"/delete"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoAgentDeleteRs deleteAgent(@RequestBody GeoAgentDeleteRq rq, HttpServletRequest request) {
        GeoAgentDeleteRs rs = geoAgentService.deleteAgent(rq);
        return rs;
    }

}
