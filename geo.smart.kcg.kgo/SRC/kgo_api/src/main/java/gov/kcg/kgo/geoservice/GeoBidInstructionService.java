package gov.kcg.kgo.geoservice;

import gov.kcg.kgo.enums.error.CityApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.geoentity.GeoKgoLight;
import gov.kcg.kgo.geomodel.Geo1999ItemsMainModel;
import gov.kcg.kgo.geomodel.GeoKgoLightModel;
import gov.kcg.kgo.georepository.GeoKgoLightRepository;
import gov.kcg.kgo.geoutil.GeoApi1999Properties;
import gov.kcg.kgo.geoviewmodel.frontend.rq.GeoBidInstruction1999AddrRq;
import gov.kcg.kgo.geoviewmodel.frontend.rq.GeoBidInstructionLightRq;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoBidInstruction1999AddrRs;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoBidInstructionLightRs;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoBidInstruction1999AddrViewForm;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoBidInstructionLightViewForm;
import gov.kcg.kgo.util.GeoJsonUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * GEO 20210814 add
 *
 * 便民一路通前台 之 2
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class GeoBidInstructionService extends GeoBaseService {

    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoBidInstructionService.class);

    @Autowired
    private GeoKgoLightRepository geoKgoLightRepository;

    @Autowired
    private GeoApi1999Properties geoApi1999Properties;

    /**
     * 申辦說明頁-1999取得縣市資料
     *
     * @return the list
     */
    public synchronized GeoBidInstruction1999AddrRs get1999CityData() {
        GeoBidInstruction1999AddrRs rs = new GeoBidInstruction1999AddrRs();
        GeoBidInstruction1999AddrViewForm viewForm = new GeoBidInstruction1999AddrViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            List<Geo1999ItemsMainModel> addrList = sendGet1999AddrCityApi();
            viewForm.setAddrList(addrList);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(CityApiError.UNKNOWN_EXCEPTION), e);
        } finally {
            // 設置 成功/失敗訊息.
            setResultMessage(null, rs, error);
        } //try
        return rs;
    } //get1999DistrictDataByCity


    /**
     * 申辦說明頁-依1999縣市代碼取得鄉鎮市區資料
     *
     * @param rq the rq
     * @return the list
     */
    public synchronized GeoBidInstruction1999AddrRs get1999DistrictDataByCity(GeoBidInstruction1999AddrRq rq) {
        GeoBidInstruction1999AddrRs rs = new GeoBidInstruction1999AddrRs();
        GeoBidInstruction1999AddrViewForm viewForm = new GeoBidInstruction1999AddrViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            if (rq.getAddrCode()!=null) {
                List<Geo1999ItemsMainModel> addrList = sendGet1999AddrAreaApi(rq.getAddrCode());
                viewForm.setAddrList(addrList);
            } //if (rq.getAddrCode()!=null)
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(CityApiError.UNKNOWN_EXCEPTION), e);
        } finally {
            // 設置 成功/失敗訊息.
            setResultMessage(rq, rs, error);
        } //try
        return rs;
    } //get1999DistrictDataByCity

    /**
     * 申辦說明頁-依1999鄉鎮市區代碼取得村里資料
     *
     * @param rq the rq
     * @return the list
     */
    public synchronized GeoBidInstruction1999AddrRs get1999VillageDataByDistrict(GeoBidInstruction1999AddrRq rq) {
        GeoBidInstruction1999AddrRs rs = new GeoBidInstruction1999AddrRs();
        GeoBidInstruction1999AddrViewForm viewForm = new GeoBidInstruction1999AddrViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            if (rq.getAddrCode()!=null) {
                List<Geo1999ItemsMainModel> addrList = sendGet1999AddrVillageApi(rq.getAddrCode());
                viewForm.setAddrList(addrList);
            } //if (rq.getAddrCode()!=null)
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(CityApiError.UNKNOWN_EXCEPTION), e);
        } finally {
            // 設置 成功/失敗訊息.
            setResultMessage(rq, rs, error);
        } //try
        return rs;
    } //get1999VillageDataByDistrict

    /**
     * 申辦說明頁-依行政區取得路燈資料
     *
     * @param rq the rq
     * @return the list
     */
    public synchronized GeoBidInstructionLightRs getLightDataByDistrict(GeoBidInstructionLightRq rq) {
        GeoBidInstructionLightRs rs = new GeoBidInstructionLightRs();
        GeoBidInstructionLightViewForm viewForm = new GeoBidInstructionLightViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            if (rq.getDistrictName()!=null) {
                String districtName = URLDecoder.decode(rq.getDistrictName(), "UTF-8");
                List<GeoKgoLight> geoKgoLightList = geoKgoLightRepository.findByLightDistrict(districtName);
                viewForm.setLightList(GeoKgoLightModel.changeListToModel(geoKgoLightList));
            } //if (rq.getDistrictName()!=null)
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(CityApiError.UNKNOWN_EXCEPTION), e);
        } finally {
            // 設置 成功/失敗訊息.
            setResultMessage(rq, rs, error);
        } //try
        return rs;
    } //getLightDataByDistrict

    /**
     * 取得1999地址縣市選單內容
     *
     * @return the list
     */
    private synchronized List<Geo1999ItemsMainModel> sendGet1999AddrCityApi() {
        //ex. https://soweb.kcg.gov.tw/webapi/api/AddrCode/1
        String jsonStr = sendGetApi(geoApi1999Properties.getKcg1999CityUrl()+geoApi1999Properties);
        //LOGGER.info("GeoBidInstructionService sendGet1999AddrCityApi jsonStr: "+jsonStr);
        JSONArray jsonArr = GeoJsonUtil.stringToJsonArray(jsonStr);
        List<Geo1999ItemsMainModel> mainList = Collections.synchronizedList(new ArrayList<>());
        for (int i=0; i<jsonArr.length(); i++) {
            Geo1999ItemsMainModel itemsMain = new Geo1999ItemsMainModel();
            String itemId = ((JSONObject)jsonArr.get(i)).getString("CountyCode");
            itemsMain.setItemId(itemId);
            itemsMain.setItemName(((JSONObject)jsonArr.get(i)).getString("CountyName"));
            mainList.add(itemsMain);
        } //for (int i=0; i<jsonArr.length(); i++)
        //LOGGER.info("GeoBidInstructionService sendGet1999AddrCityApi list size: "+mainList.size());
        //LOGGER.info("GeoBidInstructionService sendGet1999AddrCityApi list item: "+mainList.get(0).getItemName());
        return mainList;
    } //sendGet1999AddrCityApi

    /**
     * 取得1999地址鄉鎮市區選單內容
     *
     * @return the list
     */
    private synchronized List<Geo1999ItemsMainModel> sendGet1999AddrAreaApi(String countryCode) {
        //ex. https://soweb.kcg.gov.tw/webapi/api/AddrCode/2?p1=6400000000&p2=F
        String jsonStr = sendGetApi(geoApi1999Properties.getKcg1999AreaUrl()+countryCode+geoApi1999Properties.getKcg1999UrlSuffix());
        //LOGGER.info("GeoBidInstructionService sendGet1999AddrAreaApi jsonStr: "+jsonStr);
        JSONArray jsonArr = GeoJsonUtil.stringToJsonArray(jsonStr);
        List<Geo1999ItemsMainModel> mainList = Collections.synchronizedList(new ArrayList<>());
        for (int i=0; i<jsonArr.length(); i++) {
            Geo1999ItemsMainModel itemsMain = new Geo1999ItemsMainModel();
            String itemId = ((JSONObject)jsonArr.get(i)).getString("DistrictCode");
            itemsMain.setItemId(itemId);
            itemsMain.setItemName(((JSONObject)jsonArr.get(i)).getString("DistrictName"));
            mainList.add(itemsMain);
        } //for (int i=0; i<jsonArr.length(); i++)
        //LOGGER.info("GeoBidInstructionService sendGet1999AddrAreaApi list size: "+mainList.size());
        //LOGGER.info("GeoBidInstructionService sendGet1999AddrAreaApi list item: "+mainList.get(0).getItemName());
        return mainList;
    } //sendGet1999AddrAreaApi

    /**
     * 取得1999地址村里選單內容
     *
     * @return the list
     */
    private synchronized List<Geo1999ItemsMainModel> sendGet1999AddrVillageApi(String districtCode) {
        //ex. https://soweb.kcg.gov.tw/webapi/api/AddrCode/3?p1=6400100000&p2=F
        String jsonStr = sendGetApi(geoApi1999Properties.getKcg1999VillageUrl()+districtCode+geoApi1999Properties.getKcg1999UrlSuffix());
        //LOGGER.info("GeoBidInstructionService sendGet1999AddrVillageApi jsonStr: "+jsonStr);
        JSONArray jsonArr = GeoJsonUtil.stringToJsonArray(jsonStr);
        List<Geo1999ItemsMainModel> mainList = Collections.synchronizedList(new ArrayList<>());
        for (int i=0; i<jsonArr.length(); i++) {
            Geo1999ItemsMainModel itemsMain = new Geo1999ItemsMainModel();
            String itemId = ((JSONObject)jsonArr.get(i)).getString("RegionCode");
            itemsMain.setItemId(itemId);
            itemsMain.setItemName(((JSONObject)jsonArr.get(i)).getString("RegionName"));
            mainList.add(itemsMain);
        } //for (int i=0; i<jsonArr.length(); i++)
        //LOGGER.info("GeoBidInstructionService sendGet1999AddrVillageApi list size: "+mainList.size());
        //LOGGER.info("GeoBidInstructionService sendGet1999AddrVillageApi list item: "+mainList.get(0).getItemName());
        return mainList;
    } //sendGet1999AddrVillageApi


}
