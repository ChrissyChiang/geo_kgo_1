package gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.home.rs.bean;

import java.util.List;

import gov.kcg.kgo.dto.AcceptSetUnitQueryDto;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 網路申辦-受理機關設定-初始畫面 View Form
 */
@ApiModel(description = "網路申辦-受理機關設定-初始畫面 View Form")
public class InternetApplyAcceptSetHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 受理機關設定下拉式選單 **/
	@ApiModelProperty(value = "受理機關設定下拉式選單")
	private ComboBox acceptSetComboBox;

	/** 受理機關設定清單 **/
	@ApiModelProperty(value = "臨櫃申請清單")
	private List<AcceptSetUnitQueryDto> grid;

	public ComboBox getAcceptSetComboBox() {
		return acceptSetComboBox;
	}

	public void setAcceptSetComboBox(ComboBox acceptSetComboBox) {
		this.acceptSetComboBox = acceptSetComboBox;
	}

	public List<AcceptSetUnitQueryDto> getGrid() {
		return grid;
	}

	public void setGrid(List<AcceptSetUnitQueryDto> grid) {
		this.grid = grid;
	}

}
