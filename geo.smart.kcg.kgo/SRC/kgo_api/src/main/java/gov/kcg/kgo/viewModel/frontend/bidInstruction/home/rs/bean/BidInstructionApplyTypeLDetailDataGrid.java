package gov.kcg.kgo.viewModel.frontend.bidInstruction.home.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 申辦說明頁-書表下載案件檔案資料集合
 */
@ApiModel(description = "申辦說明頁-書表下載案件檔案資料集合")
public class BidInstructionApplyTypeLDetailDataGrid {

	/** 序號 */
	@ApiModelProperty(notes = "序號")
	private Integer seq;

	/** 標題 */
	@ApiModelProperty(notes = "標題")
	private String title;

    /** 分類 */
    @ApiModelProperty(notes = "分類")
    private String type;

    /** 分類名稱 */
    @ApiModelProperty(notes = "分類名稱")
    private String typeName;

    /** 附件 */
    @ApiModelProperty(notes = "附件")
    private String fileName;

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
