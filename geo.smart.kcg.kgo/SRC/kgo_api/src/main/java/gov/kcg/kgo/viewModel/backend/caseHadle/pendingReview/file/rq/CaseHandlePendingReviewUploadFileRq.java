package gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.file.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;

@ApiModel(description = "後台案件處理-待審核匣-上傳附件 rq")
public class CaseHandlePendingReviewUploadFileRq extends ApiRequest {

    @ApiModelProperty(value = "案件編號")
    private String caseId;

    @ApiModelProperty(value = "標題")
    private String title;

    @ApiModelProperty(value = "檔案")
    private MultipartFile file;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

}
