package gov.kcg.kgo.geoviewmodel.frontend.rs.bean;

import gov.kcg.kgo.geomodel.GeoKgoMyDataCaseModel;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GEO 20221009 add_Jim
 * MyData紀錄查詢(申請名稱、申請編號) ViewForm
 */
@ApiModel(description = "MyData紀錄查詢 ViewForm")
public class GeoMyDataQueryViewForm extends BaseViewForm {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "MyData紀錄查詢")
    private List<GeoKgoMyDataCaseModel> caseList;
    @ApiModelProperty(value = "MyData紀錄總頁數")
    private Integer totalPage;
    @ApiModelProperty(value = "MyData紀錄總筆數")
    private Integer totalSize;

    public List<GeoKgoMyDataCaseModel> getCaseList() {
        return caseList;
    }

    public void setCaseList(List<GeoKgoMyDataCaseModel> caseList) {
        this.caseList = caseList;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }
}
