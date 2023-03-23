package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.geomodel.GeoKgoCasesetAssociateModel;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

@ApiModel(description = "後台-案件關聯服務:新增關聯案件 rq")
public class GeoCaseSetAssociateInsertRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "關聯案件清單",required = true)
	private List<GeoKgoCasesetAssociateModel> dataList;

	public List<GeoKgoCasesetAssociateModel> getDataList() {
		return dataList;
	}

	public void setDataList(List<GeoKgoCasesetAssociateModel> dataList) {
		this.dataList = dataList;
	}
}
