package gov.kcg.kgo.viewModel.frontend.caseform.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
@ApiModel(value="線上場地活動租借申請表單基礎欄位")
public class CaseRentHomeActionViewForm extends HomeActionViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="是否審查(啟動流程)")
    private Boolean activeFlow;
    @ApiModelProperty(value="案件類型")
    private String caseSetCategory;
    @ApiModelProperty(value="是否需要繳費")
    private Boolean needPay;
    @ApiModelProperty(value="繳費期限")
    private Integer payDeadline;
    @ApiModelProperty(value="退費期限")
    private Integer refundDeadline;

    public Boolean getActiveFlow() {
        return activeFlow;
    }

    public void setActiveFlow(Boolean activeFlow) {
        this.activeFlow = activeFlow;
    }

    public String getCaseSetCategory() {
        return caseSetCategory;
    }

    public void setCaseSetCategory(String caseSetCategory) {
        this.caseSetCategory = caseSetCategory;
    }

    public Boolean getNeedPay() {
        return needPay;
    }

    public void setNeedPay(Boolean needPay) {
        this.needPay = needPay;
    }

    public Integer getPayDeadline() {
        return payDeadline;
    }

    public void setPayDeadline(Integer payDeadline) {
        this.payDeadline = payDeadline;
    }

    public Integer getRefundDeadline() {
        return refundDeadline;
    }

    public void setRefundDeadline(Integer refundDeadline) {
        this.refundDeadline = refundDeadline;
    }
}
