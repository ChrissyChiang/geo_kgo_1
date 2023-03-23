package gov.kcg.kgo.service.impl.helper;

import java.util.Map;

import gov.kcg.kgo.service.bean.mydata.viewModel.ColumnModel;

public class LowIncomeInfoKgoCityApiHelper extends KgoMydataBaseServiceHelper {

	/**
	 * Instantiates a new service helper.
	 */
	public LowIncomeInfoKgoCityApiHelper() {
		super();
	}

	// TODO 共通邏輯方法
	private static class Loader {
		/** The Constant instance. */
		private static final LowIncomeInfoKgoCityApiHelper instance = new LowIncomeInfoKgoCityApiHelper();
	}

	/**
	 * Gets the single instance of LowIncomeInfoMyDataHelper.
	 *
	 * @return single instance of LowIncomeInfoMyDataHelper
	 */
	public static LowIncomeInfoKgoCityApiHelper getInstance() {
		return Loader.instance;
	}

	@Override
	public String getMyDataId() {
		// TODO Auto-generated method stub
		return "ba5ab006-321e-495c-be27-14386b5da9cc";
	}

	@Override
	public Object getCurrentColumnData(Map<String, Object> originalData, Map<String, Object> currentData, String columnName, ColumnModel columnModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String mockMyDataJsonString() {
		// TODO Auto-generated method stub
		return "{\n" + "    \"contentType\": \"application/json; charset=utf-8\",\n" + "    \"isImage\": false,\n" + "    \"data\": {\n" + "        \"Id\": \"E2xxx5508\",\n"
				+ "        \"Typename\": \"中低收\",\n" + "        \"Username\": \"陳湘其\",\n" + "        \"Profile\": {\n" + "            \"Gender\": \"女\",\n" + "            \"Age\": 15,\n"
				+ "            \"Address\": \"鹽埕區\"\n" + "        }\n" + "    },\n" + "    \"id\": \"62f02fa0-6079-40ca-9603-61a84919fcea\",\n" + "    \"success\": true\n" + "}";
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
