package gov.kcg.kgo.repository;

import java.util.List;

import gov.kcg.kgo.model.KgoMydataColumn;
import gov.kcg.kgo.model.KgoMydataColumnPK;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface KgoMydataColumnRepository extends BaseRepository<KgoMydataColumn, KgoMydataColumnPK> {

	/**
	 * 
	 * @param myDataId
	 * @param type
	 * @return
	 */
	public List<KgoMydataColumn> findMyDataColumnAndNameByIdMyDataId(String myDataId);

	/**
	 * 
	 * @param myDataId
	 * @return
	 */
	public List<KgoMydataColumn> findByIdMyDataId(String myDataId);

	/**
	 * 
	 * @param myDataId
	 * @return
	 */
	public List<KgoMydataColumn> findByIdMyDataIdIn(List<String> myDataIds);
}
