package gov.kcg.kgo.geoviewmodel.frontend.rs.bean;

import gov.kcg.kgo.geomodel.GeoCaseSetListDtoModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "提供智能客服B流程案件服務清單  ViewForm")
public class GeoCaseSetListViewForm extends BaseViewForm {

    /** 查詢結果清單 **/
    @ApiModelProperty(value = "列出符合關鍵字的案件清單")
    private List<GeoCaseSetListDtoModel> caseSetList;

    public List<GeoCaseSetListDtoModel> getCaseSetList() {
        return caseSetList;
    }

    public void setCaseSetList(List<GeoCaseSetListDtoModel> caseSetList) {
        this.caseSetList = caseSetList;
    }

}
