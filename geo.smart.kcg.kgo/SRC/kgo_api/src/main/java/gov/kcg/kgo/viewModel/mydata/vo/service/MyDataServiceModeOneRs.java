package gov.kcg.kgo.viewModel.mydata.vo.service;

import gov.kcg.kgo.viewModel.mydata.vo.service.base.MyDataServiceBaseRq;
import gov.kcg.kgo.viewModel.mydata.vo.service.base.MyDataServiceBaseRs;

public class MyDataServiceModeOneRs extends MyDataServiceBaseRs {

    private String redirectMyDataUrl;

    public MyDataServiceModeOneRs(MyDataServiceBaseRq myDataServiceBaseRq) {
        this.setTxId(myDataServiceBaseRq.getTxId());
    }

    public String getRedirectMyDataUrl() {
        return redirectMyDataUrl;
    }

    public void setRedirectMyDataUrl(String redirectMyDataUrl) {
        this.redirectMyDataUrl = redirectMyDataUrl;
    }

}
