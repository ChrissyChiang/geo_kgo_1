package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireAnswerDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * GEO 20210923 add
 *
 * Model for 問卷作答內容
 */

@Component
@ApiModel(description = "問卷作答內容")
public class GeoKgoQuestionnaireAnswerDetailModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "作答內容id(不填)")
    private String detailId;

    @ApiModelProperty(notes = "作答id(不填)")
    private String answerId;

    @ApiModelProperty(notes = "問卷題目id", required = true)
    private String detailCasesetId;

    @ApiModelProperty(notes = "回答內容", required = true)
    private String answerValue;

    @ApiModelProperty(notes = "回答配分")
    private Integer answerScore;


    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

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

    public static GeoKgoQuestionnaireAnswerDetailModel changeToModel(GeoKgoQuestionnaireAnswerDetail entity) {
        GeoKgoQuestionnaireAnswerDetailModel model = new GeoKgoQuestionnaireAnswerDetailModel();
        model.setDetailId(entity.getDetailId());
        model.setAnswerId(entity.getAnswerId());
        model.setDetailCasesetId(entity.getDetailCasesetId());
        model.setAnswerValue(entity.getAnswerValue());
        model.setAnswerScore(entity.getAnswerScore());
        return model;
    } //changeToModel

    public static List<GeoKgoQuestionnaireAnswerDetailModel> changeListToModel(List<GeoKgoQuestionnaireAnswerDetail> entityList) {
        List<GeoKgoQuestionnaireAnswerDetailModel> modelList = null;
        if (entityList!=null) {
            modelList = new ArrayList<>();
            for (int i=0; i<entityList.size(); i++) {
                GeoKgoQuestionnaireAnswerDetailModel model = changeToModel(entityList.get(i));
                modelList.add(model);
            }
        } //if (entityList!=null)
        return modelList;
    } //changeListToModel
}
