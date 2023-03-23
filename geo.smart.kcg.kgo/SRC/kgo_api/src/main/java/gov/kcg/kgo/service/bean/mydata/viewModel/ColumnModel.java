package gov.kcg.kgo.service.bean.mydata.viewModel;

import org.apache.commons.lang3.StringUtils;

public class ColumnModel {

	private static final long serialVersionUID = 1L;

	private String columnId;

	private String myDataColumn;

	private String type;

	private String myDataType;

	private String fileName;

	private Integer headerRow;

	private Integer dataStartRow;

	public ColumnModel() {

	}

	public ColumnModel(String columnId, String myDataColumn, String type) {
		this.columnId = columnId;
		this.myDataColumn = myDataColumn;
		this.type = type;
		this.myDataType = "JSON";
		this.fileName = StringUtils.EMPTY;
	}

	public ColumnModel(String columnId, String myDataColumn, String type, String fileName) {
		this.columnId = columnId;
		this.myDataColumn = myDataColumn;
		this.type = type;
		this.myDataType = "JSON";
		this.fileName = fileName;
	}

	public ColumnModel(String columnId, String myDataColumn, String type, String myDataType, String fileName, Integer headerRow, Integer dataStartRow) {
		this.columnId = columnId;
		this.myDataColumn = myDataColumn;
		this.type = type;
		this.myDataType = myDataType;
		this.fileName = fileName;
		this.headerRow = headerRow;
		this.dataStartRow = dataStartRow;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getMyDataColumn() {
		return myDataColumn;
	}

	public void setMyDataColumn(String myDataColumn) {
		this.myDataColumn = myDataColumn;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMyDataType() {
		return myDataType;
	}

	public void setMyDataType(String myDataType) {
		this.myDataType = myDataType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getHeaderRow() {
		return headerRow;
	}

	public void setHeaderRow(Integer headerRow) {
		this.headerRow = headerRow;
	}

	public Integer getDataStartRow() {
		return dataStartRow;
	}

	public void setDataStartRow(Integer dataStartRow) {
		this.dataStartRow = dataStartRow;
	}

}
