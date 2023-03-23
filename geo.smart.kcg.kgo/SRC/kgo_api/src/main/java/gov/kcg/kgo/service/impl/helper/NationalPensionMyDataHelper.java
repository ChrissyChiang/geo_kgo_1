package gov.kcg.kgo.service.impl.helper;

import gov.kcg.kgo.service.bean.mydata.viewModel.ColumnModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * GEO 20211130 add
 * 國民年金
 *
 */
public class NationalPensionMyDataHelper extends KgoMydataBaseServiceHelper {
	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(NationalPensionMyDataHelper.class);

	/**
	 * Instantiates a new service helper.
	 */
	public NationalPensionMyDataHelper() {
		super();
	}

	// TODO 共通邏輯方法
	private static class Loader {
		/** The Constant instance. */
		private static final NationalPensionMyDataHelper instance = new NationalPensionMyDataHelper();
	}

	/**
	 * Gets the single instance of KinshipInfoMyDataHelper.
	 *
	 * @return single instance of KinshipInfoMyDataHelper
	 */
	public static NationalPensionMyDataHelper getInstance() {
		return Loader.instance;
	}

	@Override
	public String getMyDataId() {
		// TODO Auto-generated method stub
		return "API.EZ6XjKf1B4";
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
		return "{\n" +
				"\"httpCode\": \"200\", \"httpMessage\": \"OK\", \"returnCode\": \"0\", \"returnMessage\": \"查詢成功\", \"returnData\": [\n" +
				"{\n" +
				"\"IDN\": \"A123456789\",\n" +
				"\"name\": \"王小明\",\n" +
				"\"gvIdn\": \"A123456789\",\n" +
				"\"gvname\": \"王小明\",\n" +
				"\"apDte\": \"20190101\",\n" +
				"\"payKind\": \"老年年金\",\n" +
				"\"payYM\": \"201906\",\n" +
				"\"issueAmt\": \"3923\",\n" +
				"\"status\": \"合格(溢領金額:0;未收:0)\"\n" +
				"} ]\n" +
				"}";
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
