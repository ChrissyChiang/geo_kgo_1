package gov.kcg.kgo.viewModel.frontend.caseform.rs.bean;

import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rq.CasesetComplexColumnData;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class CheckOrganApplyViewForm extends BaseViewForm {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "可申請表單")
	private Boolean isAllow;

	public Boolean getAllow() {
		return isAllow;
	}

	public void setAllow(Boolean allow) {
		isAllow = allow;
	}
}
