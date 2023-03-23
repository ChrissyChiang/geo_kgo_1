package gov.kcg.kgo.geomodel;

import java.util.List;

/**
 * GEO 20210816 add
 *
 * 1999 外部 API 主項目資料
 */

public class Geo1999ItemsMainModel {
    String itemId;
    String itemName;
    List<Geo1999ItemsSubsModel> subsList;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public List<Geo1999ItemsSubsModel> getSubsList() {
        return subsList;
    }

    public void setSubsList(List<Geo1999ItemsSubsModel> subsList) {
        this.subsList = subsList;
    }

}
