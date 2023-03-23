package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import java.util.List;

import gov.kcg.kgo.geomodel.GeoCaseSetSiteMainModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

// Roy
@ApiModel(description = "線上場地查詢 View Form")
public class GeoSiteQueryViewForm extends BaseViewForm {
	private static final long serialVersionUID = 1L;
	
    @ApiModelProperty(notes = "線上場地查詢 ")
    private List<GeoCaseSetSiteMainModel> detailList;
    
	public List<GeoCaseSetSiteMainModel> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<GeoCaseSetSiteMainModel> detailList) {
		this.detailList = detailList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
