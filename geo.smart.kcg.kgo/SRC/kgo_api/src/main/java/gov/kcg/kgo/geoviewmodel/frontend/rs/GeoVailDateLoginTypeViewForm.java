package gov.kcg.kgo.geoviewmodel.frontend.rs;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.checkBox.CheckBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 驗證不同項目的身份驗證
 */
@ApiModel(description = "驗證不同項目的身份驗證 View Form")
public class GeoVailDateLoginTypeViewForm extends BaseViewForm {
    private static final long serialVersionUID = 1L;

    /** 身分驗證設定CheckBox元件集合 **/
    @ApiModelProperty(value = "身分驗證設定CheckBox元件集合")
    private List<CheckBox> identityVerifyCheckBox;

    @ApiModelProperty(value = "符合登入驗證")
    private Boolean isLogin;

    public List<CheckBox> getIdentityVerifyCheckBox() {
        return identityVerifyCheckBox;
    }

    public void setIdentityVerifyCheckBox(List<CheckBox> identityVerifyCheckBox) {
        this.identityVerifyCheckBox = identityVerifyCheckBox;
    }

    public Boolean getLogin() {
        return isLogin;
    }

    public void setLogin(Boolean login) {
        isLogin = login;
    }
}
