package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

/**
 * GEO 20211030 add
 * 後台-案件稽核管理:案件查詢 Model
 */
@ApiModel(description = "後台-案件稽核管理:案件查詢 Model")
public class GeoCaseInspectDataModel extends BaseViewForm {

    @ApiModelProperty(notes = "案件ID")
    private String caseId;

    @ApiModelProperty(notes = "申請日期")
    private String applyDate;

    @ApiModelProperty(notes = "申請人")
    private String applyUser;

    @ApiModelProperty(notes = "案件名稱")
    private String caseSetName;

    @ApiModelProperty(notes = "限辦日期")
    private String deadlineDate;

    @ApiModelProperty(notes = "案件狀態")
    private String status;

    @ApiModelProperty(notes = "承辦人")
    private String caseOfficer;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        if (caseId == null) caseId = StringUtils.EMPTY;
        this.caseId = caseId;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        if (applyUser == null) applyUser = StringUtils.EMPTY;
        this.applyUser = applyUser;
    }

    public String getCaseSetName() {
        return caseSetName;
    }

    public void setCaseSetName(String caseSetName) {
        if (caseSetName == null) caseSetName = StringUtils.EMPTY;
        this.caseSetName = caseSetName;
    }

    public String getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(String deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status == null) status = StringUtils.EMPTY;
        this.status = status;
    }

    public String getCaseOfficer() {
        return caseOfficer;
    }

    public void setCaseOfficer(String caseOfficer) {
        if (caseOfficer == null) caseOfficer = StringUtils.EMPTY;
        this.caseOfficer = caseOfficer;
    }
}
