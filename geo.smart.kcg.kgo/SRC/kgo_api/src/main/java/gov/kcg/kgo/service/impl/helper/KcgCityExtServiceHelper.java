package gov.kcg.kgo.service.impl.helper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import gov.kcg.kgo.dto.KgoZipF3AreaDto;
import gov.kcg.kgo.dto.SaCaseViewDetailColumnQueryDto;
import gov.kcg.kgo.dto.SaCaseViewQueryDto;
import gov.kcg.kgo.enums.backend.ApplyTypeEnum;
import gov.kcg.kgo.enums.common.ColumnTypeEnum;
import gov.kcg.kgo.enums.error.CityApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.model.KgoCaseDetail;
import gov.kcg.kgo.model.KgoCaseDetailPK;
import gov.kcg.kgo.model.KgoCasesetColumn;
import gov.kcg.kgo.model.KgoCasesetColumnPK;
import gov.kcg.kgo.repository.KgoCaseDetailRepository;
import gov.kcg.kgo.repository.KgoCaseMainRepository;
import gov.kcg.kgo.repository.KgoCasesetColumnRepository;
import gov.kcg.kgo.repository.KgoZipRepository;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.FileUtil;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.util.SpringUtil;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.saCase.home.rs.bean.CaseHandleCaseViewSaCaseApplyDataDataGridComplex;
import gov.kcg.kgo.viewModel.commonApi.flow.common.rs.FlowGetCaseInfoActionRs;
import gov.kcg.kgo.viewModel.commonApi.flow.common.rs.bean.CaseColumnDetail;
import gov.kcg.kgo.viewModel.commonApi.flow.common.rs.bean.FlowGetCaseInfoViewForm;
import gov.kcg.kgo.viewModel.commonApi.flow.common.rs.bean.MyDataFileBo;
import gov.kcg.kgo.viewModel.commonApi.genGeneralCase.rq.GenGeneralCaseActionRq;
import gov.kcg.kgo.viewModel.commonApi.genGeneralCase.rq.bean.CaseSetForm;
import gov.kcg.kgo.viewModel.commonApi.genGeneralCase.rq.bean.CaseSetMemo;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

/**
 * 對外API 高雄程式資料平台 API Service. ServiceHelper.
 */
public class KcgCityExtServiceHelper extends KgoServiceHelper {
	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(KcgCityExtServiceHelper.class);

	/**
	 * 取得 提供既有案件服務撈取案件資料 共通 rs. B-2流程_2、C-2流程_2
	 *
	 * @param caseId the case id
	 * @return the case info common rs
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public <T extends FlowGetCaseInfoActionRs<D>, D extends FlowGetCaseInfoViewForm> T getCaseInfoCommonRs(String caseId, T rs) throws InstantiationException, IllegalAccessException {
		rs = (T) rs.getClass().newInstance();
		Class<D> viewFormClass = (Class<D>) ((ParameterizedType) rs.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		try {
			// 驗證案件id&案件是否存在.
			SaCaseViewQueryDto caseMainDto = validateCase(caseId);

			if (caseMainDto != null) {
				// modify : 2021.01.28 增加覆核欄位資料 改由 getFlowGetCaseInfoViewForm() 撈出覆核欄位值
				// KgoCasesetColumnRepository kgoCasesetColumnRepository =
				// SpringUtil.getDao(KgoCasesetColumnRepository.class);
				// List<SaCaseColumnDetailDto> columnDtoList =
				// kgoCasesetColumnRepository.getSaCaseExtraData(caseId);

				// 取得既有案件服務撈取案件資料 (共通Form)
				Boolean isFLowC23 = false;
				D viewForm = getFlowGetCaseInfoViewForm(caseMainDto, viewFormClass, isFLowC23);
				rs.setData(viewForm);

				// 驗證案件 失敗
			} else {
				throw new KgoApiException(new ErrorResult(CityApiError.CASE_ID_ERROR));
			}
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new KgoApiException("getCaseInfoCommonRs error " + e.getMessage(), e);
		}
		return rs;
	}

//	/**
//	 * 取得既有案件服務撈取案件資料 (共通Form).
//	 *
//	 * @param <D>           the generic type
//	 * @param caseMainDto   the case main dto
//	 * @param columnDtoList the column dto list
//	 * @param viewFormClass the view form class
//	 * @return the flow get case info
//	 * @throws InstantiationException the instantiation exception
//	 * @throws IllegalAccessException the illegal access exception
//	 * @throws IOException
//	 * @throws JRException 
//	 * @throws ParseException 
//	 */
//	public <D extends FlowGetCaseInfoViewForm> D getFlowGetCaseInfoViewForm(SaCaseViewQueryDto caseMainDto, Class<D> viewFormClass)
//			throws InstantiationException, IllegalAccessException, IOException, JRException, ParseException {
//		
//		// modify : 2021.01.28  稅捐才需客製 傳入f3List
//		return getFlowGetCaseInfoViewForm(caseMainDto, viewFormClass);
//	}

