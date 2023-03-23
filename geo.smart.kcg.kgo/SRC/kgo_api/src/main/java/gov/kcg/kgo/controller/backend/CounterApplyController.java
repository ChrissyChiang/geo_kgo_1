package gov.kcg.kgo.controller.backend;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import gov.kcg.kgo.controller.base.BaseController;
import gov.kcg.kgo.enums.backend.ApplyTypeEnum;
import gov.kcg.kgo.service.CommonService;
import gov.kcg.kgo.service.CounterApplyService;
import gov.kcg.kgo.viewModel.backend.common.fileUpload.rs.UploadFileActionRs;
import gov.kcg.kgo.viewModel.backend.counterApply.delete.rq.CounterApplyDeleteRq;
import gov.kcg.kgo.viewModel.backend.counterApply.delete.rs.CounterApplyDeleteRs;
import gov.kcg.kgo.viewModel.backend.counterApply.fileUpload.rq.CounterApplyFileUploadRq;
import gov.kcg.kgo.viewModel.backend.counterApply.home.rq.CounterApplyHomeRq;
import gov.kcg.kgo.viewModel.backend.counterApply.home.rs.CounterApplyHomeRs;
import gov.kcg.kgo.viewModel.backend.counterApply.save.rq.CounterApplySaveRq;
import gov.kcg.kgo.viewModel.backend.counterApply.save.rs.CounterApplySaveRs;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/backend/counterApply")
public class CounterApplyController extends BaseController {

	@Autowired
	CounterApplyService counterApplyService;

	@Autowired
	CommonService commonService;

	/**
	 * 臨櫃申請-初始畫面
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "臨櫃申請-初始畫面")
	@RequestMapping(value = { "/homeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public CounterApplyHomeRs counterApplyHomeAction(@RequestBody CounterApplyHomeRq rq, HttpServletRequest request) {
		CounterApplyHomeRs rs = counterApplyService.counterApplyHome(rq);
		return rs;
	}

	/**
	 * 臨櫃申請-說明附件上傳
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	@ApiOperation("臨櫃申請-說明附件上傳")
	@RequestMapping(value = { "/attachmentUploadAction" }, method = { RequestMethod.POST }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public UploadFileActionRs attachUploadAction(@RequestParam(value = "file", required = true) MultipartFile file,
			CounterApplyFileUploadRq rq, HttpServletRequest request) {
		// 取得附件檔案上傳路徑
		String attachmentUploadBasePath = commonService.getAttachmentUploadBasePath(rq.getCaseSetId(),
				ApplyTypeEnum.C.getValue());
		UploadFileActionRs rs = commonService.uploadFileAction(file, attachmentUploadBasePath);
		return rs;
	}

	/**
	 * 臨櫃申請-申請說明資料儲存
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "臨櫃申請-申請說明資料儲存")
	@RequestMapping(value = { "/saveAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public CounterApplySaveRs counterApplyQueryAction(@RequestBody CounterApplySaveRq rq, HttpServletRequest request) {
		CounterApplySaveRs rs = counterApplyService.counterApplySave(rq);
		return rs;
	}
	
	/**
	 * 臨櫃申請-申請說明資料刪除
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "臨櫃申請-申請說明資料刪除")
	@RequestMapping(value = { "/deleteAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public CounterApplyDeleteRs counterApplyDeleteAction(@RequestBody CounterApplyDeleteRq rq, HttpServletRequest request) {
		CounterApplyDeleteRs rs = counterApplyService.counterApplyDelete(rq);
		return rs;
	}

}
