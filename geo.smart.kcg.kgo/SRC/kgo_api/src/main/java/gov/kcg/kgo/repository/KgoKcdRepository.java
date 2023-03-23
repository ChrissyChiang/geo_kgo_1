package gov.kcg.kgo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import gov.kcg.kgo.dto.KgoKcdDto;
import gov.kcg.kgo.model.KgoKcd;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface KgoKcdRepository extends BaseRepository<KgoKcd, String> {

	@Query(value = "select new gov.kcg.kgo.dto.KgoKcdDto(u, o) "
			+ "from KgoKcd u left join KgoZip o on u.krmk = o.krmk ")
	public List<KgoKcdDto> findByAll();

}
