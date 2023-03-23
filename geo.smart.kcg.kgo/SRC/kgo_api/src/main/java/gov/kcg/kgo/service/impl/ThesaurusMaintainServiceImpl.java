package gov.kcg.kgo.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.kcg.kgo.enums.backend.BackendFunctionCodeEnum;
import gov.kcg.kgo.enums.common.SuccessMsg;
import gov.kcg.kgo.enums.common.SysLogExeType;
import gov.kcg.kgo.enums.common.SystemTypeEnum;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.model.KgoCommonWord;
import gov.kcg.kgo.repository.KgoCommonWordRepository;
import gov.kcg.kgo.service.ThesaurusMaintainService;
import gov.kcg.kgo.service.operationmemo.OperationApiMemo;
import gov.kcg.kgo.service.operationmemo.OperationColumn;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.viewModel.backend.thesaurusMaintain.delete.rq.ThesaurusMaintainDeleteRq;
import gov.kcg.kgo.viewModel.backend.thesaurusMaintain.delete.rs.ThesaurusMaintainDeleteRs;
import gov.kcg.kgo.viewModel.backend.thesaurusMaintain.delete.rs.bean.ThesaurusMaintainDeleteViewForm;
import gov.kcg.kgo.viewModel.backend.thesaurusMaintain.edit.rq.ThesaurusMaintainEditRq;
import gov.kcg.kgo.viewModel.backend.thesaurusMaintain.edit.rs.ThesaurusMaintainEditRs;
import gov.kcg.kgo.viewModel.backend.thesaurusMaintain.edit.rs.bean.ThesaurusMaintainEditViewForm;
import gov.kcg.kgo.viewModel.backend.thesaurusMaintain.editHome.rq.ThesaurusMaintainEditHomeRq;
import gov.kcg.kgo.viewModel.backend.thesaurusMaintain.editHome.rs.ThesaurusMaintainEditHomeRs;
import gov.kcg.kgo.viewModel.backend.thesaurusMaintain.editHome.rs.bean.ThesaurusMaintainEditHomeViewForm;
import gov.kcg.kgo.viewModel.backend.thesaurusMaintain.query.rq.ThesaurusMaintainQueryRq;
import gov.kcg.kgo.viewModel.backend.thesaurusMaintain.query.rs.ThesaurusMaintainQueryRs;
import gov.kcg.kgo.viewModel.backend.thesaurusMaintain.query.rs.bean.ThesaurusMaintainQueryDataGrid;
import gov.kcg.kgo.viewModel.backend.thesaurusMaintain.query.rs.bean.ThesaurusMaintainQueryViewForm;

