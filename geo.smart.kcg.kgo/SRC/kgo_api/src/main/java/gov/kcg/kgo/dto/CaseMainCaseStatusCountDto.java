package gov.kcg.kgo.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *  案件狀態統計分析 top10 dto.
 */
@Entity
public class CaseMainCaseStatusCountDto {

	/** 更新日期 */
	@Id
	@Column(name = "UpdateDate")
	private Date updateDate;

	/** 數量 */
	@Column(name = "Count")
	private Integer count;


	public CaseMainCaseStatusCountDto() {}


	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
