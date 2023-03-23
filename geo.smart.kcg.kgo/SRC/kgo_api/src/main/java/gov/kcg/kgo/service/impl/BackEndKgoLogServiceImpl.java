package gov.kcg.kgo.service.impl;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.kcg.kgo.dto.CaseMainCaseSetCountDto;
import gov.kcg.kgo.dto.KgoUseLogFunctionCodeCountDto;
import gov.kcg.kgo.enums.backend.BackendFunctionCodeEnum;
import gov.kcg.kgo.enums.common.SysType;
import gov.kcg.kgo.enums.common.SystemTypeEnum;
import gov.kcg.kgo.enums.common.YesNoFlag;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.enums.front.FrontendFunctionCodeEnum;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.model.KgoCasesetColumnChildTemplate;
import gov.kcg.kgo.model.KgoUseLog;
import gov.kcg.kgo.model.KgoUser;
import gov.kcg.kgo.repository.KgoCaseMainRepository;
import gov.kcg.kgo.repository.KgoUserLogRepository;
import gov.kcg.kgo.repository.KgoUserRepository;
import gov.kcg.kgo.service.BackEndKgoLogService;
import gov.kcg.kgo.service.ExcelTempExportService;
import gov.kcg.kgo.service.bean.excel.KgoLogExportExcelVo;
import gov.kcg.kgo.service.impl.helper.BackEndKgoLogServiceHelper;
import gov.kcg.kgo.util.CryptUtil;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.util.MessageUtil;
import gov.kcg.kgo.viewModel.backend.kgoLog.exportExcel.rq.KgoLogExportExcelActionRq;
import gov.kcg.kgo.viewModel.backend.kgoLog.exportPdf.rq.KgoLogExportPdfActionRq;
import gov.kcg.kgo.viewModel.backend.kgoLog.exportPdf.rs.bean.PdfBean;
import gov.kcg.kgo.viewModel.backend.kgoLog.query.rq.KgoLogQueryActionRq;
import gov.kcg.kgo.viewModel.backend.kgoLog.query.rs.KgoLogQueryActionRs;
import gov.kcg.kgo.viewModel.backend.kgoLog.query.rs.bean.KgoLogQueryActionViewForm;
import gov.kcg.kgo.viewModel.backend.kgoLog.query.rs.bean.LogFuncOrServiceCount;
import gov.kcg.kgo.viewModel.backend.kgoLog.query.rs.bean.LogLoginTypeCount;
import gov.kcg.kgo.viewModel.backend.kgoLog.query.rs.bean.LogLoninIpCount;
import gov.kcg.kgo.viewModel.backend.kgoLog.query.rs.bean.LoginLogoutData;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

/**
 * 後台 - 軌跡紀錄 Service.
 */
