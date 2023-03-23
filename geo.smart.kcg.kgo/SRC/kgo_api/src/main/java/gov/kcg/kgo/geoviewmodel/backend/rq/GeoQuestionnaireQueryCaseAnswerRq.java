package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211012 add
 * 後台-檢視填寫:檢視該問卷題目答案 rq
 */
@ApiModel(description = "後台-檢視填寫:檢視該問卷題目答案 rq")
public class GeoQuestionnaireQueryCaseAnswerRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "服務ID", required = true)
    private String caseSetId;

    @ApiModelProperty(notes = "問卷版本", required = true)
    private Integer questionVersion;

    @ApiModelProperty(notes = "問卷Id", required = true)
    private String answerId;

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

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }
}
