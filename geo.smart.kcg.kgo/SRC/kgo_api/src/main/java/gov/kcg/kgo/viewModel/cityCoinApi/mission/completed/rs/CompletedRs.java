package gov.kcg.kgo.viewModel.cityCoinApi.mission.completed.rs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import gov.kcg.kgo.viewModel.cityCoinApi.base.response.CityCoinApiBaseResponse;
import gov.kcg.kgo.viewModel.cityCoinApi.mission.completed.rs.bean.CompletedViewForm;
import io.swagger.annotations.ApiModel;

/**
 * 城市幣API-取得城市幣任務清單資料 rs
 */
@ApiModel(description = "城市幣API-完成任務 rs")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompletedRs extends CityCoinApiBaseResponse<CompletedViewForm> {

}
