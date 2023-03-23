package gov.kcg.kgo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *  案件狀態統計分析 top5 dto.
 */
@Entity
public class CaseMainCaseStatusIsLateCountDto {

    /** 狀態 */
	@Id
    @Column(name = "Status")
    private String status;

	/** 數量 */
	@Column(name = "Count")
	private Integer count;


	public CaseMainCaseStatusIsLateCountDto() {
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
