package gov.kcg.kgo.viewModel.mydata.vo.service;

import gov.kcg.kgo.viewModel.mydata.vo.service.base.MyDataServiceBaseRq;

import java.util.List;

public class MyDataServiceModeOneRq extends MyDataServiceBaseRq {

    private String clientId;

    private String clientIv;

    private String clientSecretKey;

    private String spReturnUrl;

    private String personalId;

    private List<String> resourceList;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientIv() {
        return clientIv;
    }

    public void setClientIv(String clientIv) {
        this.clientIv = clientIv;
    }

    public String getClientSecretKey() {
        return clientSecretKey;
    }

    public void setClientSecretKey(String clientSecretKey) {
        this.clientSecretKey = clientSecretKey;
    }

    public String getSpReturnUrl() {
        return spReturnUrl;
    }

    public void setSpReturnUrl(String spReturnUrl) {
        this.spReturnUrl = spReturnUrl;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public List<String> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<String> resourceList) {
        this.resourceList = resourceList;
    }

}
