package gov.kcg.kgo.viewModel.backend.template.update.rq.bean;

import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rq.CasesetComplexColumnData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import java.util.List;

/**
 * 網路申辦-表單設定-欄位群組維護-進版儲存-欄位資料
 */
@ApiModel(description = "網路申辦-表單設定-欄位群組維護-進版儲存-欄位資料")
public class TemplateUpdateCasesetColumnData {

	@ApiModelProperty(notes = "序號")
	private Integer seq;

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

	@ApiModelProperty(notes = "MyData資料集ID")
	private String myDataId;

	@ApiModelProperty(notes = "MyData欄位")
	private String myDataColumn;

	@ApiModelProperty(notes = "MyData驗證")
	private String myDataCheckType;

	@ApiModelProperty(notes = "MyData驗證值")
	private String myDataCheckValue;

	@ApiModelProperty(notes = "MyData整合方式")
	private String myDataType;

	@ApiModelProperty(notes = "myDataClientId")
	private String myDataClientId;

	@ApiModelProperty(notes = "備註說明")
	private String memo;

	@ApiModelProperty(notes = "附件類型")
	private String fileType;

	@ApiModelProperty(notes = "複合欄位資料集合")
	private List<List<TemplateUpdateCasesetComplexColumnData>> complex;

	/**GEO 20211019 add */
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

	public String getMyDataId() {
		return myDataId;
	}

	public void setMyDataId(String myDataId) {
		this.myDataId = myDataId;
	}

	public String getMyDataColumn() {
		return myDataColumn;
	}

	public void setMyDataColumn(String myDataColumn) {
		this.myDataColumn = myDataColumn;
	}

	public String getMyDataCheckType() {
		return myDataCheckType;
	}

	public void setMyDataCheckType(String myDataCheckType) {
		this.myDataCheckType = myDataCheckType;
	}

	public String getMyDataCheckValue() {
		return myDataCheckValue;
	}

	public void setMyDataCheckValue(String myDataCheckValue) {
		this.myDataCheckValue = myDataCheckValue;
	}

	public String getMyDataType() {
		return myDataType;
	}

	public void setMyDataType(String myDataType) {
		this.myDataType = myDataType;
	}

	public String getMyDataClientId() {
		return myDataClientId;
	}

	public void setMyDataClientId(String myDataClientId) {
		this.myDataClientId = myDataClientId;
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

	public List<List<TemplateUpdateCasesetComplexColumnData>> getComplex() {
		return complex;
	}

	public void setComplex(List<List<TemplateUpdateCasesetComplexColumnData>> complex) {
		this.complex = complex;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
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
