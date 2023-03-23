package gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class CaseProcessSearchDetailCheckViewForm extends BaseViewForm {

    /** 檢核結果 **/
    @ApiModelProperty(value = "檢核結果")
    private Boolean result;

    public Boolean isResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }
}
