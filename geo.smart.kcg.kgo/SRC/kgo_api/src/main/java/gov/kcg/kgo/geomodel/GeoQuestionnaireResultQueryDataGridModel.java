package gov.kcg.kgo.geomodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211011 add
 * 後台-問卷結果查詢:服務問卷清單
 */
@ApiModel(description = "後台-問卷結果查詢:服務問卷清單")
public class GeoQuestionnaireResultQueryDataGridModel {

    @ApiModelProperty(notes = "機關代碼")
    private String organId;

    @ApiModelProperty(notes = "機關分類代碼")
    private String seqId;

    @ApiModelProperty(notes = "機關名稱")
    private String organName;

    @ApiModelProperty(notes = "服務ID")
    private String caseSetId;

    @ApiModelProperty(notes = "服務名稱")
    private String caseSetName;

    @ApiModelProperty(value = "問卷主題")
    private String questionName;

    @ApiModelProperty(notes = "問卷版本")
    private Integer questionVersion;

    @ApiModelProperty(value = "起始日 yyyy-MM-dd")
    private String dateStart;

    @ApiModelProperty(value = "結束日 yyyy-MM-dd")
    private String dateEnd;

    @ApiModelProperty(value = "是否有配分")
    private Boolean isHadScore;

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

    public String getCaseSetName() {
        return caseSetName;
    }

    public void setCaseSetName(String caseSetName) {
        this.caseSetName = caseSetName;
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

    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public Boolean getHadScore() {
        return isHadScore;
    }

    public void setHadScore(Boolean hadScore) {
        isHadScore = hadScore;
    }
}

