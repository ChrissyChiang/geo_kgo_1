package gov.kcg.kgo.geomodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 20220725 add GEO依照登入方式顯示相對應案件
 */
@ApiModel(description = "前台-依照登入方式顯示不同案件")
public class GeoCaseSetGroupAndCheckTypeModel {

    @ApiModelProperty(notes = "seq")
    private Integer seq;

    @ApiModelProperty(notes = "案件id")
    private String caseSetId;

    @ApiModelProperty(notes = "角色:Role、Service")
    private String mainType;

    @ApiModelProperty(notes = "groupLevelSeq")
    private String groupLevelSeq;

    @ApiModelProperty(notes = "serviceTo")
    private String serviceTo;

    @ApiModelProperty(notes = "案件名稱")
    private String caseSetName;

    @ApiModelProperty(notes = "依身份別")
    private Integer role;

    @ApiModelProperty(notes = "依服務別")
    private Integer service;

    @ApiModelProperty(notes = "依機關別")
    private Integer organ;

    @ApiModelProperty(notes = "登入方式驗證")
    private String checkAType;

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

    public String getMainType() {
        return mainType;
    }

    public void setMainType(String mainType) {
        this.mainType = mainType;
    }

    public String getGroupLevelSeq() {
        return groupLevelSeq;
    }

    public void setGroupLevelSeq(String groupLevelSeq) {
        this.groupLevelSeq = groupLevelSeq;
    }

    public String getServiceTo() {
        return serviceTo;
    }

    public void setServiceTo(String serviceTo) {
        this.serviceTo = serviceTo;
    }

    public String getCaseSetName() {
        return caseSetName;
    }

    public void setCaseSetName(String caseSetName) {
        this.caseSetName = caseSetName;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getService() {
        return service;
    }

    public void setService(Integer service) {
        this.service = service;
    }

    public Integer getOrgan() {
        return organ;
    }

    public void setOrgan(Integer organ) {
        this.organ = organ;
    }

    public String getCheckAType() {
        return checkAType;
    }

    public void setCheckAType(String checkAType) {
        this.checkAType = checkAType;
    }

    @Override
    public String toString() {
        return "GeoCaseSetGroupAndCheckTypeＭodel{" +
                "seq=" + seq +
                ", caseSetId='" + caseSetId + '\'' +
                ", mainType='" + mainType + '\'' +
                ", groupLevelSeq='" + groupLevelSeq + '\'' +
                ", serviceTo='" + serviceTo + '\'' +
                ", caseSetName='" + caseSetName + '\'' +
                ", role=" + role +
                ", service=" + service +
                ", organ=" + organ +
                ", checkAType='" + checkAType + '\'' +
                '}';
    }
}
