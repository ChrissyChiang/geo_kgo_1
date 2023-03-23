package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireTopic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** 
 * GEO 20210829 add
 *
 * Model for 問卷題組檔
 */

@Component
@ApiModel(description = "問卷題組檔")
public class GeoKgoQuestionnaireTopicModel implements Serializable {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(notes = "題組id(新增不填, 編輯或刪除時必填)")
    private String topicId; 

    @ApiModelProperty(notes = "題組名稱", required = true)
    private String topicName; 

    @ApiModelProperty(notes = "題組描述", required = true)
    private String topicDesc; 

    @ApiModelProperty(notes = "是否有效(刪除時必填; 1表有效, 0表刪除)")
    private Integer isEnable;

    @ApiModelProperty(notes = "問卷id", required = true)
    private String questionId;

    @ApiModelProperty(notes = "題組排序", required = true)
    private Integer topicSort;

    @ApiModelProperty(notes = "子題目清單", required = true)
    private List<GeoKgoQuestionnaireTopicDetailModel> detailList;


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

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public Integer getTopicSort() {
        return topicSort;
    }

    public void setTopicSort(Integer topicSort) {
        this.topicSort = topicSort;
    }

    public List<GeoKgoQuestionnaireTopicDetailModel> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<GeoKgoQuestionnaireTopicDetailModel> detailList) {
        this.detailList = detailList;
    }


    public static GeoKgoQuestionnaireTopicModel changeToModel(GeoKgoQuestionnaireTopic entity) {
        GeoKgoQuestionnaireTopicModel model = new GeoKgoQuestionnaireTopicModel();
        model.setTopicId(entity.getTopicId()); 
        model.setTopicName(entity.getTopicName()); 
        model.setTopicDesc(entity.getTopicDesc()); 
        model.setIsEnable(entity.getIsEnable()); 
        return model; 
    } //changeToModel 

    public static List<GeoKgoQuestionnaireTopicModel> changeListToModel(List<GeoKgoQuestionnaireTopic> entityList) {
        List<GeoKgoQuestionnaireTopicModel> modelList = null;
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoQuestionnaireTopicModel model = changeToModel(entityList.get(i));
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
