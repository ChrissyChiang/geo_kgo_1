package gov.kcg.kgo.viewModel.backend.taskMaintain.editHome.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 任務及公告管理-任務維護(新增/修改)初始畫面 View Form
 */
@ApiModel(description = "任務及公告管理-任務維護(新增/修改)初始畫面 View Form")
public class TaskMaintainEditHomeViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	/** 序號 **/
	@ApiModelProperty(value = "序號")
	private Integer seq;

	/** 發布對象(F:前台/B:後台) **/
	@ApiModelProperty(value = "發布對象(F:前台/B:後台)")
	private String releaseObject;

	/**
	 * ===================== common =====================
	 */
	/** 活動名稱 **/
	@ApiModelProperty(value = "活動名稱")
	private String activityName;

	/** 活動內容 **/
	@ApiModelProperty(value = "活動內容")
	private String activityContent;

	@ApiModelProperty(value = "附件")
	private List<TaskMaintainEditHomeFileViewForm> files;

	/**
	 * ===================== only for KGO_TASK_SET =====================
	 */
	/** 活動日期 **/
	@ApiModelProperty(value = "活動日期")
	private String[] pfDate;

	/** 活動項目下拉式選單 **/
	@ApiModelProperty(value = "活動項目下拉式選單")
	private ComboBox activityItemOptions;

	/** 程式幣總點數 **/
	@ApiModelProperty(value = "程式幣總點數")
	private String totalPoint;

	/** 每次核發點數 **/
	@ApiModelProperty(value = "每次核發點數")
	private String point;

	/** 服務案件-機關 **/
	@ApiModelProperty(value = "服務案件-機關")
	private ComboBox organNameOptions;

	/** 服務案件-案件 **/
	@ApiModelProperty(value = "服務案件-案件")
	private ComboBox caseNameOptions;

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getReleaseObject() {
		return releaseObject;
	}

	public void setReleaseObject(String releaseObject) {
		this.releaseObject = releaseObject;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityContent() {
		return activityContent;
	}

	public void setActivityContent(String activityContent) {
		this.activityContent = activityContent;
	}

	public String[] getPfDate() {
		return pfDate;
	}

	public void setPfDate(String[] pfDate) {
		this.pfDate = pfDate;
	}

	public ComboBox getActivityItemOptions() {
		return activityItemOptions;
	}

	public void setActivityItemOptions(ComboBox activityItemOptions) {
		this.activityItemOptions = activityItemOptions;
	}

	public String getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(String totalPoint) {
		this.totalPoint = totalPoint;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public ComboBox getOrganNameOptions() {
		return organNameOptions;
	}

	public void setOrganNameOptions(ComboBox organNameOptions) {
		this.organNameOptions = organNameOptions;
	}

	public ComboBox getCaseNameOptions() {
		return caseNameOptions;
	}

	public void setCaseNameOptions(ComboBox caseNameOptions) {
		this.caseNameOptions = caseNameOptions;
	}

	public List<TaskMaintainEditHomeFileViewForm> getFiles() {
		return files;
	}

	public void setFiles(List<TaskMaintainEditHomeFileViewForm> files) {
		this.files = files;
	}
}
