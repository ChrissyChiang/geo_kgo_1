package gov.kcg.kgo.geoentity; 

import javax.persistence.*; 
import org.hibernate.annotations.DynamicInsert; 
import java.io.Serializable; 

/** 
 * GEO 20211224 add 線上預約表單
 * 預約表單子欄位
 */
@Entity
@DynamicInsert
@Table(name = "GEO_KGO_APPOINTMENT_COLUMN_CHILD", schema = "dbo") 
public class GeoKgoAppointmentColumnChild implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private GeoKgoAppointmentColumnChildPK id;
    private String ColumnType; //欄位種類 
    private String ColumnValue; //欄位值 
    private Integer Length; //欄位長度 
    private String Memo; //欄位備註 
    private String PColumnId; //父欄位 
    private String FText; //前文字 
    private String BText; //後文字 
    private String VGroup; //群組編號
    private Integer Row; //所在行數 
    private Integer IsCheckFrequency; //是否重複檢核0-否，1-是 
    private Integer IsFieldCheck; // 欄位勾選0-否，1-是
    private Boolean isMustKey; //是否必填

    public GeoKgoAppointmentColumnChildPK getId() {
        return id;
    }

    public void setId(GeoKgoAppointmentColumnChildPK id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ColumnType", columnDefinition = "varchar(30) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL") 
    public String getColumnType() { 
        return ColumnType; 
    } 

    public void setColumnType(String ColumnType) { 
        this.ColumnType = ColumnType; 
    } 

    @Basic 
    @Column(name = "ColumnValue", columnDefinition = "nvarchar(4000) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL") 
    public String getColumnValue() { 
        return ColumnValue; 
    } 

    public void setColumnValue(String ColumnValue) { 
        this.ColumnValue = ColumnValue; 
    } 

    @Basic 
    @Column(name = "Length", columnDefinition = "int  NULL") 
    public Integer getLength() { 
        return Length; 
    } 

    public void setLength(Integer Length) { 
        this.Length = Length; 
    } 

    @Basic 
    @Column(name = "Memo", columnDefinition = "nvarchar(500) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL") 
    public String getMemo() { 
        return Memo; 
    } 

    public void setMemo(String Memo) { 
        this.Memo = Memo; 
    } 

    @Basic 
    @Column(name = "PColumnId", columnDefinition = "varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL") 
    public String getPColumnId() { 
        return PColumnId; 
    } 

    public void setPColumnId(String PColumnId) { 
        this.PColumnId = PColumnId; 
    } 

    @Basic 
    @Column(name = "FText", columnDefinition = "nvarchar(max) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL") 
    public String getFText() { 
        return FText; 
    } 

    public void setFText(String FText) { 
        this.FText = FText; 
    } 

    @Basic 
    @Column(name = "BText", columnDefinition = "nvarchar(max) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL") 
    public String getBText() { 
        return BText; 
    } 

    public void setBText(String BText) { 
        this.BText = BText; 
    } 

    @Basic 
    @Column(name = "VGroup", columnDefinition = "varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL") 
    public String getVGroup() { 
        return VGroup; 
    } 

    public void setVGroup(String VGroup) { 
        this.VGroup = VGroup; 
    }

    @Basic 
    @Column(name = "Row", columnDefinition = "int  NULL") 
    public Integer getRow() { 
        return Row; 
    } 

    public void setRow(Integer Row) { 
        this.Row = Row; 
    } 

    @Basic 
    @Column(name = "IsCheckFrequency", columnDefinition = "int NULL") 
    public Integer getIsCheckFrequency() { 
        return IsCheckFrequency; 
    } 

    public void setIsCheckFrequency(Integer IsCheckFrequency) { 
        this.IsCheckFrequency = IsCheckFrequency; 
    } 

    @Basic 
    @Column(name = "IsFieldCheck", columnDefinition = "int NULL") 
    public Integer getIsFieldCheck() { 
        return IsFieldCheck; 
    } 

    public void setIsFieldCheck(Integer IsFieldCheck) { 
        this.IsFieldCheck = IsFieldCheck; 
    }

    @Basic
    @Column(name = "IsMustKey", columnDefinition = "NULL")
    public Boolean getMustKey() {
        return isMustKey;
    }

    public void setMustKey(Boolean mustKey) {
        isMustKey = mustKey;
    }



}
