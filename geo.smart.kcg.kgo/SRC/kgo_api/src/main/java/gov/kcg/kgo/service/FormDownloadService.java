package gov.kcg.kgo.service;

import org.springframework.web.multipart.MultipartFile;

import gov.kcg.kgo.viewModel.backend.formDownload.delete.rq.FormDownloadDeleteRq;
import gov.kcg.kgo.viewModel.backend.formDownload.delete.rs.FormDownloadDeleteRs;
import gov.kcg.kgo.viewModel.backend.formDownload.download.rq.FormDownloadFileDownloadRq;
import gov.kcg.kgo.viewModel.backend.formDownload.edit.rq.FormDownloadEditRq;
import gov.kcg.kgo.viewModel.backend.formDownload.edit.rs.FormDownloadEditRs;
import gov.kcg.kgo.viewModel.backend.formDownload.home.rq.FormDownloadHomeRq;
import gov.kcg.kgo.viewModel.backend.formDownload.home.rs.FormDownloadHomeRs;
import gov.kcg.kgo.viewModel.backend.formDownload.upload.rq.FormDownloadFileUploadRq;
import gov.kcg.kgo.viewModel.backend.formDownload.upload.rs.FormDownloadFileUploadRs;

public interface FormDownloadService {

	/**
	 * 表單下載-初始畫面
	 * 
	 * @return
	 */
	public FormDownloadHomeRs formDownloadHome(FormDownloadHomeRq rq);

	/**
	 * 表單下載-刪除
	 * 
	 * @param rq
	 * @return
	 */
	public FormDownloadDeleteRs formDownloadDelete(FormDownloadDeleteRq rq);

	/**
	 * 表單下載-範本下載
	 * 
	 * @param rq
	 */
	public void formDownloadAction(FormDownloadFileDownloadRq rq);

	/**
	 * 表單下載-範本上傳
	 * 
	 * @param file
	 */
	public FormDownloadFileUploadRs formDownloadFileUpload(FormDownloadFileUploadRq rq, MultipartFile file);

	/**
	 * 表單下載-編輯
	 * 
	 * @param rq
	 * @return
	 */
	public FormDownloadEditRs formDownloadEdit(FormDownloadEditRq rq);

}
