package gov.kcg.kgo.geomodel.base; 

import org.springframework.stereotype.Component; 
import io.swagger.annotations.ApiModel; 
import io.swagger.annotations.ApiModelProperty; 
import java.io.Serializable; 
import java.util.ArrayList; 
import java.util.List; 
import gov.kcg.kgo.geoentity.GeoKgoCaseCrossReviewComment; 

/** 
 * GEO 20211115 add 跨機關檢視 備註
 * Model for 跨機關檢視 備註
 */
@Component
@ApiModel(description = "跨機關檢視 備註")
public class GeoKgoCaseCrossReviewCommentModelBase implements Serializable {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(notes = "備註id")
    private String crossReviewCommentId;

    @ApiModelProperty(notes = "服務id")
    private String caseId;

    @ApiModelProperty(notes = "機關id")
    private String organId;

    @ApiModelProperty(notes = "科室id")
    private String unitId;

    @ApiModelProperty(notes = "填寫人員id")
    private String userId;

    @ApiModelProperty(notes = "備註內容")
    private String comment;


    public String getCrossReviewCommentId() { 
        return crossReviewCommentId;
    } 

    public void setCrossReviewCommentId(String CrossReviewCommentId) { 
        this.crossReviewCommentId = CrossReviewCommentId;
    } 

    public String getCaseId() { 
        return caseId;
    } 

    public void setCaseId(String CaseId) { 
        this.caseId = CaseId;
    } 

    public String getOrganId() { 
        return organId;
    } 

    public void setOrganId(String OrganId) { 
        this.organId = OrganId;
    } 

    public String getUnitId() { 
        return unitId;
    } 

    public void setUnitId(String UnitId) { 
        this.unitId = UnitId;
    } 

    public String getUserId() { 
        return userId;
    } 

    public void setUserId(String UserId) { 
        this.userId = UserId;
    } 

    public String getComment() { 
        return comment;
    } 

    public void setComment(String Comment) { 
        this.comment = Comment;
    } 



    public static GeoKgoCaseCrossReviewCommentModelBase changeToModel(GeoKgoCaseCrossReviewComment entity) { 
        GeoKgoCaseCrossReviewCommentModelBase model = new GeoKgoCaseCrossReviewCommentModelBase(); 
        model.setCrossReviewCommentId(entity.getCrossReviewCommentId()); 
        model.setCaseId(entity.getCaseId()); 
        model.setOrganId(entity.getOrganId()); 
        model.setUnitId(entity.getUnitId()); 
        model.setUserId(entity.getUserId()); 
        model.setComment(entity.getComment()); 
        return model; 
    } //changeToModel 

    public static List<GeoKgoCaseCrossReviewCommentModelBase> changeListToModel(List<GeoKgoCaseCrossReviewComment> entityList) { 
        List<GeoKgoCaseCrossReviewCommentModelBase> modelList = null; 
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoCaseCrossReviewCommentModelBase model = changeToModel(entityList.get(i)); 
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
