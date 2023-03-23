package gov.kcg.kgo.georepository.custom;

import gov.kcg.kgo.model.KgoMydataFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * GEO 20211201 add
 * 排程 刪除 myData附件檔案
 */
@Repository
public class GeoKgoMyDataFileCustom extends GeoBaseReposCustom {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoKgoMyDataFileCustom.class);

    /**
     * 刪除 myData附件檔案
     */
    public List<KgoMydataFile> findExpiredKgoMyDataFile(String status, String createTime) {
        String sqlStr = "Select mf.* ";
        String fromStr = "FROM KGO_MYDATA_FILE mf, KGO_CASE_MAIN cm ";
        String whereStr = "WHERE mf.TxId = cm.MyDataTxId and cm.Status = :status " +
                "and mf.CreateTime < Convert(datetime, :createTime) ";

        Query sqlQuery = getEntityManager().createNativeQuery(sqlStr + fromStr + whereStr, KgoMydataFile.class);
        sqlQuery.setParameter("status", status);
        sqlQuery.setParameter("createTime", createTime);
        List listData = sqlQuery.getResultList();
        return listData;
    } //findExpiredKgoMyDataFile
}
