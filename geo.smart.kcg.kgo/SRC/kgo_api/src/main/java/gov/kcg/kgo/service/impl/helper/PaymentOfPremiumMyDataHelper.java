package gov.kcg.kgo.service.impl.helper;

import gov.kcg.kgo.service.bean.mydata.viewModel.ColumnModel;

import java.util.Map;

/**
 * GEO 20211130 add
 * 保費繳納
 */
public class PaymentOfPremiumMyDataHelper extends KgoMydataBaseServiceHelper {

	/**
	 * Instantiates a new service helper.
	 */
	public PaymentOfPremiumMyDataHelper() {
		super();
	}

	// TODO 共通邏輯方法
	private static class Loader {
		/** The Constant instance. */
		private static final PaymentOfPremiumMyDataHelper instance = new PaymentOfPremiumMyDataHelper();
	}

	/**
	 * Gets the single instance of LowIncomeInfoMyDataHelper.
	 *
	 * @return single instance of LowIncomeInfoMyDataHelper
	 */
	public static PaymentOfPremiumMyDataHelper getInstance() {
		return Loader.instance;
	}

	@Override
	public String getMyDataId() {
		// TODO Auto-generated method stub
		return "API.1qIr0nM0BT";
	}

	@Override
	public Object getCurrentColumnData(Map<String, Object> originalData, Map<String, Object> currentData,
			String columnName, ColumnModel columnModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String mockMyDataJsonString() {
		return null;
	}

	@Override
	public String mockMyDataPdfFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String mockMyDataCsvFile() {
		// TODO Auto-generated method stub
		return null;
	}

}
