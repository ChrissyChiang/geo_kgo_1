package gov.kcg.kgo.service.impl.helper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.kcg.kgo.service.bean.mydata.viewModel.ColumnModel;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.ExcelCSV;
import gov.kcg.kgo.util.JsonUtil;
import gov.kcg.kgo.viewModel.mydata.bo.ServiceDataBO;
import gov.kcg.kgo.viewModel.mydata.bo.ServiceDataFileBO;

/**
 * MyData 規格書轉換底層
 * 
 * @author joelee
 *
 */
public abstract class KgoMydataBaseServiceHelper extends KgoServiceHelper {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(KgoMydataBaseServiceHelper.class);

	private static final String DEFAULT_JSON_FILE_NAME = "JSON";
	private static final String DEFAULT_CSV_FILE_NAME = "CSV";
	private static final String DEFAULT_PDF_FILE_NAME = "PDF";

	private static final String CSV_FILE_TAG = "CSV_FILE";

	private static final String PDF_FILE_TAG = "PDF_FILE";

	private ServiceDataBO myDataFile;

	public KgoMydataBaseServiceHelper() {
		this.myDataFile = new ServiceDataBO();
	}

	public KgoMydataBaseServiceHelper(ServiceDataBO myDataFile) {
		this.myDataFile = myDataFile;
	}

	/**
	 * 取得 MyDataId
	 * 
	 * @return
	 */
	public abstract String getMyDataId();

	/**
	 * 取得 MyData Service Data BO
	 * 
	 * @return
	 */
	public ServiceDataBO getMyDataFile() {
		return myDataFile;
	}

	/**
	 * 設得 MyData Service Data BO
	 * 
	 * @param myDataFile
	 */
	public void setMyDataFile(ServiceDataBO myDataFile) {
		this.myDataFile = myDataFile;
	}

	public Map<String, Object> getColumnData(List<ColumnModel> columnNames) throws Exception {
		Map<String, Object> dataMap = getDataSource(columnNames);
		return this.getBaseFileColumnData(dataMap, columnNames);
	}

	/**
	 * 取得 merge 欄位值
	 * 
	 * @param columnName
	 * @return
	 * @throws Exception
	 */
	public Object getJsonColumnData(ColumnModel columnName) throws Exception {
		return this.getJsonBaseColumnData(this.getJsonString(), columnName);
	}

	/**
	 * 取得 merge 欄位值
	 * 
	 * @param columnName
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public Object getJsonColumnData(ColumnModel columnName, String fileName) throws Exception {
		return this.getJsonBaseColumnData(this.getJsonString(fileName), columnName);
	}

	/**
	 * 取得 MyData jsonString merge 欄位值
	 * 
	 * @param jsonString
	 * @param columnName
	 * @return
	 * @throws Exception
	 */
	public Object getJsonColumnData(String jsonString, ColumnModel columnName) throws Exception {
		return this.getJsonBaseColumnData(jsonString, columnName);
	}

	/**
	 * 取得 MyData jsonString merge 欄位值
	 * 
	 * @param jsonString
	 * @param columnName
	 * @return
	 * @throws Exception
	 */
	protected Object getJsonBaseColumnData(String jsonString, ColumnModel columnName) throws Exception {
		Object object = this.getBaseJsanMapData(jsonString);
		return getBaseColumnData(object, columnName);
	}

	/**
	 * 取得 merge 欄位值
	 * 
	 * @param columnNames
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getJsonColumnData(List<ColumnModel> columnNames) throws Exception {
		return this.getJsonBaseColumnData(this.getJsonString(), columnNames);
	}

	/**
	 * 取得 merge 欄位值
	 * 
	 * @param columnNames
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getJsonColumnData(List<ColumnModel> columnNames, String fileName) throws Exception {
		return this.getJsonBaseColumnData(this.getJsonString(fileName), columnNames);
	}

	/**
	 * 取得 MyData jsonString merge 欄位值
	 * 
	 * @param jsonString
	 * @param columnNames
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getJsonColumnData(String jsonString, List<ColumnModel> columnNames) throws Exception {
		return this.getJsonBaseColumnData(jsonString, columnNames);
	}

	/**
	 * 取得 MyData jsonString merge 欄位值
	 * 
	 * @param jsonStrig
	 * @param columnNames
	 * @return
	 * @throws Exception
	 */
	protected Map<String, Object> getJsonBaseColumnData(String jsonString, List<ColumnModel> columnNames) throws Exception {
		Object object = this.getBaseJsanMapData(jsonString);
		return getBaseColumnData(object, columnNames);
	}

