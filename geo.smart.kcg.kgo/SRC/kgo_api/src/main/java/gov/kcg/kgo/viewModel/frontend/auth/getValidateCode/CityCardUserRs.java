package gov.kcg.kgo.viewModel.frontend.auth.getValidateCode;

import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.viewModel.frontend.auth.getValidateCode.bean.CityCardUserViewForm;
import io.swagger.annotations.ApiModel;


/**
 *市民卡界接至本站rs.
 */
@ApiModel(description = "市民卡界接至本站 rs")
public class CityCardUserRs extends ApiBaseResponse<CityCardUserViewForm>  {
	
	private static final long serialVersionUID = 1L;
}
