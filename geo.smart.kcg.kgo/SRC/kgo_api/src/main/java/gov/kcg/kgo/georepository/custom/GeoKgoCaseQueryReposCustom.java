package gov.kcg.kgo.georepository.custom;

import gov.kcg.kgo.dto.CasesetMemoHotSearchDto;
import gov.kcg.kgo.enums.backend.ApplyTypeEnum;
import gov.kcg.kgo.enums.backend.CaseFlowTypeEnum;
import gov.kcg.kgo.geomodel.GeoCaseColumnModel;
import gov.kcg.kgo.geomodel.GeoCaseSetListDtoModel;
import gov.kcg.kgo.geomodel.GeoCaseSetModel;
import gov.kcg.kgo.geomodel.GeoCivilAffairsDtoModel;
import gov.kcg.kgo.model.KgoCasesetColumn;
import gov.kcg.kgo.model.KgoCasesetColumnPK;
import gov.kcg.kgo.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

;

/**
 * GEO 20210814 add
 * <p>
 * 取得 KGO_CASESET、KGO_CASESET_COLUMN、KGO_CASE_MAIN、KGO_CASE_DETAIL 組合資料
 */

@Repository
public class GeoKgoCaseQueryReposCustom extends GeoBaseReposCustom {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoKgoCaseQueryReposCustom.class);

    /**
     * 依服務編號和時間區間查詢案件清單
     *
     * @param caseSetName
     * @param startTime
     * @param endTime
     * @return
     */
    public List<GeoCaseSetModel> getCaseListByTimeRange(String caseSetName, String startTime, String endTime) {
        //LOGGER.info("GeoKgoCaseRepository getCaseListByTimeRange caseSetName: "+caseSetName);
        String sqlStr = "SELECT cm.CaseId, cs.CaseSetId, cs.CaseSetName, cm.ApplyDate " +
                "FROM KGO_CASESET cs, KGO_CASE_MAIN cm " +
                "WHERE cm.CaseSetId=cs.CaseSetId " +
                "and cs.CaseSetName=:caseSetName " +
                "and (cm.ApplyDate>=convert(datetime, :startTime) and cm.ApplyDate<=convert(datetime, :endTime)) " +
                "order by cm.CaseId ;";

        Query sqlQuery = getEntityManager().createNativeQuery(sqlStr);
        //LOGGER.info("GeoKgoCaseRepository getCaseListByTimeRange sqlStr: "+sqlStr);
        sqlQuery.setParameter("caseSetName", caseSetName);
        sqlQuery.setParameter("startTime", startTime);
        sqlQuery.setParameter("endTime", endTime);

        List listData = sqlQuery.getResultList();
        List<GeoCaseSetModel> datas = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < listData.size(); i++) {
            GeoCaseSetModel dto = new GeoCaseSetModel();
            Object[] recordArray = (Object[]) listData.get(i);
            dto.setCaseId((String) recordArray[0]);
            dto.setCaseSetId((String) recordArray[1]);
            dto.setCaseSetName((String) recordArray[2]);
            dto.setApplyDate((Timestamp) recordArray[3]);
            datas.add(dto);
        }
        //LOGGER.info("GeoKgoCaseRepository getCaseListByTimeRange list size: "+list.size());

        return datas;
    } //getCaseListByTimeRange

    /**
     * 依服務編號和案件編號和取得欄位清單
     *
     * @param caseId
     * @return
     */
    public List<GeoCaseColumnModel> getCaseColumnList(String caseSetId, String caseId) {
        //LOGGER.info("GeoKgoCaseRepository getCaseColumnList caseSetId/caseId: "+caseSetId+" / "+caseId);
        String sqlStr = "SELECT cd.CaseId, csc.ColumnId, csc.ColumnName, cd.ColumnValue, csc.ColumnType " +
                "FROM KGO_CASE_DETAIL cd, KGO_CASESET_COLUMN csc " +
                "WHERE cd.ColumnId=csc.ColumnId and cd.Version=csc.Version " +
                "and csc.CaseSetId=:caseSetId and cd.CaseId=:caseId " +
                "order by csc.ColumnId ;";
        Query sqlQuery = getEntityManager().createNativeQuery(sqlStr);
        //LOGGER.info("GeoKgoCaseRepository getCaseColumnList sqlStr: "+sqlStr);
        sqlQuery.setParameter("caseSetId", caseSetId);
        sqlQuery.setParameter("caseId", caseId);

        List listData = sqlQuery.getResultList();
        List<GeoCaseColumnModel> datas = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < listData.size(); i++) {
            GeoCaseColumnModel dto = new GeoCaseColumnModel();
            Object[] recordArray = (Object[]) listData.get(i);
            dto.setCaseId((String) recordArray[0]);
            dto.setColumnId((String) recordArray[1]);
            dto.setColumnName((String) recordArray[2]);
            dto.setColumnValue((String) recordArray[3]);
            dto.setColumnType((String) recordArray[4]);
            datas.add(dto);
        }
        //LOGGER.info("GeoKgoCaseRepository getCaseColumnList list size: "+list.size());

        return datas;
    } //getCaseListByRange

    /**
     * 依服務編號取得服務名稱
     *
     * @param caseSetId
     * @return
     */
    public String getCaseSetNameByCaseSetId(String caseSetId) {
        //LOGGER.info("GeoKgoCaseRepository getCaseSetNameByCaseSetId caseSetId: "+caseSetId);
        String sqlStr = "SELECT cs.CaseSetName " +
                "FROM KGO_CASESET cs " +
                "WHERE cs.CaseSetId=:caseSetId ;";

        Query sqlQuery = getEntityManager().createNativeQuery(sqlStr);
        //LOGGER.info("GeoKgoCaseRepository getCaseSetNameByCaseSetId sqlStr: "+sqlStr);
        sqlQuery.setParameter("caseSetId", caseSetId);

        String caseSetName = null;
        List listData = sqlQuery.getResultList();
        //LOGGER.info("GeoKgoCaseRepository getCaseSetNameByCaseSetId list size: "+list.size());
        if (listData != null && listData.size() > 0) caseSetName = (String) listData.get(0);
        return caseSetName;
    } //getCaseSetNameByCaseSetId

    /**
     * GEO 20211115 add for 民政局五種服務轉成B流程
     * 依服務編號和時間區間查詢案件清單
     *
     * @param caseSetName
     * @param startTime
     * @param endTime
     * @return
     */
    public List<GeoCivilAffairsDtoModel> getMilitaryServiceCaseListByTimeRange(String caseSetName, String startTime, String endTime) {
        //LOGGER.info("GeoKgoCaseRepository getCaseListByTimeRange caseSetName: "+caseSetName);
        String sqlStr = "SELECT cm.CaseId, cs.CaseSetId, cs.CaseSetName, cm.ApplyDate " +
                "FROM KGO_CASESET cs, KGO_CASE_MAIN cm " +
                "WHERE cm.CaseSetId=cs.CaseSetId " +
                "and cs.CaseSetName=:caseSetName " +
                "and (cm.ApplyDate>=convert(datetime, :startTime) and cm.ApplyDate<=convert(datetime, :endTime)) " +
                "order by cm.CaseId ;";

        Query sqlQuery = getEntityManager().createNativeQuery(sqlStr);
        LOGGER.info("GeoKgoCaseRepository getCaseListByTimeRange sqlStr: " + sqlStr);
        sqlQuery.setParameter("caseSetName", caseSetName);
        sqlQuery.setParameter("startTime", startTime);
        sqlQuery.setParameter("endTime", endTime);

        List listData = sqlQuery.getResultList();
        List<GeoCivilAffairsDtoModel> datas = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < listData.size(); i++) {
            GeoCivilAffairsDtoModel dto = new GeoCivilAffairsDtoModel();
            Object[] recordArray = (Object[]) listData.get(i);
            dto.setCaseId((String) recordArray[0]);
            dto.setCaseSetId((String) recordArray[1]);
            dto.setCaseSetName((String) recordArray[2]);
            dto.setApplyDate(DateUtil.timestampToString((Timestamp) recordArray[3], DateUtil.PATTEN_FULL_TIME));
            datas.add(dto);
        }
        //LOGGER.info("GeoKgoCaseRepository getCaseListByTimeRange list size: "+list.size());

        return datas;
    } //getCaseListByTimeRange


    /**
     * 依關鍵字取得服務案件清單，找出B流程且已上架狀態的案件
     */
    public List<GeoCaseSetListDtoModel> getCaseListByKeyWord(String keyWord) {
        /** GEO 20211201 為府內線上服務調整關鍵字搜尋 add "AND kc.IsOpenForOrgan = 0"*/
        StringBuilder sb = new StringBuilder();
        sb.append("select b.CaseSetId CASE_SET_ID, b.CaseSetName CASE_SET_NAME, b.CaseFlowType CASE_FLOW_TYPE, b.ApplyType APPLY_TYPE, b.Title TITLE, "
                + "b.ContentHtml CONTENT_HTML, b.SerialNum SERIAL_NUM, b.Status STATUS, b.Organ ORGAN_ID, b.ServiceHtml, name ORGAN_NAME "
                + "from (select a.CaseSetId, a.CaseSetName, a.CaseFlowType, a.ApplyType, a.Title, a.ContentHtml, a.SerialNum, a.ServiceHtml, a.Status, a.Organ, b.name "
                + "from ( select ROW_NUMBER() over(partition by kc.CaseSetName order by kc.CaseFlowType desc, CASE WHEN kt.ApplyType= :applyTypeE THEN 1 "
                + "WHEN kt.ApplyType= :applyTypeC THEN 2 "
                + "ELSE 3 END ) as row_index, kc.CaseSetId, kc.CaseSetName, kt.ApplyType, kc.Organ, CONCAT(  kcm.Title,kf.Title)  Title,CONCAT( kcm.ContentHtml,kf.FileName) ContentHtml, kc.CaseFlowType, kc.LinkType, kc.ServiceHtml, kc.LinkUrl, kc.Status, case "
                + "WHEN (kc.CaseSetName LIKE CONCAT('%', :keyword, '%') AND kcm.Title LIKE CONCAT('%', :keyword, '%') AND kcm.ContentHtml LIKE CONCAT('%', :keyword, '%')) THEN 1 "
                + "WHEN (kc.CaseSetName LIKE CONCAT('%', :keyword, '%') AND kcm.Title LIKE CONCAT('%', :keyword, '%')) THEN 2 "
                + "WHEN (kc.CaseSetName LIKE CONCAT('%', :keyword, '%') AND kcm.ContentHtml LIKE CONCAT('%', :keyword, '%')) THEN 3 "
                + "WHEN (kcm.Title LIKE CONCAT('%', :keyword, '%') AND kcm.ContentHtml LIKE CONCAT('%', :keyword, '%')) THEN 4 " + "WHEN (kc.CaseSetName LIKE CONCAT('%', :keyword, '%')) THEN 5 "
                + "WHEN (kcm.Title LIKE CONCAT('%', :keyword, '%')) THEN 6 " + "WHEN (kcm.ContentHtml LIKE CONCAT('%', :keyword, '%')) THEN 7 " + "ELSE 8 END AS SerialNum " + "FROM KGO_CASESET kc "
                + "INNER JOIN KGO_CASESET_type kt on kc.CaseSetId = kt.CaseSetId AND kc.IsOpenForOrgan = 0 AND kc.CaseFlowType LIKE CONCAT ('%', :flowType, '%') LEFT JOIN KGO_CASESET_MEMO kcm " + "ON kc.CaseSetId = kcm.CaseSetId "
                + "AND kt.ApplyType=kcm.ApplyType LEFT JOIN KGO_CASESET_FORM kf on kf.CaseSetId=kc.CaseSetId "
                + "and kt.ApplyType= :applyTypeL ) a left join KGO_GROUP_LEVEL b on a.Organ = b.Seq "
                + "where a.row_index = 1 and a.SerialNum != 8 ) b where  b.Status = 'On' order by b.SerialNum ");

        Query sqlQuery = getEntityManager().createNativeQuery(sb.toString());
        String applyTypeL = ApplyTypeEnum.L.getValue();
        String applyTypeE = ApplyTypeEnum.E.getValue();
        String applyTypeC = ApplyTypeEnum.C.getValue();
        sqlQuery.setParameter("keyword", keyWord);
        sqlQuery.setParameter("applyTypeL", applyTypeL);
        sqlQuery.setParameter("applyTypeE", applyTypeE);
        sqlQuery.setParameter("applyTypeC", applyTypeC);
        sqlQuery.setParameter("flowType", "B");
        LOGGER.info("GeoKgoCaseRepository getCaseListByKeyWord sqlStr: " + sb);
        LOGGER.info("GeoKgoCaseRepository getCaseListByKeyWord applyTypeL: " + applyTypeL);
        LOGGER.info("GeoKgoCaseRepository getCaseListByKeyWord applyTypeE: " + applyTypeE);
        LOGGER.info("GeoKgoCaseRepository getCaseListByKeyWord applyTypeC: " + applyTypeC);

        List listData = sqlQuery.getResultList();
        List<GeoCaseSetListDtoModel> datas = Collections.synchronizedList(new ArrayList<>());
        if (listData != null && listData.size() > 0) {
            LOGGER.info("GeoKgoCaseRepository getCaseListByKeyWord list size: " + listData.size());
            for (int i = 0; i < listData.size(); i++) {
                Object[] recordArray = (Object[]) listData.get(i);
                GeoCaseSetListDtoModel dto = new GeoCaseSetListDtoModel();
                dto.setCaseSetId((String) recordArray[0]);
                dto.setCaseSetName((String) recordArray[1]);
//              dto.setCaseFlowType((String)recordArray[2]); 未使用到
//              dto.setApplyType((String)recordArray[3]); 未使用到
                if (recordArray[4] != null) dto.setTitle((String) recordArray[4]);
                if (recordArray[5] != null) dto.setContentHtml((String) recordArray[5]);
                dto.setOrganId((String) recordArray[8]);
                dto.setServiceHtml((String) recordArray[9]);
                dto.setOrganName((String) recordArray[10]);
                datas.add(dto);
            } //for (int i = 0; i < listData.size(); i++)
        } //if (listData!=null && listData.size()>0)
        return datas;
    } //getCaseListByKeyWord

    /**
     * 找出目前有使用Mydata服務的案件欄位
     */
    public List<KgoCasesetColumnPK> getCaseSetListUseMyDataId() {
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct CaseSetId from KGO_CASESET_COLUMN  where len(MyDataId)<>0 ;");
        Query sqlQuery = getEntityManager().createNativeQuery(sb.toString());
        List<String> listData = sqlQuery.getResultList();
        List<KgoCasesetColumnPK> datas = Collections.synchronizedList(new ArrayList<>());
        if (listData != null && listData.size() > 0) {
            for (int i = 0; i < listData.size(); i++) {
                if (listData.get(i) != null && listData.get(i).length() > 0) {
                    KgoCasesetColumnPK dto = new KgoCasesetColumnPK();
                    dto.setCaseSetId(listData.get(i));
                    datas.add(dto);
                } //if (StringUtils.isNotEmpty(listData.get(i)))
            } //for (int i = 0; i < listData.size(); i++)
        } //if (listData!=null && listData.size()>0)
        LOGGER.info("GeoKgoCaseRepository getCaseSetListUseMyDataId datas size: " + datas.size());
        return datas;
    } //getCaseSetListUseMyDataId
}


