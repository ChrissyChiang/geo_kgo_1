package gov.kcg.kgo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.kcg.kgo.dto.KgoCasesetFormQueryDto;
import gov.kcg.kgo.model.KgoCasesetForm;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface KgoCasesetFormRepository extends BaseRepository<KgoCasesetForm, Integer> {

	/**
	 * 表單下載-範本下載
	 * 
	 * @return List<KgoCaseSetForm>
	 */
	public KgoCasesetForm findBySeq(Integer seq);

	/**
	 * 
	 * @return
	 */
	public List<KgoCasesetForm> findByCaseSetId(String caseSetId);

	/**
	 * 新增資料
	 * 
	 * @param caseSetId
	 * @param seq
	 * @param type
	 * @param title
	 * @param fileName
	 * @return
	 */
	@Modifying
	@Query(value = "insert into KGO_CASESET_FORM (CaseSetId, Type, Title, FileName) values (:caseSetId, :type, :title, :fileName)", nativeQuery = true)
	public int saveData(@Param("caseSetId") String caseSetId, @Param("type") String type, @Param("title") String title,
			@Param("fileName") String fileName);

	/**
	 * 取得書表下載的檔案資料
	 * 
	 * @param caseSetId
	 * @param codeType
	 * @return
	 */
	@Query(value = "select new gov.kcg.kgo.dto.KgoCasesetFormQueryDto(cf, c) "
			+ "from KgoCasesetForm cf left join KgoCode c on cf.type = c.id.codeId "
			+ "where c.id.codeType = :codeType and cf.caseSetId = :caseSetId ")
	public List<KgoCasesetFormQueryDto> getFormDownloadDetailData(@Param("caseSetId") String caseSetId,
			@Param("codeType") String codeType);

	int deleteByCaseSetId(String caseSetId);
}
