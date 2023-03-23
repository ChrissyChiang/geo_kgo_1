package gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.file.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台案件處理-待審核匣-案件查詢 View Form
 */
@ApiModel(description = "後台案件處理-待審核匣-上傳附件 View Form")
public class CaseHandlePendingReviewUploadFileForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "待審核匣-附件Seq")
    private String seq;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

}
