package gov.kcg.kgo.viewModel.frontend.bidInstruction.home.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 申辦說明頁-案件申辦說明詳細資料集合(臨櫃申請C&線上申請E)
 */
@ApiModel(description = "申辦說明頁-案件申辦說明詳細資料集合(臨櫃申請C&線上申請E)")
public class BidInstructionApplyTypeCEDetailDataGrid {

	/** 序號 */
	@ApiModelProperty(notes = "序號")
	private Integer seq;

	/** 標題 */
	@ApiModelProperty(notes = "標題")
	private String title;

	/** 內容 */
	@ApiModelProperty(notes = "內容")
	private String contentHtml;

    /** 附件 */
    @ApiModelProperty(notes = "附件")
    private String fileName;

	/** 是否可以下載 */
	@ApiModelProperty(notes = "是否可以下載")
	private Boolean canDownload;

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

	public Boolean getCanDownload() {
		return canDownload;
	}

	public void setCanDownload(Boolean canDownload) {
		this.canDownload = canDownload;
	}

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
