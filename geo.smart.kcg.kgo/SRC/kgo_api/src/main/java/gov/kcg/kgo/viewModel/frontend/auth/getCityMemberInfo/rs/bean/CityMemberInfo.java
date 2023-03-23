package gov.kcg.kgo.viewModel.frontend.auth.getCityMemberInfo.rs.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "市民科技會員資訊 ViewForm")
public class CityMemberInfo  {
	
	private String recidentCountyCode;
	private String recidentDistrictCode;
	private String recidentCounty;
	private String recidentDistrict;
	private String hashID;
	private String kCoinBalance;
	private String isRealName;
	private String isDelete;
	private List<Object> identityCode;
	private List<Object> subscribeList;
	private String account;
	private String name;
	private String gender;
	private String currentCountyCode;
	private String currentDistrictCode;
	private String currentCounty;
	private String currentDistrict;
	
	public String getRecidentCountyCode() {
		return recidentCountyCode;
	}
	public void setRecidentCountyCode(String recidentCountyCode) {
		this.recidentCountyCode = recidentCountyCode;
	}
	public String getRecidentDistrictCode() {
		return recidentDistrictCode;
	}
	public void setRecidentDistrictCode(String recidentDistrictCode) {
		this.recidentDistrictCode = recidentDistrictCode;
	}
	public String getRecidentCounty() {
		return recidentCounty;
	}
	public void setRecidentCounty(String recidentCounty) {
		this.recidentCounty = recidentCounty;
	}
	public String getRecidentDistrict() {
		return recidentDistrict;
	}
	public void setRecidentDistrict(String recidentDistrict) {
		this.recidentDistrict = recidentDistrict;
	}
	public String getHashID() {
		return hashID;
	}
	public void setHashID(String hashID) {
		this.hashID = hashID;
	}
	public String getkCoinBalance() {
		return kCoinBalance;
	}
	public void setkCoinBalance(String kCoinBalance) {
		this.kCoinBalance = kCoinBalance;
	}
	public String getIsRealName() {
		return isRealName;
	}
	public void setIsRealName(String isRealName) {
		this.isRealName = isRealName;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public List<Object> getIdentityCode() {
		return identityCode;
	}
	public void setIdentityCode(List<Object> identityCode) {
		this.identityCode = identityCode;
	}
	public List<Object> getSubscribeList() {
		return subscribeList;
	}
	public void setSubscribeList(List<Object> subscribeList) {
		this.subscribeList = subscribeList;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCurrentCountyCode() {
		return currentCountyCode;
	}
	public void setCurrentCountyCode(String currentCountyCode) {
		this.currentCountyCode = currentCountyCode;
	}
	public String getCurrentDistrictCode() {
		return currentDistrictCode;
	}
	public void setCurrentDistrictCode(String currentDistrictCode) {
		this.currentDistrictCode = currentDistrictCode;
	}
	public String getCurrentCounty() {
		return currentCounty;
	}
	public void setCurrentCounty(String currentCounty) {
		this.currentCounty = currentCounty;
	}
	public String getCurrentDistrict() {
		return currentDistrict;
	}
	public void setCurrentDistrict(String currentDistrict) {
		this.currentDistrict = currentDistrict;
	}
}
