package gov.kcg.kgo.geoviewmodel.frontend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "前台-下載檔案 rq")
public class CaseSiteFileDownloadRq extends ApiRequest {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(notes="檔案ID")
    private String fileId;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
