package gov.kcg.kgo.viewModel.mydata.vo.ec.rq;

import gov.kcg.kgo.viewModel.mydata.vo.ec.MyDataBaseRq;

public class VerifySpIdRq extends MyDataBaseRq {

    private String data;

    private String pkcs7;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPkcs7() {
        return pkcs7;
    }

    public void setPkcs7(String pkcs7) {
        this.pkcs7 = pkcs7;
    }

}
