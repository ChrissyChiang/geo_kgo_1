package gov.kcg.kgo.viewModel.backend.internetApply.description.home.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 網路申辦-網路申請說明-初始畫面
 */
@ApiModel(description = "網路申辦-網路申請說明-初始畫面")
public class InternetApplyDescriptionHomeDataGrid {

	/**
	 * 序號
	 */
	@ApiModelProperty(notes = "序號")
	private Integer seq;
	/**
	 * 標題
	 */
	@ApiModelProperty(notes = "標題")
	private String title;

	/**
	 * 說明內容
	 */
	@ApiModelProperty(notes = "說明內容")
	private String contentHtml;

	/**
	 * 附件
	 */
	@ApiModelProperty(notes = "附件")
	private String fileName;

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
