package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.geoentity.OrganDiscountRatio;
import gov.kcg.kgo.util.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "機關折扣回傳物件 model")
public class OrganDiscountRatioModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(notes = "折扣ID")
    private Long organDiscountRatioId;
    @ApiModelProperty(notes = "機關ID")
    private String organId;
    @ApiModelProperty(notes = "機關名稱")
    private String organName;
    @ApiModelProperty(notes = "折扣比例%")
    private Integer discountRatio;
    @ApiModelProperty(notes = "建立人")
    private String editUser;
    @ApiModelProperty(notes = "建立時間 yyyy/MM/dd ")
    private String editTime;

    public Long getOrganDiscountRatioId() {
        return organDiscountRatioId;
    }

    public void setOrganDiscountRatioId(Long organDiscountRatioId) {
        this.organDiscountRatioId = organDiscountRatioId;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public Integer getDiscountRatio() {
        return discountRatio;
    }

    public void setDiscountRatio(Integer discountRatio) {
        this.discountRatio = discountRatio;
    }

    public String getEditUser() {
        return editUser;
    }

    public void setEditUser(String editUser) {
        this.editUser = editUser;
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public static OrganDiscountRatioModel entityToModel(OrganDiscountRatio entity){
        OrganDiscountRatioModel model = new OrganDiscountRatioModel();
        model.setOrganId(entity.getOrganId());
        model.setDiscountRatio(entity.getDiscountRatio());
        model.setOrganDiscountRatioId(entity.getOrganDiscountRatioId());
        model.setEditTime(DateUtil.dateToString(entity.getEditTime(),DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH));
        model.setEditUser(entity.getEditUser());
        return model;
    }
}
