package gov.kcg.kgo.enums.backend;

import org.apache.commons.lang3.StringUtils;

/**
 * 後台 - 功能類別(選單) Enum
 * 
 * @author TPIuser
 *
 */
public enum BackendFunctionEnum {
/**
	公告申請				AnnApply
	案件處理				CaseHandle
	系統設定				SystemSetting
	待簽收匣				CsignBox
	已審核匣				CreviewBox
	案件檢視				CaseView
	常用詞庫設定			OWordSet
	案件異動管理			Ctransfer
	服務申請				SCAApply
	分類維護				TypeM
	熱門搜尋管理			HotSearch
	常見問題管理			QAndA
	任務及公告管理			AnnounceM
	機關科室管理			OrganM
	帳號權限管理			AccountM
	案件管理				CaseM
	報表管理				ReportM
	前台使用軌跡統計		FELog
	後台使用軌跡統計		BELog
	案件軌跡統計			CaseLog
* */
	/** 公告申請 */				
	AnnApply("AnnApply"),
	
	/** 案件處理 */				
	CaseHandle("CaseHandle"),
	
	/** 系統設定. */
	SystemSetting("SystemSetting"),
	
	/** 待簽收匣 */				
	CsignBox("CsignBox"),
	
	/** 已審核匣 */				
	CreviewBox("CreviewBox"),
	
	/** 案件檢視. */				
	CaseView("CaseView"),

	/** 常用詞庫設定 */			
	OWordSet("OWordSet"),
	
	/** 案件異動管理. */
	Ctransfer("Ctransfer"),
	
	/** 服務申請 */				
	SCAApply("SCAApply"),

	/** 分類維護 */		
	TypeM("TypeM"),
	
	/** 熱門搜尋管理 */			
	HotSearch("HotSearch"),
	
	/** 常見問題管理 */
	QAndA("QAndA"),
	
	/** 任務及公告管理 */			
	AnnounceM("AnnounceM"),
	
	/** 機關科室管理*/			
	OrganM("OrganM"),
	
	/** 帳號權限管理 */
	AccountM("AccountM"),
	
	/** 案件管理 */
	CaseM("CaseM"),
	
	/** 報表管理 */				
	ReportM("ReportM"),
	
	/** 前台使用軌跡統計 */		
	FELog("FELog"),
	
	/** 後台使用軌跡統計 */		
	BELog("BELog"),
	
	/** 案件軌跡統計 */
	CaseLog("CaseLog");

	/** 功能代碼 */
	private String functionId;

	private BackendFunctionEnum(String functionId ) {
		this.functionId = functionId;
	}

	/**
	 * 取得 後台 - 功能選單類別
	 *
	 */
	public static BackendFunctionEnum getBackendFunctionEnum(String functionId) {
		for (BackendFunctionEnum e : values()) {
			if (StringUtils.equals(e.getFunctionId(), functionId)) {
				return e;
			}
		}
		return null;
	}

	public String getFunctionId() {
		return functionId;
	}
}
