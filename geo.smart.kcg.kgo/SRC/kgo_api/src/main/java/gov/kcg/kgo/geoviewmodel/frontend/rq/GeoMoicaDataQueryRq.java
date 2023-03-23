package gov.kcg.kgo.geoviewmodel.frontend.rq;

import gov.kcg.kgo.viewModel.commonApi.common.rq.ApiCityCommonRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 2021108 add
 * <p>
 * 為A流程服務提供取得自然人憑證登入資訊(姓名與身分證字號) rq
 */

@ApiModel(description = "為A流程服務提供取得自然人憑證登入資訊(姓名與身分證字號) rq")
public class GeoMoicaDataQueryRq extends ApiCityCommonRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "案件id", required = true)
    private String caseId;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }
}
