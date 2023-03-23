package gov.kcg.kgo.viewModel.backend.caseManagement.managerComboBox.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 服務案件管理-服務管理者下拉式選單查詢 rq
 */
@ApiModel(description = "服務案件管理-服務管理者下拉式選單查詢 rq")
public class CaseManagerComboBoxQueryRq extends ApiRequest {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "機關代碼")
    private String organId;

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }
}
