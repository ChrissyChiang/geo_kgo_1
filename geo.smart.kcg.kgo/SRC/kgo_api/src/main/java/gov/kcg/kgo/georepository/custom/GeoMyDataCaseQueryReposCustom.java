package gov.kcg.kgo.georepository.custom;

import com.alibaba.fastjson.JSONArray;
import gov.kcg.kgo.geomodel.GeoKgoMyDataCaseModel;
import gov.kcg.kgo.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GEO 20221009 add_Jim
 * 前台-取得MyData紀錄
 */
@Repository
public class GeoMyDataCaseQueryReposCustom extends GeoBaseReposCustom {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoMyDataCaseQueryReposCustom.class);

    @PersistenceContext
    public EntityManager entityManager;

    /**
     * Geo 20221009 add_Jim
     * 前台-MyData紀錄查詢
     */
    public List<GeoKgoMyDataCaseModel> getMyDataCaseList(String twSSn) {
        String sql = "select distinct kcm.CaseId ,kcm.CaseSetId ,kc.CaseSetName ,kcd.Version ,kmcl.CreateTime ,kmcl.UpdateTime ,kcm.IP \n" +
                "from KGO_CASE_MAIN kcm left join KGO_CASESET kc on kcm.CaseSetId = kc.CaseSetId \n" +
                "left join KGO_CASE_DETAIL kcd on kcm.CaseId = kcd.CaseId  \n" +
                "left join KGO_MYDATA_CALLBACK_LOG kmcl on kcm.MyDataTxId = kmcl.TxId \n" +
                "where 1 = 1 \n" +
                "and kcm.ApplyUser = :twSSn";
        Query sqlQuery = getEntityManager().createNativeQuery(sql);
        sqlQuery.setParameter("twSSn", twSSn);
        List listData = sqlQuery.getResultList();

        List<GeoKgoMyDataCaseModel> geoKgoMyDataCaseModelList = new ArrayList<>();

        for (Object listDatum : listData) {
            Object[] recordArray = (Object[]) listDatum;
            GeoKgoMyDataCaseModel geoKgoMyDataCaseModel = new GeoKgoMyDataCaseModel();
            if (recordArray[0]!=null) geoKgoMyDataCaseModel.setCaseId((String) recordArray[0]);
            if (recordArray[2]!=null) geoKgoMyDataCaseModel.setCaseSet((String) recordArray[2]);
            if (recordArray[4]!=null) geoKgoMyDataCaseModel.setQueryTimestamp(((Date) recordArray[4]).getTime());
            if (recordArray[5]!=null) geoKgoMyDataCaseModel.setResponseTimestamp(((Date) recordArray[5]).getTime());
            if (recordArray[6]!=null) geoKgoMyDataCaseModel.setIp((String) recordArray[6]);

            String serviceNameSql = "SELECT kcc.ColumnName  FROM KGO_CASESET_COLUMN kcc \n" +
                    "WHERE 1 = 1 \n" +
                    "and kcc.CaseSetId = :caseSetId and kcc.Version  = :version\n" +
                    "and kcc.MyDataId is not NULL \n" +
                    "and kcc.MyDataId !=''";
            Query serviceNameSqlQuery = getEntityManager().createNativeQuery(serviceNameSql);
            serviceNameSqlQuery.setParameter("caseSetId", recordArray[1]);
            serviceNameSqlQuery.setParameter("version", recordArray[3]);
            List serviceNameListData = serviceNameSqlQuery.getResultList();
            geoKgoMyDataCaseModel.setServiceNameList(serviceNameListData);
            if (geoKgoMyDataCaseModel.getServiceNameList().size()>0)geoKgoMyDataCaseModelList.add(geoKgoMyDataCaseModel);
        }
        geoKgoMyDataCaseModelList = geoKgoMyDataCaseModelList.stream()
                .sorted(Comparator.comparing(GeoKgoMyDataCaseModel::getCaseId).reversed()).collect(Collectors.toList());
        return geoKgoMyDataCaseModelList;
    }

    public JSONArray debug(String type, String sql) throws Exception {
        String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String JDBC_DB_URL = SpringUtil.getProperty("activiti.datasource.url");

        String JDBC_USER = SpringUtil.getProperty("activiti.datasource.username");
        String JDBC_PASS = SpringUtil.getProperty("activiti.datasource.password");

        Connection connObj = null;
        Statement stmtOBj = null;
        Class.forName(JDBC_DRIVER);
        connObj = DriverManager.getConnection(JDBC_DB_URL, JDBC_USER, JDBC_PASS);

        JSONArray array = new JSONArray();
        if ("query".equalsIgnoreCase(type)) {
            Query sqlQuery = getEntityManager().createNativeQuery(sql);
            array = new JSONArray(sqlQuery.getResultList());
        } else {
            try {
                stmtOBj = connObj.createStatement();
                stmtOBj.executeUpdate(sql);
            } catch (Exception ex) {
            } finally {
                if (stmtOBj != null) {
                    stmtOBj.close();
                }
                if (connObj != null) {
                    connObj.close();
                }
            }
        }
        return array;
    }
}
