package gov.kcg.kgo.repository.custom;

import java.util.List;

import gov.kcg.kgo.model.KgoCommonWord;

public interface KgoCommonWordRepositoryCustom {

	/**
	 * 依據詞彙來搜尋資料
	 * 
	 * @param type
	 * @return List<KgoGroupLevel>
	 */
	public List<KgoCommonWord> findSeqAndTitleByTitleLikeOrderByUpdateTimeDesc(String title);
}
