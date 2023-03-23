package gov.kcg.kgo.viewModel.backend.kgoLog.exportPdf.rq;

import java.util.List;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "後台 - 軌跡紀錄 - 匯出Pdf rq")
public class KgoLogExportPdfActionRq extends ApiRequest {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "系統類型 (B:後台, F:前台)")
	private String systemType;

	@ApiModelProperty(value = "pdf圖片base64清單")
	/** The img base 64 list. */
	private List<String> imgBase64List;

	public String getSystemType() {
		return systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	public List<String> getImgBase64List() {
		return imgBase64List;
	}

	public void setImgBase64List(List<String> imgBase64List) {
		this.imgBase64List = imgBase64List;
	}
	
}
