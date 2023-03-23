package gov.kcg.kgo.geomodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("案件查詢 - 服務類型相關資料")
public class CaseSetCategoryGrid {

    @ApiModelProperty(notes ="服務案件類型")
    private String category;

    public String getCategory() {return category;}

    public void setCategory(String category) {this.category = category;}
}
