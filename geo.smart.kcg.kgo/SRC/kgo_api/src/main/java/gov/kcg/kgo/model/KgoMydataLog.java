package gov.kcg.kgo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the KGO_MYDATA_LOG database table.
 */
@Entity
@Table(name = "KGO_MYDATA_LOG")
public class KgoMydataLog implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Seq", unique = true, nullable = false)
    private Integer seq;

    @Column(name = "TxId", nullable = false, length = 36)
    private String txId;

    @Column(name = "ServiceName", nullable = false, length = 50)
    private String serviceName;

    @Column(name = "RequestData", nullable = false)
    private String requestData;

    @Column(name = "ResponseData")
    private String responseData;

    @Column(name = "ReturnCode", nullable = false, length = 4)
    private String returnCode;

    @Column(name = "CreateTime", nullable = false)
    private Timestamp createTime;

    @Column(name="UpdateTime")
    private Timestamp updateTime;
    
    @Column(name = "ResponseNewData")
    private String responseNewData;

    public KgoMydataLog() {
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

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
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

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

	public String getResponseNewData() {
		return responseNewData;
	}

	public void setResponseNewData(String responseNewData) {
		this.responseNewData = responseNewData;
	}
    
}