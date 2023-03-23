package gov.kcg.kgo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import gov.kcg.kgo.model.TpiFlow;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface TpiFlowRepository extends BaseRepository<TpiFlow, String> {
	
	/**
	 * 更新avtiviti流程.
	 *
	 * @param flowDefId the flow def id
	 * @param flowEnable the flow enable
	 * @param flowId the flow id
	 * @return the int
	 */
	@Modifying
	@Query(value = "Update TpiFlow f SET f.flowDefId = :flowDefId, f.flowEnable =:flowEnable where f.flowId = :flowId")
	public int updateFlowByFlowId(String flowDefId, String flowEnable, String flowId);
	
	/**
	 * 查找流程 Y啟用/N停用, organId.
	 *
	 * @param flowEnable the flow enable
	 * @return the list
	 */
	public List<TpiFlow> findByFlowEnableAndOrganIdAndIsDefault(String flowEnable, String organId, Boolean isDefault);
	
	/**
	 * 動態流程畫面初始.
	 *
	 * @param organId the organ id
	 * @param enable the enable
	 * @param isDefault the is default
	 * @return the list
	 */
	@Query(value = "SELECT f FROM TpiFlow f WHERE f.organId = :organId AND f.flowEnable =:enable OR (f.isDefault = :isDefault) ORDER BY f.isDefault desc, f.createTime ")
	public List<TpiFlow> findByOrganIdAndFlowEnableAndIsDefaultOrderByCreateTimeDesc(String organId, String enable, Boolean isDefault);

//	/**
//	 * 動態流程畫面初始(系統管理者).
//	 *
//	 * @param organId the organ id
//	 * @param enable the enable
//	 * @param isDefault the is default
//	 * @return the list
//	 */
//	@Query(value = "SELECT f FROM TpiFlow f WHERE f.flowEnable =:enable OR f.isDefault = :isDefault ORDER BY f.updateTime")
//	public List<TpiFlow> findByFlowEnableAndIsDefaultOrderByUpdateTimeDesc(String organId, String enable, Boolean isDefault);

}
