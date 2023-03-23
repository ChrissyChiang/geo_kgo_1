package gov.kcg.kgo.geomodel.base; 

import org.springframework.stereotype.Component; 
import io.swagger.annotations.ApiModel; 
import io.swagger.annotations.ApiModelProperty; 
import java.io.Serializable; 
import java.util.ArrayList; 
import java.util.List; 
import java.sql.Timestamp; 
import gov.kcg.kgo.geoentity.GeoKgoTaskComment; 

/** 
 * GEO 20211101 add
 * Model for 簽核意見
 */
@Component
@ApiModel(description = "簽核意見")
public class GeoKgoTaskCommentModelBase implements Serializable {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(notes = "簽核意見id(ACT_HI_COMMENT:ID_)")
    private String commentId; 

    @ApiModelProperty(notes = "簽核意見")
    private String commentText; 

    @ApiModelProperty(notes = "編輯單位")
    private String editOrgan; 

    @ApiModelProperty(notes = "編輯人員")
    private String editUser; 

    @ApiModelProperty(notes = "編輯時間")
    private Timestamp editTime; 


    public String getCommentId() { 
        return commentId; 
    } 

    public void setCommentId(String commentId) { 
        this.commentId = commentId; 
    } 

    public String getCommentText() { 
        return commentText; 
    } 

    public void setCommentText(String commentText) { 
        this.commentText = commentText; 
    } 

    public String getEditOrgan() { 
        return editOrgan; 
    } 

    public void setEditOrgan(String editOrgan) { 
        this.editOrgan = editOrgan; 
    } 

    public String getEditUser() { 
        return editUser; 
    } 

    public void setEditUser(String editUser) { 
        this.editUser = editUser; 
    } 

    public Timestamp getEditTime() { 
        return editTime; 
    } 

    public void setEditTime(Timestamp editTime) { 
        this.editTime = editTime; 
    } 



    public static GeoKgoTaskCommentModelBase changeToModel(GeoKgoTaskComment entity) { 
        GeoKgoTaskCommentModelBase model = new GeoKgoTaskCommentModelBase(); 
        model.setCommentId(entity.getCommentId()); 
        model.setCommentText(entity.getCommentText()); 
        model.setEditOrgan(entity.getEditOrgan()); 
        model.setEditUser(entity.getEditUser()); 
        model.setEditTime(entity.getEditTime()); 
        return model; 
    } //changeToModel 

    public static List<GeoKgoTaskCommentModelBase> changeListToModel(List<GeoKgoTaskComment> entityList) { 
        List<GeoKgoTaskCommentModelBase> modelList = null; 
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoTaskCommentModelBase model = changeToModel(entityList.get(i)); 
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
