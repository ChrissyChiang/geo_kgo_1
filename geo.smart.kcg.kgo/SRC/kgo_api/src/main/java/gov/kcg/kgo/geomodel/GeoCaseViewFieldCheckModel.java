package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.saCase.home.rs.bean.CaseHandleCaseViewSaCaseApplyDataDataGrid;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * GEO 20211102 add
 * 欄位勾選
 */

public class GeoCaseViewFieldCheckModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "欄位清單")
    List<CaseHandleCaseViewSaCaseApplyDataDataGrid> applyDataGrids;

    @ApiModelProperty(value = "案件id")
    private String caseId;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public List<CaseHandleCaseViewSaCaseApplyDataDataGrid> getApplyDataGrids() {
        return applyDataGrids;
    }

    public void setApplyDataGrids(List<CaseHandleCaseViewSaCaseApplyDataDataGrid> applyDataGrids) {
        this.applyDataGrids = applyDataGrids;
    }
}
