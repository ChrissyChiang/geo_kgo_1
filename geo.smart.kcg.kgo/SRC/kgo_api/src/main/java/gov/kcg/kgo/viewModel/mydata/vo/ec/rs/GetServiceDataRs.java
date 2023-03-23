package gov.kcg.kgo.viewModel.mydata.vo.ec.rs;

import gov.kcg.kgo.viewModel.mydata.vo.ec.MyDataBaseRs;

public class GetServiceDataRs extends MyDataBaseRs {

    private String jwe;

    private String delaySeconds;

    public GetServiceDataRs() {
        this.delaySeconds = "0";
    }

    public String getJwe() {
        return jwe;
    }

    public void setJwe(String jwe) {
        this.jwe = jwe;
    }

    public String getDelaySeconds() {
        return delaySeconds;
    }

    public void setDelaySeconds(String delaySeconds) {
        this.delaySeconds = delaySeconds;
    }

}
