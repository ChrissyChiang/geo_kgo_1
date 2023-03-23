package gov.kcg.kgo.georepository.custom;

import gov.kcg.kgo.geoentity.OrganDiscountRatio;
import gov.kcg.kgo.geomodel.GeoUserQueryModel;
import gov.kcg.kgo.model.OrganRefundRatio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class GeoOrganDiscountRatioReposCustom extends GeoBaseReposCustom {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeoOrganDiscountRatioReposCustom.class);

    @PersistenceContext
    public EntityManager entityManager;

    /**
     * Geo 20221104
     * 後台-機關折扣查詢
     */
    public List<OrganDiscountRatio> OrganDiscountQuery(String organId, Integer percent) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM GEO_KGO_ORGAN_DISCOUNT_RATIO  ");
        sql.append("WHERE organ_id = :organId  ");
        if(percent != null) sql.append("AND discount_ratio = :percent  ");

        Query sqlQuery = getEntityManager().createNativeQuery(sql.toString(), OrganDiscountRatio.class);
        sqlQuery.setParameter("organId", organId);
        if(percent != null)sqlQuery.setParameter("percent", percent);
        List listData = sqlQuery.getResultList();

        return sqlQuery.getResultList();
    }


}
