package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211030 add
 * 後台-案件稽核管理:案件查詢 rq
 */
@ApiModel(description = "後台-案件稽核管理:案件查詢 rq")
public class GeoCaseInspectQueryRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "申辦類型 A-站外連結,B1-案件暫存區(入案通知),B2-案件暫存區(自行取案),B3-由本平台完整申辦")
    private String  CaseFlowType;

    @ApiModelProperty(notes = "申辦時間起始 yyyy-MM-dd")
    private String applyDateStart;

    @ApiModelProperty(notes = "申辦時間起始 yyyy-MM-dd")
    private String applyDateEnd;

    @ApiModelProperty(notes = "查核百分比, 1-100", required = true)
    private Integer percentage;

    @ApiModelProperty(notes = "服務名稱")
    private String caseSetName;

    @ApiModelProperty(notes = "機關id", hidden = true)
    private String ownerOrganId;

    @ApiModelProperty(notes = "服務id", hidden = true)
    private String caseSetId;

    public String getCaseFlowType() {
        return CaseFlowType;
    }

    public void setCaseFlowType(String caseFlowType) {
        CaseFlowType = caseFlowType;
    }

    public String getApplyDateStart() {
        return applyDateStart;
    }

    public void setApplyDateStart(String applyDateStart) {
        this.applyDateStart = applyDateStart;
    }

    public String getApplyDateEnd() {
        return applyDateEnd;
    }

    public void setApplyDateEnd(String applyDateEnd) {
        this.applyDateEnd = applyDateEnd;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public String getOwnerOrganId() {
        return ownerOrganId;
    }

    public void setOwnerOrganId(String ownerOrganId) {
        this.ownerOrganId = ownerOrganId;
    }

    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

    public String getCaseSetName() {
        return caseSetName;
    }

    public void setCaseSetName(String caseSetName) {
        this.caseSetName = caseSetName;
    }
}
