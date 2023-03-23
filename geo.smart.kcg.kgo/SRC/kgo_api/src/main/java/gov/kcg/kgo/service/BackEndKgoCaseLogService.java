package gov.kcg.kgo.service;

import java.util.List;

import gov.kcg.kgo.model.KgoCaseMain;
import gov.kcg.kgo.service.bean.excel.KgoCaseLogExportExcelVo;
import gov.kcg.kgo.viewModel.backend.kgoCaselog.exportExcel.rq.KgoCaseLogExportExcelActionRq;
import gov.kcg.kgo.viewModel.backend.kgoCaselog.query.rq.KgoCaseLogQueryActionRq;
import gov.kcg.kgo.viewModel.backend.kgoCaselog.query.rs.KgoCaseLogQueryActionRs;

/**
 * 後台 - 案件軌跡統計 Service
 *
 */
public interface BackEndKgoCaseLogService {

	/**
	 * 後台 - 案件軌跡統計 - 查詢.
	 */
	public KgoCaseLogQueryActionRs queryAction(KgoCaseLogQueryActionRq rq);
	
	
	/**
	 * 後台 - 案件軌跡統計 - 匯出報表.
	 *
	 * @param rq the rq
	 */
	public void exportExcelAction(KgoCaseLogExportExcelActionRq rq);
	
	/**
	 * 後台 - 案件軌跡統計 - 產出案件軌跡資料.
	 *
	 * @param caseList the case list
	 * @return the list
	 */
	public List<KgoCaseLogExportExcelVo> getLogDataList(List<KgoCaseMain> caseList);
//	
//	/**
//	 * 前/後台 - 軌跡紀錄 - 匯出PDF.
//	 *
//	 * @param rq the rq
//	 */
//	public void exportPdfAction(KgoLogExportPdfActionRq rq);
}
