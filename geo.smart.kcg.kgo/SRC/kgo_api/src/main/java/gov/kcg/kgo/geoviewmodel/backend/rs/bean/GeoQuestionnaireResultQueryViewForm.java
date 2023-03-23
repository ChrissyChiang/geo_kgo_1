package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.geomodel.GeoKgoQuestionnaireResultQueryTopicModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20211012 add
 * 後台-問卷結果查詢:問卷結果(配分) ViewForm
 */
@ApiModel(description = "後台-問卷結果查詢:問卷結果(配分) ViewForm")
public class GeoQuestionnaireResultQueryViewForm extends BaseViewForm {

    @ApiModelProperty(notes = "服務ID")
    private String caseSetId;

    @ApiModelProperty(value = "問卷主題")
    private String questionName;

    @ApiModelProperty(notes = "問卷版本")
    private Integer questionVersion;

    @ApiModelProperty(value = "起始日 yyyy-MM-dd")
    private String dateStart;

    @ApiModelProperty(value = "結束日 yyyy-MM-dd")
    private String dateEnd;

    @ApiModelProperty(notes = "子題目清單", required = true)
    private List<GeoKgoQuestionnaireResultQueryTopicModel> topicList;

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

    public Integer getQuestionVersion() {
        return questionVersion;
    }

    public void setQuestionVersion(Integer questionVersion) {
        this.questionVersion = questionVersion;
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

    public List<GeoKgoQuestionnaireResultQueryTopicModel> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<GeoKgoQuestionnaireResultQueryTopicModel> topicList) {
        this.topicList = topicList;
    }
}

