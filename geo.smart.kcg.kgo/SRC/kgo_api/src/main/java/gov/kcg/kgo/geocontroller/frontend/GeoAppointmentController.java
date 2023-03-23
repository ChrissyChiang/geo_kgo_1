package gov.kcg.kgo.geocontroller.frontend;

import gov.kcg.kgo.enums.common.SystemTypeEnum;
import gov.kcg.kgo.geoservice.GeoAppointmentOrderService;
import gov.kcg.kgo.geoservice.GeoAppointmentService;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoAppointmentDeleteRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoAppointmentFormQueryRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoAppointmentOrderEditRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoAppointmentOrderFormQueryRq;
import gov.kcg.kgo.geoviewmodel.backend.rs.*;
import gov.kcg.kgo.geoviewmodel.frontend.rq.GeoAppointmentInfoQueryByDayRq;
import gov.kcg.kgo.geoviewmodel.frontend.rq.GeoAppointmentNumbersSearchRq;
import gov.kcg.kgo.geoviewmodel.frontend.rq.GeoAppointmentOrderQueryByPersonRq;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoAppointmentInfoQueryByDayRs;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoAppointmentNumbersSearchRs;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import gov.kcg.kgo.viewModel.frontend.bidInstruction.home.rq.BidInstructionHomeRq;
import gov.kcg.kgo.viewModel.frontend.bidInstruction.home.rs.BidInstructionHomeRs;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * GEO 20211015 add
 * 前台-線上預約臨櫃 API Controller.
 */
@Controller
@RequestMapping("/frontend/appointment")
@Api(tags = {"geo-appointment-controller 前台-線上預約臨櫃"})
public class GeoAppointmentController {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoAppointmentController.class);

    @Autowired
    GeoAppointmentService geoAppointmentService;

    @Autowired
    GeoAppointmentOrderService geoAppointmentOrderService;

    /**
     * 前台-線上預約臨櫃:搜尋線上預約臨櫃服務清單
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "前台-線上預約臨櫃:搜尋線上預約臨櫃服務清單")
    @RequestMapping(value = {"/appointmentMainSearch"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoAppointmentMainSearchRs searchAppointmentMain(@RequestBody ApiRequest rq, HttpServletRequest request) {
        GeoAppointmentMainSearchRs rs = geoAppointmentService.getAppointmentMainListByStatus();
        return rs;
    }

    /**
     * 前台-線上預約臨櫃:取得該預約單當日之後的預約名額與資訊
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "前台-線上預約臨櫃:取得該預約單當日之後的預約名額與資訊")
    @RequestMapping(value = {"/appointmentNumbersSearch"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoAppointmentNumbersSearchRs searchAppointmentNumbers(@RequestBody GeoAppointmentNumbersSearchRq rq, HttpServletRequest request) {
        GeoAppointmentNumbersSearchRs rs = geoAppointmentService.searchAppointmentNumbers(rq);
        return rs;
    }

    /**
     * 前台-線上預約臨櫃:取得單日預約資料
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "前台-線上預約臨櫃:取得單日預約資料")
    @RequestMapping(value = {"/appointmentInfoQueryByDay"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoAppointmentInfoQueryByDayRs queryAppointmentInfoByDay(@RequestBody GeoAppointmentInfoQueryByDayRq rq, HttpServletRequest request) {
        GeoAppointmentInfoQueryByDayRs rs = geoAppointmentService.queryAppointmentInfoByDay(rq);
        return rs;
    }

    /**
     * 20220811 GEO add
     * 前台-線上預約臨櫃:取得同意說明頁
     *
     */
    @ApiOperation(value = "前台-線上預約臨櫃:取得同意說明頁")
    @RequestMapping(value = { "/bidInstruction/homeAction" }, method = { RequestMethod.POST }, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public AppointmentBidInstructionHomeRs bidInstructionHomeAction(@RequestBody GeoAppointmentFormQueryRq rq, HttpServletRequest request) {

        AppointmentBidInstructionHomeRs rs = geoAppointmentService.bidInstructionHome(rq);
        return rs;
    }

    /**
     * 前台-線上預約臨櫃:新增/編輯預約登錄
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "前台-線上預約臨櫃:新增/編輯預約登錄")
    @RequestMapping(value = {"/appointmentOrderEdit"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoAppointmentOrderEditRs editAppointmentOrder(@RequestBody GeoAppointmentOrderEditRq rq, HttpServletRequest request) {
        GeoAppointmentOrderEditRs rs = geoAppointmentOrderService.editAppointmentOrder(rq, SystemTypeEnum.F);
        return rs;
    }

    /**
     * 前台-線上預約臨櫃:新增/編輯取得預約對應表單
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "前台-線上預約臨櫃:新增/編輯取得預約對應表單")
    @RequestMapping(value = {"/appointmentOrderFormQuery"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoAppointmentOrderFormQueryRs queryAppointmentForm(@RequestBody GeoAppointmentOrderFormQueryRq rq, HttpServletRequest request) {
        GeoAppointmentOrderFormQueryRs rs = geoAppointmentOrderService.getAppointmentOrderForm(rq, SystemTypeEnum.F);
        return rs;
    }

    /**
     * 前台-線上預約臨櫃:刪除已登錄預約
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "前台-線上預約臨櫃:刪除已登錄預約")
    @RequestMapping(value = {"/appointmentDelete"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoAppointmentDeleteRs editAppointmentOrder(@RequestBody GeoAppointmentDeleteRq rq, HttpServletRequest request) {
        GeoAppointmentDeleteRs rs = geoAppointmentOrderService.deleteAppointmentById(rq, SystemTypeEnum.F);
        return rs;
    }

    /**
     * 前台-線上預約臨櫃:搜尋該民眾已登錄預約單
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "前台-線上預約臨櫃:搜尋該民眾已登錄預約單")
    @RequestMapping(value = {"/appointmentOrderQuery"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoAppointmentOrderQueryRs queryAppointmentOrderByPerson(@RequestBody GeoAppointmentOrderQueryByPersonRq rq, HttpServletRequest request) {
        GeoAppointmentOrderQueryRs rs = geoAppointmentService.getAppointmentList(rq);
        return rs;
    }

    @ApiOperation(value = "前台-線上預約臨櫃:外部連結民眾已登錄預約單")
    @RequestMapping(value = {"/caseCheck"}, method = {RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoAppointmentOrderQueryRs queryAppointmentByQRCodeLink(@RequestParam("code")String caseId, HttpServletRequest request) {
        GeoAppointmentOrderQueryRs rs = geoAppointmentService.getAppointByQRCode(caseId);
        return rs;
    }

}
