package gov.kcg.kgo.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.kcg.kgo.enums.backend.IsPublishEnum;
import gov.kcg.kgo.enums.backend.ReleaseToEnum;
import gov.kcg.kgo.enums.common.AttFileEnum;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.model.KgoAnnounce;
import gov.kcg.kgo.model.KgoAttFile;
import gov.kcg.kgo.repository.KgoAnnounceRepository;
import gov.kcg.kgo.repository.KgoAttFileRepository;
import gov.kcg.kgo.service.SystemAnnounceService;
import gov.kcg.kgo.viewModel.backend.systemAnnounce.home.rs.SystemAnnounceHomeRs;
import gov.kcg.kgo.viewModel.backend.systemAnnounce.home.rs.bean.SystemAnnounceHomeDataGrid;
import gov.kcg.kgo.viewModel.backend.systemAnnounce.home.rs.bean.SystemAnnounceHomeViewForm;
import gov.kcg.kgo.viewModel.backend.taskMaintain.editHome.rs.bean.TaskMaintainEditHomeFileViewForm;

@Transactional(rollbackFor = Exception.class)
@Service("SystemAnnounceService")
public class SystemAnnounceServiceImpl extends KgoBackEndServiceImpl implements SystemAnnounceService {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SystemAnnounceServiceImpl.class);

	@Autowired
	private KgoAnnounceRepository kgoAnnounceRepository;

	@Autowired
	private KgoAttFileRepository kgoAttFileRepository;

	/**
	 * 系統公告-初始畫面
	 */
	@Override
	public SystemAnnounceHomeRs systemAnnouncetHome() {
		SystemAnnounceHomeRs rs = new SystemAnnounceHomeRs();
		SystemAnnounceHomeViewForm viewForm = new SystemAnnounceHomeViewForm();

		try {

			List<SystemAnnounceHomeDataGrid> gridList = new LinkedList<SystemAnnounceHomeDataGrid>();
			List<KgoAnnounce> announceList = kgoAnnounceRepository.findAllByIsPublishAndReleaseToOrderByPublishTimeDesc(
					IsPublishEnum.ON.getValue(), ReleaseToEnum.BACK.getValue());

			announceList.forEach(l -> {
				SystemAnnounceHomeDataGrid grid = new SystemAnnounceHomeDataGrid();
				grid.setSeq(l.getSeq());
				grid.setName(l.getName());
				grid.setContent(l.getContentHtml());

				/** 附件 */
				List<KgoAttFile> KgoAttFiles = this.kgoAttFileRepository.findByfSeqAndTypeAndStatus(l.getSeq(),
						AttFileEnum.AnnounceM.getValue(), "S");
				List<TaskMaintainEditHomeFileViewForm> files = KgoAttFiles.stream()
						.map(x -> new TaskMaintainEditHomeFileViewForm(x.getGuid(), x.getFileName()))
						.collect(Collectors.toList());
				grid.setFiles(files);
				gridList.add(grid);
			});

			viewForm.setGrid(gridList);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("systemAnnouncetHome error " + e.getMessage(), e);
		}
		return rs;
	}

}
