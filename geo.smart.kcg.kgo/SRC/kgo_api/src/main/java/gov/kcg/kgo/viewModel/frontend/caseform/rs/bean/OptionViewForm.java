package gov.kcg.kgo.viewModel.frontend.caseform.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.SelectListItem;
import io.swagger.annotations.ApiModelProperty;

public class OptionViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	public OptionViewForm() {
		super();
	}

	public OptionViewForm(String columnId, List<SelectListItem> options) {
		super();
		this.columnId = columnId;
		this.options = options;
	}

	@ApiModelProperty(notes = "欄位ID，固定ID(地址：縣市 addressCounty, 鄉鎮市區 addressZip 段小段: 區 landNumKcnt, 小段 landNumkrmk)")
	private String columnId;

	@ApiModelProperty(notes = "欄位資料")
	private List<SelectListItem> options;

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public List<SelectListItem> getOptions() {
		return options;
	}

	public void setOptions(List<SelectListItem> options) {
		this.options = options;
	}

}
