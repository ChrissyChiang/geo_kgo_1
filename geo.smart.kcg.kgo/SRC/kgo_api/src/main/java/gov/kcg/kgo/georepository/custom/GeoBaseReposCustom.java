package gov.kcg.kgo.georepository.custom;

import gov.kcg.kgo.geoutil.GeoStringUtil;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class GeoBaseReposCustom {

    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * 搜尋當天最大值的編號並取得下一個
     *
     * @return
     */
    public String getNextTableId(String prefixChar, String tableName, String tablePK) {
        String currentDateStr = GeoStringUtil.getCurrentDateStr();
        //LOGGER.info("GeoBaseReposCustom getNextTableId currentDateStr: "+currentDateStr);
        String sqlStr = "select SUBSTRING(MAX("+tablePK+"),"+(9+prefixChar.length())+",5) + 1 from "+tableName+" where "+tablePK+" like CONCAT('%', :currentDateStr,'%') ;";
        Query sqlQuery = getEntityManager().createNativeQuery(sqlStr);
        //LOGGER.info("GeoBaseReposCustom getNextTableId sqlStr: "+sqlStr);
        sqlQuery.setParameter("currentDateStr", currentDateStr);

        List listData = sqlQuery.getResultList();
        String suffixStr = "";
        if (listData.size()>0 && listData.get(0)!=null) suffixStr = String.valueOf(listData.get(0));
        String nextId = GeoStringUtil.getNextId(suffixStr);
        String nextTableId = prefixChar + currentDateStr + nextId;
        return nextTableId;
    } //getNextTableId

}
