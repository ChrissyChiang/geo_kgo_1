package gov.kcg.kgo.geoviewmodel.frontend.rq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 20221104 Ruby add 便民一路通依照市政服務不同的資安等級需求，搭配各種安全等級的認證機制
 * */
@ApiModel(description = "服務申辦-驗證符合登入身份 rq")
public class GeoValidateLoginTypeRq {

    @ApiModelProperty(value="驗證項目id，例如caseSetId:S2020112500002/appointmentId:AM2022102400001")
    private String vailDataId;

    @ApiModelProperty(value="驗證項目類別：1 ->預約(AM開頭), 2 ->案件(S開頭),3 ->mydata查詢 ")
    private int vailDataType;

    public String getVailDataId() {
        return vailDataId;
    }

    public void setVailDataId(String vailDataId) {
        this.vailDataId = vailDataId;
    }

    public int getVailDataType() {
        return vailDataType;
    }

    public void setVailDataType(int vailDataType) {
        this.vailDataType = vailDataType;
    }
}
