package gov.kcg.kgo.viewModel.backend.thesaurusMaintain.query.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 常見詞庫維護-問題查詢 View Form
 */
@ApiModel(description = "常見詞庫維護-問題查詢 View Form")
public class ThesaurusMaintainQueryViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 詞彙搜尋結果清單 **/
	@ApiModelProperty(value = "詞彙搜尋結果清單")
	private List<ThesaurusMaintainQueryDataGrid> grid;

	public List<ThesaurusMaintainQueryDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<ThesaurusMaintainQueryDataGrid> grid) {
		this.grid = grid;
	}

}
