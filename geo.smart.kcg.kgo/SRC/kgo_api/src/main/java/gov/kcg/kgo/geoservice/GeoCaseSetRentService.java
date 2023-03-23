package gov.kcg.kgo.geoservice;

import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.enums.backend.CaseMainStatusEnum;
import gov.kcg.kgo.enums.common.SuccessMsg;
import gov.kcg.kgo.enums.error.KgoFrontEndApiError;
import gov.kcg.kgo.geoenum.CaseSetCategoryEnum;
import gov.kcg.kgo.geoenum.GeoPaymentTypeEnum;
import gov.kcg.kgo.geoenum.RentStatusEnum;
import gov.kcg.kgo.enums.common.ComboBoxStatusEnum;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.geoentity.*;
import gov.kcg.kgo.geomodel.*;
import gov.kcg.kgo.georepository.*;
import gov.kcg.kgo.georepository.custom.GeoCaseSetRentReposCustom;
import gov.kcg.kgo.georepository.custom.GeoCaseSetSiteReposCustom;
import gov.kcg.kgo.georepository.custom.GeoCaseSetSiteTimeReposCustom;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoKgoRentCaseRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoKgoRentTimeQueryRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeokgoRentRq;
import gov.kcg.kgo.geoviewmodel.backend.rs.ApplyFormInitRs;
import gov.kcg.kgo.geoviewmodel.backend.rs.CaseSetSiteTimeQueryRs;
import gov.kcg.kgo.geoviewmodel.backend.rs.GeoRentComboBoxRs;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.*;
import gov.kcg.kgo.geoviewmodel.backend.rs.GeoRentWeekQueryRs;
import gov.kcg.kgo.geoviewmodel.frontend.rq.ApplyFormExInfoRq;
import gov.kcg.kgo.geoviewmodel.frontend.rq.ApplyFormValidTimeRq;
import gov.kcg.kgo.geoviewmodel.frontend.rq.CaseSetRentTimeQueryRq;
import gov.kcg.kgo.geoviewmodel.frontend.rq.CaseSetSearchDateRq;
import gov.kcg.kgo.geoviewmodel.frontend.rs.CaseSetRentMainSearchRs;
import gov.kcg.kgo.geoviewmodel.frontend.rs.CaseSetSearchDateRs;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.CaseSetSearchDateViewForm;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoCasesetRentMainViewForm;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoCityCoinMembershipViewForm;
import gov.kcg.kgo.model.*;
import gov.kcg.kgo.repository.*;
import gov.kcg.kgo.service.bean.excel.GeoCaseRentalCaseExcelVo;
import gov.kcg.kgo.service.impl.helper.KgoServiceHelper;
import gov.kcg.kgo.service.impl.helper.OrganUnitManagementServiceHelper;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.util.SpringUtil;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import gov.kcg.kgo.viewModel.frontend.caseform.rq.bean.SaveActionCColumnViewForm;
import gov.kcg.kgo.viewModel.frontend.caseform.rs.bean.ColumnViewForm;
import io.swagger.annotations.ApiModelProperty;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ErrorMessages;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class GeoCaseSetRentService extends GeoBaseService {

    private static final Logger logger = LoggerFactory.getLogger(GeoCaseSetRentService.class);
    private OrganUnitManagementServiceHelper organUnitManagementServiceHelper = OrganUnitManagementServiceHelper.getInstance();
    private KgoServiceHelper kgoServiceHelper = KgoServiceHelper.getInstance();
    //之後可能改成ENUM統一放置prefix
    private static final String RENT_MAIN_ID_PREFIX = "RTM";
    private static final String RENT_DATE_ID_PREFIX = "RTD";
    private static final String RENT_TIME_ID_PREFIX = "RTT";
    private static final String RENT_PAYMENT_ID_PREFIX = "PAY";
    @Autowired
    GeoCaseSetSiteRepos geoCaseSetSiteRepos;
    @Autowired
    GeoKgoCasesetRentMainRepository geoKgoCasesetRentMainRepository;
    @Autowired
    GeoKgoCasesetRentDateRepository geoKgoCasesetRentDateRepository;
    @Autowired
    GeoKgoCasesetRentTimeRepository geoKgoCasesetRentTimeRepository;
    @Autowired
    GeoCaseSetRentReposCustom geoCaseSetRentReposCustom;
    @Autowired
    GeoCaseSetSiteReposCustom geoCaseSetSiteReposCustom;
    @Autowired
    GeoKgoRentPaymentRepos geoKgoRentPaymentRepos;
    @Autowired
    GeoCaseRentRelationRepos geoCaseRentRelationRepos;
    @Autowired
    KgoCasesetRepository kgoCasesetRepository;
    @Autowired
    KgoGroupLevelRepository kgoGroupLevelRepository;
    @Autowired
    KgoCaseMainRepository kgoCaseMainRepository;
    @Autowired
    GeoCaseSetRentService geoCaseSetRentService;
    @Autowired
    KgoCasesetRefundRatioRepository refundRatioRepository;
    @Autowired
    GeoKcgPaymentService geoKcgPaymentService;
    @Autowired
    GeoCaseSetSiteTimeReposCustom geoCaseSetSiteTimeReposCustom;
    @Autowired
    KgoOrganRepository kgoOrganRepository;
    @Autowired
    KgoUnitRepository kgoUnitRepository;
    @Autowired
    GeoCaseSetSiteMainRepos geoCaseSetSiteMainRepos;
    /**
     * 後台-服務管理場地/活動線上租借 下拉選單
     */
    public GeoRentComboBoxRs getDefaultRentCase(GeokgoRentRq rq) {
        logger.info(" GeoCaseRentService : getDefaultRentCase Start: ");
        KgoApiException error = null;
        GeoRentComboBoxViewForm viewForm = new GeoRentComboBoxViewForm();
        GeoRentComboBoxRs rs = new GeoRentComboBoxRs();
        BackendLoginUserInfo userInfo = getLoginUser();
        logger.info(userInfo.toString());
        try {
            //登入者預設科室ComboBox
            ComboBox unitComboBox;
            String unitId = rq.getUnitId();
            KgoCaseset caseset = kgoCasesetRepository.getById(rq.getCaseSetId());
            String organId = caseset.getOwnerOrgan();
            logger.info("siteComboBox find by OwnOrgan");
            if(StringUtils.isBlank( unitId )){
                unitComboBox = getUnitComboBox(organId, null);
                unitId = "";
            }else{
                unitComboBox = getUnitComboBox(organId, unitId );
            }
            String categroy = caseset.getCasesetCategory();
            //登入本人所建立的場地
            List<GeoKgoCaseSetSiteMain> siteList = geoCaseSetSiteRepos.getSiteByUserAndUnit(unitId, organId, categroy);
            ComboBox siteComboBox = getUnitSiteComboBox(siteList, userInfo.getUserId());
            viewForm.setSiteComboBox((siteComboBox));
            viewForm.setUnitComboBox(unitComboBox);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            logger.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoBackEndApiError.UNKNOWN_EXCEPTION), e);
        } finally {
            setResultMessage(rq, rs, error);
        }
        return rs;
    }

    /**
     * 後台-預約時間編輯-查詢單位週的預約時間
     */
    public GeoRentWeekQueryRs queryRentWeek(GeoKgoRentTimeQueryRq rq) {
        logger.error("GeoCaseRentService query by holeWeek Start:");
        logger.error("Query by year :" + rq.getYear() + " Month :" + rq.getMonth() + " week :" + rq.getWeek());
        GeoRentWeekQueryRs rs = new GeoRentWeekQueryRs();
        GeoRentWeekQueryViewForm viewForm = new GeoRentWeekQueryViewForm();
        GeokgoRentCaseSetModel caseSetModel = new GeokgoRentCaseSetModel();
        KgoApiException error = null;
        BackendLoginUserInfo userInfo = getLoginUser();
        try {
            //確認租借主檔
            String siteMainId = rq.getServiceId();
            String casesetId = rq.getCaseSetId();
            String caseRentId = rq.getCaseRentId();
            if(StringUtils.isBlank(casesetId)||StringUtils.isBlank(siteMainId)){
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.DATA_FORMAT_ERROR));
            }
            Boolean isNew = false;
            if (StringUtils.isBlank(caseRentId) ) {
                GeoKgoCasesetRentMain rentMain = geoKgoCasesetRentMainRepository.getRentMainByServiceId(siteMainId, casesetId);
                //尚未有任何資料，等前端第一次儲存才產生ID
                if(rentMain == null){
                    isNew = true;
                }else{
                    caseRentId = rentMain.getCaseRentId();
                }
            }
            caseSetModel.setCaseRentId(caseRentId);
            caseSetModel.setCaseSetId(casesetId);
            caseSetModel.setServiceId(siteMainId);
            //場地預設價格
            GeoKgoCaseSetSiteMain siteMain = geoCaseSetSiteRepos.getById(siteMainId);
            caseSetModel.setDefaultPrice(siteMain.getSiteAmount());
            viewForm.setRentCaseSetModel(caseSetModel);
            //回傳 週內七天個別資料(in Id 鎖定預約件/ out Id 鎖定占用件 )
            String firstDate = DateUtil.getFirstDateInWeek(rq.getYear(), rq.getMonth(), rq.getWeek());
            String lastDate = DateUtil.getLastDateInWeek(rq.getYear(), rq.getMonth(), rq.getWeek());
            List<GeokgoRentDateInsertModel> rtnDateModelList;
            if (isNew) {
                logger.info("no data in query week :");
                rtnDateModelList = createOneWeekDate(firstDate,siteMainId);
            } else {
                List<GeoKgoCasesetRentDate> oldRentDate = geoKgoCasesetRentDateRepository.getWeekList(caseRentId, firstDate, lastDate);
                if(oldRentDate.size()== 0){
                    rtnDateModelList = createOneWeekDate(firstDate,siteMainId);
                }else{
                    logger.info("have data in query week");
                    rtnDateModelList = oldRentDate.stream().map(de -> {
                        List<GeokgoRentTimeInsertModel> timeModelList = geoCaseSetRentReposCustom.findSiteRentTimeList(de.getRentDateId(), de.getDetailDate(), siteMainId);
                        GeokgoRentDateInsertModel dateModel = GeokgoRentDateInsertModel.changeToModel(de);
                        dateModel.setDetailTimeList(timeModelList);
                        return dateModel;
                    }).collect(Collectors.toList());
                }

            }
            viewForm.setRentDateModelList(rtnDateModelList);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            logger.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SEARCH), e);
        } finally {
            // 設置 成功/失敗訊息.
            setResultMessage(rq, rs, error);
        } //try
        //都沒有則回傳空資料
        return rs;
    }

    /** 複製上一周預約項目*/
    public GeoRentWeekQueryRs copyLastWeek(GeoKgoRentTimeQueryRq rq) {
        logger.error("GeoCaseRentService copy last week Start:");
        logger.error("Query by year :" + rq.getYear() + " Month :" + rq.getMonth() + " week :" + rq.getWeek());
        GeoRentWeekQueryRs rs = new GeoRentWeekQueryRs();
        GeoRentWeekQueryViewForm viewForm = new GeoRentWeekQueryViewForm();
        GeokgoRentCaseSetModel caseSetModel = new GeokgoRentCaseSetModel();
        KgoApiException error = null;
        BackendLoginUserInfo userInfo = getLoginUser();
        try {
            //確認租借主檔
            String siteMainId = rq.getServiceId();
            String casesetId = rq.getCaseSetId();
            String caseRentId = rq.getCaseRentId();
            if(StringUtils.isBlank(casesetId)||StringUtils.isBlank(siteMainId)){
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.DATA_FORMAT_ERROR));
            }
            if (StringUtils.isBlank(caseRentId) ) {
                GeoKgoCasesetRentMain rentMain = geoKgoCasesetRentMainRepository.getRentMainByServiceId(siteMainId, casesetId);
                //尚未有任何資料，等前端第一次儲存才產生ID
                if(rentMain == null){
                    logger.error("caseRentID not found!! cant not copy!!!!");
                    error = new KgoApiException(new ErrorResult(KgoBackEndApiError.RENTAL_WEEKQUERY_ERROR));
                    throw error;
                }else{
                    caseRentId = rentMain.getCaseRentId();
                }
            }
            caseSetModel.setCaseRentId(caseRentId);
            caseSetModel.setCaseSetId(casesetId);
            caseSetModel.setServiceId(siteMainId);
            //場地預設價格
            GeoKgoCaseSetSiteMain siteMain = geoCaseSetSiteRepos.getById(siteMainId);
            caseSetModel.setDefaultPrice(siteMain.getSiteAmount());
            viewForm.setRentCaseSetModel(caseSetModel);
            //回傳 週內七天個別資料(in Id 鎖定預約件/ out Id 鎖定占用件 )
            String firstDate = DateUtil.getFirstDateInWeek(rq.getYear(), rq.getMonth(), rq.getWeek());
            String lastDate = DateUtil.getLastDateInWeek(rq.getYear(), rq.getMonth(), rq.getWeek());
            List<GeokgoRentDateInsertModel> rtnDateModelList;

            List<GeoKgoCasesetRentDate> oldRentDate = geoKgoCasesetRentDateRepository.getWeekList(caseRentId, firstDate, lastDate);
            if(oldRentDate.size()== 0){
                rtnDateModelList = createOneWeekDate(firstDate,siteMainId);
                String finalCaseRentId = caseRentId;
                rtnDateModelList.forEach(dm->{//複製上一周的內容
                        List<GeokgoRentTimeInsertModel> timeModelList = dm.getDetailTimeList();
                        try {
                            List<GeokgoRentTimeInsertModel> copyTimeList = findLastWeekTimeListBydate(finalCaseRentId,DateUtil.strToTimestamp(dm.getDetailDate(), "yyyy-MM-dd") );
                            dm.setDetailTimeList(removeLockTimeList(timeModelList,copyTimeList));
                        }catch (Exception e){
                            logger.error("Date parse error ");
                            throw new KgoApiException(new ErrorResult(KgoBackEndApiError.DATA_FORMAT_ERROR));
                        }
                    });
            }else{
                logger.info("have data in query week");
                rtnDateModelList = oldRentDate.stream().map(de -> {
                    GeokgoRentDateInsertModel dateModel = GeokgoRentDateInsertModel.changeToModel(de);
                    List<GeokgoRentTimeInsertModel> timeModelList = geoCaseSetRentReposCustom.findSiteRentTimeList(de.getRentDateId(), de.getDetailDate(), siteMainId);
                    //複製上一周的預約時間(上週所建立案件)過濾本周已佔用預約時間
                    List<GeokgoRentTimeInsertModel> copyTimeList = findLastWeekTimeListBydate(de.getCaseRentId(),de.getDetailDate());
                    dateModel.setDetailTimeList(removeLockTimeList(timeModelList,copyTimeList));
                    return dateModel;
                }).collect(Collectors.toList());
            }

            viewForm.setRentDateModelList(rtnDateModelList);
            rs.setData(viewForm);
            rs.setMsg("查詢成功");
        } catch (KgoApiException apiE) {
            logger.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SEARCH), e);
        } finally {
            // 設置 成功/失敗訊息.
            setResultMessage(rq, rs, error);
        } //try
        //都沒有則回傳空資料
        return rs;
    }


    /** 後台-服務管理-新增/編輯預約時間*/
    @Transactional
    public GeoRentWeekQueryRs editAndSave(GeoKgoRentCaseRq rq) {
        logger.error("GeoCaseRentService save / edit case start :");
        GeoRentWeekQueryRs rs = new GeoRentWeekQueryRs();
        KgoApiException error = null;
        try{
            validateInsertData(rq);
            GeoRentWeekQueryViewForm viewForm = geoCaseSetRentService.saveProcess(rq);
            rs.setData(viewForm);
            rs.setMsg(SuccessMsg.UNKNOW.getMsg());
        } catch (KgoApiException apiE) {
            logger.error(apiE.getMessage());
            error = apiE;
            throw error;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SAVE), e);
        } finally {
            // 設置 成功/失敗訊息.
            setResultMessage(rq, rs, error);
        }
        return rs;
    }

    /** transaction field rollback if exception */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public GeoRentWeekQueryViewForm saveProcess(GeoKgoRentCaseRq rq) throws Exception {
        GeoRentWeekQueryViewForm viewForm = new GeoRentWeekQueryViewForm();
        GeokgoRentCaseSetModel caseSetModel = new GeokgoRentCaseSetModel();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        BackendLoginUserInfo userInfo = getLoginUser();

        String caseSetId = rq.getRentCaseSetModel().getCaseSetId();
        String rentMainId = rq.getRentCaseSetModel().getCaseRentId();
        String serviceId = rq.getRentCaseSetModel().getServiceId();
        Boolean isNew = false;
            //確認租借預約時間主檔
            if (rentMainId == null) {
                rentMainId = createRentMainId();
                isNew = true;
            }

            caseSetModel.setCaseSetId(caseSetId);
            caseSetModel.setServiceId(serviceId);
            caseSetModel.setCaseRentId(rentMainId);
            GeoKgoCasesetRentMain rentMain;

            if (isNew) {
                //主檔db資料
                rentMain = new GeoKgoCasesetRentMain();
                rentMain.setCaseRentId(rentMainId);
                rentMain.setCreateTime(now);
                rentMain.setCreateUser(userInfo.getUserId());
                rentMain.setCaseSetId(caseSetId);
                rentMain.setServiceId(serviceId);
                logger.error("create rentMainData RentMainId :" + rentMainId);
            } else {
                rentMain = geoKgoCasesetRentMainRepository.getById(rentMainId);
            }
            geoKgoCasesetRentMainRepository.save(rentMain);
            //回傳物件:當週
            List<GeokgoRentDateInsertModel> returnDateModelList = new ArrayList<>();
            //確認是否當周資料ID  TODO: 如果查詢撈出的資料不正確,回傳的資料未帶ID有可能導致同一時間建立複數ID及資料
            List<GeokgoRentDateInsertModel> dateModelList = rq.getRentDateModelList();
            if (dateModelList.get(0).getRentDateId() == null) {
                logger.info("have no week data -> create whole week Id ");
                //產生一周的id
                List<String> idList = createRentDateId(7);
                AtomicInteger i = new AtomicInteger(0);
                dateModelList.stream().forEach(dm -> dm.setRentDateId(idList.get(i.getAndIncrement())));
            }

            for (GeokgoRentDateInsertModel dm : dateModelList) {
                //建立當周每日db資料
                GeokgoRentDateInsertModel returnDateModel = saveRentDateData(now, rentMainId, dm);
                returnDateModelList.add(returnDateModel);
            }

            // return Rs區塊
            viewForm.setRentCaseSetModel(caseSetModel);
            viewForm.setRentDateModelList(returnDateModelList);
            return viewForm;

    }

    /** 建立一周的每日模組 並放入已佔用的預約時間*/
    private  List<GeokgoRentDateInsertModel> createOneWeekDate(String firstDate , String siteMainId ) throws ParseException {
        AtomicReference<Date> queryDate = new AtomicReference<>(DateUtil.strToDate(firstDate, DateUtil.PATTEN_YEAR_MONTH_DAY));
        List<GeokgoRentDateInsertModel> rtnDateModelList = IntStream.range(0, 7).mapToObj(i -> {
            GeokgoRentDateInsertModel dm = new GeokgoRentDateInsertModel();
            //取得同場地同時間的佔用件
            List<GeokgoRentTimeInsertModel> timeModelList = geoCaseSetRentReposCustom.findSiteRentTimeList(null, queryDate.get(), siteMainId);
            dm.setDetailTimeList(timeModelList);
            dm.setWeek(DateUtil.getWeekDay(queryDate.get()));
            dm.setWeekInMonth(DateUtil.getWeekInMonth(queryDate.get()));
            dm.setMonth(DateUtil.getMonth(queryDate.get()));
            dm.setDetailDate(DateUtil.dateToString(queryDate.get(), DateUtil.PATTEN_YEAR_MONTH_DAY));
            queryDate.set(DateUtil.getTomorrow(queryDate.get()));
            return dm;
        }).collect(Collectors.toList());
        return rtnDateModelList;
    }


    /** 後台-建立預約時間date*/
    private GeokgoRentDateInsertModel saveRentDateData(Timestamp now, String rentMainId, GeokgoRentDateInsertModel dm) throws Exception {
        GeoKgoCasesetRentDate dateEntity;
        Integer isEnable = dm.getIsEnable();
        Integer isAllDay = dm.getIsAllDay();
        //單日資料輸入未帶主檔id , 判斷為新建
        if (dm.getCaseRentId() == null) {
            logger.info("create one rentDate caee ~~");
            dateEntity = GeokgoRentDateInsertModel.changeToEntity(dm);
            dateEntity.setCaseRentId(rentMainId);
        } else {
            dateEntity = geoKgoCasesetRentDateRepository.getById(dm.getRentDateId());
            dateEntity.setLastiestTime(dm.getLastiestTime());
            dateEntity.setLastiestDay(dm.getLastiestDay());
            dateEntity.setEarliestTime(dm.getEarliestTime());
            dateEntity.setEarliestDay(dm.getEarliestDay());
            dateEntity.setIsEnable(dm.getIsEnable());
            dateEntity.setIsAllDay(dm.getIsAllDay());
        }
        dateEntity.setEditTime(now);
        geoKgoCasesetRentDateRepository.saveAndFlush(dateEntity);

        //預約時間單位,包含已鎖定時間及本次儲存改動時間
        List<GeokgoRentTimeInsertModel> timeModelList = dm.getDetailTimeList();
        List<GeokgoRentTimeInsertModel> returnTimeModelList = new ArrayList<>();
        if(timeModelList!=null ){//將已佔用預約時間先回填至回傳時間清單
            List<GeokgoRentTimeInsertModel> lockTimeList = timeModelList.stream().filter(tm -> tm.getIsLocked()!=null && tm.getIsLocked() == 1).collect(Collectors.toList());
            returnTimeModelList.addAll(lockTimeList);
        }
        /** 1.取消預約 2.全天開放  3.更新新版預約時間刪除舊版(同ID取代/不同ID刪除) */
        //分歧1
        if(isEnable==0 && dm.getRentDateId()!=null){//1.取消預約
            //所有舊件abondon
            geoKgoCasesetRentTimeRepository.updateAbandonTimeByDateId(dm.getRentDateId());
        }else{
            //更新新版預約時間刪除舊版(同ID取代/不同ID刪除)
            if (timeModelList != null) {
                //過濾鎖定時間
                logger.info(" rentTimeId filter -- status is Locked ");
                timeModelList = timeModelList.stream().filter(tm -> tm.getIsLocked() == null || tm.getIsLocked() != 1).collect(Collectors.toList());
                //分歧2
                if (timeModelList.size() > 0) {
                    //撈取同時間同場地案件比對,並變更前台未回傳的時間id狀態改為放棄
                    List<GeoKgoCasesetRentTime> oldTimeList = geoKgoCasesetRentTimeRepository.getTimeByRentDateId(dateEntity.getRentDateId());
                    if (oldTimeList.size() > 0) {
                        logger.info("start to match to new edit time and abanbon old ");
                        Map insertTimeId = timeModelList.stream().filter(tm->tm.getRentTimeId()!=null).collect(Collectors.toMap(tm -> tm.getRentTimeId(), tm -> tm.getRentTimeId()));
                        List<GeoKgoCasesetRentTime> abandonTimeEntity = oldTimeList.stream()
                                .filter(timEntity -> insertTimeId.containsKey(timEntity.getRentTimeId()) == false)
                                .map(tn -> {tn.setIsAbandon(1);return tn;})
                                .collect(Collectors.toList());
                        StringBuilder sb = new StringBuilder();
                        abandonTimeEntity.forEach(e -> sb.append(e.getRentTimeId()).append(","));
                        logger.info("abandonList : [" + sb.toString() + "]");
                        geoKgoCasesetRentTimeRepository.saveAll(abandonTimeEntity);
                    }

                    //新建立的預約時間單位建立ID
                    Long needTimeIdCount = timeModelList.stream().filter(tm -> tm.getRentTimeId() == null).count();
                    if (needTimeIdCount > 0) {
                        List<String> newTimeIdList = createRentTimeId(needTimeIdCount.intValue());
                        AtomicInteger i = new AtomicInteger(0);
                        timeModelList.stream().filter(tm -> tm.getRentTimeId() == null)
                                .forEach(tm -> tm.setRentTimeId(newTimeIdList.get(i.getAndIncrement())));
                    }

                    for (GeokgoRentTimeInsertModel tm : timeModelList) {
                        GeokgoRentTimeInsertModel returnTimeModel = saveRentTimeData(now, dateEntity.getRentDateId(), tm);
                        returnTimeModelList.add(returnTimeModel);
                    }
                }//timeModelList.size() > 0
            }//timeModelList != null
        }

        GeokgoRentDateInsertModel returnDateModel = GeokgoRentDateInsertModel.changeToModel(dateEntity);

        if (returnTimeModelList.size() > 0) {
            returnDateModel.setDetailTimeList(returnTimeModelList);
        } else {
            returnDateModel.setDetailTimeList(null);
        }
        return returnDateModel;
    }
    /** 後台-建立預約時間-預約時段儲存*/
    private GeokgoRentTimeInsertModel saveRentTimeData(Timestamp now, String rentDateId, GeokgoRentTimeInsertModel tm) throws Exception {
        GeoKgoCasesetRentTime timeEntity = null;
        //單位預約時段資料確認: 尚未儲存RentDateId為新建
        if (tm.getRentDateId() == null) {
            timeEntity = GeokgoRentTimeInsertModel.changeToEntity(tm);
            timeEntity.setUsedUsersNum(0);
            timeEntity.setRentDateId(rentDateId);
            logger.error("create rentTimeId " + timeEntity.getRentTimeId());
        } else {
            timeEntity = geoKgoCasesetRentTimeRepository.getById(tm.getRentTimeId());
            timeEntity.setAvailableUserQouta(tm.getAvailableUserQuota());
            timeEntity.setUsedUsersNum(tm.getUsedUsersNum());
            timeEntity.setUnitPrice(tm.getUnitPrice());
            //yyyy-MM-dd HH:mm:ss 去分秒
            String startTime = tm.getStartTime().substring(0,14)+"00";
            String endTime = tm.getEndTime().substring(0,14)+"00";
            timeEntity.setStartTime(DateUtil.strToTimestamp(startTime, DateUtil.PATTEN_FULL_TIME_TO_MINUTE));
            timeEntity.setEndTime(DateUtil.strToTimestamp(endTime, DateUtil.PATTEN_FULL_TIME_TO_MINUTE));
        }
        timeEntity.setEditTime(now);
        timeEntity.setEditUser(getLoginUser().getUserId());
        geoKgoCasesetRentTimeRepository.saveAndFlush(timeEntity);
        GeokgoRentTimeInsertModel returnTimeModel = GeokgoRentTimeInsertModel.changeToModel(timeEntity);
        logger.error(returnTimeModel.toString());
        return returnTimeModel;
    }

    /** 後台 - 取同樣服務案件上一周的預約時間List*/
    private List<GeokgoRentTimeInsertModel>findLastWeekTimeListBydate(String caseRentId ,Timestamp detalDate){
        List<GeokgoRentTimeInsertModel> rtnList = new ArrayList<>();
        try{
            Timestamp lastWeekDate = DateUtil.modifyDate(detalDate, 0, 0, -7);
            GeoKgoCasesetRentDate lastDate = geoKgoCasesetRentDateRepository.findByCaseRentIdAndDetailDate(caseRentId,lastWeekDate);
            if(lastDate != null){
                List<GeoKgoCasesetRentTime> lastTimeList = geoKgoCasesetRentTimeRepository.getTimeByRentDateId(lastDate.getRentDateId());
                 rtnList = lastTimeList.stream().filter(t -> t.getIsAbandon() == null )
                        .map(t -> {
                            GeoKgoCasesetRentTime newTime = new GeoKgoCasesetRentTime();
                            try {
                                newTime.setUsedUsersNum(t.getUsedUsersNum());
                                newTime.setUnitPrice(t.getUnitPrice());
                                newTime.setAvailableUserQouta(t.getAvailableUserQouta());
                                newTime.setUsedUsersNum(0);
                                newTime.setStartTime(DateUtil.modifyDate(t.getStartTime(), 0, 0, 7));
                                newTime.setEndTime(DateUtil.modifyDate(t.getEndTime(), 0, 0, 7));
                            } catch (ParseException e) {
                                logger.error("Find lastWeek Time List  modify Timesamp error");
                            }
                            return GeokgoRentTimeInsertModel.changeToModel(newTime);
                        }).collect(Collectors.toList());
                }
        }catch (ParseException e) {
            logger.error("Find lastWeek Time List  modify Timesamp error");
        }
        return rtnList;
    }

    private List<GeokgoRentTimeInsertModel> removeLockTimeList(List<GeokgoRentTimeInsertModel> lockTimeList ,List<GeokgoRentTimeInsertModel> newTimeList){
        Map<String, Integer> lockTimeMap = new HashMap();
        lockTimeList.stream().forEach(lt->lockTimeMap.put(lt.getStartTime(),lt.getIsLocked()));
        if(!lockTimeMap.containsValue(2)){//全天鎖定時間 isLocked = 2
            List<GeokgoRentTimeInsertModel> usableTimeList = newTimeList.stream().filter(nt -> !lockTimeMap.containsValue(nt.getStartTime())).collect(Collectors.toList());
            lockTimeList.addAll(usableTimeList);
        }
        return lockTimeList;
    }

    /**
     * 後台 - 場地案件檢視 - 可租借時段匯出EXCEL
     */
    public List<GeoCaseRentalCaseExcelVo> findCaseRentableData(CaseHandleSiteRentableExcelRq rq){
        List<GeoCaseRentalCaseExcelVo> excelVoList = geoCaseSetRentReposCustom.findAllRentableCase(
                false,
                rq.getCasesetName(),
                rq.getCasesetId(),
                rq.getOrganId(),
                rq.getUnitId(),
                rq.getSiteMainId(),
                rq.getPeriodFrom(),
                rq.getPeriodTo(),
                rq.getSiteType());
        for(GeoCaseRentalCaseExcelVo vo : excelVoList){
            //機關名稱
            KgoOrgan organEntity = kgoOrganRepository.findByOrganId(vo.getOrganId());
            if(organEntity != null ) vo.setOrganName(organEntity.getOrganName());
            //科室名稱
            KgoUnit unitEntity = kgoUnitRepository.findByIdOrganIdAndIdUnitId(vo.getOrganId(), vo.getUnitId());
            if(unitEntity != null ) vo.setUnitName(unitEntity.getUnitName());

            List<GeoKgoCasesetRentTime> timeList = geoCaseSetRentReposCustom.findRentableTimeByRentDate(false, vo.getRentDateId());
            List<String> timeStrList = timeList.stream().map(time->{
                StringBuilder timeSb = new StringBuilder();
                if( time.getAvailableUserQouta() != null ){
                    timeSb.append(DateUtil.timestampToString(time.getStartTime(), DateUtil.PATTEN_HOUR_MINUTE))
                            .append("~ ")
                            .append(DateUtil.timestampToString(time.getEndTime(), DateUtil.PATTEN_HOUR_MINUTE))
                            .append(" (剩餘:")
                            .append(time.getAvailableUserQouta() - time.getUsedUsersNum())
                            .append("位 )");
                }else{
                    timeSb.append(DateUtil.timestampToString(time.getStartTime(), DateUtil.PATTEN_HOUR_MINUTE))
                           .append("~ ")
                           .append(DateUtil.timestampToString(time.getEndTime(), DateUtil.PATTEN_HOUR_MINUTE));
                }
                return timeSb.toString();
            }).collect(Collectors.toList());
            vo.setTimeList(timeStrList);
        };
        return excelVoList;
    }

    /**
     * 後台 - 場地案件檢視 - 已租借時段匯出EXCEL
     */
    public List<GeoCaseRentalCaseExcelVo> findCaseRentedData(CaseHandleSiteRentableExcelRq rq){
        List<GeoCaseRentalCaseExcelVo> excelVoList = geoCaseSetRentReposCustom.findAllRentableCase(
                true,
                rq.getCasesetName(),
                rq.getCasesetId(),
                rq.getOrganId(),
                rq.getUnitId(),
                rq.getSiteMainId(),
                rq.getPeriodFrom(),
                rq.getPeriodTo(),
                rq.getSiteType());
        for(GeoCaseRentalCaseExcelVo vo : excelVoList){
            //機關名稱
            KgoOrgan organEntity = kgoOrganRepository.findByOrganId(vo.getOrganId());
            if(organEntity != null ) vo.setOrganName(organEntity.getOrganName());
            //科室名稱
            KgoUnit unitEntity = kgoUnitRepository.findByIdOrganIdAndIdUnitId(vo.getOrganId(), vo.getUnitId());
            if(unitEntity != null ) vo.setUnitName(unitEntity.getUnitName());

            List<GeoKgoCasesetRentTime> timeList = geoCaseSetRentReposCustom.findRentableTimeByRentDate(true, vo.getRentDateId());
            List<String> timeStrList = timeList.stream().map(time->{
                StringBuilder timeSb = new StringBuilder();
                if( time.getAvailableUserQouta() != null ){
                    timeSb.append(DateUtil.timestampToString(time.getStartTime(), DateUtil.PATTEN_HOUR_MINUTE))
                            .append("~ ")
                            .append(DateUtil.timestampToString(time.getEndTime(), DateUtil.PATTEN_HOUR_MINUTE))
                            .append(" (已預約:")
                            .append(time.getUsedUsersNum())
                            .append("位 )");
                }else{
                    timeSb.append(DateUtil.timestampToString(time.getStartTime(), DateUtil.PATTEN_HOUR_MINUTE))
                            .append("~ ")
                            .append(DateUtil.timestampToString(time.getEndTime(), DateUtil.PATTEN_HOUR_MINUTE));
                }
                return timeSb.toString();
            }).collect(Collectors.toList());
            vo.setTimeList(timeStrList);
        };
        return excelVoList;
    }


