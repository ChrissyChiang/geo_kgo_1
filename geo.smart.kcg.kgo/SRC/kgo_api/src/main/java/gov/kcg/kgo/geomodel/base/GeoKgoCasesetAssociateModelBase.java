package gov.kcg.kgo.geomodel.base; 

import gov.kcg.kgo.geoentity.GeoKgoCasesetAssociatePK;
import org.springframework.stereotype.Component;
import io.swagger.annotations.ApiModel; 
import io.swagger.annotations.ApiModelProperty; 
import java.io.Serializable; 
import java.util.ArrayList; 
import java.util.List; 
import gov.kcg.kgo.geoentity.GeoKgoCasesetAssociate; 

/** 
 * GEO 20211019 add
 * Model for 案件關聯服務
 */
@Component
@ApiModel(description = "案件關聯服務")
public class GeoKgoCasesetAssociateModelBase implements Serializable {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(notes = "服務id")
    private String casesetId; 

    @ApiModelProperty(notes = "關聯服務id")
    private String associateCasesetId; 


    public String getCasesetId() { 
        return casesetId; 
    } 

    public void setCasesetId(String casesetId) { 
        this.casesetId = casesetId; 
    } 

    public String getAssociateCasesetId() { 
        return associateCasesetId; 
    } 

    public void setAssociateCasesetId(String associateCasesetId) { 
        this.associateCasesetId = associateCasesetId; 
    } 



    public static GeoKgoCasesetAssociateModelBase changeToModel(GeoKgoCasesetAssociate entity) { 
        GeoKgoCasesetAssociateModelBase model = new GeoKgoCasesetAssociateModelBase();
        GeoKgoCasesetAssociatePK pk = new GeoKgoCasesetAssociatePK(model.getCasesetId(),model.getAssociateCasesetId());
        return model; 
    } //changeToModel 

    public static List<GeoKgoCasesetAssociateModelBase> changeListToModel(List<GeoKgoCasesetAssociate> entityList) { 
        List<GeoKgoCasesetAssociateModelBase> modelList = null; 
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoCasesetAssociateModelBase model = changeToModel(entityList.get(i)); 
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
