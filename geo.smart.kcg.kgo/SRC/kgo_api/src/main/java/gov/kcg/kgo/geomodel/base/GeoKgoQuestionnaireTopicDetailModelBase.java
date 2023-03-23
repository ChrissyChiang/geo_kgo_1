package gov.kcg.kgo.geomodel.base; 

import org.springframework.stereotype.Component; 
import io.swagger.annotations.ApiModel; 
import io.swagger.annotations.ApiModelProperty; 
import java.io.Serializable; 
import java.util.ArrayList; 
import java.util.List; 
import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireTopicDetail; 

/** 
 * GEO 20210829 add
 *
 * Model for 問卷題組子題目檔
 */

@Component
@ApiModel(description = "問卷題組子題目檔")
public class GeoKgoQuestionnaireTopicDetailModelBase implements Serializable {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(notes = "子題目id")
    private String detailId; 

    @ApiModelProperty(notes = "題組id")
    private String topicId; 

    @ApiModelProperty(notes = "子題目名稱")
    private String detailName;

    @ApiModelProperty(notes = "子題目類型(1-單選不含配分,2-單選含配分,3-複選,4-問答)")
    private Integer detailType; 

    @ApiModelProperty(notes = "子題目排序")
    private Integer detailSort;

    @ApiModelProperty(notes = "選項資料(ex.A-滿意,B-不滿意)")
    private String detailChoose;

    @ApiModelProperty(notes = "是否必填(GeoBooleanType)")
    private Integer isMust;


    public String getDetailId() { 
        return detailId; 
    } 

    public void setDetailId(String detailId) { 
        this.detailId = detailId; 
    } 

    public String getTopicId() { 
        return topicId; 
    } 

    public void setTopicId(String topicId) { 
        this.topicId = topicId; 
    } 

    public String getDetailName() { 
        return detailName; 
    } 

    public void setDetailName(String detailName) { 
        this.detailName = detailName; 
    } 

    public Integer getDetailType() { 
        return detailType; 
    } 

    public void setDetailType(Integer detailType) { 
        this.detailType = detailType; 
    } 

    public Integer getDetailSort() { 
        return detailSort; 
    } 

    public void setDetailSort(Integer detailSort) { 
        this.detailSort = detailSort; 
    }

    public String getDetailChoose() {
        return detailChoose;
    }

    public void setDetailChoose(String detailChoose) {
        this.detailChoose = detailChoose;
    }

    public Integer getIsMust() { 
        return isMust; 
    } 

    public void setIsMust(Integer isMust) { 
        this.isMust = isMust; 
    }



    public static GeoKgoQuestionnaireTopicDetailModelBase changeToModel(GeoKgoQuestionnaireTopicDetail entity) { 
        GeoKgoQuestionnaireTopicDetailModelBase model = new GeoKgoQuestionnaireTopicDetailModelBase(); 
        model.setDetailId(entity.getDetailId()); 
        model.setTopicId(entity.getTopicId()); 
        model.setDetailName(entity.getDetailName()); 
        model.setDetailType(entity.getDetailType()); 
        model.setDetailSort(entity.getDetailSort());
        model.setDetailChoose(entity.getDetailChoose());
        model.setIsMust(entity.getIsMust());
        return model; 
    } //changeToModel 

    public static List<GeoKgoQuestionnaireTopicDetailModelBase> changeListToModel(List<GeoKgoQuestionnaireTopicDetail> entityList) { 
        List<GeoKgoQuestionnaireTopicDetailModelBase> modelList = null; 
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoQuestionnaireTopicDetailModelBase model = changeToModel(entityList.get(i)); 
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