@Transactional(rollbackFor = Exception.class)
@Service("BackEndKgoLogService")
public class BackEndKgoLogServiceImpl extends KgoBackEndServiceImpl implements BackEndKgoLogService {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BackEndKgoLogServiceImpl.class);

	@Autowired
	private KgoUserLogRepository kgoUserLogRepository;
	
	@Autowired
	private KgoUserRepository kgoUserRepository;
	
	@Autowired
	private ExcelTempExportService excelTempExportService;
	
	@Autowired
	private KgoCaseMainRepository kgoCaseMainRepository;
	
	@Autowired
	private HttpServletResponse response;
	
	@Autowired
	private MessageUtil messageUtil;
	
	/** helper. */
	private BackEndKgoLogServiceHelper helper = BackEndKgoLogServiceHelper.getInstance();

	/**
	 * 前/後台 - 軌跡紀錄 - 查詢.
	 *
	 * @param rq the rq
	 * @return the kgo log query action rs
	 */
	@Override
	public KgoLogQueryActionRs queryAction(KgoLogQueryActionRq rq) {
		KgoLogQueryActionRs rs = new KgoLogQueryActionRs();
		KgoLogQueryActionViewForm viewFrom = new KgoLogQueryActionViewForm();
		rs.setData(viewFrom);
		try {
			// 取得某天最小時間  00:00:00
			Timestamp startDate = DateUtil.getStartOfDay(rq.getStartDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH, DateUtil.PATTEN_FULL_TIME_SLASH);
			// 取得某天最大時間 23:59:59
			Timestamp endDate = DateUtil.getEndOfDay(rq.getEndDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH, DateUtil.PATTEN_FULL_TIME_SLASH);

			// 前/後台
			SystemTypeEnum systemTypeEnum = SystemTypeEnum.getSystemTypeEnum(rq.getSystemType());
			// 系統別錯誤
			if(systemTypeEnum == null) {
				throw new KgoApiException(new ErrorResult(KgoBackEndApiError.SYSTEM_TYPE_ERROR));
			}
			// 前/後台
			String systemType = systemTypeEnum.getSystemType();
			// function = Login
			String loginFuncCode = SystemTypeEnum.B.equals(systemTypeEnum) ? BackendFunctionCodeEnum.Login.getFunctionCode() : FrontendFunctionCodeEnum.Login.getFunctionCode();
			
			// 1. 取得 前/後台 - 身分證驗證統計清單.
			List<LogLoginTypeCount> loginTypeCountList = helper.getLoginTypeCountDtoList(systemType, loginFuncCode, startDate, endDate);
			viewFrom.setLoginTypeCountList(loginTypeCountList);
			
			// 2. 取得 前/後台 - 登入/登出時間統計物件.
			LoginLogoutData loginLogoutData = helper.getLoginLogoutData(systemType, startDate, endDate);
			viewFrom.setLoginLogoutData(loginLogoutData);
			
			// 3. 取得前/後台 - 承辦前10筆登入IP清單.
			List<LogLoninIpCount>  top10IpCountList = helper.getTop10IpCountList(systemType, loginFuncCode, startDate, endDate);
			viewFrom.setTop10IpCountList(top10IpCountList);
			
			// 4. 取得前/後台 - 前10筆 功能使用分析清單.
			// 後臺
			if (SystemTypeEnum.B.equals(systemTypeEnum)) {
				List<LogFuncOrServiceCount> top10FunctionCountList = helper.getTop10FunctionCountList(systemType, startDate, endDate);
				viewFrom.setTop10FuncOrServiceCountList(top10FunctionCountList);				
			}
			// 前臺 2020.12.11 調整 前臺改申辦服務前10名
			if (SystemTypeEnum.F.equals(systemTypeEnum)) {
				// TODO　軌跡紀錄內容待確認 調整
				String organId = StringUtils.EMPTY;
				List<CaseMainCaseSetCountDto> caseSetCountList  = kgoCaseMainRepository.queryCaseSetCount(startDate, endDate, organId);
				if(CollectionUtils.isNotEmpty(caseSetCountList) && caseSetCountList.size() > 10) {
					caseSetCountList = caseSetCountList.subList(0, 9);
				}
				List<LogFuncOrServiceCount> list = new ArrayList<>();
				for(CaseMainCaseSetCountDto dto : caseSetCountList) {
					LogFuncOrServiceCount vo = new LogFuncOrServiceCount(dto);
					list.add(vo);
				}
				viewFrom.setTop10FuncOrServiceCountList(list);
			}
		
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
	 * 後台 - 軌跡紀錄 - 匯出報表.
	 *
	 * @param rq the rq
	 */
	@Override
	public void exportExcelAction(KgoLogExportExcelActionRq rq) {
		try {
			// 前/後台
			SystemTypeEnum systemTypeEnum = SystemTypeEnum.getSystemTypeEnum(rq.getSystemType());
			// 系統別錯誤
			if(systemTypeEnum == null) {
				throw new KgoApiException(new ErrorResult(KgoBackEndApiError.SYSTEM_TYPE_ERROR));
			}
			
			String fileName =  StringUtils.EMPTY;
			String sheetName = StringUtils.EMPTY;
			String tempPath = StringUtils.EMPTY;
			
			// 軌跡紀錄 - 匯出報表  excel資料處理.
			Map<String, Object> data = new HashMap<>();
			// 後台
			if(SystemTypeEnum.B.equals(systemTypeEnum)) {
				fileName = messageUtil.getMessage("kgo.log.backend.excel.fileName");
				sheetName = messageUtil.getMessage("kgo.log.backend.excel.sheet");
				data.put("LOG_TITLE", messageUtil.getMessage("kgo.log.backend.excel.title"));
				
				// 機關、單位、姓名
				data.put("PAR_ORG", messageUtil.getMessage("kgo.log.excel.backend.header.parOrg"));
				data.put("ORG", messageUtil.getMessage("kgo.log.excel.backend.header.org"));
				data.put("NAME", messageUtil.getMessage("kgo.log.excel.backend.header.name"));
				data.put("MEMO", messageUtil.getMessage("kgo.log.excel.backend.header.memo"));
				data.put("LOGIN_TYPE", messageUtil.getMessage("kgo.log.excel.header.loginType"));
				tempPath = "/templates/kgo/excel/kgoUserLog/後台_使用軌跡紀錄_temp.xlsx";				
			}
			// 前台
			if(SystemTypeEnum.F.equals(systemTypeEnum)) {
				fileName = messageUtil.getMessage("kgo.log.frontend.excel.fileName");
				sheetName = messageUtil.getMessage("kgo.log.frontend.excel.sheet");
				data.put("LOG_TITLE", messageUtil.getMessage("kgo.log.frontend.excel.title"));
				data.put("LOGIN_TYPE", messageUtil.getMessage("kgo.log.excel.header.loginType"));
				data.put("SERVICE_STATUS", messageUtil.getMessage("kgo.log.excel.frontend.header.serviceAndStatus"));
				
				tempPath = "/templates/kgo/excel/kgoUserLog/前台_使用軌跡紀錄_temp.xlsx";
			}
			String mergeDate = DateUtil.mergeDate(rq.getStartDate(), rq.getEndDate());
			data.put("SUB_TITLE", messageUtil.getMessage("kgo.log.excel.subtitle", new String[] {mergeDate}));
			
			// 設置表頭
			data.put("ORDER", messageUtil.getMessage("kgo.log.excel.header.order"));
			data.put("USER_INFO", messageUtil.getMessage("kgo.log.excel.frontend.header.userInfo"));
			data.put("DATE_TIME", messageUtil.getMessage("kgo.log.excel.header.datetime"));
			data.put("FUNC_NAME", messageUtil.getMessage("kgo.log.excel.header.funcName"));
			data.put("ID_VER", messageUtil.getMessage("kgo.log.excel.header.idVer"));
			data.put("IP", messageUtil.getMessage("kgo.log.excel.header.ip"));
	
			// 取得某天最小時間  00:00:00
			Timestamp startDate = DateUtil.getStartOfDay(rq.getStartDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH, DateUtil.PATTEN_FULL_TIME_SLASH);
			// 取得某天最大時間 23:59:59
			Timestamp endDate = DateUtil.getEndOfDay(rq.getEndDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH, DateUtil.PATTEN_FULL_TIME_SLASH);

			/** ==== 報表SQL === */
			List<KgoUseLog> logList = new ArrayList<>();
			// 後台 查詢 過濾機關
			if(SystemTypeEnum.B.equals(systemTypeEnum)) {
				String organId = rq.getOrganId();
				List<KgoUser> kgoUserList = kgoUserRepository.findByOrgan(organId);
				Map<String, List<KgoUser>> kgoUserMap = kgoUserList.stream().collect(Collectors.groupingBy(KgoUser::getOrgan));
				List<KgoUser> organUserList = kgoUserMap.get(organId);
				if(CollectionUtils.isNotEmpty(organUserList)) {
					List<String> organUserIdList = organUserList.stream().map(u -> u.getUserId()).collect(Collectors.toList());
					logList = kgoUserLogRepository.findLogbySystemTypeAndCreateTimeAndCreateUsers(systemTypeEnum.getSystemType(), startDate, endDate, organUserIdList);
				}
			} 
			
			// 前台
			if(SystemTypeEnum.F.equals(systemTypeEnum)) {
				// 依系統別、新增時間查詢
				logList = kgoUserLogRepository.findLogbySystemTypeAndCreateTime(systemTypeEnum.getSystemType(), startDate, endDate);
			}
			/** ==== 報表SQL === */

			// 後台 - 軌跡紀錄 - 匯出報表資料.
			List<KgoLogExportExcelVo> dataList = getLogDataList(logList, systemTypeEnum);
			data.put("DATA_LIST", dataList);
			
			// 軌跡紀錄 匯出報表 Excel下載
			excelTempExportService.exportUserLogExcel(fileName, sheetName, data, tempPath);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new KgoApiException("queryAction error " + e.getMessage(), e);
		}
	}
	

	/**
	 * 後台 - 軌跡紀錄 - 匯出報表資料.
	 *
	 * @param logList the log list
	 * @param systemTypeEnum the system type enum
	 * @return the list
	 */
	@Override
	public List<KgoLogExportExcelVo> getLogDataList(List<KgoUseLog> logList, SystemTypeEnum systemTypeEnum) {
		// 軌跡紀錄資料.
		List<KgoLogExportExcelVo> dataList = new ArrayList<>();

		Map<String, KgoUser> kgoUserMap = new HashMap<>();
		// 後台取得人員資料map
		if(SystemTypeEnum.B.equals(systemTypeEnum)) {
			List<String> createUserList = logList.stream().map(l -> l.getCreateUser()).distinct().collect(Collectors.toList());
			kgoUserMap = kgoUserRepository.findAllById(createUserList).stream().collect(Collectors.toMap(u -> u.getUserId(), u -> u));
		}
		
		// 前臺 過濾動作為空 (申辦服務及狀態 -> 有action)
		if(SystemTypeEnum.F.equals(systemTypeEnum)) {
//			logList = logList.stream().filter(l -> StringUtils.isNotBlank(l.getAction())).collect(Collectors.toList());
		}
		
		int order = 1;
		for(KgoUseLog log : logList) {
			String funcCodeI18n = StringUtils.EMPTY;
			String functionCode = log.getFunctionCode();
			if(StringUtils.isNotBlank(functionCode)) {
				if (SystemTypeEnum.B.equals(systemTypeEnum)) {
					BackendFunctionCodeEnum beEnum = BackendFunctionCodeEnum.getBackendFunctionEnum(functionCode);
					funcCodeI18n = beEnum != null ? beEnum.getFunctionNameI18n() : StringUtils.EMPTY;
				}
				if(SystemTypeEnum.F.equals(systemTypeEnum)) {
					FrontendFunctionCodeEnum feEnum = FrontendFunctionCodeEnum.getFrontendFunctionEnum(functionCode);
					funcCodeI18n = feEnum != null ? feEnum.getFunctionNameI18n() : StringUtils.EMPTY;
				}
			}

			// 功能名稱
			String funcName = StringUtils.isNoneBlank(funcCodeI18n) ? messageUtil.getMessage(funcCodeI18n) : StringUtils.EMPTY;
			// 是否驗證身分 (是/否)
			String isVer = StringUtils.isBlank(log.getLoginType()) ? messageUtil.getMessage(YesNoFlag.N.getMsgKey()) : messageUtil.getMessage(YesNoFlag.Y.getMsgKey());
			
			if(SystemTypeEnum.B.equals(systemTypeEnum)) {
				KgoUser kgoUser = kgoUserMap.get(log.getCreateUser());
				String parOrg = StringUtils.EMPTY; String org = StringUtils.EMPTY; String name = StringUtils.EMPTY;
				if (kgoUser != null) {
					//TODO 人員 組織table 可能調整
					parOrg = KgoUtil.getOrganName(kgoUser.getOrgan());
					org = KgoUtil.getOrganName(kgoUser.getUnit());
					name = kgoUser.getName();
				}
				KgoLogExportExcelVo vo = new KgoLogExportExcelVo(order++, parOrg, org, name, funcName, isVer, log);
				dataList.add(vo);
			}
			
			if(SystemTypeEnum.F.equals(systemTypeEnum)) {
				KgoLogExportExcelVo vo = new KgoLogExportExcelVo(order++, funcName, isVer, log);
				dataList.add(vo);
			}
		}
		return dataList;
	}

	/**
	 * 前/後台 - 軌跡紀錄 - 匯出PDF.
	 *
	 * @param rq the rq
	 */
	@Override
	public void exportPdfAction(KgoLogExportPdfActionRq rq) {
		try {
			// 讀取pdf jasper檔案.
			InputStream repKgoLogJasper = new ClassPathResource("/templates/kgo/pdf/Rep_KgoLog.jasper").getInputStream();
	    	Map<String, Object> data = new HashMap<>();
	    	String fileName = "";
	    	
	    	List<String> subTitleList = new ArrayList<>();	    	
	    	// 後台 標題、文字處理
	    	if(SysType.BACK.equals(SysType.getType(rq.getSystemType()))) {
	    		fileName = messageUtil.getMessage("kgo.log.backend.excel.fileName");
	    		data.put("TITLE", messageUtil.getMessage("kgo.log.backend.excel.title"));
	    		
		    	subTitleList.add(messageUtil.getMessage("kgo.log.pdf.backend.sub.title1"));
		    	subTitleList.add(messageUtil.getMessage("kgo.log.pdf.backend.sub.title2"));
	    		subTitleList.add(messageUtil.getMessage("kgo.log.pdf.backend.sub.title3"));
		    	subTitleList.add(messageUtil.getMessage("kgo.log.pdf.backend.sub.title4"));
	    	// 前台 標題、文字處理
	    	} else {
	    		fileName = messageUtil.getMessage("kgo.log.frontend.excel.fileName");
	    		data.put("TITLE",  messageUtil.getMessage("kgo.log.frontend.excel.title"));
	    		
	    		subTitleList.add(messageUtil.getMessage("kgo.log.pdf.frontend.sub.title1"));
	    		subTitleList.add(messageUtil.getMessage("kgo.log.pdf.frontend.sub.title2"));
	    		subTitleList.add(messageUtil.getMessage("kgo.log.pdf.frontend.sub.title3"));
	    		subTitleList.add(messageUtil.getMessage("kgo.log.pdf.frontend.sub.title4"));
	    	}
	    	
	    	List<Image> imgList = rq.getImgBase64List().stream().map(i -> {
				try {
					return CryptUtil.base64ImgToImgFile(i);
				} catch (IOException e) {
					LOGGER.error("exportPdfAction 圖片轉換錯誤 : " + e.getMessage(), e);
					throw new KgoApiException(new ErrorResult(KgoBackEndApiError.IMAGE_TRANS_ERROR));
				}
			}).collect(Collectors.toList());
	    	
	    	JasperReport report = (JasperReport) JRLoader.loadObject(repKgoLogJasper);

	    	List<PdfBean> resultList = new ArrayList<>();
	    	int index = 0;
	    	for(String imageTitle : subTitleList) {
	    		PdfBean pdfBean = new PdfBean();
	    		pdfBean.setImageTitle(imageTitle);
	    		pdfBean.setImage(imgList.get(index++));
	    		resultList.add(pdfBean);
	    	}

	    	JasperPrint jp = JasperFillManager.fillReport(report, data, new JRBeanCollectionDataSource(resultList));
	    	response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"" + "; filename*=UTF-8" + fileName);
	    	
	    	//獲取輸出字節流
	        JRPdfExporter exporter = new JRPdfExporter();
	        ExporterInput exporterInput = new SimpleExporterInput(jp);
	        
	        exporter.setExporterInput(exporterInput);
	        OutputStreamExporterOutput exporterOutput= new SimpleOutputStreamExporterOutput(response.getOutputStream());
	        exporter.setExporterOutput(exporterOutput);
	        exporter.exportReport();
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new KgoApiException("exportPdfAction error " + e.getMessage(), e);
		}
	}
}
