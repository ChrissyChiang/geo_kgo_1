package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211005 add
 * 服務申辦統計-取得申辦服務名次列表 ViewForm
 */

@ApiModel(description = "服務申辦統計-取得申辦服務名次列表 ViewForm")
public class GeoCaseSetApplyRankViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "執行結果訊息")
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
