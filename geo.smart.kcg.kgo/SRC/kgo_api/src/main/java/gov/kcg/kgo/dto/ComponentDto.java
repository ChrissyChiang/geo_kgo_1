package gov.kcg.kgo.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Embeddable
@ApiModel(description = "用於儲存 comboBox 的資料來源集合")
public class ComponentDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 標籤
	 */
	@ApiModelProperty(notes = "標籤")
	@Column(name = "Label")
	private String label;

	/**
	 * 單位代碼
	 */
	@ApiModelProperty(notes = "值")
	@Column(name = "Value")
	private String value;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
