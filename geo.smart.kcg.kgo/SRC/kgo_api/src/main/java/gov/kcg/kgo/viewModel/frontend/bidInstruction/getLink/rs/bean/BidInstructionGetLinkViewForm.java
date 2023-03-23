package gov.kcg.kgo.viewModel.frontend.bidInstruction.getLink.rs.bean;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 申辦說明頁-取得站外連結 View Form
 */
@ApiModel(description = "申辦說明頁-取得站外連結 View Form")
public class BidInstructionGetLinkViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

    /** 站外連結網址 */
    @ApiModelProperty(notes = "站外連結網址")
    private String linkType;

	/** 對外連結 */
	@ApiModelProperty(notes = "對外連結")
	private String linkUrl;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

}
