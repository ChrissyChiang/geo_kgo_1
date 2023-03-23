package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.geomodel.GeoKgoAgentModel;
import gov.kcg.kgo.geomodel.GeoKgoUserInfoModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211026 add
 * 後台-設定代理人:新增代理人 ViewForm
 */

@ApiModel(description = "後台-設定代理人:新增代理人 ViewForm")
public class GeoAgentInsertViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "代理資料")
    private GeoKgoAgentModel agent;

    public GeoKgoAgentModel getAgent() {
        return agent;
    }

    public void setAgent(GeoKgoAgentModel agent) {
        this.agent = agent;
    }
}
