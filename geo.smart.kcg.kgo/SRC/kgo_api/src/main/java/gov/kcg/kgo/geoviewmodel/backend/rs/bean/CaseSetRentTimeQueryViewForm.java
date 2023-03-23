package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import java.util.List;
import gov.kcg.kgo.geomodel.GeokgoRentTimeInsertModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;

@ApiModel(description = "線上場地/活動租借-時段查詢 View Form")
public class CaseSetRentTimeQueryViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	private List<GeokgoRentTimeInsertModel> timeList;

	public List<GeokgoRentTimeInsertModel> getTimeList() {
		return timeList;
	}
	public void setTimeList(List<GeokgoRentTimeInsertModel> timeList) {
		this.timeList = timeList;
	}
}
