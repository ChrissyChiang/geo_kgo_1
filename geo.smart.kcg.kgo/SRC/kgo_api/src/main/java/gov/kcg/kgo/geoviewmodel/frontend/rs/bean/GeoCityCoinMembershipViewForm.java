package gov.kcg.kgo.geoviewmodel.frontend.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.logging.log4j.util.Strings;

/**
 * GEO 20221107 add_Jim
 * 查詢市民科技會員資訊 rs ViewForm
 */
@ApiModel(description = "查詢市民科技會員資訊 rs ViewForm")
public class GeoCityCoinMembershipViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "subType")
    private String subType;
    @ApiModelProperty(value = "sub")
    private String sub;
    @ApiModelProperty(value = "會員狀態")
    private boolean isMembership;
    @ApiModelProperty(value = "會員碼")
    private String uuid;
    @ApiModelProperty(value = "實名制")
    private boolean isRealName;
    @ApiModelProperty(value = "是否有高銀帳號")
    private boolean haveKcgBankAccount;
    @ApiModelProperty(value = "身分證")
    private String license;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "程式幣")
    private String cityCoin;

    public GeoCityCoinMembershipViewForm() {
        this.subType = Strings.EMPTY;
        this.sub = Strings.EMPTY;
        this.isMembership = false;
        this.uuid = Strings.EMPTY;
        this.isRealName = false;
        this.license = Strings.EMPTY;
        this.name = Strings.EMPTY;
        this.haveKcgBankAccount = false;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public boolean isMembership() {
        return isMembership;
    }

    public void setMembership(boolean membership) {
        isMembership = membership;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isRealName() {
        return isRealName;
    }

    public void setRealName(boolean realName) {
        isRealName = realName;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityCoin() {
        return cityCoin;
    }

    public void setCityCoin(String cityCoin) {
        this.cityCoin = cityCoin;
    }

    public boolean isHaveKcgBankAccount() {return haveKcgBankAccount;}

    public void setHaveKcgBankAccount(boolean haveKcgBankAccount) {this.haveKcgBankAccount = haveKcgBankAccount;}
}
