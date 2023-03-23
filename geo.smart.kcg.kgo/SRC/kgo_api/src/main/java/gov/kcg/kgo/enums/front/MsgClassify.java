package gov.kcg.kgo.enums.front;

public enum MsgClassify {

	/** 緊急事件推播 */
	P01("01", "緊急事件推播"),
	/** 行銷推廣推播 */
	P02("02", "行銷推廣推播"),
	/** 資訊中心推播 */
	P03("03", "資訊中心推播"),
	/** 個人化推播 */
	P04("04", "個人化推播"),
	/** 交通資訊 */
	P05("05", "交通資訊"),
	/** 緊急事件推播 */
	P06("06", "申辦案件");

	/** 狀態代碼 */
	private String classify;

	/** 狀態代碼 */
	private String classifyName;

	private MsgClassify(String classify, String classifyName) {
		this.classify = classify;
		this.classifyName = classifyName;
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	public String getClassifyName() {
		return classifyName;
	}

	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}

}
