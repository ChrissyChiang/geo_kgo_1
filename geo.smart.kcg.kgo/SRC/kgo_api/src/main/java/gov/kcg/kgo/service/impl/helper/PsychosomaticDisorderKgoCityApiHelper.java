package gov.kcg.kgo.service.impl.helper;

import java.util.Map;

import gov.kcg.kgo.service.bean.mydata.viewModel.ColumnModel;

public class PsychosomaticDisorderKgoCityApiHelper extends KgoMydataBaseServiceHelper {

	/**
	 * Instantiates a new service helper.
	 */
	public PsychosomaticDisorderKgoCityApiHelper() {
		super();
	}

	// TODO 共通邏輯方法
	private static class Loader {
		/** The Constant instance. */
		private static final PsychosomaticDisorderKgoCityApiHelper instance = new PsychosomaticDisorderKgoCityApiHelper();
	}

	/**
	 * Gets the single instance of LowIncomeInfoMyDataHelper.
	 *
	 * @return single instance of LowIncomeInfoMyDataHelper
	 */
	public static PsychosomaticDisorderKgoCityApiHelper getInstance() {
		return Loader.instance;
	}

	@Override
	public String getMyDataId() {
		// TODO Auto-generated method stub
		return "7d65cf3d-6030-446e-b86d-7c0fe59c5624";
	}

	@Override
	public Object getCurrentColumnData(Map<String, Object> originalData, Map<String, Object> currentData, String columnName, ColumnModel columnModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String mockMyDataJsonString() {
		// TODO Auto-generated method stub
		return "{\n" + "    \"contentType\": \"application/json; charset=utf-8\",\n" + "    \"isImage\": false,\n" + "    \"data\": {\n" + "        \"Id\": \"S2xxx3906\",\n"
				+ "        \"Typename\": \"ICF二類中度\",\n" + "        \"Username\": \"黃林美惠\",\n" + "        \"Profile\": {\n" + "            \"Gender\": \"女\",\n" + "            \"Age\": 66,\n"
				+ "            \"Address\": \"彌陀區\"\n" + "        }\n" + "    },\n" + "    \"id\": \"0b5856e6-2ce9-4b05-a58c-f03fcda442c4\",\n" + "    \"success\": true\n" + "}";
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
