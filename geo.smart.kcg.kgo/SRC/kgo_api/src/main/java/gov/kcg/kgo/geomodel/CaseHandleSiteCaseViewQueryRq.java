package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.query.rq.CaseHandleCaseViewQueryRq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台案件處理-場地案件檢視-初始畫面 rq")
public class CaseHandleSiteCaseViewQueryRq extends CaseHandleCaseViewQueryRq {
    @ApiModelProperty(name="場地名稱")
    private String siteName;
    @ApiModelProperty(name="租借時間起")
    private String timeStart;
    @ApiModelProperty(name="租借時間迄")
    private String timeEnd;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }
}
