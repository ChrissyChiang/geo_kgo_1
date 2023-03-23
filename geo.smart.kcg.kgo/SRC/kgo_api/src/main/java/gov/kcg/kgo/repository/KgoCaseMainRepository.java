package gov.kcg.kgo.repository;

import gov.kcg.kgo.dto.PendingSignCaseQueryDto;
import gov.kcg.kgo.dto.ReviewedCaseQueryDto;
import gov.kcg.kgo.dto.SaCaseViewQueryDto;
import gov.kcg.kgo.model.KgoCaseMain;
import gov.kcg.kgo.repository.base.BaseRepository;
import gov.kcg.kgo.repository.custom.KgoCaseMainRepositoryCustom;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface KgoCaseMainRepository extends BaseRepository<KgoCaseMain, String>, KgoCaseMainRepositoryCustom {

    /**
     * 依據caseId查找.
     *
     * @param caseId the case id
     * @return the kgo case main
     */
    public KgoCaseMain findByCaseId(String caseId);

    /**
     * 依據caseIds查找.
     *
     * @param caseIdList
     * @return the kgo case main
     */
    public List<KgoCaseMain> findByCaseIdIn(List<String> caseIdList);

    public List<KgoCaseMain> findByReviewerAndStatusIn(String reviewer, List<String> status);

    /**
     * 取得 服務申辦(SA)案件檢視資料
     *
     * @param caseId
     * @return
     */
    @Query(value = "Select new gov.kcg.kgo.dto.SaCaseViewQueryDto(m, s) from KgoCaseMain m Left join KgoCaseset s on m.caseSetId = s.caseSetId where m.caseId=:caseId ")
    public SaCaseViewQueryDto getSaCaseViewData(@Param("caseId") String caseId);

    /**
     * 取得 服務申辦(SA)案件檢視資料清單
     *
     * @param caseIdList
     * @return
     */
    @Query(value = "Select new gov.kcg.kgo.dto.SaCaseViewQueryDto(m, s) from KgoCaseMain m Left join KgoCaseset s on m.caseSetId = s.caseSetId where m.caseId in :caseIdList ")
    public List<SaCaseViewQueryDto> getSaCaseViewDataList(@Param("caseIdList") List<String> caseIdList);

    /**
     * 依服務編號、案件狀態 、 查找案件. Status @CaseMainStatusEnum.W
     *
     * @param status the status
     * @return the list
     */
    @Query(value = "Select m from KgoCaseMain m " + "inner join KgoCaseset cs on cs.caseSetId = m.caseSetId " + "inner join KgoGroupLevel l on l.mainType = 'Organ' and l.seq = cs.organ "
            + "inner join KgoOrgan o on o.organId = l.organId  " + "where m.caseSetId =:caseSetId and m.status= :status ")
    public List<KgoCaseMain> findFlowCaseAndStatus(String caseSetId, String status);

    /**
     * 依服務編號、案件狀態 、 機關代碼找案件. Status @CaseMainStatusEnum.W
     *
     * @param status the status
     * @return the list
     */
    @Query(value = "Select m from KgoCaseMain m " + "inner join KgoCaseset cs on cs.caseSetId = m.caseSetId " + "inner join KgoGroupLevel l on l.mainType = 'Organ' and l.seq = cs.organ "
            + "inner join KgoOrgan o on o.organId = l.organId and o.organId = :organId  " + "where m.caseSetId =:caseSetId and m.status= :status ")
    public List<KgoCaseMain> findFlowCaseByOrganNameAndStatus(String caseSetId, String organId, String status);

    /**
     * 搜尋當天最大值的CaseId
     *
     * @param currentDateStr
     * @return
     */
    @Query(value = "select SUBSTRING(MAX(CaseId),10,5) + 1 \r\n" + "from KGO_CASE_MAIN\r\n" + "where CaseId like CONCAT(:prefix, :currentDateStr,'%')", nativeQuery = true)
    public String findNextCaseIdSuffixStr(@Param("prefix") String prefix, @Param("currentDateStr") String currentDateStr);

    /**
     * 依據processId查找待簽收匣.
     *
     * @param processId
     * @return KgoCaseMain
     */
    @Query(value = "select new gov.kcg.kgo.dto.PendingSignCaseQueryDto(cm, s) " + "from KgoCaseMain cm " + "inner join KgoCaseset s on cm.caseSetId = s.id.caseSetId "
            + "where cm.processId in (:processId)")
    public List<PendingSignCaseQueryDto> getPendingSignCase(@Param("processId") List<String> processId);

    /**
     * 依據processId查找待審核匣, CaseType=SA.
     *
     * @param processId
     * @return KgoCaseMain
     */
    @Query(value = "select new gov.kcg.kgo.dto.PendingSignCaseQueryDto(cm, s) " + "from KgoCaseMain cm " + "inner join KgoCaseset s on cm.caseSetId = s.id.caseSetId "
            + "where cm.processId in (:processId)")
    public List<PendingSignCaseQueryDto> getPendingReviewSACase(@Param("processId") List<String> processId);

    /**
     * 依據processId查找待審核匣, CaseType=SCA,URA. TODO 待修正
     *
     * @param processId
     * @return KgoCaseMain
     */
    @Query(value = "select new gov.kcg.kgo.dto.PendingSignCaseQueryDto(cm, s) " + "from KgoCaseMain cm " + "inner join KgoCaseset s on cm.caseSetId = s.id.caseSetId "
            + "where cm.processId in (:processId)")
    public List<PendingSignCaseQueryDto> getPendingReviewSCAURACase(@Param("processId") List<String> processId);

    /**
     * 依據processId查找已審核匣, CaseType=SA.
     *
     * @param processId
     * @return KgoCaseMain
     */
    @Query(value = "select new gov.kcg.kgo.dto.ReviewedCaseQueryDto(cm, s) " + "from KgoCaseMain cm " + "inner join KgoCaseset s on cm.caseSetId = s.id.caseSetId "
            + "where cm.processId in (:processId) order by cm.updateTime desc")
    public List<ReviewedCaseQueryDto> getReviewedSACase(@Param("processId") List<String> processId);

    /**
     * 依據caseId, idCheck查找.
     *
     * @param caseId
     * @param idCheck
     * @return KgoCaseMain
     */
    @Query(value = "SELECT * FROM KGO_CASE_MAIN WHERE :caseId in (CaseId,OCaseId) " + "AND SUBSTRING(ApplyUser, LEN(ApplyUser)-3, LEN(ApplyUser)) = :idCheck", nativeQuery = true)
    public KgoCaseMain findByCaseIdAndIdCheck(@Param("caseId") String caseId, @Param("idCheck") String idCheck);

    /**
     * 依據caseId, applyUser查找.
     *
     * @param caseId
     * @param applyUser
     * @return KgoCaseMain
     */
    @Query(value = "SELECT * FROM KGO_CASE_MAIN WHERE :caseId in (CaseId,OCaseId) " + "AND ApplyUser=:applyUser", nativeQuery = true)
    public KgoCaseMain findByCaseIdAndApplyUser(@Param("caseId") String caseId, @Param("applyUser") String applyUser);

    /**
     * 依據caseId, email查找.
     *
     * @param caseId
     * @param email
     * @return KgoCaseMain
     */
    @Query(value = "select kcm.* " + "from KGO_CASE_MAIN kcm " + "inner join KGO_CASESET kc on kcm.CaseSetId = kc.CaseSetId " + "left join ( " + "		select ColumnId, CaseId, max(Version) Version "
            + "		from KGO_CASE_DETAIL kcd " + "		where kcd.ColumnId = 'Email' " + "		group by ColumnId , CaseId  " + ") c on c.CaseId = kcm.CaseId "
            + "left join KGO_CASE_DETAIL kcd on c.ColumnId = kcd.ColumnId and c.CaseId = kcd.CaseId and c.Version = kcd.Version "
            + "where kcd.ColumnValue = :email and c.CaseId = :caseId ", nativeQuery = true)
    public KgoCaseMain findByCaseIdAndEmail(String caseId, String email);

    /**
     * 依據caseId, applyUser查找.
     *
     * @param caseId
     * @param cellPhone
     * @return KgoCaseMain
     */
    @Query(value = "SELECT * FROM KGO_CASE_MAIN WHERE :caseId in (CaseId,OCaseId) " + "AND CellPhone=:cellPhone", nativeQuery = true)
    public KgoCaseMain findByCaseIdAndCellPhone(@Param("caseId") String caseId, @Param("cellPhone") String cellPhone);

    /**
     * 案件軌跡紀錄 - 進案時間. caseSetList (前兩名 服務list)
     *
     * @param caseSetList the case set list
     * @param startDate   the start date
     * @param endDate     the end date
     * @return the list
     */
    @Query(value = "select m from KgoCaseMain m where m.caseSetId in :caseSetList and m.applyDate>=:startDate and m.applyDate<=:endDate")
    public List<KgoCaseMain> findByCasetListAndApplyDate(List<String> caseSetList, Timestamp startDate, Timestamp endDate);

    /**
     * 更新
     *
     * @param caseOfficer
     * @param updateUser
     * @return updateTime
     */
//	@Modifying
//	@Transactional(rollbackFor = Exception.class)
//	@Query(value="update KgoCaseMain m set")
//	public KgoCaseMain UpdateByCaseIdAndIdCheck(String caseOfficer, String updateUser, Date date);

    /**
     * Search KGO_CASE_MAIN by casesetId
     *
     * @param caseSetId
     * @return
     */
    List<KgoCaseMain> findByCaseSetId(String caseSetId);

    List<KgoCaseMain> findByCaseSetIdIn(List<String> caseSetIds);

    @Query(value = "SELECT * FROM KGO_CASE_MAIN WHERE :caseId in (CaseId,OCaseId) " + "AND Account=:account", nativeQuery = true)
    public KgoCaseMain findByCaseIdAndAccount(@Param("caseId") String caseId, @Param("account") String account);

    @Query(value = "select m from KgoCaseMain m where m.applyDate >=:applyDate and m.applyUser =:applyUser and m.caseSetId in :caseSetIds and :caseSetId in :caseSetIds ")
    List<KgoCaseMain> findByApplyDateAfterAndApplyUserAndCaseSetIdInAndCaseSetIdInList(Date applyDate, String applyUser, List<String> caseSetIds, String caseSetId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE KgoCaseMain SET cityCoin = ?2 WHERE caseId = ?1")
    int updateCaseMainCityCoin(String caseId,Integer cityCoinAmount);

    @Modifying
    @Transactional
    @Query(value = "UPDATE KgoCaseMain SET status = ?2 WHERE caseId = ?1")
    int updateCaseMainStatus(String caseId,String status);
}
