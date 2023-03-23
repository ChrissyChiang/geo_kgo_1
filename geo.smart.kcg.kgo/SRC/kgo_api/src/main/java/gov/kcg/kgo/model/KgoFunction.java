package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the KGO_FUNCTION database table.
 * 
 */
@Entity
@Table(name="KGO_FUNCTION")
@NamedQuery(name="KgoFunction.findAll", query="SELECT k FROM KgoFunction k")
public class KgoFunction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Seq", unique=true, nullable=false)
	private Integer seq;

	@Column(name="F_Seq")
	private Integer fSeq;

	@Column(name="FunctionId", length=50)
	private String functionId;

	@Column(name="Name")
	private String name;

	@Column(name="OrderNum")
	private Integer orderNum;

	@Column(name="Url", length=300)
	private String url;

	public KgoFunction() {
	}


	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
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
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}