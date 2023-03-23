package gov.kcg.kgo.viewModel.backend.kgoCaselog.query.rs.bean;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台 - 軌跡紀錄 - 查詢 - 進案時間
 */
@ApiModel(description = "後台 - 軌跡紀錄 - 查詢 - 進案時間")
public class EntryCase {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "進案時間統計清單TOP1")
	List<EntryCaseCount> entryCaseTop1CountList;
	
	@ApiModelProperty(value = "進案時間統計清單TOP2")
	List<EntryCaseCount> entryCaseTop2CountList;

	public List<EntryCaseCount> getEntryCaseTop1CountList() {
		return entryCaseTop1CountList;
	}

	public void setEntryCaseTop1CountList(List<EntryCaseCount> entryCaseTop1CountList) {
		this.entryCaseTop1CountList = entryCaseTop1CountList;
	}

	public List<EntryCaseCount> getEntryCaseTop2CountList() {
		return entryCaseTop2CountList;
	}

	public void setEntryCaseTop2CountList(List<EntryCaseCount> entryCaseTop2CountList) {
		this.entryCaseTop2CountList = entryCaseTop2CountList;
	}

}
