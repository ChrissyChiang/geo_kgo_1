package gov.kcg.kgo.geocontroller.common;

import com.alibaba.fastjson.JSON;
import gov.kcg.kgo.controller.base.BaseController;
import gov.kcg.kgo.geoservice.GeoCityExtService;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoCrossOrganResultRq;
import gov.kcg.kgo.geoviewmodel.backend.rs.GeoCrossOrganResultRs;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoCrossOrganResultViewForm;
import gov.kcg.kgo.geoviewmodel.frontend.rq.*;
import gov.kcg.kgo.geoviewmodel.frontend.rs.*;
import gov.kcg.kgo.model.KgoCaseMain;
import gov.kcg.kgo.repository.KgoCaseMainRepository;
import gov.kcg.kgo.service.CaseHandleService;
import gov.kcg.kgo.service.CityCoinAPIService;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

import static gov.kcg.kgo.enums.backend.CaseMainStatusEnum.F3;
import static gov.kcg.kgo.enums.backend.CaseMainStatusEnum.J3;

/**
 * GEO 20210814 add
 * <p>
 * 對外API 之 2
 * 高雄城市資料平台 API Controller.
 */
@Controller
@RequestMapping("/common")
@Api(tags = "GeoKcgCityExtController")
public class GeoKcgCityExtController extends BaseController {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoKcgCityExtController.class);

    @Autowired
    GeoCityExtService geoKcgCityExtService;
    @Autowired
    KgoCaseMainRepository kgoCaseMainRepository;
    @Autowired
    CaseHandleService caseHandleService;
    @Autowired
    CityCoinAPIService cityCoinAPIService;

    /**
     * 提供1999派工案件編號查詢(依時間區間)
     *
     * @param rq the rq
     * @return CorrectStatusActionRs rs
     */
    @ApiOperation(value = "提供1999派工案件編號查詢(依時間區間)")
    @RequestMapping(value = {"/get1999KdCaseIdAction"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoGet1999CaseIdActionRs get1999KdCaseIdAction(@RequestBody GeoGet1999CaseIdActionRq rq, HttpServletRequest request) {
        synchronized (this) {
            GeoGet1999CaseIdActionRs rs = geoKcgCityExtService.get1999KdActionByRange(rq);
            return rs;
        }
    } //get1999KdCaseIdAction

    /**
     * 提供1999陳情案件編號查詢(依時間區間)
     *
     * @param rq the rq
     * @return CorrectStatusActionRs rs
     */
    @ApiOperation(value = "提供1999陳情案件編號查詢(依時間區間)")
    @RequestMapping(value = {"/get1999NewCaseIdAction"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoGet1999CaseIdActionRs get1999NewCaseIdAction(@RequestBody GeoGet1999CaseIdActionRq rq, HttpServletRequest request) {
        synchronized (this) {
            GeoGet1999CaseIdActionRs rs = geoKcgCityExtService.get1999NewActionByRange(rq);
            return rs;
        }
    } //get1999NewCaseIdAction

    /**
     * GEO 20211108 add
     * 為A流程服務提供取得自然人憑證登入資訊(姓名與身分證字號)
     *
     * @param rq the rq
     * @return CorrectStatusActionRs rs
     */
    @ApiOperation(value = "為A流程服務提供取得自然人憑證登入資訊")
    @RequestMapping(value = {"/getMoicaData"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoMoicaDataQueryRs getMoicaData(@RequestBody GeoMoicaDataQueryRq rq, HttpServletRequest request) {
        GeoMoicaDataQueryRs rs = geoKcgCityExtService.getMoicaDataByCaseId(rq);
        return rs;
    } //getMoicaData

    /**
     * for test
     **/
    @ApiOperation(value = "取得路燈資料，暫時開啟")
    @RequestMapping(value = {"/test"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public void getTest(HttpServletRequest request, @RequestBody ApiRequest rq) {
        synchronized (this) {
            //geoKcgCityExtService.sendGet1999KdApi();
            //geoKcgCityExtService.sendGet1999NewApi();
            get1999Light();
        }
    } //test

    private void get1999Light() {
        String[] arr = {"800", "801", "802", "803", "804", "805", "806", "807",
                "811", "812", "813", "814", "815",
                "820", "821", "822", "823", "824", "825", "826", "827", "828", "829",
                "830", "831", "832", "833",
                "840", "842", "843", "844", "845", "846", "847", "848", "849",
                "851", "852"};
        geoKcgCityExtService.setGet1999AddrAdviceApi(arr);
    } //get1999Light


    //排程取得路燈資料
    //@Scheduled(cron = "0 0 3 1 * *")
    @Scheduled(cron = "0 0 3 1 * *")
    protected void scheduleGet1999Light() {
        //整個流程跑完大概要超過15分鐘
        LOGGER.info("GeoKcgCityExtController scheduleGet1999Light start...");
        get1999Light();
        LOGGER.info("GeoKcgCityExtController scheduleGet1999Light end...");
    } //get1999Light, 台灣時間每月一日 3:00 執行

    /**
     * GEO 20211115 add for 民政局五種服務轉成B流程
     * 提供民政局兵役類案件資料查詢(依時間區間)
     *
     * @param rq the rq
     * @return CorrectStatusActionRs rs
     */
    @ApiOperation(value = "提供民政局兵役類案件資料查詢(依時間區間)")
    @RequestMapping(value = {"/civilAffairs/getMilitaryServiceCaseAction"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoGetMilitaryServiceCaseActionRs getMilitaryServiceCaseAction(@RequestBody GeoGetCivilAffairsCaseActionRq rq, HttpServletRequest request) {
        synchronized (this) {
            GeoGetMilitaryServiceCaseActionRs rs = geoKcgCityExtService.getMilitaryServiceCaseActionByRange(rq);
            return rs;
        }
    } //getMilitaryServiceCaseAction

    /**
     * GEO 20211115 add for 民政局五種服務轉成B流程
     * 提供民政局社政類案件資料查詢(依時間區間)
     *
     * @param rq the rq
     * @return CorrectStatusActionRs rs
     */
    @ApiOperation(value = "提供民政局社政類案件資料查詢(依時間區間)")
    @RequestMapping(value = {"/civilAffairs/getSocialAffairsCaseAction"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoGetSocialAffairsCaseActionRs getSocialAffairsCaseAction(@RequestBody GeoGetCivilAffairsCaseActionRq rq, HttpServletRequest request) {
        synchronized (this) {
            GeoGetSocialAffairsCaseActionRs rs = geoKcgCityExtService.getSocialAffairsCaseActionByRange(rq);
            return rs;
        }
    } //getMilitaryServiceCaseAction

    /**
     * GEO 20220711 add for 智能客服
     * 提供智能客服B流程案件服務清單
     */
    @ApiOperation(value = "提供智能客服B流程案件服務清單")
    @RequestMapping(value = {"/getCaseSetListAction"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoGetCaseSetListActionRs getCaseSetListAction(@RequestBody GeoGetCaseSetListActionRq rq, HttpServletRequest request) {
        GeoGetCaseSetListActionRs rs = geoKcgCityExtService.getCaseSetListActionByKeyWord(rq);
        return rs;
    } //getMilitaryServiceCaseAction

    /**
     * GEO 20220711 add for 智能客服
     * 提供智能客服對應表單欄位
     */
    @ApiOperation(value = "提供智能客服對應表單欄位")
    @RequestMapping(value = {"/getCaseSetFromAction"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoGetCaseSetFormActionRs getCaseSetFromAction(@RequestBody GeoGetCaseSetFormActionRq rq, HttpServletRequest request) {
        GeoGetCaseSetFormActionRs rs = geoKcgCityExtService.getCaseSetFromActionByCaseSetId(rq);
        return rs;
    } //getMilitaryServiceCaseAction


    /**
     * GEO 20220711 add for 智能客服
     * 提供智能客服服務案件入案存檔
     */
    @ApiOperation(value = "提供智能客服服務案件入案存檔")
    @RequestMapping(value = {"/saveAction"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoGetCaseSetSaveActionRs saveAction(@RequestBody GeoGetCaseSetSaveActionRq rq, HttpServletRequest request) {
        GeoGetCaseSetSaveActionRs rs = geoKcgCityExtService.saveAction(rq);
        return rs;
    } //getMilitaryServiceCaseAction

    /**
     * GEO 20220828 add for 智能客服
     * 案件進度查詢
     */
    @ApiOperation(value = "智能客服 案件進度查詢-明細")
    @PostMapping(value = {"/detailAction"}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoCaseProcessDetailRs detailAction(@RequestBody GeoCaseProcessDetailRq rq) {
        GeoCaseProcessDetailRs rs = geoKcgCityExtService.detailAction(rq);
        return rs;
    } //detailAction

    /**
     * 介接本府跨機關數位服務平台 callback
     *
     * @param rq
     */
    @ApiOperation(value = "介接本府跨機關數位服務平台 callback")
    @RequestMapping(value = {"/crossOrganResult"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoCrossOrganResultRs crossOrganResult(@RequestBody GeoCrossOrganResultRq rq) throws Exception {
        LOGGER.info(String.format("crossOrganResult : %s", JSON.toJSONString(rq)));
        String caseId = rq.getCaseId();
        boolean updated = rq.getResult() != null ? rq.getResult() : false;
        if (updated)
            kgoCaseMainRepository.updateCaseMainStatus(caseId, F3.getValue());
        else
            kgoCaseMainRepository.updateCaseMainStatus(caseId, J3.getValue());

        KgoCaseMain kgoCaseMain = kgoCaseMainRepository.findByCaseId(caseId);
        cityCoinAPIService.completed(kgoCaseMain);
        caseHandleService.doNotifyComplete(caseId, kgoCaseMain, new HashMap<>());

        GeoCrossOrganResultRs rs = new GeoCrossOrganResultRs();
        GeoCrossOrganResultViewForm form = new GeoCrossOrganResultViewForm();
        form.setResult(updated);
        rs.setData(form);
        return rs;
    }

//	/** 排程取得民政局黑名單資料 **/
//	@ApiOperation(value = "排程取得民政局黑名單資料")
//	@RequestMapping(value = { "/blackList" }, method = { RequestMethod.POST }, consumes = {
//			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
//	@ResponseBody
//	public void getCivilBlackListByCityApi(HttpServletRequest request, @RequestBody ApiRequest rq) {
//		geoKcgCityExtService.sendPostApiForCity();
//	} //getCivilBlackListByCityApi


    /**
     * 市民支付平台-繳費入帳查詢(測試機)
     **/
    @ApiOperation(value = "市民支付平台-繳費入帳查詢(測試機)")
    @RequestMapping(value = {"/getPaymentRecordDataList"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public void getPaymentRecordDataList(HttpServletRequest request, @RequestBody ApiRequest rq) {
        geoKcgCityExtService.getPaymentRecordDataList();
    } //getCivilBlackListByCityApi

    /**
     * 市民支付平台-繳費導頁至繳費平台(測試機)
     **/
    @ApiOperation(value = "市民支付平台-繳費導頁至繳費平台(測試機)")
    @RequestMapping(value = {"/goToPayWeb"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public void goToPayWeb(HttpServletRequest request, @RequestBody ApiRequest rq) {
        geoKcgCityExtService.goToPayWeb();
    } //getCivilBlackListByCityApi

    /**
     * 市民支付平台-退費資料處理(測試機)post
     **/
    @ApiOperation(value = "市民支付平台-退費資料處理(測試機)")
    @RequestMapping(value = {"/refundMoney"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public void refundMoney(HttpServletRequest request, @RequestBody ApiRequest rq) {
        geoKcgCityExtService.refundMoney();
    } //getCivilBlackListByCityApi


    //	/**便民一路通-跨機關自動入案 post **/
//	@ApiOperation(value = "便民一路通-跨機關自動入案(測試機)")
//	@RequestMapping(value = { "/testOrgan" }, method = { RequestMethod.POST }, consumes = {
//			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
//	@ResponseBody
//	public void testOrgan(HttpServletRequest request, @RequestBody ApiRequest rq) {
//		geoKcgCityExtService.testOrgan();
//	} //getCivilBlackListByCityApi
}
