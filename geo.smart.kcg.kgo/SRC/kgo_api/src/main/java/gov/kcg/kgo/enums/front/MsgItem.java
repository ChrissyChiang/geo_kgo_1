package gov.kcg.kgo.enums.front;

public enum MsgItem {

	/** 緊急事件推播 */
	P0101("01", "01", "緊急事件推播"),
	/** 行銷推廣推播 */
	P0201("02", "01", "行銷推廣推播"),
	/** 資訊中心推播 */
	P0301("03", "01", "資訊中心推播"),
	/** 個人化推播 */
	P0401("04", "01", "個人化推播"),
	/** 圖書館借書逾期通知" */
	P0402("04", "02", "圖書館借書逾期通知"),
	/** 預約書籍到館通知 */
	P0403("04", "03", "預約書籍到館通知"),
	/** 新書推薦通知 */
	P0404("04", "04", "新書推薦通知"),
	/** 交通資訊 */
	P0501("05", "01", "交通資訊"),
	/** 申請地價稅自用住宅用地稅率 */
	P0601("06", "01", "申請地價稅自用住宅用地稅率"),
	/** 申請房屋稅低收入戶減免 */
	P0602("06", "02", "申請房屋稅低收入戶減免"),
	/** 身心障礙者健保自付額核退 */
	P0603("06", "03", "身心障礙者健保自付額核退"),
	/** 身心障礙者勞保自付額核退 */
	P0604("06", "04", "身心障礙者勞保自付額核退"),
	/** 身心障礙者居家使用維生器材及必要生活輔具用電優惠 */
	P0605("06", "05", "身心障礙者居家使用維生器材及必要生活輔具用電優惠"),
	/** 高雄市市民身心障礙身分英文證明申請 */
	P0606("06", "06", "高雄市市民身心障礙身分英文證明申請"),
	/** 高雄市身心障礙者專用停車位識別證申請 */
	P0607("06", "07", "高雄市身心障礙者專用停車位識別證申請"),
	/** 身心障礙者相關補貼(租屋、購屋貸款利息差額、租停車位、購停車位貸款利息差額) */
	P0608("06", "08", "身心障礙者相關補貼(租屋、購屋貸款利息差額、租停車位、購停車位貸款利息差額)");

	/** 類別代碼 */
	private String classify;
	/** 項目代碼 */
	private String item;
	/** 項目名稱 */
	private String itemName;
	/** 項目名稱 */
	private String msgItemCode;

	private MsgItem(String classify, String item, String itemName) {
		this.classify = classify;
		this.item = item;
		this.itemName = itemName;
		this.msgItemCode = "P" + classify + item;
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getMsgItemCode() {
		return msgItemCode;
	}

	public void setMsgItemCode(String msgItemCode) {
		this.msgItemCode = msgItemCode;
	}

}
