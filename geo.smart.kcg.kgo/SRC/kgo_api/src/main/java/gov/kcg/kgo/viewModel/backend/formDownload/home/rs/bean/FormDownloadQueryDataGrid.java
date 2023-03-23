package gov.kcg.kgo.viewModel.backend.formDownload.home.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 表單下載-案件書表維護資料集合
 */
@ApiModel(description = "表單下載-案件書表維護資料集合")
public class FormDownloadQueryDataGrid {

	/** 序號 */
	@ApiModelProperty(notes = "序號")
	private Integer seq;

	/** 分類代碼 */
	@ApiModelProperty(notes = "分類代碼")
	private String type;

	/** 標題 */
	@ApiModelProperty(notes = "標題")
	private String title;

	/** 附件 */
	@ApiModelProperty(notes = "附件")
	private String fileName;

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
