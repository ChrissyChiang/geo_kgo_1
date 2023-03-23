package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the KGO_CASESET database table.
 * 
 */
@Entity
@Table(name = "KGO_CASESET")
@NamedQuery(name = "KgoCaseset.findAll", query = "SELECT k FROM KgoCaseset k")
public class KgoCaseset implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CaseSetId", unique = true, nullable = false, length = 30)
	private String caseSetId;

	@Column(name = "ServiceTo", length = 30)
	private String serviceTo;

	@Column(name = "CaseSetName", length = 200)
	private String caseSetName;

	@Column(name = "CaseType", length = 50)
	private String caseType;

	@Column(name = "CaseFlowType", length = 50)
	private String caseFlowType;

	@Column(name = "LinkType", length = 50)
	private String linkType;

	@Column(name = "LinkUrl", length = 500)
	private String linkUrl;

	@Column(name = "Role", length = 50)
	private String role;

	@Column(name = "Service", length = 50)
	private String service;

	@Column(name = "Organ", length = 50)
	private String organ;

	@Column(name = "OwnerOrgan", length = 50)
	private String ownerOrgan;

	@Column(name = "ServiceHtml")
	private String serviceHtml;

	@Column(name = "IsServiceHtml", columnDefinition = "BIT")
	private Boolean isServiceHtml;

	@Column(name = "Status", length = 30)
	private String status;

	@Column(name = "AcceptSet", length = 30)
	private String acceptSet;

	@Column(name = "DateStart", length = 30)
	private String dateStart;

	@Column(name = "DateEnd", length = 30)
	private String dateEnd;

	@Column(name = "LimitedPeriod")
	private Integer limitedPeriod;

	@Column(name = "Sort")
	private Integer sort;

	@Column(name = "CreateUser", length = 50)
	private String createUser;

	@Column(name = "CreateTime")
	private Timestamp createTime;

	@Column(name = "UpdateUser", length = 50)
	private String updateUser;

	@Column(name = "UpdateTime")
	private Timestamp updateTime;

	@Column(name = "Mail", length = 500)
	private String mail;
	
	@Column(name = "activeFlow")
	private Boolean activeFlow;
	
	@Column(name = "caseset_category")
	private String casesetCategory;
	
	@Column(name = "needPay")
	private Boolean needPay;	
	
	// 動態流程 flowId
	@Column(name="FlowId")
	private String flowId;

	/**GEO 20211019 add */
	// 是否開啟府內線上服務
	@Column(name="IsOpenForOrgan")
	private Integer isOpenForOrgan;

	/**GEO 20211109 add */
	// 是否啟用機關審核表單
	@Column(name="IsOpenOrganForm")
	private Integer isOpenOrganForm;

	/**GEO 20211115 add 跨機關檢視*/
	// 是否啟用跨機關檢視
	@Column(name="IsAvailableCrossReview")
	private Boolean isAvailableCrossReview;
	
	@Column(name = "pay_deadline")
	private Integer payDeadline;
	
	@Column(name = "refund_deadline")
	private Integer refundDeadline;

	@Column(name="CityCoin")
	private Boolean cityCoin;

	public KgoCaseset() {
	}

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public String getServiceTo() {
		return serviceTo;
	}

	public void setServiceTo(String serviceTo) {
		this.serviceTo = serviceTo;
	}

	public String getCaseSetName() {
		return caseSetName;
	}

	public void setCaseSetName(String caseSetName) {
		this.caseSetName = caseSetName;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getCaseFlowType() {
		return caseFlowType;
	}

	public void setCaseFlowType(String caseFlowType) {
		this.caseFlowType = caseFlowType;
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}

	public String getOwnerOrgan() {
		return ownerOrgan;
	}

	public void setOwnerOrgan(String ownerOrgan) {
		this.ownerOrgan = ownerOrgan;
	}

	public String getServiceHtml() {
		return serviceHtml;
	}

	public void setServiceHtml(String serviceHtml) {
		this.serviceHtml = serviceHtml;
	}

	public Boolean getIsServiceHtml() {
		return isServiceHtml;
	}

	public void setIsServiceHtml(Boolean isServiceHtml) {
		this.isServiceHtml = isServiceHtml;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAcceptSet() {
		return acceptSet;
	}

	public void setAcceptSet(String acceptSet) {
		this.acceptSet = acceptSet;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public Integer getLimitedPeriod() {
		return limitedPeriod;
	}

	public void setLimitedPeriod(Integer limitedPeriod) {
		this.limitedPeriod = limitedPeriod;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public Integer getIsOpenForOrgan() {
		return isOpenForOrgan;
	}

	public void setIsOpenForOrgan(Integer isInsideOpen) {
		this.isOpenForOrgan = isInsideOpen;
	}

	public Integer getIsOpenOrganForm() {
		return isOpenOrganForm;
	}

	public void setIsOpenOrganForm(Integer isOpenOrganForm) {
		this.isOpenOrganForm = isOpenOrganForm;
	}

	public Boolean getAvailableCrossReview() {
		return isAvailableCrossReview;
	}

	public void setAvailableCrossReview(Boolean availableCrossReview) {
		isAvailableCrossReview = availableCrossReview;
	}

	public Boolean getActiveFlow() {
		return activeFlow;
	}

	public void setActiveFlow(Boolean activeFlow) {
		this.activeFlow = activeFlow;
	}

	public Boolean getIsAvailableCrossReview() {
		return isAvailableCrossReview;
	}

	public void setIsAvailableCrossReview(Boolean isAvailableCrossReview) {
		this.isAvailableCrossReview = isAvailableCrossReview;
	}

	public String getCasesetCategory() {
		return casesetCategory;
	}

	public void setCasesetCategory(String caseSetCategory) {
		this.casesetCategory = caseSetCategory;
	}

	public Integer getPayDeadline() {
		return payDeadline;
	}

	public void setPayDeadline(Integer payDeadline) {
		this.payDeadline = payDeadline;
	}

	public Integer getRefundDeadline() {
		return refundDeadline;
	}

	public void setRefundDeadline(Integer refundDeadline) {
		this.refundDeadline = refundDeadline;
	}

	public Boolean getNeedPay() {
		return needPay;
	}

	public void setNeedPay(Boolean needPay) {
		this.needPay = needPay;
	}

	public Boolean getCityCoin() {
		return cityCoin;
	}

	public void setCityCoin(Boolean cityCoin) {
		this.cityCoin = cityCoin;
	}
}
