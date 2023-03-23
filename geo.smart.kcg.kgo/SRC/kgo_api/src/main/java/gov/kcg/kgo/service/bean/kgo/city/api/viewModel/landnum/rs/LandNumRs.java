package gov.kcg.kgo.service.bean.kgo.city.api.viewModel.landnum.rs;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import gov.kcg.kgo.service.bean.kgo.city.api.viewModel.landnum.rs.bean.DataModel;
import gov.kcg.kgo.service.bean.kgo.city.api.viewModel.rs.BaseKgoCityApi;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LandNumRs extends BaseKgoCityApi<List<DataModel>> {

	private static final long serialVersionUID = 1L;

}
