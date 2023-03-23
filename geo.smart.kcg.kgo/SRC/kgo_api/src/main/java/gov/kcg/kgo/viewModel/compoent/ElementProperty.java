package gov.kcg.kgo.viewModel.compoent;

/**
 * ElementProperty.
 */
public class ElementProperty implements java.io.Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7917842397355396097L;

    /**
     * 是否顯示，預設為true.
     */
    private Boolean isShow = true;

    /** 是否 disable. */
    private Boolean isDisabled = false;
    
    /** 編輯/修改 下拉選單選中值. */
    protected String selectedVal = "";
 

	/**
     * Constructor.
     */
    public ElementProperty() {
    }

    public ElementProperty(Boolean isShow, Boolean isDisabled) {
		this.isShow = isShow;
		this.isDisabled = isDisabled;
	}
    
	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	public Boolean getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(Boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	public String getSelectedVal() {
		return selectedVal;
	}

	public void setSelectedVal(String selectedVal) {
		this.selectedVal = selectedVal;
	}
}
