package gov.kcg.kgo.geomodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * GEO 20211115 add for 民政局五種服務轉成B流程
 * 社政類案件 model
 */
@ApiModel(description = "社政類案件 model")
public class GeoSocialAffairsCaseModel implements Serializable {

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

    @ApiModelProperty(notes = "戶籍地，需為高雄市地區名稱")
    private String Resident;

    @ApiModelProperty(notes = "聯絡電話")
    private String Tel;

    @ApiModelProperty(notes = "Email")
    private String Email;

    @ApiModelProperty(notes = "目前是否領取補助：False:否、True:是 低收入戶生活補助證明書才需出現此欄位")
    private Boolean IsReceivedSubsidy;

    @ApiModelProperty(notes = "目前是否具中低收入戶資格：False:否、True:是 中低收入戶生活補助證明書才需出現此欄位")
    private Boolean IsLowIncome;

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

    public String getResident() {
        return Resident;
    }

    public void setResident(String resident) {
        Resident = resident;
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

    public Boolean getReceivedSubsidy() {
        return IsReceivedSubsidy;
    }

    public void setReceivedSubsidy(Boolean receivedSubsidy) {
        IsReceivedSubsidy = receivedSubsidy;
    }

    public Boolean getLowIncome() {
        return IsLowIncome;
    }

    public void setLowIncome(Boolean lowIncome) {
        IsLowIncome = lowIncome;
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
