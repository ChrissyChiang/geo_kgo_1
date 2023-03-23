package gov.kcg.kgo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.kcg.kgo.dto.MydataQueryDto;
import gov.kcg.kgo.model.KgoMydataResource;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface KgoMydataResourceRepository extends BaseRepository<KgoMydataResource, String> {

	/**
	 * 
	 * @param sType
	 * @return
	 */
	public List<KgoMydataResource> findBySType(String clientId);

	/**
	 * 
	 * @param clientId
	 * @return
	 */
	@Query(value = "select new gov.kcg.kgo.dto.MydataQueryDto(msr, ms, mr) " + "from KgoMydataServiceResource msr "
			+ "right join KgoMydataService ms on msr.id.clientId = ms.id.clientId "
			+ "left join KgoMydataResource mr on msr.id.myDataId = mr.id.myDataId "
			+ "where msr.id.clientId = :clientId ")
	public List<MydataQueryDto> findByClientId(@Param("clientId") String clientId);
}
