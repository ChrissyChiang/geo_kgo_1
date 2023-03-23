package gov.kcg.kgo.repository.custom;

import java.util.List;

import gov.kcg.kgo.dto.AccountManagementEditDto;
import gov.kcg.kgo.dto.AccountManagementQueryDto;
import gov.kcg.kgo.dto.KeywordCountDto;

public interface KgoKeywordsRepositoryCustom {

	/**
	 * 關鍵字統計
	 * 
	 * @return
	 */
	List<KeywordCountDto> countKeyword();

}
