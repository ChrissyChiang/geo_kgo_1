package gov.kcg.kgo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.kcg.kgo.enums.backend.PublishStateEnum;
import gov.kcg.kgo.enums.common.SystemTypeEnum;
import gov.kcg.kgo.enums.error.KgoFrontEndApiError;
import gov.kcg.kgo.enums.front.FrontendFunctionCodeEnum;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.model.KgoCommonQuestion;
import gov.kcg.kgo.repository.KgoCommonQuestionRepository;
import gov.kcg.kgo.service.CommonQuestionService;
import gov.kcg.kgo.service.operationmemo.OperationApiMemo;
import gov.kcg.kgo.service.operationmemo.OperationColumn;
import gov.kcg.kgo.viewModel.frontend.commonQuestion.home.rs.CommonQuestionHomeRs;
import gov.kcg.kgo.viewModel.frontend.commonQuestion.home.rs.bean.CommonQuestionHomeViewForm;
import gov.kcg.kgo.viewModel.frontend.commonQuestion.home.rs.bean.CommonQuestionQueryDataGrid;

@Transactional(rollbackFor = Exception.class)
@Service("CommonQuestionService")
public class CommonQuestionServiceImpl extends KgoFrontEndServiceImpl implements CommonQuestionService {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonQuestionServiceImpl.class);

	@Autowired
	private KgoCommonQuestionRepository kgoCommonQuestionRepository;

	/**
	 * 常見問題-初始畫面
	 * 
	 * @return
	 */
	@Override
	public CommonQuestionHomeRs commonQuestionHome() {
		CommonQuestionHomeViewForm viewForm = new CommonQuestionHomeViewForm();
		CommonQuestionHomeRs rs = new CommonQuestionHomeRs();
		KgoApiException error = null;
		OperationApiMemo memo = null;
		try {
			// 前台、、QA
			memo = super.genOperationMemo(SystemTypeEnum.F, null, FrontendFunctionCodeEnum.QAndA);
			List<KgoCommonQuestion> kgoCommonQuestionList = kgoCommonQuestionRepository
					.findByStateOrderBySeqAsc(PublishStateEnum.ON.getValue());

			List<CommonQuestionQueryDataGrid> grid = kgoCommonQuestionList.stream().map(l -> {
				return new CommonQuestionQueryDataGrid(l.getSeq(), l.getQuestion(), l.getContent());
			}).collect(Collectors.toList());

			viewForm.setGrid(grid);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			error= apiE;
		} catch (Exception e) {
			LOGGER.error(KgoFrontEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			error= new KgoApiException("commonQuestionHome error " + e.getMessage(), e);
		}finally {
        	/** === 儲存操作紀錄 === */
			List<OperationColumn> memoList = new ArrayList<>();
				//	memoList.add(new OperationColumn("公務帳號", userId));
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
