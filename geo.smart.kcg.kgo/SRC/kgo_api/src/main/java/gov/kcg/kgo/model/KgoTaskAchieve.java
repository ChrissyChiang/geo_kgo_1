package gov.kcg.kgo.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "KGO_TASK_ACHIEVE")
public class KgoTaskAchieve {

    @EmbeddedId
    private KgoTaskAchievePK id;

    @Column(name = "GetPoint")
    private Integer getPoint;

    @Column(name = "GetUser")
    private String getUser;

    @Column(name = "GetTime")
    private Timestamp getTime;

    public KgoTaskAchievePK getId() {
        return id;
    }

    public void setId(KgoTaskAchievePK id) {
        this.id = id;
    }

    public Integer getGetPoint() {
        return getPoint;
    }

    public void setGetPoint(Integer getPoint) {
        this.getPoint = getPoint;
    }


    public String getGetUser() {
        return getUser;
    }

    public void setGetUser(String getUser) {
        this.getUser = getUser;
    }

    public Timestamp getGetTime() {
        return getTime;
    }

    public void setGetTime(Timestamp getTime) {
        this.getTime = getTime;
    }

}
