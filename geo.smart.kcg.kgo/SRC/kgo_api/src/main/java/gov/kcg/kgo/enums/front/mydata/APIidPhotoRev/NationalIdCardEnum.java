package gov.kcg.kgo.enums.front.mydata.APIidPhotoRev;

import org.apache.commons.lang3.StringUtils;

public enum NationalIdCardEnum {

	統號("person_id", "responseData.personHouseholdData12"), 姓名("person_name", "responseData.personHouseholdData12"),
	出生日期("birth_yyymmdd", "responseData.personHouseholdData12"),
	出生地("birth_place_code", "responseData.personHouseholdData12"),
	配偶姓名("spouse_name", "responseData.personHouseholdData12"),
	特殊記事("special_inci_code", "responseData.personHouseholdData12"),
	父親姓名("father", "responseData.personHouseholdData12"), 母親姓名("mother", "responseData.personHouseholdData12"),
	養父姓名("foster_father", "responseData.personHouseholdData12"),
	養母姓名("foster_mother", "responseData.personHouseholdData12"),
	領補換日期("id_card_apply_yyymmdd", "responseData.personHouseholdData12"),
	領補換代碼("id_card_apply_code", "responseData.personHouseholdData12"),
	行政區域名稱("from_site_id", "responseData.personHouseholdData12.householdAddress"),
	鄉里("village", "responseData.personHouseholdData12.householdAddress"),
	鄰號("neighbor", "responseData.personHouseholdData12.householdAddress"),
	鄰("neighbor_char", "responseData.personHouseholdData12.householdAddress"),
	街道門牌號碼("street_doorplate", "responseData.personHouseholdData12.householdAddress"),
	換證機關("reissue_site_id", "responseData.personHouseholdData12"),
	相片列印方式("image_flag", "responseData.personHouseholdData12"),
	身分證影像("image_base64", "responseData.personHouseholdData12"),

	;

	private String columnName;
	/** 路由 */
	private String routing;

	/**
	 * 取得案件設定欄位型態.
	 *
	 */
	public static NationalIdCardEnum getNationalIdCardEnum(String value) {
		for (NationalIdCardEnum cEnum : values()) {
			if (StringUtils.equalsIgnoreCase(cEnum.columnName, value)) {
				return cEnum;
			}
		}
		return null;
	}

	private NationalIdCardEnum(String columnName, String routing) {
		this.routing = routing;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getRouting() {
		return routing;
	}

	public void setRouting(String routing) {
		this.routing = routing;
	}
}
