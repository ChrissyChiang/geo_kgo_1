package gov.kcg.kgo.geoviewmodel.frontend.rs.bean;

import gov.kcg.kgo.geomodel.GeoUserQueryModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20221014 add_Jim
 * 查詢使用者資訊 rs ViewForm
 */
@ApiModel(description = "查詢使用者資訊 rs ViewForm")
public class GeoUserQueryViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "使用者資訊")
    private List<GeoUserQueryModel> userList;

    public List<GeoUserQueryModel> getUserList() {
        return userList;
    }

    public void setUserList(List<GeoUserQueryModel> userList) {
        this.userList = userList;
    }
}
