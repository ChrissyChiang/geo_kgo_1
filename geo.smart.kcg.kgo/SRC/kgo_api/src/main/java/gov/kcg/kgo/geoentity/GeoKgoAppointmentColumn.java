package gov.kcg.kgo.geoentity; 

import javax.persistence.*; 
import org.hibernate.annotations.DynamicInsert; 
import java.io.Serializable; 

/** 
 * GEO 20211224 add 線上預約表單
 * 預約表單 主欄位
 */
@Entity
@DynamicInsert
@Table(name = "GEO_KGO_APPOINTMENT_COLUMN", schema = "dbo") 
public class GeoKgoAppointmentColumn implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private GeoKgoAppointmentColumnPK id;
    private Integer groupSeq; //群組序號
    private String columnName; //欄位名稱
    private String columnType; //欄位型態
    private String columnValue; //欄位設定值
    private Integer orderNum; //顯示順序
    private Integer length; //欄位長度
    private String memo; //欄位備註
    private String fileType; //附件上傳檔案限制副檔名
    private Integer isCheckFrequency; //是否重複檢核0-否，1-是
    private Integer isFieldCheck; // 欄位勾選0-否，1-是
    private Boolean isMustKey; //是否必填

    public GeoKgoAppointmentColumnPK getId() {
        return id;
    }

    public void setId(GeoKgoAppointmentColumnPK id) {
        this.id = id;
    }

    @Basic
    @Column(name = "GroupSeq", columnDefinition = "int  NOT NULL") 
    public Integer getGroupSeq() { 
        return groupSeq;
    } 

    public void setGroupSeq(Integer GroupSeq) { 
        this.groupSeq = GroupSeq;
    } 

    @Basic 
    @Column(name = "ColumnName", columnDefinition = "nvarchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL") 
    public String getColumnName() { 
        return columnName;
    } 

    public void setColumnName(String ColumnName) { 
        this.columnName = ColumnName;
    } 

    @Basic 
    @Column(name = "ColumnType", columnDefinition = "varchar(30) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL") 
    public String getColumnType() { 
        return columnType;
    } 

    public void setColumnType(String ColumnType) { 
        this.columnType = ColumnType;
    } 

    @Basic 
    @Column(name = "ColumnValue", columnDefinition = "nvarchar(4000) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL") 
    public String getColumnValue() { 
        return columnValue;
    } 

    public void setColumnValue(String ColumnValue) { 
        this.columnValue = ColumnValue;
    } 

    @Basic 
    @Column(name = "OrderNum", columnDefinition = "int  NULL") 
    public Integer getOrderNum() { 
        return orderNum;
    } 

    public void setOrderNum(Integer OrderNum) { 
        this.orderNum = OrderNum;
    } 

    @Basic 
    @Column(name = "Length", columnDefinition = "int  NULL") 
    public Integer getLength() { 
        return length;
    } 

    public void setLength(Integer Length) { 
        this.length = Length;
    } 

    @Basic 
    @Column(name = "Memo", columnDefinition = "nvarchar(500) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL") 
    public String getMemo() { 
        return memo;
    } 

    public void setMemo(String Memo) { 
        this.memo = Memo;
    } 

    @Basic 
    @Column(name = "FileType", columnDefinition = "varchar(200) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL") 
    public String getFileType() { 
        return fileType;
    } 

    public void setFileType(String FileType) { 
        this.fileType = FileType;
    } 

    @Basic 
    @Column(name = "IsCheckFrequency", columnDefinition = "int NULL") 
    public Integer getIsCheckFrequency() { 
        return isCheckFrequency;
    } 

    public void setIsCheckFrequency(Integer IsCheckFrequency) { 
        this.isCheckFrequency = IsCheckFrequency;
    } 

    @Basic 
    @Column(name = "IsFieldCheck", columnDefinition = "int NULL") 
    public Integer getIsFieldCheck() { 
        return isFieldCheck;
    }

    public void setIsFieldCheck(Integer IsFieldCheck) {
        this.isFieldCheck = IsFieldCheck;
    }

    @Basic
    @Column(name = "IsMustKey", columnDefinition = " NULL")
    public Boolean getMustKey() {
        return isMustKey;
    }

    public void setMustKey(Boolean mustKey) {
        isMustKey = mustKey;
    }
}
