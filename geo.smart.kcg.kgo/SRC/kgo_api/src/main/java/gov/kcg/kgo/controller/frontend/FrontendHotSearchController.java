package gov.kcg.kgo.controller.frontend;

import gov.kcg.kgo.controller.base.BaseController;
import gov.kcg.kgo.geocontroller.common.GeoKcgCityExtController;
import gov.kcg.kgo.geoservice.GeoCityExtService;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoOfferCaseSetInfoRs;
import gov.kcg.kgo.service.FrontendHotSearchService;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import gov.kcg.kgo.viewModel.frontend.hotSearch.rq.HotSearchGovernmentQueryRq;
import gov.kcg.kgo.viewModel.frontend.hotSearch.rq.HotSearchQueryRq;
import gov.kcg.kgo.viewModel.frontend.hotSearch.rs.FrontendHotSearchGovernmentRs;
import gov.kcg.kgo.viewModel.frontend.hotSearch.rs.FrontendHotSearchHomeRs;
import gov.kcg.kgo.viewModel.frontend.hotSearch.rs.FrontendHotSearchTypeRs;
import gov.kcg.kgo.viewModel.frontend.hotSearch.rs.HotSearchQueryRs;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/frontend/hotSearch")
@Api(tags = {"frontend-hot-search-controller 前台熱門搜尋管理"})
public class FrontendHotSearchController extends BaseController {

    @Autowired
    private FrontendHotSearchService frontendHotSearchService;

    @Autowired
    GeoCityExtService geoCityExtService;

    /**
     * 熱門搜尋-初始畫面
     *
     * @return HotSearchHomeRs
     */
    @ApiOperation(value = "熱門搜尋-熱門關鍵字")
    @PostMapping(value = { "/homeAction" },
                consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public FrontendHotSearchHomeRs frontendHotSearchHomeAction() {
        FrontendHotSearchHomeRs rs = frontendHotSearchService.frontendHotSearchHome();
        return rs;
    }

    /**
     * 申辦服務選單-申辦案件數查詢x
     *
     * @param rq
     * @return HotSearchHomeRs
     */
    @ApiOperation(value = "熱門搜尋-查詢")
    @PostMapping(value = { "/queryAction" },
                consumes = { MediaType.APPLICATION_JSON_VALUE },
                produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public HotSearchQueryRs hotSearchQueryAction(@RequestBody HotSearchQueryRq rq) {
        HotSearchQueryRs rs = frontendHotSearchService.hotSearchQuery(rq);
        return rs;
    }

    /**
     * 20220729 前台切換搜尋引擎
     * 熱門搜尋-顯示搜尋引擎種類
     */
    @ApiOperation(value = "熱門搜尋-顯示搜尋引擎種類")
    @PostMapping(value = { "/typeOfEngine" },
            consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public FrontendHotSearchTypeRs frontendHotSearchTypeOfEngine() {
        FrontendHotSearchTypeRs rs = frontendHotSearchService.frontendHotSearchType();
        return rs;
    }

    /**
     * 20220729 前台切換搜尋引擎
     * 提供目前所有上架狀態的申辦案件資料(不含站外連結)，讓其他廠商可爬蟲使用
     */
    @ApiOperation(value = "前台切換搜尋引擎-顯示所有已上架案件資料")
    @GetMapping(value = { "/getCaseSetInfoList" }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public GeoOfferCaseSetInfoRs offerTotalCaseInfo() {
        GeoOfferCaseSetInfoRs rs = geoCityExtService.GeoOfferCaseSetInfo();
        return rs;
    }

}
