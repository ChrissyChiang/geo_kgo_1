package gov.kcg.kgo.viewModel.backend.organUnitManagement.editHome.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 機關科室管理-機關科室維護(新增/修改)初始畫面 View Form
 */
@ApiModel(description = "機關科室管理-機關科室維護(新增/修改)初始畫面 View Form")
public class OrganUnitManagementEditHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/**
	 * type comboBox (只有機關/科室兩個選項)
	 */
	@ApiModelProperty(value = "固定選項的comboBox元件")
	private ComboBox organUnitComboBox;

	/**
	 * 上層機關 comboBox
	 */
	@ApiModelProperty(value = "上層機關 comboBox元件")
	private ComboBox parentOrganComboBox;

	/**
	 * 機關 comboBox
	 */
	@ApiModelProperty(value = "機關comboBox元件")
	private ComboBox organComboBox;

	/** 機關代碼 **/
	@ApiModelProperty(value = "機關代碼")
	private String organId;

	/** 機關名稱 **/
	@ApiModelProperty(value = "機關名稱")
	private String organName;

	/** 單位代碼 **/
	@ApiModelProperty(value = "單位代碼")
	private String unitId;

	/** 單位名稱 **/
	@ApiModelProperty(value = "單位名稱")
	private String unitName;

	public ComboBox getOrganUnitComboBox() {
		return organUnitComboBox;
	}

	public ComboBox getParentOrganComboBox() {
		return parentOrganComboBox;
	}

	public void setParentOrganComboBox(ComboBox parentOrganComboBox) {
		this.parentOrganComboBox = parentOrganComboBox;
	}

	public ComboBox getOrganComboBox() {
		return organComboBox;
	}

	public void setOrganComboBox(ComboBox organComboBox) {
		this.organComboBox = organComboBox;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public void setOrganUnitComboBox(ComboBox organUnitComboBox) {
		this.organUnitComboBox = organUnitComboBox;
	}

}
