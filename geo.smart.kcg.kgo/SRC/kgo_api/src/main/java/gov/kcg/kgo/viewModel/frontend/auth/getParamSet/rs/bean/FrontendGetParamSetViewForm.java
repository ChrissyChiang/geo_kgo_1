package gov.kcg.kgo.viewModel.frontend.auth.getParamSet.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "前臺 參數設定資料 ViewForm")
public class FrontendGetParamSetViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;
	
	/** 參數設定清單 */
	@ApiModelProperty(notes = "參數設定清單")
	List<FrontendGetParamSetVo> paramSetList;

	public List<FrontendGetParamSetVo> getParamSetList() {
		return paramSetList;
	}

	public void setParamSetList(List<FrontendGetParamSetVo> paramSetList) {
		this.paramSetList = paramSetList;
	}
}
