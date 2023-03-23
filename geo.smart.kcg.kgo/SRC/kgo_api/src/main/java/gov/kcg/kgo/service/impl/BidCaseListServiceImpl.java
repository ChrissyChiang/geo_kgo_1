package gov.kcg.kgo.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import gov.kcg.kgo.geoenum.GeoBooleanType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.kcg.kgo.dto.BidServiceMenuQueryDto;
import gov.kcg.kgo.enums.backend.ApplyTypeEnum;
import gov.kcg.kgo.enums.backend.PublishStateEnum;
import gov.kcg.kgo.enums.common.MainTypeEnum;
import gov.kcg.kgo.enums.common.SystemTypeEnum;
import gov.kcg.kgo.enums.error.KgoFrontEndApiError;
import gov.kcg.kgo.enums.front.FrontendFunctionCodeEnum;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.model.KgoCaseset;
import gov.kcg.kgo.model.KgoCasesetGroupLevel;
import gov.kcg.kgo.model.KgoCasesetType;
import gov.kcg.kgo.repository.KgoCasesetGroupLevelRepository;
import gov.kcg.kgo.repository.KgoCasesetRepository;
import gov.kcg.kgo.repository.KgoCasesetTypeRepository;
import gov.kcg.kgo.service.BidCaseListService;
import gov.kcg.kgo.service.BidServiceMenuService;
import gov.kcg.kgo.service.operationmemo.OperationApiMemo;
import gov.kcg.kgo.service.operationmemo.OperationColumn;
import gov.kcg.kgo.viewModel.frontend.bidCaseList.home.rq.BidCaseListHomeRq;
import gov.kcg.kgo.viewModel.frontend.bidCaseList.home.rs.BidCaseListHomeRs;
import gov.kcg.kgo.viewModel.frontend.bidCaseList.home.rs.bean.BidCaseListHomeViewForm;
import gov.kcg.kgo.viewModel.frontend.bidCaseList.home.rs.bean.BidCaseListQueryDataGrid;
import gov.kcg.kgo.viewModel.frontend.bidCaseList.home.rs.bean.BidCaseListTypeDataGrid;
import gov.kcg.kgo.viewModel.frontend.bidCaseList.query.rq.BidCaseListQueryRq;
import gov.kcg.kgo.viewModel.frontend.bidCaseList.query.rs.BidCaseListQueryRs;
import gov.kcg.kgo.viewModel.frontend.bidCaseList.query.rs.bean.BidCaseListQueryViewForm;

@Transactional(rollbackFor = Exception.class)
@Service("BidCaseListService")
public class BidCaseListServiceImpl extends KgoFrontEndServiceImpl implements BidCaseListService {

    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(BidCaseListServiceImpl.class);

    @Autowired
    private KgoCasesetRepository kgoCasesetRepository;

    @Autowired
    private KgoCasesetGroupLevelRepository kgoCasesetGroupLevelRepository;

    @Autowired
    private KgoCasesetTypeRepository kgoCasesetTypeRepository;

    @Autowired
    private BidServiceMenuService bidServiceMenuService;

