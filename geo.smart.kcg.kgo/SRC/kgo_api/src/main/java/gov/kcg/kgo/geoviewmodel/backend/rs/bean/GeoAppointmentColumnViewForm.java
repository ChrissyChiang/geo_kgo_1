package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rq.CasesetComplexColumnData;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 *
 */
public class GeoAppointmentColumnViewForm  extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "案件種類ID")
    private String appointmentId = "";

    @ApiModelProperty(notes = "群組序號")
    private Integer groupSeq;

    @ApiModelProperty(notes = "版本號")
    private Integer version;

    @ApiModelProperty(notes = "對應欄位")
    private String columnId;

    @ApiModelProperty(notes = "欄位名稱")
    private String columnName;

    @ApiModelProperty(notes = "欄位型態")
    private String columnType;

    @ApiModelProperty(notes = "欄位型態名稱")
    private String columnTypeName;

    @ApiModelProperty(notes = "欄位長度")
    private Integer length;

    @ApiModelProperty(notes = "必填")
    private Boolean isMustKey;

    @ApiModelProperty(notes = "必填名稱")
    private String isMustKeyStr;

    @ApiModelProperty(notes = "顯示順序")
    private Integer orderNum;

    @ApiModelProperty(notes = "欄位設定值")
    private String columnValue;

    @ApiModelProperty(value = "欄位備註")
    private String memo;

    @ApiModelProperty(notes = "附件類型")
    private String fileType;

    @ApiModelProperty(value = "複合欄位資料集合")
    private List<List<CasesetComplexColumnData>> complex;

    @ApiModelProperty(notes = "是否重複檢核")
    private String isCheckFrequency;

    @ApiModelProperty(notes = "是否重複檢核名稱")
    private String isCheckFrequencyStr;

    @ApiModelProperty(notes = "是否欄位勾選")
    private String isFieldCheck;

    @ApiModelProperty(notes = "是否欄位勾選名稱")
    private String isFieldCheckStr;

    @ApiModelProperty(notes = "是否唯讀")
    private Boolean isReadonly;

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Integer getGroupSeq() {
        return groupSeq;
    }

    public void setGroupSeq(Integer groupSeq) {
        this.groupSeq = groupSeq;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnTypeName() {
        return columnTypeName;
    }

    public void setColumnTypeName(String columnTypeName) {
        this.columnTypeName = columnTypeName;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Boolean getIsMustKey() {
        return isMustKey;
    }

    public void setIsMustKey(Boolean isMustKey) {
        this.isMustKey = isMustKey;
    }

    public String getIsMustKeyStr() {
        return isMustKeyStr;
    }

    public void setIsMustKeyStr(String isMustKeyStr) {
        this.isMustKeyStr = isMustKeyStr;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public List<List<CasesetComplexColumnData>> getComplex() {
        return complex;
    }

    public void setComplex(List<List<CasesetComplexColumnData>> complex) {
        this.complex = complex;
    }

    public String getIsCheckFrequency() {
        return isCheckFrequency;
    }

    public void setIsCheckFrequency(String isCheckFrequency) {
        this.isCheckFrequency = isCheckFrequency;
    }

    public String getIsCheckFrequencyStr() {
        return isCheckFrequencyStr;
    }

    public void setIsCheckFrequencyStr(String isCheckFrequencyStr) {
        this.isCheckFrequencyStr = isCheckFrequencyStr;
    }

    public String getIsFieldCheck() {
        return isFieldCheck;
    }

    public void setIsFieldCheck(String isFieldCheck) {
        this.isFieldCheck = isFieldCheck;
    }

    public String getIsFieldCheckStr() {
        return isFieldCheckStr;
    }

    public void setIsFieldCheckStr(String isFieldCheckStr) {
        this.isFieldCheckStr = isFieldCheckStr;
    }

    public Boolean getIsReadonly() {
        return isReadonly;
    }

    public void setIsReadonly(Boolean readonly) {
        isReadonly = readonly;
    }
}
