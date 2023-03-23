package gov.kcg.kgo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the KGO_MYDATA_FILE database table.
 */
@Entity
@Table(name = "KGO_MYDATA_FILE")
public class KgoMydataFile implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Seq", unique = true, nullable = false)
    private Integer seq;

    @Column(name = "TxId", nullable = false, length = 36)
    private String txId;

    @Column(name = "PermissionTicket", nullable = false, length = 36)
    private String permissionTicket;

    @Column(name = "MyDataFile")
    private String myDataFile;

    @Column(name = "CreateTime", nullable = false)
    private Timestamp createTime;

    /** GEO 20211201 Add */
    @Column(name = "UpdateTime")
    private Timestamp updateTime;

    /** GEO 20211201 Add */
    @Column(name = "UpdateUser")
    private String updateUser;

    public KgoMydataFile() {
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

    public void setMyDataFile(String myDataFile) {
        this.myDataFile = myDataFile;
    }

    public String getMyDataFile() {
        return myDataFile;
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

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}