package gov.kcg.kgo.viewModel.frontend.bidInstruction.home.rs.bean;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 申辦說明頁-案件申辦說明資料集合(書表L)
 */
@ApiModel(description = "申辦說明頁-案件申辦說明資料集合(書表L)")
public class BidInstructionApplyTypeLData {

	/** 申辦類型 */
	@ApiModelProperty(notes = "申辦類型")
	private String applyType;

	/** 案件申辦說明詳細資料集合 **/
	@ApiModelProperty(value = "案件申辦說明詳細資料集合")
	private List<BidInstructionApplyTypeLTypeDataGrid> grid;

	public BidInstructionApplyTypeLData(String applyType) {
		this.applyType = applyType;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public List<BidInstructionApplyTypeLTypeDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<BidInstructionApplyTypeLTypeDataGrid> grid) {
		this.grid = grid;
	}

}
