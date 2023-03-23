package gov.kcg.kgo.geoviewmodel.frontend.rs.bean;

import gov.kcg.kgo.geomodel.GeoKgoFrontendUserModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211113 add 前台使用者註冊
 * 前台-使用者帳號:取得註冊後使用者資訊 ViewForm
 */
@ApiModel(description = "前台-使用者帳號:取得註冊後使用者資訊 ViewForm")
public class GeoFrontendUserInfoQueryViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "註冊後使用者資訊")
    private GeoKgoFrontendUserModel user;

    @ApiModelProperty(value = "是否已註冊")
    private boolean isRegister;

    @ApiModelProperty(value = "是否已登入")
    private boolean isLogin;

    public GeoKgoFrontendUserModel getUser() {
        return user;
    }

    public void setUser(GeoKgoFrontendUserModel user) {
        this.user = user;
    }

    public boolean isRegister() {
        return isRegister;
    }

    public void setRegister(boolean register) {
        isRegister = register;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
