package gov.kcg.kgo.viewModel.frontend.caseform.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "前臺 取得 MyData 轉址 URL")
public class MyDataActionUrlRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "案件種類ID")
	private String caseSetId;

	@ApiModelProperty(notes = "統號")
	private String pid;

	@ApiModelProperty(notes = "生日")
	private String birthday;

	public String getCaseSetId() {
		return caseSetId;
	}

	public void setCaseSetId(String caseSetId) {
		this.caseSetId = caseSetId;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

}