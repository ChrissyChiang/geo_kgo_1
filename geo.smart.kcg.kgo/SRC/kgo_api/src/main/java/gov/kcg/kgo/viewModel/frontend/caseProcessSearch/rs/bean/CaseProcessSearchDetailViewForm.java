package gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rs.bean;

import gov.kcg.kgo.geomodel.CaseSetCategoryGrid;
import gov.kcg.kgo.viewModel.backend.caseHadle.base.rs.bean.CaseHandleCaseViewCaseHistoryDataGrid;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class CaseProcessSearchDetailViewForm extends BaseViewForm {

    /** 案件主檔 **/
    @ApiModelProperty(value = "案件主檔")
    private CaseProcessSearchDetailCaseset caseProcessSearchDetailCaseset;

    /** 群組 **/
    @ApiModelProperty(value = "群組")
    private List<CaseProcessSearchDetailCasesetGroup> caseProcessSearchDetailCasesetGroups;

    /** 處理歷程 **/
    @ApiModelProperty(value = "處理歷程")
    private List<CaseHandleCaseViewCaseHistoryDataGrid> historyData;

    /** GEO 20211010 add for 1999 service **/
    /** 1999查詢連結網址 **/
    @ApiModelProperty(value = "1999查詢連結網址")
    private String search1999Url;

    /** GEO 20220118 add **/
    /** 機關審核表單是否顯示前台 **/
    @ApiModelProperty(value = "機關審核表單是否顯示前台")
    private Boolean isShow;

    private CaseSetCategoryGrid categoryGrid;

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

    public String getSearch1999Url() {
        return search1999Url;
    }

    public void setSearch1999Url(String search1999Url) {
        this.search1999Url = search1999Url;
    }

    public Boolean getShow() {
        return isShow;
    }

    public void setShow(Boolean show) {
        isShow = show;
    }

    public CaseSetCategoryGrid getCategoryGrid() {return categoryGrid;}

    public void setCategoryGrid(CaseSetCategoryGrid categoryGrid) {this.categoryGrid = categoryGrid;}
}
