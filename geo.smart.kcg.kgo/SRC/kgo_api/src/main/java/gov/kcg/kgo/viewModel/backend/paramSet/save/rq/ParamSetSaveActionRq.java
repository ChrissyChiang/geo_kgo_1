package gov.kcg.kgo.viewModel.backend.paramSet.save.rq;

import java.util.List;

import gov.kcg.kgo.viewModel.backend.paramSet.save.rq.bean.ParamSetSaveBean;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台 - 參數設定 - 儲存 rq")
public class ParamSetSaveActionRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "參數設定清單")
	List<ParamSetSaveBean> paramSetList;

	public List<ParamSetSaveBean> getParamSetList() {
		return paramSetList;
	}

	public void setParamSetList(List<ParamSetSaveBean> paramSetList) {
		this.paramSetList = paramSetList;
	}
}
