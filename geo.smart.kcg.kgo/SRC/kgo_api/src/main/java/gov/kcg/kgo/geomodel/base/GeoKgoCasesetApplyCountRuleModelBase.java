package gov.kcg.kgo.geomodel.base; 

import org.springframework.stereotype.Component; 
import io.swagger.annotations.ApiModel; 
import io.swagger.annotations.ApiModelProperty; 
import java.io.Serializable; 
import java.util.ArrayList; 
import java.util.List; 
import gov.kcg.kgo.geoentity.GeoKgoCasesetApplyCountRule; 

/** 
 * GEO 20211123 add 服務申辦統計名次排序條件
 * Model for 服務申辦統計名次排序條件
 */
@Component
@ApiModel(description = "服務申辦統計名次排序條件")
public class GeoKgoCasesetApplyCountRuleModelBase implements Serializable {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(notes = "條件id")
    private Integer ruleId;

    @ApiModelProperty(notes = "搜尋區間")
    private Integer searchRangeType; 

    @ApiModelProperty(notes = "搜尋前幾名，預設10")
    private Integer searchRank; 

    @ApiModelProperty(notes = "起始日 yyyy-MM-dd，搜尋區間=4：必填")
    private String dateStart; 

    @ApiModelProperty(notes = "結束日 yyyy-MM-dd，搜尋區間=4：必填")
    private String dateEnd; 

    @ApiModelProperty(notes = "案件啟用狀態 (開啟-On關閉-Off)，不填時包含所有狀態")
    private String caseSetStatus; 


    public Integer getRuleId() {
        return ruleId; 
    } 

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId; 
    } 

    public Integer getSearchRangeType() { 
        return searchRangeType; 
    } 

    public void setSearchRangeType(Integer searchRangeType) { 
        this.searchRangeType = searchRangeType; 
    } 

    public Integer getSearchRank() { 
        return searchRank; 
    } 

    public void setSearchRank(Integer searchRank) { 
        this.searchRank = searchRank; 
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

    public String getCaseSetStatus() { 
        return caseSetStatus; 
    } 

    public void setCaseSetStatus(String caseSetStatus) { 
        this.caseSetStatus = caseSetStatus; 
    } 



    public static GeoKgoCasesetApplyCountRuleModelBase changeToModel(GeoKgoCasesetApplyCountRule entity) { 
        GeoKgoCasesetApplyCountRuleModelBase model = new GeoKgoCasesetApplyCountRuleModelBase(); 
        model.setRuleId(entity.getRuleId()); 
        model.setSearchRangeType(entity.getSearchRangeType()); 
        model.setSearchRank(entity.getSearchRank()); 
        model.setDateStart(entity.getDateStart()); 
        model.setDateEnd(entity.getDateEnd()); 
        model.setCaseSetStatus(entity.getCaseSetStatus()); 
        return model; 
    } //changeToModel 

    public static List<GeoKgoCasesetApplyCountRuleModelBase> changeListToModel(List<GeoKgoCasesetApplyCountRule> entityList) { 
        List<GeoKgoCasesetApplyCountRuleModelBase> modelList = null; 
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoCasesetApplyCountRuleModelBase model = changeToModel(entityList.get(i)); 
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
