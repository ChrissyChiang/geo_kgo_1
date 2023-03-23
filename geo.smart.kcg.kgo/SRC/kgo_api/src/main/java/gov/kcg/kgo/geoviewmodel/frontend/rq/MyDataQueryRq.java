package gov.kcg.kgo.geoviewmodel.frontend.rq;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20221009 add_Jim
 * MyData紀錄查詢(申請名稱、申請編號) rq
 */

@ApiModel(description = "紀錄查詢 rq")
public class MyDataQueryRq extends ApiRequest {

    @ApiModelProperty(value = "申請名稱關鍵字")
    private String myDataKeyWord;

    @ApiModelProperty(value = "申請編號關鍵字")
    private String myDataCaseNoKeyWord;

    @ApiModelProperty(value = "頁數")
    private Integer pageNumber;

    @ApiModelProperty(value = "每頁筆數")
    private Integer pageSize;

    public String getMyDataKeyWord() {
        return myDataKeyWord;
    }

    public void setMyDataKeyWord(String myDataKeyWord) {
        this.myDataKeyWord = myDataKeyWord;
    }

    public String getMyDataCaseNoKeyWord() {
        return myDataCaseNoKeyWord;
    }

    public void setMyDataCaseNoKeyWord(String myDataCaseNoKeyWord) {
        this.myDataCaseNoKeyWord = myDataCaseNoKeyWord;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(this);
    }
}