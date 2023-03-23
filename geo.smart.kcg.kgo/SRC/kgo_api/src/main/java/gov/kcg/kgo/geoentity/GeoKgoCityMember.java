package gov.kcg.kgo.geoentity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "GEO_KGO_CITY_MEMBER")
public class GeoKgoCityMember implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "Id")
    private String id;
    @Column(name = "HashId")
    private String hashId;
    @Column(name = "RealName")
    private Boolean realName;
    @Column(name = "UpdateTime")
    private Date updateTime;

    public GeoKgoCityMember() {
    }

    public GeoKgoCityMember(String id, String hashId, Boolean realName) {
        this.id = id;
        this.hashId = hashId;
        this.realName = realName;
        this.updateTime = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public Boolean getRealName() {
        return realName;
    }

    public void setRealName(Boolean realName) {
        this.realName = realName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
