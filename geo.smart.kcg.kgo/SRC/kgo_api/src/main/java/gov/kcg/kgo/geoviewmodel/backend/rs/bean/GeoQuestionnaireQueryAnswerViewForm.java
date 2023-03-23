package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.geomodel.GeoKgoQuestionnaireAnswerListModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20211012 add
 * 後台-檢視填寫:取得答案清單 ViewForm
 */
@ApiModel(description = "後台-檢視填寫:取得答案清單 ViewForm")
public class GeoQuestionnaireQueryAnswerViewForm extends BaseViewForm {

    @ApiModelProperty(value = "服務ID")
    private String caseSetId;

    @ApiModelProperty(value = "服務名稱")
    private String caseSetName;

    @ApiModelProperty(value = "問卷主題")
    private String questionName;

    @ApiModelProperty(notes = "問卷版本")
    private Integer questionVersion;

    @ApiModelProperty(value = "起始日 yyyy-MM-dd")
    private String dateStart;

    @ApiModelProperty(value = "結束日 yyyy-MM-dd")
    private String dateEnd;

    @ApiModelProperty(value = "機關分類代碼")
    private String organSeq;

    @ApiModelProperty(notes = "機關分類名稱")
    private String organName;

    @ApiModelProperty(notes = "機關代碼")
    private String organId;

    @ApiModelProperty(notes = "答案卷清單", required = true)
    private List<GeoKgoQuestionnaireAnswerListModel> answerList;

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

    public String getOrganSeq() {
        return organSeq;
    }

    public void setOrganSeq(String organSeq) {
        this.organSeq = organSeq;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public List<GeoKgoQuestionnaireAnswerListModel> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<GeoKgoQuestionnaireAnswerListModel> answerList) {
        this.answerList = answerList;
    }

    public String getCaseSetName() {
        return caseSetName;
    }

    public void setCaseSetName(String caseSetName) {
        this.caseSetName = caseSetName;
    }
}

