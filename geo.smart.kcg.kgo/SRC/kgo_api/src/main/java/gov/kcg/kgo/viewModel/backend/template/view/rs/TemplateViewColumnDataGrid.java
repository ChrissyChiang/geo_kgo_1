package gov.kcg.kgo.viewModel.backend.template.view.rs;

import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rq.CasesetComplexColumnData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "後台表單維護– 明細-欄位資料集合")
public class TemplateViewColumnDataGrid {

	/** 序號 */
	@ApiModelProperty(notes = "序號")
	private Integer seq;

	/** 對應欄位 */
	@ApiModelProperty(notes = "對應欄位")
	private String columnId;

	/** 欄位名稱 */
	@ApiModelProperty(notes = "欄位名稱")
	private String columnName;

	/** 欄位型態 */
	@ApiModelProperty(notes = "欄位型態")
	private String columnType;

	/** 欄位型態名稱 */
	@ApiModelProperty(notes = "欄位型態名稱")
	private String columnTypeName;

	/** 欄位長度 */
	@ApiModelProperty(notes = "欄位長度")
	private Integer length;

	/** 必填 */
	@ApiModelProperty(notes = "必填")
	private String isMustKey;

	/** 必填名稱 */
	@ApiModelProperty(notes = "必填名稱")
	private String isMustKeyStr;

	/** 顯示順序 */
	@ApiModelProperty(notes = "顯示順序")
	private Integer orderNum;

	/** 欄位設定值 */
	@ApiModelProperty(notes = "欄位設定值")
	private String columnValue;

	/** MyData資料集ID */
	@ApiModelProperty(notes = "MyData資料集ID")
	private String myDataId;

	/** MyData欄位 */
	@ApiModelProperty(notes = "MyData欄位")
	private String myDataColumn;

	/** MyData驗證 */
	@ApiModelProperty(notes = "MyData驗證")
	private String myDataCheckType;

	/** MyData驗證值 */
	@ApiModelProperty(notes = "MyData驗證值")
	private String MyDataCheckValue;

	/** MyData整合方式 */
	@ApiModelProperty(notes = "MyData整合方式")
	private String MyDataType;

	/** 欄位備註 **/
	@ApiModelProperty(value = "欄位備註")
	private String memo;

	/** 附件類型 **/
	@ApiModelProperty(notes = "附件類型")
	private String fileType;

	/** 複合欄位資料集合 **/
	@ApiModelProperty(value = "複合欄位資料集合")
	private List<List<TemplateViewComplexColumnData>> complex;

	/** GEO 20211019 add 重複檢核 */
	@ApiModelProperty(notes = "是否重複檢核")
	private String isCheckFrequency;

	/** GEO 20211019 add 重複檢核 */
	@ApiModelProperty(notes = "是否重複檢核名稱")
	private String isCheckFrequencyStr;

	/** GEO 20211102 add 欄位勾選*/
	@ApiModelProperty(notes = "是否欄位勾選")
	private String isFieldCheck;

	/** GEO 20211102 add 欄位勾選*/
	@ApiModelProperty(notes = "是否欄位勾選名稱")
	private String isFieldCheckStr;


	public TemplateViewColumnDataGrid() {
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getIsMustKey() {
		return isMustKey;
	}

	public void setIsMustKey(String isMustKey) {
		this.isMustKey = isMustKey;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getColumnTypeName() {
		return columnTypeName;
	}

	public void setColumnTypeName(String columnTypeName) {
		this.columnTypeName = columnTypeName;
	}

	public String getIsMustKeyStr() {
		return isMustKeyStr;
	}

	public void setIsMustKeyStr(String isMustKeyStr) {
		this.isMustKeyStr = isMustKeyStr;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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
		return MyDataCheckValue;
	}

	public void setMyDataCheckValue(String myDataCheckValue) {
		MyDataCheckValue = myDataCheckValue;
	}

	public String getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}

	public String getMyDataType() {
		return MyDataType;
	}

	public void setMyDataType(String myDataType) {
		MyDataType = myDataType;
	}


	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public List<List<TemplateViewComplexColumnData>> getComplex() {
		return complex;
	}

	public void setComplex(List<List<TemplateViewComplexColumnData>> complex) {
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
}
