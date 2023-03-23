package gov.kcg.kgo.viewModel.frontend.caseform.rq.bean;

import io.swagger.annotations.ApiModelProperty;

public class SaveFileViewForm {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "附件檔案名稱")
	private String fileName;

	@ApiModelProperty(value = "附件檔案base64 endcode")
	private String fileBase64Str;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileBase64Str() {
		return fileBase64Str;
	}

	public void setFileBase64Str(String fileBase64Str) {
		this.fileBase64Str = fileBase64Str;
	}

}
