package gov.kcg.kgo.geoviewmodel.frontend.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211011 add
 * 後台-問卷結果查詢:初始畫面 View Form
 */
@ApiModel(description = "服務案件清單-初始畫面 View Form")
public class GeoQuestionnaireResultHomeViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    /**
     * 初始時列出所有的機關分類資料的 comboBox
     */
    @ApiModelProperty(value = "機關分類comboBox元件")
    private ComboBox organComboBox;

    public ComboBox getOrganComboBox() {
        return organComboBox;
    }

    public void setOrganComboBox(ComboBox organComboBox) {
        this.organComboBox = organComboBox;
    }

}
