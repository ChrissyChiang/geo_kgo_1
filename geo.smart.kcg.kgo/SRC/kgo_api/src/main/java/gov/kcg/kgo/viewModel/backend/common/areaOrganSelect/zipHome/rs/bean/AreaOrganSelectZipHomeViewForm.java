package gov.kcg.kgo.viewModel.backend.common.areaOrganSelect.zipHome.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.checkBox.CheckBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 共用-區機關選擇-鄉鎮選擇-初始畫面 View Form
 */
@ApiModel(description = "共用-區機關選擇-鄉鎮選擇-初始畫面 View Form")
public class AreaOrganSelectZipHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "鄉鎮 checkBox 清單")
	private List<CheckBox> zipCheckBox;

	public List<CheckBox> getZipCheckBox() {
		return zipCheckBox;
	}

	public void setZipCheckBox(List<CheckBox> zipCheckBox) {
		this.zipCheckBox = zipCheckBox;
	}

}
