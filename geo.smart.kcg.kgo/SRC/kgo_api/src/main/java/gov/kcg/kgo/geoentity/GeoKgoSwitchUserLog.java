package gov.kcg.kgo.geoentity;

import gov.kcg.kgo.util.KgoUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Geo 20221108 add_Jim
 */
@Entity
@Table(name = "GEO_KGO_SWITCH_USER_LOG")
public class GeoKgoSwitchUserLog implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "Id")
    private UUID id;
    @Column(name = "SessionId")
    private String sessionId;
    @Column(name = "FunctionName")
    private String functionName;
    @Column(name = "SwitchFrom")
    private String switchFrom;
    @Column(name = "SwitchTo")
    private String switchTo;
    @Column(name = "Ip")
    private String ip;
    @Column(name = "SwitchTime")
    private Date switchTime;

    public GeoKgoSwitchUserLog() {
    }

    public GeoKgoSwitchUserLog(HttpServletRequest request, String functionName, String switchFrom, String switchTo) {
        this.id = UUID.randomUUID();
        this.sessionId = request.getRequestedSessionId();
        this.functionName = functionName;
        this.switchFrom = switchFrom;
        this.switchTo = switchTo;
        this.ip = KgoUtil.getClientIp();
        this.switchTime = new Date();
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getSwitchFrom() {
        return switchFrom;
    }

    public void setSwitchFrom(String switchFrom) {
        this.switchFrom = switchFrom;
    }

    public String getSwitchTo() {
        return switchTo;
    }

    public void setSwitchTo(String switchTo) {
        this.switchTo = switchTo;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getSwitchTime() {
        return switchTime;
    }

    public void setSwitchTime(Date switchTime) {
        this.switchTime = switchTime;
    }
}
