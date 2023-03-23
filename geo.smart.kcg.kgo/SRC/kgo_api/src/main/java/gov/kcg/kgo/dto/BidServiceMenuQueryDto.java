package gov.kcg.kgo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "申辦服務選單-初始畫面-申辦案件數資料集合")
@Entity
public class BidServiceMenuQueryDto {

	@Id
	@ApiModelProperty(notes = "類別代碼")
	@Column(name = "VALUE")
	private String value;

	@ApiModelProperty(notes = "類別名稱")
	@Column(name = "NAME")
	private String name;

	@ApiModelProperty(notes = "顯示申辦案件數")
	@Column(name = "COUNT")
	private String count;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

}
