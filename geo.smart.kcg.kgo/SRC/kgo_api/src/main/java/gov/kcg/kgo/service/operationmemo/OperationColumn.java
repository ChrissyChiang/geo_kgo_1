package gov.kcg.kgo.service.operationmemo;

/**
 * 操作紀錄 對應值.
 */
public class OperationColumn {
	
	/** 操作紀錄 標題. */
	private String colLabel;
	
	/** 操作紀錄 value. */
	private String colValue;
	
	public OperationColumn() {}
	
	/**
	 * Instantiates a new operation map column.
	 *
	 * @param colLabel the col label
	 * @param colValue the col value
	 */
	public OperationColumn(String colLabel, String colValue) {
		this.colLabel = colLabel;
		this.colValue = colValue;
	}

	public String getColLabel() {
		return colLabel;
	}

	public void setColLabel(String colLabel) {
		this.colLabel = colLabel;
	}

	public String getColValue() {
		return colValue;
	}

	public void setColValue(String colValue) {
		this.colValue = colValue;
	}
}
