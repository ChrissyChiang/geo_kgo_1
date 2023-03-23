package gov.kcg.kgo.geoutil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


/**
 * GEO 20211115 add for 民政局五種服務轉成B流程
 *
 * 用component注入方式從geoapi1999.properties取得API相關設定
 */
@Component
@PropertySource("classpath:geoapi1999.properties")
public class GeoApiCivilAffairsProperties {

	@Value("${api.kgo.civil.affairs..aes_key}")
	private String kgoCivilAffairsAesKey;

	/** 替代備役退役證明書補（換）發 **/
	@Value("${apply.project.name.d6a6fc78-ed57-4c60-aeb0-b2e7928a7a0e}")
	private String militaryServiceName01;

	/** 義務役服兵役證明書(役男在營證明書) **/
	@Value("${apply.project.name.f2ce0fab-91e4-4af4-bd23-6105613c9571}")
	private String militaryServiceName02;

	/** 免役證明書遺失補發 **/
	@Value("${apply.project.name.cbd83a70-e1d3-4261-86ab-ccbb84141d55}")
	private String militaryServiceNAme03;

	/** 低收入戶生活補助證明書 **/
	@Value("${apply.project.name.328dbabd-1c06-48c9-8cfa-382232536f7f}")
	private String socialAffairsName01;

	/** 中低收入戶生活補助證明書 **/
	@Value("${apply.project.name.b9c84b0a-9f89-4bd5-b093-9d88b0fea5f9}")
	private String socialAffairsNAme02;

	/** 替代備役退役證明書補（換）發 **/
	@Value("${apply.project.code.d6a6fc78-ed57-4c60-aeb0-b2e7928a7a0e}")
	private String militaryService01;

	/** 義務役服兵役證明書(役男在營證明書) **/
	@Value("${apply.project.code.f2ce0fab-91e4-4af4-bd23-6105613c9571}")
	private String militaryService02;

	/** 免役證明書遺失補發 **/
	@Value("${apply.project.code.cbd83a70-e1d3-4261-86ab-ccbb84141d55}")
	private String militaryService03;

	/** 低收入戶生活補助證明書 **/
	@Value("${apply.project.code.328dbabd-1c06-48c9-8cfa-382232536f7f}")
	private String socialAffairs01;

	/** 中低收入戶生活補助證明書 **/
	@Value("${apply.project.code.b9c84b0a-9f89-4bd5-b093-9d88b0fea5f9}")
	private String socialAffairs02;

	/** 欄位id 申辦項目主鍵值 **/
	@Value("${response.key.CategoryID}")
	private String categoryID;

	/** 欄位id 便民一路通案件編號 **/
	@Value("${response.key.KcgCaseNo}")
	private String KcgCaseNo;

	/** 欄位id 是否為委託人取件 **/
	@Value("${response.key.IsAgent}")
	private String IsAgent;

	/** 欄位id 姓名 **/
	@Value("${response.key.Name}")
	private String Name;

	/** 欄位id 身分類別 **/
	@Value("${response.key.IdentityType}")
	private String IdentityType;

	/** 欄位id 身分證字號、居留證字號 **/
	@Value("${response.key.IdentityID}")
	private String IdentityID;

	/** 欄位id 聯絡電話 **/
	@Value("${response.key.Tel}")
	private String Tel;

	/** 欄位id Email **/
	@Value("${response.key.Email}")
	private String Email;

	/** 欄位id 出生日期 **/
	@Value("${response.key.Birthday}")
	private String Birthday;

	/** 欄位id 戶籍地址，縣市+地區。Ex：高雄市左營區 **/
	@Value("${response.key.ResidentArea}")
	private String ResidentArea;

	/** 欄位id 申請原因 **/
	@Value("${response.key.ApplyReason}")
	private String ApplyReason;

	/** 欄位id 受委託人姓名 **/
	@Value("${response.key.AgentName}")
	private String AgentName;

	/** 欄位id 受委託人身份證 **/
	@Value("${response.key.AgentIdentityID}")
	private String AgentIdentityID;

	/** 欄位id 取件區公所代碼 **/
	@Value("${response.key.DistrictLocaleNum}")
	private String DistrictLocaleNum;

