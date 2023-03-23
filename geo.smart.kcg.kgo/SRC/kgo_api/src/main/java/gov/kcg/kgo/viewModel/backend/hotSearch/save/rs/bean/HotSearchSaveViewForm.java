package gov.kcg.kgo.viewModel.backend.hotSearch.save.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 熱門搜尋-熱門設定-儲存 View Form
 */
@ApiModel(description = "熱門搜尋-熱門設定-儲存 View Form")
public class HotSearchSaveViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "執行結果訊息")
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
