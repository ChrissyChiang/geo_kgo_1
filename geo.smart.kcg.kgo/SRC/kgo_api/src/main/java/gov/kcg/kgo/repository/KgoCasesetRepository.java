package gov.kcg.kgo.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.kcg.kgo.model.KgoCaseset;
import gov.kcg.kgo.repository.base.BaseRepository;
import gov.kcg.kgo.repository.custom.KgoCasesetRepositoryCustom;

public interface KgoCasesetRepository extends BaseRepository<KgoCaseset, String>, KgoCasesetRepositoryCustom {

	/**
	 * 服務案件清單-案件狀態更改 (上架/下架/立即受理)
	 * 
	 * @return int
	 */
	@Modifying
	@Query(value = "UPDATE KgoCaseset SET status = :status, updateUser = :userId, updateTime = :updateTime  WHERE CaseSetId IN :idStr")
	public int updateStatus(@Param("idStr") List<String> idStr, @Param("status") String status,
			@Param("userId") String userId, @Param("updateTime") Timestamp updateTime);

	/**
	 * 搜尋當天最大值的CasesetId
	 * 
	 * @param currentDateStr
	 * @return
	 */
	@Query(value = "select SUBSTRING(MAX(CaseSetId),10,5) + 1 from KGO_CASESET where CaseSetId like CONCAT('%', :currentDateStr,'%')", nativeQuery = true)
	public String findNextCaseSetIdSuffixStr(@Param("currentDateStr") String currentDateStr);

	/**
	 * 取得同機關底下最大+1 的 order 值
	 * 
	 * @param organId
	 * @return
	 */
	@Query(value = "select MAX(Sort)+1 from KGO_CASESET where Organ = :organId group by organ", nativeQuery = true)
	public String findNextOrderByOrganId(@Param("organId") String organId);

	/**
	 * 服務案件-案件下拉式選單用
	 * 
	 * @param organId
	 * @return
	 */
	public List<KgoCaseset> findByOwnerOrganAndStatus(String organId, String Status);

	/**
	 * 根據 KGO_CASESET_TASK.ActivitySeq 找尋 CasesetId
	 * 
	 * @param activitySeq
	 * @return
	 */
	@Query(value = "select c.* from KGO_CASESET c, KGO_CASESET_TASK ct where c.CaseSetId = ct.CaseSetId and ct.ActivitySeq =:activitySeq", nativeQuery = true)
	public List<KgoCaseset> findByIdActivitySeq(@Param("activitySeq") int activitySeq);

	/**
	 * 根據 KGO_TASK_SET.ActivitySeq 找尋 CasesetName
	 * 
	 * @param activitySeq
	 * @return
	 */
	@Query(value = "select STRING_AGG(c.CaseSetName,'、') from  KGO_CASESET_TASK ct \r\n"
			+ "right join KGO_TASK_SET ts on  ts.ActivitySeq = ct.ActivitySeq\r\n"
			+ "left join KGO_CASESET c on ct.CaseSetId = c.CaseSetId\r\n"
			+ "where ts.ActivitySeq = :activitySeq", nativeQuery = true)
	public String findCasesetNameByIdActivitySeq(@Param("activitySeq") int activitySeq);
	
	public List<KgoCaseset> findByFlowId(String flowId);
	@Query(value="SELECT * FROM KGO_CASESET WHERE caseSet_category in(:categorys) AND status = 'On'" ,nativeQuery = true)

	/**
	 * 新增服務類型查詢案件
	 */
	public List<KgoCaseset> findRentalCaseset(@Param("categorys") List<String> categorys);

	/**
	 * 線上租借服務案件名稱模糊搜尋
	 */
	@Query(value="SELECT * FROM KGO_CASESET WHERE caseset_category IN ( 'site', 'activity' ) AND CaseSetName LIKE CONCAT('%', :casesetName, '%')",nativeQuery = true)
	public List<KgoCaseset> findCasesetByCaseName(@Param("casesetName")String casesetName);


}
