package gov.kcg.kgo.viewModel.backend.caseHadle.base.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台案件處理-待審核匣-補正作業-明細-複合欄位資料
 */
@ApiModel(description = "後台案件處理-待審核匣-補正作業-明細-複合欄位資料")
public class CaseHandleCorrectCasesetColumnChild {

	
	/** 複合欄位ID **/
    @ApiModelProperty(value = "複合欄位ID")
    private String cColumnId;

    /** 欄位種類 **/
    @ApiModelProperty(value = "欄位種類")
    private String columnType;

    /** 欄位長度 **/
    @ApiModelProperty(value = "欄位長度")
    private String length;

    /** 是否必填 **/
    @ApiModelProperty(value = "是否必填")
    private String isMustKey;

    /** 欄位設定值 **/
    @ApiModelProperty(value = "欄位設定值")
    private String columnValue;

    /** 需要補正 **/
    @ApiModelProperty(value = "需要補正")
    private String isCorrect;

    /** 補正說明 **/
    @ApiModelProperty(value = "補正說明")
    private String correctMemo;

    /** 欄位資料 **/
    @ApiModelProperty(value = "欄位資料")
    private String columnDetailValue;

    /** 父欄位 **/
    @ApiModelProperty(value = "此父欄位有填時，如果父欄位有值，此複合欄位必填")
    private String pcolumnId;

    public String getcColumnId() {
        return cColumnId;
    }

    public void setcColumnId(String cColumnId) {
        this.cColumnId = cColumnId;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }
    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    public String getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(String isCorrect) {
        this.isCorrect = isCorrect;
    }

    public String getCorrectMemo() {
        return correctMemo;
    }

    public void setCorrectMemo(String correctMemo) {
        this.correctMemo = correctMemo;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getIsMustKey() {
        return isMustKey;
    }

    public void setIsMustKey(String isMustKey) {
        this.isMustKey = isMustKey;
    }

    public String getColumnDetailValue() {
        return columnDetailValue;
    }

    public void setColumnDetailValue(String columnDetailValue) {
        this.columnDetailValue = columnDetailValue;
    }

    public String getPcolumnId() {
        return pcolumnId;
    }

    public void setPcolumnId(String pcolumnId) {
        this.pcolumnId = pcolumnId;
    }

}
