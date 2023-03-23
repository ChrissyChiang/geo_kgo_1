package gov.kcg.kgo.viewModel.backend.auth.getSysMenu.rs.bean;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 功能選單.
 */
@ApiModel(description = "功能選單")
public class SysMenu implements Serializable {

	private static final long serialVersionUID = -3003984525955769124L;

	/** 功能序號. */
	@ApiModelProperty(value = "功能序號")
	private Integer seq;
	
	/** 功能名稱. */
	@ApiModelProperty(value = "功能名稱")
	private String name;
	
	/** 功能代碼. */
	@ApiModelProperty(value = "功能代碼")
	private String functionId;


	/** 功能連結. */
	@ApiModelProperty(value = "功能連結")
	private String url;

	/** 父功能序號. */
	@ApiModelProperty(value = "父功能序號")
	private Integer fSeq;
	
	/** 排序. */
	@ApiModelProperty(value = "排序")
	private Integer OrderNum;
	
	/** 子選單.	*/
	@ApiModelProperty(value = "子選單")
	private List<SysMenu> childs = new LinkedList<SysMenu>();	
	
    
	public SysMenu() {
	}


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


	public String getFunctionId() {
		return functionId;
	}


	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public Integer getfSeq() {
		return fSeq;
	}


	public void setfSeq(Integer fSeq) {
		this.fSeq = fSeq;
	}


	public Integer getOrderNum() {
		return OrderNum;
	}


	public void setOrderNum(Integer orderNum) {
		OrderNum = orderNum;
	}


	public List<SysMenu> getChilds() {
		return childs;
	}


	public void setChilds(List<SysMenu> childs) {
		this.childs = childs;
	}
}
