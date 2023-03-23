package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.viewModel.backend.taskMaintain.editHome.rs.bean.SiteMaintainEditHomeFileViewForm;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "場地新增/編輯 rq")
public class GeoSiteEditRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "場地編號")
    private String siteMainId;

    public String getSiteMainId() {
        return siteMainId;
    }

    public void setSiteMainId(String siteMainId) {
        this.siteMainId = siteMainId;
    }
}
