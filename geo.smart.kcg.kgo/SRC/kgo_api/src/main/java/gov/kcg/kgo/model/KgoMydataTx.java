package gov.kcg.kgo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the KGO_MYDATA_TX database table.
 */
@Entity
@Table(name = "KGO_MYDATA_TX")
public class KgoMydataTx implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Seq", unique = true, nullable = false)
    private Integer seq;

    @Column(name = "TxId", nullable = false, length = 36)
    private String txId;

    @Column(name = "ClientId", nullable = false, length = 50)
    private String clientId;

    @Column(name = "ClientIv", nullable = false, length = 50)
    private String clientIv;

    @Column(name = "ClientSecretKey", nullable = false, length = 50)
    private String clientSecretKey;

    @Column(name = "ResourceId", nullable = false, length = 500)
    private String resourceId;

    @Column(name = "ModeType", nullable = false, length = 1)
    private String modeType;

    @Column(name = "RequestData", nullable = false)
    private String requestData;

    @Column(name = "ResponseData")
    private String responseData;

    @Column(name = "CreateTime", nullable = false)
    private Timestamp createTime;

    public KgoMydataTx() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
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

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getModeType() {
        return modeType;
    }

    public void setModeType(String modeType) {
        this.modeType = modeType;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

}