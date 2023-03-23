package gov.kcg.kgo.geoentity; 

import javax.persistence.*; 
import org.hibernate.annotations.DynamicInsert; 
import java.io.Serializable; 
import java.sql.Timestamp; 

/** 
 * GEO 20211101 add
 * 簽核意見
 */
@Entity
@DynamicInsert
@Table(name = "GEO_KGO_TASK_COMMENT", schema = "dbo")
public class GeoKgoTaskComment implements Serializable {

    private static final long serialVersionUID = 1L; 

    private String commentId; //簽核意見id(ACT_HI_COMMENT:ID_) 
    private String commentText; //簽核意見 
    private String editOrgan; //編輯單位 
    private String editUser; //編輯人員 
    private Timestamp editTime; //編輯時間 

    @Id 
    @Column(name = "comment_id", columnDefinition = "nvarchar(64) NOT NULL") 
    public String getCommentId() { 
        return commentId; 
    } 

    public void setCommentId(String commentId) { 
        this.commentId = commentId; 
    } 

    @Basic 
    @Column(name = "comment_text", columnDefinition = "nvarchar(500)") 
    public String getCommentText() { 
        return commentText; 
    } 

    public void setCommentText(String commentText) { 
        this.commentText = commentText; 
    } 

    @Basic 
    @Column(name = "edit_organ", columnDefinition = "varchar(50) NULL") 
    public String getEditOrgan() { 
        return editOrgan; 
    } 

    public void setEditOrgan(String editOrgan) { 
        this.editOrgan = editOrgan; 
    } 

    @Basic 
    @Column(name = "edit_user", columnDefinition = "varchar(50) NULL") 
    public String getEditUser() { 
        return editUser; 
    } 

    public void setEditUser(String editUser) { 
        this.editUser = editUser; 
    } 

    @Basic 
    @Column(name = "edit_time", columnDefinition = "datetime2(0) NULL") 
    public Timestamp getEditTime() { 
        return editTime; 
    } 

    public void setEditTime(Timestamp editTime) { 
        this.editTime = editTime; 
    } 

} 
