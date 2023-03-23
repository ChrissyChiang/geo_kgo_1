package gov.kcg.kgo.viewModel.backend.internetApply.formSet.columnHome.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 網路申辦-表單設定-欄位維護-初始畫面 View Form
 */
@ApiModel(description = "網路申辦-表單設定-欄位維護-初始畫面 View Form")
public class InternetApplyFormSetColumnHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** MyData驗證值 **/
	@ApiModelProperty(value = "MyData驗證值")
	private String MyDataCheckValue;

	/** 欄位型態下拉式選單 **/
	@ApiModelProperty(value = "欄位型態下拉式選單")
	private ComboBox columnTypeComboBox;

	/** 附件類型下拉式選單(欄位類型為'附件'時使用) **/
	@ApiModelProperty(value = "附件類型下拉式選單(欄位類型為'附件'時使用)")
	private ComboBox fileTypeComboBox;

	/** 是否必填下拉式選單 **/
	@ApiModelProperty(value = "是否必填下拉式選單")
	private ComboBox isMustKeyComboBox;

	/** MYDATA資料集下拉式選單 **/
	@ApiModelProperty(value = "MYDATA資料集下拉式選單")
	private ComboBox myDataIdComboBox;

	/** MYDATA資料集下拉式選單 **/
	@ApiModelProperty(value = "MyData對應欄位下拉式選單")
	private ComboBox myDataColumnComboBox;

	/** MyData資料驗證設定下拉式選單 **/
	@ApiModelProperty(value = "MyData資料驗證設定下拉式選單")
	private ComboBox myDataCheckTypeComboBox;

	/** GEO 20211019 add 是否重複檢核下拉式選單 **/
	@ApiModelProperty(value = "是否重複檢核下拉式選單")
	private ComboBox isCheckFrequencyComboBox;

	/** GEO 20211102 add 是否欄位勾選下拉式選單 **/
	@ApiModelProperty(value = "是否欄位勾選下拉式選單")
	private ComboBox isFieldCheckComboBox;

	public String getMyDataCheckValue() {
		return MyDataCheckValue;
	}

	public void setMyDataCheckValue(String myDataCheckValue) {
		MyDataCheckValue = myDataCheckValue;
	}

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

	public ComboBox getMyDataIdComboBox() {
		return myDataIdComboBox;
	}

	public void setMyDataIdComboBox(ComboBox myDataIdComboBox) {
		this.myDataIdComboBox = myDataIdComboBox;
	}

	public ComboBox getMyDataColumnComboBox() {
		return myDataColumnComboBox;
	}

	public void setMyDataColumnComboBox(ComboBox myDataColumnComboBox) {
		this.myDataColumnComboBox = myDataColumnComboBox;
	}

	public ComboBox getMyDataCheckTypeComboBox() {
		return myDataCheckTypeComboBox;
	}

	public void setMyDataCheckTypeComboBox(ComboBox myDataCheckTypeComboBox) {
		this.myDataCheckTypeComboBox = myDataCheckTypeComboBox;
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
