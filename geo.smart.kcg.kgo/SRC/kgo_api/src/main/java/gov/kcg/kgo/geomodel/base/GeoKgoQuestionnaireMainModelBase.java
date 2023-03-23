package gov.kcg.kgo.geomodel.base; 

import org.springframework.stereotype.Component; 
import io.swagger.annotations.ApiModel; 
import io.swagger.annotations.ApiModelProperty; 
import java.io.Serializable; 
import java.util.ArrayList; 
import java.util.List; 
import java.sql.Timestamp; 
import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireMain; 

/** 
 * GEO 20210829 add
 *
 * Model for 問卷主檔
 */

@Component
@ApiModel(description = "問卷主檔")
public class GeoKgoQuestionnaireMainModelBase implements Serializable {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(notes = "問卷id")
    private String questionId; 

    @ApiModelProperty(notes = "問卷主題")
    private String questionName; 

    @ApiModelProperty(notes = "問卷描述")
    private String questionDesc;

    @ApiModelProperty(notes = "是否為預設問卷不可刪除(GeoBooleanType)")
    private Integer isDefault;

    @ApiModelProperty(notes = "是否有效(GeoBooleanType)")
    private Integer isEnable; 

    @ApiModelProperty(notes = "編輯單位")
    private String editOrgan; 

    @ApiModelProperty(notes = "編輯人員")
    private String editUser; 

    @ApiModelProperty(notes = "編輯時間")
    private Timestamp editTime; 


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

    public Integer getIsEnable() { 
        return isEnable; 
    } 

    public void setIsEnable(Integer isEnable) { 
        this.isEnable = isEnable; 
    } 

    public String getEditOrgan() { 
        return editOrgan; 
    } 

    public void setEditOrgan(String editOrgan) { 
        this.editOrgan = editOrgan; 
    } 

    public String getEditUser() { 
        return editUser; 
    } 

    public void setEditUser(String editUser) { 
        this.editUser = editUser; 
    } 

    public Timestamp getEditTime() { 
        return editTime; 
    } 

    public void setEditTime(Timestamp editTime) { 
        this.editTime = editTime; 
    } 



    public static GeoKgoQuestionnaireMainModelBase changeToModel(GeoKgoQuestionnaireMain entity) { 
        GeoKgoQuestionnaireMainModelBase model = new GeoKgoQuestionnaireMainModelBase(); 
        model.setQuestionId(entity.getQuestionId()); 
        model.setQuestionName(entity.getQuestionName()); 
        model.setQuestionDesc(entity.getQuestionDesc());
        model.setIsDefault(entity.getIsDefault());
        model.setIsEnable(entity.getIsEnable()); 
        model.setEditOrgan(entity.getEditOrgan()); 
        model.setEditUser(entity.getEditUser()); 
        model.setEditTime(entity.getEditTime()); 
        return model; 
    } //changeToModel 

    public static List<GeoKgoQuestionnaireMainModelBase> changeListToModel(List<GeoKgoQuestionnaireMain> entityList) { 
        List<GeoKgoQuestionnaireMainModelBase> modelList = null; 
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoQuestionnaireMainModelBase model = changeToModel(entityList.get(i)); 
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
