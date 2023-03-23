package gov.kcg.kgo.geomodel;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class GeokgoRentCaseSetModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "預約綁定場所案件id")
    private String caseRentId;
    @ApiModelProperty(notes = "服務案件id", required = true)
    private String caseSetId;
    @ApiModelProperty(notes = "場地/活動 服務物件id" , required = true)
    private String serviceId;
    @ApiModelProperty(notes = "場地/活動 預設費用")
    private Integer defaultPrice;


    public String getCaseRentId() {
        return caseRentId;
    }

    public void setCaseRentId(String caseRentId) {
        this.caseRentId = caseRentId;
    }

    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getDefaultPrice() {return defaultPrice;}

    public void setDefaultPrice(Integer defaultPrice) {this.defaultPrice = defaultPrice;}
}
