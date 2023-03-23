package gov.kcg.kgo.geomodel;

import io.swagger.annotations.ApiModel;

/**
 * GEO 20221028 add
 * 更新 民政局黑名單資料 來源 城市資料平台
 */
@ApiModel(description = "民政局黑名單 model")
public class GeoCityForDepartmentCivilPerson {
    private String name;
    private String identityId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }
}
