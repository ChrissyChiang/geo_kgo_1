package gov.kcg.kgo.viewModel.backend.serviceApply.permissionActive.save.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "服務申請-權限開通申請-儲存 rq")
public class ServiceApplyPermissionActiveSaveRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "機關", required = true)
    private String organ;

    @ApiModelProperty(value = "科室", required = true)
    private String unit;

    @ApiModelProperty(value = "申請人", required = true)
    private String applyUser;

    @ApiModelProperty(value = "聯絡電話", required = true)
    private String phone;

    @ApiModelProperty(value = "電子郵件", required = true)
    private String email;

    @ApiModelProperty(value = "申請角色", required = true)
    private String applyRole;

    @ApiModelProperty(value = "審核主管", required = true)
    private String reviewer;

    /**
     * GEO 20211115 add 非市府員工登入後台
     */
    @ApiModelProperty(value = "申請人姓名", required = true)
    private String applyUserName;

    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApplyRole() {
        return applyRole;
    }

    public void setApplyRole(String applyRole) {
        this.applyRole = applyRole;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }
}
