package gov.kcg.kgo.service.impl;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.kcg.kgo.enums.backend.PublishStateEnum;
import gov.kcg.kgo.enums.common.SuccessMsg;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.model.KgoCommonQuestion;
import gov.kcg.kgo.repository.KgoCommonQuestionRepository;
import gov.kcg.kgo.service.QuestionMaintainService;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.viewModel.backend.questMaintain.delete.rq.QuestionMaintainDeleteRq;
import gov.kcg.kgo.viewModel.backend.questMaintain.delete.rs.QuestionMaintainDeleteRs;
import gov.kcg.kgo.viewModel.backend.questMaintain.delete.rs.bean.QuestionMaintainDeleteViewForm;
import gov.kcg.kgo.viewModel.backend.questMaintain.edit.rq.QuestionMaintainEditRq;
import gov.kcg.kgo.viewModel.backend.questMaintain.edit.rs.QuestionMaintainEditRs;
import gov.kcg.kgo.viewModel.backend.questMaintain.edit.rs.bean.QuestionMaintainEditViewForm;
import gov.kcg.kgo.viewModel.backend.questMaintain.editHome.rq.QuestionMaintainEditHomeRq;
import gov.kcg.kgo.viewModel.backend.questMaintain.editHome.rs.QuestionMaintainEditHomeRs;
import gov.kcg.kgo.viewModel.backend.questMaintain.editHome.rs.bean.QuestionMaintainEditHomeViewForm;
import gov.kcg.kgo.viewModel.backend.questMaintain.onOff.rq.QuestionMaintainOnOffRq;
import gov.kcg.kgo.viewModel.backend.questMaintain.onOff.rs.QuestionMaintainOnOffRs;
import gov.kcg.kgo.viewModel.backend.questMaintain.onOff.rs.bean.QuestionMaintainOnOffViewForm;
import gov.kcg.kgo.viewModel.backend.questMaintain.query.rq.QuestionMaintainQueryRq;
import gov.kcg.kgo.viewModel.backend.questMaintain.query.rs.QuestionMaintainQueryRs;
import gov.kcg.kgo.viewModel.backend.questMaintain.query.rs.bean.QuestionMaintainQueryDataGrid;
import gov.kcg.kgo.viewModel.backend.questMaintain.query.rs.bean.QuestionMaintainQueryViewForm;

@Transactional(rollbackFor = Exception.class)
@Service("QuestionMaintainService")
public class QuestionMaintainServiceImpl extends KgoBackEndServiceImpl implements QuestionMaintainService {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(QuestionMaintainServiceImpl.class);

	@Autowired
	private KgoCommonQuestionRepository kgoCommonQuestionRepository;

	/**
	 * 
	 * 常見問題維護-初始畫面
	 */
	@Override
	public QuestionMaintainQueryRs questionMaintainHome() {
		QuestionMaintainQueryRs rs = new QuestionMaintainQueryRs();
		QuestionMaintainQueryViewForm viewForm = new QuestionMaintainQueryViewForm();
		try {
			viewForm.setGrid(queryQuestion(null, null, null));
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("questionMaintainHome error " + e.getMessage(), e);
		}
		return rs;
	}

	/**
	 * 
	 * 常見問題維護-問題查詢
	 */
	@Override
	public QuestionMaintainQueryRs questionMaintainQuery(QuestionMaintainQueryRq rq) {
		QuestionMaintainQueryRs rs = new QuestionMaintainQueryRs();
		QuestionMaintainQueryViewForm viewForm = new QuestionMaintainQueryViewForm();

		try {
			String question = rq.getQuestion();
			String publishTimeStart = null;
			String publishTimeEnd = null;

			if (ObjectUtils.isNotEmpty(rq.getPublishDate())) {
				if (rq.getPublishDate().length == 1) {
					publishTimeStart = publishTimeEnd = rq.getPublishDate()[0];
				} else if (rq.getPublishDate().length == 2) {
					publishTimeStart = rq.getPublishDate()[0];
					publishTimeEnd = rq.getPublishDate()[1];
				}
			}

			viewForm.setGrid(queryQuestion(question, publishTimeStart, publishTimeEnd));
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("questionMaintainQuery error " + e.getMessage(), e);
		}
		return rs;
	}

	/**
	 * 
	 * 常見問題維護-問題維護(新增/修改)初始畫面
	 */
	@Override
	public QuestionMaintainEditHomeRs questionMaintainEditHome(QuestionMaintainEditHomeRq rq) {
		QuestionMaintainEditHomeRs rs = new QuestionMaintainEditHomeRs();
		QuestionMaintainEditHomeViewForm viewForm = new QuestionMaintainEditHomeViewForm();

		try {
			Integer seq = rq.getSeq();
			String question = StringUtils.EMPTY;
			String content = StringUtils.EMPTY;

			if (!ObjectUtils.isEmpty(seq)) {
				KgoCommonQuestion entity = kgoCommonQuestionRepository.getOne(seq);

				seq = entity.getSeq();
				question = entity.getQuestion();
				content = entity.getContent();
			}
			viewForm.setSeq(seq);
			viewForm.setQestion(question);
			viewForm.setContent(content);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("questionMaintainEditHome error " + e.getMessage(), e);
		}
		return rs;
	}

