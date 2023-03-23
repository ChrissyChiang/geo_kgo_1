package gov.kcg.kgo.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.geoentity.GeoKgoHotSearch;
import gov.kcg.kgo.geoenum.GeoHotSearchType;
import gov.kcg.kgo.georepository.GeoKgoHotSearchRepository;
import gov.kcg.kgo.viewModel.backend.hotSearch.query.rq.HotSearchChangeRq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import gov.kcg.kgo.dto.KeywordCountDto;
import gov.kcg.kgo.enums.backend.BackendFunctionCodeEnum;
import gov.kcg.kgo.enums.common.SuccessMsg;
import gov.kcg.kgo.enums.common.SysLogExeType;
import gov.kcg.kgo.enums.common.SystemTypeEnum;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.model.KgoKeywordset;
import gov.kcg.kgo.repository.KgoKeywordsRepository;
import gov.kcg.kgo.repository.KgoKeywordsetRepository;
import gov.kcg.kgo.service.HotSearchService;
import gov.kcg.kgo.service.operationmemo.OperationApiMemo;
import gov.kcg.kgo.service.operationmemo.OperationColumn;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.viewModel.backend.hotSearch.delete.rq.HotSearchDeleteRq;
import gov.kcg.kgo.viewModel.backend.hotSearch.delete.rs.HotSearchDeleteRs;
import gov.kcg.kgo.viewModel.backend.hotSearch.delete.rs.bean.HotSearchDeleteViewForm;
import gov.kcg.kgo.viewModel.backend.hotSearch.home.rs.HotSearchHomeRs;
import gov.kcg.kgo.viewModel.backend.hotSearch.home.rs.bean.HotSearchHomeViewForm;
import gov.kcg.kgo.viewModel.backend.hotSearch.home.rs.bean.PopularSearchDataGrid;
import gov.kcg.kgo.viewModel.backend.hotSearch.home.rs.bean.Top10PopularSearchDataGrid;
import gov.kcg.kgo.viewModel.backend.hotSearch.save.rq.HotSearchSaveRq;
import gov.kcg.kgo.viewModel.backend.hotSearch.save.rs.HotSearchSaveRs;
import gov.kcg.kgo.viewModel.backend.hotSearch.save.rs.bean.HotSearchSaveViewForm;
import gov.kcg.kgo.viewModel.backend.hotSearch.saveAll.rq.HotSearchSaveAllRq;
import gov.kcg.kgo.viewModel.backend.hotSearch.saveAll.rs.HotSearchSaveAllRs;
import gov.kcg.kgo.viewModel.backend.hotSearch.saveAll.rs.bean.HotSearchSaveAllViewForm;
import gov.kcg.kgo.viewModel.backend.hotSearch.saveAll.rs.bean.HotSearchSaveDataGrid;

@Transactional(rollbackFor = Exception.class)
@Service("HotSearchService")
public class HotSearchServiceImpl extends KgoBackEndServiceImpl implements HotSearchService {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(HotSearchServiceImpl.class);

	@Autowired
	private KgoKeywordsetRepository kgoKeywordsetRepository;

	@Autowired
	private KgoKeywordsRepository kgoKeywordsRepository;

	@Autowired
	private GeoKgoHotSearchRepository geoKgoHotSearchRepository;

	private final String SHOW_EDIT_DEFAULT = "1"; // 文件上前端強制傳 1

	/**
	 * 熱門搜尋-初始畫面
	 * 
	 */
	@Override
	public HotSearchHomeRs hotSearchHome() {
		HotSearchHomeViewForm viewForm = new HotSearchHomeViewForm();
		HotSearchHomeRs rs = new HotSearchHomeRs();

		try {
			List<KgoKeywordset> kgoKeywordsetList = kgoKeywordsetRepository.findOrderByOrderNumDesc();
			List<KeywordCountDto> KeywordCountDtoList = kgoKeywordsRepository.countKeyword();

			List<PopularSearchDataGrid> popularSearchGrid = null;
			List<Top10PopularSearchDataGrid> top10PopularSearchGrid = null;

			/** 設定順序表格 **/
			popularSearchGrid = kgoKeywordsetList.stream().map(l -> {
				PopularSearchDataGrid dg = new PopularSearchDataGrid();
				dg.setKeyword(l.getKeyword());
				dg.setSeq(l.getSeq());
				dg.setShowEdit(SHOW_EDIT_DEFAULT);
				return dg;
			}).collect(Collectors.toList());

			/** 每月前十名 **/
			top10PopularSearchGrid = KeywordCountDtoList.stream().map(l -> {
				Top10PopularSearchDataGrid dg = new Top10PopularSearchDataGrid();
				dg.setKeyword(l.getKeyword());
				dg.setSeq(l.getSeq());
				dg.setStatistics(l.getStatistics());
				return dg;
			}).collect(Collectors.toList());

			viewForm.setPopularSearchGrid(popularSearchGrid);
			viewForm.setTop10PopularSearchGrid(top10PopularSearchGrid);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("hotSearchHome error " + e.getMessage(), e);
		}

		return rs;
	}

