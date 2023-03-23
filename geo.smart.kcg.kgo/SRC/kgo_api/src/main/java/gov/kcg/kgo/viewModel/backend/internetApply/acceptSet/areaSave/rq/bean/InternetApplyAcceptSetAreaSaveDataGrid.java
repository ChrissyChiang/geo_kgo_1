package gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.areaSave.rq.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 網路申辦-受理機關設定-受理區機關儲存資料集合
 */
@ApiModel(description = "網路申辦-受理機關設定-受理區機關儲存資料集合")
public class InternetApplyAcceptSetAreaSaveDataGrid {

	/** 機關代碼 */
	@ApiModelProperty(notes = "機關代碼")
	private String organ;

	/** 鄉鎮代碼 */
	@ApiModelProperty(notes = "鄉鎮代碼(選取多筆以,相串)")
	private String zip;

	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

}
