package gov.kcg.kgo.geomodel;


import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoAppointmentColumnViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 後台-線上預約臨櫃-預約登錄管理:新增/編輯取得預約對應表單-群組所有欄位設定資料集合
 */
@ApiModel(description = "後台-線上預約臨櫃-預約登錄管理:新增/編輯取得預約對應表單-群組所有欄位設定資料集合")
public class GeoAppointmentOrderFormQueryDataModel {

    @ApiModelProperty(notes = "群組序號")
    private Integer groupSeq;

    @ApiModelProperty(notes = "顯示順序")
    private Integer orderNum;

    @ApiModelProperty(notes = "重複檢核時間")
    private String CheckFrequencyPeriod;

    @ApiModelProperty(notes = "表單群組欄位資料")
    private List<GeoAppointmentColumnViewForm> columnDataList;

    public Integer getGroupSeq() {
        return groupSeq;
    }

    public void setGroupSeq(Integer groupSeq) {
        this.groupSeq = groupSeq;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getCheckFrequencyPeriod() {
        return CheckFrequencyPeriod;
    }

    public void setCheckFrequencyPeriod(String checkFrequencyPeriod) {
        CheckFrequencyPeriod = checkFrequencyPeriod;
    }

    public List<GeoAppointmentColumnViewForm> getColumnDataList() {
        return columnDataList;
    }

    public void setColumnDataList(List<GeoAppointmentColumnViewForm> columnDataList) {
        this.columnDataList = columnDataList;
    }
}
