package gov.kcg.kgo.geocontroller.backend;

import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.geoservice.GeoQuestionnaireService;
import gov.kcg.kgo.geoviewmodel.backend.rq.*;
import gov.kcg.kgo.geoviewmodel.backend.rs.*;
import gov.kcg.kgo.geoviewmodel.frontend.rq.GeoQuestionnaireCaseSetQueryRq;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoQuestionnaireCaseSetQueryRs;
import gov.kcg.kgo.util.SsoUtil;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/backend/questionnaire")
@Api(tags = {"geo-questionnaire-setting-controller 後台-問卷管理"})
public class GeoQuestionnaireSettingController {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoQuestionnaireSettingController.class);

    @Autowired
    GeoQuestionnaireService geoQuestionnaireService;

    /**
     * 問卷管理-取得問卷列表
     *
     * @param rq
     * @param request
     */
    @ApiOperation(value = "問卷管理-取得問卷列表")
    @RequestMapping(value = {"/questionnaireList"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoQuestionnaireListRs questionnaireList(@RequestBody GeoQuestionnaireListRq rq, HttpServletRequest request) {
        GeoQuestionnaireListRs rs = geoQuestionnaireService.getQuestionnaireList(rq);
        return rs;
    } //questionnaireList

    /**
     * 問卷管理-新增問卷/ 問卷管理-編輯:新增編輯問卷主內容
     *
     * @param rq
     * @param request
     */
    @ApiOperation(value = "問卷管理-新增問卷/ 問卷管理-編輯:新增編輯問卷主內容")
    @RequestMapping(value = {"/questionnaireEdit"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoQuestionnaireQueryRs questionnaireEdit(@RequestBody GeoQuestionnaireEditRq rq, HttpServletRequest request) {
        //LOGGER.info("GeoQuestionnaireSettingController questionnaireEdit...");
        GeoQuestionnaireQueryRs rs = geoQuestionnaireService.editQuestionnaire(rq, (BackendLoginUserInfo) WebUtils.getSessionAttribute(request, SsoUtil.BACKEND_USER_INO_KEY));
        return rs;
    } //questionnaireEdit

    /**
     * 問卷管理-刪除問卷主內容
     *
     * @param rq
     * @param request
     */
    @ApiOperation(value = "問卷管理-刪除問卷主內容")
    @RequestMapping(value = {"/questionnaireDel"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoQuestionnaireQueryRs questionnaireDelete(@RequestBody GeoQuestionnaireQueryRq rq, HttpServletRequest request) {
        GeoQuestionnaireQueryRs rs = geoQuestionnaireService.deleteQuestionnaire(rq, (BackendLoginUserInfo) WebUtils.getSessionAttribute(request, SsoUtil.BACKEND_USER_INO_KEY));
        return rs;
    } //questionnaireDelete

    /**
     * 問卷管理-編輯問卷：取得該問卷題組列表(含題目)
     *
     * @param rq
     * @param request
     */
    @ApiOperation(value = "問卷管理-編輯問卷管理：取得問卷題組列表(含題目)")
    @RequestMapping(value = {"/questionnaireTopicList"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoQuestionnaireTopicListRs questionnaireTopicList(@RequestBody GeoQuestionnaireQueryRq rq, HttpServletRequest request) {
        GeoQuestionnaireTopicListRs rs = geoQuestionnaireService.getQuestionnaireTopicList(rq);
        return rs;
    } //questionnaireTopicList

    /**
     * 問卷管理-編輯問卷-題組管理-新增題組/編輯題組:新增編輯問卷題組內容
     *
     * @param rq
     * @param request
     */
    @ApiOperation(value = "問卷管理-編輯問卷-題組管理-新增題組/編輯題組:新增編輯問卷題組內容")
    @RequestMapping(value = {"/questionnaireTopicEdit"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoQuestionnaireTopicQueryRs questionnaireTopicEdit(@RequestBody GeoQuestionnaireTopicEditRq rq, HttpServletRequest request) {
        //LOGGER.info("GeoQuestionnaireSettingController questionnaireTopicEdit...");
        GeoQuestionnaireTopicQueryRs rs = geoQuestionnaireService.editQuestionnaireTopic(rq);
        return rs;
    } //questionnaireTopicEdit

    /**
     * 問卷管理-編輯問卷-題組管理:取得全部題組列表(不含題目)
     *
     * @param request
     */
    @ApiOperation(value = "問卷管理-編輯問卷-題組管理:取得全部題組列表(不含題目)")
    @RequestMapping(value = {"/allTopicList"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoQuestionnaireTopicListRs getAllTopicList(@RequestBody ApiRequest rq, HttpServletRequest request) {
        GeoQuestionnaireTopicListRs rs = geoQuestionnaireService.getAllTopicList();
        return rs;
    } //getAllTopicList

    /**
     * 問卷管理-編輯問卷-題組管理:取得題組的子題目列表
     *
     * @param rq
     * @param request
     */
    @ApiOperation(value = "問卷管理-編輯問卷-題組管理:取得題組的子題目列表")
    @RequestMapping(value = {"/topicDetailList"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoQuestionnaireDetailListRs getTopicDetailList(@RequestBody GeoQuestionnaireTopicQueryRq rq, HttpServletRequest request) {
        GeoQuestionnaireDetailListRs rs = geoQuestionnaireService.getTopicDetailList(rq);
        return rs;
    } //getAllTopicList

    /**
     * 服務管理-問卷設定:取得該項服務問卷
     *
     * @param rq
     * @param request
     */
    @ApiOperation(value = "服務管理-問卷設定:取得該項服務問卷")
    @RequestMapping(value = {"/getQuestionnaireCaseSet"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoQuestionnaireCaseSetQueryRs getQuestionnaireCaseSet(@RequestBody GeoQuestionnaireCaseSetQueryRq rq,
                                                                  HttpServletRequest request) {
        GeoQuestionnaireCaseSetQueryRs rs = geoQuestionnaireService.getQuestionnaireByCaseSet(rq);
        return rs;
    }

    /**
     * 服務管理-問卷設定:編輯該項服務問卷
     *
     * @param rq
     * @param request
     */
    @ApiOperation(value = "服務管理-問卷設定:編輯該項服務問卷")
    @RequestMapping(value = {"/questionnaireCaseSetEdit"}, method = {RequestMethod.POST}, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoQuestionnaireCaseSetQueryRs editQuestionnaireCaseSet(@RequestBody GeoQuestionnaireCaseSetEditRq rq,
                                                                   HttpServletRequest request) {
        GeoQuestionnaireCaseSetQueryRs rs = geoQuestionnaireService.editQuestionnaireCaseSet(rq, (BackendLoginUserInfo) WebUtils.getSessionAttribute(request, SsoUtil.BACKEND_USER_INO_KEY));
        return rs;
    }

}
