package gov.kcg.kgo.viewModel.backend.caseHadle.crossReview.commentQuery.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台案件處理-結案通過之優惠折扣 view form
 */
@ApiModel(description = "後台案件處理-結案通過之優惠折扣 view form")
public class DiscountCountViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty(notes = "線上租借判斷欄位")
	private Integer rentalCase;

	@ApiModelProperty(notes = "原始價位")
    private Integer amount;
	@ApiModelProperty(notes = "依照天數折算退費比例(單位％)")
	private Integer deductPercent;

    @ApiModelProperty(notes = "優惠折扣比率")
    private ComboBox discountComboBox;

	@ApiModelProperty(notes = "提醒訊息")
	private String InfoMsg;

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public ComboBox getDiscountComboBox() {return discountComboBox;}

	public void setDiscountComboBox(ComboBox discountComboBox) {this.discountComboBox = discountComboBox;}

	public Integer getRentalCase() {return rentalCase;}

	public void setRentalCase(Integer rentalCase) {this.rentalCase = rentalCase;}

	public String getInfoMsg() {return InfoMsg;}

	public void setInfoMsg(String infoMsg) {InfoMsg = infoMsg;}

	public Integer getDeductPercent() {return deductPercent;}

	public void setDeductPercent(Integer deductPercent) {this.deductPercent = deductPercent;}
}
