package gov.kcg.kgo.viewModel.frontend.bidCaseList.home.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 申辦案件清單-申辦案件頁籤顯示資料
 */
@ApiModel(description = "申辦案件清單-申辦案件頁籤顯示資料")
public class BidCaseListTypeDataGrid {

	@ApiModelProperty(notes = "類別代碼")
	private String value;

	@ApiModelProperty(notes = "類別名稱")
	private String name;

	/** GEO 20211224 add 顯示案件服務數量 */
	@ApiModelProperty(notes = "顯示案件服務數量")
	private String count;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
}
