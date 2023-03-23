package gov.kcg.kgo.geoviewmodel.frontend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="線上場地預約-月曆查詢")
public class CaseSetSearchDateRq extends ApiRequest {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="服務案件ID")
    private String caseSetId;
    @ApiModelProperty(value="場地ID")
    private String siteMainId;
    @ApiModelProperty(notes = "查詢帶當月1號 -年月字串 yyyy-MM-dd", required = true)
    private String dateStr;

    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

    public String getSiteMainId() {
        return siteMainId;
    }

    public void setSiteMainId(String siteMainId) {
        this.siteMainId = siteMainId;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }
}
