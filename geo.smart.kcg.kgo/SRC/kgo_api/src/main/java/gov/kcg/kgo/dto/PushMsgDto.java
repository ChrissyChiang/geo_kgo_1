package gov.kcg.kgo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 推播API-推播資訊 DTO
 *
 * @author TPIuser
 */
@ApiModel(description = "推播API-推播資訊 DTO")
public class PushMsgDto {

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
     * 發送主旨
     */
    @ApiModelProperty(value = "發送主旨")
    private String msgTitle;
    /**
     * 推播文字
     */
    @ApiModelProperty(value = "推播文字")
    private String msgBody;
    /**
     * 推播詳細內容
     */
    @ApiModelProperty(value = "推播詳細內容")
    private String clickDetail;

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

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public String getClickDetail() {
        return clickDetail;
    }

    public void setClickDetail(String clickDetail) {
        this.clickDetail = clickDetail;
    }

}
