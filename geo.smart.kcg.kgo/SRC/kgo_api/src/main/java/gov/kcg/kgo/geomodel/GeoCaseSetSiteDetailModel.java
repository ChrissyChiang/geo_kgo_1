package gov.kcg.kgo.geomodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
@ApiModel(value = "場地細節")
public class GeoCaseSetSiteDetailModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "預設費用")
    private Integer siteAmount;
    @ApiModelProperty(value = "場地編號")
    private String siteMainId;
    @ApiModelProperty(value = "場地名稱")
    private String siteName;
    @ApiModelProperty(value = "場地介紹內文")
    private String serviceHtml;
    @ApiModelProperty(value = "附件名稱名單: fileName , fileId")
    private List<Map<String,String>> files;

    public Integer getSiteAmount() {
        return siteAmount;
    }

    public void setSiteAmount(Integer siteAmount) {
        this.siteAmount = siteAmount;
    }

    public String getSiteMainId() {
        return siteMainId;
    }

    public void setSiteMainId(String siteMainId) {
        this.siteMainId = siteMainId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getServiceHtml() {
        return serviceHtml;
    }

    public void setServiceHtml(String serviceHtml) {
        this.serviceHtml = serviceHtml;
    }

    public List<Map<String, String>> getFiles() {
        return files;
    }

    public void setFiles(List<Map<String, String>> files) {
        this.files = files;
    }
}
