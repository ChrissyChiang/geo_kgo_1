package gov.kcg.kgo.service.impl.helper;

import java.util.Map;

import gov.kcg.kgo.service.bean.mydata.viewModel.ColumnModel;

/**
 * 低收及中低收列冊資料
 * 
 * @author joelee
 *
 */
public class LowIncomeInfoMyDataHelper extends KgoMydataBaseServiceHelper {

	/**
	 * Instantiates a new service helper.
	 */
	public LowIncomeInfoMyDataHelper() {
		super();
	}

	// TODO 共通邏輯方法
	private static class Loader {
		/** The Constant instance. */
		private static final LowIncomeInfoMyDataHelper instance = new LowIncomeInfoMyDataHelper();
	}

	/**
	 * Gets the single instance of LowIncomeInfoMyDataHelper.
	 *
	 * @return single instance of LowIncomeInfoMyDataHelper
	 */
	public static LowIncomeInfoMyDataHelper getInstance() {
		return Loader.instance;
	}

	@Override
	public String getMyDataId() {
		// TODO Auto-generated method stub
		return "API.AbAYGIjoYw";
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
		return "{\n" + "  \"addr\": \"臺中市東區富仁里26鄰練武路99號\",\n" + "  \"app_date\": \"107年01月01日\",\n"
				+ "  \"app_no\": \"BA110700178\",\n" + "  \"chk_date\": \"107年08月13日\",\n"
				+ "  \"cipher_str\": \"DdL8a03aogl98iaZipVkTqdmtkvknVHVQnZIaifHkyc==\",\n"
				+ "  \"cityname\": \"桃園市\",\n" + "  \"cname\": \"陳 XX\",\n" + "  \"doc_no_date\": \"\",\n"
				+ "  \"doc_no_s\": \"\",\n" + "  \"lic_title\": \"低收入戶\",\n" + "  \"low_type\": \"第3款\",\n"
				+ "  \"lowtypefam\": [\n" + "    {\n" + "      \"position\": \"兄\",\n" + "      \"cnameh\": \"陳低收\",\n"
				+ "      \"id_no\": \"A14****692\",\n" + "      \"birthday\": \"32/02/02\",\n"
				+ "      \"sys_subsidy\": \"107/10~107/12(低收第3款)\"\n" + "    },\n" + "    {\n"
				+ "      \"position\": \"父親\",\n" + "      \"cnameh\": \"陳 OO\",\n"
				+ "      \"id_no\": \"B10*****36\",\n" + "      \"birthday\": \"33/04/25\",\n"
				+ "      \"sys_subsidy\": \"107/10~107/12(低收第3款)\"\n" + "    },\n" + "    {\n"
				+ "      \"position\": \"三女\",\n" + "      \"cnameh\": \"小名\",\n" + "      \"id_no\": \"B12****526\",\n"
				+ "      \"birthday\": \"107/07/01\",\n" + "      \"sys_subsidy\": \"107/07~107/12(低收第3款)\"\n"
				+ "    },\n" + "    {\n" + "      \"position\": \"家屬\",\n" + "      \"cnameh\": \"test\",\n"
				+ "      \"id_no\": \"B23****213\",\n" + "      \"birthday\": \"107/01/01\",\n"
				+ "      \"sys_subsidy\": \"107/10~107/12(低收第3款)\"\n" + "    }\n" + "  ],\n"
				+ "  \"risaddr\": \"330桃園市桃園區文化里36鄰公園路21之10之20號\",\n" + "  \"townname\": \"桃園區\",\n"
				+ "  \"until_date\": \"\",\n" + "  \"villagename\": \"文化里\"\n" + "}";
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
