package gov.kcg.kgo.service.impl.helper;

import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import gov.kcg.kgo.service.bean.mydata.viewModel.ColumnModel;

/**
 * 地籍及實價資料
 * 
 * @author joelee
 *
 */
public class CadastralAndActualPriceMyDataHelper extends KgoMydataBaseServiceHelper {

	/**
	 * Instantiates a new service helper.
	 */
	public CadastralAndActualPriceMyDataHelper() {
		super();
	}

	// TODO 共通邏輯方法
	private static class Loader {
		/** The Constant instance. */
		private static final CadastralAndActualPriceMyDataHelper instance = new CadastralAndActualPriceMyDataHelper();
	}

	/**
	 * Gets the single instance of CadastralAndActualPriceMyDataHelper.
	 *
	 * @return single instance of CadastralAndActualPriceMyDataHelper
	 */
	public static CadastralAndActualPriceMyDataHelper getInstance() {
		return Loader.instance;
	}

	@Override
	public String getMyDataId() {
		// TODO Auto-generated method stub
		return "API.KvvyRZSc5K";
	}

	private Object getHouseholdAddress(Map<String, Object> data) {
		if (ObjectUtils.isEmpty(data)) {
			return "";
		}
		String defaultStr = "";
		return String.format("%s%s%s%s", StringUtils.defaultIfEmpty((String) data.get("縣市"), defaultStr), StringUtils.defaultIfEmpty((String) data.get("鄉鎮市區"), defaultStr),
				StringUtils.defaultIfEmpty((String) data.get("段小段"), defaultStr), StringUtils.defaultIfEmpty((String) data.get("建物門牌"), defaultStr));
	}

	private Object getLandAddress(Map<String, Object> data) {
		if (ObjectUtils.isEmpty(data)) {
			return "";
		}
		String defaultStr = "";
		return String.format("%s%s%s%s", StringUtils.defaultIfEmpty((String) data.get("縣市"), defaultStr), StringUtils.defaultIfEmpty((String) data.get("鄉鎮市區"), defaultStr),
				StringUtils.defaultIfEmpty((String) data.get("段小段"), defaultStr), StringUtils.defaultIfEmpty((String) data.get("地建號"), defaultStr));
	}

	@Override
	public Object getCurrentColumnData(Map<String, Object> originalData, Map<String, Object> currentData, String columnName, ColumnModel columnModel) {
		// TODO Auto-generated method stub
		if (columnName.equalsIgnoreCase("建物地址")) {
			return getHouseholdAddress(currentData);
		}

		if (columnName.equalsIgnoreCase("土地地址")) {
			return getLandAddress(currentData);
		}

		return null;
	}

	@Override
	public String mockMyDataJsonString() {
		// TODO Auto-generated method stub
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
