package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireCaseset;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** 
 * GEO 20210922 add
 *
 * Model for 服務問卷-主檔
 */

@Component
@ApiModel(description = "服務問卷-主檔")
public class GeoKgoQuestionnaireCaseSetModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服務id")
    private String caseSetId;

    @ApiModelProperty(value = "問卷主題")
    private String questionName;

    @ApiModelProperty(value = "問卷描述")
    private String questionDesc;

    @ApiModelProperty(value = "起始日 yyyy-MM-dd ")
    private String dateStart;

    @ApiModelProperty(value = "結束日 yyyy-MM-dd ")
    private String dateEnd;

    @ApiModelProperty(value = "是否有效 (1-有效、0-無效) ")
    private Integer isEnable;

    @ApiModelProperty(value = "編輯單位")
    private String editOrgan;

    @ApiModelProperty(value = "編輯人員")
    private String editUser;

    @ApiModelProperty(notes = "問卷版本")
    private Integer questiinVersion;

    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
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

    public Integer getQuestiinVersion() {
        return questiinVersion;
    }

    public void setQuestiinVersion(Integer questiinVersion) {
        this.questiinVersion = questiinVersion;
    }

    public static GeoKgoQuestionnaireCaseSetModel changeToModel(GeoKgoQuestionnaireCaseset entity) {
        GeoKgoQuestionnaireCaseSetModel model = new GeoKgoQuestionnaireCaseSetModel();
        model.setCaseSetId(entity.getCaseSetId());
        model.setQuestionName(entity.getQuestionName()); 
        model.setQuestionDesc(entity.getQuestionDesc());
        model.setDateStart(entity.getDateStart());
        model.setDateEnd(entity.getDateEnd());
        model.setIsEnable(entity.getIsEnable());
        model.setEditOrgan(entity.getEditOrgan());
        model.setEditUser(entity.getEditUser());
        model.setQuestiinVersion(entity.getQuestiinVersion());
        return model; 
    } //changeToModel 

    public static List<GeoKgoQuestionnaireCaseSetModel> changeListToModel(List<GeoKgoQuestionnaireCaseset> entityList) {
        List<GeoKgoQuestionnaireCaseSetModel> modelList = null;
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoQuestionnaireCaseSetModel model = changeToModel(entityList.get(i));
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
