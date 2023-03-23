package gov.kcg.kgo.geomodel.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@ApiModel(description = "後臺查詢服務管理-案件搜尋")
@Entity
public class GeoExCaseQueryDto {

    /**
     * 案件編號
     */
    @Id
    @ApiModelProperty(notes = "案件編號")
    @Column(name = "CASESET_ID")
    private String caseSetId;

    /**
     * 案件名稱
     */
    @ApiModelProperty(notes = "案件名稱")
    @Column(name = "CASESET_NAME")
    private String caseSetName;
    /**
     * 機關代碼
     */
    @ApiModelProperty(notes = "機關代碼")
    @Column(name = "ORGAN_ID")
    private String organId;
    /**
     * 機關名稱
     */
    @ApiModelProperty(notes = "機關名稱")
    @Column(name = "ORGAN_NAME")
    private String organName;
    /**
     * 權責機關代碼
     */
    @ApiModelProperty(notes = "權責機關代碼")
    @Column(name = "OWNER_ORGAN_ID")
    private String ownerOrganId;
    /**
     * 權責機關名稱
     */
    @ApiModelProperty(notes = "權責機關名稱")
    @Column(name = "OWNER_ORGAN_NAME")
    private String ownerOrganName;

    /**
     * 案件狀態 (Accept/On/Off)
     */
    @ApiModelProperty(notes = "案件狀態")
    @Column(name = "STATUS")
    private String status;
    /**
     * 作業流程 (SA-服務申辦流程 / URA-系統權限申請流 / SCA-服務案件新增流程/ D 動態流程)
     */
    @ApiModelProperty(notes = "作業流程")
    @Column(name = "CASE_TYPE")
    private String caseType;
    /**
     * 案件順序
     */
    @ApiModelProperty(notes = "案件順序")
    @Column(name = "SORT")
    private Integer sort;

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

    public String getOwnerOrganId() {
        return ownerOrganId;
    }

    public void setOwnerOrganId(String ownerOrganId) {
        this.ownerOrganId = ownerOrganId;
    }

    public String getOwnerOrganName() {
        return ownerOrganName;
    }

    public void setOwnerOrganName(String ownerOrganName) {
        this.ownerOrganName = ownerOrganName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "GeoExCaseQueryDto{" +
                "caseSetId='" + caseSetId + '\'' +
                ", caseSetName='" + caseSetName + '\'' +
                ", organId='" + organId + '\'' +
                ", organName='" + organName + '\'' +
                ", ownerOrganId='" + ownerOrganId + '\'' +
                ", ownerOrganName='" + ownerOrganName + '\'' +
                ", status='" + status + '\'' +
                ", caseType='" + caseType + '\'' +
                ", sort=" + sort +
                '}';
    }
}
