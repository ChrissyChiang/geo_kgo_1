package gov.kcg.kgo.dto;

/**
 * GEO 20211019 add
 * 重複檢核案件數dto.
 */
public class CaseMainCheckFrequencyDto {

	/** 欄位id */
	private String columnId;

	/** 複合欄位cColumn id */
	private String CColumnId;

	/** 檢核週期 */
	private String checkFrequencyPeriod;

	/** 欄位填寫值 */
	private String value;

	public CaseMainCheckFrequencyDto() {
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getCColumnId() {
		return CColumnId;
	}

	public void setCColumnId(String CColumnId) {
		this.CColumnId = CColumnId;
	}

	public String getCheckFrequencyPeriod() {
		return checkFrequencyPeriod;
	}

	public void setCheckFrequencyPeriod(String checkFrequencyPeriod) {
		this.checkFrequencyPeriod = checkFrequencyPeriod;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