	/**
	 * 
	 * 常見問題維護-問題維護(新增/修改)
	 */
	@Override
	public QuestionMaintainEditRs questionMaintainEdit(QuestionMaintainEditRq rq) {
		QuestionMaintainEditRs rs = new QuestionMaintainEditRs();
		QuestionMaintainEditViewForm viewForm = new QuestionMaintainEditViewForm();
		KgoBackEndApiError kgoBackEndApiError = KgoBackEndApiError.UNKNOWN_EXCEPTION;
		boolean flag = false;

		/** 待修正.................... **/
		String createUser = KgoUtil.getTempCreateUser();
		String updateUser = KgoUtil.getTempUpdateUser();
		String executeResult = SuccessMsg.UNKNOW.getMsg();
		try {
			KgoCommonQuestion entity = null;

			if (ObjectUtils.isEmpty(rq.getSeq())) {
				/** 新增 **/
				executeResult = SuccessMsg.INSERT.getMsg();
				kgoBackEndApiError = KgoBackEndApiError.FAIL_TO_ADD;
				entity = new KgoCommonQuestion();
				entity.setState(PublishStateEnum.OFF.getValue());
				entity.setPublishDate(null);
				entity.setCreateTime(DateUtil.getCurrentTimestamp());
				entity.setCreateUser(createUser);
			} else {
				/** 更新 **/
				executeResult = SuccessMsg.UPDATE.getMsg();
				kgoBackEndApiError = KgoBackEndApiError.FAIL_TO_EDIT;
				entity = kgoCommonQuestionRepository.getOne(rq.getSeq());
				if (StringUtils.isNotBlank(rq.getState())) {
					/** 上下架 **/
					entity.setState(rq.getState());
					entity.setPublishDate(DateUtil.getCurrentTimestamp());
				}
			}
			/** 共通 **/
			entity.setQuestion(rq.getQuestion());
			entity.setContent(rq.getContent());
			entity.setUpdateTime(DateUtil.getCurrentTimestamp());
			entity.setUpdateUser(updateUser);

			kgoCommonQuestionRepository.save(entity);

			flag = true;
			viewForm.setMsg(executeResult);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(kgoBackEndApiError.getErrorMsgKey());
			throw new KgoApiException("questionMaintainEdit error " + e.getMessage(), e);
		}
		return rs;
	}

	/**
	 * 
	 * 常見問題維護-問題上下架
	 */
	@Override
	public QuestionMaintainOnOffRs questionMaintainOnOff(QuestionMaintainOnOffRq rq) {
		QuestionMaintainOnOffRs rs = new QuestionMaintainOnOffRs();
		QuestionMaintainOnOffViewForm viewForm = new QuestionMaintainOnOffViewForm();

		boolean flag = false;

		/** 待修正.................... **/
		String createUser = KgoUtil.getTempCreateUser();
		String updateUser = KgoUtil.getTempUpdateUser();
		int seq = rq.getSeq();
		String state = rq.getState();
		try {
			KgoCommonQuestion entity = kgoCommonQuestionRepository.getOne(seq);
			entity.setState(state);
			entity.setPublishDate(DateUtil.getCurrentTimestamp());
			entity.setUpdateTime(DateUtil.getCurrentTimestamp());
			entity.setUpdateUser(updateUser);
			kgoCommonQuestionRepository.save(entity);

			viewForm.setMsg(PublishStateEnum.getPublishStateEnum(state).getLabel() + SuccessMsg.UPDATE.getMsg());
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SAVE.getErrorMsgKey());
			throw new KgoApiException("questionMaintainOnOff error " + e.getMessage(), e);
		}
		return rs;
	}

	/**
	 * 
	 * 常見問題維護-問題刪除
	 */
	@Override
	public QuestionMaintainDeleteRs questionMaintainDelete(QuestionMaintainDeleteRq rq) {
		QuestionMaintainDeleteRs rs = new QuestionMaintainDeleteRs();
		QuestionMaintainDeleteViewForm viewForm = new QuestionMaintainDeleteViewForm();
		try {
			kgoCommonQuestionRepository.deleteById(rq.getSeq());
			viewForm.setMsg(SuccessMsg.DELETE.getMsg());
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_DELETE.getErrorMsgKey());
			throw new KgoApiException("questionMaintainDelete error " + e.getMessage(), e);
		}
		return rs;
	}

	/**
	 * Query KGO_COMMON_QUESTION data By conditions
	 * (question/publishTimeStart/publishTimeEnd)
	 * 
	 * @param question
	 * @param publishTimeStart
	 * @param publishTimeEnd
	 * @return
	 */
	private List<QuestionMaintainQueryDataGrid> queryQuestion(String question, String publishTimeStart,
			String publishTimeEnd) {
		List<QuestionMaintainQueryDataGrid> gridList = new LinkedList<QuestionMaintainQueryDataGrid>();
		List<KgoCommonQuestion> list = kgoCommonQuestionRepository.findByQuestionAndPublishTimeOrderBySeq(question,
				publishTimeStart, publishTimeEnd);
		try {
			list.forEach(l -> {
				QuestionMaintainQueryDataGrid grid = new QuestionMaintainQueryDataGrid();
				if(!ObjectUtils.isEmpty(l.getPublishDate())){
					grid.setPublishTime(DateUtil.dateToString(new Date(l.getPublishDate().getTime()),
							DateUtil.PATTEN_YEAR_MONTH_DAY_NO_HYPHEN));
				}
				grid.setQuestion(l.getQuestion());
				grid.setSeq(l.getSeq());
				grid.setState(StringUtils.equalsIgnoreCase(PublishStateEnum.ON.getValue(), l.getState())
						? PublishStateEnum.ON.getLabel()
						: PublishStateEnum.OFF.getLabel());
				gridList.add(grid);
			});
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("queryQuestion error " + e.getMessage(), e);
		}
		return gridList;
	}
}
