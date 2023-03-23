package gov.kcg.kgo.geomodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * GEO 20211026 add
 * Model for 使用者資料
 */
@Component
@ApiModel(description = "使用者資料")
public class GeoKgoUserInfoModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "市府員工帳號.")
    private String userId;

    @ApiModelProperty(notes = "姓名.")
    private String name;

    @ApiModelProperty(notes = "Email.")
    private String email;

    @ApiModelProperty(notes = "電話.")
    private String phone;

    @ApiModelProperty(notes = "機關.")
    private String organName;

    @ApiModelProperty(notes = "單位.")
    private String unitName;

    @ApiModelProperty(notes = "機關id.")
    private String organId;

    @ApiModelProperty(notes = "單位id.")
    private String unitId;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }
}
