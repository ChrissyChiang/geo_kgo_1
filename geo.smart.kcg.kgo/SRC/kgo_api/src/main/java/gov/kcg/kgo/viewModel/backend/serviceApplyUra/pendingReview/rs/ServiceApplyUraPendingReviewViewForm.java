package gov.kcg.kgo.viewModel.backend.serviceApplyUra.pendingReview.rs;

import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.checkBox.CheckBox;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import java.sql.Timestamp;
import java.util.List;

@ApiModel(description = "後台案件處理-待審核匣-檢視")
public class ServiceApplyUraPendingReviewViewForm extends BaseViewForm {

    @ApiModelProperty(notes = "案件編號")
    private String caseId;

    @ApiModelProperty(notes = "機關")
    private String applyOrgan;

    @ApiModelProperty(notes = "角色")
    private List<CheckBox> applyRoles;

    @ApiModelProperty(notes = "科室")
    private String applyUnit;

    @ApiModelProperty(notes = "申請人")
    private String applyUser;

    @ApiModelProperty(notes = "電子郵件")
    private String email;

    @ApiModelProperty(notes = "電話")
    private String phone;

    @ApiModelProperty(notes = "主管")
    private ComboBox reviewer;

    @ApiModelProperty(notes = "案件歷程")
    private List<ServiceApplyUraPendingReviewHistoryDataGrid> serviceApplyUraPendingReviewHistoryDataGrid;

    @ApiModelProperty(notes = "歷程圖")
    private String image;

    /** ura階段 */
    @ApiModelProperty(value = "ura階段")
    private String uraStage;

    /** manager1 */
    @ApiModelProperty(value = "manager1")
    private String manager1;

    /** manager2 */
    @ApiModelProperty(value = "manager2")
    private String manager2;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getApplyOrgan() {
        return applyOrgan;
    }

    public void setApplyOrgan(String applyOrgan) {
        this.applyOrgan = applyOrgan;
    }

    public List<CheckBox> getApplyRoles() {
        return applyRoles;
    }

    public void setApplyRoles(List<CheckBox> applyRoles) {
        this.applyRoles = applyRoles;
    }

    public String getApplyUnit() {
        return applyUnit;
    }

    public void setApplyUnit(String applyUnit) {
        this.applyUnit = applyUnit;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ComboBox getReviewer() {
        return reviewer;
    }

    public void setReviewer(ComboBox reviewer) {
        this.reviewer = reviewer;
    }

    public List<ServiceApplyUraPendingReviewHistoryDataGrid> getServiceApplyUraPendingReviewHistoryDataGrid() {
        return serviceApplyUraPendingReviewHistoryDataGrid;
    }

    public void setServiceApplyUraPendingReviewHistoryDataGrid(List<ServiceApplyUraPendingReviewHistoryDataGrid> serviceApplyUraPendingReviewHistoryDataGrid) {
        this.serviceApplyUraPendingReviewHistoryDataGrid = serviceApplyUraPendingReviewHistoryDataGrid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUraStage() {
        return uraStage;
    }

    public void setUraStage(String uraStage) {
        this.uraStage = uraStage;
    }

    public String getManager1() {
        return manager1;
    }

    public void setManager1(String manager1) {
        this.manager1 = manager1;
    }

    public String getManager2() {
        return manager2;
    }

    public void setManager2(String manager2) {
        this.manager2 = manager2;
    }
}
