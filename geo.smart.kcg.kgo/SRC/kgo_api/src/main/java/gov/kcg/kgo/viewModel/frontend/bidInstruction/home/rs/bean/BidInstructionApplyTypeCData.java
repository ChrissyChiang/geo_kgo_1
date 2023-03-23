package gov.kcg.kgo.viewModel.frontend.bidInstruction.home.rs.bean;

import java.util.LinkedList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 申辦說明頁-案件申辦說明資料集合(臨櫃C)
 */
@ApiModel(description = "申辦說明頁-案件申辦說明資料集合(臨櫃C)")
public class BidInstructionApplyTypeCData {

	/** 申辦類型 */
	@ApiModelProperty(notes = "申辦類型")
	private String applyType;

	/** 案件申辦說明詳細資料集合 **/
	@ApiModelProperty(value = "案件申辦說明詳細資料集合")
	private List<BidInstructionApplyTypeCEDetailDataGrid> grid;

	public BidInstructionApplyTypeCData(String applyType) {
		this.applyType = applyType;
		grid = new LinkedList<BidInstructionApplyTypeCEDetailDataGrid>();
	}
	
	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public List<BidInstructionApplyTypeCEDetailDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<BidInstructionApplyTypeCEDetailDataGrid> grid) {
		this.grid = grid;
	}

}
