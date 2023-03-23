package gov.kcg.kgo.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.kcg.kgo.model.KgoHoliday;
import gov.kcg.kgo.repository.base.BaseRepository;

public interface KgoHolidayRepository extends BaseRepository<KgoHoliday, Integer> {

	/**
	 * 查詢區間內所有假日.
	 *
	 * @param startDate the start date
	 * @param endDate   the end date
	 * @return the list
	 */
	@Query(value = "Select kh from KgoHoliday kh where kh.holidayDate >= :startDate and kh.holidayDate <= :endDate")
	public List<KgoHoliday> findHolidayList(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	public Optional<KgoHoliday> findByHolidayDate(Date holidayDate);

}
