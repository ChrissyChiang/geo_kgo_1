package gov.kcg.kgo.geoentity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Geo 20221020 add_Jim
 */
@Entity
@Table(name = "KGO_GEO_MYDATA_MODEL")
public class GeoKgoMyDataModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CaseSetId")
    private String caseSetId;

    @Column(name = "Model")
    private Integer model;

    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

    public Integer getModel() {
        return model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }
}
