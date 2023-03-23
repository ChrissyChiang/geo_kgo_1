package gov.kcg.kgo.viewModel.pushApi.push.push0001.rq.bean;

import io.swagger.annotations.ApiModelProperty;

public class KGData {

    /**
     * account
     */
    @ApiModelProperty(value = "account")
    private String account;

    /**
     * 身份證字號(以SHA256加密)
     */
    @ApiModelProperty(value = "身份證字號(以SHA256加密)")
    private String custNum;
    /**
     * 靜音模式(default: TRUE 靜音)
     */
    @ApiModelProperty(value = "靜音模式(default: TRUE 靜音)")
    private boolean muteMode;

    /**
     * 靜音模式(default: TRUE 靜音)
     */
    @ApiModelProperty(value = "第三層")
    private PushMsgData pushMsg;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCustNum() {
        return custNum;
    }

    public void setCustNum(String custNum) {
        this.custNum = custNum;
    }

    public boolean isMuteMode() {
        return muteMode;
    }

    public void setMuteMode(boolean muteMode) {
        this.muteMode = muteMode;
    }

    public PushMsgData getPushMsg() {
        return pushMsg;
    }

    public void setPushMsg(PushMsgData pushMsg) {
        this.pushMsg = pushMsg;
    }

}
