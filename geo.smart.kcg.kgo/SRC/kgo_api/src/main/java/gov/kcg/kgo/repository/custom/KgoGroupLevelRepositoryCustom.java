package gov.kcg.kgo.repository.custom;

import java.util.List;

import gov.kcg.kgo.dto.ClassManagementClassQueryDto;

public interface KgoGroupLevelRepositoryCustom {

	/**
	 * 分類維護主畫面初始化
	 * 
	 * @return List
	 */
	public List<ClassManagementClassQueryDto> findKgoGroupLevelData();

	/**
	 * 分類維護主畫面搜尋
	 * 
	 * @param seq
	 * @param mainType
	 * @param publishTimeStart
	 * @param publishTimeEnd
	 * @return
	 */
	public List<ClassManagementClassQueryDto> findKgoGroupLevelData(Integer seq, String name, String mainType,
			String publishTimeStart, String publishTimeEnd);

}
