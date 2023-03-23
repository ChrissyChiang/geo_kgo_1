package gov.kcg.kgo.viewModel.backend.kgoCaselog.query.rs.bean;

import java.util.ArrayList;
import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台 - 案件軌跡統計 - 查詢 ViewForm
 */
@ApiModel(description = "後台 - 軌跡紀錄 - 查詢  ViewForm")
public class KgoCaseLogQueryActionViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="進案時間統計")
	private EntryCase entryCase = new EntryCase();
	
	@ApiModelProperty(value="承辦人受理案件數")
	private List<CasesAccCount> casesAccCountList = new ArrayList<>();
	
	@ApiModelProperty(value="案件狀態統計分析")
	private List<CasesStatusCount> casesStatusCountList = new ArrayList<>();
	
	@ApiModelProperty(value="每日結案統計分析")
	private List<CasesFinishCount> finishCountList = new ArrayList<>();


	public EntryCase getEntryCase() {
		return entryCase;
	}

	public void setEntryCase(EntryCase entryCase) {
		this.entryCase = entryCase;
	}

	public List<CasesAccCount> getCasesAccCountList() {
		return casesAccCountList;
	}

	public void setCasesAccCountList(List<CasesAccCount> casesAccCountList) {
		this.casesAccCountList = casesAccCountList;
	}

	public List<CasesStatusCount> getCasesStatusCountList() {
		return casesStatusCountList;
	}

	public void setCasesStatusCountList(List<CasesStatusCount> casesStatusCountList) {
		this.casesStatusCountList = casesStatusCountList;
	}

	public List<CasesFinishCount> getFinishCountList() {
		return finishCountList;
	}

	public void setFinishCountList(List<CasesFinishCount> finishCountList) {
		this.finishCountList = finishCountList;
	}
}
