package gov.kcg.kgo.geoviewmodel.frontend.rq;

import gov.kcg.kgo.viewModel.commonApi.common.rq.ApiCityCommonRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20220828 add for 智能客服
 * 案件進度查詢
 *
 */
@ApiModel(description = "提供智能客服案件進度查詢 rq")
public class GeoCaseProcessDetailRq extends ApiCityCommonRequest {

    @ApiModelProperty(value = "案件編號")
    private String caseId;

    @ApiModelProperty(value = "身分證後四碼")
    private String idCheck;

    @ApiModelProperty(value = "電話")
    private String phone;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getIdCheck() {
        return idCheck;
    }

    public void setIdCheck(String idCheck) {
        this.idCheck = idCheck;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
