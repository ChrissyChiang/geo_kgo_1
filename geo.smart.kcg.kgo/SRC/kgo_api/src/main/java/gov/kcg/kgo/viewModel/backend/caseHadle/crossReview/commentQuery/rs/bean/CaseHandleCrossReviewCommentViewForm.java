package gov.kcg.kgo.viewModel.backend.caseHadle.crossReview.commentQuery.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211115 add for 跨機關檢視
 * 後台案件處理-跨機關檢視-取得備註 view form
 */
@ApiModel(description = "後台案件處理-跨機關檢視-取得備註 view form")
public class CaseHandleCrossReviewCommentViewForm extends BaseViewForm {

    @ApiModelProperty(notes = "備註id，如回傳為空值，表示該人員尚未建立備註")
    private String crossReviewCommentId;

    @ApiModelProperty(notes = "服務id")
    private String caseId;

    @ApiModelProperty(notes = "機關id")
    private String organId;

    @ApiModelProperty(notes = "科室id")
    private String unitId;

    @ApiModelProperty(notes = "機關名稱")
    private String organName;

    @ApiModelProperty(notes = "科室名稱")
    private String unitName;

    @ApiModelProperty(notes = "填寫人員id")
    private String userId;

    @ApiModelProperty(notes = "備註內容")
    private String comment;

    @ApiModelProperty(value = "是否可編輯")
    private Boolean isEdit;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCrossReviewCommentId() {
        return crossReviewCommentId;
    }

    public void setCrossReviewCommentId(String crossReviewCommentId) {
        this.crossReviewCommentId = crossReviewCommentId;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getEdit() {
        return isEdit;
    }

    public void setEdit(Boolean edit) {
        isEdit = edit;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
