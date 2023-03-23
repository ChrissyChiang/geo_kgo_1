package gov.kcg.kgo.geomodel;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * GEO 20211005 add
 * 服務申辦統計-申辦服務資料
 */

public class GeoCaseSetApplyCountModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "統計次數")
    private Integer applyCount;

    @ApiModelProperty(value = "機關分類代碼")
    private String organSeq;

    @ApiModelProperty(notes = "機關分類名稱")
    private String organName;

    @ApiModelProperty(notes = "機關代碼")
    private String organId;

    @ApiModelProperty(value = "案件id")
    private String caseSetId;

    @ApiModelProperty(value = "案件名稱")
    private String caseSetName;

    @ApiModelProperty(value = "案件啟用狀態")
    private String caseSetStatus;

    public Integer getApplyCount() {
        return applyCount;
    }

    public void setApplyCount(Integer applyCount) {
        this.applyCount = applyCount;
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

    public String getCaseSetStatus() {
        return caseSetStatus;
    }

    public void setCaseSetStatus(String caseSetStatus) {
        this.caseSetStatus = caseSetStatus;
    }
}
