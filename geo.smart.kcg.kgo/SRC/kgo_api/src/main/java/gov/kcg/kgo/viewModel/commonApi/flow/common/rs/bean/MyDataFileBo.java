package gov.kcg.kgo.viewModel.commonApi.flow.common.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * myData資料集物件.
 */
@ApiModel(description = "myData資料集物件")
public class MyDataFileBo {

	@ApiModelProperty(value = "檔案名稱", position=1, example="xxx.csv")
	private String fileName;

	// PDF、CSV file Base64 String
	@ApiModelProperty(value = "File base64 String", position=2, example="(base64 string)")
	private String fileBase64Str;

	public MyDataFileBo(String fileName, String fileBase64Str) {
		this.fileName = fileName;
		this.fileBase64Str = fileBase64Str;
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
