package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台-線上預約臨櫃-編輯：臨櫃預約表單設定欄位初始化
 */
@ApiModel(description = "後台-線上預約臨櫃-編輯：臨櫃預約表單設定欄位初始化")
public class GeoAppointmentApplyFormColumnHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "欄位型態下拉式選單")
	private ComboBox columnTypeComboBox;

	@ApiModelProperty(value = "附件類型下拉式選單(欄位類型為'附件'時使用)")
	private ComboBox fileTypeComboBox;

	@ApiModelProperty(value = "是否必填下拉式選單")
	private ComboBox isMustKeyComboBox;

	@ApiModelProperty(value = "是否重複檢核下拉式選單")
	private ComboBox isCheckFrequencyComboBox;

	@ApiModelProperty(value = "是否欄位勾選下拉式選單")
	private ComboBox isFieldCheckComboBox;

	public ComboBox getColumnTypeComboBox() {
		return columnTypeComboBox;
	}

	public void setColumnTypeComboBox(ComboBox columnTypeComboBox) {
		this.columnTypeComboBox = columnTypeComboBox;
	}

	public ComboBox getFileTypeComboBox() {
		return fileTypeComboBox;
	}

	public void setFileTypeComboBox(ComboBox fileTypeComboBox) {
		this.fileTypeComboBox = fileTypeComboBox;
	}

	public ComboBox getIsMustKeyComboBox() {
		return isMustKeyComboBox;
	}

	public void setIsMustKeyComboBox(ComboBox isMustKeyComboBox) {
		this.isMustKeyComboBox = isMustKeyComboBox;
	}

	public ComboBox getIsCheckFrequencyComboBox() {
		return isCheckFrequencyComboBox;
	}

	public void setIsCheckFrequencyComboBox(ComboBox isCheckFrequencyComboBox) {
		this.isCheckFrequencyComboBox = isCheckFrequencyComboBox;
	}

	public ComboBox getIsFieldCheckComboBox() {
		return isFieldCheckComboBox;
	}

	public void setIsFieldCheckComboBox(ComboBox isFieldCheckComboBox) {
		this.isFieldCheckComboBox = isFieldCheckComboBox;
	}
}
