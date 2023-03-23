package gov.kcg.kgo.viewModel.mydata.vo.ec.rq;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SpApplyRq {

    @JsonProperty("tx_id")
    private String txId;

    @JsonProperty("permission_ticket")
    private String permissionTicket;

    @JsonProperty("secret_key")
    private String secretKey;

    @JsonProperty("unable_to_deliver")
    private List<String> unableToDeliver;

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getPermissionTicket() {
        return permissionTicket;
    }

    public void setPermissionTicket(String permissionTicket) {
        this.permissionTicket = permissionTicket;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public List<String> getUnableToDeliver() {
        return unableToDeliver;
    }

    public void setUnableToDeliver(List<String> unableToDeliver) {
        this.unableToDeliver = unableToDeliver;
    }

}
