package gov.kcg.kgo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 20221105 好孕檢視-個別動態欄位
 */
@ApiModel(description = "好孕檢視-動態欄位")
public class GeoCaseByOrganModel {

    /** 欄位值 */
    @ApiModelProperty(notes = "案件服務編號")
    private String caseSetId;

    /** 欄位值 */
    @ApiModelProperty(notes = "案件編號")
    private String caseId;

    /** 欄位名稱 */
    @ApiModelProperty(notes = "欄位名稱")
    private String columnName;

    /** 欄位ID */
    @ApiModelProperty(notes = "欄位ID")
    private String columnId;

    /** 欄位value */
    @ApiModelProperty(notes = "欄位Value")
    private String columnValue;

    /** 欄位value */
    @ApiModelProperty(notes = "案件表單欄位版本")
    private Integer version;

    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
