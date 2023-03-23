package gov.kcg.kgo.geomodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * GEO 20211115 add for 民政局五種服務轉成B流程
 * 兵役類案件 model
 */
@ApiModel(description = "兵役類案件 model")
public class GeoCivilAffairsDtoModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "案件ID")
    private String caseId;

    @ApiModelProperty(notes = "服務ID")
    private String CaseSetId;

    @ApiModelProperty(notes = "服務名稱")
    private String caseSetName;

    @ApiModelProperty(notes = "建立日期")
    private String applyDate;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCaseSetId() {
        return CaseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        CaseSetId = caseSetId;
    }

    public String getCaseSetName() {
        return caseSetName;
    }

    public void setCaseSetName(String caseSetName) {
        this.caseSetName = caseSetName;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }
}