	/**
	 * 熱門搜尋-熱門設定-刪除
	 * 
	 * @param rq
	 * @return
	 */
	@Override
	public HotSearchDeleteRs hotSearchDelete(HotSearchDeleteRq rq) {
		HotSearchDeleteViewForm viewForm = new HotSearchDeleteViewForm();
		HotSearchDeleteRs rs = new HotSearchDeleteRs();
		KgoApiException error = null;
		OperationApiMemo memo = null;
		String name=null;
		try {
			// 後台、刪除、熱搜
			memo = super.genOperationMemo(SystemTypeEnum.B, SysLogExeType.TYPE_D, BackendFunctionCodeEnum.HotSearchM);
			name= kgoKeywordsetRepository.findById(rq.getSeq()).get().getKeyword();
			int seq = rq.getSeq();
			kgoKeywordsetRepository.deleteById(seq);
			viewForm.setMsg(SuccessMsg.DELETE.getMsg());
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			error= apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_DELETE.getErrorMsgKey());
			error= new KgoApiException("hotSearchDelete error " + e.getMessage(), e);
		}finally {
        	/** === 儲存操作紀錄 === */
			List<OperationColumn> memoList = new ArrayList<>();
			memoList.add(new OperationColumn("熱門搜尋", name));
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
	 * 熱門搜尋-熱門設定-儲存
	 * 
	 * @param rq
	 * @return
	 */
	@Override
	public HotSearchSaveRs hotSearchSave(HotSearchSaveRq rq) {
		HotSearchSaveViewForm viewForm = new HotSearchSaveViewForm();
		HotSearchSaveRs rs = new HotSearchSaveRs();
		KgoBackEndApiError kgoBackEndApiError = KgoBackEndApiError.UNKNOWN_EXCEPTION;
		String msg = SuccessMsg.UNKNOW.getMsg();
		KgoApiException error = null;
		OperationApiMemo memo = null;

		try {
			// 後台、新增或編輯、熱搜
			SysLogExeType sysLogExeType=ObjectUtils.isEmpty(rq.getSeq()) ? SysLogExeType.TYPE_A : SysLogExeType.TYPE_U;
			memo = super.genOperationMemo(SystemTypeEnum.B, sysLogExeType, BackendFunctionCodeEnum.HotSearchM);

			Integer seq = ObjectUtils.isEmpty(rq.getSeq()) ? null : rq.getSeq();
			String keyword = rq.getKeyword();
			String loginUserId = KgoUtil.getLoginUserId();

			KgoKeywordset kgoKeywordset = null;
			if (ObjectUtils.isEmpty(seq)) {
				kgoBackEndApiError = KgoBackEndApiError.FAIL_TO_ADD;
				msg = SuccessMsg.INSERT.getMsg();

				Long count = kgoKeywordsetRepository.count();
				int orderNnum = count.intValue() + 1;
				kgoKeywordset = new KgoKeywordset();
				kgoKeywordset.setKeyword(keyword);
				kgoKeywordset.setOrderNum(orderNnum);
				kgoKeywordset.setCreateUser(loginUserId);
				kgoKeywordset.setCreateTime(DateUtil.getCurrentTimestamp());
				kgoKeywordset.setUpdateUser(loginUserId);
				kgoKeywordset.setUpdateTime(DateUtil.getCurrentTimestamp());
			} else {
				kgoBackEndApiError = KgoBackEndApiError.FAIL_TO_EDIT;
				msg = SuccessMsg.UPDATE.getMsg();

				kgoKeywordset = kgoKeywordsetRepository.getOne(seq);
				kgoKeywordset.setKeyword(keyword);
				kgoKeywordset.setUpdateTime(DateUtil.getCurrentTimestamp());
				kgoKeywordset.setUpdateUser(loginUserId);
			}
			kgoKeywordsetRepository.save(kgoKeywordset);

			viewForm.setMsg(msg);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			error= apiE;
		} catch (Exception e) {
			LOGGER.error(kgoBackEndApiError.getErrorMsgKey());
			error= new KgoApiException("hotSearchSave error " + e.getMessage(), e);
		}finally {
        	/** === 儲存操作紀錄 === */
			List<OperationColumn> memoList = new ArrayList<>();
			memoList.add(new OperationColumn("熱門搜尋", rq.getKeyword()));
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
	 * 熱門搜尋-整頁儲存
	 * 
	 * @param rq
	 * @return
	 */
	@Override
	public HotSearchSaveAllRs hotSearchSaveAll(HotSearchSaveAllRq rq) {
		HotSearchSaveAllViewForm viewForm = new HotSearchSaveAllViewForm();
		HotSearchSaveAllRs rs = new HotSearchSaveAllRs();
		KgoApiException error = null;
		OperationApiMemo memo = null;

		try {
			// 後台、編輯、熱搜排序
			memo = super.genOperationMemo(SystemTypeEnum.B, SysLogExeType.TYPE_U, BackendFunctionCodeEnum.HotSearchM);

			String loginUserId = KgoUtil.getLoginUserId();

			List<HotSearchSaveDataGrid> dgList = rq.getGrid();
			List<KgoKeywordset> kgoKeywordsetList = dgList.stream().map(l -> {
				KgoKeywordset kgoKeywordset = new KgoKeywordset();
				kgoKeywordset.setKeyword(l.getKeyword());
				kgoKeywordset.setOrderNum(l.getOrderNum());
				kgoKeywordset.setCreateUser(loginUserId);
				kgoKeywordset.setCreateTime(DateUtil.getCurrentTimestamp());
				kgoKeywordset.setUpdateUser(loginUserId);
				kgoKeywordset.setUpdateTime(DateUtil.getCurrentTimestamp());
				return kgoKeywordset;
			}).collect(Collectors.toList());
			kgoKeywordsetRepository.deleteAll();
			kgoKeywordsetRepository.saveAll(kgoKeywordsetList);
			viewForm.setMsg(SuccessMsg.SAVE.getMsg());
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			error= apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SAVE.getErrorMsgKey());
			error= new KgoApiException("hotSearchSaveAll error " + e.getMessage(), e);
		}finally {
        	/** === 儲存操作紀錄 === */
			List<OperationColumn> memoList = new ArrayList<>();
			List<String> list =new ArrayList<String>();
			String changeSeq=null;
			for (HotSearchSaveDataGrid Grid : rq.getGrid()) {
				changeSeq=String.format("{ \"keyword\" : \"%s\" ,\"OrderNum\" : %s }", Grid.getKeyword(),Grid.getOrderNum());
				list.add(changeSeq);
			}
			
			memoList.add(new OperationColumn("熱門搜尋排序",list.toString() ));
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
	 * Geo 20220729 add 介接高雄市府引擎
	 */
	@Override
	public void hotSearchChangeAction(HotSearchChangeRq rq) {
		KgoApiException error = null;
		OperationApiMemo memo = null;
		GeoKgoHotSearch geoKgoHotSearch = new GeoKgoHotSearch();
		String hotSearchType = new String();
		try {
			memo = super.genOperationMemo(SystemTypeEnum.B, SysLogExeType.TYPE_U, BackendFunctionCodeEnum.HotSearchM);
			BackendLoginUserInfo loginUser = KgoUtil.getBackendLoginUser();
			Timestamp now = new Timestamp(System.currentTimeMillis());
            if (rq.getOpenKgoSearch()==true){
				geoKgoHotSearch.setIsOpenKgo(GeoHotSearchType.KGO_HSIUNG_CITY_GOVERNMENT.getValue());
				hotSearchType = GeoHotSearchType.KGO_HSIUNG_CITY_GOVERNMENT.getLabel();
			}else {
				geoKgoHotSearch.setIsOpenKgo(GeoHotSearchType.KGO_HSIUNG_EASYGO.getValue());
				hotSearchType = GeoHotSearchType.KGO_HSIUNG_EASYGO.getLabel();
			}
			geoKgoHotSearch.setEditUser(loginUser.getName());
			geoKgoHotSearch.setEditTime(now);
			geoKgoHotSearchRepository.save(geoKgoHotSearch);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			error = apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SAVE.getErrorMsgKey());
			error = new KgoApiException("hotSearchSaveAll error " + e.getMessage(), e);
		} finally {
			/** === 儲存操作紀錄 === */
				List<OperationColumn> memoList = new ArrayList<>();
				String memoStr = String.format("切換搜尋引擎：");
				memoList.add(new OperationColumn(memoStr, hotSearchType));
				memo.setMemoList(memoList);
				super.saveOperationLog(memo);
			/** === 儲存操作紀錄 === */
			if (error != null) {
				throw error;
			}
		}
	} //hotSearchChangeAction

}
