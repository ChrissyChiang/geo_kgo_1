package gov.kcg.kgo.geoviewmodel.frontend.rs.bean;

import gov.kcg.kgo.geomodel.GeoCaseSetSearchDateModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

@ApiModel(value="線上場地租借-單月每日案件查詢 ViewForm")
public class CaseSetSearchDateViewForm extends BaseViewForm {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="查詢整月每日案件")
    List<GeoCaseSetSearchDateModel> DateModels;
    @ApiModelProperty(value="跨日群組集合")
    List<Map<String,String>> allDayGroup;
    public List<GeoCaseSetSearchDateModel> getDateModels() {
        return DateModels;
    }

    public void setDateModels(List<GeoCaseSetSearchDateModel> dateModels) {
        DateModels = dateModels;
    }

    public List<Map<String, String>> getAllDayGroup() {return allDayGroup;}

    public void setAllDayGroup(List<Map<String, String>> allDayGroup) {this.allDayGroup = allDayGroup;}
}
