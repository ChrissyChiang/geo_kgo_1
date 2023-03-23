package gov.kcg.kgo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
/**
 * GEO 20221015 申辦案件資料查詢物件
 * 新增外部連結及服務類別
 * */
@ApiModel(description = "申辦案件清單-申辦案件資料清單擴充欄位")
@Entity
public class BidCaseListLinkQueryDto {

    @Id
    @ApiModelProperty(notes = "案件種類ID")
    @Column(name = "CASESET_ID")
    private String caseSetId;

    @ApiModelProperty(notes = "申辦類型")
    @Column(name = "APPLY_TYPE")
    private String applyType;

    @ApiModelProperty(notes = "案件名稱")
    @Column(name = "CASESET_NAME")
    private String caseSetName;

    @ApiModelProperty(notes = "CASEFLOW_TYPE")
    @Column(name = "CASEFLOW_TYPE")
    private String caseFlowType;


    @ApiModelProperty(notes = "查詢服務外部連結")
    @Column(name = "LINK_URL")
    private String linkUrl;

    @ApiModelProperty(notes = "服務類型")
    @Column(name = "CASE_CATEGORY")
    private String caseCategory;

    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
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

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getCaseCategory() {
        return caseCategory;
    }

    public void setCaseCategory(String caseCategory) {
        this.caseCategory = caseCategory;
    }
}
