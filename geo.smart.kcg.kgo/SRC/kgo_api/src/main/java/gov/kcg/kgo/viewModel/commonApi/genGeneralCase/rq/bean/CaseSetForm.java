package gov.kcg.kgo.viewModel.commonApi.genGeneralCase.rq.bean;

import javax.persistence.Column;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 *【書表維護分類】DocType
D書證表單
T填寫範例
O其他說明
 * */
@ApiModel(description = "書表維護Model")
public class CaseSetForm {
	
	@ApiModelProperty(value = "標題")
	private String title;
	
	@ApiModelProperty(value = "書表維護分類, (D)書證表單 (T)填寫範例 (O)其他說明")
	private String type;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
