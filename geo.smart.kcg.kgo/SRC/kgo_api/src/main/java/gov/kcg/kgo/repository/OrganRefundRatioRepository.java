package gov.kcg.kgo.repository;

import java.util.List;

import gov.kcg.kgo.model.OrganRefundRatio;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface OrganRefundRatioRepository extends BaseRepository<OrganRefundRatio, Long> {
	
	public int deleteByOrganId(String organId);

	public List<OrganRefundRatio> findAllByOrganId(String organId);

}
