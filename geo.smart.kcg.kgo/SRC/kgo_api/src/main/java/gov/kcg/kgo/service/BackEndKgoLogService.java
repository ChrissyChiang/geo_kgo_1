package gov.kcg.kgo.service;

import java.util.List;

import gov.kcg.kgo.enums.common.SystemTypeEnum;
import gov.kcg.kgo.model.KgoUseLog;
import gov.kcg.kgo.service.bean.excel.KgoLogExportExcelVo;
import gov.kcg.kgo.viewModel.backend.kgoLog.exportExcel.rq.KgoLogExportExcelActionRq;
import gov.kcg.kgo.viewModel.backend.kgoLog.exportPdf.rq.KgoLogExportPdfActionRq;
import gov.kcg.kgo.viewModel.backend.kgoLog.query.rq.KgoLogQueryActionRq;
import gov.kcg.kgo.viewModel.backend.kgoLog.query.rs.KgoLogQueryActionRs;

/**
 * 後台 - 軌跡紀錄 Service
 *
 */
public interface BackEndKgoLogService {

	/**
	 * 後台 - 軌跡紀錄 - 查詢.
	 */
	public KgoLogQueryActionRs queryAction(KgoLogQueryActionRq rq);

	/**
	 * 後台 - 軌跡紀錄 - 匯出報表.
	 *
	 * @param rq the rq
	 */
	public void exportExcelAction(KgoLogExportExcelActionRq rq);
	
	/**
	 * 後台 - 軌跡紀錄 - 匯出報表資料.
	 *
	 * @param rq the rq
	 * @return the list
	 */
	public List<KgoLogExportExcelVo> getLogDataList(List<KgoUseLog> logList, SystemTypeEnum systemTypeEnum);
	
	/**
	 * 前/後台 - 軌跡紀錄 - 匯出PDF.
	 *
	 * @param rq the rq
	 */
	public void exportPdfAction(KgoLogExportPdfActionRq rq);
}
