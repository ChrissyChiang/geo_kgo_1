package gov.kcg.kgo.geoviewmodel.backend.rq;

import java.util.List;
import gov.kcg.kgo.viewModel.backend.taskMaintain.editHome.rs.bean.SiteMaintainEditHomeFileViewForm;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "新增或修改場地 rq")
public class GeoSiteSaveRq extends ApiRequest {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "場地/活動類型")
	private String category;

	@ApiModelProperty(value = "場地編號")
	private String siteMainId;
	
	@ApiModelProperty(value = "機關")
	private String organ;

	@ApiModelProperty(value = "科室")
	private String unitId;

	@ApiModelProperty(value = "場地名稱")
	private String siteName;

	@ApiModelProperty(value = "場地租金")
	private Integer siteAmount;

	@ApiModelProperty(value = "場地介紹")
	private String serviceHtml;

	@ApiModelProperty(value = "啟用狀態")
	private Integer siteStatus;
	
	@ApiModelProperty(value = "附件")
	private List<SiteMaintainEditHomeFileViewForm> files;

	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getServiceHtml() {
		return serviceHtml;
	}

	public void setServiceHtml(String serviceHtml) {
		this.serviceHtml = serviceHtml;
	}

	public String getSiteMainId() {return siteMainId;}

	public void setSiteMainId(String siteMainId) {this.siteMainId = siteMainId;}

	public Integer getSiteStatus() {
		return siteStatus;
	}

	public void setSiteStatus(Integer siteStatus) {
		this.siteStatus = siteStatus;
	}

	public List<SiteMaintainEditHomeFileViewForm> getFiles() {
		return files;
	}

	public void setFiles(List<SiteMaintainEditHomeFileViewForm> files) {
		this.files = files;
	}

	public String getCategory() {return category;}

	public void setCategory(String category) {this.category = category;}

	@Override
	public String toString() {
		return "GeoSiteSaveRq{" +
				"siteMainId=" + siteMainId +
				", organ='" + organ + '\'' +
				", unitId='" + unitId + '\'' +
				", siteName='" + siteName + '\'' +
				", siteAmount=" + siteAmount +
				", serviceHtml='" + serviceHtml + '\'' +
				", siteStatus=" + siteStatus +
				", files=" + files +
				'}';
	}
}
