package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.enums.common.sso.LoginAuthTokenType;
import org.springframework.stereotype.Component;
import io.swagger.annotations.ApiModel; 
import io.swagger.annotations.ApiModelProperty; 
import java.io.Serializable; 
import java.util.ArrayList; 
import java.util.List;

import gov.kcg.kgo.geoentity.GeoKgoFrontendUser;

/** 
 * GEO 20211113 add 前台使用者註冊
 * Model for 使用者帳號
 */
@Component
@ApiModel(description = "使用者帳號")
public class GeoKgoFrontendUserModel implements Serializable {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(notes = "使用者id")
    private String UserId; 

    @ApiModelProperty(notes = "信箱")
    private String Email; 

    @ApiModelProperty(notes = "姓名")
    private String Name; 

    @ApiModelProperty(notes = "身分證號")
    private String Identity; 

    @ApiModelProperty(notes = "手機")
    private String Phone; 

    @ApiModelProperty(notes = "登入方式")
    private String UserLoginType;

    public String getUserId() { 
        return UserId; 
    } 

    public void setUserId(String UserId) { 
        this.UserId = UserId; 
    } 

    public String getEmail() { 
        return Email; 
    } 

    public void setEmail(String Email) { 
        this.Email = Email; 
    } 

    public String getName() { 
        return Name; 
    } 

    public void setName(String Name) { 
        this.Name = Name; 
    } 

    public String getIdentity() { 
        return Identity; 
    } 

    public void setIdentity(String Identity) { 
        this.Identity = Identity; 
    } 

    public String getPhone() { 
        return Phone; 
    } 

    public void setPhone(String Phone) { 
        this.Phone = Phone; 
    } 

    public String getUserLoginType() { 
        return UserLoginType; 
    } 

    public void setUserLoginType(String UserLoginType) {
        this.UserLoginType = UserLoginType;
    }


    public static GeoKgoFrontendUserModel changeToModel(GeoKgoFrontendUser entity) {
        GeoKgoFrontendUserModel model = new GeoKgoFrontendUserModel();
        model.setUserId(entity.getUserId()); 
        model.setEmail(entity.getEmail()); 
        model.setName(entity.getName()); 
        model.setIdentity(entity.getUserIdentity());
        model.setPhone(entity.getPhone()); 
        model.setUserLoginType(entity.getUserLoginType());
        return model; 
    } //changeToModel 

    public static List<GeoKgoFrontendUserModel> changeListToModel(List<GeoKgoFrontendUser> entityList) {
        List<GeoKgoFrontendUserModel> modelList = null;
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoFrontendUserModel model = changeToModel(entityList.get(i));
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