    /**
     * 申辦服務選單-初始畫面
     *
     * @return
     */
    @Override
    public BidCaseListHomeRs bidCaseListHome(BidCaseListHomeRq rq) {
        BidCaseListHomeRs rs = new BidCaseListHomeRs();
        BidCaseListHomeViewForm viewForm = new BidCaseListHomeViewForm();
        KgoApiException error = null;
        OperationApiMemo memo = null;

        String logTitle = null;
        try {

            String mainType = rq.getMainType();
            String value = rq.getValue();
            String status = PublishStateEnum.ON.getValue(); // 固定 OFF
            String titleName = StringUtils.EMPTY;

            MainTypeEnum mainTypeEnum = MainTypeEnum.getMainTypeEnum(mainType);
            FrontendFunctionCodeEnum frontendFunctionCodeEnum = null;
            if (StringUtils.equals(MainTypeEnum.SERVICE.getValue(), mainType)) {
                frontendFunctionCodeEnum = FrontendFunctionCodeEnum.ServiceType;
            } else if (StringUtils.equals(MainTypeEnum.ROLE.getValue(), mainType)) {
                frontendFunctionCodeEnum = FrontendFunctionCodeEnum.RoleType;
            } else {
                frontendFunctionCodeEnum = FrontendFunctionCodeEnum.OrganType;
            }
            // 前台、服務分類
            memo = super.genOperationMemo(SystemTypeEnum.F, null, frontendFunctionCodeEnum);

            List<BidServiceMenuQueryDto> dtoList = new ArrayList<>();

            if (mainTypeEnum == MainTypeEnum.ORGAN)
                dtoList.addAll(kgoCasesetRepository.getBidServiceMenuCaseCountData(mainType, status));
            else
                dtoList.addAll(bidServiceMenuService.getBidServiceMenuCaseCountData(mainType));

            // 被選類別標題名稱
            BidServiceMenuQueryDto dto = dtoList.stream().filter(l -> l.getValue().equalsIgnoreCase(value)).findAny().get();
            titleName = dto.getName();
            logTitle = titleName;
            // 頁籤顯示資料
            List<BidCaseListTypeDataGrid> typeGrid = dtoList.stream().map(l -> {
                String dtoValue = l.getValue();
                String dtoName = l.getName();
                /** GEO 20211224 add 顯示案件服務數量 */
                String dtoCount = l.getCount();
                BidCaseListTypeDataGrid dg = new BidCaseListTypeDataGrid();
                dg.setName(dtoName);
                dg.setValue(dtoValue);
                dg.setCount(dtoCount);
                return dg;
            }).collect(Collectors.toList());

            List<BidCaseListQueryDataGrid> dataGrid = new ArrayList<>();

            if (mainTypeEnum == MainTypeEnum.ORGAN)
                dataGrid.addAll(queryKgoCasesetData(mainType, value));
            else
                dataGrid.addAll(getAllKgoCasesetByMainTypeAndValue(value));

            viewForm.setTitleName(titleName);
            viewForm.setDataGrid(dataGrid);
            viewForm.setTypeGrid(typeGrid);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            error = apiE;
        } catch (Exception e) {
            LOGGER.error(KgoFrontEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            error = new KgoApiException("BidCaseListHome error " + e.getMessage(), e);
        } finally {
            /** === 儲存操作紀錄 === */
            List<OperationColumn> memoList = new ArrayList<>();
            memoList.add(new OperationColumn(null, logTitle));
            memo.setMemoList(memoList);
            super.saveOperationLog(memo);
            /** === 儲存操作紀錄 === */

            if (error != null) {
                throw error;
            }
        }
        return rs;
    }

    /**
     * 申辦案件清單-申辦案件資料查詢
     *
     * @param rq
     * @return
     */
    @Override
    public BidCaseListQueryRs bidCaseListQuery(BidCaseListQueryRq rq) {
        BidCaseListQueryRs rs = new BidCaseListQueryRs();
        BidCaseListQueryViewForm viewForm = new BidCaseListQueryViewForm();
        try {
            String mainType = rq.getMainType();
            String value = rq.getValue();
            viewForm.setGrid(queryKgoCasesetData(mainType, value));
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoFrontEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            throw new KgoApiException("BidCaseListQuery error " + e.getMessage(), e);
        }
        return rs;
    }

    /**
     *
     * @return
     */
    private List<BidCaseListQueryDataGrid> queryKgoCasesetData(String mainType, String value) {
        return kgoCasesetRepository.getBidCaseListData(mainType, value).stream().map(l -> {
            String applyType = StringUtils.isBlank(l.getApplyType()) ? StringUtils.EMPTY : l.getApplyType();
            boolean isApplyTypeCActive = applyType.contains(ApplyTypeEnum.C.getValue()) ? true : false;
            boolean isApplyTypeEActive = applyType.contains(ApplyTypeEnum.E.getValue()) ? true : false;
            boolean isApplyTypeLActive = applyType.contains(ApplyTypeEnum.L.getValue()) ? true : false;
            /** GEO 20221014 查詢服務案件外部連結 Oberyn */
            String linkUrl = "excase".equals(l.getCaseCategory()) ? l.getLinkUrl() : null;
            BidCaseListQueryDataGrid dg = new BidCaseListQueryDataGrid();
            dg.setCaseSetId(l.getCaseSetId());
            dg.setCaseSetName(l.getCaseSetName());
            dg.setCaseFlowType(l.getCaseFlowType());
            dg.setIsApplyTypeCActive(isApplyTypeCActive);
            dg.setIsApplyTypeEActive(isApplyTypeEActive);
            dg.setIsApplyTypeLActive(isApplyTypeLActive);
            dg.setLinkUrl(linkUrl);
            dg.setCategroy(l.getCaseCategory());


            return dg;
        }).collect(Collectors.toList());
    }

    private List<BidCaseListQueryDataGrid> getAllKgoCasesetByMainTypeAndValue(String value) {
        List<BidCaseListQueryDataGrid> bidCaseListQueryDataGrids = new ArrayList<>();

        List<KgoCasesetGroupLevel> kgoCasesetGroupLevels = kgoCasesetGroupLevelRepository.findAllByGroupLevelSeq(value);

        if (kgoCasesetGroupLevels != null && !kgoCasesetGroupLevels.isEmpty()) {
            kgoCasesetGroupLevels.forEach(k -> {
                Optional<KgoCaseset> kgoCaseset = kgoCasesetRepository.findById(k.getCaseSetId());

                if (kgoCaseset.isPresent()) {
                    PublishStateEnum stateEnum = PublishStateEnum.getPublishStateEnum(kgoCaseset.get().getStatus());
                    /**GEO 20211019 add */
                    if (stateEnum == PublishStateEnum.ON && kgoCaseset.get().getIsOpenForOrgan() == GeoBooleanType.DISABLED.getCode()) {

                        List<KgoCasesetType> kgoCasesetTypes = kgoCasesetTypeRepository.findByIdCaseSetId(k.getCaseSetId());

                        BidCaseListQueryDataGrid bidCaseListQueryDataGrid = new BidCaseListQueryDataGrid();
                        bidCaseListQueryDataGrid.setCaseSetId(kgoCaseset.get().getCaseSetId());
                        bidCaseListQueryDataGrid.setCaseSetName(kgoCaseset.get().getCaseSetName());
                        bidCaseListQueryDataGrid.setCaseFlowType(kgoCaseset.get().getCaseFlowType());

                        if (kgoCasesetTypes != null && !kgoCasesetTypes.isEmpty()) {
                            kgoCasesetTypes.forEach(t -> {
                                bidCaseListQueryDataGrid.setIsApplyTypeCActive(ApplyTypeEnum.C.getValue().equals(t.getId().getApplyType()) ? true : false);
                                bidCaseListQueryDataGrid.setIsApplyTypeEActive(ApplyTypeEnum.E.getValue().equals(t.getId().getApplyType()) ? true : false);
                                bidCaseListQueryDataGrid.setIsApplyTypeLActive(ApplyTypeEnum.L.getValue().equals(t.getId().getApplyType()) ? true : false);
                                /** GEO 20221014 查詢服務案件外部連結 Oberyn */
                                if(kgoCaseset.get().getCasesetCategory() != null){
                                    String category = kgoCaseset.get().getCasesetCategory();
                                    bidCaseListQueryDataGrid.setLinkUrl("excase".equals(category) ? kgoCaseset.get().getLinkUrl(): null );
                                    bidCaseListQueryDataGrid.setCategroy(category);
                                }
                            });
                        }

                        bidCaseListQueryDataGrids.add(bidCaseListQueryDataGrid);
                    }
                }
            });
        }

        return bidCaseListQueryDataGrids;
    }

}
