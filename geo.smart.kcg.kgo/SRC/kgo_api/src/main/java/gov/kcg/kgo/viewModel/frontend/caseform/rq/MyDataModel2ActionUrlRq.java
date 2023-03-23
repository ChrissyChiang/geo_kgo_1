package gov.kcg.kgo.viewModel.frontend.caseform.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "前臺 取得 MyData 轉址 URL")
public class MyDataModel2ActionUrlRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "txId")
    private String txId;

    @ApiModelProperty(notes = "data")
    private String data;

    @ApiModelProperty(notes = "pkcs7")
    private String pkcs7;

    @ApiModelProperty(notes = "caseSetId")
    private String caseSetId;

    @ApiModelProperty(notes = "pid")
    private String pid;

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

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

    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}