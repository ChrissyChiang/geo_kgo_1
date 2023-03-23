package gov.kcg.kgo.geomodel;

import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * GEO 20221028 add
 * 更新 民政局黑名單資料 來源 城市資料平台
 */
@ApiModel(description = "民政局黑名單 model")
public class GeoCityForDepartmentCivil {
    private static String aesKey = "HExk7X3scM7Ti1BmWrHGrBHUWU+bFhPhUS4+VkWSFYM=";
    private String ivStr;
    private List<GeoCityForDepartmentCivilPerson> blackList;

    public static String getAesKey() {
        return aesKey;
    }

    public static void setAesKey(String aesKey) {
        GeoCityForDepartmentCivil.aesKey = aesKey;
    }

    public String getIvStr() {
        return ivStr;
    }

    public void setIvStr(String ivStr) {
        this.ivStr = ivStr;
    }

    public List<GeoCityForDepartmentCivilPerson> getBlackList() {
        return blackList;
    }

    public void setBlackList(List<GeoCityForDepartmentCivilPerson> blackList) {
        this.blackList = blackList;
    }
}
