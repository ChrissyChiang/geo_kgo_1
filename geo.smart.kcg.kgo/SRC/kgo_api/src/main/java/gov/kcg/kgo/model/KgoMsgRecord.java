package gov.kcg.kgo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the KGO_MSG_RECORD database table.
 */
@Entity
@Table(name = "KGO_MSG_RECORD")
public class KgoMsgRecord implements Serializable {

    @EmbeddedId
    private KgoMsgRecordPK id;

    @Column(name = "Handler", length = 50)
    private String handler;

    @Column(name = "Memo", length = 500)
    private String memo;

    @Column(name = "HandleTime")
    private Timestamp handleTime;

    @Column(name = "Status", length = 30)
    private String status;

    public KgoMsgRecordPK getId() {
        return id;
    }

    public void setId(KgoMsgRecordPK id) {
        this.id = id;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Timestamp getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Timestamp handleTime) {
        this.handleTime = handleTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}