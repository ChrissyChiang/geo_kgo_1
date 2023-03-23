package gov.kcg.kgo.viewModel.backend.caseHadle.caseView.queryOrganHome.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211109 add 機關審核表單
 * 後台案件處理-案件檢視-案件檢視-初始畫面-取得機關審核表單 rq
 */
@ApiModel(description = "後台案件處理-案件檢視-案件檢視-初始畫面-取得機關審核表單 rq")
public class CaseHandleCaseViewQueryOrganFormRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "案件id", required = true)
    private String caseId;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

}
