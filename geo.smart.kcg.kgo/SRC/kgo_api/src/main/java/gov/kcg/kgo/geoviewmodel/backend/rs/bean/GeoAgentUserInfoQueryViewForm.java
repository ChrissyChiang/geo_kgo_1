package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.geomodel.GeoKgoUserInfoModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211026 add
 * 後台-設定代理人:取得該登入人員資料 ViewForm
 */

@ApiModel(description = "後台-設定代理人:取得該登入人員資料 ViewForm")
public class GeoAgentUserInfoQueryViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "使用者資料")
    private GeoKgoUserInfoModel userInfo;

    public GeoKgoUserInfoModel getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(GeoKgoUserInfoModel userInfo) {
        this.userInfo = userInfo;
    }
}
