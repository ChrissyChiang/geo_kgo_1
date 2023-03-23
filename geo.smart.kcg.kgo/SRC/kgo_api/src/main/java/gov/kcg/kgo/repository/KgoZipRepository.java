package gov.kcg.kgo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import gov.kcg.kgo.dto.KgoZipF3AreaDto;
import gov.kcg.kgo.model.KgoZip;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface KgoZipRepository extends BaseRepository<KgoZip, String> {

	/**
	 * 
	 * @param countyId
	 * @return
	 */
	public List<KgoZip> findByCountyId(String countyId);

	@Query(value = "select new gov.kcg.kgo.dto.KgoZipF3AreaDto(cf, c) " + "from KgoZip cf inner join KgoF3area c on cf.f3Seq = c.f3Seq ")
	public List<KgoZipF3AreaDto> findByZipF3AreaList();

}
