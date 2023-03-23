package gov.kcg.kgo.georepository.custom;

import gov.kcg.kgo.enums.backend.CaseFlowTypeEnum;
import gov.kcg.kgo.geomodel.GeoBidCaseListQueryDataGridModel;
import gov.kcg.kgo.geomodel.GeoCaseSetApplyCountModel;
import gov.kcg.kgo.geomodel.GeoCaseSetListDtoModel;
import gov.kcg.kgo.model.KgoCasesetType;
import gov.kcg.kgo.model.KgoCasesetTypePK;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * GEO 20211005 add
 * 取得 KGO_CASE_SET, KGO_GROUP_LEVEL 組合資料
 */

@Repository
public class GeoCaseSetApplyCountReposCustom extends GeoBaseReposCustom {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoCaseSetApplyCountReposCustom.class);

    /**
     * 取得申辦服務名次列表
     */
    public List<GeoCaseSetApplyCountModel> getApplyCountListByRange(Date startDate, Date endDate, CaseFlowTypeEnum caseFlowTypeEnum, String caseSetStatus) {
        String withSelectStr = "WITH CaseSet_Count (CaseSetId, CaseSetCount) AS "+
                "( SELECT cm.CaseSetId, COUNT(cm.CaseSetId) AS CaseSetCount "+
                "FROM KGO_CASE_MAIN cm, KGO_CASESET cs " +
                "WHERE cm.ApplyDate <= Convert(datetime, :endDate) " +
                "and cm.ApplyDate >= Convert(datetime, :startDate) "+
                "and cs.CaseSetId = cm.CaseSetId ";

        String sqlStr = "SELECT cc.CaseSetId, cc.CaseSetCount, cs.CaseSetName, cs.OwnerOrgan, cs.Organ, gl.OrganName, cs.Status ";
        String fromStr = "FROM KGO_ORGAN gl, KGO_CASESET cs, CaseSet_Count cc ";
        String whereStr = "WHERE cs.CaseSetId = cc.CaseSetId " +
                "and cs.OwnerOrgan = gl.OrganId "+
                "ORDER BY CaseSetCount DESC";

        if (StringUtils.isNotBlank(caseSetStatus)) withSelectStr += "and UPPER(cs.Status) = :caseSetStatus GROUP BY cm.CaseSetId)";
        else withSelectStr += "GROUP BY cm.CaseSetId)";
        if (caseFlowTypeEnum != null) withSelectStr += "and cs.CaseFlowType = :caseFlowType  ";

        Query sqlQuery = getEntityManager().createNativeQuery(withSelectStr + sqlStr + fromStr + whereStr);
        if (startDate != null) sqlQuery.setParameter("startDate", startDate);
        if (endDate != null) sqlQuery.setParameter("endDate", endDate);
        if (caseFlowTypeEnum != null) sqlQuery.setParameter("caseFlowType", caseFlowTypeEnum.getValue());
        if (StringUtils.isNotBlank(caseSetStatus)) sqlQuery.setParameter("caseSetStatus", caseSetStatus.toUpperCase());
//        LOGGER.info("GeoCaseSetApplyCountReposCustom getApplyCountListByRange sqlQuery: " +withSelectStr + sqlStr + fromStr + whereStr);
        List listData = sqlQuery.getResultList();
        List<GeoCaseSetApplyCountModel> datas = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < listData.size(); i++) {
            GeoCaseSetApplyCountModel model = new GeoCaseSetApplyCountModel();
            Object[] recordArray = (Object[]) listData.get(i);
            model.setCaseSetId((String) recordArray[0]);
            model.setApplyCount((Integer) recordArray[1]);
            model.setCaseSetName((String) recordArray[2]);
            model.setOrganId((String) recordArray[3]);
            model.setOrganSeq((String) recordArray[4]);
            model.setOrganName((String) recordArray[5]);
            model.setCaseSetStatus(((String) recordArray[6]));
            datas.add(model);
        }
        return datas;
    } //getApplyCountListByRange


