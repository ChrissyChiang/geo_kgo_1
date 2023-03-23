package gov.kcg.kgo.geomodel.base; 

import org.springframework.stereotype.Component; 
import io.swagger.annotations.ApiModel; 
import io.swagger.annotations.ApiModelProperty; 
import java.io.Serializable; 
import java.util.ArrayList; 
import java.util.List; 
import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireMainTopic; 

/** 
 * GEO 20210829 add
 *
 * Model for 問卷題組對應
 */

@Component
@ApiModel(description = "問卷題組對應")
public class GeoKgoQuestionnaireMainTopicModelBase implements Serializable {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(notes = "問卷id")
    private String questionId; 

    @ApiModelProperty(notes = "題組id")
    private String topicId; 

    @ApiModelProperty(notes = "題組排序")
    private Integer topicSort; 


    public String getQuestionId() { 
        return questionId; 
    } 

    public void setQuestionId(String questionId) { 
        this.questionId = questionId; 
    } 

    public String getTopicId() { 
        return topicId; 
    } 

    public void setTopicId(String topicId) { 
        this.topicId = topicId; 
    } 

    public Integer getTopicSort() { 
        return topicSort; 
    } 

    public void setTopicSort(Integer topicSort) { 
        this.topicSort = topicSort; 
    } 



    public static GeoKgoQuestionnaireMainTopicModelBase changeToModel(GeoKgoQuestionnaireMainTopic entity) { 
        GeoKgoQuestionnaireMainTopicModelBase model = new GeoKgoQuestionnaireMainTopicModelBase(); 
        model.setQuestionId(entity.getQuestionId()); 
        model.setTopicId(entity.getTopicId()); 
        model.setTopicSort(entity.getTopicSort()); 
        return model; 
    } //changeToModel 

    public static List<GeoKgoQuestionnaireMainTopicModelBase> changeListToModel(List<GeoKgoQuestionnaireMainTopic> entityList) { 
        List<GeoKgoQuestionnaireMainTopicModelBase> modelList = null; 
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoQuestionnaireMainTopicModelBase model = changeToModel(entityList.get(i)); 
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
