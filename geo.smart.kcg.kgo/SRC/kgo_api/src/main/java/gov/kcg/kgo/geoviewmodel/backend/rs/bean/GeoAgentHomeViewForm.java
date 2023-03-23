package gov.kcg.kgo.geoviewmodel.backend.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211026 add
 * 後台-設定代理人:初始畫面 ViewForm
 */

@ApiModel(description = "後台-設定代理人:初始畫面 ViewForm")
public class GeoAgentHomeViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    /**
     * 初始時列出所有的機關資料的 comboBox
     */
    @ApiModelProperty(value = "機關comboBox元件")
    private ComboBox organComboBox;

    /**
     * 初始時只會顯示預設值的comboBox
     */
    @ApiModelProperty(value = "單位comboBox元件")
    private ComboBox unitComboBox;

    public ComboBox getOrganComboBox() {
        return organComboBox;
    }

    public void setOrganComboBox(ComboBox organComboBox) {
        this.organComboBox = organComboBox;
    }

    public ComboBox getUnitComboBox() {
        return unitComboBox;
    }

    public void setUnitComboBox(ComboBox unitComboBox) {
        this.unitComboBox = unitComboBox;
    }
}