	/**
	 * 取得既有案件服務撈取案件資料 (稅捐).
	 *
	 * @param <D>           the generic type
	 * @param caseMainDto   the case main dto
	 * @param f3List        the f 3 list
	 * @param viewFormClass the view form class
	 * @return the flow get case info view form
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws IOException            Signals that an I/O exception has occurred.
	 * @throws JRException
	 * @throws ParseException
	 */
	public <D extends FlowGetCaseInfoViewForm> D getFlowGetCaseInfoViewForm(SaCaseViewQueryDto caseMainDto, Class<D> viewFormClass, Boolean isFLowC23)
			throws InstantiationException, IllegalAccessException, IOException, JRException, ParseException {
		D viewForm = viewFormClass.newInstance();
		String caseId = caseMainDto.getCaseId();

		viewForm.setCaseId(caseMainDto.getCaseId());
		viewForm.setCaseSetName(caseMainDto.getCaseSetName());
		viewForm.setApplyDate(DateUtil.dateToString(caseMainDto.getApplyDate(), DateUtil.PATTEN_FULL_TIME_NO_HYPHEN));

		KgoCaseDetailRepository kgoCaseDetailRepository = SpringUtil.getBean(KgoCaseDetailRepository.class);
		EntityManager entityManager = SpringUtil.getBean(EntityManager.class);

		// 查找 欄位資料 & 覆核欄位
		List<SaCaseViewDetailColumnQueryDto> columnDtoList = kgoCaseDetailRepository.getSaCaseDetailData(caseId);
		for (SaCaseViewDetailColumnQueryDto saCaseViewDetailColumnQueryDto : columnDtoList) {
			entityManager.detach(saCaseViewDetailColumnQueryDto);
		}

		// 排除掉覆核欄位
		List<SaCaseViewDetailColumnQueryDto> filterDto = columnDtoList.stream().filter(item -> !"M".equals(item.getSetColumnType())).collect(Collectors.toList());

		// flowC23 才須處理 :段小段資料(zip) && 區處資料(f3)
		List<KgoZipF3AreaDto> kgoZipF3AreaDotList = null;
		if (isFLowC23) {
			KgoZipRepository kgoZipRepository = SpringUtil.getDao(KgoZipRepository.class);
			kgoZipF3AreaDotList = kgoZipRepository.findByZipF3AreaList();
		}

		/** == 覆核欄位另外處理 == */
		Map<String, List<SaCaseViewDetailColumnQueryDto>> typeMDtoMap = columnDtoList.stream().filter(item -> "M".equals(item.getSetColumnType()))
				.collect(Collectors.groupingBy(SaCaseViewDetailColumnQueryDto::getSetColumnId));

		for (Map.Entry<String, List<SaCaseViewDetailColumnQueryDto>> stringListEntry : typeMDtoMap.entrySet()) {
			// List<SaCaseViewDetailColumnQueryDto> value = stringListEntry.getValue();
			List<SaCaseViewDetailColumnQueryDto> value = typeMDtoMap.get(stringListEntry.getKey());
			Map<Integer, List<SaCaseViewDetailColumnQueryDto>> dataMap = value.stream().filter(x -> StringUtils.isNoneBlank(x.getSetCcolumnId()))
					.collect(Collectors.groupingBy(SaCaseViewDetailColumnQueryDto::getCcolumnRowNum, HashMap::new, Collectors.toCollection(LinkedList::new)));
			String joinedColumnValue = dataMap.keySet().stream().map(i -> {
				return dataMap.get(i).stream().map(item -> {
					String result = "";
					String ftext = item.getFtext();
					if (StringUtils.isNotEmpty(ftext)) {
						result += ftext;
					}
					ColumnTypeEnum columnTypeEnum = ColumnTypeEnum.getColumnTypeEnum(item.getSetCcolumnType());
					String realColumnValue = item.getRealColumnValue();
					if ((columnTypeEnum == ColumnTypeEnum.CHECKBOX || columnTypeEnum == ColumnTypeEnum.S_CHECKBOX || columnTypeEnum == ColumnTypeEnum.S_RADIO || columnTypeEnum == ColumnTypeEnum.RADIO
							|| columnTypeEnum == ColumnTypeEnum.DRP)) {
						realColumnValue = super.getColumnMappingValue(item.getSetCcolumnValue(), realColumnValue);
					}

					if (StringUtils.isNotEmpty(realColumnValue)) {
						result += realColumnValue;
					}
					String btext = item.getBtext();
					if (StringUtils.isNotEmpty(btext)) {
						result += btext;
					}
					return result;
				}).filter(s -> StringUtils.isNotEmpty(s)).collect(Collectors.joining(""));
			}).filter(s -> StringUtils.isNotEmpty(s)).collect(Collectors.joining("\r\n"));

			String joinedPdfColumnValue = dataMap.keySet().stream().map(i -> {
				return dataMap.get(i).stream().map(item -> {
					String result = "";
					String ftext = item.getFtext();
					if (StringUtils.isNotEmpty(ftext)) {
						result += ftext;
					}
					ColumnTypeEnum columnTypeEnum = ColumnTypeEnum.getColumnTypeEnum(item.getSetCcolumnType());
					String realColumnValue = item.getRealPdfColumnValue();
					if ((columnTypeEnum == ColumnTypeEnum.CHECKBOX || columnTypeEnum == ColumnTypeEnum.S_CHECKBOX || columnTypeEnum == ColumnTypeEnum.S_RADIO || columnTypeEnum == ColumnTypeEnum.RADIO
							|| columnTypeEnum == ColumnTypeEnum.DRP)) {
						realColumnValue = super.getColumnMappingPDFValue(columnTypeEnum, item.getSetCcolumnValue(), realColumnValue);
					}

					if (StringUtils.isNotEmpty(realColumnValue)) {
						result += realColumnValue;
					}
					String btext = item.getBtext();
					if (StringUtils.isNotEmpty(btext)) {
						result += btext;
					}
					return result;
				}).filter(s -> StringUtils.isNotEmpty(s)).collect(Collectors.joining(""));
			}).filter(s -> StringUtils.isNotEmpty(s)).collect(Collectors.joining("\r\n"));
			/*
			 * String joinedColumnValue =
			 * value.stream().sorted(Comparator.nullsLast(Comparator.comparingInt(item ->
			 * item.getCcolumnOrderNum() == null ? 0 : item.getCcolumnOrderNum()))).map(item
			 * -> { String result = ""; String ftext = item.getFtext(); if
			 * (StringUtils.isNotEmpty(ftext)) { result += ftext; } ColumnTypeEnum
			 * columnTypeEnum = ColumnTypeEnum.getColumnTypeEnum(item.getSetCcolumnType());
			 * String realColumnValue = item.getRealColumnValue(); if ((columnTypeEnum ==
			 * ColumnTypeEnum.CHECKBOX || columnTypeEnum == ColumnTypeEnum.S_CHECKBOX ||
			 * columnTypeEnum == ColumnTypeEnum.S_RADIO || columnTypeEnum ==
			 * ColumnTypeEnum.RADIO || columnTypeEnum == ColumnTypeEnum.DRP)) {
			 * realColumnValue = super.getColumnMappingPDFValue(columnTypeEnum,
			 * item.getSetCcolumnValue(), realColumnValue); }
			 * 
			 * if (StringUtils.isNotEmpty(realColumnValue)) { result += realColumnValue; }
			 * String btext = item.getBtext(); if (StringUtils.isNotEmpty(btext)) { result
			 * += btext; } return result; }).filter(s ->
			 * StringUtils.isNotEmpty(s)).collect(Collectors.joining("\r\n"));
			 */
			// 補正
			String joinedCorrectBValue = dataMap.keySet().stream().map(i -> {
				return dataMap.get(i).stream().map(item -> {
					String result = "";
					String ftext = item.getFtext();
					if (StringUtils.isNotEmpty(ftext)) {
						result += ftext;
					}
					ColumnTypeEnum columnTypeEnum = ColumnTypeEnum.getColumnTypeEnum(item.getSetCcolumnType());
					String realColumnValue = item.getCorrectBValue();

					if (new Integer(1).equals(item.getIsSource()) && (columnTypeEnum == ColumnTypeEnum.CHECKBOX || columnTypeEnum == ColumnTypeEnum.RADIO || columnTypeEnum == ColumnTypeEnum.DRP)) {
						realColumnValue = super.getColumnMappingValue(item.getSetCcolumnValue(), realColumnValue);
					}

					if (StringUtils.isNotEmpty(realColumnValue)) {
						result += realColumnValue;
					}
					String btext = item.getBtext();
					if (StringUtils.isNotEmpty(btext)) {
						result += btext;
					}
					return result;
				}).filter(s -> StringUtils.isNotEmpty(s)).collect(Collectors.joining(""));
			}).filter(s -> StringUtils.isNotEmpty(s)).collect(Collectors.joining("\r\n"));

			String joinedCorrectBPdfValue = dataMap.keySet().stream().map(i -> {
				return dataMap.get(i).stream().map(item -> {
					String result = "";
					String ftext = item.getFtext();
					if (StringUtils.isNotEmpty(ftext)) {
						result += ftext;
					}
					ColumnTypeEnum columnTypeEnum = ColumnTypeEnum.getColumnTypeEnum(item.getSetCcolumnType());
					String realColumnValue = item.getCorrectBPdfValue();
					if ((columnTypeEnum == ColumnTypeEnum.CHECKBOX || columnTypeEnum == ColumnTypeEnum.S_CHECKBOX || columnTypeEnum == ColumnTypeEnum.S_RADIO || columnTypeEnum == ColumnTypeEnum.RADIO
							|| columnTypeEnum == ColumnTypeEnum.DRP)) {
						realColumnValue = super.getColumnMappingPDFValue(columnTypeEnum, item.getSetCcolumnValue(), realColumnValue);
					}

					if (StringUtils.isNotEmpty(realColumnValue)) {
						result += realColumnValue;
					}
					String btext = item.getBtext();
					if (StringUtils.isNotEmpty(btext)) {
						result += btext;
					}
					return result;
				}).filter(s -> StringUtils.isNotEmpty(s)).collect(Collectors.joining(""));
			}).filter(s -> StringUtils.isNotEmpty(s)).collect(Collectors.joining("\r\n"));

			SaCaseViewDetailColumnQueryDto saCaseViewDetailColumnQueryDto = value.get(0);
			saCaseViewDetailColumnQueryDto.setRealColumnValue(joinedColumnValue);
			saCaseViewDetailColumnQueryDto.setRealPdfColumnValue(joinedPdfColumnValue);
			saCaseViewDetailColumnQueryDto.setCorrectBValue(joinedCorrectBValue);
			saCaseViewDetailColumnQueryDto.setCorrectBPdfValue(joinedCorrectBPdfValue);

			filterDto.add(saCaseViewDetailColumnQueryDto);
		}
		/** == 覆核欄位另外處理 == */

		// 合併覆核欄位的資料
		filterDto = filterDto.stream().sorted(Comparator.comparingInt(SaCaseViewDetailColumnQueryDto::getColumnOrderNum)).collect(Collectors.toList());

		// 申請資料處裡
		List<CaseColumnDetail> caseColumns = new ArrayList<>();
		List<CaseColumnDetail> casePdfColumns = new ArrayList<>();
		List<MyDataFileBo> myDataList = new ArrayList<>();
		for (SaCaseViewDetailColumnQueryDto dto : filterDto) {
			String columnValue = dto.getRealColumnValue();
			String columnPdfValue = dto.getRealPdfColumnValue();

			boolean isMydataFileCol = false;
			// 是否為 myData 資料集(檔案)
			// MyDataColumn = CSV, PDF 是檔案 detail 會存檔案名稱
			if (StringUtils.isNotBlank(dto.getMyDataColumn()) && StringUtils.isNotBlank(dto.getMyDataId())) {
				// MyDataColumn = CSV, PDF 是檔案 detail 會存檔案名稱
				if ("CSV".equals(dto.getMyDataColumn()) || "PDF".equals(dto.getMyDataColumn())) {
					isMydataFileCol = true;
					// 檔案名稱 逗號分隔
					String[] fileNameArr = StringUtils.split(columnValue, ",");
					if (fileNameArr != null) {
						for (String fileName : fileNameArr) {
							// 案件附件 檔案上傳路徑
							try {
								byte[] myDataFileByte = FileUtils.readFileToByteArray(FileUtils.getFile(KgoUtil.getCaseDownloadUploadBasePath(caseMainDto.getCaseId()) + fileName));
								MyDataFileBo myDayaFile = new MyDataFileBo(fileName, Base64.getEncoder().encodeToString(myDataFileByte));
								myDataList.add(myDayaFile);
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					}
				}
			}

			// flowC23流程 稅捐需求
			List<List<CaseHandleCaseViewSaCaseApplyDataDataGridComplex>> complexDataList = null;
			if (isFLowC23) {
				complexDataList = new ArrayList<List<CaseHandleCaseViewSaCaseApplyDataDataGridComplex>>();
				if (typeMDtoMap.containsKey(dto.getSetColumnId())) {
					List<SaCaseViewDetailColumnQueryDto> value = typeMDtoMap.get(dto.getSetColumnId());
					Map<Integer, List<SaCaseViewDetailColumnQueryDto>> dataMap = value.stream().filter(x -> StringUtils.isNoneBlank(x.getSetCcolumnId()))
							.collect(Collectors.groupingBy(SaCaseViewDetailColumnQueryDto::getCcolumnRowNum, HashMap::new, Collectors.toCollection(LinkedList::new)));
					complexDataList = dataMap.keySet().stream().map(i -> {
						return dataMap.get(i).stream().map(cl -> {
							CaseHandleCaseViewSaCaseApplyDataDataGridComplex complexData = new CaseHandleCaseViewSaCaseApplyDataDataGridComplex();
							complexData.setbText(StringUtils.defaultString(cl.getBtext(), ""));
							complexData.setcColumnId(StringUtils.defaultString(cl.getSetCcolumnId(), ""));
							// 2021/01/18 判斷複合欄位型態
							complexData.setColumnType(StringUtils.defaultString(cl.getSetCcolumnType(), ""));
							String ccolumnValue = cl.getRealColumnValue();
							if (StringUtils.isNotBlank(ccolumnValue) && (cl.getSetCcolumnType().equalsIgnoreCase(ColumnTypeEnum.S_RADIO.getValue())
									|| cl.getSetCcolumnType().equalsIgnoreCase(ColumnTypeEnum.S_CHECKBOX.getValue()) || cl.getSetCcolumnType().equalsIgnoreCase(ColumnTypeEnum.DRP.getValue()))) {
								ccolumnValue = super.getColumnMappingValue(cl.getSetCcolumnValue(), ccolumnValue);
							}

							String ccorrectBValue = cl.getCorrectBValue();
							if (StringUtils.isNotBlank(ccorrectBValue) && (cl.getSetCcolumnType().equalsIgnoreCase(ColumnTypeEnum.S_RADIO.getValue())
									|| cl.getSetCcolumnType().equalsIgnoreCase(ColumnTypeEnum.S_CHECKBOX.getValue()) || cl.getSetCcolumnType().equalsIgnoreCase(ColumnTypeEnum.DRP.getValue()))) {
								ccorrectBValue = super.getColumnMappingValue(cl.getSetCcolumnValue(), ccorrectBValue);
							}
							complexData.setColumnValue(StringUtils.defaultString(cl.getSetCcolumnValue(), ""));
							complexData.setfText(StringUtils.defaultString(cl.getFtext(), ""));
							complexData.setOrderNum(cl.getCcolumnOrderNum());
							complexData.setRow(cl.getCcolumnRowNum());
							complexData.setValue(StringUtils.defaultString(ccolumnValue, ""));
							complexData.setCorrectBValue(StringUtils.defaultString(ccorrectBValue, ""));
							return complexData;
						}).collect(Collectors.toList());
					}).collect(Collectors.toList());
				}
			}

			// 一般資料(非mtData資料)
			if (!isMydataFileCol) {
				// 有來源資料mapping
				if (new Integer(1).equals(dto.getIsSource())) {
					// 取得KGO_CASE_DETAIL columnValue 對應到 KGO_CASESET 的對應資料轉換.
					columnValue = super.getColumnMappingValue(dto.getSetColumnValue(), dto.getRealColumnValue());

					ColumnTypeEnum columnTypeEnum = ColumnTypeEnum.getColumnTypeEnum(dto.getSetColumnType());
					columnPdfValue = super.getColumnMappingPDFValue(columnTypeEnum, dto.getSetColumnValue(), dto.getRealColumnValue());
				}

				CaseColumnDetail caseColumn = new CaseColumnDetail(dto.getSetColumnId(), dto.getColumnName(), columnValue, complexDataList);
				CaseColumnDetail casePdfColumn = new CaseColumnDetail(dto.getSetColumnId(), dto.getColumnName(), columnPdfValue, complexDataList);
				caseColumns.add(caseColumn);
				casePdfColumns.add(casePdfColumn);
			}

			// flowC23流程 稅捐需求
			if (isFLowC23) {
				// modify 2021.01.28 地址, 稅捐需求: 由地址取得分處、產製pdf bae64Str
				ColumnTypeEnum columnTypeEnum = ColumnTypeEnum.getColumnTypeEnum(dto.getSetColumnType());
				if (columnTypeEnum == ColumnTypeEnum.ADDRESSTEXT && ObjectUtils.isNotEmpty(kgoZipF3AreaDotList)) {
					String f3Name = super.getF3Name(columnValue, kgoZipF3AreaDotList);
					if (StringUtils.isNotBlank(f3Name)) {
						viewForm.setF3Name(f3Name);
					}
				}
			}
		}

		// 產製pdf bae64Str
		viewForm.setCaseInfoPdfBase64Str(this.genCaseInfoPdfToBase64Str(caseMainDto, casePdfColumns, viewForm.getF3Name()));

		viewForm.setCaseColumns(caseColumns);
		viewForm.setDataList(myDataList);
		return viewForm;
	}

	/**
	 * 產製pdf bae64Str.
	 *
	 * @param <D>         the generic type
	 * @param caseMainDto the case main dto
	 * @param viewForm    the view form
	 * @param caseColumns the case columns
	 * @return the string
	 * @throws IOException    Signals that an I/O exception has occurred.
	 * @throws JRException    the JR exception
	 * @throws ParseException the parse exception
	 */
	private String genCaseInfoPdfToBase64Str(SaCaseViewQueryDto caseMainDto, List<CaseColumnDetail> caseColumns, String f3Name) throws IOException, JRException, ParseException {
		String caseInfoPdfBase64Str = StringUtils.EMPTY;

		/** == 產製PDF == */
		// parameter
		Map<String, Object> parameter = new HashMap<String, Object>();
		//
		InputStream getCaseInfoRepFile = new ClassPathResource("/templates/kgo/pdf/getCaseInfo/KGO_API_GETCASE_INGO.jasper").getInputStream();
		JasperReport getCaseInfoRep = (JasperReport) JRLoader.loadObject(getCaseInfoRepFile);
		parameter.put("CASEID", caseMainDto.getCaseId());
		parameter.put("CASESET_NAME", caseMainDto.getCaseSetName());

		parameter.put("LIMIT_DAY", caseMainDto.getLimitedPeriod() + "天");
		// parameter.put("APPLY_DATE", DateUtil.strDateFormat(viewForm.getApplyDate(),
		// DateUtil.PATTEN_FULL_TIME_NO_HYPHEN, DateUtil.PATTEN_FULL_TIME_SLASH));
		parameter.put("APPLY_DATE", DateUtil.dateToString(caseMainDto.getApplyDate(), DateUtil.PATTEN_FULL_TIME_SLASH));
		// String f3Name = viewForm.getF3Name(); // 分處
		parameter.put("F3_AREA", StringUtils.isNotBlank(f3Name) ? f3Name : StringUtils.EMPTY);
		parameter.put("LIMIT_TIME", DateUtil.dateToString(caseMainDto.getDeadlineDate(), DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH));

		parameter.put("CASE_COLUMNS", caseColumns);

		InputStream getCaseInfoSubRepFile = new ClassPathResource("/templates/kgo/pdf/getCaseInfo/SUB_REPORT_COL.jasper").getInputStream();
		JasperReport getCaseInfoSubRep = (JasperReport) JRLoader.loadObject(getCaseInfoSubRepFile);
		parameter.put("SUBREPORT", getCaseInfoSubRep);

		JasperPrint printRep = null;
		// 資料套版
		printRep = JasperFillManager.fillReport(getCaseInfoRep, parameter, new JREmptyDataSource());

		List<JasperPrint> jasperPrintList = new ArrayList<>();
		jasperPrintList.add(printRep);

		// 獲取輸出字節流
		JRPdfExporter exporter = new JRPdfExporter();
		ExporterInput exporterInput = SimpleExporterInput.getInstance(jasperPrintList);
		exporter.setExporterInput(exporterInput);

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(os);
		exporter.setExporterOutput(exporterOutput);
		exporter.exportReport();

		/** == 產製PDF == */
		caseInfoPdfBase64Str = Base64.getEncoder().encodeToString(os.toByteArray());
		File fileFolder = new File(KgoUtil.getCaseDownloadUploadBasePath(caseMainDto.getCaseId()));
		saveFile(fileFolder, caseInfoPdfBase64Str, String.format("%s.pdf", caseMainDto.getCaseId()));
		return caseInfoPdfBase64Str;
	}

	private void saveFile(File fileFolder, String fileBase64Str, String fileName) {
		try {
			if (StringUtils.isNoneBlank(fileBase64Str)) {
				String base64Str = fileBase64Str;

				String[] dataString = fileBase64Str.split(",");
				if (dataString.length > 1) {
					base64Str = dataString[1];
				}
				byte[] decoder = Base64.getDecoder().decode(base64Str);
				FileUtil.createFile(fileFolder, fileName, decoder);
			}
		} catch (Exception e) {
			LOGGER.error("\n >>>>>>>saveFile>>> " + e.getMessage(), e);
		}
	}

	/**
	 * 驗證案件id&案件是否存在.
	 *
	 * @param caseId the case id
	 * @return true, if successful
	 */
	private SaCaseViewQueryDto validateCase(String caseId) {
		if (StringUtils.isBlank(caseId)) {
			return null;
		}
		KgoCaseMainRepository kgoCaseMainRepository = SpringUtil.getDao(KgoCaseMainRepository.class);
		SaCaseViewQueryDto caseMainDto = kgoCaseMainRepository.getSaCaseViewData(caseId);
		return caseMainDto;
	}

	/**
	 * 取得欄位設定map 判斷欄位資料是否有對應值.
	 *
	 * @param columnDetailList the column detail list
	 * @param caseSetId        the case set id
	 * @param maxVerson        the max verson
	 * @return the case couumn map
	 */
	public Map<KgoCasesetColumnPK, KgoCasesetColumn> getCaseColumnMap(List<CaseColumnDetail> columnDetailList, String caseSetId, Integer maxVerson) {
		List<KgoCasesetColumnPK> caseColumnIds = columnDetailList.stream().map(d -> {
			KgoCasesetColumnPK id = new KgoCasesetColumnPK();
			id.setCaseSetId(caseSetId);
			id.setColumnId(d.getColumnId());
			id.setVersion(maxVerson);
			return id;
		}).collect(Collectors.toList());

		KgoCasesetColumnRepository kgoCasesetColumnRepository = SpringUtil.getDao(KgoCasesetColumnRepository.class);

		// 取得欄位設定map 判斷欄位資料是否有對應值
		Map<KgoCasesetColumnPK, KgoCasesetColumn> caseCouumnMap = kgoCasesetColumnRepository.findAllById(caseColumnIds).stream().collect(Collectors.toMap(c -> c.getId(), c -> c));
		return caseCouumnMap;
	}

	/**
	 * 提供平台案件處理區呼叫使用，C-3流程 detail資料處理.
	 *
	 * @param newCaseId        the new case id
	 * @param caseSetId        the case set id
	 * @param maxVerson        the max verson
	 * @param columnDetailList the column detail list
	 * @param caseCouumnMap    the case couumn map
	 * @return the list
	 */
	public List<KgoCaseDetail> generalSaveDetailList(String newCaseId, String caseSetId, Integer maxVerson, List<CaseColumnDetail> columnDetailList,
			Map<KgoCasesetColumnPK, KgoCasesetColumn> caseCouumnMap) {
		// 儲存detail
		List<KgoCaseDetail> detailList = new ArrayList<>();
		for (CaseColumnDetail columnDetail : columnDetailList) {
			KgoCaseDetail coulmnDetail = new KgoCaseDetail();
			KgoCaseDetailPK id = new KgoCaseDetailPK();
			id.setCaseId(newCaseId);
			id.setColumnId(columnDetail.getColumnId());
			id.setVersion(maxVerson);
			// 子欄位ID擺空
			id.setCColumnId("");

			coulmnDetail.setId(id);

			KgoCasesetColumnPK caseColumnPk = new KgoCasesetColumnPK();
			caseColumnPk.setCaseSetId(caseSetId);
			caseColumnPk.setColumnId(columnDetail.getColumnId());
			caseColumnPk.setVersion(maxVerson);
			KgoCasesetColumn caseSetColumn = caseCouumnMap.get(caseColumnPk);
			if (caseSetColumn != null) {
				ColumnTypeEnum columnType = ColumnTypeEnum.getColumnTypeEnum(caseSetColumn.getColumnType());
				// 設置資料類型 是否有對應直
				if (ColumnTypeEnum.DRP.equals(columnType) || ColumnTypeEnum.RADIO.equals(columnType) || ColumnTypeEnum.ADDRESS.equals(columnType)) {
					coulmnDetail.setIsSource(true);
				} else {
					coulmnDetail.setIsSource(false);
				}
			}

			coulmnDetail.setColumnValue(columnDetail.getColumnValue());
			detailList.add(coulmnDetail);
		}
		return detailList;
	}

	/**
	 * 通用型入案作業 檔案上傳 網路申辦(E)、臨櫃(C)、書表(L).
	 *
	 * @param rq the rq
	 */
	public void genGeneralCaseUploadFile(GenGeneralCaseActionRq rq) {
		String caseSetId = rq.getCaseSetId();
		// 網路
		CaseSetMemo rqMemoE = rq.getCaseSetMemoE();
		// 臨櫃
		CaseSetMemo rqMemoc = rq.getCaseSetMemoC();
		// 書表
		CaseSetForm rqForm = rq.getCaseSetForm();

		// 網路 檔案位置
		if (rqMemoE != null) {
			// base64 Str轉成檔案 放到對應資料夾
			byte[] decoder = Base64.getDecoder().decode(rqMemoE.getFileBase64Str());
			File fileFolder = new File(KgoUtil.getAttachmentUploadBasePath(caseSetId, ApplyTypeEnum.E.getValue()));
			FileUtil.createFile(fileFolder, rqMemoE.getFileName(), decoder);
		}

		// 臨櫃 檔案位置
		if (rqMemoc != null) {
			// base64 Str轉成檔案 放到對應資料夾
			byte[] decoder = Base64.getDecoder().decode(rqMemoc.getFileBase64Str());
			File fileFolder = new File(KgoUtil.getAttachmentUploadBasePath(caseSetId, ApplyTypeEnum.C.getValue()));
			FileUtil.createFile(fileFolder, rqMemoc.getFileName(), decoder);
		}

		// 書表 檔案位置
		if (rqForm != null) {
			// base64 Str轉成檔案 放到對應資料夾
			byte[] decoder = Base64.getDecoder().decode(rqForm.getFileBase64Str());
			File fileFolder = new File(KgoUtil.getFormDownloadUploadBasePath(caseSetId));
			FileUtil.createFile(fileFolder, rqForm.getFileName(), decoder);
		}
	}

	/**
	 * Instantiates a new common service helper.
	 */
	public KcgCityExtServiceHelper() {

	}

	// TODO 共通邏輯方法
	private static class Loader {
		/** The Constant instance. */
		private static final KcgCityExtServiceHelper instance = new KcgCityExtServiceHelper();
	}

	/**
	 * Gets the single instance of CommonServiceHelper.
	 *
	 * @return single instance of CommonServiceHelper
	 */
	public static KcgCityExtServiceHelper getInstance() {
		return Loader.instance;
	}
}
