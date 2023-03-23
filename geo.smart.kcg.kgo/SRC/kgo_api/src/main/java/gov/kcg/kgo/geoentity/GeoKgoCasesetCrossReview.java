package gov.kcg.kgo.geoentity; 

import javax.persistence.*; 
import org.hibernate.annotations.DynamicInsert; 
import java.io.Serializable; 

/** 
 * GEO 20211115 add 跨機關檢視
 * 跨機關檢視權限 機關服務對應
 */
@Entity
@DynamicInsert
@Table(name = "GEO_KGO_CASESET_CROSS_REVIEW", schema = "dbo")
public class GeoKgoCasesetCrossReview implements Serializable {

    private static final long serialVersionUID = 1L; 

    @EmbeddedId
    private GeoKgoCasesetCrossReviewPK id;

    public GeoKgoCasesetCrossReviewPK getId() {
        return id;
    }

    public void setId(GeoKgoCasesetCrossReviewPK id) {
        this.id = id;
    }
}
