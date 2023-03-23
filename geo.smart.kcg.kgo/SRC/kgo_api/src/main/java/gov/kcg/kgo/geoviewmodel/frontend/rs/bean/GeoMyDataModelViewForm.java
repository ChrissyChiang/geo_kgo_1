package gov.kcg.kgo.geoviewmodel.frontend.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20221020 add_Jim
 * MyData Model查詢 ViewForm
 */
@ApiModel(description = "MyData Model查詢 ViewForm")
public class GeoMyDataModelViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "MyData Model")
    private Integer model;

    public GeoMyDataModelViewForm(Integer model) {
        this.model = model;
    }

    public Integer getModel() {
        return model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }
}
