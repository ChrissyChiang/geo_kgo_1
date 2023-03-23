package gov.kcg.kgo.geomodel.base; 

import org.springframework.stereotype.Component; 
import io.swagger.annotations.ApiModel; 
import io.swagger.annotations.ApiModelProperty; 
import java.io.Serializable; 
import java.util.ArrayList; 
import java.util.List; 
import java.sql.Timestamp; 
import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireCaseset; 

/** 
 * GEO 20210829 add
 *
 * Model for 服務問卷主檔
 */

@Component
@ApiModel(description = "服務問卷主檔")
public class GeoKgoQuestionnaireCasesetModelBase implements Serializable {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(notes = "服務id")
    private String caseSetId; 

    @ApiModelProperty(notes = "問卷版本")
    private Integer questiinVersion; 

    @ApiModelProperty(notes = "問卷主題")
    private String questionName; 

    @ApiModelProperty(notes = "問卷描述")
    private String questionDesc; 

    @ApiModelProperty(notes = "起始日")
    private String dateStart; 

    @ApiModelProperty(notes = "結束日")
    private String dateEnd; 

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

    public String getDateStart() { 
        return dateStart; 
    } 

    public void setDateStart(String dateStart) { 
        this.dateStart = dateStart; 
    } 

    public String getDateEnd() { 
        return dateEnd; 
    } 

    public void setDateEnd(String dateEnd) { 
        this.dateEnd = dateEnd; 
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



    public static GeoKgoQuestionnaireCasesetModelBase changeToModel(GeoKgoQuestionnaireCaseset entity) { 
        GeoKgoQuestionnaireCasesetModelBase model = new GeoKgoQuestionnaireCasesetModelBase(); 
        model.setCaseSetId(entity.getCaseSetId()); 
        model.setQuestiinVersion(entity.getQuestiinVersion()); 
        model.setQuestionName(entity.getQuestionName()); 
        model.setQuestionDesc(entity.getQuestionDesc()); 
        model.setDateStart(entity.getDateStart()); 
        model.setDateEnd(entity.getDateEnd()); 
        model.setIsDefault(entity.getIsDefault()); 
        model.setIsEnable(entity.getIsEnable()); 
        model.setEditOrgan(entity.getEditOrgan()); 
        model.setEditUser(entity.getEditUser()); 
        model.setEditTime(entity.getEditTime()); 
        return model; 
    } //changeToModel 

    public static List<GeoKgoQuestionnaireCasesetModelBase> changeListToModel(List<GeoKgoQuestionnaireCaseset> entityList) { 
        List<GeoKgoQuestionnaireCasesetModelBase> modelList = null; 
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoQuestionnaireCasesetModelBase model = changeToModel(entityList.get(i)); 
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
