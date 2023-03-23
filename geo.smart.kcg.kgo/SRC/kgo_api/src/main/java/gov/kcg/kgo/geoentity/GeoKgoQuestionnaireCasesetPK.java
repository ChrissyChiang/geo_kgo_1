package gov.kcg.kgo.geoentity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class GeoKgoQuestionnaireCasesetPK implements Serializable {

    private String caseSetId; //服務id
    private int questiinVersion; //問卷版本

    @Id
    @Column(name = "case_set_id", columnDefinition = "varchar(30) NOT NULL")
    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

    @Id
    @Column(name = "questiin_version", columnDefinition = "int DEFAULT 1 NOT NULL")
    public int getQuestiinVersion() {
        return questiinVersion;
    }

    public void setQuestiinVersion(int questiinVersion) {
        this.questiinVersion = questiinVersion;
    }

    /** 多 PK 的 class 須繼承以下 method **/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeoKgoQuestionnaireCasesetPK that = (GeoKgoQuestionnaireCasesetPK) o;
        return questiinVersion == that.questiinVersion && Objects.equals(caseSetId, that.caseSetId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(caseSetId, questiinVersion);
    }
}
