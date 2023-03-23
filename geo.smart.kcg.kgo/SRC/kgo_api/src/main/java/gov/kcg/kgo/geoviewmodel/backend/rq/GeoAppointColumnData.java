package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rq.CasesetComplexColumnData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 後台-線上預約臨櫃-編輯：臨櫃預約表單設定欄位初始化-欄位資料
 */
@ApiModel(description = "後台-線上預約臨櫃-編輯：臨櫃預約表單設定欄位初始化-欄位資料")
public class GeoAppointColumnData {

	@ApiModelProperty(notes = "欄位ID")
	private String columnId;

	@ApiModelProperty(notes = "欄位名稱")
	private String columnName;

	@ApiModelProperty(notes = "欄位型態")
	private String columnType;

	@ApiModelProperty(notes = "欄位設定值")
	private String columnValue;

	@ApiModelProperty(notes = "顯示順序")
	private Integer orderNum;

	@ApiModelProperty(notes = "欄位長度")
	private Integer length;

	@ApiModelProperty(notes = "是否必填")
	private String isMustKey;

	@ApiModelProperty(notes = "備註說明")
	private String memo;

	@ApiModelProperty(notes = "附件類型")
	private String fileType;

	@ApiModelProperty(notes = "複合欄位資料集合")
	private List<List<CasesetComplexColumnData>> complex;

	/** GEO 20211019 add */
	@ApiModelProperty(notes = "是否重複檢核")
	private Integer isCheckFrequency;

	/** GEO 20211102 add 欄位勾選*/
	@ApiModelProperty(notes = "是否欄位勾選")
	private Integer isFieldCheck;

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

	public String getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getIsMustKey() {
		return isMustKey;
	}

	public void setIsMustKey(String isMustKey) {
		this.isMustKey = isMustKey;
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

	public Integer getIsCheckFrequency() {
		return isCheckFrequency;
	}

	public void setIsCheckFrequency(Integer isCheckFrequency) {
		this.isCheckFrequency = isCheckFrequency;
	}

	public Integer getIsFieldCheck() {
		return isFieldCheck;
	}

	public void setIsFieldCheck(Integer isFieldCheck) {
		this.isFieldCheck = isFieldCheck;
	}
}
