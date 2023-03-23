package gov.kcg.kgo.enums.common;

/**
 * 政府單登個人資料主機ID Service enum.
 */
public enum ServiceType {

	/** iKPD */
	IKPD(1, "5ac0ad56-a153-41fc-945c-74afc251bf58", "iKPD"),

	/** 職人工事資料庫 */
	STAFF_DB(2, "336d5f82-b856-453b-a53e-654bcb1b535d", "職人工事資料庫");

	private Integer code;
	private String serviceId;
	private String description;

	private ServiceType(Integer code, String serviceId, String description) {
		this.code = code;
		this.serviceId = serviceId;
		this.description = description;
	}

	public static ServiceType getServiceType(Integer code) {
		for (ServiceType type : values()) {
			if (type.getCode() == code) {
				return type;
			}
		}
		return null;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
