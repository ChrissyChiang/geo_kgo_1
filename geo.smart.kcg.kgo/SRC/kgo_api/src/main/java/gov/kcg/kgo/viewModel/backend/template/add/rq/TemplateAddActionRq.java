package gov.kcg.kgo.viewModel.backend.template.add.rq;

import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rq.CasesetColumnData;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "後台表單維護– 新增 rq")
public class TemplateAddActionRq extends ApiRequest {

    @ApiModelProperty(value = "表單名稱")
    private String name;

    /** 欄位設定集合 */
    @ApiModelProperty(notes = "欄位設定集合")
    private List<CasesetColumnData> columnData;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CasesetColumnData> getColumnData() {
        return columnData;
    }

    public void setColumnData(List<CasesetColumnData> columnData) {
        this.columnData = columnData;
    }
}
