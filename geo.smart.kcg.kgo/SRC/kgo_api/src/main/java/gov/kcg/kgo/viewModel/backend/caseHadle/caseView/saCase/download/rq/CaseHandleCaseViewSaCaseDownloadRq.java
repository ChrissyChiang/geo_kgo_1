package gov.kcg.kgo.viewModel.backend.caseHadle.caseView.saCase.download.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台案件處理-案件檢視-服務申辦(SA)案件檢視-檔案下載(myData資料、上傳附件) rq")
public class CaseHandleCaseViewSaCaseDownloadRq extends ApiRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "案件編號")
	private String caseId;
	
	@ApiModelProperty(value = "檔案名稱")
	private String fileName;

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
