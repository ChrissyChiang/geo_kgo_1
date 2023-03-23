package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@ApiModel(description = "場地新增/編輯 View Form")
public class GeoSiteEditViewForm extends BaseViewForm {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "場地/活動下拉")
    private ComboBox categoryComboBox;
    @ApiModelProperty(value = "機關分類下拉")
    private ComboBox organComboBox;
    @ApiModelProperty(value = "科室分類下拉")
    private ComboBox unitComboBox;
    @ApiModelProperty(value = "預設費用")
    private Integer siteAmount;
    @ApiModelProperty(value = "場地編號")
    private String siteMainId;
    @ApiModelProperty(value = "場地名稱")
    private String siteName;
    @ApiModelProperty(value = "上架/下架狀態")
    private Integer siteStatus;
    @ApiModelProperty(value = "場地介紹內文")
    private String serviceHtml;
    @ApiModelProperty(value = "附件名稱名單: fileName , fileId")
    private List<Map<String,String>> files;



    public ComboBox getCategoryComboBox() {return categoryComboBox; }

    public void setCategoryComboBox(ComboBox categoryComboBox) {this.categoryComboBox = categoryComboBox;}

    public ComboBox getOrganComboBox() {
        return organComboBox;
    }

    public void setOrganComboBox(ComboBox organComboBox) {
        this.organComboBox = organComboBox;
    }

    public ComboBox getUnitComboBox() {
        return unitComboBox;
    }

    public void setUnitComboBox(ComboBox unitComboBox) {
        this.unitComboBox = unitComboBox;
    }

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

    public Integer getSiteStatus() {
        return siteStatus;
    }

    public void setSiteStatus(Integer siteStatus) {
        this.siteStatus = siteStatus;
    }

    public String getServiceHtml() {
        return serviceHtml;
    }

    public void setServiceHtml(String serviceHtml) {
        this.serviceHtml = serviceHtml;
    }

    public List<Map<String, String>> getFiles() {return files;}

    public void setFiles(List<Map<String, String>> files) {this.files = files;}
}
