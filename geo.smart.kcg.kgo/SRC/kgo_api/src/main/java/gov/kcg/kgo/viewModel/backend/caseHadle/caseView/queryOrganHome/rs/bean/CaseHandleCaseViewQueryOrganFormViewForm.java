package gov.kcg.kgo.viewModel.backend.caseHadle.caseView.queryOrganHome.rs.bean;

import gov.kcg.kgo.viewModel.backend.internetApply.formSet.home.rs.bean.InternetApplyFormSetHomeOrganDataGrid;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.frontend.caseform.rs.bean.GroupViewForm;
import gov.kcg.kgo.viewModel.frontend.caseform.rs.bean.OptionViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20211109 add 機關審核表單
 * 後台案件處理-案件檢視-案件檢視-初始畫面-取得機關審核表單 View Form
 */
@ApiModel(description = "後台案件處理-案件檢視-案件檢視-初始畫面-取得機關審核表單 View Form")
public class CaseHandleCaseViewQueryOrganFormViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服務案件編號")
    private String caseSetId;

    @ApiModelProperty(value = "表單群組&欄位版號")
    private Integer version;

    @ApiModelProperty(value = "機關審核表單表單群組&欄位版號")
    private Integer organFormVersion;

    @ApiModelProperty(value = "案件機關審核表單設定所有群組欄位資料集合")
    private List<InternetApplyFormSetHomeOrganDataGrid> organFormGrid;

    @ApiModelProperty(notes = "欄位資料")
    private List<OptionViewForm> options;

    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public List<InternetApplyFormSetHomeOrganDataGrid> getOrganFormGrid() {
        return organFormGrid;
    }

    public void setOrganFormGrid(List<InternetApplyFormSetHomeOrganDataGrid> organFormGrid) {
        this.organFormGrid = organFormGrid;
    }

    public List<OptionViewForm> getOptions() {
        return options;
    }

    public void setOptions(List<OptionViewForm> options) {
        this.options = options;
    }

    public Integer getOrganFormVersion() {
        return organFormVersion;
    }

    public void setOrganFormVersion(Integer organFormVersion) {
        this.organFormVersion = organFormVersion;
    }
}
