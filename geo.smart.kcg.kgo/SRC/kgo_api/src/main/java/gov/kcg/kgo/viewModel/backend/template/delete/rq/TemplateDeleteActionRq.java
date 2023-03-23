package gov.kcg.kgo.viewModel.backend.template.delete.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台表單維護– 刪除 rq")
public class TemplateDeleteActionRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "代碼")
    private Integer seq;

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}
