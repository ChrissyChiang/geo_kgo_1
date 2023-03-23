package gov.kcg.kgo.viewModel.compoent.button;

import java.io.Serializable;

/**
 * Button.
 */
public class Button implements Serializable{

	private static final long serialVersionUID = -3696520094591213725L;

	/**
     * 是否顯示，預設為true.
     */
    private Boolean isShow = true;

    /** 是否 disable. */
    private Boolean isDisabled = false;


	/**
     * Constructor.
     */
    public Button() {
    }
    
    public Button(Boolean isDisabled) {
    	this.isDisabled = isDisabled;
    }
    
    public Button(Boolean isShow, Boolean isDisabled) {
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
}
