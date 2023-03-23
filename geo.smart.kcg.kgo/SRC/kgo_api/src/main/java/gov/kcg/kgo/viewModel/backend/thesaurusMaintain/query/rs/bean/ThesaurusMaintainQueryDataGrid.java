package gov.kcg.kgo.viewModel.backend.thesaurusMaintain.query.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 常見詞庫維護-問題查詢
 */
@ApiModel(description = "常見詞庫維護-問題查詢")
public class ThesaurusMaintainQueryDataGrid {

	/** 序號 */
	@ApiModelProperty(notes = "序號")
	private Integer seq;

	/** 詞彙名稱 */
	@ApiModelProperty(notes = "詞彙名稱")
	private String title;

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
