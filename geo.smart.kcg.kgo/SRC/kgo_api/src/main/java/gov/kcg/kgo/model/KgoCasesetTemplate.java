package gov.kcg.kgo.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="KGO_CASESET_TEMPLATE")
@NamedQuery(name="KgoCasesetTemplate.findAll", query="SELECT k FROM KgoCasesetTemplate k")
public class KgoCasesetTemplate implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="Seq", unique=true, nullable=false)
    private Integer seq;

    @Column(name="Name")
    private String name;

    @Column(name="Suspend")
    private String suspend;

    @Column(name="IsDefault")
    private String isDefault;

    @Column(name="Organ")
    private String organ;

    @Column(name="CheckFrequencyPeriod")
    private String checkFrequencyPeriod;

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuspend() {
        return suspend;
    }

    public void setSuspend(String suspend) {
        this.suspend = suspend;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
    }

    public String getCheckFrequencyPeriod() {
        return checkFrequencyPeriod;
    }

    public void setCheckFrequencyPeriod(String checkFrequencyPeriod) {
        this.checkFrequencyPeriod = checkFrequencyPeriod;
    }
}
