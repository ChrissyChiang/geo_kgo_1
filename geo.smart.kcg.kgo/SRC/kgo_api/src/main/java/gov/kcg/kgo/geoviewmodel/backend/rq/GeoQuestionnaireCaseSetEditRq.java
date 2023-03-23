package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.geomodel.GeoKgoQuestionnaireCaseSetTopicModel;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20210922 add
 *
 * 服務管理-問卷設定:編輯該項服務問卷(主內容、題組、子題目) rq
 */
@ApiModel(description = "服務管理-問卷設定:編輯該服務問卷(主內容、題組、子題目) rq")
public class GeoQuestionnaireCaseSetEditRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服務id", required = true)
    private String caseSetId;

    @ApiModelProperty(value = "問卷主題", required = true)
    private String questionName;

    @ApiModelProperty(value = "問卷描述", required = true)
    private String questionDesc;

    @ApiModelProperty(value = "起始日 yyyy-MM-dd，有效必填")
    private String dateStart;

    @ApiModelProperty(value = "結束日 yyyy-MM-dd，有效必填")
    private String dateEnd;

    @ApiModelProperty(value = "是否有效 (1-有效、0-無效)", required = true)
    private Integer isEnable;

    @ApiModelProperty(value = "服務問卷-題組清單", required = true)
    private List<GeoKgoQuestionnaireCaseSetTopicModel> topicList;

    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getQuestionDesc() {
        return questionDesc;
    }

    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public List<GeoKgoQuestionnaireCaseSetTopicModel> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<GeoKgoQuestionnaireCaseSetTopicModel> topicList) {
        this.topicList = topicList;
    }

}
