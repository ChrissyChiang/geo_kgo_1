package gov.kcg.kgo.geomodel;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * GEO 20211005 add
 * 服務申辦統計-申辦服務名次資料
 */

public class GeoCaseSetApplyCountRankModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "統計名次")
    private Integer applyCountRank;

    @ApiModelProperty(value = "申辦服務資料")
    private List<GeoCaseSetApplyCountModel> dataDetailList;


    public Integer getApplyCountRank() {
        return applyCountRank;
    }

    public void setApplyCountRank(Integer applyCountRank) {
        this.applyCountRank = applyCountRank;
    }

    public List<GeoCaseSetApplyCountModel> getDataDetailList() {
        return dataDetailList;
    }

    public void setDataDetailList(List<GeoCaseSetApplyCountModel> dataDetailList) {
        this.dataDetailList = dataDetailList;
    }
}
