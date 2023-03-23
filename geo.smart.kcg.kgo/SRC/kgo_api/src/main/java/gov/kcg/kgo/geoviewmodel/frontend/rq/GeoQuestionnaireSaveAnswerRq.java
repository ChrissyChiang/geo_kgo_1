package gov.kcg.kgo.geoviewmodel.frontend.rq;

import gov.kcg.kgo.geomodel.GeoKgoQuestionnaireAnswerDetailModel;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * GEO 20210923 add
 *
 * 問卷:儲存服務問卷答案(主檔、內容) rq
 */

@ApiModel(description = "問卷:儲存服務問卷答案(主檔、內容) rq")
public class GeoQuestionnaireSaveAnswerRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "案件id", required = true)
    private String caseId;

    @ApiModelProperty(value = "服務id", required = true)
    private String caseSetId;

    @ApiModelProperty(value = "問卷版本", required = true)
    private Integer questionVersion;

    @ApiModelProperty(value = "作答者id")
    private String applyUserId;

    @ApiModelProperty(value = "作答者姓名")
    private String applyName;

    @ApiModelProperty(value = "服務問卷作答內容清單", required = true)
    private List<GeoKgoQuestionnaireAnswerDetailModel> detailList;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

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

    public String getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(String applyUserId) {
        this.applyUserId = applyUserId;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public List<GeoKgoQuestionnaireAnswerDetailModel> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<GeoKgoQuestionnaireAnswerDetailModel> detailList) {
        this.detailList = detailList;
    }
}
