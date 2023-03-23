package gov.kcg.kgo.geoviewmodel.frontend.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20221020 add_Jim
 * MyData Gen Data ViewForm
 */
@ApiModel(description = "MyData Gen Data ViewForm")
public class GeoMyDataGenDataViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "TxId")
    private String txId;
    @ApiModelProperty(value = "MyData data")
    private String data;

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
