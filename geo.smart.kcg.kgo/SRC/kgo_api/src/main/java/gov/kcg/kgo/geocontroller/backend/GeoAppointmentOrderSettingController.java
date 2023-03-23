package gov.kcg.kgo.geocontroller.backend;

import gov.kcg.kgo.enums.common.SystemTypeEnum;
import gov.kcg.kgo.geoservice.GeoAppointmentOrderService;
import gov.kcg.kgo.geoviewmodel.backend.GeoAppointmentInfoFormQueryRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.*;
import gov.kcg.kgo.geoviewmodel.backend.rs.*;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoAppointmentOrderFormQueryViewForm;
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
 * GEO 20211015 add
 * 後台-線上預約臨櫃-預約登錄管理  API Controller.
 */
@Controller
@RequestMapping("/backend/appointment")
@Api(tags = {"geo-appointment-order-setting-controller 後台-線上預約臨櫃-預約登錄管理"})
public class GeoAppointmentOrderSettingController {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoAppointmentOrderSettingController.class);

    @Autowired
    GeoAppointmentOrderService geoAppointmentOrderService;

    /**
     * 後台-線上預約臨櫃-預約登錄管理:搜尋該預約服務已登陸預約單
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "後台-線上預約臨櫃-預約登錄管理:搜尋該預約服務已登陸預約單")
    @RequestMapping(value = {"/appointmentOrderQuery"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoAppointmentOrderQueryRs queryAppointmentOrder(@RequestBody GeoAppointmentOrderQueryRq rq, HttpServletRequest request) {
        GeoAppointmentOrderQueryRs rs = geoAppointmentOrderService.getAppointmentList(rq);
        return rs;
    }

    /**
     * 後台-線上預約臨櫃-預約登錄管理:取得該服務預約時段
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "後台-線上預約臨櫃-預約登錄管理:取得該服務預約時段")
    @RequestMapping(value = {"/appointmentOrderDateQuery"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoAppointmentOrderDateQueryRs queryAppointmentOrder(@RequestBody GeoAppointmentOrderDateQueryRq rq, HttpServletRequest request) {
        GeoAppointmentOrderDateQueryRs rs = geoAppointmentOrderService.getAppointmentOrderDate(rq);
        return rs;
    }

    /**
     * 後台-線上預約臨櫃-預約登錄管理:新增/編輯預約登錄
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "後台-線上預約臨櫃-預約登錄管理:新增/編輯預約登錄")
    @RequestMapping(value = {"/appointmentOrderEdit"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoAppointmentOrderEditRs editAppointmentOrder(@RequestBody GeoAppointmentOrderEditRq rq, HttpServletRequest request) {
        GeoAppointmentOrderEditRs rs = geoAppointmentOrderService.editAppointmentOrder(rq, SystemTypeEnum.B);
        return rs;
    }

    /**
     * 後台-線上預約臨櫃-預約登錄管理:新增/編輯取得預約對應表單
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "後台-線上預約臨櫃-預約登錄管理:新增/編輯取得預約對應表單")
    @RequestMapping(value = {"/appointmentOrderFormQuery"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoAppointmentOrderFormQueryRs queryAppointmentForm(@RequestBody GeoAppointmentOrderFormQueryRq rq, HttpServletRequest request) {
        GeoAppointmentOrderFormQueryRs rs = geoAppointmentOrderService.getAppointmentOrderForm(rq,SystemTypeEnum.B);
        return rs;
    }

    /**
     * 後台-線上預約臨櫃-預約登錄管理:編輯取得預約者表單資料
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "後台-線上預約臨櫃-預約登錄管理:編輯取得預約者表單資料")
    @RequestMapping(value = {"/appointmentOrderInfoFormQuery"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoAppointmentOrderFormQueryRs queryAppointmentInfoForm(@RequestBody GeoAppointmentInfoFormQueryRq rq, HttpServletRequest request) {
        GeoAppointmentOrderFormQueryRs rs = geoAppointmentOrderService.getAppointmentIfoForm(rq,SystemTypeEnum.B);
        return rs;
    }


    /**
     * 後台-線上預約臨櫃-預約登錄管理:刪除已登錄預約
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "後台-線上預約臨櫃-預約登錄管理:刪除已登錄預約")
    @RequestMapping(value = {"/appointmentDelete"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoAppointmentDeleteRs editAppointmentOrder(@RequestBody GeoAppointmentDeleteRq rq, HttpServletRequest request) {
        GeoAppointmentDeleteRs rs = geoAppointmentOrderService.deleteAppointmentById(rq, SystemTypeEnum.B);
        return rs;
    }

    /**
     * GEO 20211115 add
     * 後台-線上預約臨櫃:excel匯出
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "*後台-線上預約臨櫃:excel匯出")
    @RequestMapping(value = {"/exportExcelAction"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public void exportExcelAction(@RequestBody GeoAppointmentOrderQueryRq rq, HttpServletRequest request) {
        geoAppointmentOrderService.exportExcelAction(rq);
    }
}
