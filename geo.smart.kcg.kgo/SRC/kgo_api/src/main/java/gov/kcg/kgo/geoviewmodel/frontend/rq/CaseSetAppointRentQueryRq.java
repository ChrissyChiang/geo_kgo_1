package gov.kcg.kgo.geoviewmodel.frontend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(value="前台線上預約-租借清單")
public class CaseSetAppointRentQueryRq  extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "線上預約查詢租借ID",required = true)
    private String caseSetId;
}