	/** 欄位id 建立日期 **/
	@Value("${response.key.CreateDate}")
	private String CreateDate;

	/** 欄位id 身分證正面檔案 **/
	@Value("${response.key.ObverseIDCard}")
	private String ObverseIDCard;

	/** 欄位id 身分證反面檔案 **/
	@Value("${response.key.ReverseIDCard}")
	private String ReverseIDCard;

	/** 欄位id 委託書 **/
	@Value("${response.key.POA}")
	private String POA = "";

	/** 欄位id 戶籍地 **/
	@Value("${response.key.Resident}")
	private String Resident;

	/** 欄位id 目前是否領取補助 **/
	@Value("${response.key.IsReceivedSubsidy}")
	private String IsReceivedSubsidy;

	/** 欄位id 委託書 **/
	@Value("${response.key.IsLowIncome}")
	private String IsLowIncome;

	public String getMilitaryServiceName01() {
		return militaryServiceName01;
	}

	public void setMilitaryServiceName01(String militaryServiceName01) {
		this.militaryServiceName01 = militaryServiceName01;
	}

	public String getMilitaryServiceName02() {
		return militaryServiceName02;
	}

	public void setMilitaryServiceName02(String militaryServiceName02) {
		this.militaryServiceName02 = militaryServiceName02;
	}

	public String getMilitaryServiceNAme03() {
		return militaryServiceNAme03;
	}

	public void setMilitaryServiceNAme03(String militaryServiceNAme03) {
		this.militaryServiceNAme03 = militaryServiceNAme03;
	}

	public String getSocialAffairsName01() {
		return socialAffairsName01;
	}

	public void setSocialAffairsName01(String socialAffairsName01) {
		this.socialAffairsName01 = socialAffairsName01;
	}

	public String getSocialAffairsNAme02() {
		return socialAffairsNAme02;
	}

	public void setSocialAffairsNAme02(String socialAffairsNAme02) {
		this.socialAffairsNAme02 = socialAffairsNAme02;
	}

	public String getMilitaryService01() {
		return militaryService01;
	}

	public void setMilitaryService01(String militaryService01) {
		this.militaryService01 = militaryService01;
	}

	public String getMilitaryService02() {
		return militaryService02;
	}

	public void setMilitaryService02(String militaryService02) {
		this.militaryService02 = militaryService02;
	}

	public String getMilitaryService03() {
		return militaryService03;
	}

	public void setMilitaryService03(String militaryService03) {
		this.militaryService03 = militaryService03;
	}

	public String getSocialAffairs01() {
		return socialAffairs01;
	}

	public void setSocialAffairs01(String socialAffairs01) {
		this.socialAffairs01 = socialAffairs01;
	}

	public String getSocialAffairs02() {
		return socialAffairs02;
	}

	public void setSocialAffairs02(String socialAffairs02) {
		this.socialAffairs02 = socialAffairs02;
	}

	public String getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}

	public String getKcgCaseNo() {
		return KcgCaseNo;
	}

	public void setKcgCaseNo(String kcgCaseNo) {
		KcgCaseNo = kcgCaseNo;
	}

	public String getIsAgent() {
		return IsAgent;
	}

	public void setIsAgent(String isAgent) {
		IsAgent = isAgent;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getIdentityType() {
		return IdentityType;
	}

	public void setIdentityType(String identityType) {
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

	public String getResident() {
		return Resident;
	}

	public void setResident(String resident) {
		Resident = resident;
	}

	public String getIsReceivedSubsidy() {
		return IsReceivedSubsidy;
	}

	public void setIsReceivedSubsidy(String isReceivedSubsidy) {
		IsReceivedSubsidy = isReceivedSubsidy;
	}

	public String getIsLowIncome() {
		return IsLowIncome;
	}

	public void setIsLowIncome(String isLowIncome) {
		IsLowIncome = isLowIncome;
	}

	public String getKgoCivilAffairsAesKey() {
		return kgoCivilAffairsAesKey;
	}

	public void setKgoCivilAffairsAesKey(String kgoCivilAffairsAesKey) {
		this.kgoCivilAffairsAesKey = kgoCivilAffairsAesKey;
	}
}
