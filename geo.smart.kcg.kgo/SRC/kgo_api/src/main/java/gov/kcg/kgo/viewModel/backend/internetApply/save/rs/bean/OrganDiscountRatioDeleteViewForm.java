package gov.kcg.kgo.viewModel.backend.internetApply.save.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModelProperty;

public class OrganDiscountRatioDeleteViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    /** 儲存執行結果 **/
    @ApiModelProperty(value = "儲存執行結果")
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
