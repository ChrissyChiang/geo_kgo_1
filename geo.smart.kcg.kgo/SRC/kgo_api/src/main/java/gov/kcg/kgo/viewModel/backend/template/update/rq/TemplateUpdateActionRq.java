package gov.kcg.kgo.viewModel.backend.template.update.rq;

import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rq.CasesetColumnData;
import gov.kcg.kgo.viewModel.backend.template.update.rq.bean.TemplateUpdateCasesetColumnData;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;


@ApiModel(description = "後台表單維護– 更新 rq")
public class TemplateUpdateActionRq extends ApiRequest {

    @ApiModelProperty(value = "序號")
    private Integer seq;

    @ApiModelProperty(value = "表單名稱")
    private String name;

    /** 欄位設定集合 */
    @ApiModelProperty(notes = "欄位設定集合")
    private List<TemplateUpdateCasesetColumnData> columnData;

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TemplateUpdateCasesetColumnData> getColumnData() {
        return columnData;
    }

    public void setColumnData(List<TemplateUpdateCasesetColumnData> columnData) {
        this.columnData = columnData;
    }
}
