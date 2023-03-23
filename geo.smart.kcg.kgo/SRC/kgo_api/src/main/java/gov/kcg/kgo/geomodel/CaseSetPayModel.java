package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import java.util.List;
@ApiModel(value="費用設定")
public class CaseSetPayModel extends ApiRequest {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="是否繳費")
    private Boolean needPay;
    @ApiModelProperty(value="繳費期限")
    private Integer payDeadline;
    @ApiModelProperty(value="退費期限")
    private Integer refundDeadline;
    @ApiModelProperty(value="退費天數比例")
    private List<GeoCaseSetRefundModel> refundList;

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

    public List<GeoCaseSetRefundModel> getRefundList() {
        return refundList;
    }

    public void setRefundList(List<GeoCaseSetRefundModel> refundList) {
        this.refundList = refundList;
    }
}
