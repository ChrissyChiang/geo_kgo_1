package gov.kcg.kgo.geocontroller.backend;

import com.mchange.v2.cfg.PropertiesConfigSource;
import gov.kcg.kgo.geoservice.GeoCaseSetRentService;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoKgoRentCaseRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoKgoRentTimeQueryRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeokgoRentRq;
import gov.kcg.kgo.geoviewmodel.backend.rs.GeoRentComboBoxRs;
import gov.kcg.kgo.geoviewmodel.backend.rs.GeoRentWeekQueryRs;
import gov.kcg.kgo.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * *後台-服務管理-場地/活動預約 Controller
 */
@Controller
@RequestMapping("/backend/caseSetRent")
@Api(tags = {"geo-caseSetRent-controller 後台-線上場地租借"})
public class GeoCaseSetRentController {

    @Autowired
    GeoCaseSetRentService  geoCaseSetRentService;
    /**
     * 服務案件建立,線上場地租借畫面
     * */
    @ApiOperation(value = "後台-線上場地租借編輯畫面-取得機關與科室預設下拉式選單")
    @RequestMapping(value = {"/siteComboBox"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoRentComboBoxRs getDefaultRentCase(@RequestBody GeokgoRentRq rq ){
        GeoRentComboBoxRs rs = geoCaseSetRentService.getDefaultRentCase(rq);
        return rs;
    }

    @ApiOperation(value = "後台-場地/活動線上預約:編輯畫面年月週查詢")
    @RequestMapping(value = {"/rentWeekQuery"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoRentWeekQueryRs getWeekDate(@RequestBody GeoKgoRentTimeQueryRq rq ){
        GeoRentWeekQueryRs rs = geoCaseSetRentService.queryRentWeek(rq);
        return rs;
    }


    @ApiOperation(value = "後台-場地/活動線上預約:編輯畫面儲存")
    @RequestMapping(value = {"/rentSaveAction"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoRentWeekQueryRs editAndSave(@RequestBody GeoKgoRentCaseRq rq ){
        GeoRentWeekQueryRs rs = geoCaseSetRentService.editAndSave(rq);
        return rs;
    }

    @ApiOperation(value = "後台-場地/活動線上預約:複製上一週的預約時間")
    @RequestMapping(value = {"/copyLastWeek"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoRentWeekQueryRs copyLastWeek(@RequestBody GeoKgoRentTimeQueryRq rq ){
        GeoRentWeekQueryRs rs = geoCaseSetRentService.copyLastWeek(rq);
        return rs;
    }
}
