package gov.kcg.kgo.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the KGO_USER database table.
 * 
 */
@Entity
@Table(name="KGO_USER")
@NamedQuery(name="KgoUser.findAll", query="SELECT k FROM KgoUser k")
public class KgoUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="UserId", unique=true, nullable=false, length=50)
	private String userId;

	@Column(name="CreateTime")
	private Timestamp createTime;

	@Column(name="CreateUser", length=50)
	private String createUser;

	@Column(name="Email", nullable=false, length=80)
	private String email;

	@Column(name="Name")
	private String name;

	@Column(name="Organ", nullable=false, length=50)
	private String organ;

	@Column(name="PublicEmail", nullable=false, length=80)
	private String publicEmail;

	@Column(name="Tel", length=30)
	private String tel;

	@Column(name="Unit", nullable=false, length=50)
	private String unit;

	@Column(name="UpdateTime")
	private Timestamp updateTime;

	@Column(name="UpdateUser", length=50)
	private String updateUser;

	/** GEO 20211115 add for 跨機關檢視 */
	@Column(name="IsAvailableCrossReview")
	private Boolean isAvailableCrossReview;

	/** GEO 20211115 add for 非市府員工登入後台 */
	@Column(name="FromGov")
	private Boolean fromGov;

	/** GEO 20211115 add for 非市府員工登入後台 */
	@Column(name="UserValidate")
	private String userValidate;

	/** GEO 20211115 add for 非市府員工登入後台 */
	@Column(name="UserLoginType")
	private String userLoginType;

	public KgoUser() {
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrgan() {
		return this.organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}

	public String getPublicEmail() {
		return this.publicEmail;
	}

	public void setPublicEmail(String publicEmail) {
		this.publicEmail = publicEmail;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Boolean getAvailableCrossReview() {
		return isAvailableCrossReview;
	}

	public void setAvailableCrossReview(Boolean availableCrossReview) {
		isAvailableCrossReview = availableCrossReview;
	}

	public Boolean getFromGov() {
		return fromGov;
	}

	public void setFromGov(Boolean fromGov) {
		this.fromGov = fromGov;
	}

	public String getUserValidate() {
		return userValidate;
	}

	public void setUserValidate(String userValidate) {
		this.userValidate = userValidate;
	}

	public String getUserLoginType() {
		return userLoginType;
	}

	public void setUserLoginType(String userLoginType) {
		this.userLoginType = userLoginType;
	}
}

