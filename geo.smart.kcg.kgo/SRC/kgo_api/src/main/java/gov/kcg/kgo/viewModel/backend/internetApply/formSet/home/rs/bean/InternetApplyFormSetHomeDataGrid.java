package gov.kcg.kgo.viewModel.backend.internetApply.formSet.home.rs.bean;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 網路申辦-表單設定-初始畫面-群組所有欄位設定資料集合
 */
@ApiModel(description = "網路申辦-表單設定-初始畫面-群組所有欄位設定資料集合")
public class InternetApplyFormSetHomeDataGrid {

	/** 群組序號 */
	@ApiModelProperty(notes = "群組序號")
	private Integer groupSeq;

	/** 群組名稱 */
	@ApiModelProperty(notes = "群組名稱")
	private String groupName;

	/** 顯示順序 */
	@ApiModelProperty(notes = "顯示順序")
	private Integer orderNum;

	/** GEO 20211203 add 重複檢核 */
	@ApiModelProperty(notes = "重複檢核時間")
	private String CheckFrequencyPeriod;

	/** 表單群組欄位資料 */
	@ApiModelProperty(notes = "表單群組欄位資料")
	private List<InternetApplyFormSetGroupColumnDataGrid> columnData;

	public Integer getGroupSeq() {
		return groupSeq;
	}

	public void setGroupSeq(Integer groupSeq) {
		this.groupSeq = groupSeq;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public List<InternetApplyFormSetGroupColumnDataGrid> getColumnData() {
		return columnData;
	}

	public void setColumnData(List<InternetApplyFormSetGroupColumnDataGrid> columnData) {
		this.columnData = columnData;
	}

	public String getCheckFrequencyPeriod() {
		return CheckFrequencyPeriod;
	}

	public void setCheckFrequencyPeriod(String checkFrequencyPeriod) {
		CheckFrequencyPeriod = checkFrequencyPeriod;
	}
}