// 前台 線上租借區塊

    /**
     * 前台- 線上預約租借清單查詢
     */
    public CaseSetRentMainSearchRs rentMainSearch(ApiRequest rq , CaseSetCategoryEnum caseType) {
        KgoApiException error = null;
        CaseSetRentMainSearchRs rs = new CaseSetRentMainSearchRs();
        GeoCasesetRentMainViewForm viewForm = new GeoCasesetRentMainViewForm();

        try {
            //查詢上架的場地與活動案件
            List<String> categorys = Arrays.asList(new String[]{caseType.getValue()});
            List<KgoCaseset> casesetList = kgoCasesetRepository.findRentalCaseset(categorys);
            List<GeoKgoCasesetRentModel> modelList = casesetList.stream().map(caseset -> {
                GeoKgoCasesetRentModel model = new GeoKgoCasesetRentModel();
                model.setCaseName(caseset.getCaseSetName());
                model.setCasesetCategory(caseset.getCasesetCategory());
                model.setOrganId(caseset.getOrgan());
                model.setCasesetId(caseset.getCaseSetId());
                KgoGroupLevel kgl = kgoGroupLevelRepository.getById(Integer.valueOf(caseset.getOrgan()));
                model.setOrganName(kgl.getName());
                return model;
            }).collect(Collectors.toList());
            viewForm.setRentMainList(modelList);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            logger.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SEARCH), e);
        } finally {
            setResultMessage(rq, rs, error);
        }

        return rs;
    }
    /**
     * 前台-線上預約查詢月曆 - 取得有建立案件的每日案件編號
     */
    public CaseSetSearchDateRs searchSiteDate(@RequestBody CaseSetSearchDateRq rq) {
        KgoApiException error = null;
        CaseSetSearchDateRs rs = new CaseSetSearchDateRs();
        CaseSetSearchDateViewForm viewForm = new CaseSetSearchDateViewForm();
        try {
            String caseSetId = rq.getCaseSetId();
            String siteMainId = rq.getSiteMainId();
            String firstDateStr = rq.getDateStr();
            Calendar cal = Calendar.getInstance();
            cal.setTime(DateUtil.strToDate(firstDateStr, DateUtil.PATTEN_YEAR_MONTH_DAY));
            String LastDateStr = DateUtil.dateToString(DateUtil.getLastMonthDay(cal), DateUtil.PATTEN_YEAR_MONTH_DAY);
            List<GeoCaseSetSearchDateModel> ModelList = geoCaseSetSiteReposCustom.searchDateByCaseSet(caseSetId, siteMainId, firstDateStr, LastDateStr);
            //跨日群組邏輯
            List<Map<String,String>> allDayGroup = createAllDayGroup(ModelList);
            viewForm.setAllDayGroup(allDayGroup);
            viewForm.setDateModels(ModelList);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            logger.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SEARCH), e);
        } finally {
            setResultMessage(rq, rs, error);
        }
        return rs;
    }

    /**
     * 前台 - 租借月曆 - 跨日案件集合
     */
    private List<Map<String,String>> createAllDayGroup( List<GeoCaseSetSearchDateModel> ModelList){
        List<Map<String,String>> allDayGroup = new ArrayList<>();
        Map<String,String> unit = new HashMap<>();
        for(GeoCaseSetSearchDateModel model:ModelList){
            if(model.getIsAllDay() == 1){
                if(!unit.containsKey("ids")) {
                    unit.put("ids",model.getRentDateId());
                    String timeId = geoKgoCasesetRentTimeRepository.getTimeByRentDateId(model.getRentDateId()).get(0).getRentTimeId();
                    unit.put("timeIds",timeId);
                }else{
                    unit.replace("ids",unit.get("ids")+","+model.getRentDateId());
                    String timeId = geoKgoCasesetRentTimeRepository.getTimeByRentDateId(model.getRentDateId()).get(0).getRentTimeId();
                    unit.replace("timeIds",unit.get("timeIds")+","+timeId);
                }
            }else{
                if(!unit.isEmpty()){
                    allDayGroup.add(unit);
                    unit = new HashMap<>();
                }
            }
        }
        return allDayGroup;
    }

    /**
     * 前台-線上預約查詢月曆 - 取得單日每個預約時間案件
     */
    public CaseSetSiteTimeQueryRs caseSetRentTimeQuery(CaseSetRentTimeQueryRq rq) {
        KgoApiException error = null;
        CaseSetSiteTimeQueryRs rs = new CaseSetSiteTimeQueryRs();
        CaseSetRentTimeQueryViewForm viewForm = new CaseSetRentTimeQueryViewForm();
        try {
            List<GeoKgoCasesetRentTime> timeEntityList = geoKgoCasesetRentTimeRepository.getTimeByRentDateId(rq.getRentDateId());
            //過濾已預約案件
             timeEntityList = timeEntityList.stream().filter(time->time.getIsLocked() == null).collect(Collectors.toList());
            List<GeokgoRentTimeInsertModel> modelList = GeokgoRentTimeInsertModel.changeListToQueryDateModel(timeEntityList);
            viewForm.setTimeList(modelList);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            logger.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SEARCH), e);
        } finally {
            setResultMessage(rq, rs, error);
        }
        return rs;
    }

    /**
     * 前台 - step2 to step3 場地預約資訊申請單初始畫面回傳
     * return 選擇的預約時段
     */
    public ApplyFormInitRs applyFormValid(ApplyFormValidTimeRq rq) {
        logger.info("CaseFormController  query siteRentTime imformation ...");
        KgoApiException error = null;
        ApplyFormInitRs rs = new ApplyFormInitRs();
        ApplyFormInitViewForm viewForm = new ApplyFormInitViewForm();
        //目前時段單選,跨日選擇為多個timeId ","分隔目前僅提供連續日
        String rentTimeIds = rq.getSiteRentTimeId();
        viewForm.setRentTimeIds(rentTimeIds);
        List<GeokgoRentTimeInsertModel> modelList = new ArrayList<>();
        try {
            Boolean invalid = false;
            String[] timeIdlist = rentTimeIds.split(",");
            StringBuilder validMsg = new StringBuilder("");
            for(String timeId : timeIdlist){
                GeoKgoCasesetRentTime rentTime = geoKgoCasesetRentTimeRepository.getById(timeId);
                GeoKgoCasesetRentDate rentDate = geoKgoCasesetRentDateRepository.queryByRentDateId(rentTime.getRentDateId());
                //判斷時間點是否已經預約
                if(rentTime.getAvailableUserQouta()!=null && rentTime.getUsedUsersNum() != null){
                    int lave = rentTime.getAvailableUserQouta() - rentTime.getUsedUsersNum();
                    if(lave == 0 ){
                        validMsg.append("本活動預約人數已額滿。\n");
                        invalid = true;
                    }
                }
                if(rentTime.getIsLocked() != null){
                    validMsg.append("該場地預約時段，已經被預約");
                    invalid = true;
                }
                //判斷時段是否已開放預約
                Timestamp now = new Timestamp(System.currentTimeMillis());
                //預約時間設定為 n 天 HH:mm:ss 小時
                String[] earliestTime = rentDate.getEarliestTime().split(":");
                String[] lastiestTime = rentDate.getLastiestTime().split(":");
                Timestamp earliestDay = DateUtil.modifyDate(rentTime.getStartTime(), 0, 0, -rentDate.getEarliestDay(), -Integer.valueOf(earliestTime[0]), -Integer.valueOf(earliestTime[1]),  -Integer.valueOf(earliestTime[2]));
                Timestamp lastiestDay = DateUtil.modifyDate(rentTime.getStartTime(), 0, 0, -rentDate.getLastiestDay(), -Integer.valueOf(lastiestTime[0]), -Integer.valueOf(lastiestTime[1]),  -Integer.valueOf(lastiestTime[2]));
                logger.info("Rental Time valided between "+ DateUtil.dateToString(earliestDay,DateUtil.PATTEN_FULL_TIME)+
                        "and "+DateUtil.dateToString(lastiestDay,DateUtil.PATTEN_FULL_TIME));
                if(now.compareTo(earliestDay)< 0 ){
                    validMsg.append("預約新增失敗 尚未開放預約時段");
                    invalid = true;
                }
                if( now.compareTo(lastiestDay) > 0){
                    validMsg.append("預約新增失敗 已超過最晚可預約時段");
                    invalid = true;
                }
                if(invalid){
                    viewForm.setEarliestTime(DateUtil.dateToString(earliestDay,DateUtil.PATTEN_FULL_TIME));
                    viewForm.setLastiestTime(DateUtil.dateToString(lastiestDay,DateUtil.PATTEN_FULL_TIME));
                    viewForm.setValidMsg(validMsg.toString());
                    rs.setData(viewForm);
                    return rs;
                }

            }
            String casesetId = geoCaseSetSiteReposCustom.findCasesetIdByTimeId(timeIdlist[0]);
            KgoCaseset caseset = kgoCasesetRepository.getById(casesetId);
            GeoKgoCaseSetSiteMain siteMainEntity = geoCaseSetSiteReposCustom.findSiteNameByTimeId(timeIdlist[0]);
            logger.info("stieName : " + siteMainEntity.getSiteName());
            String[] rentColumnStr = new String[]{"租借位置","租借時間","租借金額"};
            if(CaseSetCategoryEnum.ACTIVITY.getValue().equals(siteMainEntity.getSiteType())){
                rentColumnStr = new String[]{"活動位置","活動時間","活動報名金額"};
            }
            List<GeoKgoCasesetRentTime> rentTimeList = Arrays.stream(timeIdlist).map(id->geoKgoCasesetRentTimeRepository.getById(id))
                    .sorted(Comparator.comparing(GeoKgoCasesetRentTime::getStartTime))
                    .collect(Collectors.toList());
            String rentTimeStart = DateUtil.dateToString(rentTimeList.get(0).getStartTime(), DateUtil.PATTEN_FULL_TIME_TO_MINUTE);
            String rentTimeEnd = DateUtil.dateToString(rentTimeList.get(rentTimeList.size()-1).getEndTime(),DateUtil.PATTEN_FULL_TIME_TO_MINUTE);
            String rentTimeStr = rentTimeStart + " ~ " + rentTimeEnd;
            String amountStr;
            if(caseset.getNeedPay()){
                AtomicInteger amount = new AtomicInteger();
                rentTimeList.stream().forEach(model-> amount.addAndGet(model.getUnitPrice()));
                amountStr = amount + "元";
            }else{
                amountStr = "免繳費";
            }

            List<ColumnViewForm> columnDataList = new ArrayList<>();
            columnDataList.add(new ColumnViewForm("siteName",rentColumnStr[0],"TextBox",siteMainEntity.getSiteName(),true));
            columnDataList.add(new ColumnViewForm("rentTime",rentColumnStr[1],"AddressTextBox",rentTimeStr,true));
            columnDataList.add(new ColumnViewForm("amount",rentColumnStr[2],"TextBox",amountStr,true));
            viewForm.setColumnData(columnDataList);

            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            logger.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoBackEndApiError.UNKNOWN_EXCEPTION), e);
        } finally {
            setResultMessage(rq, rs, error);
        }
        return rs;
    }

    /** 前台 - step3 依照輸入欄位取得相關訊息 */
    public ApiBaseResponse<ApplyFormExInfoForm> applyCaseInfo(ApplyFormExInfoRq rq){
        ApplyFormExInfoForm viewForm = new ApplyFormExInfoForm();
        ApiBaseResponse<ApplyFormExInfoForm> rs = new ApiBaseResponse<>();
        KgoApiException error = null;
        String casesetId = rq.getCasesetId();
        try{
            //退費相關資訊
            if(casesetId.equals(SpringUtil.getProperty("applyRefund.caseset"))){
                List<SaveActionCColumnViewForm> columnData = rq.getColumnData();
                SaveActionCColumnViewForm refundData = columnData.stream()
                        .filter(data -> data.getColumnId().equals("refurnCaseId")).findAny()
                        .orElseThrow(()->new KgoApiException(new ErrorResult(KgoFrontEndApiError.DATA_FORMAT_ERROR)));
                String refundCaseId = refundData.getValue();
                GeoKgoCaseRentRelation rentRelationEntity = geoCaseRentRelationRepos.findByCaseId(refundCaseId);

                Timestamp firstRentDate = rentRelationEntity.getStartTime();
                GeoKgoCaseRentPayment paymentEntity = geoKgoRentPaymentRepos.findByCaseId(refundCaseId);
                Integer payAmount = paymentEntity.getPayAmount();
                Long remaining = ( firstRentDate.getTime() - System.currentTimeMillis() ) / ( 1000 * 60 * 60 * 24 );
                if(remaining < 0 ) throw new KgoApiException(new ErrorResult(KgoFrontEndApiError.REFUND_OVER_TIME));
                Integer percent = refundRatioRepository.findRefundPercent(rentRelationEntity.getCasesetId(),remaining.intValue());
                if(percent == null) percent = 100;
                String deductStr = percent+"%";
                String refundAmount = String.valueOf(payAmount * percent / 100);
                List<ColumnViewForm> columnDataList = new ArrayList<>();
                columnDataList.add(new ColumnViewForm("payAmount","實際繳費金額","Num",String.valueOf(payAmount),true));
                columnDataList.add(new ColumnViewForm("deductPercent","服務退費比例","TextBox",deductStr,true));
                columnDataList.add(new ColumnViewForm("refundAmount","預計退費金額","Num",refundAmount,true));
                viewForm.setColumnData(columnDataList);
            }
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            logger.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoBackEndApiError.UNKNOWN_EXCEPTION), e);
        } finally {
            setResultMessage(rq, rs, error);
        }
        return rs;
    }


    /** 前台 - 退費申請流程檢核 step2-3*/
    public KgoFrontEndApiError validateRefurnCase(String refurnCaseId ){
        //return Msg
        try {
            GeoKgoCaseRentPayment paymentEntity = geoKgoRentPaymentRepos.findByCaseId(refurnCaseId);
            if(paymentEntity == null ){
                return KgoFrontEndApiError.REFUND_NO_CASE;
            }
            if ( RentStatusEnum.YET.getValue().equals(paymentEntity.getPaymentStatus())){
                return KgoFrontEndApiError.REFUND_PAYERROR;
            }
            KgoCaseMain refundCaseMain = kgoCaseMainRepository.findByCaseId(refurnCaseId);
            KgoCaseset refundCaseset = kgoCasesetRepository.getById(refundCaseMain.getCaseSetId());
            Integer deadLineDays = refundCaseset.getRefundDeadline();
            GeoKgoCaseRentRelation rentRelation = geoCaseRentRelationRepos.findByCaseId(refurnCaseId);
            Timestamp timeStart = rentRelation.getStartTime();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            if (now.compareTo(DateUtil.modifyDate(timeStart, 0, 0, -deadLineDays)) > 0 ){
                return KgoFrontEndApiError.REFUND_OVER_TIME;
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            return KgoFrontEndApiError.UNKNOWN_EXCEPTION;
        }
        return null;
    }


    public CaseSetRentalCategoryGrid findRentalCaseDetail(KgoCaseMain caseMain){
        CaseSetRentalCategoryGrid grid = new CaseSetRentalCategoryGrid();
        grid.setRentalCase(true);
        String caseId = caseMain.getCaseId();

        GeoKgoCaseRentRelation relationEntity = geoCaseRentRelationRepos.findByCaseId(caseId);
        grid.setRentTimeStart(DateUtil.timestampToString(relationEntity.getStartTime(),DateUtil.PATTEN_FULL_TIME_TO_MINUTE));
        grid.setRentTimeEnd(DateUtil.timestampToString(relationEntity.getEndTime(),DateUtil.PATTEN_FULL_TIME_TO_MINUTE));

        GeoKgoCaseSetSiteMain siteMainEntity = geoCaseSetSiteRepos.getById(relationEntity.getSiteMainId());
        grid.setSiteName(siteMainEntity.getSiteName());

        KgoOrgan organEntity = kgoOrganRepository.findByOrganId(siteMainEntity.getOrganId());
        grid.setCaseOrganName(organEntity.getOrganName());

        KgoCaseset kgocaseset = kgoCasesetRepository.getById(caseMain.getCaseSetId());
        grid.setCategory(kgocaseset.getCasesetCategory());
        boolean needPay = kgocaseset.getNeedPay() == null ? false : kgocaseset.getNeedPay();
        if(needPay){
            GeoKgoCaseRentPayment paymentEntity = geoKgoRentPaymentRepos.findByCaseId(caseId);
            if(paymentEntity == null ){
                grid.setAmount("待審核後顯示");
            }else{
                grid.setAmount(paymentEntity.getPayAmount() + " 元 ");
            }
        }else{
            grid.setAmount("免繳費");
        }

        return grid;
    }


// 後端Service呼叫

    /**建立租借清單關聯資料*/
    @Transactional(propagation = Propagation.SUPPORTS)
    public GeoKgoCaseRentRelation createRentRelation(String rentTimeIds , String caseId ,KgoCaseset kgoCaseset){
        //跨日字串
        String[] timeList = rentTimeIds.split(",");
        List<GeoKgoCasesetRentTime> timeEntity = Arrays.stream(timeList).map(id -> geoKgoCasesetRentTimeRepository.getById(id))
                            .sorted(Comparator.comparing(GeoKgoCasesetRentTime::getStartTime)).collect(Collectors.toList());
        if(CaseSetCategoryEnum.SITE.getValue().equals(kgoCaseset.getCasesetCategory())){
            //場地預約鎖定
            timeEntity.stream().forEach(entity->entity.setIsLocked(1));
            geoKgoCasesetRentTimeRepository.saveAll(timeEntity);
        }
        //排序日期起日取第一 : 排序日期迄日取最後一
        Timestamp startTime = timeEntity.get(0).getStartTime();
        Timestamp endTime = timeEntity.stream().sorted(Comparator.comparing(GeoKgoCasesetRentTime::getEndTime).reversed()).collect(Collectors.toList())
                                               .get(0).getEndTime();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        GeoKgoCaseRentRelation relationEntity = new GeoKgoCaseRentRelation();
        relationEntity.setCaseId(caseId);
        relationEntity.setCasesetId(kgoCaseset.getCaseSetId());
        GeoCaseSetSiteMainModel siteMain = geoCaseSetRentReposCustom.findSiteMainByTimeId(timeList[0]);
        relationEntity.setSiteMainId(siteMain.getSiteMainId());
        relationEntity.setRentTimeId(rentTimeIds);
        relationEntity.setStartTime(startTime);
        relationEntity.setEndTime(endTime);
        relationEntity.setRentStatus(RentStatusEnum.PROC.getValue());
        relationEntity.setCreateTime(now);
        relationEntity.setEditTime(now);
        geoCaseRentRelationRepos.save(relationEntity);
        return relationEntity;
    }
    //更新預約狀態
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean updateRentRelation(RentStatusEnum status , String caseId ){
        GeoKgoCaseRentRelation relationEntity = geoCaseRentRelationRepos.findByCaseId(caseId);
        if(relationEntity == null) return false;
        Timestamp now = new Timestamp(System.currentTimeMillis());
        relationEntity.setRentStatus(status.getValue());
        relationEntity.setEditTime(now);

        geoCaseRentRelationRepos.save(relationEntity);
        return true;
    }

    /**建立租借繳費資料*/
    @Transactional(propagation = Propagation.SUPPORTS)
    public GeoKgoCaseRentPayment createRentRentalPayment(Integer deadLineDays , Integer lastCharge, String caseId, String rentTimeIds, int payDiscount, String payAssurer){
        int payPrice = 0;
        if(rentTimeIds != null){
            String[] timeIds = rentTimeIds.split(",");
            for(String id : timeIds){
                GeoKgoCasesetRentTime timeEntity = geoKgoCasesetRentTimeRepository.getById(id);
                if(timeEntity!=null) payPrice += timeEntity.getUnitPrice();
            };
            logger.info("caseId : "+caseId+" total price :" + payPrice);
        }
        logger.info("price change to last charge : "+lastCharge);

        GeoKgoCaseRentPayment rentPayment = new GeoKgoCaseRentPayment();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        rentPayment.setRentPaymentId(createRentPaymentId());
        rentPayment.setPaymentStatus(RentStatusEnum.YET.getValue());
        rentPayment.setCaseId(caseId);
        rentPayment.setSysPayAmount(payPrice);
        rentPayment.setPayAmount(lastCharge != null ? lastCharge : payPrice);
        rentPayment.setPaymentDiscount(payDiscount);
        rentPayment.setPayAssurer(payAssurer);
        rentPayment.setCreateTime(now);
        rentPayment.setEditTime(now);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(now.getTime());
        cal.add(Calendar.DAY_OF_MONTH, deadLineDays);
        rentPayment.setPayDeadline(new Timestamp(cal.getTimeInMillis()));

        geoKgoRentPaymentRepos.save(rentPayment);
        return rentPayment;
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean updateRentRentalPayment(RentStatusEnum status, GeoPaymentTypeEnum payType, String caseId, Timestamp updateTime, String receiptNum, String editUser){
        GeoKgoCaseRentPayment paymentEntity = geoKgoRentPaymentRepos.findByCaseId(caseId);
        if(paymentEntity == null) return false;
        Timestamp now = new Timestamp(System.currentTimeMillis());
        paymentEntity.setPaymentStatus(status.getValue());
        if(status.equals(RentStatusEnum.FIN)){
            paymentEntity.setPayTime(updateTime);
        }else if (status.equals(RentStatusEnum.FRFD))
            paymentEntity.setRefundTime(updateTime);
        if(receiptNum != null){
            paymentEntity.setReceiptNum(receiptNum);
        }
        paymentEntity.setPayType(payType.getPayTypeCode());
        paymentEntity.setEditUser(editUser);
        paymentEntity.setEditTime(now);
        geoKgoRentPaymentRepos.save(paymentEntity);
        return true;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void cancelAfterRefound(String caseId){
        KgoCaseMain caseMain = kgoCaseMainRepository.findByCaseId(caseId);

        //儲存[KGO_CASE_MAIN]
        Timestamp currentTime = DateUtil.getCurrentTimestamp();
        caseMain.setResult("退費取消預約");
        caseMain.setUpdateTime(currentTime);
        caseMain.setUpdateUser("SYSTEM");
        kgoCaseMainRepository.save(caseMain);

        //更新預約狀態
        GeoKgoCaseRentRelation rentRelation = geoCaseRentRelationRepos.findByCaseId(caseId);
        rentRelation.setEditTime(currentTime);
        rentRelation.setRentStatus(RentStatusEnum.RCANL.getValue());

        geoCaseRentRelationRepos.save(rentRelation);


        GeoKgoCaseSetSiteMain siteMain = geoCaseSetSiteMainRepos.getById(rentRelation.getSiteMainId());
        String category = siteMain.getSiteType();
        String[] rentTimeIds = rentRelation.getRentTimeId().split(",");
        for(String timeid : rentTimeIds) {
            Boolean isUpdate = true;
            if("activity".equals(category)){
                isUpdate = geoCaseSetSiteTimeReposCustom.cancelCaseRelease(timeid );
            }else if ("site".equals(category)){
                isUpdate = geoCaseSetSiteTimeReposCustom.cancelSiteCase(timeid );
            }
            if(!isUpdate) {
                logger.info("caseId : "+caseId + " rentStatus :"+rentRelation.getRentStatus() +" cant update rentTime release ");
            }
        }
    }

    /** 線上預約時間檢核*/
    private void validateInsertData(GeoKgoRentCaseRq rq){
        List<GeokgoRentDateInsertModel> dateModelList = rq.getRentDateModelList();
        KgoCaseset caseset = kgoCasesetRepository.getById(rq.getRentCaseSetModel().getCaseSetId());
        boolean needPay = caseset.getNeedPay();
        if(dateModelList != null){
            for(GeokgoRentDateInsertModel datemodel : dateModelList ){
                List<GeokgoRentTimeInsertModel> timeList = datemodel.getDetailTimeList();
                if(timeList != null){
                    boolean allDayCase = false;
                    if(timeList.stream().anyMatch(time-> time.getIsLocked() != null && time.getIsLocked() == 2)){
                        datemodel.setIsEnable(0);
                        datemodel.setLastiestDay(null);
                        datemodel.setLastiestTime(null);
                        datemodel.setEarliestDay(null);
                        datemodel.setEarliestTime(null);
                    }else{
                        List<int[]> usedTimeList = new ArrayList<>();
                        for(GeokgoRentTimeInsertModel timeModel : timeList){
                            try{
                                int startTime =  DateUtil.strToDate(timeModel.getStartTime(),DateUtil.PATTEN_FULL_TIME).getHours();
                                int endTime =  DateUtil.strToDate(timeModel.getEndTime(),DateUtil.PATTEN_FULL_TIME).getHours();
                                for(int[] usedTime : usedTimeList ){
                                    boolean invalid = startTime > usedTime[0] && startTime < usedTime[1] ? true
                                                     : endTime > usedTime[0] && endTime < usedTime[1] ? true
                                                     : false;
                                    if( invalid ) {
                                        logger.info(timeModel.getStartTime() + " - " + timeModel.getEndTime() + " 預約時段衝突");
                                        throw new KgoApiException(new ErrorResult(KgoBackEndApiError.RENTAL_TIME_ERROR));
                                    }
                                }
                                usedTimeList.add(new int[]{startTime,endTime});
                            } catch (ParseException e) {
                                e.printStackTrace();
                                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.DATA_FORMAT_ERROR));
                            }
                            if(needPay){
                                if(timeModel.getUnitPrice() <= 0 ) {
                                    logger.info("casesetId :" + caseset.getCaseSetId() + " needPay ... price cant be zero ");
                                    throw new KgoApiException(new ErrorResult(KgoBackEndApiError.DATA_FORMAT_ERROR));
                                }
                            }
                        }
                    }
                }//timeList != null
            }//for(dateModelList)
        }
    }



    /** 更新預約時段人數 (無法預約回傳false) */
    @Transactional
    public boolean updateUserReserve(String[] rentTimeIds){
        boolean usable = false;
        for(String timeId : rentTimeIds ){
            usable = geoCaseSetSiteTimeReposCustom.updateUserReserve(timeId,1);
        }
        return usable;
    }

    /** GEO  申請退費程序 計算退費金額 > 呼叫市民科技退費API > 等待繳費更新狀態*/
    @Transactional(rollbackFor = KgoApiException.class, propagation = Propagation.SUPPORTS)
    public void applyRefundService(String applyCaseId , String refurnCaseId , Integer cityCoin , Timestamp applyDate, Integer refundDiscount, Integer refundAmount ,String refundAssurer)throws Exception{
        //計算退費金額
        logger.info("rentServie applyRefundService ... start... ");
        try{
            GeoKgoCaseRentRelation rentRelation = geoCaseRentRelationRepos.findByCaseId(refurnCaseId);
            Timestamp firstRentDate = rentRelation.getStartTime();
            String rentalCasesetId = rentRelation.getCasesetId();
            GeoKgoCaseRentPayment paymentEntity = geoKgoRentPaymentRepos.findByCaseId(refurnCaseId);
            Integer payAmount = paymentEntity.getPayAmount();
            String receiptNum = paymentEntity.getReceiptNum();
            logger.info("refund Servcie detail applyCaseid : "+applyCaseId+" - refurnCaeId :"+refurnCaseId+" - cityCoin :"+cityCoin
            +" - orgin PayAmount :"+payAmount);

            //距離預約時間剩餘天數
            Long remaining = ( firstRentDate.getTime() - applyDate.getTime() ) / ( 1000 * 60 * 60 * 24 );
            Integer percent = refundRatioRepository.findRefundPercent(rentalCasesetId,remaining.intValue());
            if( percent == null) percent = 100;
            paymentEntity.setRefundAssurer(refundAssurer);
            paymentEntity.setRefundDiscount(refundDiscount);
            paymentEntity.setDeductPercent(percent);
            paymentEntity.setActualRefundAmount(refundAmount);
            geoKgoRentPaymentRepos.save(paymentEntity);

            logger.info("remaining days :" + remaining + " - discount percent :" + percent + " - refundAmount :" + refundAmount + " receiptNum :" + receiptNum );
            geoKcgPaymentService.doRefund( refurnCaseId, refundAmount, String.valueOf(cityCoin), receiptNum );
        }catch (Exception e){
            throw new KgoApiException("applyRefundService error " + e.getMessage(), e);
        }
    }

    public void updateCaseWithCityMember(GeoCityCoinMembershipViewForm form, String caseId){
        logger.info("save cityMemberUUID to PaymentCaseID ");
        GeoKgoCaseRentPayment paymentEntity = geoKgoRentPaymentRepos.findByCaseId(caseId);
        paymentEntity.setSubUUid(form.getUuid());
        geoKgoRentPaymentRepos.save(paymentEntity);
    }

    private String createRentMainId() {
        String KeyWord = RENT_MAIN_ID_PREFIX + DateUtil.timestampToString(DateUtil.getCurrentTimestamp(), DateUtil.PATTEN_YEAR_MONTH_DAY_NO_HYPHEN);
        String nextId = geoKgoCasesetRentMainRepository.getIdCountStr(KeyWord.length() + 1, KeyWord);
        nextId = nextId == null ? "00000" : String.format("%05d", Integer.valueOf(nextId));
        return KeyWord + nextId;
    }

    private List<String> createRentDateId(int createNum) {
        String keyWord = RENT_DATE_ID_PREFIX + DateUtil.timestampToString(DateUtil.getCurrentTimestamp(), DateUtil.PATTEN_YEAR_MONTH_DAY_NO_HYPHEN);
        String nextId = geoKgoCasesetRentDateRepository.getIdCountStr(keyWord.length() + 1, keyWord);
        if (nextId == null) nextId = "00000";
        return createIdList(createNum, nextId, keyWord);
    }

    private List<String> createRentTimeId(int createNum) {
        String keyWord = RENT_TIME_ID_PREFIX + DateUtil.timestampToString(DateUtil.getCurrentTimestamp(), DateUtil.PATTEN_YEAR_MONTH_DAY_NO_HYPHEN);
        String nextId = geoKgoCasesetRentTimeRepository.getIdCountStr(keyWord.length() + 1, keyWord);
        if (nextId == null) nextId = "00000";
        return createIdList(createNum, nextId, keyWord);
    }

    private String createRentPaymentId(){
        String keyWord = RENT_PAYMENT_ID_PREFIX + DateUtil.timestampToString(DateUtil.getCurrentTimestamp(), DateUtil.PATTEN_YEAR_MONTH_DAY_NO_HYPHEN);
        String nextId = geoKgoRentPaymentRepos.getIdCountStr(keyWord.length() + 1, keyWord);
        nextId = nextId == null ? "00000" : String.format("%05d", Integer.valueOf(nextId));
        return keyWord + nextId;
    }

    private List<String> createIdList(int createNum, String nextId, String keyWord) {
        List<String> idList = new ArrayList<>();
        int idNum = Integer.valueOf(nextId);
        idList.add(keyWord + String.format("%05d", idNum));
        IntStream.rangeClosed(1, createNum - 1).forEach(i -> {
            int newNum = idNum + i;
            idList.add(keyWord + String.format("%05d", newNum));
        });
        return idList;
    }


    private ComboBox getUnitSiteComboBox(List siteList, String defaultUser) {
        String siteId = geoCaseSetSiteRepos.getFisrtSiteByUser(defaultUser);
        return kgoServiceHelper.getComboBox(siteList, "siteName", "siteMainId", siteId, ComboBoxStatusEnum.ALL.getCode(), false);
    }

    private ComboBox getUnitComboBox(String organId, String defaultValue) {
        return organUnitManagementServiceHelper.getUnitComboBoxByOrganId(organId, defaultValue, ComboBoxStatusEnum.ALL.getCode());
    }


    private BackendLoginUserInfo getLoginUser() {
        BackendLoginUserInfo loginUser = KgoUtil.getBackendLoginUser();
//        if (KgoServiceHelper.getInstance().isDevMode()) {
//        BackendLoginUserInfo loginUser = new BackendLoginUserInfo();
//        loginUser.setUserId("chance");
//        loginUser.setOrgan("397140100P");
//        loginUser.setName("朱科安");
//        loginUser.setUnit("A890");
//        List<String> roles = new ArrayList<>();
//        roles.add("ADMIN");
//        roles.add("CASE_M");
//        roles.add("UNIT_A");
//        roles.add("UNIT_M");
//        roles.add("UNIT_U");
//        loginUser.setRoles(roles);
//        loginUser.setJwtToken("");
//        loginUser.setLoginAuthTokenType(LoginAuthTokenType.BASIC);
//        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
//        Date date = new Date();
//        sdFormat.format(date);
//        loginUser.setLoginTime(date);
//        }
        return loginUser;
    }

}
