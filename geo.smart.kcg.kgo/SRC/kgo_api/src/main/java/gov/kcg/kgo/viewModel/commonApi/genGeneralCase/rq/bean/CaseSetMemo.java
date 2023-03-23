package gov.kcg.kgo.viewModel.commonApi.genGeneralCase.rq.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "網路(E)、臨櫃(C) 申辦Model")
public class CaseSetMemo {
	
	@ApiModelProperty(value = "標題")
	private String title;

	@ApiModelProperty(value = "內容")
	private String content;

	@ApiModelProperty(value = "附件檔案名稱")
	private String fileName;
	
	@ApiModelProperty(value = "附件檔案base64 endcode")
	private String fileBase64Str;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

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
