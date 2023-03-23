package gov.kcg.kgo.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import gov.kcg.kgo.enums.common.SuccessMsg;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.model.KgoCasesetForm;
import gov.kcg.kgo.repository.KgoCasesetFormRepository;
import gov.kcg.kgo.service.CommonService;
import gov.kcg.kgo.service.FormDownloadService;
import gov.kcg.kgo.service.impl.helper.FormDownloadServiceHelper;
import gov.kcg.kgo.viewModel.backend.formDownload.delete.rq.FormDownloadDeleteRq;
import gov.kcg.kgo.viewModel.backend.formDownload.delete.rs.FormDownloadDeleteRs;
import gov.kcg.kgo.viewModel.backend.formDownload.delete.rs.bean.FormDownloadDeleteViewForm;
import gov.kcg.kgo.viewModel.backend.formDownload.download.rq.FormDownloadFileDownloadRq;
import gov.kcg.kgo.viewModel.backend.formDownload.edit.rq.FormDownloadEditRq;
import gov.kcg.kgo.viewModel.backend.formDownload.edit.rs.FormDownloadEditRs;
import gov.kcg.kgo.viewModel.backend.formDownload.edit.rs.bean.FormDownloadEditViewForm;
import gov.kcg.kgo.viewModel.backend.formDownload.home.rq.FormDownloadHomeRq;
import gov.kcg.kgo.viewModel.backend.formDownload.home.rs.FormDownloadHomeRs;
import gov.kcg.kgo.viewModel.backend.formDownload.home.rs.bean.FormDownloadHomeViewForm;
import gov.kcg.kgo.viewModel.backend.formDownload.home.rs.bean.FormDownloadQueryDataGrid;
import gov.kcg.kgo.viewModel.backend.formDownload.upload.rq.FormDownloadFileUploadRq;
import gov.kcg.kgo.viewModel.backend.formDownload.upload.rs.FormDownloadFileUploadRs;
import gov.kcg.kgo.viewModel.backend.formDownload.upload.rs.bean.FormDownloadFileUploadViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;

@Transactional(rollbackFor = Exception.class)
@Service("FormDownloadService")
public class FormDownloadServiceImpl extends KgoBackEndServiceImpl implements FormDownloadService {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(FormDownloadServiceImpl.class);

	@Autowired
	private KgoCasesetFormRepository kgoCasesetFormRepository;

	@Autowired
	private CommonService commonService;

	private FormDownloadServiceHelper formDownloadServiceHelper = FormDownloadServiceHelper.getInstance();

	/**
	 * 表單下載-初始畫面
	 * 
	 * @return
	 */
	@Override
	public FormDownloadHomeRs formDownloadHome(FormDownloadHomeRq rq) {
		FormDownloadHomeRs rs = new FormDownloadHomeRs();
		FormDownloadHomeViewForm viewForm = new FormDownloadHomeViewForm();
		try {
			// 分類下拉式選單
			ComboBox formTypeComboBox = formDownloadServiceHelper.getFormTypeComboBox();

			// CasesetForm data
			List<KgoCasesetForm> kgoCasesetFormList = kgoCasesetFormRepository.findByCaseSetId(rq.getCaseSetId());
			List<FormDownloadQueryDataGrid> grid = kgoCasesetFormList.stream().map(l -> {
				FormDownloadQueryDataGrid dg = new FormDownloadQueryDataGrid();
				dg.setFileName(l.getFileName());
				dg.setSeq(l.getSeq());
				dg.setTitle(l.getTitle());
				dg.setType(l.getType());
				return dg;
			}).collect(Collectors.toList());

			viewForm.setFormTypeComboBox(formTypeComboBox);
			viewForm.setGrid(grid);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("formDownloadHome error " + e.getMessage(), e);
		}
		return rs;
	}

