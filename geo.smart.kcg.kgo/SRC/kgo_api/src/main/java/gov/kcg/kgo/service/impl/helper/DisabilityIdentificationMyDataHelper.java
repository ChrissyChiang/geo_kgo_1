package gov.kcg.kgo.service.impl.helper;

import gov.kcg.kgo.service.bean.mydata.viewModel.ColumnModel;

import java.util.Map;

/**
 * 身心障礙資格
 */
public class DisabilityIdentificationMyDataHelper extends KgoMydataBaseServiceHelper{

    public DisabilityIdentificationMyDataHelper(){
        super();
    }

    public static class Loader{
        /** The Constant instance. */
        private static final DisabilityIdentificationMyDataHelper instance = new DisabilityIdentificationMyDataHelper();
    }

    public static DisabilityIdentificationMyDataHelper getInstance() {
        return DisabilityIdentificationMyDataHelper.Loader.instance;
    }

    /**
     * 取得 MyDataId
     *
     * @return
     */
    @Override
    public String getMyDataId() {
        return "API.nyhnLM1fve";
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
