package gov.kcg.kgo.viewModel.backend.taskMaintain.editHome.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
@ApiModel(description = "上傳檔案物件")
public class SiteMaintainEditHomeFileViewForm implements Serializable {


	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "檔案名稱")
	private String name;
	@ApiModelProperty(value = "檔案內容base64字串")
	private String base64Str = "";


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBase64Str() {
		return base64Str;
	}

	public void setBase64Str(String base64Str) {
		this.base64Str = base64Str;
	}

}
