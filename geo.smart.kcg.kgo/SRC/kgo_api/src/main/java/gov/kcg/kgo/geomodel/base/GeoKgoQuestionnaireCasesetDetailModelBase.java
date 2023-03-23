package gov.kcg.kgo.geomodel.base; 

import org.springframework.stereotype.Component; 
import io.swagger.annotations.ApiModel; 
import io.swagger.annotations.ApiModelProperty; 
import java.io.Serializable; 
import java.util.ArrayList; 
import java.util.List; 
import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireCasesetDetail; 

/** 
 * GEO 20210829 add
 *
 * Model for 服務問卷子題目檔
 */

@Component
@ApiModel(description = "服務問卷子題目檔")
public class GeoKgoQuestionnaireCasesetDetailModelBase implements Serializable {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(notes = "服務問卷子題目id")
    private String detailCasesetId; 

    @ApiModelProperty(notes = "服務問卷題組id")
    private String topicCasesetId; 

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


    public String getDetailCasesetId() { 
        return detailCasesetId; 
    } 

    public void setDetailCasesetId(String detailCasesetId) { 
        this.detailCasesetId = detailCasesetId; 
    } 

    public String getTopicCasesetId() { 
        return topicCasesetId; 
    } 

    public void setTopicCasesetId(String topicCasesetId) { 
        this.topicCasesetId = topicCasesetId; 
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



    public static GeoKgoQuestionnaireCasesetDetailModelBase changeToModel(GeoKgoQuestionnaireCasesetDetail entity) { 
        GeoKgoQuestionnaireCasesetDetailModelBase model = new GeoKgoQuestionnaireCasesetDetailModelBase(); 
        model.setDetailCasesetId(entity.getDetailCasesetId()); 
        model.setTopicCasesetId(entity.getTopicCasesetId()); 
        model.setDetailName(entity.getDetailName()); 
        model.setDetailType(entity.getDetailType()); 
        model.setDetailSort(entity.getDetailSort());
        model.setDetailChoose(entity.getDetailChoose());
        model.setIsMust(entity.getIsMust()); 
        return model; 
    } //changeToModel 

    public static List<GeoKgoQuestionnaireCasesetDetailModelBase> changeListToModel(List<GeoKgoQuestionnaireCasesetDetail> entityList) { 
        List<GeoKgoQuestionnaireCasesetDetailModelBase> modelList = null; 
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoQuestionnaireCasesetDetailModelBase model = changeToModel(entityList.get(i)); 
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
