package gov.kcg.kgo.viewModel.backend.formDownload.upload.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 表單下載-範本上傳 View Form
 */
@ApiModel(description = "表單下載-範本上傳 View Form")
public class FormDownloadFileUploadViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 執行結果訊息 **/
	@ApiModelProperty(value = "執行結果訊息")
	private String msg;

	/** 附件檔案名稱 **/
	@ApiModelProperty(value = "附件檔案名稱")
	private String fileName;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
