package gov.kcg.kgo.viewModel.backend.counterApply.home.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *臨櫃申請資料集合
 */
@ApiModel(description = "臨櫃申請資料集合")
public class CounterApplyHomeDataGrid {

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
