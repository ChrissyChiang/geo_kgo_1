package gov.kcg.kgo.service;

import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireCasesetDetail;
import gov.kcg.kgo.service.bean.excel.GeoCaseHandleExcelVo;
import gov.kcg.kgo.service.bean.excel.GeoCaseRentalCaseExcelVo;
import gov.kcg.kgo.service.bean.excel.GeoQuestionExportExcelVo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface ExcelTempExportService {
	/**
	 * 軌跡紀錄 匯出報表 Excel下載
	 */
	public void exportUserLogExcel(String fileName, String sheetName, Map<String, Object> data, String tempPath);


	/**
	 * 案件軌跡紀錄 匯出報表 Excel下載
	 */
	public void exportCaseLogExcel(String fileName, String sheetName, Map<String, Object> data);

    /**
	 * GEO 20211030 add
     * 問卷結果 匯出報表 Excel下載
     */
    public void exportQuestionExcel(String fileName, String sheetName, Map<String, Object> data, LinkedHashMap<String,List<GeoKgoQuestionnaireCasesetDetail>> topicTitle, List<GeoQuestionExportExcelVo> excelVos);

	/**
	 * GEO 2021102 add
	 * 案件處理 稽核管理 Excel下載
	 */
	public void exportCaseInspectExcel(String fileName, String sheetName, Map<String, Object> data);

	/**
	 * GEO 2021105 add
	 * 案件處理 案件檢視 Excel下載
	 */
	public void exportCaseViewExcel(String fileName, String sheetName, Map<String, Object> data, List<GeoCaseHandleExcelVo> excelVos);

	/**
	 * GEO 2021115 add
	 * 案件處理 線上預約臨櫃 Excel下載
	 */
	public void exportAppointmentExcel(String fileName, String sheetName, Map<String, Object> data);

	/**
	 ** 場地案件檢視(匯出EXCEL)
	 */	
	public void exportCaseSiteExcel(String fileName, String sheetName, Map<String, Object> data , List<GeoCaseHandleExcelVo> excelVos);

	/**
	 ** 可預約時段(匯出EXCEL)
	 */
	public void exportCaseRentableExcel(String fileName, String[] sheetNames, Map<String, Object> data );

}
