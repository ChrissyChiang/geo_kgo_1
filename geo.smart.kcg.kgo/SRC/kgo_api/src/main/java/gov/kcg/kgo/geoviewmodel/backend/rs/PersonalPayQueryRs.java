package gov.kcg.kgo.geoviewmodel.backend.rs;

import gov.kcg.kgo.geoviewmodel.backend.rs.bean.PersonalPayQueryViewForm;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import io.swagger.annotations.ApiModel;

/**
 * 提供個人預約繳費資訊清單 rs
 */

@ApiModel(description = "提供個人預約繳費資訊清單 rs")
public class PersonalPayQueryRs extends ApiBaseResponse<PersonalPayQueryViewForm> {
	private static final long serialVersionUID = 1L;
}