	private Map<String, Object> getBaseColumnData(Object object, List<ColumnModel> columnNames) throws Exception {
		if (object instanceof List) {
			return this.getBaseListColumnData((List<Map<String, Object>>) object, columnNames);
		} else {
			return this.getBaseColumnData((Map<String, Object>) object, columnNames);
		}
	}

	private Object getBaseColumnData(Object object, ColumnModel columnName) throws Exception {
		if (object instanceof List) {
			return this.getBaseListColumnData((List<Map<String, Object>>) object, columnName);
		} else {
			return this.getBaseColumnData((Map<String, Object>) object, columnName);
		}
	}

	protected Map<String, Object> getBaseFileColumnData(Map<String, Object> dataMap, List<ColumnModel> columnNames) throws Exception {
		try {
			Map<String, Object> rtnMap = new HashMap<String, Object>();

			if (ObjectUtils.isNotEmpty(columnNames)) {
				for (ColumnModel columnName : columnNames.stream().distinct().collect(Collectors.toList())) {
					String fileName = StringUtils.defaultString(columnName.getFileName(), DEFAULT_JSON_FILE_NAME);
					Object data = null;
					if (dataMap.containsKey(fileName)) {
						data = dataMap.get(fileName);
						rtnMap.put(columnName.getColumnId(), this.getBaseColumnData(data, columnName));
					} else {
						rtnMap.put(columnName.getColumnId(), this.getColumnData((Map<String, Object>) data, columnName));
					}
				}
			}

			rtnMap.put(PDF_FILE_TAG, getPdfFile());
			rtnMap.put(CSV_FILE_TAG, getCsvFile());
			return rtnMap;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * 取得 MyData dataMap 欄位清單值
	 * 
	 * @param dataMap
	 * @param columnNames
	 * @return
	 * @throws Exception
	 */
	protected Map<String, Object> getBaseColumnData(Map<String, Object> data, List<ColumnModel> columnNames) throws Exception {

		try {
			Map<String, Object> rtnMap = new HashMap<String, Object>();

			if (ObjectUtils.isNotEmpty(columnNames)) {
				for (ColumnModel columnName : columnNames.stream().distinct().collect(Collectors.toList())) {
					rtnMap.put(columnName.getColumnId(), this.getBaseColumnData(data, columnName));
				}
			}

			rtnMap.put(PDF_FILE_TAG, getPdfFile());
			rtnMap.put(CSV_FILE_TAG, getCsvFile());
			return rtnMap;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * 取得 MyData jsonString merge 欄位值
	 * 
	 * @param columnNames
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getJsonListColumnData(List<ColumnModel> columnNames) throws Exception {
		return this.getJsonBaseListColumnData(this.getJsonString(), columnNames);
	}

	/**
	 * 取得 MyData jsonString merge 欄位值
	 * 
	 * @param columnNames
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getJsonListColumnData(List<ColumnModel> columnNames, String fileName) throws Exception {
		return this.getJsonBaseListColumnData(this.getJsonString(fileName), columnNames);
	}

	/**
	 * 取得 MyData jsonString 為 Array 欄位清單值
	 * 
	 * @param jsonStrig   for List
	 * @param columnNames
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getJsonListColumnData(String jsonStrig, List<ColumnModel> columnNames) throws Exception {
		return this.getJsonBaseListColumnData(jsonStrig, columnNames);
	}

	/**
	 * 取得 MyData merge 欄位值
	 * 
	 * @param data
	 * @param columnNames
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getListColumnData(List<Map<String, Object>> data, List<ColumnModel> columnNames) throws Exception {
		return this.getBaseListColumnData(data, columnNames);
	}

	/**
	 * 取得 MyData jsonString 為 Array 欄位清單值
	 * 
	 * @param jsonStrig
	 * @param columnNames
	 * @return
	 * @throws Exception
	 */
	protected Map<String, Object> getJsonBaseListColumnData(String jsonStrig, List<ColumnModel> columnNames) throws Exception {
		List<Map<String, Object>> data = this.getJsanListMapData(jsonStrig);
		return this.getBaseListColumnData(data, columnNames);
	}

	/**
	 * 取得 MyData merge 欄位值
	 * 
	 * @param data
	 * @param columnNames
	 * @return
	 * @throws Exception
	 */
	protected Map<String, Object> getBaseListColumnData(List<Map<String, Object>> data, List<ColumnModel> columnNames) throws Exception {
		try {
			Map<String, Object> rtnMap = new HashMap<String, Object>();
			for (ColumnModel columnName : columnNames) {
				rtnMap.put(columnName.getColumnId(), this.getBaseListColumnData(data, columnName));
			}

			rtnMap.put(PDF_FILE_TAG, getPdfFile());
			rtnMap.put(CSV_FILE_TAG, getCsvFile());
			return rtnMap;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage(), e);
			throw e;
		}

	}

	/**
	 * 取得 MyData merge DataSource
	 * 
	 * @param columnNames
	 * @return
	 */
	public Map<String, Object> getDataSource(List<ColumnModel> columnNames) {
		return getDataSource(this.myDataFile, columnNames);
	}

	/**
	 * 取得 MyData merge DataSource
	 * 
	 * @param myDataFile
	 * @param columnNames
	 * @return
	 */
	private Map<String, Object> getDataSource(ServiceDataBO myDataFile, List<ColumnModel> columnNames) {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		if (ObjectUtils.isNotEmpty(myDataFile) && ObjectUtils.isNotEmpty(columnNames)) {
			if (ObjectUtils.isNotEmpty(myDataFile.getCvsFile())) {
				for (ServiceDataFileBO fileBO : myDataFile.getCvsFile()) {
					if (StringUtils.isNoneBlank(fileBO.getFileName())) {
						List<ColumnModel> fileColumnNames = columnNames.stream().filter(x -> {
							String fileName = StringUtils.defaultIfEmpty(x.getFileName(), DEFAULT_CSV_FILE_NAME);
							if (fileBO.getFileName().indexOf(fileName) > 0) {
								return true;
							}
							return false;
						}).collect(Collectors.toList());

						if (ObjectUtils.isNotEmpty(fileColumnNames) && fileColumnNames.size() > 0) {
							ColumnModel model = fileColumnNames.get(0);
							Object object = ExcelCSV.getFieldDataByBase64Str(fileBO.getFileData(), model.getHeaderRow(), model.getDataStartRow());
							rtnMap.put(model.getFileName(), object);
						}
					}
				}
			}

			if (ObjectUtils.isNotEmpty(myDataFile.getJsonData())) {
				for (ServiceDataFileBO fileBO : myDataFile.getJsonData()) {
					String fileName = "JSON";
					if (StringUtils.isNoneBlank(fileBO.getFileName())) {
						List<ColumnModel> fileColumnNames = columnNames.stream().filter(x -> StringUtils.defaultIfEmpty(x.getFileName(), DEFAULT_JSON_FILE_NAME).equalsIgnoreCase(fileBO.getFileName()))
								.collect(Collectors.toList());
						if (ObjectUtils.isNotEmpty(fileColumnNames) && fileColumnNames.size() > 0) {
							fileName = fileColumnNames.get(0).getFileName();
						}
					}

					try {
						Object object = getJsanMapData(fileBO.getFileData());
						rtnMap.put(fileName, object);
					} catch (Exception e) {

					}

				}
			}
		}

		return rtnMap;
	}

	/**
	 * jsonStrig 轉 Map
	 * 
	 * @param jsonStrig
	 * @return
	 * @throws Exception
	 */
	public Object getJsanMapData(String jsonStrig) throws Exception {
		return this.getBaseJsanMapData(jsonStrig);
	}

	/**
	 * jsonStrig 轉 Map
	 * 
	 * @param jsonStrig
	 * @return
	 * @throws Exception
	 */
	protected Object getBaseJsanMapData(String jsonStrig) throws Exception {
		try {
			return JsonUtil.getObject(jsonStrig, Object.class);
		} catch (Exception e) {
			return JsonUtil.getObject(jsonStrig, List.class);
		}

	}

	/**
	 * jsonStrig 轉 List Map
	 * 
	 * @param jsonStrig
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getJsanListMapData(String jsonStrig) throws Exception {
		return this.getBaseJsanListMapData(jsonStrig);
	}

	/**
	 * jsonStrig 轉 List Map
	 * 
	 * @param jsonStrig
	 * @return
	 * @throws Exception
	 */
	protected List<Map<String, Object>> getBaseJsanListMapData(String jsonStrig) throws Exception {
		return (List<Map<String, Object>>) JsonUtil.getObject(jsonStrig, List.class);
	}

	/**
	 * 取得 map 欄位資料
	 * 
	 * @param data
	 * @param columnName
	 * @return
	 * @throws Exception
	 */
	public Object getColumnData(Map<String, Object> data, ColumnModel columnName) throws Exception {
		return this.getBaseColumnData(data, columnName);
	}

	/**
	 * 取得 map 欄位資料
	 * 
	 * @param data
	 * @param columnName
	 * @return
	 * @throws Exception
	 */
	protected Object getBaseColumnData(Map<String, Object> data, ColumnModel columnName) throws Exception {
		return getBaseColumnData(data, columnName, 0);
	}

	/**
	 * 取得 map 欄位資料
	 * 
	 * @param data
	 * @param columnName
	 * @param idx
	 * @return
	 * @throws Exception
	 */
	protected Object getBaseColumnData(Map<String, Object> data, ColumnModel columnName, int idx) throws Exception {

		String[] routings = columnName.getMyDataColumn().split("\\.");

		String myDataColumn = routings[routings.length - 1];

		Map<String, Object> columnData = data;
		for (int i = idx; i < routings.length; i++) {
			String[] keys = routings[i].split(":");
			String key = keys[0];
			if (key.equalsIgnoreCase(myDataColumn))
				break;
			if (ObjectUtils.isEmpty(data) || ObjectUtils.isEmpty(columnData)) {
				return geBaseControlColumnData(data, columnData, myDataColumn, columnName);
			}

			Object object = columnData.get(key);
			try {

				if (object instanceof List) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) object;
					if (keys.length > 1) {
						columnData = getColumnDataByCondition(list, keys[1]);
					} else {
						return getBaseListColumnData(list, columnName, i);
					}
				} else {
					columnData = (Map<String, Object>) object;
				}

			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.error("key:" + key + "\n " + e.getMessage(), e);
			}

		}

		return geControlColumnData(data, columnData, myDataColumn, columnName);
	}

	/**
	 * 取得 map 欄位資料 Where condition
	 * 
	 * @param listData
	 * @param conditionString
	 * @return
	 */
	private Map<String, Object> getColumnDataByCondition(List<Map<String, Object>> listData, String conditionString) {

		int index = 0;
		List<Map<String, Object>> conditionMaps = listData;
		String[] conditionStr = conditionString.split("&");
		for (int idx = 0; idx < conditionStr.length; idx++) {
			String condition = conditionStr[idx];
			String[] whereStr = condition.split("=");
			if (whereStr.length == 2) {
				if (StringUtils.isNoneBlank(whereStr[0])) {
					if (whereStr[0].equalsIgnoreCase("index")) {
						index = NumberUtils.toInt(whereStr[1], 0);
					} else {
						conditionMaps = conditionMaps.stream().filter(x -> {
							if (x.containsKey(whereStr[0])) {
								String value = StringUtils.defaultString((String) x.get(whereStr[0]), "");
								return value.equalsIgnoreCase(whereStr[1]);
							} else {
								return false;
							}
						}).collect(Collectors.toList());
					}
				}
			}
		}

		if (conditionMaps.size() > index) {
			return conditionMaps.get(index);
		} else {
			return null;
		}

//		Map<String, Object> columnData = null;
//		for (Map<String, Object> map : listData) {
//			String[] conditionStr = conditionString.split("&");
//			for (int idx = 0; idx < conditionStr.length; idx++) {
//				String condition = conditionStr[idx];
//				String[] whereStr = condition.split("=");
//				if (whereStr.length == 2) {
//					if (StringUtils.isNoneBlank(whereStr[0])) {
//						if (!map.containsKey(whereStr[0])) {
//							if (whereStr[0].equalsIgnoreCase("index")) {
//								int index = NumberUtils.toInt(whereStr[1], 0);
//								if (conditionStr.length == 1) {
//									if (listData.size() > index) {
//										return listData.get(index);
//									}
//								} 
//							}
//						}
//						break;
//					} else {
//						String value = StringUtils.defaultString((String) map.get(whereStr[0]), "");
//						if (!value.equalsIgnoreCase(whereStr[1])) {
//							break;
//						}
//					}
//				}
//			}
//			columnData = map;
//		}
//	}

//		return columnData;

	}

	/**
	 * 取得 List map 欄位資料
	 * 
	 * @param data
	 * @param columnName
	 * @return
	 * @throws Exception
	 */
	public Object getListColumnData(List<Map<String, Object>> dataMap, ColumnModel columnName) throws Exception {
		return this.getBaseListColumnData(dataMap, columnName);
	}

	/**
	 * 取得 List map 欄位資料
	 * 
	 * @param data
	 * @param columnName
	 * @return
	 * @throws Exception
	 */
	protected Object getBaseListColumnData(List<Map<String, Object>> listData, ColumnModel columnName) throws Exception {
		return getBaseListColumnData(listData, columnName, 0);
	}

	/**
	 * 取得 List map 欄位資料
	 * 
	 * @param listData
	 * @param columnName
	 * @param idx
	 * @return
	 * @throws Exception
	 */
	protected Object getBaseListColumnData(List<Map<String, Object>> listData, ColumnModel columnName, Integer idx) throws Exception {
		String[] routings = columnName.getMyDataColumn().split("\\.");

		List<Object> rtnList = new ArrayList<Object>();

		// Where Condition
		String[] keys = routings.length > idx ? routings[idx].split(":") : null;

		if (ObjectUtils.isNotEmpty(keys) && keys.length > 1 && idx == 0) {
			Map<String, Object> data = getColumnDataByCondition(listData, keys[1]);
			return this.getBaseColumnData(data, columnName, idx + 1);
		} else {
			for (Map<String, Object> map : listData) {
				Map<String, Object> data = map;
				idx = idx + 1;
				Object itemObject = getBaseColumnData(data, columnName, idx);
				rtnList.add(itemObject);
			}

			try {
				return rtnList.stream().map(x -> {
					if (ObjectUtils.isNotEmpty(x)) {
						return x.toString();
					} else {
						return "";
					}
				}).collect(Collectors.joining(","));
			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.error("[MyDataId=" + getMyDataId() + ", MyDataColumn=" + columnName.getMyDataColumn() + "]" + e.getMessage(), e);
				return "";
			}

		}

	}

	protected Object geBaseControlColumnData(Map<String, Object> originalData, Map<String, Object> currentData, String columnName, ColumnModel columnModel) {
		return geControlColumnData(originalData, currentData, columnName, columnModel);
	}

	/**
	 * 控制取的 MyData 欄位資料
	 * 
	 * @param originalData
	 * @param currentData
	 * @param columnName
	 * @return
	 */
	private Object geControlColumnData(Map<String, Object> originalData, Map<String, Object> currentData, String columnName, ColumnModel columnModel) {

		Object data = getCurrentColumnData(originalData, currentData, columnName, columnModel);
		if (data == null) {
			try {
				if (columnName.equalsIgnoreCase(DEFAULT_PDF_FILE_NAME)) {
					return this.getPdfFileName();
				}

				if (columnName.equalsIgnoreCase(DEFAULT_CSV_FILE_NAME)) {
					return this.getCsvFileName();
				}
			} catch (Exception e) {
				return null;
			}

			if (ObjectUtils.isNotEmpty(currentData)) {
				Object value = currentData.get(columnName);
				return convertColumnData(value, columnModel);
			} else {
				return null;
			}

		} else {

			return data;
		}

	}

	/**
	 * 客製化欄位處理
	 * 
	 * @param originalData
	 * @param currentData
	 * @param columnName
	 * @return
	 */
	public abstract Object getCurrentColumnData(Map<String, Object> originalData, Map<String, Object> currentData, String columnName, ColumnModel columnModel);

	/**
	 * Convert Column Data
	 * 
	 * @param value
	 * @param columnModel
	 * @return
	 */
	protected Object convertColumnData(Object value, ColumnModel columnModel) {

		try {
			switch (columnModel.getType()) {
			case "D":
				return convertDate((String) value, columnModel);
			case "S":
				return convertString((String) value, columnModel);
			case "N":
				return convertNumber((String) value, columnModel);
			case "9":
				return convertSpecial((String) value, columnModel);
			default:
				return (String) value;
			}
		} catch (Exception e) {
			return value;
		}

	}

	/**
	 * Convert Date Type Column Data
	 * 
	 * @param data
	 * @param columnModel
	 * @return
	 */
	protected String convertDate(String data, ColumnModel columnModel) {
		return convertTWDate(data);
	}

	/**
	 * Convert String Type Column Data
	 * 
	 * @param data
	 * @param columnModel
	 * @return
	 */
	protected String convertString(String data, ColumnModel columnModel) {
		return data;
	}

	/**
	 * Convert Number Type Column Data
	 * 
	 * @param data
	 * @param columnModel
	 * @return
	 */
	protected String convertNumber(String data, ColumnModel columnModel) {
		return data;
	}

	/**
	 * 客製化 Convert Column Data
	 * 
	 * @param data
	 * @param columnModel
	 * @return
	 */
	protected String convertSpecial(String data, ColumnModel columnModel) {
		return data;
	}

	/**
	 * Convert TW Date
	 * 
	 * @param data
	 * @return
	 */
	public String convertTWDate(String data) {
		try {
			return DateUtil.convertTWDate(data, DateUtil.PATTEN_YEAR_MONTH_DAY_NO_HYPHEN_TW, DateUtil.PATTEN_YEAR_MONTH_DAY_SLASH);
		} catch (ParseException e) {
			return data;
		}
	}

	/**
	 * Convert List to String
	 * 
	 * @param data
	 * @return
	 */
	public String convertList(List<String> data) {
		return convertList(data, ",");
	}

	public String convertList(List<String> data, CharSequence delimiter) {
		if (ObjectUtils.isNotEmpty(data)) {
			return data.stream().map(x -> x).collect(Collectors.joining(delimiter));
		}
		return "";
	}

	/**
	 * 取得 Json String
	 * 
	 * @return
	 */
	public String getJsonString() {

		if (ObjectUtils.isNotEmpty(this.myDataFile)) {
			if (this.myDataFile.getJsonData().size() > 0) {
				return this.myDataFile.getJsonData().get(0).getFileData();
			}
		}

		return "";
	}

	/**
	 * 取得 PDF 檔名
	 * 
	 * @return
	 */
	private String getPdfFileName() {
		if (ObjectUtils.isNotEmpty(this.myDataFile)) {
			return this.myDataFile.getPdfFile().stream().map(x -> x.getFileName()).collect(Collectors.joining(","));
		}

		return "";
	}

	/**
	 * 取得 PDF ServiceDataFileBO
	 * 
	 * @return
	 */
	private List<ServiceDataFileBO> getPdfFile() {
		return ObjectUtils.isNotEmpty(this.myDataFile) ? this.myDataFile.getPdfFile() : null;
	}

	/**
	 * 取得 CSV 檔名
	 * 
	 * @return
	 */
	private String getCsvFileName() {
		if (ObjectUtils.isNotEmpty(this.myDataFile)) {
			return this.myDataFile.getCvsFile().stream().map(x -> x.getFileName()).collect(Collectors.joining(","));

		}

		return "";
	}

	/**
	 * 取得 CSV ServiceDataFileBO
	 * 
	 * @return
	 */
	private List<ServiceDataFileBO> getCsvFile() {
		return ObjectUtils.isNotEmpty(this.myDataFile) ? this.myDataFile.getCvsFile() : null;
	}

	/**
	 * jsonData 轉 JsonString
	 * 
	 * @param jsonData
	 * @return
	 */
	public String getJsonString(String fileName) {
		if (ObjectUtils.isNotEmpty(this.myDataFile)) {
			Optional<ServiceDataFileBO> data = this.myDataFile.getJsonData().stream().filter(x -> x.getFileName().equalsIgnoreCase(fileName)).findAny();
			return data.isPresent() ? data.get().getFileData() : "";
		}

		return "";
	}

	/**
	 * Mock MyData JsonString
	 * 
	 * @return
	 */
	public abstract String mockMyDataJsonString();

	/**
	 * Mock MyData PdfFile
	 * 
	 * @return
	 */
	public abstract String mockMyDataPdfFile();

	/**
	 * Mock MyData CsvFile
	 * 
	 * @return
	 */
	public abstract String mockMyDataCsvFile();

}
