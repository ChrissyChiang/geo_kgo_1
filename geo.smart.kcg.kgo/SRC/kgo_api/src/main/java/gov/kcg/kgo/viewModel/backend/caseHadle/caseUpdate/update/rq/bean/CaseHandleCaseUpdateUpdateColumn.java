package gov.kcg.kgo.viewModel.backend.caseHadle.caseUpdate.update.rq.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 後台案件處理-案件異動-變更承辦人-資料
 */
@ApiModel(description = "後台案件處理-案件異動-變更承辦人-資料")
public class CaseHandleCaseUpdateUpdateColumn {

    @ApiModelProperty(value = "案件編號")
    private String caseId;

    @ApiModelProperty(value = "承辦人")
    private String officer;

    @ApiModelProperty(value = "案件階段編號")
    private String taskId;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getOfficer() {
        return officer;
    }

    public void setOfficer(String officer) {
        this.officer = officer;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
