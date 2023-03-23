package gov.kcg.kgo.geoviewmodel.frontend.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20211011 add
 * 後台-問卷結果查詢:服務問卷查詢 rq
 */
@ApiModel(description = "後台-問卷結果查詢:服務問卷查詢 rq")
public class GeoQuestionnaireResultQueryRq extends ApiRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "機關分類代碼",required = true)
    private String organId;

    @ApiModelProperty(value = "案件名稱")
    private String caseSetName;

    @ApiModelProperty(value = "起始日 yyyy-MM-dd 有起始日即需有結束日")
    private String dateStart;

    @ApiModelProperty(value = "結束日 yyyy-MM-dd 有起始日即需有結束日")
    private String dateEnd;

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getCaseSetName() {
        return caseSetName;
    }

    public void setCaseSetName(String caseSetName) {
        this.caseSetName = caseSetName;
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
}
