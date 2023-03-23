package gov.kcg.kgo.controller.backend;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.kcg.kgo.controller.base.BaseController;
import gov.kcg.kgo.service.BackEndKgoLogService;
import gov.kcg.kgo.viewModel.backend.kgoLog.exportExcel.rq.KgoLogExportExcelActionRq;
import gov.kcg.kgo.viewModel.backend.kgoLog.exportPdf.rq.KgoLogExportPdfActionRq;
import gov.kcg.kgo.viewModel.backend.kgoLog.query.rq.KgoLogQueryActionRq;
import gov.kcg.kgo.viewModel.backend.kgoLog.query.rs.KgoLogQueryActionRs;
import io.swagger.annotations.ApiOperation;

/**
 * 後台 - 軌跡紀錄 controller.
 */
@Controller
@RequestMapping("/backend/kgoLog")
public class BackEndKgoLogController extends BaseController {

	@Autowired
	BackEndKgoLogService backEndKgoLogService;

	/**
	 * 前/後台 - 軌跡紀錄 - 查詢 
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "前/後台 - 軌跡紀錄 - 查詢")
	@RequestMapping(value = { "/queryAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public KgoLogQueryActionRs queryAction(@RequestBody KgoLogQueryActionRq rq, HttpServletRequest request) {
		KgoLogQueryActionRs rs = backEndKgoLogService.queryAction(rq);
		return rs;
	}
	
	/**
	 * 前/後台 - 軌跡紀錄 - 匯出報表 
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "前/後台 - 軌跡紀錄 - 匯出報表")
	@RequestMapping(value = { "/exportExcelAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public void exportExcelAction(@RequestBody KgoLogExportExcelActionRq rq, HttpServletRequest request) {
		// 後台 - 軌跡紀錄 - 匯出報表.
		backEndKgoLogService.exportExcelAction(rq);
	}
	
	/**
	 * 前/後台 - 軌跡紀錄 - 匯出PDF
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "前/後台 - 軌跡紀錄 - 匯出PDF")
	@RequestMapping(value = { "/exportPdfAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public void exportPdfAction(@RequestBody KgoLogExportPdfActionRq rq, HttpServletRequest request) {
		// 後台 - 軌跡紀錄 - 匯出報表.
		backEndKgoLogService.exportPdfAction(rq);
	}
}