//    /**
//     * 取得 申辦服務名次內的服務資訊  KGO_CASESET, KGO_GROUP_LEVEL
//     */
//    public GeoBidCaseListQueryDataGridModel getCaseSetData(String caseSetStatus, String caseSetId) {
//        String sqlStr = "SELECT cs.CaseSetId, cs.CaseSetName, cs.CaseFlowType, cs.Status, cs.OwnerOrgan, cs.Organ, gl.Name, ct.ApplyTYpe ";
//        String fromStr = "FROM KGO_CASESET cs, KGO_GROUP_LEVEL gl, KGO_CASESET_TYPE ct ";
//        String whereStr = "WHERE cs.CaseSetId = :caseSetId and gl.OrganId = cs.OwnerOrgan and cs.Organ = gl.Seq and cs.CaseSetId = ct.CaseSetId ";
//        if (caseSetStatus != null) whereStr += "and UPPER(cs.Status) = :caseSetStatus ";
//        Query sqlQuery = getEntityManager().createNativeQuery(sqlStr + fromStr + whereStr);
//        if (caseSetStatus != null) sqlQuery.setParameter("caseSetStatus", caseSetStatus.toUpperCase());
//        if (caseSetId != null) sqlQuery.setParameter("caseSetId", caseSetId);
//        LOGGER.info("GeoCaseSetApplyCountReposCustom getCaseSetApplyCountList sqlQuery: " + sqlStr + fromStr + whereStr);
//        List listData = sqlQuery.getResultList();
//        GeoBidCaseListQueryDataGridModel data = null;
//        if (listData != null && listData.size() > 0) {
//            data = new GeoBidCaseListQueryDataGridModel();
//            Object[] recordArray = (Object[]) listData.get(0);
//            data.setCaseSetId((String) recordArray[0]);
//            data.setCaseSetName((String) recordArray[1]);
//            data.setCaseFlowType((String) recordArray[2]);
//            data.setCaseSetStatus((String) recordArray[3]);
//            data.setOrganId((String) recordArray[4]);
//            data.setOrganSeq((String) recordArray[5]);
//            data.setOrganName((String) recordArray[6]);
//            data.setApplyType((String) recordArray[7]);
//        }
//        return data;
//    } //getApplyCountListByRange

    /**
     * 取得申辦服務名次資料列表
     */
    public List<GeoBidCaseListQueryDataGridModel> getCaseSetApplyCountList(Date startDate, Date endDate, CaseFlowTypeEnum caseFlowTypeEnum, String caseSetStatus) {
        String withSelectStr = "WITH CaseSet_Count (CaseSetId, CaseSetCount) AS "+
                "( SELECT cm.CaseSetId, COUNT(cm.CaseSetId) AS CaseSetCount "+
                "FROM KGO_CASE_MAIN cm, KGO_CASESET cs " +
                "WHERE cm.CreateTime <= Convert(datetime, :endDate) " +
                "and cm.CreateTime >= Convert(datetime, :startDate) "+
                "and cs.CaseSetId = cm.CaseSetId ";
        String sqlStr = "SELECT cs.CaseSetId, cs.CaseSetName, cs.CaseFlowType, cs.Status, " +
                "cs.OwnerOrgan, cs.Organ, gl.OrganName, car.CaseSetCount ";
        String fromStr = "FROM KGO_CASESET cs, KGO_ORGAN gl, CaseSet_Count car ";
        String whereStr = "WHERE cs.CaseSetId = car.CaseSetId and gl.OrganId = cs.OwnerOrgan ";
        String orderStr = "ORDER BY car.CaseSetCount DESC ";

        if (caseFlowTypeEnum != null) withSelectStr += "and cs.CaseFlowType = :caseFlowType  ";
        if (StringUtils.isNotBlank(caseSetStatus)) withSelectStr += "and UPPER(cs.Status) = :caseSetStatus GROUP BY cm.CaseSetId)";
        else withSelectStr += "GROUP BY cm.CaseSetId)";

        Query sqlQuery = getEntityManager().createNativeQuery(withSelectStr + sqlStr + fromStr + whereStr + orderStr);
        if (startDate != null) sqlQuery.setParameter("startDate", startDate);
        if (endDate != null) sqlQuery.setParameter("endDate", endDate);
        if (caseFlowTypeEnum != null) sqlQuery.setParameter("caseFlowType", caseFlowTypeEnum.getValue());
        if (StringUtils.isNotBlank(caseSetStatus)) sqlQuery.setParameter("caseSetStatus", caseSetStatus.toUpperCase());
        List listData = sqlQuery.getResultList();
        List<GeoBidCaseListQueryDataGridModel> datas = Collections.synchronizedList(new ArrayList<>());
        if (listData != null && listData.size() > 0) {
            for (int i = 0; i < listData.size(); i++) {
                GeoBidCaseListQueryDataGridModel data = new GeoBidCaseListQueryDataGridModel();
                Object[] recordArray = (Object[]) listData.get(i);
                data.setCaseSetId((String) recordArray[0]);
                data.setCaseSetName((String) recordArray[1]);
                data.setCaseFlowType((String) recordArray[2]);
                data.setCaseSetStatus((String) recordArray[3]);
                data.setOrganId((String) recordArray[4]);
                data.setOrganSeq((String) recordArray[5]);
                data.setOrganName((String) recordArray[6]);
                data.setApplyCount((Integer) recordArray[7]);
                datas.add(data);
            }
        }
        return datas;
    } //getCaseSetApplyCountList

    /**
     * KGO_CASESET_TYPE 取得案件申辦型態
     */
    public List<KgoCasesetType> getCaseSetApplyType(String caseSetId) {
        String sqlStr = "SELECT CaseSetId, ApplyType ";
        String fromStr = "FROM KGO_CASESET_TYPE ct ";
        String whereStr = "WHERE ct.CaseSetId = :caseSetId ";

        Query sqlQuery = getEntityManager().createNativeQuery( sqlStr + fromStr + whereStr);
        if (caseSetId != null) sqlQuery.setParameter("caseSetId", caseSetId);
        List listData = sqlQuery.getResultList();
        List<KgoCasesetType> datas = Collections.synchronizedList(new ArrayList<>());
        if (listData != null && listData.size() > 0) {
            for (int i = 0; i < listData.size(); i++) {
                KgoCasesetType data = new KgoCasesetType();
                KgoCasesetTypePK typePk = new KgoCasesetTypePK();
                Object[] recordArray = (Object[]) listData.get(i);
                typePk.setCaseSetId((String) recordArray[0]);
                typePk.setApplyType((String) recordArray[1]);
                data.setId(typePk);
                datas.add(data);
            } //for (int i = 0; i < listData.size(); i++) {
        } //if (listData != null && listData.size() > 0) {
        return datas;
    } //getCaseSetApplyType

    /**
     * GEO 20211202 add
     * 前台-關聯服務: 取得關聯服務
     */
    public List<GeoBidCaseListQueryDataGridModel> getCaseSetAssociateList(String caseSetId, String caseSetStatus) {
        String withSelectStr = "WITH CaseSetData(caseSetId, associateCasesetId) AS (\n" +
                "    SELECT cs.caseset_id, associate_caseset_id\n" +
                "    FROM GEO_KGO_CASESET_ASSOCIATE cs\n" +
                "    WHERE cs.caseset_id = :caseSetId\n" +
                ") ";
        String sqlStr = "SELECT cs.CaseSetId, cs.CaseSetName, cs.CaseFlowType, cs.Status,\n" +
                "       cs.OwnerOrgan, cs.Organ, gl.Name ";
        String fromStr = "FROM KGO_CASESET cs, KGO_GROUP_LEVEL gl, CaseSetData cd ";
        String whereStr = "WHERE cs.CaseSetId = cd.associateCasesetId and gl.OrganId = cs.OwnerOrgan \n" +
                "  and UPPER(cs.Status) = :caseSetStatus ";

        Query sqlQuery = getEntityManager().createNativeQuery(withSelectStr + sqlStr + fromStr + whereStr);
        sqlQuery.setParameter("caseSetStatus", caseSetStatus.toUpperCase());
        sqlQuery.setParameter("caseSetId", caseSetId);
        List listData = sqlQuery.getResultList();
        List<GeoBidCaseListQueryDataGridModel> datas = Collections.synchronizedList(new ArrayList<>());
        if (listData != null && listData.size() > 0) {
            for (int i = 0; i < listData.size(); i++) {
                GeoBidCaseListQueryDataGridModel data = new GeoBidCaseListQueryDataGridModel();
                Object[] recordArray = (Object[]) listData.get(i);
                data.setCaseSetId((String) recordArray[0]);
                data.setCaseSetName((String) recordArray[1]);
                data.setCaseFlowType((String) recordArray[2]);
                data.setCaseSetStatus((String) recordArray[3]);
                data.setOrganId((String) recordArray[4]);
                data.setOrganSeq((String) recordArray[5]);
                data.setOrganName((String) recordArray[6]);
                datas.add(data);
            }
        }
        return datas;
    } //getCaseSetApplyCountList

    /**
     * KGO_CASESET 取得案件資料
     */
    public List<GeoCaseSetListDtoModel> getCaseSetListInfo(String status, String caseFlowType) {
        LOGGER.info("取得案件資料 getCaseSetListInfo status="+status);

        String sqlStr = "SELECT CaseSetId, CaseSetName, ServiceHtml ";
        String fromStr = "FROM KGO_CASESET ";
        String whereStr = "WHERE Status = :status and CaseFlowType = :caseFlowType ";
        Query sqlQuery = getEntityManager().createNativeQuery(sqlStr + fromStr + whereStr);
        sqlQuery.setParameter("status",status);
        sqlQuery.setParameter("caseFlowType",caseFlowType);

        LOGGER.info("getCaseSetListInfo ssqlQuery="+sqlStr + fromStr + whereStr);
        List listData = sqlQuery.getResultList();
        LOGGER.info("getCaseSetListInfo listData.size="+listData.size());
        List<GeoCaseSetListDtoModel> datas = Collections.synchronizedList(new ArrayList<>());
        if (listData != null && listData.size() > 0) {
            for (int i = 0; i < listData.size(); i++) {
                GeoCaseSetListDtoModel model = new GeoCaseSetListDtoModel();
                Object[] recordArray = (Object[]) listData.get(i);
                model.setCaseSetId((String) recordArray[0]);
                model.setCaseSetName((String) recordArray[1]);
                model.setServiceHtml((String) recordArray[2]);
                datas.add(model);
            } //for (int i = 0; i < listData.size(); i++) {
        } //if (listData != null && listData.size() > 0) {
        return datas;
    } //getCaseSetApplyType
}
