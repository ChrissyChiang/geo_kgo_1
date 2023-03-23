package gov.kcg.kgo.repository.custom;

import java.util.List;

import gov.kcg.kgo.dto.AccountManagementEditDto;
import gov.kcg.kgo.dto.AccountManagementQueryDto;

public interface KgoUserRepositoryCustom {

	/**
	 * 帳號權限管理-帳號搜尋
	 * 
	 * @param organId   機關代碼
	 * @param unitId    單位代碼
	 * @param name 		使用者姓名
	 * @param userId   	使用者帳號
	 * @param roleId   	角色代碼
	 * @return List
	 */
	public List<AccountManagementQueryDto> findAccountQueryData();
	
	/**
	 * 帳號權限管理-帳號搜尋
	 * 
	 * @param organId   機關代碼
	 * @param unitId    單位代碼
	 * @param name 		使用者姓名
	 * @param userId   	使用者帳號
	 * @param roleId   	角色代碼
	 * @return List
	 */
	public List<AccountManagementQueryDto> findAccountQueryData(String organId, String unitId, String name,
			String userId, String roleId);

	/**
	 * GEO 20211115 add 非市府員工登入後台
	 * 帳號權限管理-帳號搜尋
	 * @param userId
	 * @return
	 */
	public List<AccountManagementQueryDto> findAccountQueryDataByUserId(String userId);
	

	/**
	 * 帳號權限管理-帳號維護(新增/修改)-資料搜尋
	 * 
	 * @param userId   	使用者帳號
	 * @return List
	 */
	public List<AccountManagementEditDto> findAccountEditData(String userId);

}
