package gov.kcg.kgo.service.impl.helper;

import gov.kcg.kgo.service.bean.mydata.viewModel.ColumnModel;

import java.util.Map;

/**
 * 商業負責人、合夥人、經理人及法定代理人之商業登記資料
 *
 *
 */
public class CompanyManagersInfoMyDataHelper extends KgoMydataBaseServiceHelper{

    /**
     * Instantiates a new account management service helper.
     */
    public CompanyManagersInfoMyDataHelper() {
        super();
    }

    // TODO 共通邏輯方法
    private static class Loader {
        /** The Constant instance. */
        private static final CompanyManagersInfoMyDataHelper instance = new CompanyManagersInfoMyDataHelper();
    }

    /**
     * Gets the single instance of AccountManagementServiceHelper.
     *
     * @return single instance of AccountManagementServiceHelper
     */
    public static CompanyManagersInfoMyDataHelper getInstance() {
        return CompanyManagersInfoMyDataHelper.Loader.instance;
    }

    @Override
    public String getMyDataId() {
        // TODO Auto-generated method stub
        return "API.Ev4zr0WpdE";
    }

    @Override
    public Object getCurrentColumnData(Map<String, Object> originalData, Map<String, Object> currentData,
                                       String columnName, ColumnModel columnModel) {
        // TODO Auto-generated method stub
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

    @Override
    public String mockMyDataPdfFile() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String mockMyDataCsvFile() {
        // TODO Auto-generated method stub
        return null;
    }

}
