package gov.kcg.kgo.service.impl.helper;

import gov.kcg.kgo.service.bean.mydata.viewModel.ColumnModel;

import java.util.Map;

/**
 * 全國志工時數
 */
public class VolunteerHoursMyDataHelper extends KgoMydataBaseServiceHelper{

    public VolunteerHoursMyDataHelper(){
        super();
    }

    public static class Loader{
        /** The Constant instance. */
        private static final VolunteerHoursMyDataHelper instance = new VolunteerHoursMyDataHelper();
    }

    public static VolunteerHoursMyDataHelper getInstance() {
        return VolunteerHoursMyDataHelper.Loader.instance;
    }

    /**
     * 取得 MyDataId
     *
     * @return
     */
    @Override
    public String getMyDataId() {
        return "API.Ev4zr0WpdE";
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
