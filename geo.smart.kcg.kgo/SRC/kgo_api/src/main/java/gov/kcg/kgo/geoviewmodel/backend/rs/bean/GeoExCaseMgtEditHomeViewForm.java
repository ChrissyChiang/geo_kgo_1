package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.viewModel.backend.caseManagement.editHome.rs.bean.CityCoinTask;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.checkBox.CheckBox;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
/**
 * 查詢服務管理-新增/編輯畫面 View Form
 */
@ApiModel(description = "查詢服務管理-新增/編輯畫面 View Form")
public class GeoExCaseMgtEditHomeViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服務案件編號")
    private String caseSetId;

    @ApiModelProperty(value = "案件名稱")
    private String caseSetName;

    @ApiModelProperty(value = "站外連結網址")
    private String linkUrl;

    @ApiModelProperty(value = "機關分類")
    private ComboBox organComboBox;

    @ApiModelProperty(value = "角色分類")
    private ComboBox roleComboBox;

    @ApiModelProperty(value = "服務分類")
    private ComboBox serviceComboBox;

    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

    public String getCaseSetName() {
        return caseSetName;
    }

    public void setCaseSetName(String caseSetName) {
        this.caseSetName = caseSetName;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public ComboBox getOrganComboBox() {
        return organComboBox;
    }

    public void setOrganComboBox(ComboBox organComboBox) {
        this.organComboBox = organComboBox;
    }

    public ComboBox getRoleComboBox() {
        return roleComboBox;
    }

    public void setRoleComboBox(ComboBox roleComboBox) {
        this.roleComboBox = roleComboBox;
    }

    public ComboBox getServiceComboBox() {
        return serviceComboBox;
    }

    public void setServiceComboBox(ComboBox serviceComboBox) {
        this.serviceComboBox = serviceComboBox;
    }
}
