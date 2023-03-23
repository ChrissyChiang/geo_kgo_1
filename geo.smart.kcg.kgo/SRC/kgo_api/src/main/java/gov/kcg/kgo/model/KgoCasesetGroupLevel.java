package gov.kcg.kgo.model;

import javax.persistence.*;

@Entity
@Table(name = "KGO_CASSET_GROUP_LEVEL")
@NamedQuery(name = "KgoCasesetGroupLevel.findAll", query = "SELECT k FROM KgoCasesetGroupLevel k")
public class KgoCasesetGroupLevel {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "Seq", unique = true, nullable = false)
    private Integer seq;

    @Column(name = "CaseSetId")
    private String caseSetId;

    @Column(name = "MainType")
    private String mainType;

    @Column(name = "GroupLevelSeq")
    private String groupLevelSeq;

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

    public String getMainType() {
        return mainType;
    }

    public void setMainType(String mainType) {
        this.mainType = mainType;
    }

    public String getGroupLevelSeq() {
        return groupLevelSeq;
    }

    public void setGroupLevelSeq(String groupLevelSeq) {
        this.groupLevelSeq = groupLevelSeq;
    }
}
