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
import gov.kcg.kgo.service.FormDownloadService;
import gov.kcg.kgo.viewModel.backend.formDownload.delete.rq.FormDownloadDeleteRq;
import gov.kcg.kgo.viewModel.backend.formDownload.delete.rs.FormDownloadDeleteRs;
import gov.kcg.kgo.viewModel.backend.formDownload.download.rq.FormDownloadFileDownloadRq;
import gov.kcg.kgo.viewModel.backend.formDownload.edit.rq.FormDownloadEditRq;
import gov.kcg.kgo.viewModel.backend.formDownload.edit.rs.FormDownloadEditRs;
import gov.kcg.kgo.viewModel.backend.formDownload.home.rq.FormDownloadHomeRq;
import gov.kcg.kgo.viewModel.backend.formDownload.home.rs.FormDownloadHomeRs;
import gov.kcg.kgo.viewModel.backend.formDownload.upload.rq.FormDownloadFileUploadRq;
import gov.kcg.kgo.viewModel.backend.formDownload.upload.rs.FormDownloadFileUploadRs;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/backend/formDownload")
public class FormDownloadController extends BaseController {

	@Autowired
	FormDownloadService formDownloadService;

	/**
	 * 表單下載-初始畫面
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "表單下載-初始畫面")
	@RequestMapping(value = { "/homeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public FormDownloadHomeRs formDownloadHomeAction(@RequestBody FormDownloadHomeRq rq) {
		FormDownloadHomeRs rs = formDownloadService.formDownloadHome(rq);
		return rs;
	}

	/**
	 * 表單下載-編輯
	 * 
	 * @param rq
	 * @return
	 */
	@ApiOperation("表單下載-編輯")
	@RequestMapping(value = { "/editAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public FormDownloadEditRs formDownloadEditAction(@RequestBody FormDownloadEditRq rq) {
		FormDownloadEditRs rs = formDownloadService.formDownloadEdit(rq);
		return rs;
	}

	/**
	 * 表單下載-刪除
	 * 
	 * @param rq
	 * @return
	 */
	@ApiOperation("表單下載-刪除")
	@RequestMapping(value = { "/deleteAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public FormDownloadDeleteRs formDownloadDeleteAction(@RequestBody FormDownloadDeleteRq rq) {
		FormDownloadDeleteRs rs = formDownloadService.formDownloadDelete(rq);
		return rs;
	}

	/**
	 * 表單下載-範本上傳
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	@ApiOperation("表單下載-範本上傳")
	@RequestMapping(value = { "/uploadAction" }, method = { RequestMethod.POST }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public FormDownloadFileUploadRs formDownloadFileUploadAction(
			@RequestParam(value = "file", required = true) MultipartFile file, FormDownloadFileUploadRq rq,
			HttpServletRequest request) {
		FormDownloadFileUploadRs rs = formDownloadService.formDownloadFileUpload(rq, file);
		return rs;
	}

	/**
	 * 表單下載 - 範本下載
	 * 
	 * @param request
	 * @return
	 */
	@ApiOperation("表單下載 - 範本下載")
	@RequestMapping(value = { "/downloadAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public void formDownloadFileDownloadAction(@RequestBody FormDownloadFileDownloadRq rq) {
		formDownloadService.formDownloadAction(rq);
	}
}
