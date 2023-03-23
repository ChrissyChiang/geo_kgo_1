package gov.kcg.kgo.repository.custom;

import java.util.List;

import gov.kcg.kgo.dto.EventNewsHomeDataGridDto;
import gov.kcg.kgo.dto.TaskAndAnnounceQueryDto;
import gov.kcg.kgo.dto.TaskSetQueryByActivitySeqDto;
import gov.kcg.kgo.dto.TaskSetQueryByCaseSetIdDto;

public interface KgoTaskSetRepositoryCustom {

	/**
	 * 服務案件清單-案件維護-任務查詢
	 * 
	 * @param caseSetId
	 * @return
	 */
	public List<TaskSetQueryByCaseSetIdDto> findTaskByCaseSetId(String caseSetId);

	/**
	 * 任務及公告管理-任務以及公告查詢
	 * 
	 * @param name
	 * @param publishTimeStart
	 * @param publishTimeEnd
	 * @return
	 */
	List<TaskAndAnnounceQueryDto> findTaskAndAnnounceData(String name, String type, String publishTimeStart,
			String publishTimeEnd);

	/**
	 * 任務及公告管理-任務維護(新增/修改)-城市幣任務查詢
	 * 
	 * @param activitySeq
	 * @return
	 */
	List<TaskSetQueryByActivitySeqDto> findTaskByActivitySeq(int activitySeq);

	/**
	 * 活動消息-初始畫面
	 * 
	 * @param isPublish
	 * @return
	 */
	List<EventNewsHomeDataGridDto> eventNewsfindTaskAndAnnounceData(boolean isPublish, String releaseTo);

}
