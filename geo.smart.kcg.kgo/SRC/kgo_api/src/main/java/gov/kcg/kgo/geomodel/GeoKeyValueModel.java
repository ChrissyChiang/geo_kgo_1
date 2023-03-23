package gov.kcg.kgo.geomodel;

import org.springframework.stereotype.Component;

/**
 * GEO 20210829 add
 *
 * Model for 共用 key-value 組合物件
 */
@Component
public class GeoKeyValueModel {
    String keyData;
    String valueData;

    public String getKeyData() {
        return keyData;
    }

    public void setKeyData(String keyData) {
        this.keyData = keyData;
    }

    public String getValueData() {
        return valueData;
    }

    public void setValueData(String valueData) {
        this.valueData = valueData;
    }

}
