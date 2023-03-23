package gov.kcg.kgo.geomodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * GEO 20211005 add
 * 申辦服務名次對應
 */

@ApiModel(description = "申辦服務名次對應")
public class GeoCaseSetApplyRankModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名次", required = true)
    private Integer casesetRank;

    @ApiModelProperty(value = "服務id", required = true)
    private String casesetId;

    public Integer getCasesetRank() {
        return casesetRank;
    }

    public void setCasesetRank(Integer casesetRank) {
        this.casesetRank = casesetRank;
    }

    public String getCasesetId() {
        return casesetId;
    }

    public void setCasesetId(String casesetId) {
        this.casesetId = casesetId;
    }
}
