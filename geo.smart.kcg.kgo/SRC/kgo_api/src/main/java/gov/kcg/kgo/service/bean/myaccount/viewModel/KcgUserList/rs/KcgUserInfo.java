package gov.kcg.kgo.service.bean.myaccount.viewModel.KcgUserList.rs;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KcgUserInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("UserUUID")
	private String userUUID;

	@JsonProperty("UserLoginID")
	private String userLoginID;

	@JsonProperty("UserName")
	private String userName;

	@JsonProperty("DeptCode")
	private String deptCode;

	@JsonProperty("DeptUUID")
	private String deptUUID;

	@JsonProperty("UserTwSsnSHA256")
	private String userTwSsnSHA256;

	@JsonProperty("Status")
	private String status;

	@JsonProperty("LastUpdateTime")
	private String lastUpdateTime;

    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    public String getUserLoginID() {
        return userLoginID;
    }

    public void setUserLoginID(String userLoginID) {
        this.userLoginID = userLoginID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptUUID() {
        return deptUUID;
    }

    public void setDeptUUID(String deptUUID) {
        this.deptUUID = deptUUID;
    }

    public String getUserTwSsnSHA256() {
        return userTwSsnSHA256;
    }

    public void setUserTwSsnSHA256(String userTwSsnSHA256) {
        this.userTwSsnSHA256 = userTwSsnSHA256;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public String toString() {
        return "KcgUserInfo [userLoginID=" + userLoginID + ", userName=" + userName + ", deptCode="
                + deptCode + ", status=" + status + ", lastUpdateTime=" + lastUpdateTime + "]";
    }
    
}
