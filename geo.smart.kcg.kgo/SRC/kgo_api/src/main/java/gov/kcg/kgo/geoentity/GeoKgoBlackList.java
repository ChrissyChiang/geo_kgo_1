package gov.kcg.kgo.geoentity;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * GEO 20221028 add
 * 更新 民政局黑名單資料 來源 城市資料平台
 */
@Entity
@DynamicInsert
@Table(name = "GEO_KGO_BLACK_LIST", schema = "dbo")
public class GeoKgoBlackList implements Serializable {

    private Integer seqGuid; //id
    private String userId; //民眾身分證
    private String userName; //民眾姓名
    private Timestamp createTime; //創建時間

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq_guid", columnDefinition = "Integer not null")
    public Integer getSeqGuid() {
        return seqGuid;
    }

    public void setSeqGuid(Integer seqGuid) {
        this.seqGuid = seqGuid;
    }

    @Basic
    @Column(name = "user_Id", columnDefinition = "varchar(20)")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    @Basic
    @Column(name = "user_name", columnDefinition = "varchar(10)")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "create_time", columnDefinition = "Timestamp")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
