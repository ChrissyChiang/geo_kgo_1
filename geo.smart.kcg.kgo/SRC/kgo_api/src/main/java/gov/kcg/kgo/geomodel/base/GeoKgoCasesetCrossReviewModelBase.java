package gov.kcg.kgo.geomodel.base; 

import org.springframework.stereotype.Component; 
import io.swagger.annotations.ApiModel; 
import io.swagger.annotations.ApiModelProperty; 
import java.io.Serializable; 
import java.util.ArrayList; 
import java.util.List; 
import gov.kcg.kgo.geoentity.GeoKgoCasesetCrossReview; 

/** 
 * GEO 20211115 add 跨機關檢視
 * Model for 跨機關檢視權限 機關服務對應
 */
@Component
@ApiModel(description = "跨機關檢視權限 機關服務對應")
public class GeoKgoCasesetCrossReviewModelBase implements Serializable {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(notes = "服務id")
    private String CaseSetId; 

    @ApiModelProperty(notes = "機關id")
    private String OrganId; 


    public String getCaseSetId() { 
        return CaseSetId; 
    } 

    public void setCaseSetId(String CaseSetId) { 
        this.CaseSetId = CaseSetId; 
    } 

    public String getOrganId() { 
        return OrganId; 
    } 

    public void setOrganId(String OrganId) { 
        this.OrganId = OrganId; 
    } 



    public static GeoKgoCasesetCrossReviewModelBase changeToModel(GeoKgoCasesetCrossReview entity) { 
        GeoKgoCasesetCrossReviewModelBase model = new GeoKgoCasesetCrossReviewModelBase(); 
        model.setCaseSetId(entity.getId().getCaseSetId());
        model.setOrganId(entity.getId().getOrganId());
        return model; 
    } //changeToModel 

    public static List<GeoKgoCasesetCrossReviewModelBase> changeListToModel(List<GeoKgoCasesetCrossReview> entityList) { 
        List<GeoKgoCasesetCrossReviewModelBase> modelList = null; 
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoCasesetCrossReviewModelBase model = changeToModel(entityList.get(i)); 
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
