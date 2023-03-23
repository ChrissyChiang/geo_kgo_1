package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireTopicDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** 
 * GEO 20210829 add
 *
 * Model for 問卷題組子題目檔
 */

@Component
@ApiModel(description = "問卷題組子題目檔")
public class GeoKgoQuestionnaireTopicDetailModel implements Serializable {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(notes = "子題目id(新增不填, 編輯時必填)")
    private String detailId;

    @ApiModelProperty(notes = "子題目名稱", required = true)
    private String detailName; 

    @ApiModelProperty(notes = "子題目類型(1-單選不含配分,2-單選含配分,3-複選,4-問答)", required = true)
    private Integer detailType; 

    @ApiModelProperty(notes = "子題目排序", required = true)
    private Integer detailSort;

    @ApiModelProperty(notes = "選項資料(ex.A-滿意,B-不滿意)")
    private String detailChoose;

    @ApiModelProperty(notes = "是否為必填題(本欄位必填; 1表必填題, 0表非必填題)", required = true)
    private Integer isMust;

    @ApiModelProperty(notes = "是否有效(1表有效, 0表刪除)")
    private Integer isEnable;


    public String getDetailId() { 
        return detailId; 
    } 

    public void setDetailId(String detailId) { 
        this.detailId = detailId; 
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

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }


    public static GeoKgoQuestionnaireTopicDetailModel changeToModel(GeoKgoQuestionnaireTopicDetail entity) {
        GeoKgoQuestionnaireTopicDetailModel model = new GeoKgoQuestionnaireTopicDetailModel();
        model.setDetailId(entity.getDetailId());
        model.setDetailName(entity.getDetailName()); 
        model.setDetailType(entity.getDetailType()); 
        model.setDetailSort(entity.getDetailSort());
        model.setDetailChoose(entity.getDetailChoose());
        model.setIsMust(entity.getIsMust());
        return model; 
    } //changeToModel 

    public static List<GeoKgoQuestionnaireTopicDetailModel> changeListToModel(List<GeoKgoQuestionnaireTopicDetail> entityList) {
        List<GeoKgoQuestionnaireTopicDetailModel> modelList = null;
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoQuestionnaireTopicDetailModel model = changeToModel(entityList.get(i));
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
