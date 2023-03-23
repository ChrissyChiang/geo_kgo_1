package gov.kcg.kgo.dto.kgoUnit;

import java.io.Serializable;

/**
 * 市府機關單位資料DTO
 *
 */
public class KgoUnitDto implements Serializable{
	/**
	 * Sample:
	 * 
		[
			{
				"B02SORCOD": "397000000A",
				"B02UNICOD": "A000",
				"B02UNIT": "府本部"
			},
			{
				"B02SORCOD": "397007000Q",
				"B02UNICOD": "A150",
				"B02UNIT": "秘書處"
			}
		]
	 * */
	
	/** 機關代碼 */
	private String B02SORCOD;
	
	/** 單位代碼 */
	private String B02UNICOD;
	
	/** 單位名稱 */
	private String B02UNIT;

	public String getB02SORCOD() {
		return B02SORCOD;
	}

	public void setB02SORCOD(String b02sorcod) {
		B02SORCOD = b02sorcod;
	}

	public String getB02UNICOD() {
		return B02UNICOD;
	}

	public void setB02UNICOD(String b02unicod) {
		B02UNICOD = b02unicod;
	}

	public String getB02UNIT() {
		return B02UNIT;
	}

	public void setB02UNIT(String b02unit) {
		B02UNIT = b02unit;
	}

	@Override
	public String toString() {
		return "KgoUnitDto [B02SORCOD=" + B02SORCOD + ", B02UNICOD=" + B02UNICOD + ", B02UNIT=" + B02UNIT + "]";
	}
}
