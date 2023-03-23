package gov.kcg.kgo.geoentity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "GEO_KGO_CASESET_RENT_MAIN")
public class GeoKgoCasesetRentMain implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name="case_rent_id",columnDefinition = "primary key not null")
    private String caseRentId;
    @Column(name="case_set_id",columnDefinition = "varchar(30) not null")
    private String caseSetId;
    @Column(name="service_id",columnDefinition = "varchar(50) not null")
    private String serviceId;
    @Column(name="create_user",columnDefinition = "varchar(50) not null")
    private String createUser;
    @Column(name="create_time",columnDefinition = "datetime2(0) not null")
    private Timestamp createTime;

    @Id
    @Column(name = "case_rent_id", columnDefinition = "varchar(50) NOT NULL")
    public String getCaseRentId() {
        return caseRentId;
    }

    public void setCaseRentId(String caseRentId) {
        this.caseRentId = caseRentId;
    }

    @Column(name = "case_set_id", columnDefinition = "varchar(30) NOT NULL")
    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

    @Column(name = "service_id", columnDefinition = "varchar(50) NOT NULL")
    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    @Column(name = "create_user", columnDefinition = "varchar(50) NOT NULL")
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Column(name = "create_time", columnDefinition = "datetime2(0) NOT NULL")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
