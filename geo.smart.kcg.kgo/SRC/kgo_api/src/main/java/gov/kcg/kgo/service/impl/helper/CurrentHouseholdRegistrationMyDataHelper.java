package gov.kcg.kgo.service.impl.helper;

import java.util.List;
import java.util.Map;

import gov.kcg.kgo.service.bean.mydata.viewModel.ColumnModel;

/**
 * 現戶全戶戶籍資料
 * 
 * @author joelee
 *
 */
public class CurrentHouseholdRegistrationMyDataHelper extends KgoMydataBaseServiceHelper {

	/**
	 * Instantiates a new account management service helper.
	 */
	public CurrentHouseholdRegistrationMyDataHelper() {
		super();
	}

	// TODO 共通邏輯方法
	private static class Loader {
		/** The Constant instance. */
		private static final CurrentHouseholdRegistrationMyDataHelper instance = new CurrentHouseholdRegistrationMyDataHelper();
	}

	/**
	 * Gets the single instance of AccountManagementServiceHelper.
	 *
	 * @return single instance of AccountManagementServiceHelper
	 */
	public static CurrentHouseholdRegistrationMyDataHelper getInstance() {
		return Loader.instance;
	}

	@Override
	public String getMyDataId() {
		// TODO Auto-generated method stub
		return "API.UDauDOLyZg";
	}

	private Object getHouseholdAddress(Map<String, Object> data) {
		return String.format("%s%s%s%s%s", data.get("from_site_id"), data.get("village"), data.get("neighbor"),
				data.get("neighbor_char"), data.get("street_doorplate"));
	}

	@Override
	public Object getCurrentColumnData(Map<String, Object> originalData, Map<String, Object> currentData,
			String columnName, ColumnModel columnModel) {
		// TODO Auto-generated method stub
		if (columnName.equalsIgnoreCase("householdAddress")) {
			return getHouseholdAddress((Map<String, Object>) currentData.get(columnName));
		}

		if (columnName.equalsIgnoreCase("household_register_content")) {
			return convertList((List<String>) currentData.get(columnName));
		}

		return null;
	}

