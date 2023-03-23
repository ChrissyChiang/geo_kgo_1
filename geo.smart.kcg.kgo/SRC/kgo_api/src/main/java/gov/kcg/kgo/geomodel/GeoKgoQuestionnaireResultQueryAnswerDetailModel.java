package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireAnswerDetail;
import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireCasesetDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * GEO 20211012 add
 * Model for 問卷結果問卷作答內容
 */
@Component
@ApiModel(description = "問卷結果問卷作答內容")
public class GeoKgoQuestionnaireResultQueryAnswerDetailModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "問卷題目id")
    private String detailCasesetId;

    @ApiModelProperty(notes = "回答內容")
    private String answerValue;

    @ApiModelProperty(notes = "回答配分")
    private Integer answerScore;

    @ApiModelProperty(notes = "次數")
    private Integer frequencyCount;

    public String getDetailCasesetId() {
        return detailCasesetId;
    }

    public void setDetailCasesetId(String detailCasesetId) {
        this.detailCasesetId = detailCasesetId;
    }

    public String getAnswerValue() {
        return answerValue;
    }

    public void setAnswerValue(String answerValue) {
        this.answerValue = answerValue;
    }

    public Integer getAnswerScore() {
        return answerScore;
    }

    public void setAnswerScore(Integer answerScore) {
        this.answerScore = answerScore;
    }

    public Integer getFrequencyCount() {
        return frequencyCount;
    }

    public void setFrequencyCount(Integer frequencyCount) {
        this.frequencyCount = frequencyCount;
    }

    public static GeoKgoQuestionnaireResultQueryAnswerDetailModel changeToModel(GeoKgoQuestionnaireAnswerDetail entity) {
        GeoKgoQuestionnaireResultQueryAnswerDetailModel model = new GeoKgoQuestionnaireResultQueryAnswerDetailModel();
        model.setDetailCasesetId(entity.getDetailCasesetId());
        model.setAnswerScore(entity.getAnswerScore());
        model.setDetailCasesetId(entity.getDetailCasesetId());
        model.setAnswerValue(entity.getAnswerValue());
        return model;
    } //changeToModel

    public static List<GeoKgoQuestionnaireResultQueryAnswerDetailModel> changeListToModel(List<GeoKgoQuestionnaireAnswerDetail> entityList) {
        List<GeoKgoQuestionnaireResultQueryAnswerDetailModel> modelList = null;
        if (entityList!=null) {
            modelList = new ArrayList<>();
            for (int i=0; i<entityList.size(); i++) {
                GeoKgoQuestionnaireResultQueryAnswerDetailModel model = changeToModel(entityList.get(i));
                modelList.add(model);
            }
        } //if (entityList!=null)
        return modelList;
    } //changeListToModel
}
