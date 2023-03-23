package gov.kcg.kgo.geomodel.base; 

import org.springframework.stereotype.Component; 
import io.swagger.annotations.ApiModel; 
import io.swagger.annotations.ApiModelProperty; 
import java.io.Serializable; 
import java.util.ArrayList; 
import java.util.List; 
import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireAnswerDetail; 

/** 
 * GEO 20210829 add
 *
 * Model for 問卷作答內容
 */

@Component
@ApiModel(description = "問卷作答內容")
public class GeoKgoQuestionnaireAnswerDetailModelBase implements Serializable {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(notes = "作答內容id")
    private String detailId; 

    @ApiModelProperty(notes = "作答id")
    private String answerId; 

    @ApiModelProperty(notes = "問卷題目id")
    private String detailCasesetId; 

    @ApiModelProperty(notes = "回答內容")
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



    public static GeoKgoQuestionnaireAnswerDetailModelBase changeToModel(GeoKgoQuestionnaireAnswerDetail entity) { 
        GeoKgoQuestionnaireAnswerDetailModelBase model = new GeoKgoQuestionnaireAnswerDetailModelBase(); 
        model.setDetailId(entity.getDetailId()); 
        model.setAnswerId(entity.getAnswerId()); 
        model.setDetailCasesetId(entity.getDetailCasesetId()); 
        model.setAnswerValue(entity.getAnswerValue()); 
        model.setAnswerScore(entity.getAnswerScore()); 
        return model; 
    } //changeToModel 

    public static List<GeoKgoQuestionnaireAnswerDetailModelBase> changeListToModel(List<GeoKgoQuestionnaireAnswerDetail> entityList) { 
        List<GeoKgoQuestionnaireAnswerDetailModelBase> modelList = null; 
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoQuestionnaireAnswerDetailModelBase model = changeToModel(entityList.get(i)); 
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
