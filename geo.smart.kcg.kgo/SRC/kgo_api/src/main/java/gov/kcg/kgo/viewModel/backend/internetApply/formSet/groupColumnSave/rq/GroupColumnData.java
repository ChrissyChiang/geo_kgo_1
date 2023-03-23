package gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rq;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 網路申辦-表單設定-欄位群組維護-進版儲存-群組欄位資料
 */
@ApiModel(description = "網路申辦-表單設定-欄位群組維護-進版儲存-群組欄位資料")
public class GroupColumnData {

	/** 區塊說明 */
	@ApiModelProperty(notes = "區塊說明")
	private String groupName;

	/** 顯示順序 */
	@ApiModelProperty(notes = "顯示順序")
	private Integer orderNum;

	/** GEO 20211203 add 重複檢核 */
	@ApiModelProperty(notes = "檢核週期 'Year','Month', null ")
	private String checkFrequencyPeriod;

	/** 欄位設定集合 */
	@ApiModelProperty(notes = "欄位設定集合")
	private List<CasesetColumnData> columnData;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<CasesetColumnData> getColumnData() {
		return columnData;
	}

	public void setColumnData(List<CasesetColumnData> columnData) {
		this.columnData = columnData;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getCheckFrequencyPeriod() {
		return checkFrequencyPeriod;
	}

	public void setCheckFrequencyPeriod(String checkFrequencyPeriod) {
		this.checkFrequencyPeriod = checkFrequencyPeriod;
	}
}
