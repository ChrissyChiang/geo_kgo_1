package gov.kcg.kgo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@ApiModel(description = "帳號權限管理-帳號搜尋清單")
@Entity
public class AccountManagementQueryDto {

    @Id
    @ApiModelProperty(notes = "使用者帳號")
    @Column(name = "USER_ID")
    private String userId;

    @ApiModelProperty(notes = "使用者名稱")
    @Column(name = "NAME")
    private String name;

    @ApiModelProperty(notes = "科室名稱")
    @Column(name = "UNIT_NAME")
    private String unitName;

    @ApiModelProperty(notes = "機關名稱")
    @Column(name = "ORGAN_NAME")
    private String organName;

    /**
     * GEO 20211115 add 非市府員工登入後台
     */
    @ApiModelProperty(value = "Email")
    @Column(name = "EMAIL")
    private String email;

    /**
     * GEO 20211115 add 非市府員工登入後台
     */
    @ApiModelProperty(value = "公務Email")
    @Column(name = "PUBLIC_EMAIL")
    private String publicEmail;

    /**
     * GEO 20211115 add 非市府員工登入後台
     */
    @ApiModelProperty(value = "電話")
    @Column(name = "PHONE")
    private String phoneNumber;

    /**
     * GEO 20211115 add 非市府員工登入後台
     */
    @ApiModelProperty(value = "登入方式")
    @Column(name = "USER_LOGIN_TYPE")
    private String loginType;

    /**
     * GEO 20211125 add 跨機關檢視
     */
    @ApiModelProperty(value = "是否可跨機關檢視")
    @Column(name = "IS_AVAILABLE_CROSS_REVIEW")
    private Boolean IsAvailableCrossReview;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPublicEmail() {
        return publicEmail;
    }

    public void setPublicEmail(String publicEmail) {
        this.publicEmail = publicEmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public Boolean getAvailableCrossReview() {
        return IsAvailableCrossReview;
    }

    public void setAvailableCrossReview(Boolean availableCrossReview) {
        IsAvailableCrossReview = availableCrossReview;
    }
}
