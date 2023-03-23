package gov.kcg.kgo.service.impl.helper;

import java.util.Map;

import gov.kcg.kgo.service.bean.mydata.viewModel.ColumnModel;

/**
 * 駕籍資料
 * 
 * @author joelee
 *
 */
public class DriverLicenseMyDataHelper extends KgoMydataBaseServiceHelper {

	/**
	 * Instantiates a new service helper.
	 */
	public DriverLicenseMyDataHelper() {
		super();
	}

	// TODO 共通邏輯方法
	private static class Loader {
		/** The Constant instance. */
		private static final DriverLicenseMyDataHelper instance = new DriverLicenseMyDataHelper();
	}

	/**
	 * Gets the single instance of DriverLicenseMyDataHelper.
	 *
	 * @return single instance of DriverLicenseMyDataHelper
	 */
	public static DriverLicenseMyDataHelper getInstance() {
		return Loader.instance;
	}

	@Override
	public String getMyDataId() {
		// TODO Auto-generated method stub
		return "API.n0c9qVZBQI";
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
