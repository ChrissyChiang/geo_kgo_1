package gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupHome.rs.bean;

import java.util.List;

import gov.kcg.kgo.dto.CasesetGroupQueryDataMaxVersionDto;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 網路申辦-表單設定-群組維護-初始畫面 View Form
 */
@ApiModel(description = "網路申辦-表單設定-群組維護-初始畫面 View Form")
public class InternetApplyFormSetGroupHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 案件表單設定群組資料集合 **/
	@ApiModelProperty(value = "案件表單設定群組資料集合")
	private List<CasesetGroupQueryDataMaxVersionDto> grid;

	/** 版號 **/
	@ApiModelProperty(value = "版號")
	private Integer verison;

	public List<CasesetGroupQueryDataMaxVersionDto> getGrid() {
		return grid;
	}

	public void setGrid(List<CasesetGroupQueryDataMaxVersionDto> grid) {
		this.grid = grid;
	}

	public Integer getVerison() {
		return verison;
	}

	public void setVerison(Integer verison) {
		this.verison = verison;
	}

}
