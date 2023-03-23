package gov.kcg.kgo.viewModel.mydata.vo.service;

import gov.kcg.kgo.viewModel.mydata.bo.ServiceDataBO;

import java.util.Map;

public class MyDataServiceDownloadRs {

    private String txId;

    private String delaySeconds;

    private Map<String, ServiceDataBO> dpDataMap;

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getDelaySeconds() {
        return delaySeconds;
    }

    public void setDelaySeconds(String delaySeconds) {
        this.delaySeconds = delaySeconds;
    }

    public Map<String, ServiceDataBO> getDpDataMap() {
        return dpDataMap;
    }

    public void setDpDataMap(Map<String, ServiceDataBO> dpDataMap) {
        this.dpDataMap = dpDataMap;
    }

}
