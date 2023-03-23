package gov.kcg.kgo.controller.backend;

import gov.kcg.kgo.service.PushMsgCaseSetService;
import gov.kcg.kgo.service.PushMsgManagementService;
import gov.kcg.kgo.viewModel.backend.pushmsg.rq.*;
import gov.kcg.kgo.viewModel.backend.pushmsg.rs.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/backend/pushMsgManagement")
public class PushMsgController {

    @Autowired
    private PushMsgManagementService pushMsgManagementService;

    @Autowired
    private PushMsgCaseSetService pushMsgCaseSetService;

    /**
     * 推播訊息管理-初始畫面
     */
    @ApiOperation(value = "推播訊息管理-初始畫面")
    @PostMapping(value = { "/homeAction" },
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public PushMsgManagementHomeRs pushMsgManagementHomeAction() {
        return pushMsgManagementService.getUserIdAndOrganId();
    }

    /**
     * 推播訊息管理-查詢
     */
    @ApiOperation(value = "推播訊息管理-查詢")
    @PostMapping(value = { "/queryAction" },
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public PushMsgManagementQueryRs pushMsgManagementQueryAction(@RequestBody PushMsgManagementQueryRq rq) {
        return pushMsgManagementService.queryPushMsgData(rq);
    }

    /**
     * 推播訊息管理-編輯
     */
    @ApiOperation(value = "推播訊息管理-編輯")
    @PostMapping(value = { "/editAction" },
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public PushMsgManagementEditRs pushMsgManagementEditAction(@RequestBody PushMsgManagementEditRq rq) {
        return pushMsgManagementService.editPushMsgData(rq);
    }

    /**
     * 推播訊息管理-編輯
     */
    @ApiOperation(value = "推播訊息管理-刪除")
    @PostMapping(value = { "/deleteAction" },
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public PushMsgManagementDeleteRs pushMsgManagementDeleteAction(@RequestBody PushMsgManagementDeleteRq rq) {
        return pushMsgManagementService.deletePushMsgData(rq);
    }

    /**
     * 服務推播訊息-查詢
     */
    @ApiOperation(value = "服務推播訊息-查詢")
    @PostMapping(value = { "/queryCaseSetAction" },
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public PushMsgCaseSetQueryRs pushMsgCaseSetQueryCaseSetAction(@RequestBody PushMsgCaseSetQueryRq rq) {
        return pushMsgCaseSetService.queryPushMsgCaseSetData(rq);
    }

    /**
     * 服務推播訊息-編輯
     */
    @ApiOperation(value = "服務推播訊息-編輯")
    @PostMapping(value = { "/editCaseSetAction" },
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public PushMsgCaseSetEditRs pushMsgCaseSetEditCaseSetAction(@RequestBody PushMsgCaseSetEditRq rq) {
        return pushMsgCaseSetService.editPushMsgCaseSetData(rq);
    }

}
