package gov.kcg.kgo.viewModel.backend.questMaintain.editHome.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 常見問題維護-問題維護(新增/修改)初始畫面 View Form
 */
@ApiModel(description = "常見問題維護-問題維護(新增/修改)初始畫面 View Form")
public class QuestionMaintainEditHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 問題序號 **/
	@ApiModelProperty(value = "問題序號")
	private Integer seq;

	/** 問題 **/
	@ApiModelProperty(value = "問題")
	private String qestion;

	/** "問題內容 **/
	@ApiModelProperty(value = "問題內容")
	private String content;

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getQestion() {
		return qestion;
	}

	public void setQestion(String qestion) {
		this.qestion = qestion;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