	@Override
	public String mockMyDataJsonString() {
		// TODO Auto-generated method stub
		return "{\n" + "    \"personHouseholdData21\": [\n" + "        {\n"
				+ "            \"mother_id\": \"S230515567\",\n" + "            \"spouse_id\": \"S230515585\",\n"
				+ "            \"mother_name\": \"王大媽\",\n"
				+ "            \"name_romanization\": \"OOutol\\u2027Sawmah\\u2027ʼOkak FFutol\\u2027Sawmah\\u2027ʼOkak\",\n"
				+ "            \"personal_mark\": \"現住人口\",\n" + "            \"birth_order_sex\": \"長男\",\n"
				+ "            \"birth_yyymmdd\": \"0560914\",\n" + "            \"spouse_name\": \"洪𣉥𫙸\",\n"
				+ "            \"relationship\": \"長子\",\n" + "            \"foster_father_name\": \"養父名\",\n"
				+ "            \"living_style_code\": \"無\",\n" + "            \"person_id\": \"F128205045\",\n"
				+ "            \"foster_mother_name\": \"養母名\",\n" + "            \"military_code\": \"後備除役\",\n"
				+ "            \"foster_father_id\": \"A123456789\",\n"
				+ "            \"move_in_yyymmdd\": \"0861009\",\n" + "            \"foster_parent_mark\": \"有養父母\",\n"
				+ "            \"foster_mother_id\": \"B234567890\",\n" + "            \"person_name\": \"謝永強\",\n"
				+ "            \"living_race_type\": \"排灣族\",\n" + "            \"marriage_code\": \"有偶\",\n"
				+ "            \"father_name\": \"謝大爸\",\n" + "            \"register_content\": [\n"
				+ "                \"○配偶名○○○○民國○○年○月○○日○○○○。\",\n" + "                \"民國○○年○月○日○○○○○○。\"\n"
				+ "            ],\n" + "            \"birth_place_code\": \"高雄市\",\n"
				+ "            \"father_id\": \"S132996808\",\n" + "            \"education_mark\": \"博士畢業\"\n"
				+ "        },\n" + "        {\n" + "            \"mother_id\": \"S230515567\",\n"
				+ "            \"spouse_id\": \"\",\n" + "            \"mother_name\": \"王大媽\",\n"
				+ "            \"name_romanization\": \"\",\n" + "            \"personal_mark\": \"現住人口\",\n"
				+ "            \"birth_order_sex\": \"次女\",\n" + "            \"birth_yyymmdd\": \"0610313\",\n"
				+ "            \"spouse_name\": \"\",\n" + "            \"relationship\": \"次女\",\n"
				+ "            \"foster_father_name\": \"\",\n" + "            \"living_style_code\": \"無\",\n"
				+ "            \"person_id\": \"S231436609\",\n" + "            \"foster_mother_name\": \"\",\n"
				+ "            \"military_code\": \"\",\n" + "            \"foster_father_id\": \"\",\n"
				+ "            \"move_in_yyymmdd\": \"0950530\",\n" + "            \"foster_parent_mark\": \"無養父母\",\n"
				+ "            \"foster_mother_id\": \"\",\n" + "            \"person_name\": \"謝土屋\",\n"
				+ "            \"living_race_type\": \"\",\n" + "            \"marriage_code\": \"未婚\",\n"
				+ "            \"father_name\": \"謝大爸\",\n"
				+ "            \"register_content\": \"○○○○○○民區○○○○○○○○○○○○○○○○○○戶○○○戶○民國○○年○月○日○○民國○○年○月○○日○○登記。\",\n"
				+ "            \"birth_place_code\": \"高雄市\",\n" + "            \"father_id\": \"S132996808\",\n"
				+ "            \"education_mark\": \"碩士畢業\"\n" + "        },\n" + "        {\n"
				+ "            \"mother_id\": \"S231499991\",\n" + "            \"spouse_id\": \"\",\n"
				+ "            \"mother_name\": \"湯先生\",\n" + "            \"name_romanization\": \"\",\n"
				+ "            \"personal_mark\": \"現住人口\",\n" + "            \"birth_order_sex\": \"四女\",\n"
				+ "            \"birth_yyymmdd\": \"0331109\",\n" + "            \"spouse_name\": \"\",\n"
				+ "            \"relationship\": \"戶長\",\n" + "            \"foster_father_name\": \"\",\n"
				+ "            \"living_style_code\": \"無\",\n" + "            \"person_id\": \"S230515567\",\n"
				+ "            \"foster_mother_name\": \"\",\n" + "            \"military_code\": \"\",\n"
				+ "            \"foster_father_id\": \"\",\n" + "            \"move_in_yyymmdd\": \"0650503\",\n"
				+ "            \"foster_parent_mark\": \"無養父母\",\n" + "            \"foster_mother_id\": \"\",\n"
				+ "            \"person_name\": \"湯瓜瓜\",\n" + "            \"living_race_type\": \"\",\n"
				+ "            \"marriage_code\": \"喪偶\",\n" + "            \"father_name\": \"湯先生\",\n"
				+ "            \"register_content\": \"\",\n" + "            \"birth_place_code\": \"臺灣省屏東縣\",\n"
				+ "            \"father_id\": \"S135152166\",\n" + "            \"education_mark\": \"五專畢業\"\n"
				+ "        }\n" + "    ],\n" + "    \"householdBasicData21\": {\n"
				+ "        \"household_head_name\": \"N/A\",\n" + "        \"household_head_id\": \"S230515567\",\n"
				+ "        \"household_id\": \"E1475134\",\n" + "        \"household_register_content\": [\n"
				+ "            \"○戶○○○○○○○民國○○○年○○月○日○○○○○○戶○。\",\n" + "            \"民國○○○年○月○○日○○。\"\n"
				+ "        ],\n" + "        \"householdAddress\": {\n" + "            \"neighbor_char\": \"鄰\",\n"
				+ "            \"street_doorplate\": \"○全○路○○○號\",\n" + "            \"from_site_id\": \"高雄市三民區\",\n"
				+ "            \"village\": \"德智里\",\n" + "            \"neighbor\": \"016\"\n" + "        }\n"
				+ "    }\n" + "}";
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
