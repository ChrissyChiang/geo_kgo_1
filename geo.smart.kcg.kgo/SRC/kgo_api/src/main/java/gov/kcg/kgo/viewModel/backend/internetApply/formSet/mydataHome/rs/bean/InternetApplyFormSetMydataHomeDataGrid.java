package gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataHome.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 網路申辦-表單設定-MYDAYA維護-初始畫面-CASESET_MYDATA資料集
 */
@ApiModel(description = "網路申辦-表單設定-MYDAYA維護-初始畫面-CASESET_MYDATA資料集")
public class InternetApplyFormSetMydataHomeDataGrid {

	@ApiModelProperty(notes = "來源代碼")
	private String sType;

	@ApiModelProperty(notes = "來源名稱")
	private String sTypeName;

	/** MYDATA資料集代碼 */
	@ApiModelProperty(notes = "MYDATA資料集代碼")
	private String myDataId;

	/** MYDATA資料集名稱 */
	@ApiModelProperty(notes = "MYDATA資料集名稱")
	private String myDataName;

	/** 是否可刪除 */
	@ApiModelProperty(notes = "是否可刪除")
	private Boolean canDelete;

	public String getMyDataId() {
		return myDataId;
	}

	public void setMyDataId(String myDataId) {
		this.myDataId = myDataId;
	}

	public String getMyDataName() {
		return myDataName;
	}

	public void setMyDataName(String myDataName) {
		this.myDataName = myDataName;
	}

	public Boolean getCanDelete() {
		return canDelete;
	}

	public void setCanDelete(Boolean canDelete) {
		this.canDelete = canDelete;
	}

	public String getsType() {
		return sType;
	}

	public void setsType(String sType) {
		this.sType = sType;
	}

	public String getsTypeName() {
		return sTypeName;
	}

	public void setsTypeName(String sTypeName) {
		this.sTypeName = sTypeName;
	}

}
