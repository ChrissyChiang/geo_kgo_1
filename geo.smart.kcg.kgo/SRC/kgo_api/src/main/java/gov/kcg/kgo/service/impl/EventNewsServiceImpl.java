package gov.kcg.kgo.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.kcg.kgo.dto.EventNewsHomeDataGridDto;
import gov.kcg.kgo.enums.backend.BackendFunctionCodeEnum;
import gov.kcg.kgo.enums.backend.IsPublishEnum;
import gov.kcg.kgo.enums.backend.ReleaseToEnum;
import gov.kcg.kgo.enums.common.AttFileEnum;
import gov.kcg.kgo.enums.error.KgoFrontEndApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.model.KgoAnnounce;
import gov.kcg.kgo.model.KgoAttFile;
import gov.kcg.kgo.model.KgoTaskSet;
import gov.kcg.kgo.repository.KgoAnnounceRepository;
import gov.kcg.kgo.repository.KgoAttFileRepository;
import gov.kcg.kgo.repository.KgoCasesetRepository;
import gov.kcg.kgo.repository.KgoTaskSetRepository;
import gov.kcg.kgo.service.EventNewsService;
import gov.kcg.kgo.service.TaskMaintainService;
import gov.kcg.kgo.viewModel.backend.taskMaintain.editHome.rs.bean.TaskMaintainEditHomeFileViewForm;
import gov.kcg.kgo.viewModel.frontend.eventNews.download.rq.DownloadActionRq;
import gov.kcg.kgo.viewModel.frontend.eventNews.home.rs.EventNewsHomeRs;
import gov.kcg.kgo.viewModel.frontend.eventNews.home.rs.bean.EventNewsHomeDataGrid;
import gov.kcg.kgo.viewModel.frontend.eventNews.home.rs.bean.EventNewsHomeViewForm;
import gov.kcg.kgo.viewModel.frontend.eventNews.query.rq.EventNewsQueryRq;
import gov.kcg.kgo.viewModel.frontend.eventNews.query.rs.EventNewsQueryRs;
import gov.kcg.kgo.viewModel.frontend.eventNews.query.rs.bean.EventNewsQueryViewForm;

@Transactional(rollbackFor = Exception.class)
@Service("EventNewsService")
public class EventNewsServiceImpl extends KgoFrontEndServiceImpl implements EventNewsService {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(EventNewsServiceImpl.class);

	@Autowired
	private KgoTaskSetRepository kgoTaskSetRepository;

	@Autowired
	private KgoCasesetRepository kgoCasesetRepository;

	@Autowired
	private KgoAnnounceRepository kgoAnnounceRepository;

	@Autowired
	private KgoAttFileRepository kgoAttFileRepository;

	@Autowired
	private TaskMaintainService taskMaintainService;

	/**
	 * 活動消息-初始畫面
	 * 
	 * @param rq
	 * @return
	 */
	@Override
	public EventNewsHomeRs eventNewsHome() {
		EventNewsHomeRs rs = new EventNewsHomeRs();
		EventNewsHomeViewForm viewForm = new EventNewsHomeViewForm();
		try {

			List<EventNewsHomeDataGridDto> taskList = kgoTaskSetRepository
					.eventNewsfindTaskAndAnnounceData(IsPublishEnum.ON.getValue(), ReleaseToEnum.FRONT.getValue());

			List<EventNewsHomeDataGrid> grid = taskList.stream().map(l -> {
				EventNewsHomeDataGrid dg = new EventNewsHomeDataGrid();
				dg.setActivitySeq(l.getId().getSeq());
				dg.setActivityName(l.getName());
				dg.setActivityDate(l.getActivityDate());
				dg.setFunctionCode(l.getId().getFunCode());
				return dg;
			}).collect(Collectors.toList());

			viewForm.setGrid(grid);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoFrontEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("eventNewsHome error " + e.getMessage(), e);
		}
		return rs;
	}

	/**
	 * 活動消息-任務消息查詢
	 * 
	 * @param rq
	 * @return
	 */
	@Override
	public EventNewsQueryRs eventNewsQuery(EventNewsQueryRq rq) {
		EventNewsQueryRs rs = new EventNewsQueryRs();
		EventNewsQueryViewForm viewForm = new EventNewsQueryViewForm();
		try {
			Integer seq = rq.getActivitySeq();
			BackendFunctionCodeEnum backendFunctionCodeEnum = BackendFunctionCodeEnum
					.getBackendFunctionEnum(rq.getFunctionCode());

			if (backendFunctionCodeEnum == BackendFunctionCodeEnum.TaskM) {
				KgoTaskSet kgoTaskSet = kgoTaskSetRepository.getOne(seq);

				String caseSetName = kgoCasesetRepository.findCasesetNameByIdActivitySeq(seq);
				String activityName = kgoTaskSet.getActivityName();
				String activityDate = kgoTaskSet.getActivityDate();
				String contentHtml = kgoTaskSet.getContentHtml();

				viewForm.setActivityDate(activityDate);
				viewForm.setActivityName(activityName);
				viewForm.setCaseSetName(caseSetName);
				viewForm.setContentHtml(contentHtml);
			} else if (backendFunctionCodeEnum == BackendFunctionCodeEnum.AnnounceM) {
				Optional<KgoAnnounce> kgoAnnounce = kgoAnnounceRepository.findById(seq);
				if (kgoAnnounce.isPresent()) {
					viewForm.setActivityDate("");
					viewForm.setActivityName(kgoAnnounce.get().getName());
					viewForm.setCaseSetName("");
					viewForm.setContentHtml(kgoAnnounce.get().getContentHtml());
				}

			} else {
				throw new KgoApiException(new ErrorResult(KgoFrontEndApiError.RQ_NOT_COMPLETED));
			}

			AttFileEnum attFileEnum = AttFileEnum.getEnum(backendFunctionCodeEnum);
			List<KgoAttFile> KgoAttFiles = this.kgoAttFileRepository.findByfSeqAndTypeAndStatus(seq,
					attFileEnum.getValue(), "S");
			List<TaskMaintainEditHomeFileViewForm> files = KgoAttFiles.stream()
					.map(x -> new TaskMaintainEditHomeFileViewForm(x.getGuid(), x.getFileName()))
					.collect(Collectors.toList());
			viewForm.setFiles(files);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoFrontEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("eventNewsQuery error " + e.getMessage(), e);
		}
		return rs;
	}

	@Override
	public void downloadAction(DownloadActionRq rq) {
		taskMaintainService.taskMaintainDownload(rq);
	}

}
