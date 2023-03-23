package gov.kcg.kgo.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import gov.kcg.kgo.enums.backend.ApplyTypeEnum;
import gov.kcg.kgo.enums.common.SuccessMsg;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.geoentity.GeoKgoHotSearch;
import gov.kcg.kgo.geoenum.GeoHotSearchType;
import gov.kcg.kgo.geomodel.GeoHotSearchGovernmentModel;
import gov.kcg.kgo.georepository.GeoKgoHotSearchRepository;
import gov.kcg.kgo.georepository.custom.GeoCaseSetApplyCountReposCustom;
import gov.kcg.kgo.geoservice.GeoBaseService;
import gov.kcg.kgo.geoutil.GeoApi1999Properties;
import gov.kcg.kgo.model.*;
import gov.kcg.kgo.repository.KgoCasesetCheckRepository;
import gov.kcg.kgo.util.HttpRequest;
import gov.kcg.kgo.viewModel.frontend.bidCaseList.home.rs.bean.BidCaseListQueryDataGrid;
import gov.kcg.kgo.viewModel.frontend.hotSearch.rq.HotSearchGovernmentQueryRq;
import gov.kcg.kgo.viewModel.frontend.hotSearch.rs.FrontendHotSearchGovernmentRs;
import gov.kcg.kgo.viewModel.frontend.hotSearch.rs.FrontendHotSearchTypeRs;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import gov.kcg.kgo.common.frontend.FrontendLoginUserInfo;
import gov.kcg.kgo.dto.CasesetMemoHotSearchDto;
import gov.kcg.kgo.enums.common.SystemTypeEnum;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.enums.front.FrontendFunctionCodeEnum;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.repository.KgoCasesetRepository;
import gov.kcg.kgo.repository.KgoKeywordsRepository;
import gov.kcg.kgo.repository.KgoKeywordsetRepository;
import gov.kcg.kgo.service.FrontendHotSearchService;
import gov.kcg.kgo.service.operationmemo.OperationApiMemo;
import gov.kcg.kgo.service.operationmemo.OperationColumn;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.viewModel.frontend.hotSearch.rq.HotSearchQueryRq;
import gov.kcg.kgo.viewModel.frontend.hotSearch.rs.FrontendHotSearchHomeRs;
import gov.kcg.kgo.viewModel.frontend.hotSearch.rs.HotSearchQueryRs;
import gov.kcg.kgo.viewModel.frontend.hotSearch.rs.bean.FrontendHotSearchHomeViewForm;
import gov.kcg.kgo.viewModel.frontend.hotSearch.rs.bean.HotSearchQueryDataGrid;
import gov.kcg.kgo.viewModel.frontend.hotSearch.rs.bean.HotSearchQueryViewForm;

