package gov.kcg.kgo.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.kcg.kgo.enums.backend.ApplyTypeEnum;
import gov.kcg.kgo.enums.common.SuccessMsg;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.model.KgoCasesetMemo;
import gov.kcg.kgo.repository.KgoCasesetMemoRepository;
import gov.kcg.kgo.service.CommonService;
import gov.kcg.kgo.service.CounterApplyService;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.viewModel.backend.counterApply.delete.rq.CounterApplyDeleteRq;
import gov.kcg.kgo.viewModel.backend.counterApply.delete.rs.CounterApplyDeleteRs;
import gov.kcg.kgo.viewModel.backend.counterApply.delete.rs.bean.CounterApplyDeleteViewForm;
import gov.kcg.kgo.viewModel.backend.counterApply.home.rq.CounterApplyHomeRq;
import gov.kcg.kgo.viewModel.backend.counterApply.home.rs.CounterApplyHomeRs;
import gov.kcg.kgo.viewModel.backend.counterApply.home.rs.bean.CounterApplyHomeDataGrid;
import gov.kcg.kgo.viewModel.backend.counterApply.home.rs.bean.CounterApplyHomeViewForm;
import gov.kcg.kgo.viewModel.backend.counterApply.save.rq.CounterApplySaveRq;
import gov.kcg.kgo.viewModel.backend.counterApply.save.rs.CounterApplySaveRs;
import gov.kcg.kgo.viewModel.backend.counterApply.save.rs.bean.CounterApplySaveViewForm;

@Transactional(rollbackFor = Exception.class)
@Service("CounterApplyService")
public class CounterApplyServiceImpl extends KgoBackEndServiceImpl implements CounterApplyService {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CounterApplyServiceImpl.class);

	@Autowired
	private KgoCasesetMemoRepository kgoCasesetMemoRepository;

	@Autowired
	CommonService commonService;

	/**
	 * 臨櫃申請-初始畫面
	 * 
	 */
	@Override
	public CounterApplyHomeRs counterApplyHome(CounterApplyHomeRq rq) {
		CounterApplyHomeViewForm viewForm = new CounterApplyHomeViewForm();
		CounterApplyHomeRs rs = new CounterApplyHomeRs();

		try {
			String caseSetId = rq.getCaseSetId();
			String applyType = ApplyTypeEnum.C.getValue();

			List<KgoCasesetMemo> KgoCasesetMemoList = kgoCasesetMemoRepository.findAllByCaseSetIdAndApplyType(caseSetId,
					applyType);
			List<CounterApplyHomeDataGrid> counterApplyHomeDataGridList = new LinkedList<CounterApplyHomeDataGrid>();

			KgoCasesetMemoList.forEach(l -> {
				CounterApplyHomeDataGrid grid = new CounterApplyHomeDataGrid();
				grid.setContentHtml(l.getContentHtml());
				grid.setFileName(l.getFileName());
				grid.setTitle(l.getTitle());
				counterApplyHomeDataGridList.add(grid);
			});

			viewForm.setGrid(counterApplyHomeDataGridList);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("CounterApplyHome error " + e.getMessage(), e);
		}

		return rs;
	}

	/**
	 * 臨櫃申請-申請說明資料儲存
	 */
	@Override
	public CounterApplySaveRs counterApplySave(CounterApplySaveRq rq) {
		CounterApplySaveViewForm viewForm = new CounterApplySaveViewForm();
		CounterApplySaveRs rs = new CounterApplySaveRs();
		try {
			String executeResult = SuccessMsg.SAVE.getMsg();
			String caseSetId = rq.getCaseSetId();
			List<CounterApplyHomeDataGrid> dataGrid = rq.getGrid();
			List<KgoCasesetMemo> kgoCasesetMemoList = kgoCasesetMemoRepository.findAllByCaseSetIdAndApplyType(caseSetId,
					ApplyTypeEnum.C.getValue());

			kgoCasesetMemoRepository.deleteInBatch(kgoCasesetMemoList);

			kgoCasesetMemoList.removeAll(kgoCasesetMemoList.stream()
					.filter(e -> dataGrid.stream().map(CounterApplyHomeDataGrid::getFileName)
							.anyMatch(fileName -> fileName.equalsIgnoreCase(e.getFileName())))
					.collect(Collectors.toList()));
			String filePath = KgoUtil.getAttachmentUploadBasePath(caseSetId, ApplyTypeEnum.C.getValue());
			kgoCasesetMemoList.forEach(l -> {
				String fileName = l.getFileName();
				Path path = Paths.get(filePath + fileName);
				try {
					Files.deleteIfExists(path);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});

			dataGrid.forEach(l -> {
				kgoCasesetMemoRepository.saveData(caseSetId, ApplyTypeEnum.C.getValue(), l.getTitle(),
						l.getContentHtml(), l.getFileName());
			});
			viewForm.setMsg(executeResult);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SAVE.getErrorMsgKey());
			throw new KgoApiException("counterApplySave error " + e.getMessage(), e);
		}
		return rs;
	}

	/**
	 * 臨櫃申請-申請說明資料刪除
	 */
	@Override
	public CounterApplyDeleteRs counterApplyDelete(CounterApplyDeleteRq rq) {
		CounterApplyDeleteViewForm viewForm = new CounterApplyDeleteViewForm();
		CounterApplyDeleteRs rs = new CounterApplyDeleteRs();
		try {
			String caseSetId = rq.getCaseSetId();
			String applyType = rq.getApplyType();
			Integer seq = rq.getSeq();
			String fileName = rq.getFileName();
			String attachmentUploadBasePath = commonService.getAttachmentUploadBasePath(caseSetId, applyType);

			/** delete KGO_CASESET_MEMO data **/
			kgoCasesetMemoRepository.deleteById(seq);

			/** delete file **/
			Path path = Paths.get(attachmentUploadBasePath + fileName);
			Files.deleteIfExists(path);

			viewForm.setMsg(SuccessMsg.DELETE.getMsg());
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SAVE.getErrorMsgKey());
			throw new KgoApiException("counterApplyDelete error " + e.getMessage(), e);
		}
		return rs;
	}
}
