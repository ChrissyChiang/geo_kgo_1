package gov.kcg.kgo.geomodel.base; 

import org.springframework.stereotype.Component; 
import io.swagger.annotations.ApiModel; 
import io.swagger.annotations.ApiModelProperty; 
import java.io.Serializable; 
import java.util.ArrayList; 
import java.util.List; 
import java.sql.Timestamp; 
import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireAnswer; 

/** 
 * GEO 20210829 add
 *
 * Model for 問卷作答主檔
 */

@Component
@ApiModel(description = "問卷作答主檔")
public class GeoKgoQuestionnaireAnswerModelBase implements Serializable {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(notes = "作答id")
    private String answerId; 

    @ApiModelProperty(notes = "案件id")
    private String caseId; 

    @ApiModelProperty(notes = "服務id")
    private String caseSetId; 

    @ApiModelProperty(notes = "問卷版本")
    private Integer questiinVersion; 

    @ApiModelProperty(notes = "作答者id")
    private String applyUserId; 

    @ApiModelProperty(notes = "作答者姓名")
    private String applyName; 

    @ApiModelProperty(notes = "作答日期")
    private Timestamp applyDate; 


    public String getAnswerId() { 
        return answerId; 
    } 

    public void setAnswerId(String answerId) { 
        this.answerId = answerId; 
    } 

    public String getCaseId() { 
        return caseId; 
    } 

    public void setCaseId(String caseId) { 
        this.caseId = caseId; 
    } 

    public String getCaseSetId() { 
        return caseSetId; 
    } 

    public void setCaseSetId(String caseSetId) { 
        this.caseSetId = caseSetId; 
    } 

    public Integer getQuestiinVersion() { 
        return questiinVersion; 
    } 

    public void setQuestiinVersion(Integer questiinVersion) { 
        this.questiinVersion = questiinVersion; 
    } 

    public String getApplyUserId() { 
        return applyUserId; 
    } 

    public void setApplyUserId(String applyUserId) { 
        this.applyUserId = applyUserId; 
    } 

    public String getApplyName() { 
        return applyName; 
    } 

    public void setApplyName(String applyName) { 
        this.applyName = applyName; 
    } 

    public Timestamp getApplyDate() { 
        return applyDate; 
    } 

    public void setApplyDate(Timestamp applyDate) { 
        this.applyDate = applyDate; 
    } 



    public static GeoKgoQuestionnaireAnswerModelBase changeToModel(GeoKgoQuestionnaireAnswer entity) { 
        GeoKgoQuestionnaireAnswerModelBase model = new GeoKgoQuestionnaireAnswerModelBase(); 
        model.setAnswerId(entity.getAnswerId()); 
        model.setCaseId(entity.getCaseId()); 
        model.setCaseSetId(entity.getCaseSetId()); 
        model.setQuestiinVersion(entity.getQuestiinVersion()); 
        model.setApplyUserId(entity.getApplyUserId()); 
        model.setApplyName(entity.getApplyName()); 
        model.setApplyDate(entity.getApplyDate()); 
        return model; 
    } //changeToModel 

    public static List<GeoKgoQuestionnaireAnswerModelBase> changeListToModel(List<GeoKgoQuestionnaireAnswer> entityList) { 
        List<GeoKgoQuestionnaireAnswerModelBase> modelList = null; 
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoQuestionnaireAnswerModelBase model = changeToModel(entityList.get(i)); 
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
