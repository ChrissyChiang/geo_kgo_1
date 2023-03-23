package gov.kcg.kgo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "分類維護-主畫面搜尋結果")
@Entity
public class ClassManagementClassQueryDto {
	@Id
	@Column(name = "SEQ")
	private Integer seq;
	
	/** 主類別代碼 */
	@ApiModelProperty(notes = "主類別代碼")
	@Column(name = "MAIN_TYPE")
	private String mainType;
	
	/** 次類別名稱 */
	@ApiModelProperty(notes = "次類別名稱")
	@Column(name = "DETAIL_NM")
	private String detailNM;

	/** 上下架時間 (yyyyMMdd) */
	@ApiModelProperty(notes = "上下架時間")
	@Column(name = "PUBLISH_TIME")
	private String publishTime;

	/** 上下架狀態 */
	@ApiModelProperty(notes = "上下架狀態")
	@Column(name = "STATE")
	private String state;
	
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getMainType() {
		return mainType;
	}

	public void setMainType(String mainType) {
		this.mainType = mainType;
	}

	public String getDetailNM() {
		return detailNM;
	}

	public void setDetailNM(String detailNM) {
		this.detailNM = detailNM;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