@Transactional(rollbackFor = Exception.class)
@Service("ThesaurusMaintainService")
public class ThesaurusMaintainServiceImpl extends KgoBackEndServiceImpl implements ThesaurusMaintainService {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ThesaurusMaintainServiceImpl.class);

	@Autowired
	private KgoCommonWordRepository kgoCommonWordRepository;

	/**
	 * 
	 * 常見詞庫維護-初始畫面
	 */
	@Override
	public ThesaurusMaintainQueryRs thesaurusMaintainHome() {
		ThesaurusMaintainQueryRs rs = new ThesaurusMaintainQueryRs();
		ThesaurusMaintainQueryViewForm viewForm = new ThesaurusMaintainQueryViewForm();
		List<ThesaurusMaintainQueryDataGrid> gridList = new LinkedList<ThesaurusMaintainQueryDataGrid>();

		try {
			List<KgoCommonWord> kgoCommonWordList = kgoCommonWordRepository.findAll();

			kgoCommonWordList.forEach(l -> {
				ThesaurusMaintainQueryDataGrid grid = new ThesaurusMaintainQueryDataGrid();
				grid.setSeq(l.getSeq());
				grid.setTitle(l.getTitle());
				gridList.add(grid);
			});

			viewForm.setGrid(gridList);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("thesaurusMaintainHome error " + e.getMessage(), e);
		}
		return rs;
	}

	/**
	 * 
	 * 常見詞庫維護-問題查詢
	 */
	@Override
	public ThesaurusMaintainQueryRs thesaurusMaintainQuery(ThesaurusMaintainQueryRq rq) {
		ThesaurusMaintainQueryRs rs = new ThesaurusMaintainQueryRs();
		ThesaurusMaintainQueryViewForm viewForm = new ThesaurusMaintainQueryViewForm();

		List<ThesaurusMaintainQueryDataGrid> gridList = new LinkedList<ThesaurusMaintainQueryDataGrid>();

		try {
			String titile = rq.getTitle();
			List<KgoCommonWord> kgoCommonWordList = kgoCommonWordRepository.findSeqAndTitleByTitleLikeOrderByUpdateTimeDesc(titile);

			kgoCommonWordList.forEach(l -> {
				ThesaurusMaintainQueryDataGrid grid = new ThesaurusMaintainQueryDataGrid();
				grid.setSeq(l.getSeq());
				grid.setTitle(l.getTitle());
				gridList.add(grid);
			});

			viewForm.setGrid(gridList);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("thesaurusMaintainQuery error " + e.getMessage(), e);
		}
		return rs;
	}

	/**
	 * 
	 * 常見詞庫維護-問題維護(新增/修改)初始畫面
	 */
	@Override
	public ThesaurusMaintainEditHomeRs thesaurusMaintainEditHome(ThesaurusMaintainEditHomeRq rq) {
		ThesaurusMaintainEditHomeRs rs = new ThesaurusMaintainEditHomeRs();
		ThesaurusMaintainEditHomeViewForm viewForm = new ThesaurusMaintainEditHomeViewForm();

		try {
			KgoCommonWord entity = ObjectUtils.isEmpty(rq.getSeq()) ? new KgoCommonWord() : kgoCommonWordRepository.getOne(rq.getSeq());
			viewForm.setSeq(entity.getSeq());
			viewForm.setTitle(entity.getTitle());
			viewForm.setWord(entity.getWord());
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("thesaurusMaintainEditHome error " + e.getMessage(), e);
		}
		return rs;
	}

	/**
	 * 
	 * 常見詞庫維護-問題維護(新增/修改)
	 */
	@Override
	public ThesaurusMaintainEditRs thesaurusMaintainEdit(ThesaurusMaintainEditRq rq) {
		ThesaurusMaintainEditRs rs = new ThesaurusMaintainEditRs();
		ThesaurusMaintainEditViewForm viewForm = new ThesaurusMaintainEditViewForm();
		KgoBackEndApiError KgoBackEndApiError = null;

		/** 待修正.................... **/
		String createUser = KgoUtil.getTempCreateUser();
		String updateUser = KgoUtil.getTempUpdateUser();
		String executeResult = SuccessMsg.UNKNOW.getMsg();

		KgoApiException error = null;
		OperationApiMemo memo = null;

		try {
			SysLogExeType sysLogExeType = ObjectUtils.isEmpty(rq.getSeq()) ? SysLogExeType.TYPE_A : SysLogExeType.TYPE_U;
			memo = super.genOperationMemo(SystemTypeEnum.B, sysLogExeType, BackendFunctionCodeEnum.OWordSet);

			KgoCommonWord entity = null;

			if (ObjectUtils.isEmpty(rq.getSeq())) {
				/** 新增 **/
				executeResult = SuccessMsg.INSERT.getMsg();
				KgoBackEndApiError = KgoBackEndApiError.FAIL_TO_ADD;
				entity = new KgoCommonWord();
				entity.setCreateTime(DateUtil.getCurrentTimestamp());
				entity.setCreateUser(createUser);
			} else {
				/** 更新 **/
				executeResult = SuccessMsg.UPDATE.getMsg();
				KgoBackEndApiError = KgoBackEndApiError.FAIL_TO_EDIT;
				entity = kgoCommonWordRepository.getOne(rq.getSeq());
			}
			/** 共通 **/
			entity.setTitle(rq.getTitle());
			entity.setWord(rq.getWord());
			entity.setUpdateTime(DateUtil.getCurrentTimestamp());
			entity.setUpdateUser(updateUser);

			kgoCommonWordRepository.save(entity);

			viewForm.setMsg(executeResult);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			error = apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.getErrorMsgKey());
			error = new KgoApiException("thesaurusMaintainEdit error " + e.getMessage(), e);
		} finally {
			/** === 儲存操作紀錄 === */
			List<OperationColumn> memoList = new ArrayList<>();
			memoList.add(new OperationColumn("詞彙", rq.getTitle()));
			memo.setMemoList(memoList);
			super.saveOperationLog(memo);
			/** === 儲存操作紀錄 === */

			if (error != null) {
				throw error;
			}
		}
		return rs;
	}

	/**
	 * 
	 * 常見詞庫維護-問題刪除
	 */
	@Override
	public ThesaurusMaintainDeleteRs thesaurusMaintainDelete(ThesaurusMaintainDeleteRq rq) {
		ThesaurusMaintainDeleteRs rs = new ThesaurusMaintainDeleteRs();
		ThesaurusMaintainDeleteViewForm viewForm = new ThesaurusMaintainDeleteViewForm();
		KgoApiException error = null;
		OperationApiMemo memo = null;
		String title = null;
		try {
			// 後台、刪除、常見詞庫
			memo = super.genOperationMemo(SystemTypeEnum.B, SysLogExeType.TYPE_D, BackendFunctionCodeEnum.OWordSet);
			title = kgoCommonWordRepository.findById(rq.getSeq()).get().getTitle();

			kgoCommonWordRepository.deleteById(rq.getSeq());
			viewForm.setMsg(SuccessMsg.DELETE.getMsg());
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			error = apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_DELETE.getErrorMsgKey());
			error = new KgoApiException("thesaurusMaintainDelete error " + e.getMessage(), e);
		} finally {
			/** === 儲存操作紀錄 === */
			List<OperationColumn> memoList = new ArrayList<>();
			memoList.add(new OperationColumn("詞彙", title));
			memo.setMemoList(memoList);
			super.saveOperationLog(memo);
			/** === 儲存操作紀錄 === */

			if (error != null) {
				throw error;
			}
		}
		return rs;
	}

}
