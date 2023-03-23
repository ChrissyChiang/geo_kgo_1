package gov.kcg.kgo.service.impl.helper;

import gov.kcg.kgo.service.bean.mydata.viewModel.ColumnModel;

import java.util.Map;

/**
 * 身心障礙者生活補助資料
 */
public class DisabilityAllowanceMyDataHelper extends KgoMydataBaseServiceHelper {

    public DisabilityAllowanceMyDataHelper(){
        super();
    }

    public static class Loader{
        /** The Constant instance. */
        private static final DisabilityAllowanceMyDataHelper instance = new DisabilityAllowanceMyDataHelper();
    }

    public static DisabilityAllowanceMyDataHelper getInstance() {
        return DisabilityAllowanceMyDataHelper.Loader.instance;
    }


    /**
     * 取得 MyDataId
     *
     * @return
     */
    @Override
    public String getMyDataId() {
        return "API.QOHFReaQFc";
    }

    /**
     * 客製化欄位處理
     *
     * @param originalData
     * @param currentData
     * @param columnName
     * @param columnModel
     * @return
     */
    @Override
    public Object getCurrentColumnData(Map<String, Object> originalData, Map<String, Object> currentData, String columnName, ColumnModel columnModel) {
        return null;
    }

    /**
     * Mock MyData JsonString
     *
     * @return
     */
    @Override
    public String mockMyDataJsonString() {
        return null;
    }

    /**
     * Mock MyData PdfFile
     *
     * @return
     */
    @Override
    public String mockMyDataPdfFile() {
        return null;
    }

    /**
     * Mock MyData CsvFile
     *
     * @return
     */
    @Override
    public String mockMyDataCsvFile() {
        return null;
    }
}
