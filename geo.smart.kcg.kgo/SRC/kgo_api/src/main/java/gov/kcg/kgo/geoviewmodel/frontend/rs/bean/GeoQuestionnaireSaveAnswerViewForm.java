package gov.kcg.kgo.geoviewmodel.frontend.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20210923 add
 *
 * 問卷:儲存服務問卷答案(主檔、內容) ViewForm
 */

@ApiModel(description = "問卷:儲存服務問卷答案(主檔、內容)  ViewForm")
public class GeoQuestionnaireSaveAnswerViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    /** 執行結果訊息 **/
    @ApiModelProperty(value = "執行結果訊息")
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
