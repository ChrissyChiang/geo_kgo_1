package gov.kcg.kgo.geomodel.base;

import gov.kcg.kgo.geoentity.GeoKgoFrontendUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/** 
 * GEO 20211113 add 前台使用者註冊
 * Model for 使用者帳號
 */
@Component
@ApiModel(description = "使用者帳號")
public class GeoKgoFrontendUserModelBase implements Serializable {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(notes = "使用者id")
    private String userId;

    @ApiModelProperty(notes = "信箱")
    private String email;

    @ApiModelProperty(notes = "姓名")
    private String name;

    @ApiModelProperty(notes = "身分證號")
    private String identity;

    @ApiModelProperty(notes = "手機")
    private String phone;

    @ApiModelProperty(notes = "登入方式")
    private String userLoginType;

    @ApiModelProperty(notes = "驗證資訊")
    private String userValidate;

    @ApiModelProperty(notes = "編輯人員")
    private String editUser;

    @ApiModelProperty(notes = "編輯時間")
    private Timestamp editTime;

    public String getUserId() { 
        return userId;
    } 

    public void setUserId(String UserId) { 
        this.userId = UserId;
    } 

    public String getEmail() { 
        return email;
    } 

    public void setEmail(String Email) { 
        this.email = Email;
    } 

    public String getName() { 
        return name;
    } 

    public void setName(String Name) { 
        this.name = Name;
    } 

    public String getIdentity() { 
        return identity;
    } 

    public void setIdentity(String Identity) { 
        this.identity = Identity;
    } 

    public String getPhone() { 
        return phone;
    } 

    public void setPhone(String Phone) { 
        this.phone = Phone;
    } 

    public String getUserLoginType() { 
        return userLoginType;
    } 

    public void setUserLoginType(String UserLoginType) { 
        this.userLoginType = UserLoginType;
    } 

    public String getUserValidate() {
        return userValidate;
    } 

    public void setUserValidate(String UserSsoToken) {
        this.userValidate = UserSsoToken;
    } 

    public String getEditUser() { 
        return editUser;
    } 

    public void setEditUser(String EditUser) { 
        this.editUser = EditUser;
    } 

    public Timestamp getEditTime() { 
        return editTime;
    } 

    public void setEditTime(Timestamp EditTime) { 
        this.editTime = EditTime;
    }


    public static GeoKgoFrontendUserModelBase changeToModel(GeoKgoFrontendUser entity) { 
        GeoKgoFrontendUserModelBase model = new GeoKgoFrontendUserModelBase(); 
        model.setUserId(entity.getUserId()); 
        model.setEmail(entity.getEmail()); 
        model.setName(entity.getName()); 
        model.setIdentity(entity.getUserIdentity());
        model.setPhone(entity.getPhone()); 
        model.setUserLoginType(entity.getUserLoginType()); 
        model.setUserValidate(entity.getUserValidate());
        model.setEditUser(entity.getEditUser()); 
        model.setEditTime(entity.getEditTime());
        return model; 
    } //changeToModel 

    public static List<GeoKgoFrontendUserModelBase> changeListToModel(List<GeoKgoFrontendUser> entityList) { 
        List<GeoKgoFrontendUserModelBase> modelList = null; 
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoFrontendUserModelBase model = changeToModel(entityList.get(i)); 
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
