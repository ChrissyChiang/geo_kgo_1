package gov.kcg.kgo.repository.custom.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import gov.kcg.kgo.dto.*;
import gov.kcg.kgo.geomodel.GeoBidServiceMenuQueryModel;
import gov.kcg.kgo.model.KgoOrgan;
import gov.kcg.kgo.repository.KgoOrganRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ObjectUtils;

import gov.kcg.kgo.enums.backend.ApplyTypeEnum;
import gov.kcg.kgo.enums.backend.PublishStateEnum;
import gov.kcg.kgo.enums.common.CaseTypeEnum;
import gov.kcg.kgo.enums.common.MainTypeEnum;
import gov.kcg.kgo.model.KgoCaseset;
import gov.kcg.kgo.repository.custom.KgoCasesetRepositoryCustom;

public class KgoCasesetRepositoryCustomImpl implements KgoCasesetRepositoryCustom {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(KgoCasesetRepositoryCustomImpl.class);

    @PersistenceContext
    public EntityManager entityManager;
    @Autowired
    KgoOrganRepository kgoOrganRepository;

    /**
     * 服務案件清單 - 案件搜尋 (for 畫面初始)
     */
    @Override
    public List<CaseManagementQueryDto> findAllCase() {
        return findAllCase(null, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
    }

    /**
     * 服務案件清單 - 案件搜尋 (for 案件排序)
     */
    @Override
    public List<CaseManagementQueryDto> findAllCase(Integer organSeq) {
        List<CaseManagementQueryDto> list = findAllCase(organSeq, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
        List<CaseManagementQueryDto> statusIsOn = new ArrayList<CaseManagementQueryDto>();
        for (CaseManagementQueryDto queryDto : list) {
            if (StringUtils.equals(queryDto.getStatus(), PublishStateEnum.ON.getValue()))
                statusIsOn.add(queryDto);
        }
        return statusIsOn;
    }

    /**
     * @param organSeq     機關分類代碼 from KGO_GROUP_LEVEL.seq
     * @param ownerOrgan  權責機關代碼
     * @param caseSetId   案件種類ID
     * @param caseSetName 案件名稱
     * @param userId      管理者ID
     * @return
     */
    @Override
    public List<CaseManagementQueryDto> findAllCase(Integer organSeq, String ownerOrgan, String caseSetId, String caseSetName, String userId) {

        StringBuilder sb = new StringBuilder();

        /**
         * Generate custom query String
         */
        sb.append("with MANAGER_ALL as ( ");
        sb.append("Select k.caseSetId CASESET_ID, k.manager MANAGER, ku.Name NAME, ROW_NUMBER() OVER (PARTITION BY k.caseSetId ORDER BY k.Manager ASC) ROWNUM ");
        sb.append("From KGO_CASESET_MANAGER k Left Join KGO_USER ku On ku.UserId = k.Manager ");
        sb.append("), ");
        sb.append("SIZE_OF_MANAGER_BY_CASE_ID as ( ");
        sb.append("Select CASESET_ID, MAX(rownum) as SIZE ");
        sb.append("From MANAGER_ALL  ");
        sb.append("Group By CASESET_ID ");
        sb.append("), ");
        sb.append("FINAL_MANAGER_DATA as ( ");
        sb.append("Select m.CASESET_ID, m.MANAGER, Case When g.SIZE > 1 then m.NAME + '...' else m.NAME End As NAME ");
        sb.append("From SIZE_OF_MANAGER_BY_CASE_ID g, MANAGER_ALL m ");
        sb.append("where g.SIZE = m.ROWNUM and g.CASESET_ID = m.CASESET_ID ");
        sb.append(") ");
        sb.append(
                "Select c.Sort SORT, c.ServiceTo SERVICE_TO, c.CaseSetId CASESET_ID, c.CaseSetName CASESET_NAME, c.Organ ORGAN_ID, gl.Name ORGAN_NAME, c.OwnerOrgan OWNER_ORGAN_ID, o2.OrganName OWNER_ORGAN_NAME, f.NAME as MANAGER_NAME , c.Status [STATUS], c.CaseType CASE_TYPE, c.FlowId FLOW_Id ");
        sb.append("From KGO_CASESET c ");
        sb.append("Left Join KGO_GROUP_LEVEL gl On c.Organ = gl.Seq ");
        sb.append("Left Join KGO_ORGAN o2 On c.OwnerOrgan = o2.OrganId ");
        sb.append("Left Join FINAL_MANAGER_DATA f on f.CASESET_ID = c.CaseSetId ");
        if (StringUtils.isNotBlank(userId)) {
            sb.append(", MANAGER_ALL m ");
        }
        /** GEO 20221015 排除查詢服務案件 Oberyn */
        sb.append("where ( c.caseSet_category is null or c.caseSet_category !=  'excase' ) ");
        if (!ObjectUtils.isEmpty(organSeq)) {
            sb.append("and c.Organ = :organSeq ");
        }
        if (StringUtils.isNotBlank(ownerOrgan)) {
            sb.append("and c.OwnerOrgan = :ownerOrgan ");
        }
        if (StringUtils.isNotBlank(caseSetId)) {
            sb.append("and c.CaseSetId = :caseSetId ");
        }
        if (StringUtils.isNotBlank(caseSetName)) {
            sb.append("and c.CaseSetName like CONCAT('%', :caseSetName,'%') ");
        }
        if (StringUtils.isNotBlank(userId)) {
            sb.append("and m.CASESET_ID = c.CaseSetId ");
            sb.append("and m.MANAGER = :userId ");
        }
        sb.append("ORDER BY  CASE " + "when c.Status='On' then 1 " + "when c.Status='Off' then 2 " + "ELSE 3 END ,c.Sort ");
//		sb.append("ORDER BY c.UpdateTime desc");

        /**
         * Native Query
         */
        Query query = entityManager.createNativeQuery(sb.toString(), CaseManagementQueryDto.class);
        if (!ObjectUtils.isEmpty(organSeq)) {
            query.setParameter("organSeq", organSeq);
        }
        if (StringUtils.isNotBlank(ownerOrgan)) {
            query.setParameter("ownerOrgan", ownerOrgan);
        }
        if (StringUtils.isNotBlank(caseSetId)) {
            query.setParameter("caseSetId", caseSetId);
        }
        if (StringUtils.isNotBlank(caseSetName)) {
            query.setParameter("caseSetName", caseSetName);
        }
        if (StringUtils.isNotBlank(userId)) {
            query.setParameter("userId", userId);
        }

        return query.getResultList();
    }

    /**
     * 申辦服務選單-初始畫面-申辦案件數查詢
     *
     * @param status
     * @return
     */
    @Override
    public List<BidServiceMenuQueryDto> getBidServiceMenuCaseCountData(String mainType, String status) {

        StringBuilder sb = new StringBuilder();

        // 2020.12.02 問題調整, SQL 有問題
        sb.append(" select c." + mainType + " VALUE , kgl.Name NAME, Count(c." + mainType + ") COUNT from KGO_CASESET c ");
        sb.append(" left join KGO_GROUP_LEVEL kgl on c." + mainType + " = kgl.Seq ");
        sb.append(" where c." + mainType + " in ( ");
        sb.append("     select kgl.seq from KGO_GROUP_LEVEL kgl where kgl.state = :status ");
        sb.append(" ) and  kgl.state = :status and kgl.MainType = :mainType ");
        sb.append(" AND c.Status = 'on' ");
        sb.append(" AND c.IsOpenForOrgan = 0 ");/**GEO 20211019 add */
        sb.append(" group by c." + mainType + ", kgl.Name  ");

//		sb.append(
//				"with temp as (\r\n" + "select c." + mainType + " as VALUE, gl.Name NAME, Count(c.CaseSetId) COUNT\r\n"
//						+ "from KGO_CASESET c\r\n" + "left join KGO_GROUP_LEVEL gl on c.Organ = gl.Seq\r\n"
//						+ "where c.Status<> :status\r\n" + "group by c." + mainType + ", gl.Name\r\n" + ")\r\n"
//						+ "select kgl.seq VALUE, kgl.NAME, isnull(temp.COUNT,0) COUNT\r\n"
//						+ "from KGO_GROUP_LEVEL kgl left join temp on kgl.seq = temp.VALUE\r\n"
//						+ "where kgl.state <> :status and kgl.MainType = :mainType");

        /**
         * Native Query
         */
        Query query = entityManager.createNativeQuery(sb.toString(), BidServiceMenuQueryDto.class);
        query.setParameter("mainType", mainType);
        query.setParameter("status", status);

        return query.getResultList();
    }

//	/**
//	 * 熱門搜尋-關鍵字查巡
//	 *
//	 * @param keyword
//	 * @param pageable
//	 * @return Page<CasesetMemoHotSearchDto>
//	 */
//	@Override
//	public Page<CasesetMemoHotSearchDto> findByKeywordPaged(String keyword, Pageable pageable) {
//		StringBuilder sb = new StringBuilder();
//		sb.append(" from ( " +
//				"select a.CaseSetId, a.ApplyType, a.Title, a.ContentHtml from ( " +
//				"select kcm.CaseSetId, kcm.ApplyType, kcm.Title, kcm.ContentHtml, ROW_NUMBER() over(partition by kcm.CaseSetId order by kcm.Seq Desc ) as row_index " +
//				"from KGO_CASESET kc " +
//				"inner join KGO_CASESET_MEMO kcm on kc.CaseSetId = kcm.CaseSetId " +
//				"where kc.CaseSetName like CONCAT('%', :keyword, '%') or kcm.Title like CONCAT('%', :keyword, '%') or kcm.ContentHtml like CONCAT('%', :keyword, '%') " +
//				") a where a.row_index = 1 ) b inner join KGO_CASESET kc2 on b.CaseSetId = kc2.CaseSetId ");
//		/**
//		 * 取得分頁資料
//		 */
//		StringBuilder columnNameSb = new StringBuilder();
//		columnNameSb.append(
//				"select b.CaseSetId CASE_SET_ID, kc2.CaseSetName CASE_SET_NAME, kc2.CaseFlowType CASE_FLOW_TYPE, b.title TITLE , b.ContentHtml CONTENT_HTML, b.ApplyType APPLY_TYPE ");
//		columnNameSb.append(sb);
//		Query query = entityManager.createNativeQuery(columnNameSb.toString(), CasesetMemoHotSearchDto.class);
//		query.setParameter("keyword", keyword);
//		query.setFirstResult((int) pageable.getOffset());
//		query.setMaxResults(pageable.getPageSize());
//
//		/**
//		 * 取得總筆數
//		 */
//		StringBuilder countSb = new StringBuilder();
//		countSb.append("select count(*) ");
//		countSb.append(sb);
//		Query countQuery = entityManager.createNativeQuery(countSb.toString());
//		countQuery.setParameter("keyword", keyword);
//
//		/**
//		 * 取得結果
//		 */
//		Page<CasesetMemoHotSearchDto> page =
//				new PageImpl<>(query.getResultList(), pageable, Long.valueOf((int) countQuery.getSingleResult()));
//		return page;
//	}

    /**
     * 熱門搜尋-關鍵字查詢
     *
     * @param keyword
     * @param pageable
     * @return Page<CasesetMemoHotSearchDto>
     */
    @Override
    public Page<CasesetMemoHotSearchDto> findByKeywordPaged(String keyword, Pageable pageable) {
        /** GEO 20211201 為府內線上服務調整關鍵字搜尋 add "AND kc.IsOpenForOrgan = 0"*/
        StringBuilder sb = new StringBuilder();
        sb.append(
                " from ( "
                        + "select ROW_NUMBER() over(partition by kc.casesetId order by kc.CaseFlowType desc, CASE "
                        + "WHEN kt.ApplyType= :applyTypeE THEN 1 "
                        + "WHEN kt.ApplyType= :applyTypeC THEN 2 "
                        + "ELSE 3 END ) as row_index, kc.CaseSetId, kc.CaseSetName, kt.ApplyType, kc.Organ, CONCAT(  kcm.Title,kf.Title)  Title,CONCAT( kcm.ContentHtml,kf.FileName) ContentHtml, kc.CaseFlowType, kc.LinkType, kc.LinkUrl, kc.Status, case "
                        + "WHEN (kc.CaseSetName LIKE CONCAT('%', :keyword, '%') AND kcm.Title LIKE CONCAT('%', :keyword, '%') AND kcm.ContentHtml LIKE CONCAT('%', :keyword, '%')) THEN 1 "
                        + "WHEN (kc.CaseSetName LIKE CONCAT('%', :keyword, '%') AND kcm.Title LIKE CONCAT('%', :keyword, '%')) THEN 2 "
                        + "WHEN (kc.CaseSetName LIKE CONCAT('%', :keyword, '%') AND kcm.ContentHtml LIKE CONCAT('%', :keyword, '%')) THEN 3 "
                        + "WHEN (kcm.Title LIKE CONCAT('%', :keyword, '%') AND kcm.ContentHtml LIKE CONCAT('%', :keyword, '%')) THEN 4 "
                        + "WHEN (kc.CaseSetName LIKE CONCAT('%', :keyword, '%')) THEN 5 "
                        + "WHEN (kcm.Title LIKE CONCAT('%', :keyword, '%')) THEN 6 "
                        + "WHEN (kcm.ContentHtml LIKE CONCAT('%', :keyword, '%')) THEN 7 "
                        + "ELSE 8 END AS SerialNum "
                        + "FROM KGO_CASESET kc "
                        + "INNER JOIN KGO_CASESET_type kt on kc.CaseSetId = kt.CaseSetId AND kc.IsOpenForOrgan = 0 LEFT JOIN KGO_CASESET_MEMO kcm "
                        + "ON kc.CaseSetId = kcm.CaseSetId "
                        + "AND kt.ApplyType=kcm.ApplyType LEFT JOIN KGO_CASESET_FORM kf on kf.CaseSetId=kc.CaseSetId and kt.ApplyType= :applyTypeL "
                        + ") a left join KGO_GROUP_LEVEL b on a.Organ = b.Seq where a.row_index = 1 and a.SerialNum != 8 "
                        + ") b where  b.Status = 'On' "
                        + "order by b.SerialNum ");

        /**
         * 取得分頁資料
         */
        StringBuilder columnNameSb = new StringBuilder();
        columnNameSb.append(
                "select b.CaseSetId CASE_SET_ID, b.CaseSetName CASE_SET_NAME, b.CaseFlowType CASE_FLOW_TYPE, b.ApplyType APPLY_TYPE, b.Title TITLE, b.ContentHtml CONTENT_HTML, b.SerialNum SERIAL_NUM, b.Status STATUS, b.Organ ORGAN_ID, name ORGAN_NAME from (select a.CaseSetId, a.CaseSetName, a.CaseFlowType, a.ApplyType, a.Title, a.ContentHtml, a.SerialNum, a.Status, a.Organ, b.name ");
        columnNameSb.append(sb);
        Query query = entityManager.createNativeQuery(columnNameSb.toString(), CasesetMemoHotSearchDto.class);

        LOGGER.info("findByKeywordPaged sql:"+columnNameSb);
        query.setParameter("keyword", keyword);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        String applyTypeL = ApplyTypeEnum.L.getValue();
        String applyTypeE = ApplyTypeEnum.E.getValue();
        String applyTypeC = ApplyTypeEnum.C.getValue();
        query.setParameter("applyTypeL", applyTypeL);
        query.setParameter("applyTypeE", applyTypeE);
        query.setParameter("applyTypeC", applyTypeC);
        /**
         * 取得總筆數
         */
//        StringBuilder countSb = new StringBuilder();
//        countSb.append("select count(*) ");
//        countSb.append(sb);
//        Query countQuery = entityManager.createNativeQuery(countSb.toString());
//        countQuery.setParameter("keyword", keyword);
        Query countQuery = entityManager.createNativeQuery(columnNameSb.toString(), CasesetMemoHotSearchDto.class);
        countQuery.setParameter("keyword", keyword);
        countQuery.setParameter("applyTypeE", applyTypeE);
        countQuery.setParameter("applyTypeC", applyTypeC);
        countQuery.setParameter("applyTypeL", applyTypeL);
        /**
         * 取得結果
         */
        Page<CasesetMemoHotSearchDto> page = new PageImpl<>(query.getResultList(), pageable, Long.valueOf((int) countQuery.getResultList().size()));
        return page;
    }

    /**
     * 申辦案件清單-申辦案件資料查詢
     *
     * @param mainType
     * @param value
     * @return
     */
    @Override
    public List<BidCaseListLinkQueryDto> getBidCaseListData(String mainType, String value) {

        StringBuilder sb = new StringBuilder();

        /**
         * Generate custom query String
         */
        sb.append("select ROW_NUMBER() over( order by c.Sort) SORT,  STRING_AGG(ct.ApplyType, ',') APPLY_TYPE, c.CaseSetId CASESET_ID, c.CaseFlowType CASEFLOW_TYPE, c.CaseSetName CASESET_NAME ,c.caseSet_category CASE_CATEGORY,c.LinkUrl LINK_URL\r\n");
        sb.append("from KGO_CASESET c\r\n");
        sb.append("left join KGO_CASESET_TYPE ct on c.CaseSetId = ct.CaseSetId \r\n");
        sb.append("where \r\n");
        if (mainType.equalsIgnoreCase(MainTypeEnum.ORGAN.getValue())) {
            sb.append("c.Organ = :value\r\n");
        } else if (mainType.equalsIgnoreCase(MainTypeEnum.ROLE.getValue())) {
            sb.append("c.[role] = :value\r\n");
        } else if (mainType.equalsIgnoreCase(MainTypeEnum.SERVICE.getValue())) {
            sb.append("c.[service] = :value\r\n");
        }

        sb.append("and c.IsOpenForOrgan = 0 \r\n");/**GEO 20211019 add */
        sb.append("and c.Status = :status \r\n");
        sb.append("Group by c.CaseSetId, c.CaseFlowType, c.CaseSetName,c.Sort,caseSet_category,c.LinkUrl\r\n");
        // sb.append("Order By c.CaseSetId desc");
        sb.append("Order By SORT");
        /**
         * Native Query
         */
        Query query = entityManager.createNativeQuery(sb.toString(), BidCaseListLinkQueryDto.class);
        query.setParameter("value", value);
        query.setParameter("status", PublishStateEnum.ON.getValue());

        return query.getResultList();
    }

    /**
     * 服務申請-初始畫面-KgoCaseset案件搜尋
     *
     * @param caseSetName
     * @param caseType
     * @return
     */
    @Override
    public List<KgoCaseset> findByCaseTypeAndCaseSetName(String caseSetName, String caseType) {
        ;
        StringBuilder sb = new StringBuilder();

        /**
         * Generate custom query String
         */
        sb.append("select * ");
        sb.append("from KGO_CASESET ");
        sb.append("where CaseType in ('" + CaseTypeEnum.SCA.getValue() + "','" + CaseTypeEnum.URA.getValue() + "') ");
        if (StringUtils.isNotBlank(caseSetName)) {
            sb.append(" and CaseSetName like CONCAT('%', :caseSetName,'%') ");
        }
        if (StringUtils.isNotBlank(caseType)) {
            sb.append(" and CaseType = :caseType ");
        }
        sb.append("order by CaseType Asc, CreateTime Desc");

        /**
         * Native Query
         */
        Query query = entityManager.createNativeQuery(sb.toString(), KgoCaseset.class);
        if (StringUtils.isNotBlank(caseSetName)) {
            query.setParameter("caseSetName", caseSetName);
        }
        if (StringUtils.isNotBlank(caseType)) {
            query.setParameter("caseType", caseType);
        }

        return query.getResultList();
    }

    /**
     * 服務案件清單-初始畫面-機關分類下拉選單
     *
     * @param organId
     * @return
     */
    @Override
    public List<CaseManagerOrganQueryDto> findCasesetOrganByOrgan(String organId) {
        KgoOrgan organ = kgoOrganRepository.findByOrganId(organId);
        String parentOrganId = organId;
        if(organ!=null && Strings.isNotBlank(organ.getParentOrganId()))
            parentOrganId = organ.getParentOrganId();
        StringBuilder sql = new StringBuilder();
        sql.append("select DISTINCT c.Organ OrganSeq, gl.name OrganName from KGO_CASESET c ")
                .append("left join KGO_GROUP_LEVEL gl on gl.Seq = c.Organ ")
                .append("left join KGO_ORGAN o on gl.OrganId = o.OrganId ");
        if (StringUtils.isNotBlank(organId)) {
            sql.append("where o.OrganId= :organId or ParentOrganId= :parentOrganId ");
        }

        Query query = entityManager.createNativeQuery(sql.toString(), CaseManagerOrganQueryDto.class);

        if (StringUtils.isNotBlank(organId)) {
            query.setParameter("organId", organId);
            query.setParameter("parentOrganId", parentOrganId);
        }

        return query.getResultList();
    }
}
