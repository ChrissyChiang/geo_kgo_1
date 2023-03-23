package gov.kcg.kgo.viewModel.frontend.bidInstruction.home.rs.bean;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 申辦說明頁-案件申辦說明詳細資料集合(書表下載)
 */
@ApiModel(description = "申辦說明頁-案件申辦說明詳細資料集合(書表下載L)")
public class BidInstructionApplyTypeLTypeDataGrid {

	/** 分類名稱 */
	@ApiModelProperty(notes = "分類名稱")
	private String typeName;

	/** 書表下載案件檔案資料集合 */
	@ApiModelProperty(notes = "書表下載案件檔案資料集合")
	List<BidInstructionApplyTypeLDetailDataGrid> grid;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List<BidInstructionApplyTypeLDetailDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<BidInstructionApplyTypeLDetailDataGrid> grid) {
		this.grid = grid;
	}

}
