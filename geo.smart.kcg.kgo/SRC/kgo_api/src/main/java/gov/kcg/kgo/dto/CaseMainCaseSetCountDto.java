package gov.kcg.kgo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 承辦人受理案件數dto.
 */
@Entity
public class CaseMainCaseSetCountDto {
	
	/** 服務編號 */
	@Id
	@Column(name = "CaseSetId")
	private String caseSetId;

	/** 服務名稱 */
	@Column(name = "CaseSetName")
	private String caseSetName;

	/** 數量 */
	@Column(name = "Count")
	private Integer count;


	public CaseMainCaseSetCountDto() {
	}
	
	public CaseMainCaseSetCountDto(String caseSetId, String caseSetName, Long count) {
		this.caseSetId = caseSetId;
		this.caseSetName = caseSetName;
		this.count = count == null ? 0 : count.intValue();
	}

	public String getCaseSetName() {
		return caseSetName;
	}

	public void setCaseSetName(String caseSetName) {
		this.caseSetName = caseSetName;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
