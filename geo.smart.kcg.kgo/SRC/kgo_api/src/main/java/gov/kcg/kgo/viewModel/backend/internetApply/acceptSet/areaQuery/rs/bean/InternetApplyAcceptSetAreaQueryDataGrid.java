package gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.areaQuery.rs.bean;

import javax.persistence.Column;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 網路申辦-受理機關設定-受理區機關查詢資料集合
 */
@ApiModel(description = "網路申辦-受理機關設定-受理區機關查詢資料集合")
public class InternetApplyAcceptSetAreaQueryDataGrid {

	/** 機關代碼 */
	@ApiModelProperty(notes = "機關代碼")
	@Column(name = "ORGAN")
	private String organ;

	/** 機關名稱 */
	@ApiModelProperty(notes = "機關名稱")
	@Column(name = "ORGAN_NAME")
	private String organName;

	/** 鄉鎮代碼 */
	@ApiModelProperty(notes = "鄉鎮代碼(選取多筆以,相串)")
	@Column(name = "ZIP")
	private String zip;

	/** 鄉鎮名稱 */
	@ApiModelProperty(notes = "鄉鎮名稱(選取多筆以,相串)")
	@Column(name = "ZIP_NAME")
	private String zipName;

	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getZipName() {
		return zipName;
	}

	public void setZipName(String zipName) {
		this.zipName = zipName;
	}

}
