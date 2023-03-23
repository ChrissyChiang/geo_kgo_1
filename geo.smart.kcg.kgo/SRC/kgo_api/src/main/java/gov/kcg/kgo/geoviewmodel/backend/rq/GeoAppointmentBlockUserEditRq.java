package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211015 add
 * 後台-線上預約臨櫃:新增黑名單 rq
 */

@ApiModel(description = "後台-線上預約臨櫃:新增黑名單 rq")
public class GeoAppointmentBlockUserEditRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "線上預約臨櫃主檔id", required = true)
    private String appointmentMainId;

    @ApiModelProperty(value = "黑名單使用者姓名", required = true)
    private String blockUserName;

    @ApiModelProperty(value = "黑名單使用者身分證字號", required = true)
    private String blockUserIdentity;

    @ApiModelProperty(value = "黑名單鎖定起始時間 yyyy-MM-dd", required = true)
    private String blockStartTime;

    @ApiModelProperty(value = "黑名單鎖定結束時間 yyyy-MM-dd", required = true)
    private String blockEndTime;

    @ApiModelProperty(notes = "是否啟用封鎖(GeoBooleanType) 1-設定黑名單, 0-刪除", required = true)
    private Integer isTriggerBlock;

    public String getAppointmentMainId() {
        return appointmentMainId;
    }

    public void setAppointmentMainId(String appointmentMainId) {
        this.appointmentMainId = appointmentMainId;
    }

    public String getBlockUserName() {
        return blockUserName;
    }

    public void setBlockUserName(String blockUserName) {
        this.blockUserName = blockUserName;
    }

    public String getBlockUserIdentity() {
        return blockUserIdentity;
    }

    public void setBlockUserIdentity(String blockUserIdentity) {
        this.blockUserIdentity = blockUserIdentity;
    }

    public String getBlockStartTime() {
        return blockStartTime;
    }

    public void setBlockStartTime(String blockStartTime) {
        this.blockStartTime = blockStartTime;
    }

    public String getBlockEndTime() {
        return blockEndTime;
    }

    public void setBlockEndTime(String blockEndTime) {
        this.blockEndTime = blockEndTime;
    }

    public Integer getIsTriggerBlock() {
        return isTriggerBlock;
    }

    public void setIsTriggerBlock(Integer isTriggerBlock) {
        this.isTriggerBlock = isTriggerBlock;
    }
}
