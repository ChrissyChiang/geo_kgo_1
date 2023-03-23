package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;

@ApiModel(description = "後台 - 場地案件檢視 - 可預約時間Excel匯出 rq")
public class CaseHandleSiteRentableExcelRq  extends ApiRequest {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(notes = "機關編號")
    private String organId;
    @ApiModelProperty(notes = "科室編號")
    private String unitId;
    @ApiModelProperty(notes = "場地編號")
    private String siteMainId;
    @ApiModelProperty(notes = "服務編號")
    private String casesetId;
    @ApiModelProperty(notes = "服務名稱")
    private String casesetName;
    @ApiModelProperty(notes = "日期區間起始日: yyyyMMdd")
    private String periodFrom;
    @ApiModelProperty(notes = "日期區間終止日: yyyyMMdd")
    private String periodTo;
    @ApiModelProperty(notes = "預約類型")
    private String siteType;

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getUnitId() {return unitId;}

    public void setUnitId(String unitId) {this.unitId = unitId;}

    public String getSiteMainId() {return siteMainId;}

    public void setSiteMainId(String siteMainId) {this.siteMainId = siteMainId;}

    public String getCasesetId() {return casesetId;}

    public void setCasesetId(String casesetId) {this.casesetId = casesetId;}

    public String getCasesetName() {return casesetName;}

    public void setCasesetName(String casesetName) {this.casesetName = casesetName;}

    public String getPeriodFrom() {
        return periodFrom;
    }

    public void setPeriodFrom(String periodFrom) {
        this.periodFrom = periodFrom;
    }

    public String getPeriodTo() {
        return periodTo;
    }

    public void setPeriodTo(String periodTo) {
        this.periodTo = periodTo;
    }

    public String getSiteType() {return siteType;}

    public void setSiteType(String siteType) {this.siteType = siteType;}
}
