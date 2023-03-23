package gov.kcg.kgo.geomodel.base; 

import org.springframework.stereotype.Component; 
import io.swagger.annotations.ApiModel; 
import io.swagger.annotations.ApiModelProperty; 
import java.io.Serializable; 
import java.util.ArrayList; 
import java.util.List; 
import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireCasesetTopic; 

/** 
 * GEO 20210829 add
 *
 * Model for 服務問卷題組檔
 */

@Component
@ApiModel(description = "服務問卷題組檔")
public class GeoKgoQuestionnaireCasesetTopicModelBase implements Serializable {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(notes = "服務問卷題組id")
    private String topicCasesetId; 

    @ApiModelProperty(notes = "服務id")
    private String caseSetId; 

    @ApiModelProperty(notes = "問卷版本")
    private Integer questiinVersion; 

    @ApiModelProperty(notes = "題組名稱")
    private String topicName; 

    @ApiModelProperty(notes = "題組描述")
    private String topicDesc; 

    @ApiModelProperty(notes = "題組排序")
    private Integer topicSort; 


    public String getTopicCasesetId() { 
        return topicCasesetId; 
    } 

    public void setTopicCasesetId(String topicCasesetId) { 
        this.topicCasesetId = topicCasesetId; 
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

    public String getTopicName() { 
        return topicName; 
    } 

    public void setTopicName(String topicName) { 
        this.topicName = topicName; 
    } 

    public String getTopicDesc() { 
        return topicDesc; 
    } 

    public void setTopicDesc(String topicDesc) { 
        this.topicDesc = topicDesc; 
    } 

    public Integer getTopicSort() { 
        return topicSort; 
    } 

    public void setTopicSort(Integer topicSort) { 
        this.topicSort = topicSort; 
    } 



    public static GeoKgoQuestionnaireCasesetTopicModelBase changeToModel(GeoKgoQuestionnaireCasesetTopic entity) { 
        GeoKgoQuestionnaireCasesetTopicModelBase model = new GeoKgoQuestionnaireCasesetTopicModelBase(); 
        model.setTopicCasesetId(entity.getTopicCasesetId()); 
        model.setCaseSetId(entity.getCaseSetId()); 
        model.setQuestiinVersion(entity.getQuestiinVersion()); 
        model.setTopicName(entity.getTopicName()); 
        model.setTopicDesc(entity.getTopicDesc()); 
        model.setTopicSort(entity.getTopicSort()); 
        return model; 
    } //changeToModel 

    public static List<GeoKgoQuestionnaireCasesetTopicModelBase> changeListToModel(List<GeoKgoQuestionnaireCasesetTopic> entityList) { 
        List<GeoKgoQuestionnaireCasesetTopicModelBase> modelList = null; 
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoQuestionnaireCasesetTopicModelBase model = changeToModel(entityList.get(i)); 
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
