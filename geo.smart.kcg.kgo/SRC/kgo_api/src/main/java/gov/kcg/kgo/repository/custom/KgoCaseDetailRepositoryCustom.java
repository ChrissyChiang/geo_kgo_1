package gov.kcg.kgo.repository.custom;

import gov.kcg.kgo.dto.CaseCorrectDataDto;
import gov.kcg.kgo.dto.SaCaseViewDetailColumnQueryDto;
import gov.kcg.kgo.dto.GeoCaseByOrganModel;

import java.util.List;

public interface KgoCaseDetailRepositoryCustom {

	/**
	 * 取得服務申辦申請各欄位資料
	 * 
	 * @param caseId
	 * @return
	 */
	List<SaCaseViewDetailColumnQueryDto> getSaCaseDetailData(String caseId);

	/** GEO 20211102 add for 欄位勾選 */
	List<SaCaseViewDetailColumnQueryDto> getDetailData(String caseId);

	List<CaseCorrectDataDto> listCaseCorrectData(String caseId, String caseSetId);
}
