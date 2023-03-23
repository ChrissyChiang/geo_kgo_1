package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "查詢服務案件清單-案件維護-儲存 rq")
public class GeoExCaseSaveRq extends ApiRequest {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服務案件編號")
    private String caseSetId;

    @ApiModelProperty(value = "案件名稱")
    private String caseSetName;

    @ApiModelProperty(value = "站外連結網址")
    private String linkUrl;

    @ApiModelProperty(value = "機關分類")
    private String organ;

    @ApiModelProperty(value = "角色分類")
    private String role;

    @ApiModelProperty(value = "服務分類")
    private String service;


    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

    public String getCaseSetName() {
        return caseSetName;
    }

    public void setCaseSetName(String caseSetName) {
        this.caseSetName = caseSetName;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return "GeoExCaseSaveRq{" +
                "caseSetId='" + caseSetId + '\'' +
                ", caseSetName='" + caseSetName + '\'' +
                ", linkUrl='" + linkUrl + '\'' +
                ", organ='" + organ + '\'' +
                ", role='" + role + '\'' +
                ", service='" + service + '\'' +
                '}';
    }
}
