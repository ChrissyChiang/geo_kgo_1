package gov.kcg.kgo.geomodel;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;

/**
 * Model for 提供個人預約繳費資訊清單 roy
 */

@ApiModel(description = "提供個人預約繳費資訊清單")
public class PersonalPayModel implements Serializable {
	private static final long serialVersionUID = 1L;

    private String name;
    private String identity;
    private String caseId;
    private String amount;
    private String payexpiredate;
    private String serviceType;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPayexpiredate() {
		return payexpiredate;
	}
	public void setPayexpiredate(String payexpiredate) {
		this.payexpiredate = payexpiredate;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

    
} 
