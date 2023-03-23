package gov.kcg.kgo.geocontroller.frontend;

import gov.kcg.kgo.geoservice.GeoQuestionnaireService;
import gov.kcg.kgo.geoviewmodel.frontend.rq.GeoQuestionnaireCaseSetQueryRq;
import gov.kcg.kgo.geoviewmodel.frontend.rq.GeoQuestionnaireSaveAnswerRq;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoQuestionnaireCaseSetQueryRs;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoQuestionnaireSaveAnswerRs;
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

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/frontend/questionnaire")
@Api(tags = {"geo-questionnaire-frontend-controller 前台-問卷"})
public class GeoQuestionnaireController {

    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoQuestionnaireController.class);

    @Autowired
    GeoQuestionnaireService geoQuestionnaireService;

    /**
     * 問卷-取得該項服務問卷
     *
     * @param rq
     * @param request
     */
    @ApiOperation(value ="問卷-取得該項服務問卷")
    @RequestMapping(value = { "/getQuestionnaire" }, method = { RequestMethod.POST }, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public GeoQuestionnaireCaseSetQueryRs getQuestionnaireByCaseSet(@RequestBody GeoQuestionnaireCaseSetQueryRq rq,
                                                             HttpServletRequest request) {
        GeoQuestionnaireCaseSetQueryRs rs = geoQuestionnaireService.getQuestionnaireByCaseSetAndIsEnable(rq);
        return rs;
    }

    /**
     * 問卷:儲存服務問卷答案(主檔、內容)
     *
     * @param rq
     * @param request
     */
    @ApiOperation(value = "問卷:儲存服務問卷答案(主檔、內容)")
    @RequestMapping(value = { "/saveQuestionnaireAnswer" }, method = { RequestMethod.POST }, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public GeoQuestionnaireSaveAnswerRs questionnaireAnswerInsert(@RequestBody GeoQuestionnaireSaveAnswerRq rq,
                                                                  HttpServletRequest request) {
        GeoQuestionnaireSaveAnswerRs rs = geoQuestionnaireService.insertQuestionnaireAnswer(rq);
        return rs;
    }

}
