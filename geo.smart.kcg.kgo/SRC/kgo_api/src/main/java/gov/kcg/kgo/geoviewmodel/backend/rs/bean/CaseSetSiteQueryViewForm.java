package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

// Roy
@ApiModel(description = "服務場地查詢 View Form")
public class CaseSetSiteQueryViewForm extends BaseViewForm {
	private static final long serialVersionUID = 1L;
	
    @ApiModelProperty(notes = "服務場地查詢 ")
    private List<String> detailList;

	public List<String> getDetailList() {
		return detailList;
	}



	public void setDetailList(List<String> detailList) {
		this.detailList = detailList;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
