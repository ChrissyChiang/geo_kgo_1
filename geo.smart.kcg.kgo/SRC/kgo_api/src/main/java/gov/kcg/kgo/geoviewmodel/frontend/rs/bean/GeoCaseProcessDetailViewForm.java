package gov.kcg.kgo.geoviewmodel.frontend.rs.bean;

import gov.kcg.kgo.viewModel.backend.caseHadle.base.rs.bean.CaseHandleCaseViewCaseHistoryDataGrid;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.bean.CaseProcessSearchDetailCaseset;
import gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.bean.CaseProcessSearchDetailCasesetGroup;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20220828 add for 智能客服
 * 案件進度查詢
 */

public class GeoCaseProcessDetailViewForm extends BaseViewForm {

    /** 案件主檔 **/
    @ApiModelProperty(value = "案件主檔")
    private CaseProcessSearchDetailCaseset caseProcessSearchDetailCaseset;

    /** 群組 **/
    @ApiModelProperty(value = "群組")
    private List<CaseProcessSearchDetailCasesetGroup> caseProcessSearchDetailCasesetGroups;

    /** 處理歷程 **/
    @ApiModelProperty(value = "處理歷程")
    private List<CaseHandleCaseViewCaseHistoryDataGrid> historyData;

    public CaseProcessSearchDetailCaseset getCaseProcessSearchDetailCaseset() {
        return caseProcessSearchDetailCaseset;
    }

    public void setCaseProcessSearchDetailCaseset(CaseProcessSearchDetailCaseset caseProcessSearchDetailCaseset) {
        this.caseProcessSearchDetailCaseset = caseProcessSearchDetailCaseset;
    }

    public List<CaseProcessSearchDetailCasesetGroup> getCaseProcessSearchDetailCasesetGroups() {
        return caseProcessSearchDetailCasesetGroups;
    }

    public void setCaseProcessSearchDetailCasesetGroups(List<CaseProcessSearchDetailCasesetGroup> caseProcessSearchDetailCasesetGroups) {
        this.caseProcessSearchDetailCasesetGroups = caseProcessSearchDetailCasesetGroups;
    }

    public List<CaseHandleCaseViewCaseHistoryDataGrid> getHistoryData() {
        return historyData;
    }

    public void setHistoryData(List<CaseHandleCaseViewCaseHistoryDataGrid> historyData) {
        this.historyData = historyData;
    }
}
