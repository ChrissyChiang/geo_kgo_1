package gov.kcg.kgo.viewModel.cityCoinApi.mission.search.rs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import gov.kcg.kgo.viewModel.cityCoinApi.base.response.CityCoinApiBaseResponse;
import gov.kcg.kgo.viewModel.cityCoinApi.mission.search.rs.bean.SearchViewForm;
import io.swagger.annotations.ApiModel;

/**
 * 城市幣API-取得單筆城市幣任務資料 rs
 */
@ApiModel(description = "城市幣API-取得單筆城市幣任務資料 rs")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchRs extends CityCoinApiBaseResponse<SearchViewForm> {

}
