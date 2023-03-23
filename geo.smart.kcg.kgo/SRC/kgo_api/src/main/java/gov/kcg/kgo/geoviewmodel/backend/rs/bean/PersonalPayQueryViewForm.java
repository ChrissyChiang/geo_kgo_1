package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import java.util.List;

import gov.kcg.kgo.geomodel.PersonalPayModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

//roy
@ApiModel(description = "提供個人預約繳費資訊清單View Form")
public class PersonalPayQueryViewForm extends BaseViewForm {
	private static final long serialVersionUID = 1L;
	
    @ApiModelProperty(notes = "提供個人預約繳費資訊清單 ")
    private List<PersonalPayModel> detailList;
    
	public List<PersonalPayModel> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<PersonalPayModel> detailList) {
		this.detailList = detailList;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
