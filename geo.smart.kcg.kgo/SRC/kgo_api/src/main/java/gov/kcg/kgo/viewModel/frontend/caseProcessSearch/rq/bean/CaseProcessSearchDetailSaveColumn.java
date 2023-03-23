package gov.kcg.kgo.viewModel.frontend.caseProcessSearch.rq.bean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "案件進度-明細保存-欄位")
public class CaseProcessSearchDetailSaveColumn {

    @ApiModelProperty(value = "欄位ID")
    private String columnId;

    @ApiModelProperty(value = "欄位值")
    private String columnValue;

    @ApiModelProperty(value = "複合欄位ID")
    private String ccolumnId;

    @ApiModelProperty(value = "附件檔案base64 endcode")
    private String fileBase64Str;

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    public String getCcolumnId() {
        return ccolumnId;
    }

    public void setCcolumnId(String ccolumnId) {
        this.ccolumnId = ccolumnId;
    }

    public String getFileBase64Str() {
        return fileBase64Str;
    }

    public void setFileBase64Str(String fileBase64Str) {
        this.fileBase64Str = fileBase64Str;
    }
}
