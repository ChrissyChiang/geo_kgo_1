package gov.kcg.kgo.repository;

import java.util.List;

import gov.kcg.kgo.model.KgoCode;
import gov.kcg.kgo.model.KgoCodePK;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface KgoCodeRepository extends BaseRepository<KgoCode, KgoCodePK> {

	/**
	 * 根據 codeType 取得 KgoCode 資料
	 * 
	 * @param codeType
	 * @return
	 */
	public List<KgoCode> findByIdCodeType(String codeType);

}
