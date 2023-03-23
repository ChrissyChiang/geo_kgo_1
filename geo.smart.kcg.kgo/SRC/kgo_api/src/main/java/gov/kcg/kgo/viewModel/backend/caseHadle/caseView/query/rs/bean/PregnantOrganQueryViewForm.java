package gov.kcg.kgo.viewModel.backend.caseHadle.caseView.query.rs.bean;

import java.util.List;
import gov.kcg.kgo.geoentity.GeoKgoCasesetAllowOrgan;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "機關查詢 - 好孕行得通View Form")
public class PregnantOrganQueryViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "機關查詢 - 好孕行得通")
	private List<GeoKgoCasesetAllowOrgan> grid;
	
	private String myOrgan;
	private Boolean belongToKgo;
	public List<GeoKgoCasesetAllowOrgan> getGrid() {
		return grid;
	}
	public void setGrid(List<GeoKgoCasesetAllowOrgan> grid) {
		this.grid = grid;
	}
	public String getMyOrgan() {
		return myOrgan;
	}
	public void setMyOrgan(String myOrgan) {
		this.myOrgan = myOrgan;
	}
	public Boolean getBelongToKgo() {
		return belongToKgo;
	}
	public void setBelongToKgo(Boolean belongToKgo) {
		this.belongToKgo = belongToKgo;
	}


}
