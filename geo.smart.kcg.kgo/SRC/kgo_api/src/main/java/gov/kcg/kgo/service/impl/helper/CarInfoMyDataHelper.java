package gov.kcg.kgo.service.impl.helper;

import java.util.Map;

import gov.kcg.kgo.service.bean.mydata.viewModel.ColumnModel;

/**
 * 車籍資料
 * 
 * @author joelee
 *
 */
public class CarInfoMyDataHelper extends KgoMydataBaseServiceHelper {

	/**
	 * Instantiates a new service helper.
	 */
	public CarInfoMyDataHelper() {
		super();
	}

	// TODO 共通邏輯方法
	private static class Loader {
		/** The Constant instance. */
		private static final CarInfoMyDataHelper instance = new CarInfoMyDataHelper();
	}

	/**
	 * Gets the single instance of CarInfoMyDataHelper.
	 *
	 * @return single instance of CarInfoMyDataHelper
	 */
	public static CarInfoMyDataHelper getInstance() {
		return Loader.instance;
	}

	@Override
	public String getMyDataId() {
		// TODO Auto-generated method stub
		return "API.60GGfgGX1A";
	}

	@Override
	public Object getCurrentColumnData(Map<String, Object> originalData, Map<String, Object> currentData,
			String columnName, ColumnModel columnModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String mockMyDataJsonString() {
		// TODO Auto-generated method stub
		return "[\n" + "  {\n" + "    \"birthday\": \"100-01-01\",\n" + "    \"bodyNo\": \"ABCDE12345\",\n"
				+ "    \"brandName\": \"福特\",\n" + "    \"brandNo\": 12345,\n" + "    \"carType\": \"1\",\n"
				+ "    \"cc\": 2000,\n" + "    \"colorName\": \"白色\",\n" + "    \"colorNo\": \"A\",\n"
				+ "    \"lastPrintLicDt\": \"2018-07-13T13:16:10+08:00\",\n"
				+ "    \"licEffectDt\": \"2018-07-13T13:16:10+08:00\",\n" + "    \"modelName\": \"轎式 \",\n"
				+ "    \"modelNo\": \"1\",\n" + "    \"nextExamDt\": \"2018-07-13T13:16:10+08:00\",\n"
				+ "    \"ownerId\": \"A123456789\",\n" + "    \"ownerName\": \"王小明\",\n"
				+ "    \"telNo\": \"0910111111\",\n" + "    \"permAddr\": \"中正北路 XX 巷 XX 號\",\n"
				+ "    \"permZipName\": \"基隆市七堵區\",\n" + "    \"permZipNo\": 20653,\n"
				+ "    \"plateNo\": \"ABC-123\",\n" + "    \"txnName\": \"正常\",\n" + "    \"txnNo\": \"12345\"\n"
				+ "  }\n" + "]";
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
