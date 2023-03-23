package gov.kcg.kgo.geoentity; 

import javax.persistence.*; 
import org.hibernate.annotations.DynamicInsert; 
import java.io.Serializable; 
import java.sql.Timestamp; 

/** 
 * GEO 20211113 add 前台使用者註冊
 * 使用者帳號
 */
@Entity
@DynamicInsert
@Table(name = "GEO_KGO_FRONTEND_USER", schema = "dbo")
public class GeoKgoFrontendUser implements Serializable {

    private static final long serialVersionUID = 1L; 

    private String userId; //使用者id
    private String email; //信箱
    private String name; //姓名
    private String userIdentity; //身分證號
    private String phone; //手機
    private String userLoginType; //登入方式
    private String userValidate; //驗證資訊
    private String editUser; //編輯人員
    private Timestamp editTime; //編輯時間

    @Id 
    @Column(name = "UserId", columnDefinition = "varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS  NOT NULL") 
    public String getUserId() { 
        return userId;
    } 

    public void setUserId(String UserId) { 
        this.userId = UserId;
    } 

    @Basic 
    @Column(name = "Email", columnDefinition = "varchar(80) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL") 
    public String getEmail() { 
        return email;
    } 

    public void setEmail(String Email) { 
        this.email = Email;
    } 

    @Basic 
    @Column(name = "Name", columnDefinition = "nvarchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL") 
    public String getName() { 
        return name;
    } 

    public void setName(String Name) { 
        this.name = Name;
    } 

    @Basic 
    @Column(name = "UserIdentity", columnDefinition = "varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL")
    public String getUserIdentity() {
        return userIdentity;
    } 

    public void setUserIdentity(String Identity) {
        this.userIdentity = Identity;
    } 

    @Basic 
    @Column(name = "Phone", columnDefinition = "varchar(30) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL") 
    public String getPhone() { 
        return phone;
    } 

    public void setPhone(String Phone) { 
        this.phone = Phone;
    } 

    @Basic 
    @Column(name = "UserLoginType", columnDefinition = "varchar(50) NULL") 
    public String getUserLoginType() { 
        return userLoginType;
    } 

    public void setUserLoginType(String UserLoginType) { 
        this.userLoginType = UserLoginType;
    } 

    @Basic 
    @Column(name = "UserValidate", columnDefinition = "varchar(255)")
    public String getUserValidate() {
        return userValidate;
    } 

    public void setUserValidate(String UserSsoToken) {
        this.userValidate = UserSsoToken;
    } 

    @Basic 
    @Column(name = "EditUser", columnDefinition = "varchar(50) NULL") 
    public String getEditUser() { 
        return editUser;
    } 

    public void setEditUser(String EditUser) { 
        this.editUser = EditUser;
    } 

    @Basic 
    @Column(name = "EditTime", columnDefinition = "datetime2(0) NULL") 
    public Timestamp getEditTime() { 
        return editTime;
    } 

    public void setEditTime(Timestamp EditTime) { 
        this.editTime = EditTime;
    }

} 
