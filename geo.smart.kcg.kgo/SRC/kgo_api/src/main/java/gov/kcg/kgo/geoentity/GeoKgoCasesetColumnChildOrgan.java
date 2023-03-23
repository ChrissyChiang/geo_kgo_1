package gov.kcg.kgo.geoentity;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import java.io.Serializable;

/**
 * GEO 20211109 add 機關審核表單
 * 機關審核表單 服務設定子欄位
 */
@Entity
@DynamicInsert
@Table(name = "GEO_KGO_CASESET_COLUMN_CHILD_ORGAN", schema = "dbo")
public class GeoKgoCasesetColumnChildOrgan implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private  GeoKgoCasesetColumnChildOrganPK id;
    private String columnType; //欄位種類
    private String columnValue; //欄位值
    private Integer length; //欄位長度
    private String memo; //欄位備註
    private String PColumnId; //父欄位 
    private String FText; //前文字 
    private String BText; //後文字 
    private String VGroup; //群組編號
    private Integer orderNum; //顯示順序
    private Integer row; //所在行數
    private Integer isCheckFrequency; //是否重複檢核0-否，1-是
    private Integer isFieldCheck; // 欄位勾選0-否，1-是
    private Boolean isMustKey;

    public GeoKgoCasesetColumnChildOrganPK getId() {
        return id;
    }

    public void setId(GeoKgoCasesetColumnChildOrganPK id) {
        this.id = id;
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

    @Id
    @Column(name = "OrderNum", columnDefinition = "int  NOT NULL")
    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer OrderNum) {
        this.orderNum = OrderNum;
    }

    @Basic
    @Column(name = "Row", columnDefinition = "int  NULL")
    public Integer getRow() {
        return row;
    }

    public void setRow(Integer Row) {
        this.row = Row;
    }

    @Basic
    @Column(name = "IsCheckFrequency", columnDefinition = "int  NULL")
    public Integer getIsCheckFrequency() {
        return isCheckFrequency;
    }

    public void setIsCheckFrequency(Integer IsCheckFrequency) {
        this.isCheckFrequency = IsCheckFrequency;
    }

    @Basic
    @Column(name = "IsFieldCheck", columnDefinition = "int  NULL")
    public Integer getIsFieldCheck() {
        return isFieldCheck;
    }

    public void setIsFieldCheck(Integer IsFieldCheck) {
        this.isFieldCheck = IsFieldCheck;
    }

    @Basic
    @Column(name = "IsMustKey")
    public Boolean getIsMustKey() {
        return isMustKey;
    }

    public void setIsMustKey(Boolean mustKey) {
        isMustKey = mustKey;
    }

}
