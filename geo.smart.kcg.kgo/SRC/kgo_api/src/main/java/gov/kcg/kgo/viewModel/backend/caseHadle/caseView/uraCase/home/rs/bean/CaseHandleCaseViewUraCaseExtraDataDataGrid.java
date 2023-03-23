package gov.kcg.kgo.viewModel.backend.caseHadle.caseView.uraCase.home.rs.bean;

import java.util.List;

import javax.persistence.Column;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台案件處理-案件檢視-服務申辦(URA)案件檢視-額外群組資料
 */
@ApiModel(description = "後台案件處理-案件檢視-服務申辦(URA)案件檢視-額外群組資料")
public class CaseHandleCaseViewUraCaseExtraDataDataGrid {

	/** 群組標題 */
	@ApiModelProperty(notes = "群組標題")
	@Column(name = "GROUP_NAME")
	private String groupName;

	/** 欄位型態 **/
	@ApiModelProperty(notes = "欄位型態")
	@Column(name = "COLUMN_TYPE")
	private List<CaseHandleCaseViewUraCaseExtraDataDetailDataGrid> columnData;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<CaseHandleCaseViewUraCaseExtraDataDetailDataGrid> getColumnData() {
		return columnData;
	}

	public void setColumnData(List<CaseHandleCaseViewUraCaseExtraDataDetailDataGrid> columnData) {
		this.columnData = columnData;
	}

}
