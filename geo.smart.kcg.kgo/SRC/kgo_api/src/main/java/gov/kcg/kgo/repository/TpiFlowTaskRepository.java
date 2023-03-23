package gov.kcg.kgo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;

import gov.kcg.kgo.model.TpiFlowTask;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface TpiFlowTaskRepository extends BaseRepository<TpiFlowTask, String> {
	
	@Modifying
	public int deleteByTaskSeqIn(List<Integer> taskSeqList);
	
	@Modifying
	public int deleteByFlowId(String flowId);

	public List<TpiFlowTask> findByFlowIdOrderByTaskOrder(String flowId);

}
