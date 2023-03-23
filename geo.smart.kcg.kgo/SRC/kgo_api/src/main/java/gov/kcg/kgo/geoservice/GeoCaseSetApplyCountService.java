package gov.kcg.kgo.geoservice;

import gov.kcg.kgo.enums.backend.ApplyTypeEnum;
import gov.kcg.kgo.enums.backend.CaseFlowTypeEnum;
import gov.kcg.kgo.enums.common.SuccessMsg;
import gov.kcg.kgo.enums.error.CityApiError;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.geoentity.GeoKgoCaseSetApplyCount;
import gov.kcg.kgo.geoentity.GeoKgoCasesetApplyCountRule;
import gov.kcg.kgo.geoentity.GeoKgoCasesetAssociate;
import gov.kcg.kgo.geoenum.GeoCaseSetApplyCountType;
import gov.kcg.kgo.geomodel.GeoBidCaseListQueryDataGridModel;
import gov.kcg.kgo.geomodel.GeoCaseSetApplyCountModel;
import gov.kcg.kgo.geomodel.GeoCaseSetApplyCountRankModel;
import gov.kcg.kgo.geomodel.GeoCaseSetApplyRankModel;
import gov.kcg.kgo.georepository.GeoKgoCaseSetApplyRepository;
import gov.kcg.kgo.georepository.GeoKgoCasesetApplyCountRuleRepository;
import gov.kcg.kgo.georepository.GeoKgoCasesetAssociateRepository;
import gov.kcg.kgo.georepository.custom.GeoCaseSetApplyCountReposCustom;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoCaseSetApplyCountRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoCaseSetApplyRankDetailListRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoCaseSetApplyRankRq;
import gov.kcg.kgo.geoviewmodel.backend.rs.GeoCaseSetApplyCountRs;
import gov.kcg.kgo.geoviewmodel.backend.rs.GeoCaseSetApplyRankRs;
import gov.kcg.kgo.geoviewmodel.backend.rs.GeoCaseSetApplyRankRuleRs;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoCaseSetApplyCountViewForm;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoCaseSetApplyRankViewForm;
import gov.kcg.kgo.geoviewmodel.frontend.rq.GeoFrontendCaseSetAssociateQueryRq;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoCaseSetApplyRankDetailListRs;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoCaseSetApplyRankDetailListViewForm;
import gov.kcg.kgo.model.KgoCasesetType;
import gov.kcg.kgo.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * GEO 20211005 add
 * 服務申辦統計-申辦服務名次資料 API Service.
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class GeoCaseSetApplyCountService extends GeoBaseService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoCaseSetApplyCountService.class);

    @Autowired
    private GeoCaseSetApplyCountReposCustom geoCaseSetApplyCountReposCustom;

    @Autowired
    private GeoKgoCaseSetApplyRepository geoKgoCaseSetApplyRepository;

    @Autowired
    private GeoKgoCasesetApplyCountRuleRepository geoKgoCasesetApplyCountRuleRepository;

    @Autowired
    private GeoKgoCasesetAssociateRepository geoKgoCasesetAssociateRepository;

    /**
     * GEO 20211005 add
     * 服務申辦統計-取得申辦服務名次資料
     *
     * @return the list
     */
    public GeoCaseSetApplyCountRs getApplyCountList(GeoCaseSetApplyCountRq rq) {
        GeoCaseSetApplyCountRs rs = new GeoCaseSetApplyCountRs();
        GeoCaseSetApplyCountViewForm viewForm = new GeoCaseSetApplyCountViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            GeoCaseSetApplyCountType type = GeoCaseSetApplyCountType.valueOfCode(rq.getSearchRangeType());
            String dateStartStr;
            String dateEndStr;
            if (type.equals(GeoCaseSetApplyCountType.CUSTOM_DATE) &&
                    (rq.getDateStart() == null || rq.getDateStart().isEmpty() ||
                            rq.getDateEnd() == null || rq.getDateEnd().isEmpty())) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
            }
            if (type.equals(GeoCaseSetApplyCountType.CUSTOM_DATE)) {
                dateStartStr = rq.getDateStart();
                dateEndStr = rq.getDateEnd();
            } else {
                dateStartStr = getDateStart(type, DateUtil.PATTEN_YEAR_MONTH_DAY);
                dateEndStr = DateUtil.dateToString(new Date(), DateUtil.PATTEN_YEAR_MONTH_DAY);
            }
            Date dateStart = DateUtil.getStartOfDay(dateStartStr, DateUtil.PATTEN_YEAR_MONTH_DAY, DateUtil.PATTEN_FULL_TIME_MS);
            Date dateEnd = DateUtil.getEndOfDay(dateEndStr, DateUtil.PATTEN_YEAR_MONTH_DAY, DateUtil.PATTEN_FULL_TIME_MS);
            LOGGER.info("dateStart :" + DateUtil.dateToString(dateStart, DateUtil.PATTEN_FULL_TIME));
            LOGGER.info("dateEnd :" + DateUtil.dateToString(dateEnd, DateUtil.PATTEN_FULL_TIME));
            List<GeoCaseSetApplyCountRankModel> countRankModelList = new ArrayList<>();
            List<GeoCaseSetApplyCountModel> countModelList = geoCaseSetApplyCountReposCustom.getApplyCountListByRange(dateStart, dateEnd, null, rq.getCaseSetStatus());
            if (countModelList != null && countModelList.size() > 0) {
                GeoCaseSetApplyCountRankModel countRankModel = null;
                int rank = 0;
                for (int i = 0; i < countModelList.size(); i++) {
                    if (rank >= rq.getSearchRank()) break;
                    GeoCaseSetApplyCountModel model = countModelList.get(i);
                    if (i == 0 || (!model.getApplyCount().equals(countModelList.get(i - 1).getApplyCount()))) {
                        rank += 1;
                        countRankModel = new GeoCaseSetApplyCountRankModel();
                        countRankModel.setDataDetailList(new ArrayList<>());
                        countRankModelList.add(countRankModel);
                    }
                    countRankModel.setApplyCountRank(rank);
                    countRankModel.getDataDetailList().add(model);
                } // for (int i = 0; i < countModelList.size(); i++)
            } //if (countModelList != null && countModelList.size() > 0)
            viewForm.setDataList(countRankModelList);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(CityApiError.UNKNOWN_EXCEPTION), e);
        } finally {
            setResultMessage(null, rs, error);
        } //try
        return rs;
    } //getApplyCountListByRange

    private String getDateStart(GeoCaseSetApplyCountType type, String inputPattern) {
        String dateStartStr = null;
        switch (type) {
            case DAY:
                dateStartStr = DateUtil.dateToString(new Date(), inputPattern);
                break;
            case WEEK:
                dateStartStr = DateUtil.getBeforeDate(new Date(), 7, inputPattern);
                break;
            case MONTH:
                dateStartStr = DateUtil.getBeforeDate(new Date(), 30, inputPattern);
                break;
        }
        return dateStartStr;
    } //getDateStart

    /**
     * GEO 202111123 add
     * 後台-服務申辦統計:設定前台統計頻率，儲存排序
     *
     * @param rq
     * @return
     */
    public GeoCaseSetApplyRankRuleRs saveApplyCountRankRule(GeoCaseSetApplyCountRq rq) {
        GeoCaseSetApplyRankRuleRs rs = new GeoCaseSetApplyRankRuleRs();
        GeoCaseSetApplyRankViewForm viewForm = new GeoCaseSetApplyRankViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            geoKgoCasesetApplyCountRuleRepository.deleteAll();
            GeoKgoCasesetApplyCountRule rule = new GeoKgoCasesetApplyCountRule();
            rule.setCaseSetStatus(rq.getCaseSetStatus());
            rule.setDateStart(rq.getDateStart());
            rule.setDateEnd(rq.getDateEnd());
            rule.setSearchRank(rq.getSearchRank() == null ? 10 : rq.getSearchRank());
            rule.setSearchRangeType(rq.getSearchRangeType());
            rule = geoKgoCasesetApplyCountRuleRepository.save(rule);
            viewForm.setMsg(SuccessMsg.SAVE.getMsg());
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SAVE), e);
        } finally {
            setResultMessage(null, rs, error);
        } //try
        return rs;
    } //saveApplyCountRankRule


    /**
     * GEO 20211005 add
     * 服務申辦統計-設定前台統計頻率，儲存排序
     *
     * @param rq
     * @return
     */
    public GeoCaseSetApplyRankRs saveApplyCountRank(GeoCaseSetApplyRankRq rq) {
        GeoCaseSetApplyRankRs rs = new GeoCaseSetApplyRankRs();
        GeoCaseSetApplyRankViewForm viewForm = new GeoCaseSetApplyRankViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            if (rq.getDataList() != null) {
                List<GeoKgoCaseSetApplyCount> entityList = new ArrayList<>();
                for (GeoCaseSetApplyRankModel rankModel : rq.getDataList()) {
                    if (rankModel.getCasesetRank() == null || rankModel.getCasesetRank() <= 0 ||
                            rankModel.getCasesetId() == null || rankModel.getCasesetId().isEmpty()) {
                        throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
                    }
                    GeoKgoCaseSetApplyCount entity = new GeoKgoCaseSetApplyCount();
                    entity.setCasesetId(rankModel.getCasesetId());
                    entity.setCasesetRank(rankModel.getCasesetRank());
                    entityList.add(entity);
                }
                geoKgoCaseSetApplyRepository.deleteAll();
                geoKgoCaseSetApplyRepository.saveAll(entityList);
                viewForm.setMsg(SuccessMsg.SAVE.getMsg());
            }
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SAVE), e);
        } finally {
            setResultMessage(null, rs, error);
        } //try
        return rs;
    } //saveApplyCountRank

    /**
     * GEO 20211005 add
     * 前台-服務申辦統計: 取得申辦服務名次列表
     *
     * @return the list
     */
    public GeoCaseSetApplyRankDetailListRs getCaseSetRankList(GeoCaseSetApplyRankDetailListRq rq) {
        GeoCaseSetApplyRankDetailListRs rs = new GeoCaseSetApplyRankDetailListRs();
        GeoCaseSetApplyRankDetailListViewForm viewForm = new GeoCaseSetApplyRankDetailListViewForm();
        rs.setData(viewForm);
        List<GeoBidCaseListQueryDataGridModel> countModelList = new ArrayList<>();
        List<GeoBidCaseListQueryDataGridModel> modelList = new ArrayList<>();
        viewForm.setGrid(modelList);
        KgoApiException error = null;
        try {
            List<GeoKgoCasesetApplyCountRule> ruleList = geoKgoCasesetApplyCountRuleRepository.findAll();
            if (ruleList.size() > 0) {
                GeoKgoCasesetApplyCountRule rule = ruleList.get(0);
                GeoCaseSetApplyCountType type = GeoCaseSetApplyCountType.valueOfCode(rule.getSearchRangeType());
                String dateStartStr;
                String dateEndStr;
                if (type.equals(GeoCaseSetApplyCountType.CUSTOM_DATE) &&
                        (rule.getDateStart() == null || rule.getDateStart().isEmpty() ||
                                rule.getDateEnd() == null || rule.getDateEnd().isEmpty())) {
                    return rs;
                }
                if (type.equals(GeoCaseSetApplyCountType.CUSTOM_DATE)) {
                    dateStartStr = rule.getDateStart();
                    dateEndStr = rule.getDateEnd();
                } else {
                    dateStartStr = getDateStart(type, DateUtil.PATTEN_YEAR_MONTH_DAY);
                    dateEndStr = DateUtil.dateToString(new Date(), DateUtil.PATTEN_YEAR_MONTH_DAY);
                }
                Date dateStart = DateUtil.getStartOfDay(dateStartStr, DateUtil.PATTEN_YEAR_MONTH_DAY, DateUtil.PATTEN_FULL_TIME_MS);
                Date dateEnd = DateUtil.getEndOfDay(dateEndStr, DateUtil.PATTEN_YEAR_MONTH_DAY, DateUtil.PATTEN_FULL_TIME_MS);
                countModelList = geoCaseSetApplyCountReposCustom.getCaseSetApplyCountList(dateStart, dateEnd, CaseFlowTypeEnum.B3, rule.getCaseSetStatus());
                for (int i = 0; i< countModelList.size(); i++) {
                    GeoBidCaseListQueryDataGridModel model = countModelList.get(i);
                    List<KgoCasesetType>  typeList = geoCaseSetApplyCountReposCustom.getCaseSetApplyType(model.getCaseSetId());
                    if (typeList != null) {
                        boolean isApplyTypeCActive = false;
                        boolean isApplyTypeEActive = false;
                        boolean isApplyTypeLActive = false;
                        for (KgoCasesetType entity: typeList) {
                            String applyType = StringUtils.isBlank(entity.getId().getApplyType()) ? StringUtils.EMPTY : entity.getId().getApplyType();
                            if (applyType.contains(ApplyTypeEnum.C.getValue())) isApplyTypeCActive = true;
                            if (applyType.contains(ApplyTypeEnum.E.getValue())) isApplyTypeEActive = true;
                            if (applyType.contains(ApplyTypeEnum.L.getValue())) isApplyTypeLActive = true;
                        } //for (KgoCasesetType entity: typeList)
                        model.setApplyTypeCActive(isApplyTypeCActive);
                        model.setApplyTypeEActive(isApplyTypeEActive);
                        model.setApplyTypeLActive(isApplyTypeLActive);
                    } //if (typeList != null)
                } //for (GeoBidCaseListQueryDataGridModel model...

                if ( countModelList.size() > 0) {
                    int rank = 0;
                    for (int i = 0; i < countModelList.size(); i++) {
                        if (rank >= rule.getSearchRank()) break;
                        GeoBidCaseListQueryDataGridModel model = countModelList.get(i);
                        if (i == 0 || (!model.getApplyCount().equals(countModelList.get(i - 1).getApplyCount()))) {
                            rank += 1;
                        }
                        model.setApplyCountRank(rank);
                        modelList.add(model);
                    } // for (int i = 0; i < countModelList.size(); i++)
                } //if (countModelList != null && countModelList.size() > 0)
            } //if (caseSetApplyCountList != null)
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(CityApiError.UNKNOWN_EXCEPTION), e);
        } finally {
            setResultMessage(null, rs, error);
        } //try
        return rs;
    } //getCaseSetRankList

    /**
     * GEO 20211202 add
     * 前台-關聯服務: 取得關聯服務
     *
     * @return the list
     */
    public GeoCaseSetApplyRankDetailListRs getCaseSetAssociateList(GeoFrontendCaseSetAssociateQueryRq rq) {
        GeoCaseSetApplyRankDetailListRs rs = new GeoCaseSetApplyRankDetailListRs();
        GeoCaseSetApplyRankDetailListViewForm viewForm = new GeoCaseSetApplyRankDetailListViewForm();
        rs.setData(viewForm);
        List<GeoBidCaseListQueryDataGridModel> gridModelList = new ArrayList<>();
        List<GeoBidCaseListQueryDataGridModel> modelList = new ArrayList<>();
        viewForm.setGrid(modelList);
        KgoApiException error = null;
        try {
            gridModelList = geoCaseSetApplyCountReposCustom.getCaseSetAssociateList(rq.getCaseSetId(), "On");
            for (int i = 0; i< gridModelList.size(); i++) {
                GeoBidCaseListQueryDataGridModel model = gridModelList.get(i);
                List<KgoCasesetType>  typeList = geoCaseSetApplyCountReposCustom.getCaseSetApplyType(model.getCaseSetId());
                if (typeList != null) {
                    boolean isApplyTypeCActive = false;
                    boolean isApplyTypeEActive = false;
                    boolean isApplyTypeLActive = false;
                    for (KgoCasesetType entity: typeList) {
                        String applyType = StringUtils.isBlank(entity.getId().getApplyType()) ? StringUtils.EMPTY : entity.getId().getApplyType();
                        if (applyType.contains(ApplyTypeEnum.C.getValue())) isApplyTypeCActive = true;
                        if (applyType.contains(ApplyTypeEnum.E.getValue())) isApplyTypeEActive = true;
                        if (applyType.contains(ApplyTypeEnum.L.getValue())) isApplyTypeLActive = true;
                    } //for (KgoCasesetType entity: typeList)
                    model.setApplyTypeCActive(isApplyTypeCActive);
                    model.setApplyTypeEActive(isApplyTypeEActive);
                    model.setApplyTypeLActive(isApplyTypeLActive);
                } //if (typeList != null)
                modelList.add(model);
            } // for (int i = 0; i< gridModelList.size(); i++)
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(CityApiError.UNKNOWN_EXCEPTION), e);
        } finally {
            setResultMessage(null, rs, error);
        } //try
        return rs;
    } //getCaseSetRankList
}
