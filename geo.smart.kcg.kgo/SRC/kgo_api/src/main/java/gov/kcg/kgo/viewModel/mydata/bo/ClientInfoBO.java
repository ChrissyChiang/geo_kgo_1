package gov.kcg.kgo.viewModel.mydata.bo;

import java.util.List;

public class ClientInfoBO {
    private String clientId;

    private String clientIv;

    private String clientSecretKey;

    private List<String> resourceList;

    public ClientInfoBO(String clientId, String clientIv, String clientSecretKey, List<String> resourceList) {
        this.clientId = clientId;
        this.clientIv = clientIv;
        this.clientSecretKey = clientSecretKey;
        this.resourceList = resourceList;
    }

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

    public List<String> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<String> resourceList) {
        this.resourceList = resourceList;
    }

    @Override
    public String toString() {
        return "ClientInfoBO{" +
                "clientId='" + clientId + '\'' +
                ", clientIv='" + clientIv + '\'' +
                ", clientSecretKey='" + clientSecretKey + '\'' +
                ", resourceList=" + resourceList +
                '}';
    }
}
