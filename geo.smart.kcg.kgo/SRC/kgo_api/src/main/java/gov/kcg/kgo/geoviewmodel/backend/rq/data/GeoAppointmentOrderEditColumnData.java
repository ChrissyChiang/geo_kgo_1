package gov.kcg.kgo.geoviewmodel.backend.rq.data;

import gov.kcg.kgo.viewModel.frontend.caseform.rq.bean.SaveActionCColumnViewForm;
import gov.kcg.kgo.viewModel.frontend.caseform.rq.bean.SaveFileViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20211015 add
 * 後台-線上預約臨櫃-預約登錄管理:新增/編輯預約登錄欄位 rq
 */

@ApiModel(description = "後台-線上預約臨櫃-預約登錄管理:新增/編輯預約登錄欄位 rq ")
public class GeoAppointmentOrderEditColumnData {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "欄位種類ID")
    private String columnId;

    @ApiModelProperty(notes = "欄位值(附件請用塞檔名，多擋以小寫\",\"分隔)")
    private String value;

    @ApiModelProperty(notes = "欄位型態")
    private String columnType;

    @ApiModelProperty(notes = "附件實體檔案")
    private List<SaveFileViewForm> files;

    @ApiModelProperty(notes = "案件截圖PDF")
    private SaveFileViewForm casePdf;

    @ApiModelProperty(value = "複合欄位資料集合")
    private List<List<SaveActionCColumnViewForm>> complex;

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public List<SaveFileViewForm> getFiles() {
        return files;
    }

    public void setFiles(List<SaveFileViewForm> files) {
        this.files = files;
    }

    public SaveFileViewForm getCasePdf() {
        return casePdf;
    }

    public void setCasePdf(SaveFileViewForm casePdf) {
        this.casePdf = casePdf;
    }

    public List<List<SaveActionCColumnViewForm>> getComplex() {
        return complex;
    }

    public void setComplex(List<List<SaveActionCColumnViewForm>> complex) {
        this.complex = complex;
    }
}
