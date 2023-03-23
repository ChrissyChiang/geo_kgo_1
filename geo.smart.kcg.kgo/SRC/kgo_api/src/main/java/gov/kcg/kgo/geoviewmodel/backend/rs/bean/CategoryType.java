package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "服務類型分類")
public class CategoryType {
    @ApiModelProperty(notes = "服務類型名稱")
    private String typeName;
    @ApiModelProperty(notes = "服務類型值")
    private String typeValue;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }
}
