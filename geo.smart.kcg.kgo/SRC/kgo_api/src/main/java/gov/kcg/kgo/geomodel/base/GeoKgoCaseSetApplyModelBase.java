package gov.kcg.kgo.geomodel.base; 

import org.springframework.stereotype.Component; 
import io.swagger.annotations.ApiModel; 
import io.swagger.annotations.ApiModelProperty; 
import java.io.Serializable; 
import java.util.ArrayList; 
import java.util.List; 
import gov.kcg.kgo.geoentity.GeoKgoCaseSetApplyCount;

/** 
 * GEO 20210829 add
 *
 * Model for 服務申辦統計
 */

@Component
@ApiModel(description = "服務申辦統計")
public class GeoKgoCaseSetApplyModelBase implements Serializable {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(notes = "名次")
    private Integer casesetRank; 

    @ApiModelProperty(notes = "服務id")
    private String casesetId; 


    public Integer getCasesetRank() { 
        return casesetRank; 
    } 

    public void setCasesetRank(Integer casesetRank) { 
        this.casesetRank = casesetRank; 
    } 

    public String getCasesetId() { 
        return casesetId; 
    } 

    public void setCasesetId(String casesetId) { 
        this.casesetId = casesetId; 
    } 



    public static GeoKgoCaseSetApplyModelBase changeToModel(GeoKgoCaseSetApplyCount entity) {
        GeoKgoCaseSetApplyModelBase model = new GeoKgoCaseSetApplyModelBase(); 
        model.setCasesetRank(entity.getCasesetRank()); 
        model.setCasesetId(entity.getCasesetId()); 
        return model; 
    } //changeToModel 

    public static List<GeoKgoCaseSetApplyModelBase> changeListToModel(List<GeoKgoCaseSetApplyCount> entityList) {
        List<GeoKgoCaseSetApplyModelBase> modelList = null; 
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoCaseSetApplyModelBase model = changeToModel(entityList.get(i)); 
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
