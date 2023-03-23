package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211015 add
 * 後台-線上預約臨櫃-預約登錄管理:新增/編輯預約登錄檢核 ViewForm
 */

@ApiModel(description = "後台-線上預約臨櫃-預約登錄管理:新增/編輯預約登錄檢核  ViewForm")
public class GeoAppointmentOrderEditValidationViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "欄位ID")
    private String columnId;

    @ApiModelProperty(notes = "欄位名稱")
    private String columnName;

    @ApiModelProperty(notes = "驗證訊息")
    private String validationMsg;

    public GeoAppointmentOrderEditValidationViewForm(String columnId, String columnName, String validationMsg) {
        this.columnId = columnId;
        this.columnName = columnName;
        this.validationMsg = validationMsg;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getValidationMsg() {
        return validationMsg;
    }

    public void setValidationMsg(String validationMsg) {
        this.validationMsg = validationMsg;
    }
}
