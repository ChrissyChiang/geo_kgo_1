package gov.kcg.kgo.viewModel.backend.classManagement.classQuery.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 分類維護-主畫面搜尋
 */
@ApiModel(description = "分類維護-主畫面搜尋")
public class ClassManagementClassQueryDataGrid {

	/** 序號 */
	@ApiModelProperty(notes = "序號")
	private Integer seq;

	/** 隸屬主類別代碼 */
	@ApiModelProperty(notes = "隸屬主類別代碼")
	private String mainType;

	/** 隸屬主類別名稱 */
	@ApiModelProperty(notes = "隸屬主類別名稱")
	private String mainTypeNM;

	/** 次類別名稱 */
	@ApiModelProperty(notes = "次類別名稱")
	private String detailNM;

	/** 上下架時間 (yyyyMMdd) */
	@ApiModelProperty(notes = "上下架時間")
	private String publishTime;

	/** 狀態 */
	@ApiModelProperty(notes = "狀態")
	private String state;

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getMainType() {
		return mainType;
	}

	public void setMainType(String mainType) {
		this.mainType = mainType;
	}

	public String getMainTypeNM() {
		return mainTypeNM;
	}

	public void setMainTypeNM(String mainTypeNM) {
		this.mainTypeNM = mainTypeNM;
	}

	public String getDetailNM() {
		return detailNM;
	}

	public void setDetailNM(String detailNM) {
		this.detailNM = detailNM;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
