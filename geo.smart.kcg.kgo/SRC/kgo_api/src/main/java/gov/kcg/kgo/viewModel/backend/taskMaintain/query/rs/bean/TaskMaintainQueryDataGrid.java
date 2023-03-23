package gov.kcg.kgo.viewModel.backend.taskMaintain.query.rs.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 任務及公告管理-任務以及公告查詢資料集合
 */
@ApiModel(description = "任務及公告管理-任務以及公告查詢資料集合")
public class TaskMaintainQueryDataGrid {

	/** 活動序號 */
	@ApiModelProperty(notes = "活動序號")
	private Integer seq;

	/** 活動名稱 */
	@ApiModelProperty(notes = "活動名稱")
	private String titleName;

	/** 活動名稱 */
	@ApiModelProperty(notes = "活動狀態")
	private String state;

	/** 上下架日期 */
	@ApiModelProperty(notes = "上下架日期")
	private String publishTime;

	/** 發布對象 */
	@ApiModelProperty(notes = "發布對象")
	private String releaseObject;

	@ApiModelProperty(notes = "發布對象顯示名稱")
	private String releaseObjectName;

	/** 發布功能(AnnounceM:公告/TaskM:任務) **/
	@ApiModelProperty(value = "發布功能(AnnounceM:公告/TaskM:任務)")
	private String functionCode;

	@ApiModelProperty(value = "發布功能顯示名稱")
	private String functionCodeName;

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getReleaseObject() {
		return releaseObject;
	}

	public void setReleaseObject(String releaseObject) {
		this.releaseObject = releaseObject;
	}

	public String getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	public String getReleaseObjectName() {
		return releaseObjectName;
	}

	public void setReleaseObjectName(String releaseObjectName) {
		this.releaseObjectName = releaseObjectName;
	}

	public String getFunctionCodeName() {
		return functionCodeName;
	}

	public void setFunctionCodeName(String functionCodeName) {
		this.functionCodeName = functionCodeName;
	}

}
