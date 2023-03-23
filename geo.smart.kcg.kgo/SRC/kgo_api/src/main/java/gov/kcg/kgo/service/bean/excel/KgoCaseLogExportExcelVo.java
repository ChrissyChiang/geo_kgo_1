package gov.kcg.kgo.service.bean.excel;

import gov.kcg.kgo.model.KgoCaseMain;
import gov.kcg.kgo.util.DateUtil;

/**
 * 案件軌跡統計 ExcelVo.
 */
public class KgoCaseLogExportExcelVo {
	// 序號
	private Integer order;
	
	/** 案件編號 */
	private String caseId;
	
	/** 服務名稱 */
	private String caseSetName;
	
	/** 入案時間 */
	private String createTime;
	
	/** 民眾ID */
	private String applyUser;
	
	/** 民眾姓名 */
	private String applyUserName;
	
	/** 案件狀態 */
	private String status;

	/** 處理機關 */
	private String organ;

	/** 內容 */
	private String content;

	/** 承辦人 */
	private String caseOffer;

	/** 處理時間 */
	private String dealTime;
	
	public KgoCaseLogExportExcelVo() {}
	
	public KgoCaseLogExportExcelVo(int order, KgoCaseMain main, String caseSetName, String status, String organName, String content, String officer, String dealTime) {
		this.order = order;
		this.caseId = main.getCaseId();
		this.caseSetName = caseSetName;
		this.createTime = DateUtil.timestampToString(main.getCreateTime(), DateUtil.PATTEN_FULL_TIME_SLASH);
		this.applyUser = main.getApplyUser();
		this.applyUserName = main.getApplyUserName();
		this.status = status;
		this.organ = organName;
		this.content = content;
		this.caseOffer = officer;
		this.dealTime = dealTime;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}

	public String getCaseOffer() {
		return caseOffer;
	}

	public void setCaseOffer(String caseOffer) {
		this.caseOffer = caseOffer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDealTime() {
		return dealTime;
	}

	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getCaseSetName() {
		return caseSetName;
	}

	public void setCaseSetName(String caseSetName) {
		this.caseSetName = caseSetName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	public String getApplyUserName() {
		return applyUserName;
	}

	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}
}
