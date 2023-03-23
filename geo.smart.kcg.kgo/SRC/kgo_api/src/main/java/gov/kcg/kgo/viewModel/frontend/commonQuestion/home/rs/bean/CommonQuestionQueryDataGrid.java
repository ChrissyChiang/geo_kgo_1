package gov.kcg.kgo.viewModel.frontend.commonQuestion.home.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 常見問題-已上架常見問題清單
 */
@ApiModel(description = "常見問題-已上架常見問題清單")
public class CommonQuestionQueryDataGrid {

	/** 序號 **/
	@ApiModelProperty(value = "序號")
	private Integer seq;

	/** 問題 **/
	@ApiModelProperty(value = "問題")
	private String question;

	/** 內容 **/
	@ApiModelProperty(value = "內容")
	private String content;
	
	public CommonQuestionQueryDataGrid(Integer seq, String question, String content) {
		this.seq = seq;
		this.question = question;
		this.content = content;
	}

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
