package gov.kcg.kgo.viewModel.backend.hotSearch.saveAll.rq;

import java.util.List;

import gov.kcg.kgo.viewModel.backend.hotSearch.saveAll.rs.bean.HotSearchSaveDataGrid;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "熱門搜尋-整頁儲存 rq")
public class HotSearchSaveAllRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "熱門搜尋資料集合")
	private List<HotSearchSaveDataGrid> grid;

	public List<HotSearchSaveDataGrid> getGrid() {
		return grid;
	}

	public void setGrid(List<HotSearchSaveDataGrid> grid) {
		this.grid = grid;
	}
}
