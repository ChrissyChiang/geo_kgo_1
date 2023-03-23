package gov.kcg.kgo.service.impl.helper;

import java.util.Map;

import gov.kcg.kgo.service.bean.mydata.viewModel.ColumnModel;

/**
 * 公司負責人、董監事與經理人之公司登記資料
 * 
 * @author joelee
 *
 */
public class CompanyManagersMyDataHelper extends KgoMydataBaseServiceHelper {

	/**
	 * Instantiates a new account management service helper.
	 */
	public CompanyManagersMyDataHelper() {
		super();
	}

	// TODO 共通邏輯方法
	private static class Loader {
		/** The Constant instance. */
		private static final CompanyManagersMyDataHelper instance = new CompanyManagersMyDataHelper();
	}

	/**
	 * Gets the single instance of AccountManagementServiceHelper.
	 *
	 * @return single instance of AccountManagementServiceHelper
	 */
	public static CompanyManagersMyDataHelper getInstance() {
		return Loader.instance;
	}

	@Override
	public String getMyDataId() {
		// TODO Auto-generated method stub
		return "API.ea0klQl5Wp";
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
		return "[ {" + "\"ENAME\": \"Wang\",\n" + "\"ESTAB_APP_NO\": \"府產業商字第1080000001號\", \"AR_DATE\": null,\n"
				+ "\"REG_UNIT_NAME\": \"臺北市政府\", \"DUTY_TYPE_NAME\": \"有限\",\n" + "\"DTR_BEG_DATE\": \"1090424\",\n"
				+ "\"CMPY_NAME\": \"王小明股份有限公司\",\n" + "\"BAN_NO\": \"00000000\",\n"
				+ "\"ESTAB_APP_DATE\": \"1090424\",\n" + "\"CN\": \"中華民國\",\n" + "\"DTR_END_DATE\": \"1090524\",\n"
				+ "\"CHG_APP_NO\": \"府產業商字第1080000002號\", \"NAME\": \"王小明\",\n"
				+ "\"CMPY_ADD\": \"臺北市 X 區 XX 路 X 段 X 巷 X 號 X 樓\", \"INVEST_AMT\": 2600000,\n"
				+ "\"CMPY_STATUS\": \"核准設立\",\n" + "\"ORG_TYPE\": \"有限公司\",\n" + "\"CORP_REP\": \"N\",\n"
				+ "\"SEQ_NO\": \"0001\",\n" + "\"CMPY_REP\": \"Y\",\n" + "\"CHG_APP_DATE\": \"1090424\",\n"
				+ "\"POS_NAME\": \"董事長\"} ]";
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
