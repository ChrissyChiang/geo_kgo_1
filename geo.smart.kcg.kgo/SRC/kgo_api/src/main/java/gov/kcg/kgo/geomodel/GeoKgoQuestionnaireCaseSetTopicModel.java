package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireCasesetTopic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** 
 * GEO 20210922 add
 *
 * Model for 服務問卷-題組檔
 */

@Component
@ApiModel(description = "服務問卷-題組檔")
public class GeoKgoQuestionnaireCaseSetTopicModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服務問卷題組id (不填)")
    private String topicCasesetId;

    @ApiModelProperty(notes = "題組名稱", required = true)
    private String topicName; 

    @ApiModelProperty(notes = "題組描述", required = true)
    private String topicDesc;

    @ApiModelProperty(notes = "題組排序", required = true)
    private Integer topicSort;

    @ApiModelProperty(notes = "子題目清單", required = true)
    private List<GeoKgoQuestionnaireCaseSetDetailModel> detailList;

    public String getTopicCasesetId() {
        return topicCasesetId;
    }

    public void setTopicCasesetId(String topicCasesetId) {
        this.topicCasesetId = topicCasesetId;
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

    public List<GeoKgoQuestionnaireCaseSetDetailModel> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<GeoKgoQuestionnaireCaseSetDetailModel> detailList) {
        this.detailList = detailList;
    }

    public static GeoKgoQuestionnaireCaseSetTopicModel changeToModel(GeoKgoQuestionnaireCasesetTopic entity) {
        GeoKgoQuestionnaireCaseSetTopicModel model = new GeoKgoQuestionnaireCaseSetTopicModel();
        model.setTopicCasesetId(entity.getTopicCasesetId());
        model.setTopicName(entity.getTopicName());
        model.setTopicDesc(entity.getTopicDesc());
        model.setTopicSort(entity.getTopicSort());
        return model;
    } //changeToModel

    public static List<GeoKgoQuestionnaireCaseSetTopicModel> changeListToModel(List<GeoKgoQuestionnaireCasesetTopic> entityList) {
        List<GeoKgoQuestionnaireCaseSetTopicModel> modelList = null;
        if (entityList!=null) {
            modelList = new ArrayList<>();
            for (int i=0; i<entityList.size(); i++) {
                GeoKgoQuestionnaireCaseSetTopicModel model = changeToModel(entityList.get(i));
                modelList.add(model);
            }
        } //if (entityList!=null)
        return modelList;
    } //changeListToModel
}
