package gov.kcg.kgo.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.kcg.kgo.dto.CaseMainCaseSetCountDto;
import gov.kcg.kgo.dto.CaseMainCaseSetIdCountDto;
import gov.kcg.kgo.dto.CaseMainCaseStatusCountDto;
import gov.kcg.kgo.dto.CaseMainCaseStatusIsLateCountDto;
import gov.kcg.kgo.dto.Activiti.HistoryDataDto;
import gov.kcg.kgo.enums.backend.CaseMainStatusEnum;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.model.KgoCaseMain;
import gov.kcg.kgo.repository.KgoCaseMainRepository;
import gov.kcg.kgo.repository.KgoCasesetRepository;
import gov.kcg.kgo.repository.custom.condition.KgoCaseMainQueryCondition;
import gov.kcg.kgo.service.ActivitiService;
import gov.kcg.kgo.service.BackEndKgoCaseLogService;
import gov.kcg.kgo.service.ExcelTempExportService;
import gov.kcg.kgo.service.bean.excel.KgoCaseLogExportExcelVo;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.MessageUtil;
import gov.kcg.kgo.viewModel.backend.kgoCaselog.exportExcel.rq.KgoCaseLogExportExcelActionRq;
import gov.kcg.kgo.viewModel.backend.kgoCaselog.query.rq.KgoCaseLogQueryActionRq;
import gov.kcg.kgo.viewModel.backend.kgoCaselog.query.rs.KgoCaseLogQueryActionRs;
import gov.kcg.kgo.viewModel.backend.kgoCaselog.query.rs.bean.CasesAccCount;
import gov.kcg.kgo.viewModel.backend.kgoCaselog.query.rs.bean.CasesFinishCount;
import gov.kcg.kgo.viewModel.backend.kgoCaselog.query.rs.bean.CasesStatusCount;
import gov.kcg.kgo.viewModel.backend.kgoCaselog.query.rs.bean.EntryCase;
import gov.kcg.kgo.viewModel.backend.kgoCaselog.query.rs.bean.EntryCaseCount;
import gov.kcg.kgo.viewModel.backend.kgoCaselog.query.rs.bean.KgoCaseLogQueryActionViewForm;

/**
 * 後台 - 案件軌跡統計 Service.
 */
