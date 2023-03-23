package gov.kcg.kgo.geocontroller.backend;

import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.geoservice.GeoAppointmentSettingService;
import gov.kcg.kgo.geoviewmodel.backend.rq.*;
import gov.kcg.kgo.geoviewmodel.backend.rs.*;
import gov.kcg.kgo.service.OrganUnitManagementService;
import gov.kcg.kgo.util.SsoUtil;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoAppointmentFormQueryRq;
import gov.kcg.kgo.viewModel.backend.internetApply.identityVerify.home.rq.InternetApplyIdentityVerifyHomeRq;
import gov.kcg.kgo.viewModel.backend.internetApply.identityVerify.home.rs.InternetApplyIdentityVerifyHomeRs;
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
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * GEO 20211015 add
 * 後台-線上預約臨櫃 API Controller.
 */
@Controller
@RequestMapping("/backend/appointment")
@Api(tags = {"geo-appointment-setting-controller 後台-線上預約臨櫃"})
public class GeoAppointmentSettingController {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoAppointmentSettingController.class);

    @Autowired
    GeoAppointmentSettingService geoAppointmentSettingService;

    @Autowired
    OrganUnitManagementService organUnitManagementService;


    /**
     * 後台-線上預約臨櫃:初始畫面
     *
     * @param rq
     * @return
     */
    @ApiOperation(value = "後台-線上預約臨櫃:初始畫面-取得機關與科室預設下拉式選單")
    @RequestMapping(value = {"/homeAction"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoAppointmentHomeRs unitComboBoxGetByOrganIdAction(@RequestBody ApiRequest rq, HttpServletRequest request) {
        GeoAppointmentHomeRs rs = geoAppointmentSettingService.appointmentSettingHome();
        return rs;
    }

    /**
     * 後台-線上預約臨櫃:取得單位下拉式選單
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "後台-線上預約臨櫃:取得單位下拉式選單")
    @RequestMapping(value = {"/unitComboBoxGetByOrganIdAction"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public OrganUnitManagementUnitComboBoxRs unitComboBoxGetByOrganIdAction(@RequestBody OrganUnitManagementUnitComboBoxRq rq, HttpServletRequest request) {
        OrganUnitManagementUnitComboBoxRs rs = organUnitManagementService.unitComboBoxByOrganId(rq);
        return rs;
    }

    /**
     * 後台-線上預約臨櫃:搜尋線上預約臨櫃服務清單
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "後台-線上預約臨櫃:搜尋線上預約臨櫃服務清單")
    @RequestMapping(value = {"/appointmentMainSearch"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoAppointmentMainSearchRs searchAppointmentMain(@RequestBody GeoAppointmentMainSearchRq rq, HttpServletRequest request) {
        GeoAppointmentMainSearchRs rs = geoAppointmentSettingService.getAppointmentMainList(rq);
        return rs;
    }

    /**
     * 後台-線上預約臨櫃:新增編輯預約主檔
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "後台-線上預約臨櫃:新增編輯預約主檔")
    @RequestMapping(value = {"/appointmentMainInsert"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoAppointmentMainInsertRs unitComboBoxGetByOrganIdAction(@RequestBody GeoAppointmentMainInsertRq rq, HttpServletRequest request) {
        GeoAppointmentMainInsertRs rs = geoAppointmentSettingService.editAppointmentMain(rq, (BackendLoginUserInfo) WebUtils.getSessionAttribute(request, SsoUtil.BACKEND_USER_INO_KEY));
        return rs;
    }

    /**
     * 後台-線上預約臨櫃-編輯：取得該預約對應表單
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation("後台-線上預約臨櫃-編輯：取得該預約對應表單")
    @RequestMapping(value = { "/appointmentForm/Query" }, method = { RequestMethod.POST }, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public GeoAppointmentFormQueryRs appointmentFormQuery(@RequestBody GeoAppointmentFormQueryRq rq,
                                                          HttpServletRequest request) {
        GeoAppointmentFormQueryRs rs = geoAppointmentSettingService.getAppointmentForm(rq);
        return rs;
    }

    /**
     * 後台-線上預約臨櫃-編輯：預約表單欄位維護初始化
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation("後台-線上預約臨櫃-編輯：預約表單欄位維護初始化")
    @RequestMapping(value = { "/appointmentForm/ColumnHomeAction" }, method = { RequestMethod.POST }, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public GeoAppointmentFormColumnHomeActionRS appointmentFormColumnHomeAction(@RequestBody ApiRequest rq,
                                                                                HttpServletRequest request) {
        GeoAppointmentFormColumnHomeActionRS rs = geoAppointmentSettingService.appointmentFormColumnHomeAction();
        return rs;
    }

    /**
     * 後台-線上預約臨櫃-編輯：表單儲存更版
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation("後台-線上預約臨櫃-編輯：表單儲存更版")
    @RequestMapping(value = { "/appointmentForm/ColumnSaveAction" }, method = { RequestMethod.POST }, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public GeoAppointmentFormColumnSaveActionRs appointmentFormColumnSaveAction(@RequestBody GeoAppointmentFormColumnSaveActionRq rq,
                                                                                HttpServletRequest request) {
        GeoAppointmentFormColumnSaveActionRs rs = geoAppointmentSettingService.saveAppointmentFormColumn(rq);
        return rs;
    }

    /**
     * 後台-線上預約臨櫃:承辦人帳號搜尋
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "後台-線上預約臨櫃:承辦人帳號搜尋")
    @RequestMapping(value = {"/contactUserQuery"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoAppointmentContactUserQueryRs queryContactUser(@RequestBody GeoAppointmentContactUserQueryRq rq, HttpServletRequest request) {
        GeoAppointmentContactUserQueryRs rs = geoAppointmentSettingService.getContactUserList(rq);
        return rs;
    }

    /**
     * 後台-線上預約臨櫃:新增黑名單
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "後台-線上預約臨櫃:新增黑名單")
    @RequestMapping(value = {"/blockUserEdit"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoAppointmentBlockUserEditRs editBlockUser(@RequestBody GeoAppointmentBlockUserEditRq rq, HttpServletRequest request) {
        GeoAppointmentBlockUserEditRs rs = geoAppointmentSettingService.editAppointmentBlockUser(rq, (BackendLoginUserInfo) WebUtils.getSessionAttribute(request, SsoUtil.BACKEND_USER_INO_KEY));
        return rs;
    }

    /**
     * 後台-線上預約臨櫃:刪除黑名單
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "後台-線上預約臨櫃:刪除黑名單")
    @RequestMapping(value = {"/blockUserDelete"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoAppointmentBlockUserDeleteRs deleteBlockUser(@RequestBody GeoAppointmentBlockUserDeleteRq rq, HttpServletRequest request) {
        GeoAppointmentBlockUserDeleteRs rs = geoAppointmentSettingService.deleteAppointmentBlockUser(rq, (BackendLoginUserInfo) WebUtils.getSessionAttribute(request, SsoUtil.BACKEND_USER_INO_KEY));
        return rs;
    }

    /**
     * 後台-線上預約臨櫃:查詢黑名單清單
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "後台-線上預約臨櫃:查詢黑名單清單")
    @RequestMapping(value = {"/blockUserQuery"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoAppointmentBlockUserQueryRs queryBlockUser(@RequestBody GeoBlockUserQueryRq rq, HttpServletRequest request) {
        GeoAppointmentBlockUserQueryRs rs = geoAppointmentSettingService.queryAppointmentBlockUser(rq);
        return rs;
    }

    /**
     * 後台-線上預約臨櫃:儲存預約主檔、細節
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "後台-線上預約臨櫃:儲存預約主檔、細節")
    @RequestMapping(value = {"/appointmentInfoEdit"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoAppointmentInfoInsertRs appointmentInfoEdit(@RequestBody GeoAppointmentInfoInsertRq rq, HttpServletRequest request) {
        GeoAppointmentInfoInsertRs rs = geoAppointmentSettingService.editAppointmentInfo(rq, (BackendLoginUserInfo) WebUtils.getSessionAttribute(request, SsoUtil.BACKEND_USER_INO_KEY));
        return rs;
    }

    /**
     * 後台-線上預約臨櫃-編輯:取得該預約單資料
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "後台-線上預約臨櫃-編輯:取得該預約單資料")
    @RequestMapping(value = {"/appointmentInfoQuery"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoAppointmentInfoQueryRs queryAppointmentInfo(@RequestBody GeoAppointmentInfoQueryRq rq, HttpServletRequest request) {
        GeoAppointmentInfoQueryRs rs = geoAppointmentSettingService.getAppointmentInfo(rq);
        return rs;
    }

    /**
     * 後台-線上預約臨櫃-編輯:依時間範圍取得該預約單資料
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "後台-線上預約臨櫃-編輯:依時間範圍取得該預約單資料")
    @RequestMapping(value = {"/appointmentInfoByTimeQuery"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoAppointmentInfoQueryRs queryAppointmentInfoByTime(@RequestBody GeoAppointmentInfoByTimeQueryRq rq, HttpServletRequest request) {
        GeoAppointmentInfoQueryRs rs = geoAppointmentSettingService.getAppointmentInfoByTime(rq);
        return rs;
    }


    /**
     * 20220811 GEO add
     * 後台-線上預約臨櫃-編輯：身分驗證初始畫面
     */
    @ApiOperation(value = "後台-線上預約臨櫃-編輯：身分驗證初始畫面")
    @RequestMapping(value = { "/identityVerify/homeAction" }, method = { RequestMethod.POST }, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public InternetApplyIdentityVerifyHomeRs internetApplyIdentityVerifyHomeAction( @RequestBody GeoAppointmentInfoQueryRq rq, HttpServletRequest request) {
        InternetApplyIdentityVerifyHomeRs rs = geoAppointmentSettingService.internetApplyIdentityVerifyHome(rq);
        return rs;
    }
}
