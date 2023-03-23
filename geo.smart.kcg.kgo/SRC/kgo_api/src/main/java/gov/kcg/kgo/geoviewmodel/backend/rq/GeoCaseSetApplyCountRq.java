package gov.kcg.kgo.geoviewmodel.backend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211005 add
 * 服務申辦統計-取得申辦服務名次列表 rq
 */

@ApiModel(description = "服務申辦統計-取得申辦服務名次列表 rq")
public class GeoCaseSetApplyCountRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "搜尋區間 (1-每日,2-每週,3-每月,4-自訂)", required = true)
    private Integer searchRangeType;

    @ApiModelProperty(value = "搜尋前幾名，預設10")
    private Integer searchRank = 10;

    @ApiModelProperty(value = "起始日 yyyy-MM-dd，搜尋區間=4：必填")
    private String dateStart;

    @ApiModelProperty(value = "結束日 yyyy-MM-dd，搜尋區間=4：必填")
    private String dateEnd;

    @ApiModelProperty(value = "案件啟用狀態 (開啟-On,關閉-Off)，不填時包含所有狀態")
    private String caseSetStatus;

    public Integer getSearchRangeType() {
        return searchRangeType;
    }

    public void setSearchRangeType(Integer searchRangeType) {
        this.searchRangeType = searchRangeType;
    }

    public Integer getSearchRank() {
        return searchRank;
    }

    public void setSearchRank(Integer searchRank) {
        this.searchRank = searchRank;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getCaseSetStatus() {
        return caseSetStatus;
    }

    public void setCaseSetStatus(String caseSetStatus) {
        this.caseSetStatus = caseSetStatus;
    }
}
