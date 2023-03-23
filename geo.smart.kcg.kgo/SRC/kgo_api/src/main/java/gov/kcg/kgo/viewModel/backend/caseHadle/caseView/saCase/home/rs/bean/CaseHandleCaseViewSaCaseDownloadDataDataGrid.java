package gov.kcg.kgo.viewModel.backend.caseHadle.caseView.saCase.home.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台案件處理-案件檢視-服務申辦(SA)案件檢視-附件資料-申請資料
 */
@ApiModel(description = "後台案件處理-案件檢視-服務申辦(SA)案件檢視-附件資料")
public class CaseHandleCaseViewSaCaseDownloadDataDataGrid {

	/** 流水號 */
	@ApiModelProperty(notes = "流水號")
	private String seq;

	/** 標題 */
	@ApiModelProperty(notes = "標題")
	private String title;

	/** 附件 */
	@ApiModelProperty(notes = "附件")
	private String fileName;

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

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}
}
