package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;

@ApiModel("後台案件管理 - 變更繳費狀態 viewForm")
public class GeoCaseChangePaymentForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;
    private Integer RentalCase;
    private String CaseId;
    private String resultMsg;

    public Integer getRentalCase() {
        return RentalCase;
    }

    public void setRentalCase(Integer rentalCase) {
        RentalCase = rentalCase;
    }

    public String getCaseId() {
        return CaseId;
    }

    public void setCaseId(String caseId) {
        CaseId = caseId;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
