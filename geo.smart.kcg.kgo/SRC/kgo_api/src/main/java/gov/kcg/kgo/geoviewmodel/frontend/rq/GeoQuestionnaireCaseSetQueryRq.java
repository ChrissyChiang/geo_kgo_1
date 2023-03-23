package gov.kcg.kgo.geoviewmodel.frontend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20210923 add
 *
 * 服務管理-問卷設定:取得該服務問卷(主內容、題組、子題目) rq
 */

@ApiModel(description = "服務管理-問卷設定:取得該服務問卷(主內容、題組、子題目) rq")
public class GeoQuestionnaireCaseSetQueryRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "服務id", required = true)
	private String caseSetId;

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}
}
