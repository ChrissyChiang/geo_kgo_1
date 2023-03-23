package gov.kcg.kgo.geoviewmodel.frontend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import gov.kcg.kgo.viewModel.frontend.caseform.rq.bean.SaveActionCColumnViewForm;
import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel(description = "step3 根據表單內容帶出額外資訊(可擴充)")
public class ApplyFormExInfoRq extends ApiRequest {
    private static final long serialVersionUID = 1L;

    private String casesetId;
    private List<SaveActionCColumnViewForm> columnData;

    public String getCasesetId() {
        return casesetId;
    }

    public void setCasesetId(String casesetId) {
        this.casesetId = casesetId;
    }

    public List<SaveActionCColumnViewForm> getColumnData() {
        return columnData;
    }

    public void setColumnData(List<SaveActionCColumnViewForm> columnData) {
        this.columnData = columnData;
    }
}
