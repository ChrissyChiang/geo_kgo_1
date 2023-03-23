package gov.kcg.kgo.geoentity; 

import javax.persistence.*; 
import org.hibernate.annotations.DynamicInsert; 
import java.io.Serializable; 

/** 
 * GEO 20211224 add 線上預約表單
 * 預約表單 預約資料檔
 */
@Entity
@DynamicInsert
@Table(name = "GEO_KGO_APPOINTMENT_FORM_DETAIL", schema = "dbo") 
public class GeoKgoAppointmentFormDetail implements Serializable {

    private static final long serialVersionUID = 1L; 

    @EmbeddedId
    private GeoKgoAppointmentFormDetailPK id;
    private String columnValue; //欄位設定值
    private String correctMemo; //補正說明
    private String correctBValue; //補正值
    private String commentId; //簽核意見id 

    public GeoKgoAppointmentFormDetailPK getId() {
        return id;
    }

    public void setId(GeoKgoAppointmentFormDetailPK id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ColumnValue", columnDefinition = "nvarchar(max) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL") 
    public String getColumnValue() { 
        return columnValue;
    } 

    public void setColumnValue(String ColumnValue) { 
        this.columnValue = ColumnValue;
    } 

    @Basic 
    @Column(name = "CorrectMemo", columnDefinition = "nvarchar(200) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL") 
    public String getCorrectMemo() { 
        return correctMemo;
    } 

    public void setCorrectMemo(String CorrectMemo) { 
        this.correctMemo = CorrectMemo;
    } 

    @Basic 
    @Column(name = "CorrectBValue", columnDefinition = "nvarchar(max) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL") 
    public String getCorrectBValue() { 
        return correctBValue;
    } 

    public void setCorrectBValue(String CorrectBValue) { 
        this.correctBValue = CorrectBValue;
    } 

    @Basic 
    @Column(name = "CommentId", columnDefinition = "nvarchar(64)")
    public String getCommentId() { 
        return commentId; 
    } 

    public void setCommentId(String commentId) { 
        this.commentId = commentId; 
    }

} 
