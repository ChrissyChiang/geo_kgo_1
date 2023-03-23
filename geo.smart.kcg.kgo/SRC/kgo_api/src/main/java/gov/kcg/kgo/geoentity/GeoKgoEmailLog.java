package gov.kcg.kgo.geoentity;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * GEO 20220510 add 案件狀態更新修改
 * 案件狀態更新email Log
 */
@Entity
@DynamicInsert
@Table(name = "GEO_KGO_EMAIL_LOG", schema = "dbo")
public class GeoKgoEmailLog {

    private static final long serialVersionUID = 1L;

    @Id
    @ApiModelProperty(notes = "序號")
    @GeneratedValue(generator  = "generator")
    @GenericGenerator(name = "generator", strategy = "guid")
    @Column(name="seq_guid", unique=true, nullable=false, length=1)
    private String seqＧuid;

    @Column(name = "case_id", columnDefinition = "varchar(20) NOT NULL")
    private String caseId; //案件編號id

    @Column(name = "status", columnDefinition = "varchar(10) NOT NULL")
    private String statute; //案件狀態

    @Column(name = "send_time", columnDefinition = "default current time")
    private Timestamp sendTime; //寄出時間

    public String getSeqＧuid() {
        return seqＧuid;
    }

    public void setSeqＧuid(String seqＧuid) {
        this.seqＧuid = seqＧuid;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getStatute() {
        return statute;
    }

    public void setStatute(String statute) {
        this.statute = statute;
    }

    public Timestamp getSendTime() {
        return sendTime;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }
}
