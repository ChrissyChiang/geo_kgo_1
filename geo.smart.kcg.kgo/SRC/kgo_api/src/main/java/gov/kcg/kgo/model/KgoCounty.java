package gov.kcg.kgo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the KGO_COUNTY database table.
 * 
 */
@Entity
@Table(name = "KGO_COUNTY")
@NamedQuery(name = "KgoCounty.findAll", query = "SELECT k FROM KgoCounty k")
public class KgoCounty implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CountyId", unique = true, nullable = false, length = 30)
	private String countyId;

	@Column(name = "CountyName")
	private String countyName;

	@Column(name = "Sort")
	private Integer sort;

	public KgoCounty() {
	}

	public String getCountyId() {
		return this.countyId;
	}

	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}

	public String getCountyName() {
		return this.countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}