@Service
public class FrontendHotSearchServiceImpl extends KgoFrontEndServiceImpl implements FrontendHotSearchService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FrontendHotSearchServiceImpl.class);

    @Autowired
    private KgoKeywordsetRepository KgoKeywordsetRepository;

    @Autowired
    private KgoCasesetRepository kgoCasesetRepository;

    @Autowired
    private KgoKeywordsRepository kgoKeywordsRepository;

    @Autowired
    private GeoCaseSetApplyCountReposCustom geoCaseSetApplyCountReposCustom;

    @Autowired
    private CaseFormServiceImpl caseFormServiceImpl;

    @Autowired
    private KgoCasesetCheckRepository kgoCasesetCheckRepository;

    @Autowired
    private GeoKgoHotSearchRepository geoKgoHotSearchRepository;


    @Autowired
    private GeoApi1999Properties geoApi1999Properties;

    //熱門關鍵字
    @Override
    public FrontendHotSearchHomeRs frontendHotSearchHome() {
        FrontendHotSearchHomeRs hotSearchHomeRs = new FrontendHotSearchHomeRs();
        try {
            List<KgoKeywordset> kgoKeywordsets = KgoKeywordsetRepository.findOrderByOrderNumDesc();
            List<String> keywords = kgoKeywordsets.stream().map(KgoKeywordset::getKeyword).collect(Collectors.toList());
            FrontendHotSearchHomeViewForm hotSearchHomeViewForm = new FrontendHotSearchHomeViewForm();
            hotSearchHomeViewForm.setKeywords(keywords);
            hotSearchHomeRs.setData(hotSearchHomeViewForm);
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            throw new KgoApiException("frontendHotSearchHome error " + e.getMessage(), e);
        }
        return hotSearchHomeRs;
    }

    @Override
    public HotSearchQueryRs hotSearchQuery(HotSearchQueryRq rq) {
        HotSearchQueryRs hotSearchQueryRs = new HotSearchQueryRs();
        KgoApiException error = null;
        OperationApiMemo memo = null;
        try {
            // 前台、、熱搜
            memo = super.genOperationMemo(SystemTypeEnum.F, null, FrontendFunctionCodeEnum.HotSearch);
            this.validateParameter(rq);
            PageRequest pageRequest = PageRequest.of(rq.getPageNumber(), rq.getPageSize());
            String gstrKeyword = rq.getGstrKeyword();
            KgoKeywords kgoKeywords = new KgoKeywords();
            try {
                FrontendLoginUserInfo frontendLoginUser = KgoUtil.getFrontendLoginUser();
                kgoKeywords.setCreateUser(frontendLoginUser.getName());
            } catch (Exception e) {
                LOGGER.info("user not login");
            }
            kgoKeywords.setSeqGUID(UUID.randomUUID().toString());
            kgoKeywords.setKeyword(gstrKeyword);
            kgoKeywords.setCreateTime(DateUtil.getCurrentTimestamp());
            kgoKeywordsRepository.save(kgoKeywords);
            Page<CasesetMemoHotSearchDto> casesetMemoHotSearchDtos = kgoCasesetRepository.findByKeywordPaged(rq.getGstrKeyword(), pageRequest);
            HotSearchQueryViewForm hotSearchQueryViewForm = new HotSearchQueryViewForm();
            List<HotSearchQueryDataGrid> hotSearchQueryDataGrids = casesetMemoHotSearchDtos.stream().map(item -> {
                HotSearchQueryDataGrid hotSearchQueryDataGrid = new HotSearchQueryDataGrid();
                hotSearchQueryDataGrid.setCaseSetId(item.getCaseSetId());
                hotSearchQueryDataGrid.setCaseSetName(item.getCaseSetName());
                hotSearchQueryDataGrid.setContentHtml(item.getContentHtml());
                hotSearchQueryDataGrid.setTitle(item.getTitle());
                hotSearchQueryDataGrid.setApplyType(item.getApplyType());
                hotSearchQueryDataGrid.setCaseFlowType(item.getCaseFlowType());
                hotSearchQueryDataGrid.setOrganId(item.getOrganId());
                hotSearchQueryDataGrid.setOrganName(item.getOrganName());
                /** GEO 20211224 add 調整搜尋結果頁面 */
                List<KgoCasesetType>  typeList = geoCaseSetApplyCountReposCustom.getCaseSetApplyType(item.getCaseSetId());
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
                    hotSearchQueryDataGrid.setApplyTypeCActive(isApplyTypeCActive);
                    hotSearchQueryDataGrid.setApplyTypeEActive(isApplyTypeEActive);
                    hotSearchQueryDataGrid.setApplyTypeLActive(isApplyTypeLActive);
                } //if (typeList != null)
                //服務類型 及 外部服務連結
                KgoCaseset caseset = kgoCasesetRepository.getById(item.getCaseSetId());
                if(caseset.getCasesetCategory() != null){
                    hotSearchQueryDataGrid.setCategroy(caseset.getCasesetCategory());
                    if("excase".equals(caseset.getCasesetCategory())){
                        hotSearchQueryDataGrid.setLinkUrl(caseset.getLinkUrl());
                    }
                }
                return hotSearchQueryDataGrid;
            }).collect(Collectors.toList());
//
//       /** GEO 20211224 add 調整搜尋結果頁面 */
//       for (int i = 0; i< hotSearchQueryDataGrids.size(); i++) {
//          HotSearchQueryDataGrid grid = hotSearchQueryDataGrids.get(i);
//          List<KgoCasesetType>  typeList = geoCaseSetApplyCountReposCustom.getCaseSetApplyType(grid.getCaseSetId());
//          if (typeList != null) {
//             for (KgoCasesetType entity: typeList) {
//                String applyType = StringUtils.isBlank(entity.getId().getApplyType()) ? StringUtils.EMPTY : entity.getId().getApplyType();
//                boolean isApplyTypeCActive = applyType.contains(ApplyTypeEnum.C.getValue()) ? true : false;
//                boolean isApplyTypeEActive = applyType.contains(ApplyTypeEnum.E.getValue()) ? true : false;
//                boolean isApplyTypeLActive = applyType.contains(ApplyTypeEnum.L.getValue()) ? true : false;
//                grid.setApplyTypeCActive(isApplyTypeCActive);
//                grid.setApplyTypeEActive(isApplyTypeEActive);
//                grid.setApplyTypeLActive(isApplyTypeLActive);
//             } //for (KgoCasesetType entity: typeList)
//          } //if (typeList != null)
//       } //for (GeoBidCaseListQueryDataGridModel model...

            hotSearchQueryViewForm.setGrids(hotSearchQueryDataGrids);
            hotSearchQueryViewForm.setTotalPages(String.valueOf(casesetMemoHotSearchDtos.getTotalPages()));
            hotSearchQueryRs.setData(hotSearchQueryViewForm);
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            error = new KgoApiException("hotSearchQuery error " + e.getMessage(), e);
        } finally {
            /** === 儲存操作紀錄 === */
            List<OperationColumn> memoList = new ArrayList<>();
            memoList.add(new OperationColumn(null, rq.getGstrKeyword()));
            memo.setMemoList(memoList);
            super.saveOperationLog(memo);
            /** === 儲存操作紀錄 === */

            if (error != null) {
                throw error;
            }
        }
        return hotSearchQueryRs;
    }

    /**
     * 20220729 前台切換搜尋引擎
     * 熱門搜尋-顯示搜尋引擎種類
     */
    @Override
    public FrontendHotSearchTypeRs frontendHotSearchType() {
        FrontendHotSearchTypeRs hotSearchHomeRs = new FrontendHotSearchTypeRs();
        try {
             GeoKgoHotSearch kgoHotSearch = geoKgoHotSearchRepository.findByHotSearchSeqMax();
             if (kgoHotSearch!=null){
                 if (kgoHotSearch.getIsOpenKgo() == GeoHotSearchType.KGO_HSIUNG_EASYGO.getValue()){
                     hotSearchHomeRs.setUseKGOSearch(false);
                 } else{
                     hotSearchHomeRs.setUseKGOSearch(true);
                 } //if (kgoHotSearch.getIsOpenKgo()
             }else {
                 hotSearchHomeRs.setUseKGOSearch(false);
             } //if (kgoHotSearch!=null)
            hotSearchHomeRs.setMsg(SuccessMsg.UNKNOW.getMsg());
        } catch (Exception e) {
            LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            throw new KgoApiException("frontendHotSearchHome error " + e.getMessage(), e);
        }
        return hotSearchHomeRs;
    } //frontendHotSearchType

    private void validateParameter(HotSearchQueryRq rq) {
        Integer pageNumber = rq.getPageNumber();
        if (null == pageNumber || pageNumber < 1) {
            rq.setPageNumber(0);
        } else {
            rq.setPageNumber(rq.getPageNumber() - 1);
        }
        Integer pageSize = rq.getPageSize();
        if (null == pageSize || pageSize < 1) {
            rq.setPageSize(10);
        }
    }
}
