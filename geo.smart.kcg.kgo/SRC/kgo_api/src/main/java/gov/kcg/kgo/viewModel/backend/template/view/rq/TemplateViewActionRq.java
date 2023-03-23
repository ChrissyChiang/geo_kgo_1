package gov.kcg.kgo.viewModel.backend.template.view.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台表單維護– 明細 rq")
public class TemplateViewActionRq extends ApiRequest {

    @ApiModelProperty(value = "序號")
    private Integer seq;

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}
