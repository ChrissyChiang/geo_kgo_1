package gov.kcg.kgo.viewModel.frontend.hotSearch.rq;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 20220729 前台切換搜尋引擎
 * 高雄市府搜尋引擎 rq
 */
@ApiModel(description = "高雄市府搜尋引擎-rq")
public class HotSearchGovernmentQueryRq extends ApiRequest {

    @ApiModelProperty(value = "關鍵字")
    private String keyword;

    @ApiModelProperty(value = "欲取得頁數")
    private Integer current;

    @ApiModelProperty(value = "每頁筆數")
    private Integer size;

    @ApiModelProperty(value = "非必填，選擇時間範圍,近三日:3D、一個月:1M、三個月:3M、六個月:6M、超過半年:B6M")
    private String selectedDate;

    @ApiModelProperty(value = "非必填,選擇分類範圍：交通建設、教育學習")
    private String selectedStation;

    @ApiModelProperty(value = "非必填,選擇排序方式：相關性(高->低)：SCORE_DESC...")
    private String selectedOrder;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }

    public String getSelectedStation() {
        return selectedStation;
    }

    public void setSelectedStation(String selectedStation) {
        this.selectedStation = selectedStation;
    }

    public String getSelectedOrder() {
        return selectedOrder;
    }

    public void setSelectedOrder(String selectedOrder) {
        this.selectedOrder = selectedOrder;
    }
}
