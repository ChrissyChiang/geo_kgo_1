package gov.kcg.kgo.geoviewmodel.frontend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211113 add 前台使用者註冊
 * 前台-使用者帳號:註冊 rq
 */
@ApiModel(description = "前台-使用者帳號:註冊 rq")
public class GeoFrontendUserRegisterRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "使用者id 編輯時必填，新增不填")
    private String userId;

    @ApiModelProperty(notes = "信箱", required = true)
    private String Email;

    @ApiModelProperty(notes = "姓名", required = true)
    private String Name;

    @ApiModelProperty(notes = "身分證號", required = true)
    private String Identity;

    @ApiModelProperty(notes = "手機")
    private String Phone;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getIdentity() {
        return Identity;
    }

    public void setIdentity(String identity) {
        Identity = identity;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
