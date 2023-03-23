package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.geoentity.GeoKgoCasesetAssociate;
import gov.kcg.kgo.geoentity.GeoKgoCasesetAssociatePK;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** 
 * GEO 20211019 add
 * Model for 案件關聯服務
 */
@Component
@ApiModel(description = "案件關聯服務")
public class GeoKgoCasesetAssociateModel implements Serializable {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(notes = "服務id",required = true)
    private String casesetId; 

    @ApiModelProperty(notes = "關聯服務id",required = true)
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



    public static GeoKgoCasesetAssociateModel changeToModel(GeoKgoCasesetAssociate entity) {
        GeoKgoCasesetAssociateModel model = new GeoKgoCasesetAssociateModel();
        GeoKgoCasesetAssociatePK pk = new GeoKgoCasesetAssociatePK(model.getCasesetId(),model.getAssociateCasesetId());
        entity.setId(pk);
        return model; 
    } //changeToModel 

    public static List<GeoKgoCasesetAssociateModel> changeListToModel(List<GeoKgoCasesetAssociate> entityList) {
        List<GeoKgoCasesetAssociateModel> modelList = null;
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoCasesetAssociateModel model = changeToModel(entityList.get(i));
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
