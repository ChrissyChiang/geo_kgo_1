package gov.kcg.kgo.viewModel.frontend.auth.getValidateCode.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "使用者資訊 ViewForm")
public class CityCardUserViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(notes = "")
	String sub = "";
	
	@ApiModelProperty(notes = "")
	String name = "";
	
	@ApiModelProperty(notes = "")
	String email = "";
	
	@ApiModelProperty(notes = "")
	String license = "";

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
	

}
