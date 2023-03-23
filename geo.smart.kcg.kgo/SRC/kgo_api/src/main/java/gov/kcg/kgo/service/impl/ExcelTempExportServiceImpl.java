package gov.kcg.kgo.service.impl;

import gov.kcg.kgo.enums.common.ExcelType;
import gov.kcg.kgo.enums.error.KgoCommonApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireAnswerDetail;
import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireCasesetDetail;
import gov.kcg.kgo.service.ExcelTempExportService;
import gov.kcg.kgo.service.bean.excel.GeoCaseHandleExcelVo;
import gov.kcg.kgo.service.bean.excel.GeoCaseRentalCaseExcelVo;
import gov.kcg.kgo.service.bean.excel.GeoQuestionExportExcelVo;
import gov.kcg.kgo.util.ExcelUtil;
import gov.kcg.kgo.util.MessageUtil;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.saCase.home.rs.bean.CaseHandleCaseViewSaCaseApplyDataDataGrid;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.IntStream;

@Transactional(rollbackFor = Exception.class)
@Service("ExcelTempExportService")
public class ExcelTempExportServiceImpl implements ExcelTempExportService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelTempExportServiceImpl.class);

    private final int FIXED_FIELD_COUNT = 4;
    private final int FIXED_FIELD_TOPIC_NAME_START = 0;
    private final int FIXED_FIELD_DETAIL_NAME_START = 1;
    private final int FIXED_FIELD_ANSWER_VALUE_START = 2;
    private final int FIXED_FIELD_CASE_NAME_START = 9;
    private final int FIXED_FIELD_CASE_VALUE_START = 1;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private MessageUtil messageUtil;

    /**
     * 軌跡紀錄 匯出報表 Excel下載
     *
     * @param fileName
     * @param sheetName
     * @param data
     * @param tempPath
     */
    @Override
    public void exportUserLogExcel(String fileName, String sheetName, Map<String, Object> data, String tempPath) {
        try {

            // 匯出excel 共通方法
            exportExcel(fileName, sheetName, data, tempPath);

        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException(new ErrorResult(KgoCommonApiError.FAIL_TO_DOWNLOAD));
        }
    }

    /**
     * 案件軌跡紀錄 匯出報表 Excel下載.
     *
     * @param fileName
     * @param sheetName
     * @param data
     */
    @Override
    public void exportCaseLogExcel(String fileName, String sheetName, Map<String, Object> data) {
        try {

            String tempPath = "/templates/kgo/excel/kgoCaseLog/案件軌跡紀錄_temp.xlsx";
            // 匯出excel 共通方法
            exportExcel(fileName, sheetName, data, tempPath);

        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException(new ErrorResult(KgoCommonApiError.FAIL_TO_DOWNLOAD));
        }
    }

    /**
     * GEO 20211030 add
     * 問卷題組 匯出報表 Excel下載
     *
     * @param fileName
     * @param sheetName
     * @param data
     * @param topic
     * @param exportExcelVos
     */
    @Override
    public void exportQuestionExcel(String fileName, String sheetName, Map<String, Object> data, LinkedHashMap<String, List<GeoKgoQuestionnaireCasesetDetail>> topic, List<GeoQuestionExportExcelVo> exportExcelVos) {
        try {
            String tempPath = "/templates/kgo/excel/kgoQuestionLog/後台_問卷結果temp_.xlsx";
            exportQuestionExcel(fileName, sheetName, data, tempPath, topic, exportExcelVos);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException(new ErrorResult(KgoCommonApiError.FAIL_TO_DOWNLOAD));
        } //try catch
    } //exportQuestionExcel

    /**
     * GEO 20211102 add
     * 稽核管理 匯出報表 Excel下載
     *
     * @param fileName
     * @param sheetName
     * @param data
     */
    @Override
    public void exportCaseInspectExcel(String fileName, String sheetName, Map<String, Object> data) {
        try {
            String tempPath = "/templates/kgo/excel/kgoCaseLog/後台_稽核管理_temp.xlsx";
            // 匯出excel 共通方法
            exportExcel(fileName, sheetName, data, tempPath);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException(new ErrorResult(KgoCommonApiError.FAIL_TO_DOWNLOAD));
        } //try catch
    } //exportCaseViewExcel

    /**
     * GEO 20211105 add
     * 案件檢視 匯出報表 Excel下載
     *
     * @param fileName
     * @param sheetName
     * @param data
     */
    public void exportCaseViewExcel(String fileName, String sheetName, Map<String, Object> data, List<GeoCaseHandleExcelVo> excelVos) {
        try {
            String tempPath = "/templates/kgo/excel/kgoCaseLog/後台_案件檢視_temp.xlsx";
            // 匯出excel 共通方法
            exportCaseViewExcel(fileName, sheetName, data, tempPath, excelVos);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException(new ErrorResult(KgoCommonApiError.FAIL_TO_DOWNLOAD));
        } //try catch
    } //exportCaseViewExcel

    /**
     * 匯出excel 共通方法.
     *
     * @param fileName  the file name
     * @param sheetName the sheet name
     * @param data      the data
     * @param tempPath  the temp path
     * @throws IOException            Signals that an I/O exception has occurred.
     * @throws ParsePropertyException the parse property exception
     * @throws InvalidFormatException the invalid format exception
     */
    private void exportExcel(String fileName, String sheetName, Map<String, Object> data, String tempPath) throws IOException, ParsePropertyException, InvalidFormatException {
        Workbook workBook = null;
        InputStream is = null;
        try {
            // 設置回應表頭、ContentType、檔案名稱.
            ExcelUtil.setExcelResponse(response, ExcelType.XLSX, fileName);
            // 讀取範本
            is = new ClassPathResource(tempPath).getInputStream();
            XLSTransformer transformer = new XLSTransformer();
            // 傳入template的輸入流和map
            workBook = transformer.transformXLS(is, data);
            workBook.setSheetName(0, sheetName);
            workBook.write(response.getOutputStream());

        } catch (Exception e) {
            LOGGER.error(">>>>> exportExcel error: " + e.getMessage(), e);
            throw e;
        } finally {
            if (is != null) {
                is.close();
            }
            if (workBook != null) {
                // Closing the workbook
                workBook.close();
            }
        }
    }

    /**
     * GEO 20211030 add
     * 匯出excel 動態產生(for 問卷)
     *
     * @param fileName
     * @param sheetName
     * @param data
     * @param tempPath
     * @param topicAndDetailDataList
     * @param exportExcelVos
     * @throws IOException
     * @throws ParsePropertyException
     * @throws InvalidFormatException
     */
    private void exportQuestionExcel(String fileName, String sheetName, Map<String, Object> data, String tempPath, LinkedHashMap<String, List<GeoKgoQuestionnaireCasesetDetail>> topicAndDetailDataList,
                                     List<GeoQuestionExportExcelVo> exportExcelVos) throws IOException, ParsePropertyException, InvalidFormatException {
        Workbook workBook = null;
        InputStream is = null;
        try {
            //取資料
            int detailCellCount = 0; //所有子題目欄位數量
            List<Integer> detailSizeCountList = new ArrayList<>(); //放在動態生成的地方，所有子題目數量 ex[5,5,2,2]
            List<String> detailNameList = new ArrayList<>();//所有子題目 ex:整體服務體驗？、方便程度？
            List<String> topicNameList = new ArrayList<>(); //所有題組名稱

            //獲得所有子題目總數量欄位、與子選項總數量欄位才能計算動態合併的起始與結束值
            for (List<GeoKgoQuestionnaireCasesetDetail> list : topicAndDetailDataList.values()) {
                detailCellCount += list.size();
                detailSizeCountList.add(list.size());
                for (GeoKgoQuestionnaireCasesetDetail detail : list) {
                    detailNameList.add(detail.getDetailName());
                    LOGGER.info("exportQuestionExcel detail.getDetailName() =" + detail.getDetailName());
                } //for(GeoKgoQuestionnaireCasesetDetail detail : list)
            } //for(List<GeoKgoQuestionnaireCasesetDetail> list : topicAndDetailDataList.values())
            //LOGGER.info("exportQuestionExcel detailCellCount=" + detailCellCount);
            //獲得問卷題組名稱
            for (String s : topicAndDetailDataList.keySet()) {
                topicNameList.add(s);
                LOGGER.info("exportQuestionExcel topicNameList,s=" + s);
            } //for(String s : topicAndDetailDataList.keySet())
            //LOGGER.info("exportQuestionExcel topicNameList.size()=" + topicNameList.size());

            //設樣式
            ExcelUtil.setExcelResponse(response, ExcelType.XLSX, fileName);//設置回應表頭、ContentType、檔案名稱.
            is = new ClassPathResource(tempPath).getInputStream();//讀取範本
            XLSTransformer transformer = new XLSTransformer();
            workBook = transformer.transformXLS(is, data);//傳入template的輸入流和map
            Sheet sheet = workBook.getSheetAt(0);//獲得建立的表單
            Font font = workBook.createFont();//設定字體大小
            font.setFontHeightInPoints((short) 14);
            //題組名稱樣式
            CellStyle topicNameStyle = workBook.createCellStyle();
            topicNameStyle.setAlignment(HorizontalAlignment.LEFT); //水平置中
            topicNameStyle.setVerticalAlignment(VerticalAlignment.CENTER); //垂直置中
            topicNameStyle.setBorderBottom(BorderStyle.THIN);
            topicNameStyle.setBorderTop(BorderStyle.THIN);
            topicNameStyle.setBorderLeft(BorderStyle.THIN);
            topicNameStyle.setBorderRight(BorderStyle.THIN);
            topicNameStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());//設定顏色
            topicNameStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            topicNameStyle.setFont(font);
            //設定題組與子題目樣式、設子題目名稱
            for (int i = 0; i < detailCellCount; i++) {
                sheet.getRow(FIXED_FIELD_TOPIC_NAME_START).createCell(FIXED_FIELD_COUNT + i).setCellStyle(topicNameStyle);
                sheet.getRow(FIXED_FIELD_DETAIL_NAME_START).createCell(FIXED_FIELD_COUNT + i).setCellStyle(topicNameStyle);
            } //for (int i = 0; i < detailCellCount; i++)

            //作答資料樣式
            CellStyle answerStyle = workBook.createCellStyle();
            answerStyle.setAlignment(HorizontalAlignment.LEFT); //水平置中
            answerStyle.setVerticalAlignment(VerticalAlignment.CENTER); //垂直置中
            answerStyle.setBorderBottom(BorderStyle.THIN);
            answerStyle.setBorderTop(BorderStyle.THIN);
            answerStyle.setBorderLeft(BorderStyle.THIN);
            answerStyle.setBorderRight(BorderStyle.THIN);
            answerStyle.setFont(font);

            //動態合併表格，行數固定,4為excel起始位置(0~3為個人資料，固定寫死)
            int start = FIXED_FIELD_COUNT; //欄位起始值
            int end = 0;  //欄位結束值
            for (int i = 0; i < detailSizeCountList.size(); i++) {
                end = (start + detailSizeCountList.get(i)) - 1;
                //LOGGER.info("exportQuestionExcel if (end - start > 0) start=" + start + ",end=" + end);
                sheet.getRow(FIXED_FIELD_TOPIC_NAME_START).getCell(start).setCellValue(topicNameList.get(i));
                if (detailSizeCountList.get(i) > 1) { //合併數量不能為0，會報錯
                    sheet.addMergedRegion(new CellRangeAddress(0, 0, start, end));//CellRangeAddress有4個引數：起始行號，終止行號， 起始列號，終止列
                    //sheet.getRow(FIXED_FIELD_TOPIC_NAME_START).getCell(start).setCellValue(topicNameList.get(i));
                }
                start += detailSizeCountList.get(i);//下次合併的起始欄位值
            } //for (int i = 0; i < detailSizeCountList.size(); i++)

            //子題目設值
            for (int j = 0; j < detailCellCount; j++) {
                sheet.getRow(FIXED_FIELD_DETAIL_NAME_START).getCell(FIXED_FIELD_COUNT + j).setCellValue(detailNameList.get(j));
            } //for (int j = 0; j < detailCellCount ; j++)

            //放入作答資料
            for (int x = 0; x < exportExcelVos.size(); x++) {
                List<GeoKgoQuestionnaireAnswerDetail> answerDetails = exportExcelVos.get(x).getAnswerList();
                for (int i = 0; i < answerDetails.size(); i++) {
                    String answerValue = StringUtils.isBlank(answerDetails.get(i).getAnswerValue()) ? "" : answerDetails.get(i).getAnswerValue();
                    Integer answerScore = answerDetails.get(i).getAnswerScore();
                    if (answerScore != null && answerScore > 0) {
                        answerValue = answerScore + " " + answerValue;
                    }
                    sheet.getRow(x + FIXED_FIELD_ANSWER_VALUE_START).createCell(FIXED_FIELD_COUNT + i).setCellValue(answerValue);
                    sheet.getRow(x + FIXED_FIELD_ANSWER_VALUE_START).getCell(FIXED_FIELD_COUNT + i).setCellStyle(answerStyle);
                } // for (int i = 0; i < answerDetails.size(); i++)
            } //for (int x = 0; x < exportExcelVos.size(); x++)
            workBook.setSheetName(0, sheetName);
            workBook.write(response.getOutputStream());
        } catch (Exception e) {
            LOGGER.error("exportQuestionExcel exportQuestionExcel error: " + e.getMessage(), e);
            throw e;
        } finally {
            if (is != null) {
                is.close();
            } //if
            if (workBook != null) {
                // Closing the workbook
                workBook.close();
            } //if
        } //try finally
    } //exportQuestionExcel

    /**
     * GEO 20211109 add
     * 匯出excel 動態產生(for 案件檢視)
     *
     * @param fileName
     * @param sheetName
     * @param data
     * @param tempPath
     * @throws IOException
     * @throws ParsePropertyException
     * @throws InvalidFormatException
     */
    private void exportCaseViewExcel(String fileName, String sheetName, Map<String, Object> data, String tempPath, List<GeoCaseHandleExcelVo> voList) throws IOException, ParsePropertyException, InvalidFormatException {
        LOGGER.info("exportCaseViewExcel voList.size="+voList.size());
        Workbook workBook = null;
        InputStream is = null;
        try {
            //設樣式
            ExcelUtil.setExcelResponse(response, ExcelType.XLSX, fileName);//設置回應表頭、ContentType、檔案名稱.
            is = new ClassPathResource(tempPath).getInputStream();//讀取範本
            XLSTransformer transformer = new XLSTransformer();
            workBook = transformer.transformXLS(is, data);//傳入template的輸入流和map
            Sheet sheet = workBook.getSheetAt(0);//獲得建立的表單
            Font font = workBook.createFont();//設定字體大小
            font.setFontHeightInPoints((short) 14);

            if (voList.get(0).getDataGrids() != null) {
                List<String> columnNames = new ArrayList<>();//欄位名稱
                LinkedHashMap<String, List<String>> map = new LinkedHashMap<>();
                //獲得所有欄位名稱(一定會有1個以上，欄位名稱重複，所以直接以第一個的欄位名稱為主)
                for (int i = 0; i < voList.get(0).getDataGrids().size(); i++) {
                    columnNames.add(voList.get(0).getDataGrids().get(i).getColumnName());
                } //for

                //根據欄位名稱 獲取所有對應的欄位value
                for (String str:columnNames) {
                    List<String> columnValue = new ArrayList<>();
                    for(GeoCaseHandleExcelVo vos: voList){
                        for (CaseHandleCaseViewSaCaseApplyDataDataGrid gridList:vos.getDataGrids()){
                            if (str.equals(gridList.getColumnName())){
                                columnValue.add(gridList.getColumnValue());
                            }//if (str.equals(gridList.getColumnValue()))
                        } //for (CaseHandleCaseViewSaCaseApplyDataDataGrid
                    } //or(GeoCaseHandleExcelVo vos: voList)
                    map.put(str, columnValue);
                } //for (int i=0;i<columnNames.size();i++)
                LOGGER.info("beford map.size()="+map.size());

                //欄位名稱樣式
                CellStyle columnNameStyle = workBook.createCellStyle();
                columnNameStyle.setAlignment(HorizontalAlignment.CENTER); //水平置中
                columnNameStyle.setVerticalAlignment(VerticalAlignment.CENTER); //垂直置中
                columnNameStyle.setBorderBottom(BorderStyle.THIN);
                columnNameStyle.setBorderTop(BorderStyle.THIN);
                columnNameStyle.setBorderLeft(BorderStyle.THIN);
                columnNameStyle.setBorderRight(BorderStyle.THIN);
                columnNameStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());//設定顏色
                columnNameStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                columnNameStyle.setFont(font);

                int index=0;
                //放入欄位名稱，9為起始位置(0~8為固定匯出資料)
                for (String columnName: map.keySet()) {
                    sheet.getRow(0).createCell(FIXED_FIELD_CASE_NAME_START + index).setCellValue(columnName);
                    sheet.getRow(0).getCell(FIXED_FIELD_CASE_NAME_START + index).setCellStyle(columnNameStyle);
                    index++;
                } //for (int i = 0; i <columnNames.size(); i++)

                //Value樣式
                CellStyle valueStyle = workBook.createCellStyle();
                valueStyle.setAlignment(HorizontalAlignment.CENTER); //水平置中
                valueStyle.setVerticalAlignment(VerticalAlignment.CENTER); //垂直置中
                valueStyle.setBorderBottom(BorderStyle.THIN);
                valueStyle.setBorderTop(BorderStyle.THIN);
                valueStyle.setBorderLeft(BorderStyle.THIN);
                valueStyle.setBorderRight(BorderStyle.THIN);
                valueStyle.setFont(font);

                //放入ColumnValue,1為起始位置
                for (int i = 0; i < map.size(); i++) {
                    for (int j = 0; j < map.get(columnNames.get(i)).size(); j++) {
                        List<String> values = map.get(columnNames.get(i));
                        sheet.getRow(FIXED_FIELD_CASE_VALUE_START + j).createCell(FIXED_FIELD_CASE_NAME_START + i).setCellValue(values.get(j));
                        sheet.getRow(FIXED_FIELD_CASE_VALUE_START + j).getCell(FIXED_FIELD_CASE_NAME_START + i).setCellStyle(valueStyle);
                    } //for
                } //for
            } //if (voList.get(0).getDataGrids()!=null)
            workBook.setSheetName(0, sheetName);
            workBook.write(response.getOutputStream());
        } catch (Exception e) {
            LOGGER.error("exportCaseViewExcel exportCaseViewExcel error: " + e.getMessage(), e);
            throw e;
        } finally {
            if (is != null) {
                is.close();
            } //if
            if (workBook != null) {
                // Closing the workbook
                workBook.close();
            } //if
        } //try finally
    } //exportCaseViewExcel

    private void exportCaseRenatableViewExcel(String fileName, String[] sheetNames, Map<String, Object> data, String tempPath) throws IOException, ParsePropertyException, InvalidFormatException {
        List<GeoCaseRentalCaseExcelVo> rentableVoList = (List<GeoCaseRentalCaseExcelVo>) data.get("DATA_LIST");
        List<GeoCaseRentalCaseExcelVo> rentedVoList = (List<GeoCaseRentalCaseExcelVo>) data.get("DATA2_LIST");
        LOGGER.info("exportCaseRenatableViewExcel voList.size=" + rentableVoList.size());
        Workbook workBook = null;
        InputStream is = null;
        try {
            //設樣式
            ExcelUtil.setExcelResponse(response, ExcelType.XLSX, fileName);//設置回應表頭、ContentType、檔案名稱.
            is = new ClassPathResource(tempPath).getInputStream();//讀取範本
            XLSTransformer transformer = new XLSTransformer();
            workBook = transformer.transformXLS(is, data);//傳入template的輸入流和map
            Sheet sheet = workBook.getSheetAt(0);//獲得建立的表單
            Font font = workBook.createFont();//設定字體大小
            font.setFontHeightInPoints((short) 14);
            //TODO:時間管理大師
                //欄位名稱樣式
            CellStyle columnNameStyle = workBook.createCellStyle();
            columnNameStyle.setWrapText(true);
            columnNameStyle.setAlignment(HorizontalAlignment.LEFT); //水平置中
            columnNameStyle.setVerticalAlignment(VerticalAlignment.CENTER); //垂直置中
            columnNameStyle.setBorderBottom(BorderStyle.THIN);
            columnNameStyle.setBorderTop(BorderStyle.THIN);
            columnNameStyle.setBorderLeft(BorderStyle.THIN);
            columnNameStyle.setBorderRight(BorderStyle.THIN);
            columnNameStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            columnNameStyle.setFont(font);
            columnNameStyle.setFillForegroundColor(IndexedColors.WHITE.index);


            //分頁一內容
            int index = 1;
            for(GeoCaseRentalCaseExcelVo vo : rentableVoList){
                //單日場地可預約時間字串 (自表格 index 7 起)
                List<String> timeStrList = vo.getTimeList();
                for (int j = 0 ; j < timeStrList.size() ; j++ ) {
                    sheet.getRow(index).createCell(7 + j ).setCellValue(timeStrList.get(j));
                    sheet.getRow(index).getCell(7 + j ).setCellStyle(columnNameStyle);
                }
                index++;
            }


            //分頁二內容
            Sheet sheet2 = workBook.getSheetAt(1);//獲得建立的表單
            int index2 = 1;
            for(GeoCaseRentalCaseExcelVo vo : rentedVoList){
                //單日場地可預約時間字串 (自表格 index 7 起)
                List<String> timeStrList = vo.getTimeList();
                for (int j = 0 ; j < timeStrList.size() ; j++ ) {
                    sheet2.getRow(index2).createCell(7 + j ).setCellValue(timeStrList.get(j));
                    sheet2.getRow(index2).getCell(7 + j ).setCellStyle(columnNameStyle);
                }
                index2++;
            }


            for(int i = 0 ; i < sheetNames.length ; i++){
                workBook.setSheetName(i,sheetNames[i]);
            }
            workBook.write(response.getOutputStream());
        } catch (Exception e) {
            LOGGER.error("exportCaseViewExcel exportCaseViewExcel error: " + e.getMessage(), e);
            throw e;
        } finally {
            if (is != null) {
                is.close();
            } //if
            if (workBook != null) {
                // Closing the workbook
                workBook.close();
            } //if
        } //try finally
    } //exportCaseViewExcel

    /**
     * GEO 20211115 add
     * 線上預約臨櫃 Excel下載
     *
     * @param fileName
     * @param sheetName
     * @param data
     */
    @Override
    public void exportAppointmentExcel(String fileName, String sheetName, Map<String, Object> data) {
        try {
            String tempPath = "/templates/kgo/excel/KcgAppointLog/後台_線上預約臨櫃_temp.xlsx";
            // 匯出excel 共通方法
            exportExcel(fileName, sheetName, data, tempPath);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException(new ErrorResult(KgoCommonApiError.FAIL_TO_DOWNLOAD));
        } //try catch
    }
    
	/**
	 ** 場地案件檢視(匯出EXCEL)  Roy
	 */	    
    @Override
    public void exportCaseSiteExcel(String fileName, String sheetName, Map<String, Object> data , List<GeoCaseHandleExcelVo> excelVos) {
        try {
            String tempPath = "/templates/kgo/excel/site/CaseSiteExcel_temp.xlsx";
            exportCaseViewExcel(fileName, sheetName, data, tempPath ,excelVos);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException(new ErrorResult(KgoCommonApiError.FAIL_TO_DOWNLOAD));
        }
    }
    @Override
    public void exportCaseRentableExcel(String fileName, String[] sheetNames, Map<String, Object> data ){
        try {
            String tempPath = "/templates/kgo/excel/site/CaseRentableExcel_temp.xlsx";
            exportCaseRenatableViewExcel(fileName, sheetNames, data, tempPath );
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException(new ErrorResult(KgoCommonApiError.FAIL_TO_DOWNLOAD));
        }
    }




}
