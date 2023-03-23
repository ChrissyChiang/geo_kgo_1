package gov.kcg.kgo.geomodel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * GEO 20221014 add_Jim
 * 查詢使用者資訊 model
 */
@ApiModel(description = "查詢使用者資訊 model")
public class GeoUserQueryModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "機關名稱")
    private String organName;

    @ApiModelProperty(notes = "科室名稱")
    private String unitName;

    @ApiModelProperty(notes = "帳號")
    private String userId;

    @ApiModelProperty(notes = "姓名")
    private String userName;

    @ApiModelProperty(notes = "跨機關檢視")
    private Boolean isAvailableCrossReview;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean isAvailableCrossReview() {
        return isAvailableCrossReview;
    }

    public void setAvailableCrossReview(Boolean availableCrossReview) {
        isAvailableCrossReview = availableCrossReview;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(this);
    }
}
