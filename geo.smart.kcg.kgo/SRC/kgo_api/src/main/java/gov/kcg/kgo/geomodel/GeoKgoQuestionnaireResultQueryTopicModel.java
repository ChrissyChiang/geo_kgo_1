package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireCasesetTopic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * GEO 20211012 add
 * Model for 問卷結果題組檔
 */
@Component
@ApiModel(description = "問卷結果題組檔")
public class GeoKgoQuestionnaireResultQueryTopicModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "題組id")
    private String topicCaseSetId;

    @ApiModelProperty(notes = "題組名稱")
    private String topicName;

    @ApiModelProperty(notes = "題組描述")
    private String topicDesc;

    @ApiModelProperty(notes = "題組排序")
    private Integer topicSort;

    @ApiModelProperty(notes = "子題目清單", required = true)
    private List<GeoKgoQuestionnaireResultQueryDetailModel> detailList;

    public String getTopicCaseSetId() {
        return topicCaseSetId;
    }

    public void setTopicCaseSetId(String topicCaseSetId) {
        this.topicCaseSetId = topicCaseSetId;
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

    public List<GeoKgoQuestionnaireResultQueryDetailModel> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<GeoKgoQuestionnaireResultQueryDetailModel> detailList) {
        this.detailList = detailList;
    }

    public static GeoKgoQuestionnaireResultQueryTopicModel changeToModel(GeoKgoQuestionnaireCasesetTopic entity) {
        GeoKgoQuestionnaireResultQueryTopicModel model = new GeoKgoQuestionnaireResultQueryTopicModel();
        model.setTopicCaseSetId(entity.getTopicCasesetId());
        model.setTopicName(entity.getTopicName());
        model.setTopicDesc(entity.getTopicDesc());
        model.setTopicSort(entity.getTopicSort());
        return model;
    } //changeToModel

    public static List<GeoKgoQuestionnaireResultQueryTopicModel> changeListToModel(List<GeoKgoQuestionnaireCasesetTopic> entityList) {
        List<GeoKgoQuestionnaireResultQueryTopicModel> modelList = null;
        if (entityList != null) {
            modelList = new ArrayList<>();
            for (int i = 0; i < entityList.size(); i++) {
                GeoKgoQuestionnaireResultQueryTopicModel model = changeToModel(entityList.get(i));
                modelList.add(model);
            }
        } //if (entityList!=null)
        return modelList;
    } //changeListToModel
}
