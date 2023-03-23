package gov.kcg.kgo.viewModel.base.bean;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
* DataGrid 共用object.
*/
@ApiModel(description = "dataGrid共通屬性")
public class BaseDataGrid implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** 序號 */
	@ApiModelProperty(value = "序號")
	private Integer oreder;
	
	/** 可否被修改 */
	@ApiModelProperty(value = "可否編輯")
	private Boolean editable;

	public Integer getOreder() {
		return oreder;
	}

	public void setOreder(Integer oreder) {
		this.oreder = oreder;
	}

	public Boolean getEditable() {
		return editable;
	}

	public Boolean isEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}
	
	

}
