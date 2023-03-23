package gov.kcg.kgo.georepository;

import java.util.List;
import gov.kcg.kgo.geoentity.OrganDiscountRatio;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface GeoOrganDiscountRatioRepository extends BaseRepository<OrganDiscountRatio, Long> {
	
	public int deleteByOrganId(String organId);

	public List<OrganDiscountRatio> findAllByOrganIdOrderByDiscountRatio(String organId);

	OrganDiscountRatio findByOrganIdAndDiscountRatio(String organId, Integer discountRatio);
}
