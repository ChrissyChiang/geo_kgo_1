package gov.kcg.kgo.viewModel.backend.caseHadle.pendingReview.correct.home.rq.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台案件處理-待審核匣-簽核-補正通知-案件集合
 */
@ApiModel(description = "後台案件處理-待審核匣-簽核-補正通知-案件集合")
public class CaseHandlePendingReviewCorrectUpdateDataGrid {

	@ApiModelProperty(value = "是否補正")
	private String isCorrect;

	@ApiModelProperty(value = "補正說明")
	private String correctMemo;

	@ApiModelProperty(value = "欄位編號")
	private String columnId;

	public String getIsCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(String isCorrect) {
		this.isCorrect = isCorrect;
	}

	public String getCorrectMemo() {
		return correctMemo;
	}

	public void setCorrectMemo(String correctMemo) {
		this.correctMemo = correctMemo;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

}
