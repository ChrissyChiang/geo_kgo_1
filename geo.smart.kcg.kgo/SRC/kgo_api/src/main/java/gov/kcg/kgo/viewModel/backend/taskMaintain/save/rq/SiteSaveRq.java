package gov.kcg.kgo.viewModel.backend.taskMaintain.save.rq;

import java.util.List;

import gov.kcg.kgo.viewModel.backend.taskMaintain.editHome.rs.bean.TaskMaintainEditHomeFileViewForm;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

// Roy
@ApiModel(description = "新增場地 rq")
public class SiteSaveRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	private Long siteMainId;
	private String organId;
	private String unitId;
	private String siteName;
	private Integer siteAmount;
	String serviceHtml;
	Integer siteStatus;
	public Long getSiteMainId() {
		return siteMainId;
	}
	public void setSiteMainId(Long siteMainId) {
		this.siteMainId = siteMainId;
	}
	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public Integer getSiteAmount() {
		return siteAmount;
	}
	public void setSiteAmount(Integer siteAmount) {
		this.siteAmount = siteAmount;
	}
	public String getServiceHtml() {
		return serviceHtml;
	}
	public void setServiceHtml(String serviceHtml) {
		this.serviceHtml = serviceHtml;
	}
	public Integer getSiteStatus() {
		return siteStatus;
	}
	public void setSiteStatus(Integer siteStatus) {
		this.siteStatus = siteStatus;
	}

}
