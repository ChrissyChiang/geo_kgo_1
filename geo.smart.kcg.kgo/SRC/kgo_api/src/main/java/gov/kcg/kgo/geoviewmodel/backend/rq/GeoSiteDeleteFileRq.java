package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(description = "場地案件檢視 - 檔案刪除 rq")
public class GeoSiteDeleteFileRq extends ApiRequest {

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

