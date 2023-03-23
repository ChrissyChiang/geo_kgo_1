package gov.kcg.kgo.viewModel.frontend.caseform.rs.bean;

import java.util.Map;

import gov.kcg.kgo.viewModel.base.response.BaseViewForm;

public class MyDataDownloadMageViewFrom extends BaseViewForm {

	private static final long serialVersionUID = 1L;

	Map<String, Map<String, Object>> data;

	public Map<String, Map<String, Object>> getData() {
		return data;
	}

	public void setData(Map<String, Map<String, Object>> data) {
		this.data = data;
	}

}