	/**
	 * 表單下載-編輯
	 * 
	 * @param rq
	 * @return
	 */
	@Override
	public FormDownloadEditRs formDownloadEdit(FormDownloadEditRq rq) {
		FormDownloadEditRs rs = new FormDownloadEditRs();
		FormDownloadEditViewForm viewForm = new FormDownloadEditViewForm();
		KgoBackEndApiError kgoBackEndApiError = KgoBackEndApiError.UNKNOWN_EXCEPTION;
		String msg = SuccessMsg.UNKNOW.getMsg();

		try {
			String caseSetId = rq.getCaseSetId();
			Integer seq = rq.getSeq();
			String fileName = rq.getFileName();
			String title = rq.getTitle();
			String type = rq.getType();

			if (ObjectUtils.isEmpty(seq)) {
				// 新增
				kgoBackEndApiError = KgoBackEndApiError.FAIL_TO_ADD;
				msg = SuccessMsg.INSERT.getMsg();

				kgoCasesetFormRepository.saveData(caseSetId, type, title, fileName);
			} else {
				// 編輯
				kgoBackEndApiError = KgoBackEndApiError.FAIL_TO_EDIT;
				msg = SuccessMsg.UPDATE.getMsg();

				KgoCasesetForm kgoCasesetForm = kgoCasesetFormRepository.getOne(seq);

				// 有更動檔案就要把舊有的刪除掉, 需先判斷原本資料以及Request資料是否有檔名
				String oldFileName = kgoCasesetForm.getFileName();
				if (StringUtils.isNotBlank(oldFileName) && StringUtils.isNotBlank(fileName)) {
					if (!oldFileName.equalsIgnoreCase(fileName)) {
						deleteFile(commonService.getFormDownloadUploadBasePath(caseSetId), oldFileName);
					}
				}

				kgoCasesetForm.setFileName(fileName);
				kgoCasesetForm.setTitle(title);
				kgoCasesetForm.setType(type);
				kgoCasesetFormRepository.save(kgoCasesetForm);
			}

			viewForm.setMsg(msg);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(kgoBackEndApiError.getErrorMsgKey());
			throw new KgoApiException("formDownloadSave error " + e.getMessage(), e);
		}
		return rs;
	}

	/**
	 * 表單下載-刪除
	 * 
	 * @param rq
	 * @return
	 */
	@Override
	public FormDownloadDeleteRs formDownloadDelete(FormDownloadDeleteRq rq) {
		FormDownloadDeleteRs rs = new FormDownloadDeleteRs();
		FormDownloadDeleteViewForm viewForm = new FormDownloadDeleteViewForm();
		try {
			Integer seq = rq.getSeq();

			KgoCasesetForm kgoCasesetForm = kgoCasesetFormRepository.getOne(seq);
			String caseSetId = kgoCasesetForm.getCaseSetId();
			String fileName = kgoCasesetForm.getFileName();

			// 檔案刪除, 必須先判斷是否有檔案名稱才進行刪除 by Jay 20201203
			if (StringUtils.isNotBlank(fileName)) {
				LOGGER.debug("Remove File [{}]", fileName);
				deleteFile(commonService.getFormDownloadUploadBasePath(caseSetId), fileName);
			}

			// 資料刪除
			kgoCasesetFormRepository.delete(kgoCasesetForm);

			viewForm.setMsg(SuccessMsg.DELETE.getMsg());
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_DELETE.getErrorMsgKey());
			throw new KgoApiException("formDownloadDelete error " + e.getMessage(), e);
		}
		return rs;
	}

	/**
	 * 表單下載-範本上傳
	 * 
	 * @param file
	 */
	@Override
	public FormDownloadFileUploadRs formDownloadFileUpload(FormDownloadFileUploadRq rq, MultipartFile file) {
		FormDownloadFileUploadRs rs = new FormDownloadFileUploadRs();
		FormDownloadFileUploadViewForm viewForm = new FormDownloadFileUploadViewForm();
		try {
			String caseSetId = rq.getCaseSetId();
			String uploadBasePath = commonService.getFormDownloadUploadBasePath(caseSetId);
			String fileName = file.getOriginalFilename();
			commonService.uploadFileAction(file, uploadBasePath);
			viewForm.setMsg(SuccessMsg.UPLOAD.getMsg());
			viewForm.setFileName(fileName);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_UPLOAD.getErrorMsgKey());
			throw new KgoApiException("formDownloadFileUpload error " + e.getMessage(), e);
		}
		return rs;
	}

	/**
	 * 表單下載-範本下載
	 * 
	 * @param rq
	 */
	@Override
	public void formDownloadAction(FormDownloadFileDownloadRq rq) {
		try {
			Integer seq = rq.getSeq();
			String caseSetId = rq.getCaseSetId();

			KgoCasesetForm kgoCaseSetForm = kgoCasesetFormRepository.getOne(seq);

			String fileName = kgoCaseSetForm.getFileName();
			String uploadPath = commonService.getFormDownloadUploadBasePath(caseSetId);

			File file = new File(uploadPath + fileName);
			commonService.downloadFileAction(file);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_DOWNLOAD.getErrorMsgKey());
			throw new KgoApiException("downloadTempl error " + e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @param uploadPath
	 * @param fileName
	 * @throws IOException
	 */
	private void deleteFile(String uploadPath, String fileName) throws IOException {
		Path path = Paths.get(uploadPath + fileName);
		try {
			Files.deleteIfExists(path);
		} catch (Exception e) {
			File file = path.toFile();
			if (file.exists())
				file.delete();
		}
	}

}
