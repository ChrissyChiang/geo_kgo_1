package gov.kcg.kgo.viewModel.backend.paramSet.home.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 參數設定 - 畫面初始 ViewForm
 */
@ApiModel(description = "參數設定 - 畫面初始  ParamSetGrid")
public class ParamSetGrid extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="設定類別")
	String type;

	@ApiModelProperty(value="設定類別名稱")
	String typeName;
	
	@ApiModelProperty(value="設定類別對應型態(combox)")
	ComboBox combox;
	
	@ApiModelProperty(value="設定類別對應型值(String、combox)")
	String value;
	
	public ParamSetGrid() {}

	public ParamSetGrid(String type, String typeName, ComboBox combox, String value) {
		this.type = type;
		this.typeName = typeName;
		this.combox = combox;
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public ComboBox getCombox() {
		return combox;
	}

	public void setCombox(ComboBox combox) {
		this.combox = combox;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
