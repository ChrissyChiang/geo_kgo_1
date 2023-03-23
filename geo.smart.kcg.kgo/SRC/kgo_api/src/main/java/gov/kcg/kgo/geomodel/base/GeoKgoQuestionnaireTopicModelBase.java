package gov.kcg.kgo.geomodel.base; 

import org.springframework.stereotype.Component; 
import io.swagger.annotations.ApiModel; 
import io.swagger.annotations.ApiModelProperty; 
import java.io.Serializable; 
import java.util.ArrayList; 
import java.util.List; 
import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireTopic; 

/** 
 * GEO 20210829 add
 *
 * Model for 問卷題組檔
 */

@Component
@ApiModel(description = "問卷題組檔")
public class GeoKgoQuestionnaireTopicModelBase implements Serializable {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(notes = "題組id")
    private String topicId; 

    @ApiModelProperty(notes = "題組名稱")
    private String topicName; 

    @ApiModelProperty(notes = "題組描述")
    private String topicDesc; 

    @ApiModelProperty(notes = "是否有效(GeoBooleanType)")
    private Integer isEnable; 


    public String getTopicId() { 
        return topicId; 
    } 

    public void setTopicId(String topicId) { 
        this.topicId = topicId; 
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

    public Integer getIsEnable() { 
        return isEnable; 
    } 

    public void setIsEnable(Integer isEnable) { 
        this.isEnable = isEnable; 
    } 



    public static GeoKgoQuestionnaireTopicModelBase changeToModel(GeoKgoQuestionnaireTopic entity) { 
        GeoKgoQuestionnaireTopicModelBase model = new GeoKgoQuestionnaireTopicModelBase(); 
        model.setTopicId(entity.getTopicId()); 
        model.setTopicName(entity.getTopicName()); 
        model.setTopicDesc(entity.getTopicDesc()); 
        model.setIsEnable(entity.getIsEnable()); 
        return model; 
    } //changeToModel 

    public static List<GeoKgoQuestionnaireTopicModelBase> changeListToModel(List<GeoKgoQuestionnaireTopic> entityList) { 
        List<GeoKgoQuestionnaireTopicModelBase> modelList = null; 
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoQuestionnaireTopicModelBase model = changeToModel(entityList.get(i)); 
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
