package gov.kcg.kgo.geoentity;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;

/**
 * GEO 20211005 add
 * <p>
 * 服務申辦統計
 */

@Entity
@DynamicInsert
@Table(name = "GEO_KGO_CASESET_APPLY_COUNT", schema = "dbo")
public class GeoKgoCaseSetApplyCount implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer casesetRank; //名次 
    private String casesetId; //服務id 

    @Basic
    @Column(name = "caseset_rank", columnDefinition = "int NOT NULL")
    public Integer getCasesetRank() {
        return casesetRank;
    }

    public void setCasesetRank(Integer casesetRank) {
        this.casesetRank = casesetRank;
    }

    @Id
    @Column(name = "caseset_id", columnDefinition = "varchar(30) NOT NULL")
    public String getCasesetId() {
        return casesetId;
    }

    public void setCasesetId(String casesetId) {
        this.casesetId = casesetId;
    }

} 
