package gov.kcg.kgo.service.impl.helper;

import java.util.Map;

import gov.kcg.kgo.service.bean.mydata.viewModel.ColumnModel;

/**
 * 綜所稅及房地合一稅繳納、欠稅及退稅紀錄
 * 
 * @author joelee
 *
 */
public class ComprehensiveTaxPayRecordsMyDataHelper extends KgoMydataBaseServiceHelper {

	/**
	 * Instantiates a new service helper.
	 */
	public ComprehensiveTaxPayRecordsMyDataHelper() {
		super();
	}

	// TODO 共通邏輯方法
	private static class Loader {
		/** The Constant instance. */
		private static final ComprehensiveTaxPayRecordsMyDataHelper instance = new ComprehensiveTaxPayRecordsMyDataHelper();
	}

	/**
	 * Gets the single instance of ComprehensiveTaxPayRecordsMyDataHelper.
	 *
	 * @return single instance of ComprehensiveTaxPayRecordsMyDataHelper
	 */
	public static ComprehensiveTaxPayRecordsMyDataHelper getInstance() {
		return Loader.instance;
	}

	@Override
	public String getMyDataId() {
		// TODO Auto-generated method stub
		return "API.1VFIQjmY1G";
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
