package gov.kcg.kgo.geoviewmodel.frontend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value ="線上場地租借 - 服務案件內場地下拉選單")
public class CaseSetSiteComboBoxRq extends ApiRequest {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value ="場地租借服務案件編號")
    private String caseSetId ;
    @ApiModelProperty(value ="下拉選項是否附加全部")
    private Boolean findAll;

    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

    public Boolean getFindAll() {
        return findAll;
    }

    public void setFindAll(Boolean findAll) {
        this.findAll = findAll;
    }
}
