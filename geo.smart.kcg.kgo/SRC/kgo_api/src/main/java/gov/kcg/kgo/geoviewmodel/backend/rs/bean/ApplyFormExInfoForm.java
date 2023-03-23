package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.frontend.caseform.rs.bean.ColumnViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "step3. 要求額外資訊欄位 View Form")
public class ApplyFormExInfoForm extends BaseViewForm {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "動態欄位方式提供標題及內容")
    private List<ColumnViewForm> columnData;
    @ApiModelProperty(value="訊息")
    private  String msg;

    public List<ColumnViewForm> getColumnData() {
        return columnData;
    }

    public void setColumnData(List<ColumnViewForm> columnData) {
        this.columnData = columnData;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
