package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.util.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * GEO 20211012 add
 * Model for 後台-檢視填寫:取得答案清單
 */
@Component
@ApiModel(description = "後台-檢視填寫:取得答案清單")
public class GeoKgoQuestionnaireAnswerListModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "填寫人姓名")
    private String userName;

    @ApiModelProperty("填寫人Email")
    private String userEmail;

    @ApiModelProperty("填寫人手機號碼")
    private String userMobilePhone;

    @ApiModelProperty("填寫時間")
    private String applyDate;

    @ApiModelProperty("案件Id")
    private String caseId;

    @ApiModelProperty("答案卷Id")
    private String answerId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMobilePhone() {
        return userMobilePhone;
    }

    public void setUserMobilePhone(String userMobilePhone) {
        this.userMobilePhone = userMobilePhone;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Timestamp applyDate) {
        DateUtil.dateToString(applyDate, DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH_TIME);
        this.applyDate =  DateUtil.dateToString(applyDate, DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH_TIME);
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }
}
