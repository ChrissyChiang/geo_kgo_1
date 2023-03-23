package gov.kcg.kgo.viewModel.backend.thesaurusMaintain.editHome.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 常見詞庫維護-問題維護(新增/修改)初始畫面 View Form
 */
@ApiModel(description = "常見詞庫維護-問題維護(新增/修改)初始畫面 View Form")
public class ThesaurusMaintainEditHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 詞彙序號 **/
	@ApiModelProperty(value = "詞彙序號")
	private Integer seq;

	/** 詞彙名稱 **/
	@ApiModelProperty(value = "詞彙名稱")
	private String title;

	/** 詞彙內容 **/
	@ApiModelProperty(value = "詞彙內容")
	private String word;

	public Integer getSeq() {
		return seq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

}
