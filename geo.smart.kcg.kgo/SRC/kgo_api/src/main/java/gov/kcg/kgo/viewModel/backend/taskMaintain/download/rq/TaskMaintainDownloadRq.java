package gov.kcg.kgo.viewModel.backend.taskMaintain.download.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "任務及公告管理-附件下載 rq")
public class TaskMaintainDownloadRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "活動序號")
	private Integer seq;

	@ApiModelProperty(value = "發布對象(F:前台/B:後台)")
	private String releaseObject;

	/** 發布功能(AnnounceM:公告/TaskM:任務) **/
	@ApiModelProperty(value = "發布功能(AnnounceM:公告/TaskM:任務)")
	private String functionCode;

	@ApiModelProperty(value = "附件Key")
	private String fileKey;

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

	public String getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	public String getFileKey() {
		return fileKey;
	}

	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}

}
