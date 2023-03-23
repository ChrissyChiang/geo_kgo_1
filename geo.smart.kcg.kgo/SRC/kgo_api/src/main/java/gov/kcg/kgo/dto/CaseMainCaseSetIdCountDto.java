package gov.kcg.kgo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *  進案時間 前兩名dto.
 */
@Entity
public class CaseMainCaseSetIdCountDto {

	/** 服務編號 */
	@Id
	@Column(name = "CaseSetId")
	private String caseSetId;

	/** 數量 */
	@Column(name = "Count")
	private Integer count;


	public CaseMainCaseSetIdCountDto() {
	}
	
	public CaseMainCaseSetIdCountDto(String caseSetId, Long count) {
		this.caseSetId = caseSetId;
		this.count = count == null ? 0 : count.intValue();
	}

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
