package gov.kcg.kgo.geoentity; 

import javax.persistence.*; 
import org.hibernate.annotations.DynamicInsert; 
import java.io.Serializable; 

/** 
 * GEO 20211115 add 跨機關檢視 備註
 * 跨機關檢視 備註
 */
@Entity
@DynamicInsert
@Table(name = "GEO_KGO_CASE_CROSS_REVIEW_COMMENT", schema = "dbo")
public class GeoKgoCaseCrossReviewComment implements Serializable {

    private static final long serialVersionUID = 1L; 

    private String crossReviewCommentId; //備註id
    private String caseId; //服務id
    private String organId; //機關id
    private String unitId; //科室id
    private String userId; //填寫人員id
    private String comment; //備註內容

    @Id 
    @Column(name = "CrossReviewCommentId", columnDefinition = "varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS NOT NULL") 
    public String getCrossReviewCommentId() { 
        return crossReviewCommentId;
    } 

    public void setCrossReviewCommentId(String CrossReviewCommentId) { 
        this.crossReviewCommentId = CrossReviewCommentId;
    } 

    @Basic 
    @Column(name = "CaseId", columnDefinition = "varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL") 
    public String getCaseId() { 
        return caseId;
    } 

    public void setCaseId(String CaseId) { 
        this.caseId = CaseId;
    } 

    @Basic 
    @Column(name = "OrganId", columnDefinition = "varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL") 
    public String getOrganId() { 
        return organId;
    } 

    public void setOrganId(String OrganId) { 
        this.organId = OrganId;
    } 

    @Basic 
    @Column(name = "UnitId", columnDefinition = "varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL") 
    public String getUnitId() { 
        return unitId;
    } 

    public void setUnitId(String UnitId) { 
        this.unitId = UnitId;
    } 

    @Basic 
    @Column(name = "UserId", columnDefinition = "varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL") 
    public String getUserId() { 
        return userId;
    } 

    public void setUserId(String UserId) { 
        this.userId = UserId;
    } 

    @Basic 
    @Column(name = "Comment", columnDefinition = "nvarchar(500) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL") 
    public String getComment() { 
        return comment;
    } 

    public void setComment(String Comment) { 
        this.comment = Comment;
    } 

} 
