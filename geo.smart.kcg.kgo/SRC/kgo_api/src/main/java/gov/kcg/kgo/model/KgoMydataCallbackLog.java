package gov.kcg.kgo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the KGO_MYDATA_CALLBACK_LOG database table.
 * 
 */
@Entity
@Table(name="KGO_MYDATA_CALLBACK_LOG")
public class KgoMydataCallbackLog implements Serializable {
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="Seq", unique=true, nullable=false)
    private Integer seq;

    @Column(name="TxId", nullable=false, length=36)
    private String txId;

    @Column(name = "PermissionTicket", nullable = false, length = 36)
    private String permissionTicket;

    @Column(name="SecretKey", nullable=false, length=50)
    private String secretKey;

    @Column(name="UnableToDeliver")
    private String unableToDeliver;

    @Column(name="CreateTime", nullable=false)
    private Timestamp createTime;

    @Column(name="UpdateTime")
    private Timestamp updateTime;

	public KgoMydataCallbackLog() {
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

    public String getUnableToDeliver() {
        return unableToDeliver;
    }

    public void setUnableToDeliver(String unableToDeliver) {
        this.unableToDeliver = unableToDeliver;
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

}