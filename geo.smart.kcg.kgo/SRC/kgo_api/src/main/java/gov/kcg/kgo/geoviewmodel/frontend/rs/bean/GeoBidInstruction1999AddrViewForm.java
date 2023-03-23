package gov.kcg.kgo.geoviewmodel.frontend.rs.bean;

import gov.kcg.kgo.geomodel.Geo1999ItemsMainModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20210814 add
 *
 * 依1999行政區代碼取得下一級行政區資料 ViewForm
 */
@ApiModel(description = "依1999行政區代碼取得下一級行政區資料  ViewForm")
public class GeoBidInstruction1999AddrViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "行政區清單")
    private List<Geo1999ItemsMainModel> addrList;

    public List<Geo1999ItemsMainModel> getAddrList() {
        return addrList;
    }

    public void setAddrList(List<Geo1999ItemsMainModel> addrList) {
        this.addrList = addrList;
    }

}
