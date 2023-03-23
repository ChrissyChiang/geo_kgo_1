package gov.kcg.kgo.viewModel.mydata.vo.ec.rs;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.kcg.kgo.viewModel.mydata.vo.ec.MyDataBaseRs;

public class GetSpSignatureParameterRs extends MyDataBaseRs {

    @JsonProperty("tx_id")
    private String txId;

    @JsonProperty("salt")
    private String salt;

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

}
