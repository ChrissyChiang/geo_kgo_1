package gov.kcg.kgo.viewModel.backend.internetApply.description.update.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 網路申辦-網路申請說明-資料更新
 */
@ApiModel(description = "網路申辦-網路申請說明-資料更新 rq")
public class InternetApplyDescriptionUpdateRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    /** 服務案件編號 */
    @ApiModelProperty(value = "服務案件編號")
    private String caseSetId;

    /** 序號 */
    @ApiModelProperty(value = "序號")
    private Integer seq;

    /** 標題 */
    @ApiModelProperty(value = "標題")
    private String title;

    /** 說明內容 */
    @ApiModelProperty(value = "說明內容")
    private String contentHtml;

    /** 檔案名稱 */
    @ApiModelProperty(value = "檔案名稱")
    private String fileName;

    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
