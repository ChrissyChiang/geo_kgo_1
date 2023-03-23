package gov.kcg.kgo.viewModel.backend.template.query.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "後台表單維護– 查詢 rq")
public class TemplateQueryActionRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "表單名稱")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
