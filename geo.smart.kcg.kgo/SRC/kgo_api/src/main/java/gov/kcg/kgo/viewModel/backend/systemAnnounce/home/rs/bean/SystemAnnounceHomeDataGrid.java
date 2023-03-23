package gov.kcg.kgo.viewModel.backend.systemAnnounce.home.rs.bean;

import java.util.List;

import gov.kcg.kgo.viewModel.backend.taskMaintain.editHome.rs.bean.TaskMaintainEditHomeFileViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 系統公告-公告清單
 */
@ApiModel(description = "系統公告-公告清單")
public class SystemAnnounceHomeDataGrid {

	/** 序號 */
	@ApiModelProperty(notes = "序號")
	private Integer seq;

	/** 公告標題 */
	@ApiModelProperty(notes = "公告標題")
	private String name;

	/** 公告內容 */
	@ApiModelProperty(notes = "公告內容")
	private String content;

	@ApiModelProperty(value = "附件")
	private List<TaskMaintainEditHomeFileViewForm> files;

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<TaskMaintainEditHomeFileViewForm> getFiles() {
		return files;
	}

	public void setFiles(List<TaskMaintainEditHomeFileViewForm> files) {
		this.files = files;
	}

}
