package gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rq;

import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rq.bean.CaseProcessSearchDetailSaveColumn;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "案件進度-明細保存 rq")
public class CaseProcessSearchDetailSaveRq {

    @ApiModelProperty(value = "案件編號")
    private String caseId;

    private List<CaseProcessSearchDetailSaveColumn> caseProcessSearchDetailSaveColumnList;

    public List<CaseProcessSearchDetailSaveColumn> getCaseProcessSearchDetailSaveColumnList() {
        return caseProcessSearchDetailSaveColumnList;
    }

    public void setCaseProcessSearchDetailSaveColumnList(List<CaseProcessSearchDetailSaveColumn> caseProcessSearchDetailSaveColumnList) {
        this.caseProcessSearchDetailSaveColumnList = caseProcessSearchDetailSaveColumnList;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }
}
