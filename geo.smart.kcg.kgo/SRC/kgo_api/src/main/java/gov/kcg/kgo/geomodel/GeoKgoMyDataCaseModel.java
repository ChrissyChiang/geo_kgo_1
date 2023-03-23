package gov.kcg.kgo.geomodel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * GEO 20221009 add_Jim MyData紀錄查詢
 * MyData紀錄查詢 model
 */
@ApiModel(description = "MyData紀錄查詢 model")
public class GeoKgoMyDataCaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "申請編號")
    private String caseId;

    @ApiModelProperty(notes = "申請名稱")
    private String caseSet;

    @ApiModelProperty(notes = "對應資料集")
    private List<String> serviceNameList;

    @ApiModelProperty(notes = "查詢時間")
    private Long queryTimestamp;

    @ApiModelProperty(notes = "取得時間")
    private Long responseTimestamp;

    @ApiModelProperty(notes = "IP位置")
    private String ip;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCaseSet() {
        return caseSet;
    }

    public void setCaseSet(String caseSet) {
        this.caseSet = caseSet;
    }

    public List<String> getServiceNameList() {
        return serviceNameList;
    }

    public void setServiceNameList(List<String> serviceNameList) {
        this.serviceNameList = serviceNameList;
    }

    public Long getQueryTimestamp() {
        return queryTimestamp;
    }

    public void setQueryTimestamp(Long queryTimestamp) {
        this.queryTimestamp = queryTimestamp;
    }

    public Long getResponseTimestamp() {
        return responseTimestamp;
    }

    public void setResponseTimestamp(Long responseTimestamp) {
        this.responseTimestamp = responseTimestamp;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(this);
    }
}
