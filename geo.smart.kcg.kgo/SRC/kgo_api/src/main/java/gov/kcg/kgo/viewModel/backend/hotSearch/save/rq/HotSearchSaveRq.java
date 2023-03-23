package gov.kcg.kgo.viewModel.backend.hotSearch.save.rq;

import java.util.List;

import gov.kcg.kgo.viewModel.backend.hotSearch.saveAll.rs.bean.HotSearchSaveDataGrid;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "熱門搜尋-整頁儲存 rq")
public class HotSearchSaveRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "序號")
	private Integer seq;

	@ApiModelProperty(value = "關鍵字")
	private String keyword;

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
