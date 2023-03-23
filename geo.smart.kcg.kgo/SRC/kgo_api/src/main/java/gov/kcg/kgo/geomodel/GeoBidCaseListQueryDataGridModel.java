package gov.kcg.kgo.geomodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211005 add
 * 案件資料清單
 */
@ApiModel(description = "申辦案件清單-申辦案件資料清單")
public class GeoBidCaseListQueryDataGridModel {

    @ApiModelProperty(notes = "案件種類ID")
    private String caseSetId;

    @ApiModelProperty(notes = "案件名稱")
    private String caseSetName;

    @ApiModelProperty(notes = "整合流程分類")
    private String caseFlowType;

    @ApiModelProperty(notes = "是否啟用[申辦類型-線上(E)]")
    private Boolean isApplyTypeEActive;

    @ApiModelProperty(notes = "是否啟用[申辦類型-臨櫃(C)]")
    private Boolean isApplyTypeCActive;

    @ApiModelProperty(notes = "是否啟用[申辦類型-書表(L)]")
    private Boolean isApplyTypeLActive;

    @ApiModelProperty(value = "統計次數")
    private Integer applyCount;

    @ApiModelProperty(value = "統計名次")
    private Integer applyCountRank;

    @ApiModelProperty(value = "案件啟用狀態")
    private String caseSetStatus;

    @ApiModelProperty(value = "機關分類代碼")
    private String organSeq;

    @ApiModelProperty(notes = "機關分類名稱")
    private String organName;

    @ApiModelProperty(notes = "機關代碼")
    private String organId;

    @ApiModelProperty(notes = "E-線上,C-臨櫃,L-書表")
    private String applyType;

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

    public String getCaseFlowType() {
        return caseFlowType;
    }

    public void setCaseFlowType(String caseFlowType) {
        this.caseFlowType = caseFlowType;
    }

    public Boolean getApplyTypeEActive() {
        return isApplyTypeEActive;
    }

    public void setApplyTypeEActive(Boolean applyTypeEActive) {
        isApplyTypeEActive = applyTypeEActive;
    }

    public Boolean getApplyTypeCActive() {
        return isApplyTypeCActive;
    }

    public void setApplyTypeCActive(Boolean applyTypeCActive) {
        isApplyTypeCActive = applyTypeCActive;
    }

    public Boolean getApplyTypeLActive() {
        return isApplyTypeLActive;
    }

    public void setApplyTypeLActive(Boolean applyTypeLActive) {
        isApplyTypeLActive = applyTypeLActive;
    }

    public Integer getApplyCount() {
        return applyCount;
    }

    public void setApplyCount(Integer applyCount) {
        this.applyCount = applyCount;
    }

    public String getCaseSetStatus() {
        return caseSetStatus;
    }

    public void setCaseSetStatus(String caseSetStatus) {
        this.caseSetStatus = caseSetStatus;
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

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public Integer getApplyCountRank() {
        return applyCountRank;
    }

    public void setApplyCountRank(Integer applyCountRank) {
        this.applyCountRank = applyCountRank;
    }
}
