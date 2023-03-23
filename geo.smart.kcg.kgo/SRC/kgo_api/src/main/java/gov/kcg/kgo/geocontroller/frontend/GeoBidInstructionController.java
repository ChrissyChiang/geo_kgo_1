package gov.kcg.kgo.geocontroller.frontend;

import gov.kcg.kgo.controller.base.BaseController;
import gov.kcg.kgo.geoservice.GeoBidInstructionService;
import gov.kcg.kgo.geoviewmodel.frontend.rq.GeoBidInstruction1999AddrRq;
import gov.kcg.kgo.geoviewmodel.frontend.rq.GeoBidInstructionLightRq;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoBidInstruction1999AddrRs;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoBidInstructionLightRs;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
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
 * GEO 20210814 add
 *
 * 便民一路通前台 API 之 2
 */
@Controller
@RequestMapping("/frontend/bidInstruction")
public class GeoBidInstructionController extends BaseController {

    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoBidInstructionController.class);

    @Autowired
    private GeoBidInstructionService geoBidInstructionService;

    /**
     * 申辦說明頁-1999取得縣市資料
     *
     * @return
     */
    @ApiOperation(value = "申辦說明頁-1999取得縣市資料")
    @RequestMapping(value = { "/get1999CityData" }, method = { RequestMethod.POST }, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public GeoBidInstruction1999AddrRs get1999CityData(@RequestBody ApiRequest apiRequest, HttpServletRequest request) {
        GeoBidInstruction1999AddrRs rs = geoBidInstructionService.get1999CityData();
        return rs;
    } //get1999CityData

    /**
     * 申辦說明頁-依1999縣市代碼取得鄉鎮市區資料
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "申辦說明頁-依1999縣市代碼取得鄉鎮市區資料")
    @RequestMapping(value = { "/get1999DistrictByCity" }, method = { RequestMethod.POST }, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public GeoBidInstruction1999AddrRs get1999DistrictDataByCity(@RequestBody GeoBidInstruction1999AddrRq rq, HttpServletRequest request) {
        GeoBidInstruction1999AddrRs rs = geoBidInstructionService.get1999DistrictDataByCity(rq);
        return rs;
    } //get1999DistrictDataByCity

    /**
     * 申辦說明頁-依1999鄉鎮市區代碼取得村里資料
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "申辦說明頁-依1999鄉鎮市區代碼取得村里資料")
    @RequestMapping(value = { "/get1999VillageByDistrict" }, method = { RequestMethod.POST }, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public GeoBidInstruction1999AddrRs get1999VillageDataByDistrict(@RequestBody GeoBidInstruction1999AddrRq rq, HttpServletRequest request) {
        GeoBidInstruction1999AddrRs rs = geoBidInstructionService.get1999VillageDataByDistrict(rq);
        return rs;
    } //get1999VillageDataByDistrict

    /**
     * 申辦說明頁-依行政區取得路燈資料
     *
     * @param rq
     * @param request
     * @return
     */
    @ApiOperation(value = "申辦說明頁-依行政區取得路燈資料")
    @RequestMapping(value = { "/getLightData" }, method = { RequestMethod.POST }, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public GeoBidInstructionLightRs getLightDataByDistrict(@RequestBody GeoBidInstructionLightRq rq, HttpServletRequest request) {
        GeoBidInstructionLightRs rs = geoBidInstructionService.getLightDataByDistrict(rq);
        return rs;
    } //getLightDataByDistrict


}
