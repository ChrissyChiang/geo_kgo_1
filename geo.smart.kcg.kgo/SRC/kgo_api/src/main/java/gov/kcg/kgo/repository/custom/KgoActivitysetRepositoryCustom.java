package gov.kcg.kgo.repository.custom;

import java.util.List;

import gov.kcg.kgo.dto.ActivityMaintainQueryDto;

public interface KgoActivitysetRepositoryCustom {

	/**
	 * 問題資料搜尋
	 * 
	 * @param question
	 * @param publishDateStart
	 * @param publishDateEnd
	 * @return
	 */
	public List<ActivityMaintainQueryDto> findCustomData(String activityName, String publishTimeStart,
			String publishTimeEnd);

}
