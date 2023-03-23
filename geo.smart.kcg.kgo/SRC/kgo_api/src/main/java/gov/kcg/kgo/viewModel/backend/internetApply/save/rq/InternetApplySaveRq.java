package gov.kcg.kgo.viewModel.backend.internetApply.save.rq;

import java.util.List;
import gov.kcg.kgo.geomodel.GeoCaseSetRefundModel;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "網路申辦-案件儲存 rq")
public class InternetApplySaveRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "服務案件編號")
	private String caseSetId;

	@ApiModelProperty(value = "是否啟用服務宣告")
	private Boolean isServiceHtml;

	@ApiModelProperty(value = "服務宣告設定")
	private String serviceHtml;

	@ApiModelProperty(value = "受理設定")
	private String acceptSet;

	@ApiModelProperty(value = "案件受理區間(起)")
	private String dataStart;

	@ApiModelProperty(value = "案件受理區間(迄)")
	private String dataEnd;

	@ApiModelProperty(value = "限辦期限")
	private Integer limitedPeriod;

	@ApiModelProperty(value = "郵件")
	private String mail;

	@ApiModelProperty(value = "身份驗證設定(用逗號區隔)")
	private String checkType;

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public Boolean getIsServiceHtml() {
		return isServiceHtml;
	}

	public void setIsServiceHtml(Boolean isServiceHtml) {
		this.isServiceHtml = isServiceHtml;
	}

	public String getServiceHtml() {
		return serviceHtml;
	}

	public void setServiceHtml(String serviceHtml) {
		this.serviceHtml = serviceHtml;
	}

	public String getAcceptSet() {
		return acceptSet;
	}

	public void setAcceptSet(String acceptSet) {
		this.acceptSet = acceptSet;
	}

	public String getDataStart() {
		return dataStart;
	}

	public void setDataStart(String dataStart) {
		this.dataStart = dataStart;
	}

	public String getDataEnd() {
		return dataEnd;
	}

	public void setDataEnd(String dataEnd) {
		this.dataEnd = dataEnd;
	}

	public Integer getLimitedPeriod() {
		return limitedPeriod;
	}

	public void setLimitedPeriod(Integer limitedPeriod) {
		this.limitedPeriod = limitedPeriod;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

}
