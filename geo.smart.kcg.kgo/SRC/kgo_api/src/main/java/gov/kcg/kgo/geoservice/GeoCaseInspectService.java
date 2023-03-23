package gov.kcg.kgo.geoservice;

import gov.kcg.kgo.enums.backend.ApplyCaseStatusEnum;
import gov.kcg.kgo.enums.backend.CaseMainStatusEnum;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.geomodel.GeoCaseInspectDataModel;
import gov.kcg.kgo.georepository.custom.GeoCaseInspectReposCustom;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoCaseInspectExcelQueryRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoCaseInspectQueryRq;
import gov.kcg.kgo.geoviewmodel.backend.rs.GeoCaseInspectQueryRs;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.CaseCaseInspectQueryViewForm;
import gov.kcg.kgo.service.ExcelTempExportService;
import gov.kcg.kgo.service.bean.excel.GeoCaseInspectExcelVo;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.MessageUtil;
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
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

/**
 * GEO 20211030 add
 * 後台-案件稽核管理:案件查詢 service
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class GeoCaseInspectService extends GeoBaseService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoCaseInspectService.class);

    @Autowired
    private GeoCaseInspectReposCustom geoCaseInspectReposCustom;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private ExcelTempExportService excelTempExportService;

    @Autowired
    private HttpServletResponse response;

    public GeoCaseInspectQueryRs getRandomCaseList(GeoCaseInspectQueryRq rq) {
        GeoCaseInspectQueryRs rs = new GeoCaseInspectQueryRs();
        CaseCaseInspectQueryViewForm viewForm = new CaseCaseInspectQueryViewForm();
        rs.setData(viewForm);
        KgoApiException error = null;
        try {
            if (ObjectUtils.isEmpty(rq.getPercentage()) || rq.getPercentage() <= 0) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
            }
            Timestamp startDate = null;
            Timestamp endDate = null;
            if (rq.getApplyDateStart() != null)
                startDate = DateUtil.strToTimestamp(rq.getApplyDateStart(), DateUtil.PATTEN_YEAR_MONTH_DAY);
            if (rq.getApplyDateEnd() != null)
                endDate = DateUtil.strToTimestamp(rq.getApplyDateEnd(), DateUtil.PATTEN_YEAR_MONTH_DAY);
            /** GEO 20211224 add 搜尋條件增加：服務名稱 caseSetName */
            List listData = geoCaseInspectReposCustom.getRandomCaseList(rq.getCaseFlowType(),rq.getCaseSetName(), startDate, endDate, rq.getPercentage());
            List<GeoCaseInspectDataModel> datas = Collections.synchronizedList(new ArrayList<>());
            for (int i = 0; i < listData.size(); i++) {
                GeoCaseInspectDataModel model = new GeoCaseInspectDataModel();
                Object[] recordArray = (Object[]) listData.get(i);
                model.setCaseId((String) recordArray[0]);
                model.setApplyDate(DateUtil.timestampToString((Timestamp) recordArray[1], DateUtil.PATTEN_YEAR_MONTH_DAY));
                model.setApplyUser((String) recordArray[2]);
                model.setCaseSetName((String) recordArray[3]);
                model.setDeadlineDate(DateUtil.dateToString((Date) recordArray[4], DateUtil.PATTEN_YEAR_MONTH_DAY));
                model.setStatus(getStatusName((String) recordArray[5]));
                model.setCaseOfficer(((String) recordArray[6]));
                datas.add(model);
            } //for (int i = 0; i < listData.size(); i++)
            viewForm.setDataList(datas);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            error = new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SEARCH), e);
        } finally {
            // 設置 成功/失敗訊息.
            setResultMessage(rq, rs, error);
        } //try
        return rs;
    } //getRandomCaseList

    /**
     * GEO 20211102 add
     * 後台-案件稽核管理:匯出excel
     *
     */
    public void exportExcelAction(GeoCaseInspectExcelQueryRq rq) {
        try {
            if (ObjectUtils.isEmpty(rq) || rq.getDataList().size() <= 0) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.DATA_NOT_EXIST));
            }

            String fileName = messageUtil.getMessage("kgo.backend.caseInspect.excel.fileName");
            String sheetName = messageUtil.getMessage("kgo.backend.caseInspect.excel.sheet");
            Map<String, Object> data = new HashMap<>();
            data.put("ORDER", messageUtil.getMessage("kgo.backend.caseInspect.excel.header.order"));
            data.put("CASE_ID", messageUtil.getMessage("kgo.backend.caseInspect.excel.header.caseId"));
            data.put("APPLY_DATE", messageUtil.getMessage("kgo.backend.caseInspect.excel.header.applyDate"));
            data.put("APPLICANT", messageUtil.getMessage("kgo.backend.caseInspect.excel.header.applicant"));
            data.put("CASE_NAME", messageUtil.getMessage("kgo.backend.caseInspect.excel.header.caseName"));
            data.put("DEADLINE_DATE", messageUtil.getMessage("kgo.backend.caseInspect.excel.header.deadlineDate"));
            data.put("STATUS", messageUtil.getMessage("kgo.backend.caseInspect.excel.header.status"));
            data.put("CASE_OFFICER", messageUtil.getMessage("kgo.backend.caseInspect.excel.header.caseOfficer"));

            List<GeoCaseInspectExcelVo> excelVoList = getLogDataList(rq);
            data.put("DATA_LIST", excelVoList);
            excelTempExportService.exportCaseInspectExcel(fileName, sheetName, data);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("exportExcelAction exportExcelAction error " + e.getMessage(), e);
        } //try catch
    } //exportExcelAction

    /**
     * 20211102 add
     * 後台 - 稽核管理 - excel產出資料
     *
     * @return
     */
    public List<GeoCaseInspectExcelVo> getLogDataList(GeoCaseInspectExcelQueryRq rq) {

        List<GeoCaseInspectExcelVo> geoCaseInspectExcelVos = new ArrayList<>();
        Integer orderNumber = 1;

        for (GeoCaseInspectDataModel model:rq.getDataList()){
            GeoCaseInspectExcelVo excelVo = new GeoCaseInspectExcelVo();
            excelVo.setOrder(orderNumber);
            excelVo.setCaseId(model.getCaseId());
            excelVo.setApplyDate(model.getApplyDate());
            excelVo.setApplyUser(model.getApplyUser());
            excelVo.setCaseName((model.getCaseSetName()));
            excelVo.setDeadlineDate(model.getDeadlineDate());
            excelVo.setStatus(model.getStatus());
            excelVo.setCaseOfficer(model.getCaseOfficer());
            geoCaseInspectExcelVos.add(excelVo);
        } //for (GeoCaseInspectDataModel model:rq.getDataList()){
        return geoCaseInspectExcelVos;
    } //getLogDataList

    /**
     * 20211102 add
     * 後台 - 稽核管理 - 取得案件狀態名稱
     *
     * @param status
     * @return
     */
    public String getStatusName(String status) {
        if (status == null) {
            status = CaseMainStatusEnum.OTHERS.getValue();
        } //if
        String statusName = StringUtils.EMPTY;
        if (CaseMainStatusEnum.getCaseMainStatusEnum(status) == null &&
                ApplyCaseStatusEnum.getApplyCaseStatusEnum(status) != null) {
            statusName = ApplyCaseStatusEnum.getApplyCaseStatusEnum(status).getLabel();
            return statusName;
        } else {
            CaseMainStatusEnum caseMainStatusEnum = CaseMainStatusEnum.getCaseMainStatusEnum(status);
            if (null != caseMainStatusEnum) {
                statusName = caseMainStatusEnum.getLabel();
                return statusName;
            } //if
        } //if else
        return statusName;
    } //getStatusName

    /**
     * 20211104 add
     * 後台 - 稽核管理 - pdf
     *
     */
    public void exportPdfAction(GeoCaseInspectExcelQueryRq rq) {
        try {
            if (ObjectUtils.isEmpty(rq) || rq.getDataList().size() <= 0) {
                throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
            }

            List<GeoCaseInspectExcelVo> excelVoList = getLogDataList(rq);

            InputStream CaseInspectJasper = new ClassPathResource("/templates/kgo/pdf/getCaseInfo/GEO_CaseInspect.jasper").getInputStream();
            Map<String, Object> data = new HashMap<>();
            String fileName = "";
            data.put("ORDER", messageUtil.getMessage("kgo.backend.caseInspect.pdf.header.order"));
            data.put("CASE_ID", messageUtil.getMessage("kgo.backend.caseInspect.pdf.header.caseId"));
            data.put("APPLY_DATE", messageUtil.getMessage("kgo.backend.caseInspect.pdf.header.applyDate"));
            data.put("APPLICANT", messageUtil.getMessage("kgo.backend.caseInspect.pdf.header.applicant"));
            data.put("CASE_NAME", messageUtil.getMessage("kgo.backend.caseInspect.pdf.header.caseName"));
            data.put("DEADLINE_DATE", messageUtil.getMessage("kgo.backend.caseInspect.pdf.header.deadlineDate"));
            data.put("STATUS", messageUtil.getMessage("kgo.backend.caseInspect.pdf.header.status"));
            data.put("CASE_OFFICER", messageUtil.getMessage("kgo.backend.caseInspect.pdf.header.caseOfficer"));

            JasperReport report = (JasperReport) JRLoader.loadObject(CaseInspectJasper);
            JasperPrint jp = JasperFillManager.fillReport(report, data, new JRBeanCollectionDataSource(excelVoList));
            fileName = messageUtil.getMessage("kgo.backend.caseInspect.pdf.fileName");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/pdf;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"" + "; filename*=UTF-8" + fileName);

            //獲取輸出字節流
            JRPdfExporter exporter = new JRPdfExporter();
            ExporterInput exporterInput = new SimpleExporterInput(jp);
            exporter.setExporterInput(exporterInput);
            OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(response.getOutputStream());
            exporter.setExporterOutput(exporterOutput);
            exporter.exportReport();
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
            throw apiE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new KgoApiException("exportPdfAction error " + e.getMessage(), e);
        } //try catch
    } //exportPdfAction
}
