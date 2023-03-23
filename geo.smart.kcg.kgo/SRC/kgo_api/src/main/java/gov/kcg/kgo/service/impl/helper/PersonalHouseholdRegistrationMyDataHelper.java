package gov.kcg.kgo.service.impl.helper;

import java.util.List;
import java.util.Map;

import gov.kcg.kgo.service.bean.mydata.viewModel.ColumnModel;

/**
 * 個人戶籍資料
 * 
 * @author joelee
 *
 */
public class PersonalHouseholdRegistrationMyDataHelper extends KgoMydataBaseServiceHelper {

	/**
	 * Instantiates a new service helper.
	 */
	public PersonalHouseholdRegistrationMyDataHelper() {
		super();
	}

	// TODO 共通邏輯方法
	private static class Loader {
		/** The Constant instance. */
		private static final PersonalHouseholdRegistrationMyDataHelper instance = new PersonalHouseholdRegistrationMyDataHelper();
	}

	/**
	 * Gets the single instance of PersonalHouseholdRegistrationMyDataHelper.
	 *
	 * @return single instance of PersonalHouseholdRegistrationMyDataHelper
	 */
	public static PersonalHouseholdRegistrationMyDataHelper getInstance() {
		return Loader.instance;
	}

	private Object getHouseholdAddress(Map<String, Object> data) {
		return String.format("%s%s%s%s%s", data.get("from_site_id"), data.get("village"), data.get("neighbor"), data.get("neighbor_char"), data.get("street_doorplate"));
	}

	@Override
	public Object getCurrentColumnData(Map<String, Object> originalData, Map<String, Object> currentData, String columnName, ColumnModel columnModel) {
		if (columnName.equalsIgnoreCase("householdAddress")) {
			return getHouseholdAddress((Map<String, Object>) currentData.get(columnName));
		}

		if (columnName.equalsIgnoreCase("register_content")) {
			return convertList((List<String>) currentData.get(columnName));
		}

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

	@Override
	public String getMyDataId() {
		return "API.7QovE2Gev6";
	}
}
