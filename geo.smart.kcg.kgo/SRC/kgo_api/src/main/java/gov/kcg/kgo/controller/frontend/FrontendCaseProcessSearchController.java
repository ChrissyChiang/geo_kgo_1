package gov.kcg.kgo.controller.frontend;

import gov.kcg.kgo.aop.annotion.ValidCode;
import gov.kcg.kgo.service.FrontendCaseProcessSearchService;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rq.*;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/frontend/caseProcessSearch")
public class FrontendCaseProcessSearchController {

    @Autowired
    private FrontendCaseProcessSearchService frontendCaseProcessSearchService;

    /**
     * 案件進度查詢-驗證
     *
     * @return CaseProcessSearchValidateRs
     */
    @ApiOperation(value = "案件進度查詢-首頁")
    @PostMapping(value = { "/homeAction" },
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public CaseProcessSearchHomeRs homeAction() {
        CaseProcessSearchHomeRs rs = frontendCaseProcessSearchService.homeAction();
        return rs;
    }

    /**
     * 案件進度查詢-驗證
     *
     * @param rq
     * @return CaseProcessSearchValidateRs
     */
//    @ApiOperation(value = "案件進度查詢-驗證")
//    @PostMapping(value = { "/validateAction" },
//                consumes = { MediaType.APPLICATION_JSON_VALUE },
//                produces = { MediaType.APPLICATION_JSON_VALUE })
//    @ResponseBody
//    public CaseProcessSearchValidateRs validateAction(@RequestBody CaseProcessSearchValidateRq rq) {
//        CaseProcessSearchValidateRs rs = frontendCaseProcessSearchService.validateAction(rq);
//        return rs;
//    }

    /**
     * 案件進度查詢-查詢
     *
     * @param rq
     * @return CaseProcessSearchQueryRs
     */
    @ApiOperation(value = "案件進度查詢-查詢")
    @PostMapping(value = { "/queryAction" },
                consumes = { MediaType.APPLICATION_JSON_VALUE },
                produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public CaseProcessSearchQueryRs queryAction(@RequestBody CaseProcessSearchQueryRq rq) {
        CaseProcessSearchQueryRs rs = frontendCaseProcessSearchService.queryAction(rq);
        return rs;
    }

    /**
     * 案件進度查詢-明細
     *
     * @param rq
     * @return CaseProcessSearchDetailRs
     */
    @ApiOperation(value = "案件進度查詢-明細")
    @PostMapping(value = { "/detailAction" },
                consumes = { MediaType.APPLICATION_JSON_VALUE },
                produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    @ValidCode
    public CaseProcessSearchDetailRs detailAction(@RequestBody CaseProcessSearchDetailRq rq) {
        CaseProcessSearchDetailRs rs = frontendCaseProcessSearchService.detailAction(rq);
        return rs;
    }


    /**
     * 案件進度查詢-明細
     *
     * @param rq
     * @return CaseProcessSearchDetailRs
     */
    @ApiOperation(value = "案件進度查詢-檢核")
    @ValidCode
    @PostMapping(value = { "/checkInfo" },
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public CaseProcessSearchDetailCheckRs detailAction(@RequestBody CaseProcessSearchDetailCheckRq rq) {
        CaseProcessSearchDetailCheckRs rs = frontendCaseProcessSearchService.detailCheckAction(rq);
        return rs;
    }

    /**
     * 案件進度查詢-明細保存
     *
     * @param rq
     * @return CaseProcessSearchDetailSaveRs
     */
    @ApiOperation(value = "案件進度查詢-明細-保存")
    @PostMapping(value = { "/detailSaveAction" },
                consumes = {MediaType.APPLICATION_JSON_VALUE },
                produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public CaseProcessSearchDetailSaveRs detailSaveAction(@RequestBody CaseProcessSearchDetailSaveRq rq) throws Exception {
        CaseProcessSearchDetailSaveRs rs = frontendCaseProcessSearchService.detailSaveAction(rq);
        return rs;
    }
}
