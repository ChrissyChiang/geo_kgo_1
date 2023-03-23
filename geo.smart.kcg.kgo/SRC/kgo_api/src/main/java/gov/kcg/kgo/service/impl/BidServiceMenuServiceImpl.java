package gov.kcg.kgo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import gov.kcg.kgo.model.KgoCasesetGroupLevel;
import gov.kcg.kgo.repository.KgoCasesetGroupLevelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.kcg.kgo.dto.BidServiceMenuQueryDto;
import gov.kcg.kgo.enums.backend.PublishStateEnum;
import gov.kcg.kgo.enums.common.MainTypeEnum;
import gov.kcg.kgo.enums.error.KgoFrontEndApiError;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.model.KgoGroupLevel;
import gov.kcg.kgo.repository.KgoCasesetRepository;
import gov.kcg.kgo.repository.KgoGroupLevelRepository;
import gov.kcg.kgo.service.BidServiceMenuService;
import gov.kcg.kgo.viewModel.frontend.bidServiceMenu.home.rs.BidServiceMenuHomeRs;
import gov.kcg.kgo.viewModel.frontend.bidServiceMenu.home.rs.bean.BidServiceMenuHomeViewForm;
import gov.kcg.kgo.viewModel.frontend.bidServiceMenu.query.rq.BidServiceMenuQueryRq;
import gov.kcg.kgo.viewModel.frontend.bidServiceMenu.query.rs.BidServiceMenuQueryRs;
import gov.kcg.kgo.viewModel.frontend.bidServiceMenu.query.rs.bean.BidServiceMenuQueryViewForm;

@Transactional(rollbackFor = Exception.class)
@Service("BidServiceMenuService")
public class BidServiceMenuServiceImpl extends KgoFrontEndServiceImpl implements BidServiceMenuService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BidServiceMenuServiceImpl.class);

    @Autowired
    private KgoCasesetRepository kgoCasesetRepository;

    @Autowired
    private KgoGroupLevelRepository kgoGroupLevelRepository;

    @Autowired
    private KgoCasesetGroupLevelRepository kgoCasesetGroupLevelRepository;


    /**
     * 申辦服務選單-初始畫面
     *
     * @return
     */
    @Override
    public BidServiceMenuHomeRs bidServiceMenuHome() {
        BidServiceMenuHomeRs rs = new BidServiceMenuHomeRs();
        BidServiceMenuHomeViewForm viewForm = new BidServiceMenuHomeViewForm();
        try {
            List<BidServiceMenuQueryDto> grid = kgoCasesetRepository.getBidServiceMenuCaseCountData(MainTypeEnum.ORGAN.getValue(), PublishStateEnum.ON.getValue());
            viewForm.setGrid(grid);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoFrontEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            throw new KgoApiException("bidServiceMenuHome error " + e.getMessage(), e);
        }
        return rs;
    }

    /**
     * 申辦服務選單-申辦案件數查詢
     *
     * @param rq
     * @return
     */
    /**
     * 申辦服務選單-申辦案件數查詢
     *
     * @param rq
     * @return
     */
    @Override
    public BidServiceMenuQueryRs bidServiceMenuQuery(BidServiceMenuQueryRq rq) {
        BidServiceMenuQueryRs rs = new BidServiceMenuQueryRs();
        BidServiceMenuQueryViewForm viewForm = new BidServiceMenuQueryViewForm();

        try {
            String mainType = rq.getMainType();
            String status = PublishStateEnum.ON.getValue(); // 固定 OFF

            List<BidServiceMenuQueryDto> grid = new ArrayList<>();

            MainTypeEnum mainTypeEnum = MainTypeEnum.getMainTypeEnum(mainType);
            if (mainTypeEnum == MainTypeEnum.ORGAN)
                grid.addAll(kgoCasesetRepository.getBidServiceMenuCaseCountData(mainType, status));
            else
                grid.addAll(getBidServiceMenuCaseCountData(mainType));

            // order by count desc
            grid = grid.stream().sorted((r1, r2) -> {
                int r1Integer = customFormatInteger(r1);
                int r2Integer = customFormatInteger(r2);
                return -Integer.compare(r1Integer, r2Integer);
            }).collect(Collectors.toList());
            viewForm.setGrid(grid);
            rs.setData(viewForm);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(KgoFrontEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
            throw new KgoApiException("bidServiceMenuQuery error " + e.getMessage(), e);
        }
        return rs;
    }

    private int customFormatInteger(BidServiceMenuQueryDto r1) {
        int r1Integer;
        String count = String.valueOf(r1.getCount());
        try {
            r1Integer = Integer.parseInt(count);
        } catch (NumberFormatException e) {
            LOGGER.info("bidServiceMenuQuery number format error count: {}", count);
            r1Integer = 0;
        }
        return r1Integer;
    }

    /**
     * 申辦服務選單-申辦案件數查詢--依照身份登入方式
     */
    @Override
    public List<BidServiceMenuQueryDto> getBidServiceMenuCaseCountData(String mainType) {
        List<KgoGroupLevel> kgoGroupLevels = kgoGroupLevelRepository.findAllByMainTypeAndState(mainType, PublishStateEnum.ON.getValue());

        List<BidServiceMenuQueryDto> bidServiceMenuQueryDtos = new ArrayList<>();

        kgoGroupLevels.forEach(k -> {
            List<KgoCasesetGroupLevel> kgoCasesetGroupLevels = kgoCasesetGroupLevelRepository.findAllByGroupLevelSeq(String.valueOf(k.getSeq()));

            if (kgoCasesetGroupLevels != null && !kgoCasesetGroupLevels.isEmpty()) {
                BidServiceMenuQueryDto bidServiceMenuQueryDto = new BidServiceMenuQueryDto();
                bidServiceMenuQueryDto.setValue(String.valueOf(k.getSeq()));
                bidServiceMenuQueryDto.setName(k.getName());
                bidServiceMenuQueryDto.setCount(String.valueOf(kgoCasesetGroupLevels.size()));

                bidServiceMenuQueryDtos.add(bidServiceMenuQueryDto);
            }
        });

        return bidServiceMenuQueryDtos;
    }

}
