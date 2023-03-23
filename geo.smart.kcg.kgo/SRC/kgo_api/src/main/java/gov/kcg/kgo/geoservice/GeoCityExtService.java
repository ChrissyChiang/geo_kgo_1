package gov.kcg.kgo.geoservice;

import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.common.frontend.FrontendLoginUserInfo;
import gov.kcg.kgo.dto.Activiti.HistoryDataDto;
import gov.kcg.kgo.dto.CaseCorrectDataDto;
import gov.kcg.kgo.dto.SaCaseViewQueryDto;
import gov.kcg.kgo.enums.backend.*;
import gov.kcg.kgo.enums.common.*;
import gov.kcg.kgo.enums.common.kcgCityApi.KcgCityApiServiceType;
import gov.kcg.kgo.enums.common.sso.LoginAuthTokenType;
import gov.kcg.kgo.enums.error.CaseProcessSearchApiError;
import gov.kcg.kgo.enums.error.CityApiError;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.enums.error.KgoFrontEndApiError;
import gov.kcg.kgo.enums.front.FrontendFunctionCodeEnum;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.geoentity.GeoKgoLight;
import gov.kcg.kgo.geoentity.GeoKgoTaskComment;
import gov.kcg.kgo.geoenum.GeoCivilAffarsiServiceEnum;
import gov.kcg.kgo.geomodel.*;
import gov.kcg.kgo.georepository.GeoKgoTaskCommentRepository;
import gov.kcg.kgo.georepository.custom.GeoCaseSetApplyCountReposCustom;
import gov.kcg.kgo.georepository.custom.GeoKgoCaseQueryReposCustom;
import gov.kcg.kgo.georepository.GeoKgoLightRepository;
import gov.kcg.kgo.geoutil.GeoApiCivilAffairsProperties;
import gov.kcg.kgo.geoutil.GeoStringUtil;
import gov.kcg.kgo.geoutil.GeoApi1999Properties;
import gov.kcg.kgo.geoviewmodel.frontend.rq.*;
import gov.kcg.kgo.geoviewmodel.frontend.rs.*;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.*;
import gov.kcg.kgo.model.*;
import gov.kcg.kgo.repository.*;
import gov.kcg.kgo.service.ActivitiService;
import gov.kcg.kgo.service.impl.CaseFormServiceImpl;
import gov.kcg.kgo.service.impl.helper.CallKcgCityApiServiceHelper;
import gov.kcg.kgo.service.impl.helper.CommonServiceHelper;
import gov.kcg.kgo.service.operationmemo.OperationApiMemo;
import gov.kcg.kgo.service.operationmemo.OperationColumn;
import gov.kcg.kgo.util.*;
import gov.kcg.kgo.viewModel.backend.caseHadle.base.rs.bean.CaseHandleCaseViewCaseHistoryDataGrid;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.bean.*;
import gov.kcg.kgo.viewModel.frontend.caseform.rq.bean.SaveFileViewForm;
import gov.kcg.kgo.viewModel.frontend.caseform.rs.bean.HomeActionViewForm;
import gov.kcg.kgo.viewModel.frontend.caseform.rs.bean.SaveActionViewForm;
import gov.kcg.kgo.viewModel.frontend.caseform.rs.bean.ValidationActionViewForm;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GEO 20210814 add
 * <p>
 * 對外API 之 2
 * 高雄程式資料平台 API Service.
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class GeoCityExtService extends GeoBaseService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoCityExtService.class);

    private CallKcgCityApiServiceHelper callKcgCityApiServiceHelper = CallKcgCityApiServiceHelper.getInstance();

    @Autowired
    private GeoKgoCaseQueryReposCustom geoKgoCaseQueryRepositoryCustom;

    @Autowired
    private GeoApi1999Properties geoApi1999Properties;

    @Autowired
    private GeoApiCivilAffairsProperties geoApiCivilAffairsProperties;

    @Autowired
    private GeoKgoLightRepository geoKgoLightRepository;

    @Autowired
    private KgoCaseMainRepository kgoCaseMainRepository;

    @Autowired
    private KgoKeywordsRepository kgoKeywordsRepository;

    @Autowired
    private GeoCaseSetApplyCountReposCustom geoCaseSetApplyCountReposCustom;

    @Autowired
    private KgoCasesetCheckRepository kgoCasesetCheckRepository;

    @Autowired
    public CaseFormServiceImpl caseFormServiceImpl;

    private CommonServiceHelper commonServiceHelper = CommonServiceHelper.getInstance();

    @Autowired
    private ActivitiService activitiService;

    @Autowired
    private KgoCasesetRepository kgoCasesetRepository;

    @Autowired
    private KgoCaseDetailRepository kgoCaseDetailRepository;

    @Autowired
    private KgoCaseMainResendFlowRepository kgoCaseMainResendFlowRepository;

    @Autowired
    private GeoKgoTaskCommentRepository geoKgoTaskCommentRepository;

    @Autowired
    private KgoCasesetMemoRepository kgoCasesetMemoRepository;

    /**
     * 依時間區間取得1999派工服務案件清單
     *
     * @param rq the rq
     * @return the case id action
     */
    public synchronized GeoGet1999CaseIdActionRs get1999KdActionByRange(GeoGet1999CaseIdActionRq rq) {
        return get1999CaseIdActionByRange(rq, geoApi1999Properties.getKcg1999KdCaseSetName());
    }

    /**
     * 依時間區間取得1999陳情服務案件清單
     *
     * @param rq the rq
     * @return the case id action
     */
    public synchronized GeoGet1999CaseIdActionRs get1999NewActionByRange(GeoGet1999CaseIdActionRq rq) {
        return get1999CaseIdActionByRange(rq, geoApi1999Properties.getKcg1999NewCaseSetName());
    }

    /**
     * 取得1999派工選單內容
     *
     * @return the list
     */
    public synchronized List<Geo1999ItemsMainModel> sendGet1999KdApi() {
        String jsonStr = sendGetApi(geoApi1999Properties.getKcg1999KdServiceUrl());
        //LOGGER.info("GeoKcgCityExtService sendGet1999KdApi jsonStr: "+jsonStr);
        JSONArray jsonArr = GeoJsonUtil.stringToJson(jsonStr).getJSONArray("ApplicationItems");
        List<Geo1999ItemsMainModel> mainList = new ArrayList<>();
        for (int i = 0; i < jsonArr.length(); i++) {
            Geo1999ItemsMainModel itemsMain = new Geo1999ItemsMainModel();
            String itemId = ((JSONObject) jsonArr.get(i)).getString("Kind");
            itemsMain.setItemId(itemId);
            itemsMain.setItemName(((JSONObject) jsonArr.get(i)).getString("KindName"));
            List<Geo1999ItemsSubsModel> subsList = new ArrayList<>();
            JSONArray subArr = ((JSONObject) jsonArr.get(i)).getJSONArray("Items");
            for (int j = 0; j < subArr.length(); j++) {
                Geo1999ItemsSubsModel itemsSubs = new Geo1999ItemsSubsModel();
                itemsSubs.setGroupId(itemId);
                itemsSubs.setItemId(((JSONObject) subArr.get(j)).getString("Item"));
                itemsSubs.setItemName(((JSONObject) subArr.get(j)).getString("ItemName"));
                subsList.add(itemsSubs);
            } //for (int j=0; j<subArr.length(); j++)
            //LOGGER.info("GeoKcgCityExtService sendGet1999KdApi sub list size: "+i+"/"+subsList.size());
            itemsMain.setSubsList(subsList);
            mainList.add(itemsMain);
        } //for (int i=0; i<jsonArr.length(); i++)
        //LOGGER.info("GeoKcgCityExtService sendGet1999KdApi list size: "+mainList.size());
        //LOGGER.info("GeoKcgCityExtService sendGet1999KdApi list item: "+mainList.get(0).getItemName());
        //LOGGER.info("GeoKcgCityExtService sendGet1999KdApi list sub item: "+mainList.get(0).getSubsList().get(0).getItemName());
        return mainList;
    } //sendGet1999KdApi

    /**
     * 取得1999派工陳情內容
     *
     * @return the list
     */
    public synchronized List<Geo1999ItemsMainModel> sendGet1999NewApi() {
        String jsonStr = sendGetApi(geoApi1999Properties.getKcg1999NewServiceUrl());
        //LOGGER.info("GeoKcgCityExtService sendGet1999NewApi jsonStr: "+jsonStr);
        JSONArray jsonArr = GeoJsonUtil.stringToJsonArray(jsonStr);
        List<Geo1999ItemsMainModel> mainList = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < jsonArr.length(); i++) {
            Geo1999ItemsMainModel itemsMain = new Geo1999ItemsMainModel();
            String itemId = ((JSONObject) jsonArr.get(i)).getString("Item");
            itemsMain.setItemId(itemId);
            itemsMain.setItemName(((JSONObject) jsonArr.get(i)).getString("ItemName"));
            List<Geo1999ItemsSubsModel> subsList = new ArrayList<>();
            JSONArray subArr = ((JSONObject) jsonArr.get(i)).getJSONArray("Subitems");
            for (int j = 0; j < subArr.length(); j++) {
                Geo1999ItemsSubsModel itemsSubs = new Geo1999ItemsSubsModel();
                itemsSubs.setGroupId(itemId);
                itemsSubs.setItemId(((JSONObject) subArr.get(j)).getString("Subitem"));
                itemsSubs.setItemName(((JSONObject) subArr.get(j)).getString("SubitemName"));
                subsList.add(itemsSubs);
            } //for (int j=0; j<subArr.length(); j++)
            //LOGGER.info("GeoKcgCityExtService sendGet1999NewApi sub list size: "+i+"/"+subsList.size());
            itemsMain.setSubsList(subsList);
            mainList.add(itemsMain);
        } //for (int i=0; i<jsonArr.length(); i++)
        //LOGGER.info("GeoKcgCityExtService sendGet1999NewApi list size: "+mainList.size());
        //LOGGER.info("GeoKcgCityExtService sendGet1999NewApi list item: "+mainList.get(0).getItemName());
        //LOGGER.info("GeoKcgCityExtService sendGet1999NewApi list sub item: "+mainList.get(0).getSubsList().get(0).getItemName());
        return mainList;
    } //sendGet1999NewApi

    /**
     * 取得1999地址縣市選單內容
     *
     * @return the list
     */
	/*public synchronized List<Geo1999ItemsMainModel> sendGet1999AddrCityApi() {
		//ex. https://soweb.kcg.gov.tw/webapi/api/AddrCode/1
		String jsonStr = sendGetApi(geoApi1999Properties.getKcg1999CityUrl()+geoApi1999Properties);
		//LOGGER.info("GeoKcgCityExtService sendGet1999AddrCityApi jsonStr: "+jsonStr);
		JSONArray jsonArr = GeoJsonUtil.stringToJsonArray(jsonStr);
		List<Geo1999ItemsMainModel> mainList = Collections.synchronizedList(new ArrayList<>());
		for (int i=0; i<jsonArr.length(); i++) {
			Geo1999ItemsMainModel itemsMain = new Geo1999ItemsMainModel();
			String itemId = ((JSONObject)jsonArr.get(i)).getString("CountyCode");
			itemsMain.setItemId(itemId);
			itemsMain.setItemName(((JSONObject)jsonArr.get(i)).getString("CountyName"));
			mainList.add(itemsMain);
		} //for (int i=0; i<jsonArr.length(); i++)
		//LOGGER.info("GeoKcgCityExtService sendGet1999AddrCityApi list size: "+mainList.size());
		//LOGGER.info("GeoKcgCityExtService sendGet1999AddrCityApi list item: "+mainList.get(0).getItemName());
		return mainList;
	} //sendGet1999AddrCityApi*/

    /**
     * 取得1999建議事項高雄行政區選單內容
     *
     * @return the list
     */
    public synchronized List<Geo1999ItemsMainModel> sendGet1999AreaAdviceApi() {
        //ex. https://soweb.kcg.gov.tw/webapi/api/AddrCode/2?p1=6400000000&p2=F
        String jsonStr = sendGetApi(geoApi1999Properties.getKcg1999AreaUrl() + geoApi1999Properties.getKcg1999AreaKgoCode() + geoApi1999Properties.getKcg1999UrlSuffix());
        //LOGGER.info("GeoKcgCityExtService sendGet1999AreaAdviceApi jsonStr: "+jsonStr);
        JSONArray jsonArr = GeoJsonUtil.stringToJsonArray(jsonStr);
        List<Geo1999ItemsMainModel> mainList = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < jsonArr.length(); i++) {
            Geo1999ItemsMainModel itemsMain = new Geo1999ItemsMainModel();
            String itemId = ((JSONObject) jsonArr.get(i)).getString("DistrictCode");
            itemsMain.setItemId(itemId);
            itemsMain.setItemName(((JSONObject) jsonArr.get(i)).getString("DistrictName"));
            mainList.add(itemsMain);
        } //for (int i=0; i<jsonArr.length(); i++)
        //LOGGER.info("GeoKcgCityExtService sendGet1999AreaAdviceApi list size: "+mainList.size());
        //LOGGER.info("GeoKcgCityExtService sendGet1999AreaAdviceApi list item: "+mainList.get(0).getItemName());
        return mainList;
    } //sendGet1999AreaAdviceApi

    /**
     * GEO 20211115 add for 民政局五種服務轉成B流程
     * 取得項高雄行政區選單，不包含無法分區
     *
     * @return the list
     */
    public synchronized List<Geo1999ItemsMainModel> sendGetAreaAdviceApi() {
        //ex. https://soweb.kcg.gov.tw/webapi/api/AddrCode/2?p1=6400000000
        String jsonStr = sendGetApi(geoApi1999Properties.getKcg1999AreaUrl() + geoApi1999Properties.getKcg1999AreaKgoCode());
        //LOGGER.info("GeoKcgCityExtService sendGet1999AreaAdviceApi jsonStr: "+jsonStr);
        JSONArray jsonArr = GeoJsonUtil.stringToJsonArray(jsonStr);
        List<Geo1999ItemsMainModel> mainList = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < jsonArr.length(); i++) {
            Geo1999ItemsMainModel itemsMain = new Geo1999ItemsMainModel();
            String itemId = ((JSONObject) jsonArr.get(i)).getString("DistrictCode");
            itemsMain.setItemId(itemId);
            itemsMain.setItemName(((JSONObject) jsonArr.get(i)).getString("DistrictName"));
            mainList.add(itemsMain);
        } //for (int i=0; i<jsonArr.length(); i++)
        return mainList;
    } //sendGetAreaAdviceApi

    /**
     * 取得1999路燈資訊選單內容
     *
     * @return the list
     */
    public synchronized int setGet1999AddrAdviceApi(String[] postCodeArr) {
        int isSuccess = 0;
        try {
            LOGGER.info("GeoKcgCityExtService sendGet1999AddrAdviceApi start at: " + (new Date()).toString());
            geoKgoLightRepository.deleteAll();
            int lightId = 1;
            for (int i = 0; i < postCodeArr.length; i++) {

                String responseStr = sendGetApi(geoApi1999Properties.getKcg1999AddrAdviceUrl() + postCodeArr[i]);
                String jsonStr = URLDecoder.decode(responseStr, "ISO-8859-1");
                jsonStr = new String(jsonStr.getBytes("ISO-8859-1"), "UTF-8");
                jsonStr = jsonStr.substring(jsonStr.indexOf("[")); //有可能前幾個字為區名
                LOGGER.info("GeoKcgCityExtService sendGet1999AddrAdviceApi postCode/size: " + postCodeArr[i] + "/" + jsonStr.length());
                if (jsonStr != null && jsonStr.length() > 2) {
                    JSONArray jsonArr = GeoJsonUtil.stringToJsonArray(jsonStr);
                    for (int j = 0; j < jsonArr.length(); j++) {
                        JSONObject json = (JSONObject) jsonArr.get(j);
                        //LOGGER.info("GeoKcgCityExtService sendGet1999AddrAdviceApi jsonStr: "+ json.toString());
                        if (json.has("路燈編號")) {
                            GeoKgoLight itemsLight = new GeoKgoLight();
                            String lightNo = json.getString("路燈編號");
                            itemsLight.setLightId(lightId);
                            itemsLight.setLightNo(lightNo);
                            itemsLight.setLightDistrict(json.getString("行政區"));
                            itemsLight.setLightLatitude(json.getString("緯度N"));
                            itemsLight.setLightLongitude(json.getString("經度E"));
                            itemsLight.setLightAddr(json.getString("實際位置"));
                            geoKgoLightRepository.save(itemsLight);
                            //LOGGER.info("GeoKcgCityExtService sendGet1999AddrAdviceApi list item no: "+itemsLight.getLightNo());
                            lightId++;
                        }
                    } //for (int i=0; i<jsonArr.length(); i++)
                } //if (jsonStr!=null && jsonStr.length()>2)
            } //for (int i=0; i<postCodeArr.length; i++)
            LOGGER.info("GeoKcgCityExtService sendGet1999AddrAdviceApi end at: " + (new Date()).toString() + " rows:" + lightId);
            isSuccess = 1;
        } catch (Exception e) {
            LOGGER.error("GeoKcgCityExtService sendGet1999AddrAdviceApi exception: " + e.toString());
        } //try
        return isSuccess;
    } //sendGet1999AddrAdviceApi


    /**
     * 依服務編號取得服務名稱
     *
     * @param caseSetId
     * @return
     */
    public synchronized String getCaseSetNameByCaseSetId(String caseSetId) {
        return geoKgoCaseQueryRepositoryCustom.getCaseSetNameByCaseSetId(caseSetId);
    }


    /**
     * 依時間區間取得1999服務案件清單
     *
     * @param rq          the rq
     * @param caseSetName the caseSetName
     * @return the case id action
     */
    private GeoGet1999CaseIdActionRs get1999CaseIdActionByRange(GeoGet1999CaseIdActionRq rq, String caseSetName) {
        GeoGet1999CaseIdActionRs rs = new GeoGet1999CaseIdActionRs();
        GeoGet1999CaseIdActionViewForm viewForm = new GeoGet1999CaseIdActionViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            String startTime = rq.getStartTime();
            String endTime = rq.getEndTime();
            List<GeoCaseSetModel> geoCaseSetModelList = geoKgoCaseQueryRepositoryCustom.getCaseListByTimeRange(caseSetName, startTime, endTime);

            if (geoCaseSetModelList != null && geoCaseSetModelList.size() > 0) {
                for (int i = 0; i < geoCaseSetModelList.size(); i++) {
                    List<GeoCaseColumnModel> geoCaseColumnModelList =
                            geoKgoCaseQueryRepositoryCustom.getCaseColumnList(geoCaseSetModelList.get(i).getCaseSetId(), geoCaseSetModelList.get(i).getCaseId());
                    for (int j = 0; j < geoCaseColumnModelList.size(); j++) {
                        LOGGER.info("GeoCityExtService get1999CaseIdActionByRange getCaseId/getColumnId: " +
                                geoCaseSetModelList.get(i).getCaseId() + "/" + geoCaseColumnModelList.get(j).getColumnId());

                        //處理 主子項、建議地點、1999地址 回傳資料
                        if (geoCaseColumnModelList.get(j).getColumnId().trim().equals(geoApi1999Properties.getKcg1999MainSub())) {
                            //主子項(ex. 1-人行道、行道樹、路燈/01,2-人行道破損/01)
                            if (geoCaseColumnModelList.get(j).getColumnValue() != null && !geoCaseColumnModelList.get(j).getColumnValue().isEmpty()) {
                                List<GeoKeyValueModel> keyValueModelList = GeoStringUtil.parseKeyValue(geoCaseColumnModelList.get(j).getColumnValue());
                                String mainData = keyValueModelList.get(0).getValueData(); //主項資料
                                String subData = keyValueModelList.get(1).getValueData(); //子項資料
                                if (keyValueModelList.get(0).getKeyData().equals("2")) {
                                    mainData = keyValueModelList.get(1).getValueData();
                                    subData = keyValueModelList.get(0).getValueData();
                                }

                                //主項(保留caseId和columnId)
                                geoCaseColumnModelList.get(j).setColumnName(mainData.substring(0, mainData.indexOf("/")));
                                geoCaseColumnModelList.get(j).setColumnValue(mainData.substring(mainData.indexOf("/") + 1));

                                //子項
                                GeoCaseColumnModel subModel = new GeoCaseColumnModel();
                                subModel.setCaseId(geoCaseColumnModelList.get(j).getCaseId());
                                subModel.setColumnId("App_No2");
                                subModel.setColumnName(subData.substring(0, subData.indexOf("/")));
                                subModel.setColumnValue(subData.substring(subData.indexOf("/") + 1));
                                geoCaseColumnModelList.add(j + 1, subModel);
                            } //if (geoCaseColumnModelList.get(j).getColumnValue()!=null..
                        } else if (geoCaseColumnModelList.get(j).getColumnId().trim().equals(geoApi1999Properties.getKcg1999AdviceLocation())) {
                            //建議地點(ex. Lat-120.30614051359849,Lon-22.632502312178172,addr-800台灣高雄是新興區忠孝一路298號)
                            if (geoCaseColumnModelList.get(j).getColumnValue() != null && !geoCaseColumnModelList.get(j).getColumnValue().isEmpty()) {
                                List<GeoKeyValueModel> keyValueModelList = GeoStringUtil.parseKeyValue(geoCaseColumnModelList.get(j).getColumnValue());
                                for (int k = 0; k < keyValueModelList.size(); k++) {
                                    String keyData = keyValueModelList.get(k).getKeyData();
                                    String valueData = keyValueModelList.get(k).getValueData();
                                    if (keyData.toUpperCase().equals("ADDR")) {
                                        //建議地點(保留caseId和columnId)
                                        geoCaseColumnModelList.get(j).setColumnName("建議地點");
                                        geoCaseColumnModelList.get(j).setColumnValue(valueData);
                                    } else if (keyData.toUpperCase().equals("LON")) {
                                        //經度座標
                                        GeoCaseColumnModel model = new GeoCaseColumnModel();
                                        model.setCaseId(geoCaseColumnModelList.get(j).getCaseId());
                                        model.setColumnId("EventLongitude");
                                        model.setColumnName("經度座標");
                                        model.setColumnValue(valueData);
                                        geoCaseColumnModelList.add(j + 1, model);
                                    } else if (keyData.toUpperCase().equals("LAT")) {
                                        //緯度座標
                                        GeoCaseColumnModel model = new GeoCaseColumnModel();
                                        model.setCaseId(geoCaseColumnModelList.get(j).getCaseId());
                                        model.setColumnId("EventLatitude");
                                        model.setColumnName("緯度座標");
                                        model.setColumnValue(valueData);
                                        geoCaseColumnModelList.add(j + 1, model);
                                    }
                                } //for (int k=0; k<keyValueModelList.size(); k++)
                            } //if (geoCaseColumnModelList.get(j).getColumnValue()!=null..
                        } else if (geoCaseColumnModelList.get(j).getColumnId().trim().equals(geoApi1999Properties.getKcg1999AddrCity())) {
                            //1999地址(ex. city-高雄市/6400000000,dist-鹽埕區/6400100000,vill-藍橋里/6400100001,addr-忠孝一路298號)
                            if (geoCaseColumnModelList.get(j).getColumnValue() != null && !geoCaseColumnModelList.get(j).getColumnValue().isEmpty()) {
                                List<GeoKeyValueModel> keyValueModelList = GeoStringUtil.parseKeyValue(geoCaseColumnModelList.get(j).getColumnValue());
                                for (int k = 0; k < keyValueModelList.size(); k++) {
                                    String keyData = keyValueModelList.get(k).getKeyData();
                                    String valueData = keyValueModelList.get(k).getValueData();
                                    if (keyData.toUpperCase().equals("CITY")) {
                                        //縣市(保留caseId和columnId)
                                        geoCaseColumnModelList.get(j).setColumnName(valueData.substring(0, valueData.indexOf("/")));
                                        geoCaseColumnModelList.get(j).setColumnValue(valueData.substring(valueData.indexOf("/") + 1));
                                    } else if (keyData.toUpperCase().equals("DIST")) {
                                        //鄉鎮市區
                                        GeoCaseColumnModel model = new GeoCaseColumnModel();
                                        model.setCaseId(geoCaseColumnModelList.get(j).getCaseId());
                                        model.setColumnId("Sugg_Addr2");
                                        model.setColumnName(valueData.substring(0, valueData.indexOf("/")));
                                        model.setColumnValue(valueData.substring(valueData.indexOf("/") + 1));
                                        geoCaseColumnModelList.add(j + 1, model);
                                    } else if (keyData.toUpperCase().equals("VILL")) {
                                        //村里
                                        GeoCaseColumnModel model = new GeoCaseColumnModel();
                                        model.setCaseId(geoCaseColumnModelList.get(j).getCaseId());
                                        model.setColumnId("Sugg_Addr3");
                                        model.setColumnName(valueData.substring(0, valueData.indexOf("/")));
                                        model.setColumnValue(valueData.substring(valueData.indexOf("/") + 1));
                                        geoCaseColumnModelList.add(j + 1, model);
                                    } else if (keyData.toUpperCase().equals("ADDR")) {
                                        //地址
                                        GeoCaseColumnModel model = new GeoCaseColumnModel();
                                        model.setCaseId(geoCaseColumnModelList.get(j).getCaseId());
                                        model.setColumnId("Sugg_Addr4");
                                        model.setColumnName("陳情人地址");
                                        model.setColumnValue(valueData);
                                        geoCaseColumnModelList.add(j + 1, model);
                                    }
                                } //for (int k=0; k<keyValueModelList.size(); k++)
                            } //if (geoCaseColumnModelList.get(j).getColumnValue()!=null..
                        } //if (geoCaseColumnModelList.get(j)..
                    } //for (int j=0; j<geoCaseColumnModelList.size(); j++)
                    geoCaseSetModelList.get(i).setCaseColumns(geoCaseColumnModelList);

                    //處理附件
                    for (int j = 0; j < geoCaseColumnModelList.size(); j++) {
                        if (geoCaseColumnModelList.get(j).getColumnId().equals(geoApi1999Properties.getKcg1999Attachment())) {

                            String fileName = geoCaseColumnModelList.get(j).getColumnValue();
                            if (fileName != null && !fileName.equals("")) {
                                String fullPath = KgoUtil.getCaseDownloadUploadBasePath(geoCaseColumnModelList.get(j).getCaseId());
                                String base64Str = "";
                                File file = new File(fullPath + fileName);
                                if (file != null && file.exists())
                                    base64Str = GeoCryptUtil.encodeFileToBase64Binary(file);

                                SaveFileViewForm form = new SaveFileViewForm();
                                form.setFileName(fileName);
                                form.setFileBase64Str(base64Str);
                                List<SaveFileViewForm> formList = new ArrayList<>();
                                formList.add(form);
                                geoCaseSetModelList.get(i).setDataCount(1);
                                geoCaseSetModelList.get(i).setDataList(formList);
                            } //if (fileName!=null && !fileName.equals(""))
                        } //if...
                    } //for (int j=0; j<geo1999CaseColumnDtoList.size(); j++)
                } //for (int i=0; i<geo1999CaseSetDtoList.size(); i++)
                viewForm.setCaseList(geoCaseSetModelList);
            } //if (geoCaseSet1999DtoList!=null && geoCaseSet1999DtoList.size()>0)
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
    } //get1999CaseIdActionByRange

    public GeoMoicaDataQueryRs getMoicaDataByCaseId(GeoMoicaDataQueryRq rq) {
        GeoMoicaDataQueryRs rs = new GeoMoicaDataQueryRs();
        GeoMoicaDataViewForm viewForm = new GeoMoicaDataViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            String caseId = rq.getCaseId();
            String caseUserId = "";
            String caseUserName = "";
            String base64IvStr = "";
            KgoCaseMain caseMain = kgoCaseMainRepository.findByCaseId(caseId);
            if (caseMain != null && StringUtils.isNotBlank(caseMain.getAccount()) && StringUtils.isNotBlank(caseMain.getApplyUserName())
                    && StringUtils.isNotBlank(caseMain.getApplyUserLoginType()) && caseMain.getApplyUserLoginType().equals(LoginAuthTokenType.MOICA.getType())) {
                String ivStr = GeoStringUtil.generateIvStr();
                base64IvStr = GeoStringUtil.encodeIvStrToBase64(ivStr);
                caseUserId = GeoStringUtil.aesEncrypt(caseMain.getAccount(), geoApiCivilAffairsProperties.getKgoCivilAffairsAesKey(), GeoStringUtil.generateIv(ivStr));
                caseUserName = GeoStringUtil.aesEncrypt(caseMain.getApplyUserName(), geoApiCivilAffairsProperties.getKgoCivilAffairsAesKey(), GeoStringUtil.generateIv(ivStr));
            } //if (caseMain != null)
            viewForm.setCaseUserId(caseUserId);
            viewForm.setCaseUserName(caseUserName);
            viewForm.setIvStr(base64IvStr);
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
    } //getMoicaDataByCaseId

    /**
     * GEO 20211115 add for 民政局五種服務轉成B流程
     * 提供民政局兵役類案件資料查詢(依時間區間)
     *
     * @param rq
     * @return
     */
    public GeoGetMilitaryServiceCaseActionRs getMilitaryServiceCaseActionByRange(GeoGetCivilAffairsCaseActionRq rq) {
        GeoGetMilitaryServiceCaseActionRs rs = new GeoGetMilitaryServiceCaseActionRs();
        GeoMilitaryServiceCaseViewForm viewForm = new GeoMilitaryServiceCaseViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            String startTime = rq.getStartTime();
            String endTime = rq.getEndTime();
            String categoryID = rq.getCategoryID();
            String caseSetName = null;
            String ivStr = GeoStringUtil.generateIvStr(); //AES加密用
            String base64IvStr = GeoStringUtil.encodeIvStrToBase64(ivStr);
            GeoCivilAffarsiServiceEnum[] enums = GeoCivilAffarsiServiceEnum.values();
            for (GeoCivilAffarsiServiceEnum serviceEnum : enums) {
                if (serviceEnum.getValue().equals(categoryID)) caseSetName = serviceEnum.getLabel();
            }
            List<GeoMilitaryServiceCaseModel> militaryServiceCaseModelList = new ArrayList<>();
            List<GeoCivilAffairsDtoModel> geoCaseSetModelList = geoKgoCaseQueryRepositoryCustom.getMilitaryServiceCaseListByTimeRange(caseSetName, startTime, endTime);
            if (geoCaseSetModelList != null && geoCaseSetModelList.size() > 0) {
                for (int i = 0; i < geoCaseSetModelList.size(); i++) {
                    GeoMilitaryServiceCaseModel model = new GeoMilitaryServiceCaseModel();
                    model.setKcgCaseNo(geoCaseSetModelList.get(i).getCaseId());
                    model.setCreateDate(geoCaseSetModelList.get(i).getApplyDate());
                    model.setIdentityType(0);

                    List<GeoCaseColumnModel> geoCaseColumnModelList = geoKgoCaseQueryRepositoryCustom.getCaseColumnList(geoCaseSetModelList.get(i).getCaseSetId(), geoCaseSetModelList.get(i).getCaseId());
                    for (int j = 0; j < geoCaseColumnModelList.size(); j++) {
                        LOGGER.info("GeoCityExtService getMilitaryServiceCaseActionByRange getCaseId/getColumnId: " + geoCaseSetModelList.get(i).getCaseId() + "/" + geoCaseColumnModelList.get(j).getColumnId());
                        String columnId = geoCaseColumnModelList.get(j).getColumnId().trim();
                        String columnValue = geoCaseColumnModelList.get(j).getColumnValue();
                        if (columnId.equalsIgnoreCase(ColumnTypeEnum.AGENT.getValue())) {
                            List<GeoKeyValueModel> keyValueModelList = GeoStringUtil.parseKeyValue(columnValue);
                            for (int k = 0; k < keyValueModelList.size(); k++) {
                                String keyData = keyValueModelList.get(k).getKeyData();
                                String valueData = keyValueModelList.get(k).getValueData();
                                if (keyData.equalsIgnoreCase("agent")) {
                                    if (valueData.equals("本人")) {
                                        model.setAgent(false);
                                    } else if (valueData.equals("委託人")) {
                                        model.setAgent(true);
                                    }
                                } else if (keyData.equalsIgnoreCase("agentName")) {
                                    model.setAgentName(encodeStr(valueData, ivStr));
                                } else if (keyData.equalsIgnoreCase("agentId")) {
                                    model.setAgentIdentityID(encodeStr(valueData, ivStr));
                                }
                            } //for (int k=0; k<keyValueModelList.size(); k++)
                        } else if (columnId.equalsIgnoreCase("Name")) model.setName(encodeStr(columnValue, ivStr));
                        else if (columnId.equalsIgnoreCase("ID")) model.setIdentityID(encodeStr(columnValue, ivStr));
                        else if (columnId.equalsIgnoreCase("CellPhone")) model.setTel(encodeStr(columnValue, ivStr));
                        else if (columnId.equalsIgnoreCase(geoApiCivilAffairsProperties.getEmail()))
                            model.setEmail(encodeStr(columnValue, ivStr));
                        else if (columnId.equalsIgnoreCase(geoApiCivilAffairsProperties.getBirthday())) {
                            String birthStr = DateUtil.convertTWDate(columnValue, DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH, DateUtil.PATTEN_YEAR_MONTH_DAY_TW);
                            model.setBirthday(birthStr);
                        } else if (columnId.equalsIgnoreCase(geoApiCivilAffairsProperties.getResidentArea())) {
                            if (!columnValue.contains("南沙諸島")) model.setResidentArea(columnValue.substring(0, 6));
                            else model.setResidentArea(columnValue.substring(0, 7));
                        } else if (columnId.equalsIgnoreCase(geoApiCivilAffairsProperties.getApplyReason()))
                            model.setApplyReason(columnValue);
                        else if (columnId.equalsIgnoreCase(ColumnTypeEnum.KAOHSIUNG_DISTRICT_OFFICE.getValue())) {
//						    //拿到資料為：6400100000、6401000000兩種，需改成64000010、64000100
                            Boolean isContain = columnValue.contains("6400");
                            StringBuilder code = new StringBuilder(columnValue);
                            if (isContain) {
                                code.insert(4, "00");
                            } else {
                                code.insert(2, "00");
                            }
                            code.delete(7, code.length() - 1);
                            model.setDistrictLocaleNum(code.toString());
                        } else if (columnId.equalsIgnoreCase(geoApiCivilAffairsProperties.getObverseIDCard())) {
                            model.setObverseIDCard(changeFileToStr(columnValue, geoCaseColumnModelList.get(j).getCaseId(), ivStr));
                            model.setObverseIDCardFormat(getFileType(columnValue));/** GEO 20211230 add 增加檔案格式 */
                        } else if (columnId.equalsIgnoreCase(geoApiCivilAffairsProperties.getReverseIDCard())) {
                            model.setReverseIDCard(changeFileToStr(columnValue, geoCaseColumnModelList.get(j).getCaseId(), ivStr));
                            model.setReverseIDCardFormat(getFileType(columnValue));
                        } else if (columnId.equalsIgnoreCase(geoApiCivilAffairsProperties.getPOA())) {
                            model.setPOA(changeFileToStr(columnValue, geoCaseColumnModelList.get(j).getCaseId(), ivStr));
                        }
                    } //for (int i=0; i<geo1999CaseSetDtoList.size(); i++)
                    model.setIvStr(base64IvStr);
                    LOGGER.info("base64IvStr" + base64IvStr);
                    militaryServiceCaseModelList.add(model);
                } //for (int i = 0; i< geoCaseSetModelList.size(); i++)
            } //if (geoCaseSetModelList !=null...
            viewForm.setCaseList(militaryServiceCaseModelList);
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
    } //getMilitaryServiceCaseActionByRange

    /**
     * GEO 20211115 add for 民政局五種服務轉成B流程
     * 提供民政局兵役類案件資料查詢(依時間區間)
     *
     * @param rq
     * @return
     */
    public GeoGetSocialAffairsCaseActionRs getSocialAffairsCaseActionByRange(GeoGetCivilAffairsCaseActionRq rq) {
        GeoGetSocialAffairsCaseActionRs rs = new GeoGetSocialAffairsCaseActionRs();
        GeoSocialAffairsCaseViewForm viewForm = new GeoSocialAffairsCaseViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            String startTime = rq.getStartTime();
            String endTime = rq.getEndTime();
            String categoryID = rq.getCategoryID();
            String caseSetName = null;
            String ivStr = GeoStringUtil.generateIvStr();
            String base64IvStr = GeoStringUtil.encodeIvStrToBase64(ivStr);
            GeoCivilAffarsiServiceEnum[] enums = GeoCivilAffarsiServiceEnum.values();
            for (GeoCivilAffarsiServiceEnum serviceEnum : enums) {
                if (serviceEnum.getValue().equals(categoryID)) caseSetName = serviceEnum.getLabel();
            }
            List<GeoSocialAffairsCaseModel> socialServiceCaseModelList = new ArrayList<>();
            List<GeoCivilAffairsDtoModel> geoCaseSetModelList = geoKgoCaseQueryRepositoryCustom.getMilitaryServiceCaseListByTimeRange(caseSetName, startTime, endTime);
            if (geoCaseSetModelList != null && geoCaseSetModelList.size() > 0) {
                for (int i = 0; i < geoCaseSetModelList.size(); i++) {
                    GeoSocialAffairsCaseModel model = new GeoSocialAffairsCaseModel();
                    model.setKcgCaseNo(geoCaseSetModelList.get(i).getCaseId());
                    model.setCreateDate(geoCaseSetModelList.get(i).getApplyDate());
                    model.setIdentityType(0);

                    List<GeoCaseColumnModel> geoCaseColumnModelList = geoKgoCaseQueryRepositoryCustom.getCaseColumnList(geoCaseSetModelList.get(i).getCaseSetId(), geoCaseSetModelList.get(i).getCaseId());
                    for (int j = 0; j < geoCaseColumnModelList.size(); j++) {
                        LOGGER.info("GeoCityExtService getMilitaryServiceCaseActionByRange getCaseId/getColumnId: " + geoCaseSetModelList.get(i).getCaseId() + "/" + geoCaseColumnModelList.get(j).getColumnId());
                        String columnId = geoCaseColumnModelList.get(j).getColumnId().trim();
                        String columnValue = geoCaseColumnModelList.get(j).getColumnValue();
                        if (columnId.equalsIgnoreCase(ColumnTypeEnum.AGENT.getValue())) {
                            List<GeoKeyValueModel> keyValueModelList = GeoStringUtil.parseKeyValue(columnValue);
                            for (int k = 0; k < keyValueModelList.size(); k++) {
                                String keyData = keyValueModelList.get(k).getKeyData();
                                String valueData = keyValueModelList.get(k).getValueData();
                                if (keyData.equalsIgnoreCase("agent")) {
                                    if (valueData.equals("本人")) {
                                        model.setAgent(false);
                                    } else if (valueData.equals("委託人")) {
                                        model.setAgent(true);
                                    }
                                } else if (keyData.equalsIgnoreCase("agentName")) {
                                    model.setAgentName(encodeStr(valueData, ivStr));
                                } else if (keyData.equalsIgnoreCase("agentId")) {
                                    model.setAgentIdentityID(encodeStr(valueData, ivStr));
                                }
                            } //for (int k=0; k<keyValueModelList.size(); k++)
                        } else if (columnId.equalsIgnoreCase("Name")) model.setName(encodeStr(columnValue, ivStr));
                        else if (columnId.equalsIgnoreCase("ID")) model.setIdentityID(encodeStr(columnValue, ivStr));
                        else if (columnId.equalsIgnoreCase("CellPhone")) model.setTel(encodeStr(columnValue, ivStr));
                        else if (columnId.equalsIgnoreCase(geoApiCivilAffairsProperties.getEmail()))
                            model.setEmail(encodeStr(columnValue, ivStr));
                        else if (columnId.equalsIgnoreCase(geoApiCivilAffairsProperties.getIsReceivedSubsidy()))
                            model.setReceivedSubsidy(columnValue.equals("1"));
                        else if (columnId.equalsIgnoreCase(geoApiCivilAffairsProperties.getIsLowIncome()))
                            model.setLowIncome(columnValue.equals("1"));
                        else if (columnId.equalsIgnoreCase(ColumnTypeEnum.KAOHSIUNG_DISTRICT_OFFICE.getValue())) {
                            //拿到資料會是XXX區公所-區碼，需改成區碼回傳
                            int index = columnValue.indexOf("-");
                            if (index > -1) {
                                String valueData = columnValue.substring(index + 1, columnValue.length());
                                model.setDistrictLocaleNum(valueData);
                            }//if (index>-1)
                        } else if (columnId.equalsIgnoreCase(geoApiCivilAffairsProperties.getObverseIDCard())) {
                            model.setObverseIDCard(changeFileToStr(columnValue, geoCaseColumnModelList.get(j).getCaseId(), ivStr));
                            model.setObverseIDCardFormat(getFileType(columnValue));/** GEO 20211230 add 增加檔案格式 */
                        } else if (columnId.equalsIgnoreCase(geoApiCivilAffairsProperties.getReverseIDCard())) {
                            model.setReverseIDCard(changeFileToStr(columnValue, geoCaseColumnModelList.get(j).getCaseId(), ivStr));
                            model.setReverseIDCardFormat(getFileType(columnValue));
                        }
                    } //for (int i=0; i<geo1999CaseSetDtoList.size(); i++)
                    model.setIvStr(base64IvStr);
                    LOGGER.info("base64IvStr" + base64IvStr);
                    socialServiceCaseModelList.add(model);
                } //for (int i = 0; i< geoCaseSetModelList.size(); i++)
            } //if (geoCaseSetModelList !=null...
            viewForm.setCaseList(socialServiceCaseModelList);
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
    } //getSocialAffairsCaseActionByRange

    private String encodeStr(String originalStr, String ivStr) {
        String encodeStr = StringUtils.EMPTY;
        if (StringUtils.isNotBlank(originalStr)) {
            encodeStr = GeoStringUtil.aesEncrypt(originalStr, geoApiCivilAffairsProperties.getKgoCivilAffairsAesKey(), GeoStringUtil.generateIv(ivStr));
        }
        return encodeStr;
    } //changeFileToStr

    private String changeFileToStr(String fileName, String caseId, String ivStr) throws IOException {
        String fileAes = "";
        if (fileName != null && !fileName.equals("")) {
            String fullPath = KgoUtil.getCaseDownloadUploadBasePath(caseId);
            File file = new File(fullPath + fileName);
            if (file != null && file.exists()) {
                String base64Str = GeoCryptUtil.encodeFileToBase64Binary(file);
                LOGGER.info("base64Str" + base64Str);
                fileAes = GeoStringUtil.aesEncrypt(base64Str, geoApiCivilAffairsProperties.getKgoCivilAffairsAesKey(), GeoStringUtil.generateIv(ivStr));
                LOGGER.info("fileAes" + fileAes);
                LOGGER.info("ivStr" + ivStr);

            }
        }
        return fileAes;
    } //changeFileToStr

    /**
     * GEO 20211230 add 增加檔案格式
     */
    private String getFileType(String fileName) {
        String fileType = "";
        if (StringUtils.isNotBlank(fileName) && fileName.contains(".")) {
            int dot = fileName.lastIndexOf(".");
            fileType = fileName.substring(dot + 1);
        }
        return fileType;
    }//getFileType


    /**
     * GEO 20220711 add for 智能客服
     * 提供智能客服案件服務清單
     *
     * @param rq
     * @return
     */
    public GeoGetCaseSetListActionRs getCaseSetListActionByKeyWord(GeoGetCaseSetListActionRq rq) {
        LOGGER.info("GeoCityExtService getCaseSetListActionByKeyWord rq.getGstrKeyword()=" + rq.getGstrKeyword());
        GeoGetCaseSetListActionRs rs = new GeoGetCaseSetListActionRs();
        GeoCaseSetListViewForm geoCaseSetListViewForm = new GeoCaseSetListViewForm();
        rs.setData(geoCaseSetListViewForm);
        KgoApiException error = null;
        OperationApiMemo memo = null;
        try {
            // 前台，熱門搜尋
            memo = super.genOperationMemo(SystemTypeEnum.F, null, FrontendFunctionCodeEnum.Eservice);

            //紀錄keyword
            String gstrKeyword = rq.getGstrKeyword();
            if (StringUtils.isEmpty(gstrKeyword)) gstrKeyword = "";
            KgoKeywords kgoKeywords = new KgoKeywords();
            try {
                FrontendLoginUserInfo frontendLoginUser = KgoUtil.getFrontendLoginUser();
                kgoKeywords.setCreateUser(frontendLoginUser.getName());
            } catch (Exception e) {
                LOGGER.info("GeoCityExtService getCaseSetListActionByKeyWord user not login");
            } //try
            kgoKeywords.setSeqGUID(UUID.randomUUID().toString());
            kgoKeywords.setKeyword(gstrKeyword);
            kgoKeywords.setCreateTime(DateUtil.getCurrentTimestamp());
            kgoKeywordsRepository.save(kgoKeywords);
            //找B1、B2、B3流程的服務案件
            List<GeoCaseSetListDtoModel> caseListByKeyWordList = geoKgoCaseQueryRepositoryCustom.getCaseListByKeyWord(gstrKeyword);
            LOGGER.info("GeoCityExtService getCaseSetListActionByKeyWord caseListByKeyWordList.size=" + caseListByKeyWordList.size());
            caseListByKeyWordList.stream().map(item -> {
//                geoCaseSetListDtoModel.setServiceHtml(item.getServiceHtml());
                item.setMyDataTypeActive(false);
                /** GEO 20211224 add 調整搜尋結果頁面 */
                List<KgoCasesetType> typeList = geoCaseSetApplyCountReposCustom.getCaseSetApplyType(item.getCaseSetId());
                if (typeList != null) {
                    boolean isApplyTypeCActive = false;
                    boolean isApplyTypeEActive = false;
                    boolean isApplyTypeLActive = false;
                    for (KgoCasesetType entity : typeList) {
                        String applyType = StringUtils.isBlank(entity.getId().getApplyType()) ? StringUtils.EMPTY : entity.getId().getApplyType();
                        if (applyType.contains(ApplyTypeEnum.C.getValue())) isApplyTypeCActive = true;
                        if (applyType.contains(ApplyTypeEnum.E.getValue())) isApplyTypeEActive = true;
                        if (applyType.contains(ApplyTypeEnum.L.getValue())) isApplyTypeLActive = true;
                    } //for (KgoCasesetType entity: typeList)
                    item.setApplyTypeCActive(isApplyTypeCActive);
                    item.setApplyTypeEActive(isApplyTypeEActive);
                    item.setApplyTypeLActive(isApplyTypeLActive);
                } //if (typeList != null)

                /** GEO 20220725 add 驗證案件申請人登入 */
                List<KgoCasesetCheck> checkTypeList = kgoCasesetCheckRepository.findAllByIdCaseSetId(item.getCaseSetId());
                item.setCheckApplyUserTypes(checkTypeList.stream().map(x -> x.getId().getCheckType()).collect(Collectors.toList()));
                return item;
            }).collect(Collectors.toList());

            //該案件使否有使用Mydata
            List<KgoCasesetColumnPK>  kgoCasesetColumnPKS = geoKgoCaseQueryRepositoryCustom.getCaseSetListUseMyDataId();
            if (kgoCasesetColumnPKS != null){
                for (KgoCasesetColumnPK pk:kgoCasesetColumnPKS){
                    for (GeoCaseSetListDtoModel model:caseListByKeyWordList){
                        if (pk.getCaseSetId().equals(model.getCaseSetId())){
                            model.setMyDataTypeActive(true);
                            continue;
                        } //if (pk.getCaseSetId().equals(model.getCaseSetId()))
                    } //for (GeoCaseSetListDtoModel model:hotSearchQueryDataGrids)
                } //for (KgoCasesetColumnPK pk:kgoCasesetColumnPKS)
            } // if (kgoCasesetColumnPKS != null)

            geoCaseSetListViewForm.setCaseSetList(caseListByKeyWordList);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(CityApiError.UNKNOWN_EXCEPTION), e);
        } finally {
            /** === 儲存操作紀錄 === */
            List<OperationColumn> memoList = new ArrayList<>();
            memoList.add(new OperationColumn(null, rq.getGstrKeyword()));
            memo.setMemoList(memoList);
            super.saveOperationLog(memo);
            /** === 儲存操作紀錄 === */
            // 設置 成功/失敗訊息.
            setResultMessage(rq, rs, error);
        } //try
        return rs;
    } //getCaseSetListActionByKeyWord


    /**
     * GEO 20220711 add for 智能客服
     * 提供智能客服對應表單欄位
     *
     * @param rq
     * @return
     */
    public GeoGetCaseSetFormActionRs getCaseSetFromActionByCaseSetId(GeoGetCaseSetFormActionRq rq) {
        LOGGER.info("GeoCityExtService getCaseSetFromActionByCaseSetId rq.getCaseSetId()=" + rq.getCaseSetId());
        GeoGetCaseSetFormActionRs geoGetCaseSetFormActionRs = new GeoGetCaseSetFormActionRs();
        HomeActionViewForm homeActionViewForm = new HomeActionViewForm();
        try {
            KgoCaseset kgoCaseset = caseFormServiceImpl.getKgoCaseset(rq.getCaseSetId());

            // 已下架:此服務已下架‧，請選擇其他服務
            if (commonServiceHelper.getCaseSetStatusOff(kgoCaseset.getCaseSetId(), kgoCaseset.getStatus())) {
                geoGetCaseSetFormActionRs.setError(new ErrorResult(KgoFrontEndApiError.SERVICE_IS_NOT_PROVIDED));
                return geoGetCaseSetFormActionRs;
            } //if...

            // 檢核驗證方式式
            Boolean checkLoginBoolean = getCaseSetLoginCheck(rq.getCaseSetId(),rq.getLoginType());
            if (!checkLoginBoolean) {
                geoGetCaseSetFormActionRs.setError(new ErrorResult(KgoFrontEndApiError.PERMISSION_DENIED));
                return geoGetCaseSetFormActionRs;
            } //if (!checkLoginBoolean)

            homeActionViewForm = caseFormServiceImpl.generateHomeActionViewForm(rq.getCaseSetId());

            //部分欄位不需要設null
            homeActionViewForm.setCaseFlowType(null); //不影響案件入案，在搜尋案件時已提供過此參數
            homeActionViewForm.setCaseType(null); //不影響入案，在搜尋案件時已提供過此參數
            homeActionViewForm.setAcceptSet(null); //不影響案件入案，在搜尋案件時已提供過此參數
            homeActionViewForm.setCaseOrganComboBox(null); //目前看回傳都是空的，所以先null

            geoGetCaseSetFormActionRs.setData(homeActionViewForm);

            return geoGetCaseSetFormActionRs;
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("homeAction error " + e.getMessage(), e);
        } //try
    }//getCaseSetFromActionByCaseSetId

    public Boolean getCaseSetLoginCheck(String caseSetId,String loginType) {
        LOGGER.info("getCaseSetLoginCheck caseSetId="+caseSetId);
        LOGGER.info("getCaseSetLoginCheck loginType="+loginType);
        Boolean checkBoolean = false;
        List<KgoCasesetCheck> checkTypeList = kgoCasesetCheckRepository.findAllByIdCaseSetId(caseSetId);
        if (ObjectUtils.isNotEmpty(checkTypeList) && checkTypeList.size() > 0) {
            for (KgoCasesetCheck kgoCasesetCheck : checkTypeList) {
                CheckTypeEnum checkTypeEnum = CheckTypeEnum.getEnum(kgoCasesetCheck.getId().getCheckType());
                if (ObjectUtils.isNotEmpty(checkTypeEnum) && ObjectUtils.isNotEmpty(checkTypeEnum.getLoginAuthType())) {
                    if (ObjectUtils.isNotEmpty(loginType)) {
                        if (loginType.equals(checkTypeEnum.getLoginAuthType().getType())) {
                            LOGGER.info("getCaseSetLoginCheck checkBoolean  1");
                            checkBoolean = true;
                        } else {
                            LOGGER.info("getCaseSetLoginCheck checkBoolean  2");
                            checkBoolean = false;
                        }
                        //只要符合其中一個就可以
                        if (checkBoolean){
                            return true;
                        }
                    } //if (ObjectUtils.isNotEmpty(loginType)
                } else {
                    if (checkTypeEnum==null || checkTypeEnum.getValue().equals("NAN")) {
                        LOGGER.info("getCaseSetLoginCheck checkBoolean  3");
                        checkBoolean = true;
                        return checkBoolean;
                    }
                } //if (ObjectUtils.isNotEmpty(checkTypeEnum)...
            } //for (KgoCasesetCheck kgoCasesetCheck...
        } else {
            LOGGER.info("getCaseSetLoginCheck checkBoolean 4");
            checkBoolean = true;
        } //if (ObjectUtils.isNotEmpty(checkTypeList)...
        LOGGER.info("getCaseSetLoginCheck checkBoolean="+checkBoolean);
        return checkBoolean;
    } //getCaseSetLoginCheck

    /**
     * GEO 20220711 add for 智能客服
     * 提供智能客服服務案件入案存檔
     *
     * @param rq
     * @return
     */
    public GeoGetCaseSetSaveActionRs saveAction(GeoGetCaseSetSaveActionRq rq) {
        GeoGetCaseSetSaveActionRs geoGetCaseSetSaveActionRs = new GeoGetCaseSetSaveActionRs();
        SaveActionViewForm viewForm = new SaveActionViewForm();
        KgoApiException error = null;
        OperationApiMemo memo = null;
        String logCaseId = null;
        String caseSetName = null;
        try {
            // 前台、新增、申辦案件
            memo = super.genOperationMemo(SystemTypeEnum.F, SysLogExeType.TYPE_A, FrontendFunctionCodeEnum.CaseApply);
            if (StringUtils.isBlank(rq.getCaseSetId()) || ObjectUtils.isEmpty(rq.getVersion())) {
                throw new KgoApiException(new ErrorResult(KgoFrontEndApiError.RQ_NOT_COMPLETED));
            }
            KgoCaseset kgoCaseset = caseFormServiceImpl.getKgoCaseset(rq.getCaseSetId());
            // 已下架:此服務已下架‧，請選擇其他服務
            if (commonServiceHelper.getCaseSetStatusOff(kgoCaseset.getCaseSetId(), kgoCaseset.getStatus())) {
                throw new KgoApiException(new ErrorResult(KgoFrontEndApiError.SERVICE_IS_NOT_PROVIDED));
            } // if (commonServiceHelper...
            caseSetName = kgoCaseset.getCaseSetName();

            CaseTypeEnum caseType = CaseTypeEnum.getEnum(kgoCaseset.getCaseType());
            String caseId = KgoUtil.getNextCaseId(caseType);
            logCaseId = caseId;
            List<KgoCasesetColumn> casesetColumns = caseFormServiceImpl.getCasesetColumns(rq.getCaseSetId(), rq.getVersion());

            // 合併 MyData Id
            if (StringUtils.isNoneBlank(rq.getMyDataTxId())) {
                caseFormServiceImpl.setCaseSetMyDataTxidMageSaveColumnVal(rq);
            } //if (StringUtils.isNoneBlank(rq.getTxId()))

            // 檢核
            ValidationActionViewForm validationFrom = caseFormServiceImpl.saveValidation(casesetColumns, kgoCaseset, rq);
            viewForm.setValidationMsg(validationFrom.getValidationMsg());

            if (validationFrom.getValidationMsg().size() == 0) {

                // 存擋
                KgoCaseMain kgoCaseMain = caseFormServiceImpl.saveKgoCaseMain(caseId, kgoCaseset, rq, viewForm);
                caseFormServiceImpl.saveKgoCaseDetail(kgoCaseMain, kgoCaseset, rq);

                // 啟動案件
                String processId = activitiService.processCaseStart(kgoCaseMain, viewForm.getCaseOrganName());
                kgoCaseMain.setProcessId(processId);
                this.kgoCaseMainRepository.save(kgoCaseMain);

                // 申請人員
                String applyEmail =caseFormServiceImpl.getColumnViewForm(rq.getColumnData(), "Email");

                // 發送email 、通知、KCG API共通作法
                caseFormServiceImpl.doSaveCaseCommonNotify(kgoCaseMain, kgoCaseset, applyEmail, viewForm.getApplyDate());
            } //if (validationFrom.getValidationMsg().size()

            geoGetCaseSetSaveActionRs.setData(viewForm);
            return geoGetCaseSetSaveActionRs;
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("saveAction error " + e.getMessage(), e);
        } finally {
            caseFormServiceImpl.removeTxIdSession(rq.getMyDataTxId());
            /** === 儲存操作紀錄 === */
            List<OperationColumn> memoList = new ArrayList<>();
            memoList.add(new OperationColumn(String.format("%s申請，案件編號：", caseSetName), logCaseId));
            memo.setMemoList(memoList);
            super.saveOperationLog(memo);
            /** === 儲存操作紀錄 === */

            if (error != null) {
                throw error;
            } // if (error != null)
        } //try
    }// saveAction

    /**
     * GEO 20220828 add for 智能客服
     * 案件進度查詢
     *
     */
    public GeoCaseProcessDetailRs detailAction(GeoCaseProcessDetailRq rq) {
        GeoCaseProcessDetailRs geoCaseProcessDetailRs = new GeoCaseProcessDetailRs();
        GeoCaseProcessDetailViewForm geoCaseProcessDetailViewForm = new GeoCaseProcessDetailViewForm();
        String caseId = rq.getCaseId();
        String idCheck = null;
        String phone = null;
        KgoCaseMain kgoCaseMain = null;
        KgoApiException error = null;
        OperationApiMemo memo = null;

        if (rq.getPhone()!=null && !rq.getPhone().equals("")) phone = rq.getPhone();
        if (rq.getIdCheck()!=null && !rq.getIdCheck().equals("")) idCheck = rq.getIdCheck();

        if (org.springframework.util.StringUtils.isEmpty(caseId)) {
            // 未傳案件編號
            throw new KgoApiException(new ErrorResult(CaseProcessSearchApiError.PROCESS_VALIDATE_ERROR, "驗證欄位錯誤"));
        } //if (org.springframework.util.StringUtils.isEmpty(caseId))

        if (idCheck==null && phone==null)
            throw new KgoApiException(new ErrorResult(CaseProcessSearchApiError.PROCESS_VALIDATE_ERROR, "驗證欄位錯誤"));

        try {
            // 前台、新增、登入
            memo = super.genOperationMemo(SystemTypeEnum.F, null, FrontendFunctionCodeEnum.CaseQry);
            kgoCaseMain = kgoCaseMainRepository.findByCaseId(caseId);
            LOGGER.info("detailAction ertyuio:{}", JsonUtil.toJSONString(kgoCaseMain));
            if (kgoCaseMain==null) throw new KgoApiException(new ErrorResult(CaseProcessSearchApiError.PROCESS_VALIDATE_ERROR, "驗證欄位錯誤"));

            //身分證不為空
            if (!org.springframework.util.StringUtils.isEmpty(idCheck)){
                if (kgoCaseMain.getApplyUser()==null){
                    List<KgoCaseDetail> KgoCaseDetailList = kgoCaseDetailRepository.findByIdCaseId(caseId);
                    String value ="";
                    for (KgoCaseDetail detail:KgoCaseDetailList){
                        if (detail.getId().getColumnId().equals("ID")){
                            value = detail.getColumnValue();
                        }
                    } //for (KgoCaseDetail detail:KgoCaseDetailList)
                    if (value.equals("")){
                        throw new KgoApiException(new ErrorResult(CaseProcessSearchApiError.PROCESS_VALIDATE_ERROR, "驗證欄位錯誤"));
                    }else {
                        LOGGER.info("value.substring(6,10)="+value.substring(6,10));
                        if (!value.substring(6,10).equals(idCheck))
                            throw new KgoApiException(new ErrorResult(CaseProcessSearchApiError.PROCESS_VALIDATE_ERROR, "驗證欄位錯誤"));
                    }
                }else {
                  if (!kgoCaseMain.getApplyUser().substring(6,10).equals(idCheck))
                      throw new KgoApiException(new ErrorResult(CaseProcessSearchApiError.PROCESS_VALIDATE_ERROR, "驗證欄位錯誤"));
                }
            }else if (!org.springframework.util.StringUtils.isEmpty(phone)){
                //電話不為空
                List<KgoCaseDetail> KgoCaseDetailList = kgoCaseDetailRepository.findByIdCaseId(caseId);
                String value ="";
                for (KgoCaseDetail detail:KgoCaseDetailList){
                    if (detail.getColumnValue().equals(phone)) value = phone ;
                } //for (KgoCaseDetail detail:KgoCaseDetailList)
                if (value.equals(""))throw new KgoApiException(new ErrorResult(CaseProcessSearchApiError.PROCESS_VALIDATE_ERROR, "驗證欄位錯誤"));
            }

            if (kgoCaseMain !=null) {
                String caseSetId = kgoCaseMain.getCaseSetId();
                Optional<KgoCaseset> kgoCasesetOptional = kgoCasesetRepository.findById(caseSetId);

                if (kgoCasesetOptional.isPresent()) {
                    // 取得案件資料
                    KgoCaseset kgoCaseset = kgoCasesetOptional.get();
                    CaseProcessSearchDetailCaseset caseProcessSearchDetailCaseset = new CaseProcessSearchDetailCaseset();
                    caseProcessSearchDetailCaseset.setCasesetName(kgoCaseset.getCaseSetName());
                    caseProcessSearchDetailCaseset.setApplyDate(DateUtil.dateToString(kgoCaseMain.getApplyDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH));
                    caseProcessSearchDetailCaseset.setCaseId(kgoCaseMain.getCaseId());

                    CaseMainStatusEnum caseMainStatusEnum = CaseMainStatusEnum.getCaseMainStatusEnum(kgoCaseMain.getStatus());
                    caseProcessSearchDetailCaseset.setStatus(kgoCaseMain.getStatus());

                    if (ObjectUtils.isNotEmpty(caseMainStatusEnum)) {
                        if (caseMainStatusEnum == CaseMainStatusEnum.O) {
                            caseProcessSearchDetailCaseset.setStatusDscr(String.format("%s (%s)", caseMainStatusEnum.getLabel(), kgoCaseMain.getStatusDesc()));
                        } else {
                            caseProcessSearchDetailCaseset.setStatusDscr(caseMainStatusEnum.getLabel());
                        }
                    } else {
                        caseProcessSearchDetailCaseset.setStatusDscr(CaseMainStatusEnum.OTHERS.getLabel());
                    } // if (ObjectUtils.isNotEmpty(caseMainStatusEnum))

                    geoCaseProcessDetailViewForm.setCaseProcessSearchDetailCaseset(caseProcessSearchDetailCaseset);

                    // 取得群組、欄位資料
                    List<CaseCorrectDataDto> caseCorrectDataDtos = kgoCaseDetailRepository.listCaseCorrectData(kgoCaseMain.getCaseId(), caseSetId);
                    // 按照群組分
                    Map<Integer, List<CaseCorrectDataDto>> groupByGroupSeq = caseCorrectDataDtos.stream()
                            .collect(Collectors.groupingBy(CaseCorrectDataDto::getKcgGroupSeq, LinkedHashMap::new, Collectors.toList()));
                    List<CaseProcessSearchDetailCasesetGroup> caseProcessSearchDetailCasesetGroups = new ArrayList<>();
                    for (Map.Entry<Integer, List<CaseCorrectDataDto>> entry : groupByGroupSeq.entrySet()) {
                        List<CaseCorrectDataDto> value = entry.getValue();
                        if (!CollectionUtils.isEmpty(value)) {
                            CaseCorrectDataDto caseCorrectDataDtoGroupByGroupSeq = value.get(0);
                            CaseProcessSearchDetailCasesetGroup caseProcessSearchDetailCasesetGroup = new CaseProcessSearchDetailCasesetGroup();
                            caseProcessSearchDetailCasesetGroup.setGroupId(String.valueOf(caseCorrectDataDtoGroupByGroupSeq.getKcgGroupSeq()));
                            caseProcessSearchDetailCasesetGroup.setGroupName(caseCorrectDataDtoGroupByGroupSeq.getKcgMemo());
                            // 按照欄位分
                            Map<String, List<CaseCorrectDataDto>> groupByColumnId = value.stream()
                                    .collect(Collectors.groupingBy(CaseCorrectDataDto::getKccColumnId, LinkedHashMap::new, Collectors.toList()));
                            List<CaseProcessSearchDetailCasesetColumn> caseProcessSearchDetailCasesetColumns = new ArrayList<>();
                            for (Map.Entry<String, List<CaseCorrectDataDto>> columnEntry : groupByColumnId.entrySet()) {
                                List<CaseCorrectDataDto> columnValue = columnEntry.getValue();
                                if (!CollectionUtils.isEmpty(columnValue)) {
                                    CaseCorrectDataDto caseCorrectDataDto = columnValue.get(0);
                                    CaseProcessSearchDetailCasesetColumn caseProcessSearchDetailCasesetColumn = new CaseProcessSearchDetailCasesetColumn();
                                    caseProcessSearchDetailCasesetColumn.setCorrectMemo(caseCorrectDataDto.getKcdCorrectMemo());
                                    // modify: 是否補正 2020.12.03
                                    caseProcessSearchDetailCasesetColumn
                                            .setIsCorrect(caseCorrectDataDto.getKcdIsCorrect() != null ? YesNoFlag.getFlag(caseCorrectDataDto.getKcdIsCorrect()).getFlag() : YesNoFlag.N.getFlag());
                                    caseProcessSearchDetailCasesetColumn.setColumnDetailValue(caseCorrectDataDto.getKcdColumnValue());
                                    caseProcessSearchDetailCasesetColumn.setColumnValue(caseCorrectDataDto.getKccColumnValue());
                                    caseProcessSearchDetailCasesetColumn.setColumnType(caseCorrectDataDto.getKccColumnType());
                                    caseProcessSearchDetailCasesetColumn.setColumnName(caseCorrectDataDto.getKccColumnName());
                                    caseProcessSearchDetailCasesetColumn.setColumnId(caseCorrectDataDto.getKccColumnId());
                                    caseProcessSearchDetailCasesetColumn.setIsMustKey(Objects.toString(caseCorrectDataDto.getKccIsMustKey(), null));
                                    caseProcessSearchDetailCasesetColumn.setLength(caseCorrectDataDto.getKccLength());
                                    caseProcessSearchDetailCasesetColumn.setOrderNum(caseCorrectDataDto.getKccOrderNum());


                                    if (ColumnTypeEnum.M.getValue().equals(caseCorrectDataDto.getKccColumnType())) {
                                        // 取得複合欄位
                                        Map<Integer, List<CaseCorrectDataDto>> dataMap = columnValue.stream()
                                                .collect(Collectors.groupingBy(CaseCorrectDataDto::getKcccRow, HashMap::new, Collectors.toCollection(LinkedList::new)));
                                        List<List<CaseProcessSearchDetailCasesetColumnChild>> caseProcessSearchDetailCasesetColumnChildren = dataMap.keySet().stream().map(i -> {
                                            return dataMap.get(i).stream().map(innerItem -> {

                                                // List<CaseProcessSearchDetailCasesetColumnChild>
                                                // caseProcessSearchDetailCasesetColumnChildren =
                                                // columnValue.stream().map(innerItem -> {
                                                CaseProcessSearchDetailCasesetColumnChild caseProcessSearchDetailCasesetColumnChild = new CaseProcessSearchDetailCasesetColumnChild();
                                                caseProcessSearchDetailCasesetColumnChild.setcColumnId(innerItem.getKcccCColumnId());
                                                caseProcessSearchDetailCasesetColumnChild.setColumnDetailValue(innerItem.getKcd2ColumnValue());
                                                caseProcessSearchDetailCasesetColumnChild.setColumnType(innerItem.getKcccColumnType());
                                                caseProcessSearchDetailCasesetColumnChild.setColumnValue(innerItem.getKcccColumnValue());
                                                caseProcessSearchDetailCasesetColumnChild.setFText(innerItem.getKcccFText());
                                                caseProcessSearchDetailCasesetColumnChild.setBText(innerItem.getKcccBText());
                                                caseProcessSearchDetailCasesetColumnChild.setCorrectMemo(innerItem.getKcd2CorrectMemo());
                                                caseProcessSearchDetailCasesetColumn.setCorrectMemo(innerItem.getKcd2CorrectMemo());

                                                // modify: 是否補正 2020.12.03
                                                caseProcessSearchDetailCasesetColumnChild
                                                        .setIsCorrect(innerItem.getKcd2IsCorrect() != null ? YesNoFlag.getFlag(innerItem.getKcd2IsCorrect()).getFlag() : YesNoFlag.N.getFlag());
                                                if (caseProcessSearchDetailCasesetColumnChild.getIsCorrect().equalsIgnoreCase("Y")) {
                                                    caseProcessSearchDetailCasesetColumn.setIsCorrect(caseProcessSearchDetailCasesetColumnChild.getIsCorrect());
                                                }
                                                caseProcessSearchDetailCasesetColumnChild.setIsMustKey(Objects.toString(innerItem.getKcccIsMustKey(), null));
                                                caseProcessSearchDetailCasesetColumnChild.setLength(innerItem.getKcccLength());
                                                caseProcessSearchDetailCasesetColumnChild.setPcolumnId(innerItem.getKcccPColumnId());
                                                caseProcessSearchDetailCasesetColumnChild.setvGroup(innerItem.getKcccVGroup());
                                                caseProcessSearchDetailCasesetColumnChild.setRow(innerItem.getKcccRow());
                                                caseProcessSearchDetailCasesetColumnChild.setOrderNum(innerItem.getKcccOrderNum());
                                                return caseProcessSearchDetailCasesetColumnChild;
                                            }).collect(Collectors.toList());
                                        }).collect(Collectors.toList());
                                        caseProcessSearchDetailCasesetColumn.setCaseProcessSearchDetailCasesetColumnChildren(caseProcessSearchDetailCasesetColumnChildren);
                                    }
                                    caseProcessSearchDetailCasesetColumns.add(caseProcessSearchDetailCasesetColumn);
                                }
                            }
                            caseProcessSearchDetailCasesetGroup.setCaseProcessSearchDetailCasesetColumns(caseProcessSearchDetailCasesetColumns);
                            caseProcessSearchDetailCasesetGroups.add(caseProcessSearchDetailCasesetGroup);
                        }
                    }
                    geoCaseProcessDetailViewForm.setCaseProcessSearchDetailCasesetGroups(caseProcessSearchDetailCasesetGroups);

                    /** ========= 處理歷程 ========= **/
                    List<String> processIds = new ArrayList<>();

                    /**處理歷程新增案件流程重送 歷程記錄 */
                    SaCaseViewQueryDto dto = kgoCaseMainRepository.getSaCaseViewData(kgoCaseMain.getCaseId());
                    String nowProcessId = dto.getProcessId();
                    processIds.add(nowProcessId);

                    // 新增案件流程重送 歷程記錄
                    List<KgoCaseMainResendFlow> resendFlowList = kgoCaseMainResendFlowRepository.findByCaseId(dto.getCaseId());
                    processIds.addAll(resendFlowList.stream().map(r -> r.getProcessId()).distinct().collect(Collectors.toList()));
                    List<HistoryDataDto> historyDataDto = activitiService.getHistoryData(processIds);
                    List<CaseHandleCaseViewCaseHistoryDataGrid> historyData = castToHistoryDataGrid(historyDataDto);
                    geoCaseProcessDetailViewForm.setHistoryData(historyData);
                } //if (kgoCasesetOptional.isPresent())
            } //if (null != kgoCaseMain)
            geoCaseProcessDetailRs.setData(geoCaseProcessDetailViewForm);
        } catch (KgoApiException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey(), e);
            throw new KgoApiException("detailAction error " + e.getMessage(), e);
        } finally {
            /** === 儲存操作紀錄 === */
            List<OperationColumn> memoList = new ArrayList<>();
            memoList.add(new OperationColumn("智能客服查詢案件編號", rq.getCaseId()));
            memo.setMemoList(memoList);
            super.saveOperationLog(memo);
            /** === 儲存操作紀錄 === */

            if (error != null) {
                throw error;
            } //if (error != null
        } //try catch
        return geoCaseProcessDetailRs;
    } //detailAction

    /**
     * GEO 20220828 add for 智能客服
     * 案件歷程(不包含審核機關表單)
     *
     */
    public List<CaseHandleCaseViewCaseHistoryDataGrid> castToHistoryDataGrid(List<HistoryDataDto> historyDataDto) {
        List<CaseHandleCaseViewCaseHistoryDataGrid> histories = new ArrayList<>();
        historyDataDto.forEach(item -> {
            CaseHandleCaseViewCaseHistoryDataGrid grid = new CaseHandleCaseViewCaseHistoryDataGrid();
            grid.setStatus(item.getStatus());
            grid.setOrgan(item.getOrganName());
            grid.setContent(item.getContent());
            grid.setTaker(item.getOfficer());
            grid.setDealTime(item.getDealTime());
            /** GEO 20211101 add 增加簽核意見*/
            GeoKgoTaskComment taskComment = geoKgoTaskCommentRepository.findByCommentId(item.getCommentId());
            String commentText = StringUtils.EMPTY;
            if (taskComment != null && StringUtils.isNotBlank(taskComment.getCommentText()))
                commentText = taskComment.getCommentText();
            grid.setTaskComment(commentText);
            histories.add(grid);
        }); //historyDataDto.forEach(item...
        // sort by dealTime(DESC)
        histories.sort(Collections.reverseOrder((entity1, entity2) -> entity1.getDealTime().compareTo(entity2.getDealTime())));
        return histories;
    } //castToHistoryDataGrid

    /**
     * Geo 20220729 前台切換搜尋引擎
     * 提供目前所有上架狀態的申辦案件資料(不包含站外連結)，讓其他廠商可爬蟲使用
     */
    public GeoOfferCaseSetInfoRs GeoOfferCaseSetInfo() {
        GeoOfferCaseSetInfoRs rs = new GeoOfferCaseSetInfoRs();
        GeoCaseSetInfoViewForm geoCaseSetInfoViewForm = new GeoCaseSetInfoViewForm();
        rs.setData(geoCaseSetInfoViewForm);
        try {
            LOGGER.info("GeoOfferCaseSetInfo start="+System.currentTimeMillis());
            //找出所有目前已上架的案件
            List<GeoCaseSetListDtoModel> geoCaseSetListDtoModelList = geoCaseSetApplyCountReposCustom.getCaseSetListInfo(IsPublishEnum.ON.toString(),CaseFlowTypeEnum.B3.getValue());
            //做前台畫面的url
            for (GeoCaseSetListDtoModel model:geoCaseSetListDtoModelList){
                StringBuilder sb = new StringBuilder();
                sb.append(SpringUtil.getProperty("frontend.caseset.url"));
                sb.append(model.getCaseSetId());
                sb.append("?applyType=E");
                model.setUrl(sb.toString());
            } //for (GeoCaseSetListDtoModel model:geoCaseSetListDtoModelList)
            geoCaseSetInfoViewForm.setCaseSetList(geoCaseSetListDtoModelList);
            LOGGER.info("GeoOfferCaseSetInfo end="+System.currentTimeMillis());
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
        }
        return rs;
    } //getCaseSetListActionByKeyWord

    /**
     * 更新 民政局黑名單資料 來源 城市資料平台
     */
    public GeoCityForDepartmentCivil sendPostApiForCity() {
        GeoCityForDepartmentCivil geoCityForDepartmentCivil = null;
        try {
            LOGGER.info("民政局黑名單資料 sendPostApiForCity start");
            JSONObject bodyjson = callKcgCityApiServiceHelper.postCityApiData(KcgCityApiServiceType.KGO_POST_BLACKLIST.getServiceId(),null);
            LOGGER.info("民政局黑名單資料 bodyjson="+bodyjson);
            if (bodyjson !=null){
                geoCityForDepartmentCivil = new GeoCityForDepartmentCivil();
                JSONObject dataJSon = bodyjson.getJSONObject("data");
                if (dataJSon !=null){
                    String ivStr = dataJSon.optString("ivStr");
                    geoCityForDepartmentCivil.setIvStr(ivStr);
                    JSONArray blackList = dataJSon.getJSONArray("list");
                    if (blackList!=null && blackList.length()>0){
                        List<GeoCityForDepartmentCivilPerson> geoCityForDepartmentCivilPersonList = new ArrayList<>();
                        for (int i=0;i<blackList.length();i++){
                            GeoCityForDepartmentCivilPerson person= new GeoCityForDepartmentCivilPerson();
                            person.setName(blackList.getJSONObject(i).getString("name"));
                            person.setIdentityId(blackList.getJSONObject(i).getString("identityId"));
                            geoCityForDepartmentCivilPersonList.add(person);
                        } //for (int i=0;i<blackList.length();i++)
                        geoCityForDepartmentCivil.setBlackList(geoCityForDepartmentCivilPersonList);
                    } //if (blackList!=null && blackList.length()>0)
                } //if (dataJSon !=null)
            } //if (bodyjson !=null)
        } catch (Exception e) {
            e.printStackTrace();
        } //try catch
        return geoCityForDepartmentCivil;
    } //sendPostApiForCity

    /**
     * 市民支付平台-繳費入帳查詢(測試機)
     */
    public void getPaymentRecordDataList() {
        try {
            LOGGER.info("市民支付平台-繳費入帳查詢(測試機) start");
            JSONObject bodyjson =
                    callKcgCityApiServiceHelper.postCityApiData(KcgCityApiServiceType.KGO_PAYMENT_RECORD_DATA.getServiceId(),"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiLpq5jpm4TluILmlL_lupwiLCJhdWQiOiIyZjUwODgzYy1mZmY3LTQ1NzAtYTBkOC1jZTc1OGEyNzU4Y2IiLCJqdGkiOiI1OWIyZTY0MS03NzQ3LTQ1OWYtYTgwYy02YjMwMDgxMDhmOGUiLCJpYXQiOiIxNjY4NjE3MzQ5IiwiZXhwIjoiMTcwMDE1MzM0OSJ9.7nSGCMCAmnCdRZH7ITc7NTeLlBYryhkpVz8tO6isTnE","https://10.101.182.132/api/service/post","2f50883c-fff7-4570-a0d8-ce758a2758cb");
            LOGGER.info("市民支付平台-繳費入帳查詢(測試機) bodyjson="+bodyjson);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("getPaymentRecordDataList e="+e);
        } //try catch
    } //sendPostApiForCity

    /**
     * 市民支付平台-繳費導頁至繳費平台(測試機)
     */
    public void goToPayWeb() {
        try {
            LOGGER.info("市民支付平台-繳費導頁至繳費平台(測試機) start");
            JSONObject bodyjson =
                    callKcgCityApiServiceHelper.postCityApiData(KcgCityApiServiceType.KGO_PAYMENT_WEB_PAGE.getServiceId(),"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiLpq5jpm4TluILmlL_lupwiLCJhdWQiOiIyZjUwODgzYy1mZmY3LTQ1NzAtYTBkOC1jZTc1OGEyNzU4Y2IiLCJqdGkiOiI1OWIyZTY0MS03NzQ3LTQ1OWYtYTgwYy02YjMwMDgxMDhmOGUiLCJpYXQiOiIxNjY4NjE3MzQ5IiwiZXhwIjoiMTcwMDE1MzM0OSJ9.7nSGCMCAmnCdRZH7ITc7NTeLlBYryhkpVz8tO6isTnE","https://10.101.182.132/api/service/post","59b2e641-7747-459f-a80c-6b3008108f8e");
            LOGGER.info("市民支付平台-繳費導頁至繳費平台(測試機) bodyjson="+bodyjson);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("goToPayWeb e="+e);
        } //try catch
    } //sendPostApiForCity

    /**
     * 市民支付平台-退費資料處理(測試機)
     */
    public void refundMoney() {
        try {
            LOGGER.info("市民支付平台-退費資料處理(測試機) start");
            JSONObject bodyjson =
                    callKcgCityApiServiceHelper.postCityApiData(KcgCityApiServiceType.KGO_REFEND.getServiceId(),"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiLpq5jpm4TluILmlL_lupwiLCJhdWQiOiIyZjUwODgzYy1mZmY3LTQ1NzAtYTBkOC1jZTc1OGEyNzU4Y2IiLCJqdGkiOiI1OWIyZTY0MS03NzQ3LTQ1OWYtYTgwYy02YjMwMDgxMDhmOGUiLCJpYXQiOiIxNjY4NjE3MzQ5IiwiZXhwIjoiMTcwMDE1MzM0OSJ9.7nSGCMCAmnCdRZH7ITc7NTeLlBYryhkpVz8tO6isTnE","https://10.101.182.132/api/service/post","59b2e641-7747-459f-a80c-6b3008108f8e");
            LOGGER.info("市民支付平台-退費資料處理(測試機) bodyjson="+bodyjson);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("getPaymentRecordDataList e="+e);
        } //try catch
    } //sendPostApiForCity

    /**
     * 市民支付平台-退費資料處理(測試機)
     */
    public void testOrgan() {
        try {
            LOGGER.info("便民一路通-跨機關自動入案(測試機) start");
            JSONObject bodyjson =
                    callKcgCityApiServiceHelper.postCityApiData(KcgCityApiServiceType.KGO_POST_CROSS_ORGAN.getServiceId(),null);
            LOGGER.info("便民一路通-跨機關自動入案(測試機) bodyjson="+bodyjson);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("getPaymentRecordDataList e="+e);
        } //try catch
    } //sendPostApiForCity

}
