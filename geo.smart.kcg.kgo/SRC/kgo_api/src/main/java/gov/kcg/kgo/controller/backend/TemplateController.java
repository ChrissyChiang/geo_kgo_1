package gov.kcg.kgo.controller.backend;

import gov.kcg.kgo.geoviewmodel.backend.rs.GeoTemplateOrganViewActionRs;
import gov.kcg.kgo.service.TemplateService;
import gov.kcg.kgo.viewModel.backend.template.add.rq.TemplateAddActionRq;
import gov.kcg.kgo.viewModel.backend.template.add.rs.TemplateAddActionRs;
import gov.kcg.kgo.viewModel.backend.template.delete.rq.TemplateDeleteActionRq;
import gov.kcg.kgo.viewModel.backend.template.delete.rs.TemplateDeleteActionRs;
import gov.kcg.kgo.viewModel.backend.template.query.rq.TemplateQueryActionRq;
import gov.kcg.kgo.viewModel.backend.template.query.rs.TemplateQueryActionRs;
import gov.kcg.kgo.viewModel.backend.template.update.rq.TemplateUpdateActionRq;
import gov.kcg.kgo.viewModel.backend.template.update.rs.TemplateUpdateActionRs;
import gov.kcg.kgo.viewModel.backend.template.view.rq.TemplateViewActionRq;
import gov.kcg.kgo.viewModel.backend.template.view.rs.TemplateViewActionRs;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/backend/template")
@Api(tags = {"template-controller 後台-後台表單維護"})
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    /**
     * 後台表單維護-查詢
     *
     * @return
     */
    @ApiOperation(value = "後台表單維護-查詢 ")
    @RequestMapping(value = { "/queryAction" }, method = { RequestMethod.POST }, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public TemplateQueryActionRs templateQueryAction(@RequestBody TemplateQueryActionRq rq) {
        TemplateQueryActionRs rs = templateService.templateQueryAction(rq);
        return rs;
    }

    /**
     * 後台表單維護-明細
     *
     * @return
     */
    @ApiOperation(value = "後台表單維護-明細 ")
    @RequestMapping(value = { "/viewAction" }, method = { RequestMethod.POST }, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public TemplateViewActionRs templateViewAction(@RequestBody TemplateViewActionRq rq) {
        TemplateViewActionRs rs = templateService.templateViewAction(rq);
        return rs;
    }

    /**
     * GEO 20211108 add for 機關審核表單
     * 後台表單維護–取得機關審核表單初始欄位明細
     * @param rq
     * @return
     */
    @ApiOperation(value = "* 後台表單維護–取得機關審核表單初始欄位明細")
    @RequestMapping(value = { "/organ/viewAction" }, method = { RequestMethod.POST }, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public GeoTemplateOrganViewActionRs templateOrganViewAction(@RequestBody ApiRequest rq) {
        GeoTemplateOrganViewActionRs rs = templateService.templateOrganViewAction();
        return rs;
    }

    /**
     * 後台表單維護-更新
     *
     * @return
     */
    @ApiOperation(value = "後台表單維護-更新 ")
    @RequestMapping(value = { "/updateAction" }, method = { RequestMethod.POST }, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public TemplateUpdateActionRs templateUpdateAction(@RequestBody TemplateUpdateActionRq rq) {
        TemplateUpdateActionRs rs = templateService.templateUpdateAction(rq);
        return rs;
    }

    /**
     * 後台表單維護-新增
     *
     * @return
     */
    @ApiOperation(value = "後台表單維護-新增 ")
    @RequestMapping(value = { "/addAction" }, method = { RequestMethod.POST }, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public TemplateAddActionRs templateAddAction(@RequestBody TemplateAddActionRq rq) {
        TemplateAddActionRs rs = templateService.templateAddAction(rq);
        return rs;
    }

    /**
     * 後台表單維護-刪除
     *
     * @return
     */
    @ApiOperation(value = "後台表單維護-刪除 ")
    @RequestMapping(value = { "/deleteAction" }, method = { RequestMethod.POST }, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public TemplateDeleteActionRs templateDeleteAction(@RequestBody TemplateDeleteActionRq rq) {
        TemplateDeleteActionRs rs = templateService.templateDeleteAction(rq);
        return rs;
    }
}
