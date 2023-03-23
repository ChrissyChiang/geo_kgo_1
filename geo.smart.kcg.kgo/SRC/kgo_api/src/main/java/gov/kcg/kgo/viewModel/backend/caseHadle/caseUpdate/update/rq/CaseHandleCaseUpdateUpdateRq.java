package gov.kcg.kgo.viewModel.backend.caseHadle.caseUpdate.update.rq;

import gov.kcg.kgo.viewModel.backend.caseHadle.caseUpdate.update.rq.bean.CaseHandleCaseUpdateUpdateColumn;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;


@ApiModel(description = "後台案件處理-案件異動-變更承辦人 rq")
public class CaseHandleCaseUpdateUpdateRq  extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "變更承辦人資料")
    private List<CaseHandleCaseUpdateUpdateColumn> caseHandleCaseUpdateUpdateColumnList;

    public List<CaseHandleCaseUpdateUpdateColumn> getCaseHandleCaseUpdateUpdateColumnList() {
        return caseHandleCaseUpdateUpdateColumnList;
    }

    public void setCaseHandleCaseUpdateUpdateColumnList(List<CaseHandleCaseUpdateUpdateColumn> caseHandleCaseUpdateUpdateColumnList) {
        this.caseHandleCaseUpdateUpdateColumnList = caseHandleCaseUpdateUpdateColumnList;
    }
}