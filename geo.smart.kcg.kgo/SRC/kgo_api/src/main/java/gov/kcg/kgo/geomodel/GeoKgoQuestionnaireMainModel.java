package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireMain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/** 
 * GEO 20210829 add
 *
 * Model for 問卷主檔
 */

@Component
@ApiModel(description = "問卷主檔")
public class GeoKgoQuestionnaireMainModel implements Serializable {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(notes = "問卷id")
    private String questionId; 

    @ApiModelProperty(notes = "問卷主題")
    private String questionName; 

    @ApiModelProperty(notes = "問卷描述")
    private String questionDesc;

    @ApiModelProperty(notes = "是否為預設問卷不可刪除(GeoBooleanType)")
    private Integer isDefault;

    @ApiModelProperty(notes = "編輯時間")
    private Timestamp editTime;

    @ApiModelProperty(notes = "單位名稱")
    private String organName;


    public String getQuestionId() { 
        return questionId; 
    } 

    public void setQuestionId(String questionId) { 
        this.questionId = questionId; 
    } 

    public String getQuestionName() { 
        return questionName; 
    } 

    public void setQuestionName(String questionName) { 
        this.questionName = questionName; 
    } 

    public String getQuestionDesc() { 
        return questionDesc; 
    } 

    public void setQuestionDesc(String questionDesc) { 
        this.questionDesc = questionDesc; 
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public Timestamp getEditTime() {
        return editTime;
    }

    public void setEditTime(Timestamp editTime) {
        this.editTime = editTime;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }



    public static GeoKgoQuestionnaireMainModel changeToModel(GeoKgoQuestionnaireMain entity) {
        GeoKgoQuestionnaireMainModel model = new GeoKgoQuestionnaireMainModel();
        model.setQuestionId(entity.getQuestionId()); 
        model.setQuestionName(entity.getQuestionName()); 
        model.setQuestionDesc(entity.getQuestionDesc());
        model.setIsDefault(entity.getIsDefault());
        model.setEditTime(entity.getEditTime());
        return model; 
    } //changeToModel 

    public static List<GeoKgoQuestionnaireMainModel> changeListToModel(List<GeoKgoQuestionnaireMain> entityList) {
        List<GeoKgoQuestionnaireMainModel> modelList = null;
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoQuestionnaireMainModel model = changeToModel(entityList.get(i));
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
