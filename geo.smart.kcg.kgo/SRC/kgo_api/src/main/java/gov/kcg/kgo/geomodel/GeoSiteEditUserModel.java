package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "後台-場地編輯者")
public class GeoSiteEditUserModel extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "建立者帳號")
    private String userId;

    @ApiModelProperty(value = "建立者姓名")
    private String userName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
