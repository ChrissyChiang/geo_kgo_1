package gov.kcg.kgo.viewModel.backend.caseHadle.caseUpdate.query.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 後台案件處理-案件異動-案件查詢 rq
 */
@ApiModel(description = "後台案件處理-案件異動-案件查詢 rq")
public class CaseHandleCaseUpdateQueryRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "案件編號")
    private String caseId;

    @ApiModelProperty(value = "承辦人")
    private String officer;

    @ApiModelProperty(value = "申請時間起迄日(yyyymmdd)")
    private List<String> applyDate;

    @ApiModelProperty(value = "服務案件名稱")
    private String caseName;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getOfficer() {
        return officer;
    }

    public void setOfficer(String officer) {
        this.officer = officer;
    }

    public List<String> getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(List<String> applyDate) {
        this.applyDate = applyDate;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }
}
