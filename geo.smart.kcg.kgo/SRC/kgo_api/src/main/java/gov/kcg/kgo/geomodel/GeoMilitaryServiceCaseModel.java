package gov.kcg.kgo.geomodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * GEO 20211115 add for 民政局五種服務轉成B流程
 * 兵役類案件 model
 */
@ApiModel(description = "兵役類案件 model")
public class GeoMilitaryServiceCaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "申辦項目主鍵值")
    private String CategoryID;

    @ApiModelProperty(notes = "便民一路通案件編號")
    private String KcgCaseNo;

    @ApiModelProperty(notes = "是否為委託人取件：False:本人、True:委託他人")
    private Boolean IsAgent;

    @ApiModelProperty(notes = "姓名")
    private String Name;

    @ApiModelProperty(notes = "身分類別：0:身分證、1:居留證")
    private int IdentityType = 0;

    @ApiModelProperty(notes = "IdentityType: 0-身分證字號，1-居留證字號")
    private String IdentityID;

    @ApiModelProperty(notes = "聯絡電話")
    private String Tel;

    @ApiModelProperty(notes = "Email")
    private String Email;

    @ApiModelProperty(notes = "出生日期，民國年YYY-MM-DD。")
    private String Birthday;

    @ApiModelProperty(notes = "戶籍地址，縣市+地區。Ex：高雄市左營區")
    private String ResidentArea;

    @ApiModelProperty(notes = "申請原因")
    private String ApplyReason;

    @ApiModelProperty(notes = "受委託人姓名。若為委託人取件，則此欄位必填。")
    private String AgentName = "";

    @ApiModelProperty(notes = "受委託人身份證，需符合身分證格式檢查。若為委託人取件，則此欄位必填。")
    private String AgentIdentityID = "";

    @ApiModelProperty(notes = "取件區公所代碼")
    private String DistrictLocaleNum;

    @ApiModelProperty(notes = "建立日期，需符合日期格式。 西元年YYYY-MM-DD HH:MM:SS。")
    private String CreateDate;

    @ApiModelProperty(notes = "身分證正面檔案 base64 encode")
    private String ObverseIDCard = "";

    @ApiModelProperty(notes = "身分證反面檔案 base64 encode")
    private String ReverseIDCard = "";

    @ApiModelProperty(notes = "委託書 base64 encode")
    private String POA = "";

    @ApiModelProperty(value = "iv")
    private String ivStr;

    /** GEO 20211230 add 增加檔案格式 */
    @ApiModelProperty(notes = "身分證正面檔案 格式")
    private String obverseIDCardFormat;

    @ApiModelProperty(notes = "身分證反面檔案 格式")
    private String reverseIDCardFormat;

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public String getKcgCaseNo() {
        return KcgCaseNo;
    }

    public void setKcgCaseNo(String kcgCaseNo) {
        KcgCaseNo = kcgCaseNo;
    }

    public Boolean getAgent() {
        return IsAgent;
    }

    public void setAgent(Boolean agent) {
        IsAgent = agent;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getIdentityType() {
        return IdentityType;
    }

    public void setIdentityType(int identityType) {
        IdentityType = identityType;
    }

    public String getIdentityID() {
        return IdentityID;
    }

    public void setIdentityID(String identityID) {
        IdentityID = identityID;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getResidentArea() {
        return ResidentArea;
    }

    public void setResidentArea(String residentArea) {
        ResidentArea = residentArea;
    }

    public String getApplyReason() {
        return ApplyReason;
    }

    public void setApplyReason(String applyReason) {
        ApplyReason = applyReason;
    }

    public String getAgentName() {
        return AgentName;
    }

    public void setAgentName(String agentName) {
        AgentName = agentName;
    }

    public String getAgentIdentityID() {
        return AgentIdentityID;
    }

    public void setAgentIdentityID(String agentIdentityID) {
        AgentIdentityID = agentIdentityID;
    }

    public String getDistrictLocaleNum() {
        return DistrictLocaleNum;
    }

    public void setDistrictLocaleNum(String districtLocaleNum) {
        DistrictLocaleNum = districtLocaleNum;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getObverseIDCard() {
        return ObverseIDCard;
    }

    public void setObverseIDCard(String obverseIDCard) {
        ObverseIDCard = obverseIDCard;
    }

    public String getReverseIDCard() {
        return ReverseIDCard;
    }

    public void setReverseIDCard(String reverseIDCard) {
        ReverseIDCard = reverseIDCard;
    }

    public String getPOA() {
        return POA;
    }

    public void setPOA(String POA) {
        this.POA = POA;
    }

    public String getIvStr() {
        return ivStr;
    }

    public void setIvStr(String ivStr) {
        this.ivStr = ivStr;
    }

    public String getObverseIDCardFormat() {
        return obverseIDCardFormat;
    }

    public void setObverseIDCardFormat(String obverseIDCardFormat) {
        this.obverseIDCardFormat = obverseIDCardFormat;
    }

    public String getReverseIDCardFormat() {
        return reverseIDCardFormat;
    }

    public void setReverseIDCardFormat(String reverseIDCardFormat) {
        this.reverseIDCardFormat = reverseIDCardFormat;
    }
}
