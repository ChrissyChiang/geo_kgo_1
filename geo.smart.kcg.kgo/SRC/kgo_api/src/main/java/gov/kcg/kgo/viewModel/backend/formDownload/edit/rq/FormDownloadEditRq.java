package gov.kcg.kgo.viewModel.backend.formDownload.edit.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "表單下載-編輯 rq")
public class FormDownloadEditRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "案件種類ID")
	private String caseSetId;

	@ApiModelProperty(value = "序號")
	private Integer seq;

	@ApiModelProperty(value = "分類")
	private String type;

	@ApiModelProperty(value = "標題")
	private String title;

	@ApiModelProperty(value = "附件")
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
