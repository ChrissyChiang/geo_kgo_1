package gov.kcg.kgo.geoentity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * GEO 20211019 add
 * 案件關聯服務
 */
@Entity
@Table(name = "GEO_KGO_CASESET_ASSOCIATE", schema = "dbo")
@NamedQuery(name = "GeoKgoCasesetAssociate.findAll", query = "SELECT c FROM GeoKgoCasesetAssociate c")
public class GeoKgoCasesetAssociate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private GeoKgoCasesetAssociatePK id;

    public GeoKgoCasesetAssociatePK getId() {
        return id;
    }

    public void setId(GeoKgoCasesetAssociatePK id) {
        this.id = id;
    }
}
