package gov.kcg.kgo.service.impl.helper;

import gov.kcg.kgo.service.bean.mydata.viewModel.ColumnModel;

import java.util.Map;

/**
 * 中低收入老人生活津貼資料
 */
public class LowIncomeOldPersonMyDataHelper extends KgoMydataBaseServiceHelper{

    public LowIncomeOldPersonMyDataHelper(){
        super();
    }

    private static class Loader {
        /** The Constant instance. */
        private static final LowIncomeOldPersonMyDataHelper instance = new LowIncomeOldPersonMyDataHelper();
    }

    public static LowIncomeOldPersonMyDataHelper getInstance() {
        return LowIncomeOldPersonMyDataHelper.Loader.instance;
    }

    /**
     * 取得 MyDataId
     *
     * @return
     */
    @Override
    public String getMyDataId() {
        return "API.ccuZXSsnGv";
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
