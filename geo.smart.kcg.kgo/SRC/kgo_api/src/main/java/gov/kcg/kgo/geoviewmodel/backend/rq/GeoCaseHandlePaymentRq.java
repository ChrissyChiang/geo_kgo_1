package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="# 後台案件管理-變更繳費狀態")
public class GeoCaseHandlePaymentRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes="案件ID")
    private String caseId;
    @ApiModelProperty(notes="繳費變更狀態")
    private String paymentStatus;
    public String getCaseId() {return caseId;}

    public void setCaseId(String caseId) {this.caseId = caseId;}

    public String getPaymentStatus() {return paymentStatus;}

    public void setPaymentStatus(String paymentStatus) {this.paymentStatus = paymentStatus;}
}
