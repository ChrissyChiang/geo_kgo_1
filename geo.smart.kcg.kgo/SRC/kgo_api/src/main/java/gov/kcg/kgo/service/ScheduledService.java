package gov.kcg.kgo.service;

/**
 * 排程作業 Service.
 */
public interface ScheduledService {

	/**
	 * 更新機關、單位資料.
	 */
	public void updateUnitData();

	/**
	 * 更新段小段
	 */
	public void updateKgoKcdTable();

	/**
	 * reTry 起案 Api
	 */
	public void reTryCallApiLog();
//	 public void test();

	/**
	 * 更新 KgoOrgan 來源 ：myaccount.kcg.gov.tw
	 */
	public void updateKgoOrganName();

	/**
	 * 更新 KgoUser 來源 ：myaccount.kcg.gov.tw
	 */
	public void updateKgoUser();

	/**
	 * 更新 KgoHoliday 來源 data.ntpc.gov.tw
	 */
	public void updateKgoHoliday();

	/**
	 * 刪除 myData附件檔案
	 */
	public void deleteKgoMyDataFile();
}
