package gov.kcg.kgo.geoviewmodel.frontend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20210814 add
 *
 * 依1999行政區代碼取得下一級行政區資料 rq
 */

@ApiModel(description = "依1999行政區代碼取得下一級行政區資料 rq")
public class GeoBidInstruction1999AddrRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "十位代碼", position=1)
    private String addrCode;

    public String getAddrCode() {
        return addrCode;
    }

    public void setAddrCode(String addrCode) {
        this.addrCode = addrCode;
    }
}
