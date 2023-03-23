package gov.kcg.kgo.enums.front;

/**
 * 訊息推播_內部API_ENUM
 * 
 * @author TPIuser
 *
 */
public enum CityApi {

	/** addUser */
	USER0001("user", "0001", "addUser"),
	/** deleteUser */
	USER0002("user", "0002", "deleteUser"),
	/** queryUserConf */
	USER0003("user", "0003", "queryUserConf"),
	/** bindDevice */
	USER0004("user", "0004", "bindDevice"),
	/** updatePushConf */
	USER0005("user", "0005", "updatePushConf"),
	/** addDevice */
	DEVICE0001("device", "0001", "addDevice"),
	/** deleteDevice */
	DEVICE0002("device", "0002", "deleteDevice"),
	/** queryDevice */
	DEVICE0003("device", "0003", "queryDevice"),
	/** queryMessage */
	MSG0001("msg", "0001", "queryMessage"),
	/** updateMsgStatus */
	MSG0002("msg", "0002", "updateMsgStatus"),
	/** pushMessage */
	PUSH0001("push", "0001", "pushMessage "),
	/** singlePushMessage */
	PUSH0002("push", "0002", "singlePushMessage"),
	/** allPushMessage */
	PUSH0003("push", "0003", "allPushMessage");

	/** 方法類別 */
	private String type;
	/** 方法代號 */
	private String number;
	/** 方法名稱 */
	private String methodName;

	private CityApi(String type, String number, String methodName) {
		this.type = type;
		this.number = number;
		this.methodName = methodName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

}