@Transactional(rollbackFor = Exception.class)
@Service("BackEndKgoCaseLogService")
public class BackEndKgoCaseLogServiceImpl extends KgoBackEndServiceImpl implements BackEndKgoCaseLogService {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BackEndKgoCaseLogServiceImpl.class);

	@Autowired
	private KgoCaseMainRepository kgoCaseMainRepository;

	@Autowired
	private KgoCasesetRepository kgoCasesetRepository;

	@Autowired
	private ActivitiService activitiService;

	@Autowired
	private ExcelTempExportService excelTempExportService;

	@Autowired
	private MessageUtil messageUtil;

	/**
	 * 前/後台 - 案件軌跡紀錄 - 查詢.
	 *
	 * @param rq the rq
	 * @return the kgo log query action rs
	 */
	@Override
	public KgoCaseLogQueryActionRs queryAction(KgoCaseLogQueryActionRq rq) {
		KgoCaseLogQueryActionRs rs = new KgoCaseLogQueryActionRs();
		KgoCaseLogQueryActionViewForm viewFrom = new KgoCaseLogQueryActionViewForm();
		rs.setData(viewFrom);
		try {
			// 取得某天最小時間 00:00:00
			Timestamp startDate = DateUtil.getStartOfDay(rq.getStartDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH, DateUtil.PATTEN_FULL_TIME_SLASH);
			// 取得某天最大時間 23:59:59
			Timestamp endDate = DateUtil.getEndOfDay(rq.getEndDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH, DateUtil.PATTEN_FULL_TIME_SLASH);
			// 機關
			String organId = rq.getOrganId();

			// 1.進案時間 前兩名服務清單
			List<CaseMainCaseSetIdCountDto> top2CaseSetIdList = kgoCaseMainRepository.queryTop2CaseSetIdByDate(startDate, endDate, organId);
			List<String> caseSetIdList = top2CaseSetIdList.stream().map(c -> c.getCaseSetId()).collect(Collectors.toList());
			List<KgoCaseMain> caseList = kgoCaseMainRepository.findByCasetListAndApplyDate(caseSetIdList, startDate, endDate);

			// 服務名稱 map
			Map<String, String> caseSetNameMap = kgoCasesetRepository.findAllById(caseSetIdList).stream().collect(Collectors.toMap(s -> s.getCaseSetId(), s -> s.getCaseSetName()));

			Map<String, List<KgoCaseMain>> caseListMap = caseList.stream().collect(Collectors.groupingBy(KgoCaseMain::getCaseSetId));
			EntryCase entryCase = new EntryCase();
			int index = 1;
			for (CaseMainCaseSetIdCountDto top2Dto : top2CaseSetIdList) {
				String caseSetId = top2Dto.getCaseSetId();
				List<KgoCaseMain> caseMainList = caseListMap.get(caseSetId);

				if (ObjectUtils.isNotEmpty(caseMainList)) {
					// 組成 服務次數 map key = 日期
					Map<String, Integer> countByDateMap = new HashMap<>();
					for (KgoCaseMain main : caseMainList) {
						if (main.getApplyDate() != null) {
							String applyDate = DateUtil.dateToString(main.getApplyDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH);

							// 計算區間內 服務次數 次數
							if (countByDateMap.get(applyDate) == null) {
								countByDateMap.put(applyDate, 1);
							} else {
								Integer count = countByDateMap.get(applyDate) + 1;
								countByDateMap.put(applyDate, count);
							}
						}
					}

					List<EntryCaseCount> countList = new ArrayList<>();
					// 統計每天數量 存入lsit
					for (Map.Entry<String, Integer> entry : countByDateMap.entrySet()) {
						EntryCaseCount caseCount = new EntryCaseCount();
						String applyDate = entry.getKey();
						caseCount.setCaseSetName(caseSetNameMap.get(caseSetId));
						caseCount.setDate(applyDate);
						caseCount.setCount(entry.getValue());
						countList.add(caseCount);
					}

					// 存入top1
					if (index == 1) {
						entryCase.setEntryCaseTop1CountList(countList);
					}

					// 存入top2
					if (index == 2) {
						entryCase.setEntryCaseTop2CountList(countList);
					}

					index++;
				}
			}
			viewFrom.setEntryCase(entryCase);

			// 2. 承辦人受理案件數
			List<CaseMainCaseSetCountDto> caseSetCountList = kgoCaseMainRepository.queryCaseSetCount(startDate, endDate, organId);
			if (CollectionUtils.isNotEmpty(caseSetCountList) && caseSetCountList.size() > 10) {
				caseSetCountList = caseSetCountList.subList(0, 9);
			}
			caseSetCountList = caseSetCountList.stream().filter(c -> c.getCount() > 0).collect(Collectors.toList());
			List<CasesAccCount> casesAccCountList = new ArrayList<>();
			for (CaseMainCaseSetCountDto dto : caseSetCountList) {
				CasesAccCount casesAccCount = new CasesAccCount();
				casesAccCount.setCaseSetName(dto.getCaseSetName());
				casesAccCount.setCount(dto.getCount());
				casesAccCountList.add(casesAccCount);
			}
			viewFrom.setCasesAccCountList(casesAccCountList);

			// 3. 案件狀態統計分析
			List<CasesStatusCount> casesStatusCountList = new ArrayList<>();
			Boolean isLate = null; // 不過濾是否逾期
			List<CaseMainCaseStatusIsLateCountDto> statusCountDtoList = kgoCaseMainRepository.queryTop5CaseStatusIsLateByDate(startDate, endDate, isLate, organId);
			for (CaseMainCaseStatusIsLateCountDto dto : statusCountDtoList) {
				CasesStatusCount casesStatusCount = new CasesStatusCount();
				casesStatusCount.setCaseStatus(CaseMainStatusEnum.getCaseMainStatusEnum(dto.getStatus()).getLabel());
				casesStatusCount.setCount(dto.getCount());
				casesStatusCountList.add(casesStatusCount);
			}
			viewFrom.setCasesStatusCountList(casesStatusCountList);

			// 4.結案狀態統計分析
			List<CasesFinishCount> casesFinishCountList = new ArrayList<>();
			// 結案狀態
			List<String> statusList = Arrays.asList(CaseMainStatusEnum.F3.getValue(), CaseMainStatusEnum.F.getValue());
			List<CaseMainCaseStatusCountDto> lateStatusCountDtoList = kgoCaseMainRepository.queryTop10CaseStatusByDate(startDate, endDate, statusList, organId);
			lateStatusCountDtoList = lateStatusCountDtoList.stream().sorted(Comparator.comparing(CaseMainCaseStatusCountDto::getUpdateDate, Comparator.naturalOrder())).collect(Collectors.toList());

			for (CaseMainCaseStatusCountDto dto : lateStatusCountDtoList) {
				CasesFinishCount casesFinishCount = new CasesFinishCount();
				casesFinishCount.setFinishDate(DateUtil.dateToString(dto.getUpdateDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH));
				casesFinishCount.setCount(dto.getCount());
				casesFinishCountList.add(casesFinishCount);
			}
			viewFrom.setFinishCountList(casesFinishCountList);

		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new KgoApiException("queryAction error " + e.getMessage(), e);
		}
		return rs;
	}

	/**
	 * 後台 - 案件軌跡紀錄 - 匯出報表.
	 *
	 * @param rq the rq
	 */
	@Override
	public void exportExcelAction(KgoCaseLogExportExcelActionRq rq) {
		try {
			// 案件軌跡紀錄 - 匯出報表 excel資料處理.
			String fileName = messageUtil.getMessage("kgo.caselog.backend.excel.fileName");
			String sheetName = messageUtil.getMessage("kgo.caselog.backend.excel.sheet");

			Map<String, Object> data = new HashMap<>();
			data.put("LOG_TITLE", messageUtil.getMessage("kgo.caselog.excel.title"));
			String mergeDate = DateUtil.mergeDate(rq.getStartDate(), rq.getEndDate());
			data.put("SUB_TITLE", messageUtil.getMessage("kgo.caselog.excel.subtitle", new String[] { mergeDate }));
			// 設置表頭
			data.put("ORDER", messageUtil.getMessage("kgo.caselog.excel.header.order"));
			data.put("CASEID", messageUtil.getMessage("kgo.caselog.excel.header.caseId"));
			data.put("CASESET_NAME", messageUtil.getMessage("kgo.caselog.excel.header.caseset.name"));
			data.put("CREAT_TIME", messageUtil.getMessage("kgo.caselog.excel.header.creat.time"));
			data.put("APPLY_USER", messageUtil.getMessage("kgo.caselog.excel.header.apply.user"));
			data.put("APPLY_USER_NAME", messageUtil.getMessage("kgo.caselog.excel.header.apply.user.name"));
			data.put("STATUS", messageUtil.getMessage("kgo.caselog.excel.header.status"));
			data.put("ORGAN", messageUtil.getMessage("kgo.caselog.excel.header.organ"));
			data.put("CONTENT", messageUtil.getMessage("kgo.caselog.excel.header.content"));
			data.put("CASEOFFER", messageUtil.getMessage("kgo.caselog.excel.header.caseOffer"));
			data.put("DEALTIME", messageUtil.getMessage("kgo.caselog.excel.header.dealTime"));

			// 取得某天最小時間 00:00:00
			Timestamp startDate = DateUtil.getStartOfDay(rq.getStartDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH, DateUtil.PATTEN_FULL_TIME_SLASH);
			// 取得某天最大時間 23:59:59
			Timestamp endDate = DateUtil.getEndOfDay(rq.getEndDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH, DateUtil.PATTEN_FULL_TIME_SLASH);
			String organId = rq.getOrganId();

			KgoCaseMainQueryCondition condition = new KgoCaseMainQueryCondition();
			condition.setStartDate(startDate);
			condition.setEndDate(endDate);
			condition.setOrganId(organId);
			List<KgoCaseMain> caseList = kgoCaseMainRepository.findByCondition(condition);

			List<KgoCaseLogExportExcelVo> excelVoList = getLogDataList(caseList);
			data.put("DATA_LIST", excelVoList);

			// 軌跡紀錄 匯出報表 Excel下載
			excelTempExportService.exportCaseLogExcel(fileName, sheetName, data);

		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new KgoApiException("queryAction error " + e.getMessage(), e);
		}
	}

	/**
	 * * 後台 - 案件軌跡統計 - 產出案件軌跡資料.
	 *
	 * @param caseList the case list
	 * @return the list
	 */
	@Override
	public List<KgoCaseLogExportExcelVo> getLogDataList(List<KgoCaseMain> caseList) {
		List<String> caseSetIdList = caseList.stream().map(c -> c.getCaseSetId()).distinct().collect(Collectors.toList());
		Map<String, String> caseSetNameMap = kgoCasesetRepository.findAllById(caseSetIdList).stream().collect(Collectors.toMap(s -> s.getCaseSetId(), s -> s.getCaseSetName()));

		Map<String, List<HistoryDataDto>> hisDtoMap = new HashMap<>();
		// 取得案件歷程 歷史資料
		for (KgoCaseMain main : caseList) {
			String caseId = main.getCaseId();
			List<HistoryDataDto> hisDtoList = activitiService.getHistoryData(main.getProcessId());
			if (hisDtoMap.get(caseId) == null) {
				hisDtoMap.put(caseId, hisDtoList);
			} else {
				List<HistoryDataDto> dtoList = hisDtoMap.get(caseId);
				dtoList.addAll(hisDtoList);
				hisDtoMap.put(caseId, dtoList);
			}
		}

		int order = 1;
		List<KgoCaseLogExportExcelVo> excelVoList = new ArrayList<>();

		for (KgoCaseMain main : caseList) {
			String caseId = main.getCaseId();
			List<HistoryDataDto> hisDtoList = hisDtoMap.get(caseId);
			String caseSetName = caseSetNameMap.get(main.getCaseSetId());
			for (HistoryDataDto dto : hisDtoList) {
				KgoCaseLogExportExcelVo excelVo = new KgoCaseLogExportExcelVo(order++, main, caseSetName, dto.getStatus(), dto.getOrganName(), dto.getContent(), dto.getOfficer(), dto.getDealTime());
				excelVoList.add(excelVo);
			}
		}
		return excelVoList;
	}
}
