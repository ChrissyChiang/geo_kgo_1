package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20221110 add
 * <p>
 * 跨機關callback ViewForm
 */
@ApiModel(description = "跨機關callback ViewForm")
public class GeoCrossOrganResultViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "result")
    private Boolean result;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }
}
