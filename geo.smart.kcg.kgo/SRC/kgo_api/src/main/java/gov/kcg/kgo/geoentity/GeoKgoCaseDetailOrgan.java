package gov.kcg.kgo.geoentity;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import java.io.Serializable;

/**
 * GEO 20211109 add
 * 機關審核表單 案件資料檔
 */
@Entity
@DynamicInsert
@Table(name = "GEO_KGO_CASE_DETAIL_ORGAN", schema = "dbo")
public class GeoKgoCaseDetailOrgan implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private GeoKgoCaseDetailOrganPK id;
    private String columnValue; //欄位設定值
    private String correctMemo; //補正說明
    private String correctBValue; //補正值
    private Boolean isCorrect;
    private Boolean isSource;

    public GeoKgoCaseDetailOrganPK getId() {
        return id;
    }

    public void setId(GeoKgoCaseDetailOrganPK id) {
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

    @Column(name = "IsCorrect")
    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }

    @Column(name = "IsSource")
    public Boolean getSource() {
        return isSource;
    }

    public void setSource(Boolean source) {
        isSource = source;
    }

} 
