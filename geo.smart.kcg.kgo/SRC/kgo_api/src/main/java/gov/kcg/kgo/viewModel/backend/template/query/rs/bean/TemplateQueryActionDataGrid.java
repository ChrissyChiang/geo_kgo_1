package gov.kcg.kgo.viewModel.backend.template.query.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台表單維護– 查詢 data grid
 */
@ApiModel(description = "後台表單維護– 查詢 data grid")
public class TemplateQueryActionDataGrid {

	/** 序號 **/
	@ApiModelProperty(value = "序號")
	private Integer seq;

	/** 表單名稱 **/
	@ApiModelProperty(value = "表單名稱")
	private String name;

	/** 是否預設 **/
	@ApiModelProperty(value = "是否預設")
	private String isDefault;

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
}
