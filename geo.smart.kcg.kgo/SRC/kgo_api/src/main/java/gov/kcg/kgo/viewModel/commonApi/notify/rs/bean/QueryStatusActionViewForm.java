package gov.kcg.kgo.viewModel.commonApi.notify.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 查詢案件狀態 Form
 */
@ApiModel(description = "查詢案件狀態 ViewForm")
public class QueryStatusActionViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "查詢案件狀態清單")
	private List<QueryStatasInfo> queryStatasInfo;

	public List<QueryStatasInfo> getQueryStatasInfo() {
		return queryStatasInfo;
	}

	public void setQueryStatasInfo(List<QueryStatasInfo> queryStatasInfo) {
		this.queryStatasInfo = queryStatasInfo;
	}
}
