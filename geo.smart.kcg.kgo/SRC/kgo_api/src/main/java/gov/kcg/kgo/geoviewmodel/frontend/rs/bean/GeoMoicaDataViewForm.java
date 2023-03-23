package gov.kcg.kgo.geoviewmodel.frontend.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211108 add
 * 為A流程服務提供取得自然人憑證登入資訊 View Form
 */
@ApiModel(description = "為A流程服務提供取得自然人憑證登入資訊 View Form")
public class GeoMoicaDataViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "姓名")
    private String caseUserName;

    @ApiModelProperty(value = "身分證號")
    private String caseUserId;

    @ApiModelProperty(value = "iv")
    private String ivStr;

    public String getCaseUserName() {
        return caseUserName;
    }

    public void setCaseUserName(String caseUserName) {
        this.caseUserName = caseUserName;
    }

    public String getCaseUserId() {
        return caseUserId;
    }

    public void setCaseUserId(String caseUserId) {
        this.caseUserId = caseUserId;
    }

    public String getIvStr() {
        return ivStr;
    }

    public void setIvStr(String ivStr) {
        this.ivStr = ivStr;
    }

}
