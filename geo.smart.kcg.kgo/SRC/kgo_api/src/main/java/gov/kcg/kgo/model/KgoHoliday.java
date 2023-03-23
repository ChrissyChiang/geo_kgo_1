package gov.kcg.kgo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the KGO_HOLIDAY database table.
 * 
 */
@Entity
@Table(name="KGO_HOLIDAY")
@NamedQuery(name="KgoHoliday.findAll", query="SELECT k FROM KgoHoliday k")
public class KgoHoliday implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Seq", unique=true, nullable=false)
	private int seq;

	@Column(name="HolidayDate")
	private Date holidayDate;

	@Column(name="Name")
	private String name;

	public KgoHoliday() {
	}

	public int getSeq() {
		return this.seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public Date getHolidayDate() {
		return holidayDate;
	}

	public void setHolidayDate(Date holidayDate) {
		this.holidayDate = holidayDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}