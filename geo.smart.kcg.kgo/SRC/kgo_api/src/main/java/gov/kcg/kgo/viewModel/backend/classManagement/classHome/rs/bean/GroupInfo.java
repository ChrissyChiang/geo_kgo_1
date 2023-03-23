package gov.kcg.kgo.viewModel.backend.classManagement.classHome.rs.bean;

import io.swagger.annotations.ApiModelProperty;

/**
 * 主次類別 combox 物件
 * 
 * @author TPIuser
 *
 */
public class GroupInfo {

	@ApiModelProperty(value = "序號")
	private Integer seq;

	@ApiModelProperty(value = "類別名稱")
	private String name;

	@ApiModelProperty(value = "類別")
	private String type;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
