package gov.kcg.kgo.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import gov.kcg.kgo.model.KgoFunction;
import gov.kcg.kgo.model.KgoRoleFunction;


/**
 * 後台 - 系統選單dto.
 * 
 */
@Entity
public class KgoSysMenuDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="Seq")
	private Integer seq;

	@Column(name="FunctionId", length=50)
	private String functionId;
	
	@Column(name="F_Seq")
	private Integer fSeq;

	@Column(name="Name")
	private String name;

	@Column(name="OrderNum")
	private Integer orderNum;

	@Column(name="Url", length=300)
	private String url;


	public KgoSysMenuDto() {
	}
	
	public KgoSysMenuDto(KgoRoleFunction krf, KgoFunction kf) {
		// this.roleId = krf.getId().getRoleId();
		this.functionId = kf.getFunctionId();
		this.seq = kf.getSeq();
		this.fSeq = kf.getfSeq();
		this.name = kf.getName();
		this.orderNum = kf.getOrderNum();
		this.url = kf.getUrl();
	}

	public Integer getfSeq() {
		return fSeq;
	}

	public void setfSeq(Integer fSeq) {
		this.fSeq = fSeq;
	}

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fSeq == null) ? 0 : fSeq.hashCode());
		result = prime * result + ((functionId == null) ? 0 : functionId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((orderNum == null) ? 0 : orderNum.hashCode());
		result = prime * result + ((seq == null) ? 0 : seq.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KgoSysMenuDto other = (KgoSysMenuDto) obj;
		if (fSeq == null) {
			if (other.fSeq != null)
				return false;
		} else if (!fSeq.equals(other.fSeq))
			return false;
		if (functionId == null) {
			if (other.functionId != null)
				return false;
		} else if (!functionId.equals(other.functionId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (orderNum == null) {
			if (other.orderNum != null)
				return false;
		} else if (!orderNum.equals(other.orderNum))
			return false;
		if (seq == null) {
			if (other.seq != null)
				return false;
		} else if (!seq.equals(other.seq))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

}