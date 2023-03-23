package gov.kcg.kgo.viewModel.common.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "前端 - 登入使用者資訊")
public class ApiLoginUserInfo  {
	/** 使用者ID */
	@ApiModelProperty(notes = "使用者ID")
	private String userId;

	/** 使用者姓名 */
	@ApiModelProperty(notes = "使用者姓名")
	private String name;
	
	/** 登入方式類別 */
	@ApiModelProperty(notes = "登入方式類別")
	private String loginAuthTokenType;

	@ApiModelProperty(value = "前臺登入 - 與其他系統介接交換資訊 (AES加密)")
	private String exchange;

	/** 機關代碼 */
	@ApiModelProperty(notes = "機關代碼")
	private String organId;
	
	/** 市民科技會員 城市幣(呼叫API 取得) */
	@ApiModelProperty(notes = "市民城市幣")
	private String kCoinBalance;

	/** 角色權限清單 */
	@ApiModelProperty(notes = "角色權限清單")
	private List<String> roles;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginAuthTokenType() {
		return loginAuthTokenType;
	}

	public void setLoginAuthTokenType(String loginAuthTokenType) {
		this.loginAuthTokenType = loginAuthTokenType;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getkCoinBalance() {
		return kCoinBalance;
	}

	public void setkCoinBalance(String kCoinBalance) {
		this.kCoinBalance = kCoinBalance;
	}
}
