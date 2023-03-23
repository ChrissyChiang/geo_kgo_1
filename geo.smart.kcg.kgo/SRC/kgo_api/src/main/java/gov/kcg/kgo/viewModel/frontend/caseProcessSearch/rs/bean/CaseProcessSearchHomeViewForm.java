package gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class CaseProcessSearchHomeViewForm extends BaseViewForm {

    /** 狀態下拉選單列表 **/
    @ApiModelProperty(value = "狀態下拉選單列表")
    private List<CaseMainStatus> caseMainStatusList;

    public List<CaseMainStatus> getCaseMainStatusList() {
        return caseMainStatusList;
    }

    public void setCaseMainStatusList(List<CaseMainStatus> caseMainStatusList) {
        this.caseMainStatusList = caseMainStatusList;
    }
}
