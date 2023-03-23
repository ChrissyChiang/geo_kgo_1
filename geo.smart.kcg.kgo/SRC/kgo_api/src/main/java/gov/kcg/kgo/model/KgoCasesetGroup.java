package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the KGO_CASESET_GROUP database table.
 * 
 */
@Entity
@Table(name = "KGO_CASESET_GROUP")
@NamedQuery(name = "KgoCasesetGroup.findAll", query = "SELECT k FROM KgoCasesetGroup k")
public class KgoCasesetGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KgoCasesetGroupPK id;

	@Column(name = "Memo")
	private String memo;

	@Column(name = "OrderNum")
	private Integer orderNum;

	@Column(name="CheckFrequencyPeriod")
	private String checkFrequencyPeriod;

	public KgoCasesetGroup() {
	}

	public KgoCasesetGroupPK getId() {
		return this.id;
	}

	public void setId(KgoCasesetGroupPK id) {
		this.id = id;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getCheckFrequencyPeriod() {
		return checkFrequencyPeriod;
	}

	public void setCheckFrequencyPeriod(String checkFrequencyPeriod) {
		this.checkFrequencyPeriod = checkFrequencyPeriod;
	}
}