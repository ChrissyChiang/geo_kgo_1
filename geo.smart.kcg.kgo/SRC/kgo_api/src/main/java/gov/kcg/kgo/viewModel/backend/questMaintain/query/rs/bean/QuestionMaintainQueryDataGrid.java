package gov.kcg.kgo.viewModel.backend.questMaintain.query.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 常見問題維護-初始畫面
 */
@ApiModel(description = "系統公告-問題清單")
public class QuestionMaintainQueryDataGrid {

	/** 序號 */
	@ApiModelProperty(notes = "序號")
	private Integer seq;

	/** 問題 */
	@ApiModelProperty(notes = "問題")
	private String question;

	/** 狀態 */
	@ApiModelProperty(notes = "狀態")
	private String state;

	/** 上下架時間 */
	@ApiModelProperty(notes = "上下架時間")
	private String publishTime;

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

}
