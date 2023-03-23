package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211012 add
 * 後台-問卷結果查詢:問卷結果(配分)/後台-檢視填寫:取得答案清單 rq
 */
@ApiModel(description = "後台-問卷結果查詢:問卷結果(配分)/後台-檢視填寫:取得答案清單 rq")
public class GeoQuestionnaireResultAnswerQueryRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "服務ID", required = true)
    private String caseSetId;

    @ApiModelProperty(notes = "問卷版本", required = true)
    private Integer questionVersion;

    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

    public Integer getQuestionVersion() {
        return questionVersion;
    }

    public void setQuestionVersion(Integer questionVersion) {
        this.questionVersion = questionVersion;
    }
}
