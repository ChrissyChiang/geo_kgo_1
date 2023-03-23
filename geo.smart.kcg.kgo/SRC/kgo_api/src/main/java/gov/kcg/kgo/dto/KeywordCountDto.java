package gov.kcg.kgo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "熱門搜尋-關鍵字統計")
@Entity
public class KeywordCountDto {

	@Id
	@ApiModelProperty(notes = "項次")
	@Column(name = "SEQ")
	private Integer seq;

	@ApiModelProperty(notes = "關鍵字")
	@Column(name = "KEYWORD")
	private String keyword;

	/** 統計數量 */
	@ApiModelProperty(notes = "統計數量")
	@Column(name = "STATISTICS")
	private String statistics;

	public KeywordCountDto() {
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getStatistics() {
		return statistics;
	}

	public void setStatistics(String statistics) {
		this.statistics = statistics;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

}
