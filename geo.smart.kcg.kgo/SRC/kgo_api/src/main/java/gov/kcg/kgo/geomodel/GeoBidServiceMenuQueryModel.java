package gov.kcg.kgo.geomodel;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;

/**
 * 20220725 add 依照使用者登入方式顯示符合的案件服務
 * 申辦服務選單-初始畫面-申辦案件數查詢(機關)
 */
public class GeoBidServiceMenuQueryModel {

    @ApiModelProperty(notes = "類別代碼")
    private String organNumber;

    @ApiModelProperty(notes = "機關類別名稱")
    private String organName;

    @ApiModelProperty(notes = "顯示申辦案件數")
    private Integer count;

    @ApiModelProperty(notes = "案件登入方式")
    private String checkType;

    public String getOrganNumber() {
        return organNumber;
    }

    public void setOrganNumber(String organNumber) {
        this.organNumber = organNumber;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }
}
