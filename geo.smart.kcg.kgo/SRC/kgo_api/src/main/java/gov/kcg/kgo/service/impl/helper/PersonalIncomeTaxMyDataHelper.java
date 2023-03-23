package gov.kcg.kgo.service.impl.helper;

import java.util.Map;

import gov.kcg.kgo.service.bean.mydata.viewModel.ColumnModel;

/**
 * 個人所得資料
 * 
 * @author joelee
 *
 */
public class PersonalIncomeTaxMyDataHelper extends KgoMydataBaseServiceHelper {

	/**
	 * Instantiates a new service helper.
	 */
	public PersonalIncomeTaxMyDataHelper() {
		super();
	}

	// TODO 共通邏輯方法
	private static class Loader {
		/** The Constant instance. */
		private static final PersonalIncomeTaxMyDataHelper instance = new PersonalIncomeTaxMyDataHelper();
	}

	/**
	 * Gets the single instance of PersonalIncomeTaxMyDataHelper.
	 *
	 * @return single instance of PersonalIncomeTaxMyDataHelper
	 */
	public static PersonalIncomeTaxMyDataHelper getInstance() {
		return Loader.instance;
	}

	@Override
	public String getMyDataId() {
		// TODO Auto-generated method stub
		return "API.syWqjr4flJ";
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
