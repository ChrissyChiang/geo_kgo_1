package gov.kcg.kgo.service.impl.helper;

import java.util.Map;

import gov.kcg.kgo.service.bean.mydata.viewModel.ColumnModel;

/**
 * 勞工保險被保險人投保資料(明細)
 * 
 * @author joelee
 *
 */
public class LaborInsuranceInsuranceInfoMyDataHelper extends KgoMydataBaseServiceHelper {

	/**
	 * Instantiates a new service helper.
	 */
	public LaborInsuranceInsuranceInfoMyDataHelper() {
		super();
	}

	// TODO 共通邏輯方法
	private static class Loader {
		/** The Constant instance. */
		private static final LaborInsuranceInsuranceInfoMyDataHelper instance = new LaborInsuranceInsuranceInfoMyDataHelper();
	}

	/**
	 * Gets the single instance of PersonalInsuranceInfoMyDataHelper.
	 *
	 * @return single instance of PersonalInsuranceInfoMyDataHelper
	 */
	public static LaborInsuranceInsuranceInfoMyDataHelper getInstance() {
		return Loader.instance;
	}

	@Override
	public String getMyDataId() {
		// TODO Auto-generated method stub
		return "API.UZQkKbsOpz";
	}

	@Override
	public Object getCurrentColumnData(Map<String, Object> originalData, Map<String, Object> currentData, String columnName, ColumnModel columnModel) {
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
