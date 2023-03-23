package gov.kcg.kgo.service.bean.myaccount.viewModel.KcgDeptList.rs;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KcgDeptInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("DeptUUID")
	private String DeptUUID;

	@JsonProperty("LevelNO")
	private String LevelNO;

	@JsonProperty("DeptName")
	private String DeptName;

	@JsonProperty("DeptCode")
	private String DeptCode;

	@JsonProperty("ParentDeptUUID")
	private String ParentDeptUUID;

	@JsonProperty("ParentDeptCode")
	private String ParentDeptCode;

	@JsonProperty("Status")
	private Integer Status;

	@JsonProperty("LastUpdateTime")
	private String LastUpdateTime;

	public String getDeptUUID() {
		return DeptUUID;
	}

	public void setDeptUUID(String deptUUID) {
		DeptUUID = deptUUID;
	}

	public String getLevelNO() {
		return LevelNO;
	}

	public void setLevelNO(String levelNO) {
		LevelNO = levelNO;
	}

	public String getDeptName() {
		return DeptName;
	}

	public void setDeptName(String deptName) {
		DeptName = deptName;
	}

	public String getDeptCode() {
		return DeptCode;
	}

	public void setDeptCode(String deptCode) {
		DeptCode = deptCode;
	}

	public String getParentDeptUUID() {
		return ParentDeptUUID;
	}

	public void setParentDeptUUID(String parentDeptUUID) {
		ParentDeptUUID = parentDeptUUID;
	}

	public String getParentDeptCode() {
		return ParentDeptCode;
	}

	public void setParentDeptCode(String parentDeptCode) {
		ParentDeptCode = parentDeptCode;
	}

	public Integer getStatus() {
		return Status;
	}

	public void setStatus(Integer status) {
		Status = status;
	}

	public String getLastUpdateTime() {
		return LastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		LastUpdateTime = lastUpdateTime;
	}

}
