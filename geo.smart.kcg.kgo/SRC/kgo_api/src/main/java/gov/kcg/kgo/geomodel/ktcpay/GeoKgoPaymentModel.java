package gov.kcg.kgo.geomodel.ktcpay;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "導轉繳費平台請求物件")
public class GeoKgoPaymentModel extends BaseViewForm {

    @ApiModelProperty(name = "資料內容")
    private GeoKgoPaymentData data;
    @ApiModelProperty(name = "導轉url")
    private String actionUrl;

    public GeoKgoPaymentData getData() {
        return data;
    }

    public void setData(GeoKgoPaymentData data) {
        this.data = data;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }
}
