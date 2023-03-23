package gov.kcg.kgo.service.impl.helper;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.kcg.kgo.dto.KgoZipF3AreaDto;
import gov.kcg.kgo.enums.backend.PublishStateEnum;
import gov.kcg.kgo.enums.common.ColumnTypeEnum;
import gov.kcg.kgo.enums.common.ComboBoxStatusEnum;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.model.KgoHoliday;
import gov.kcg.kgo.repository.KgoHolidayRepository;
import gov.kcg.kgo.repository.KgoZipRepository;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.SpringUtil;
import gov.kcg.kgo.viewModel.compoent.checkBox.CheckBox;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import gov.kcg.kgo.viewModel.compoent.radioBox.RadioBox;
import gov.kcg.kgo.viewModel.compoent.radioBox.bean.RadioBoxItem;

public class KgoServiceHelper {

	private static final String ENTITY_PK_NAME = "id";

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(KgoServiceHelper.class);

	/**
	 * Instantiates a new kgo service helper.
	 */
	public KgoServiceHelper() {

	}

	/**
	 * -------- 底層 ----------
	 * 
	 * Generating ComboBox with given data list and following settings
	 * 
	 * @params list → Data of label & value of comboBox
	 * @params labelField → Lable column name ( value as comboBox label )
	 * @params valueField → Value column name ( value as comboBox value )
	 * @params defaultValue → for setting default selected option
	 * @params comboBoxStatus → refer to ComboBoxStatus enum object
	 * @params defaultOption → has default option ? (ex: "-----請選擇-----")
	 * @return ComboBox
	 * @throws KgoApiException
	 */
	public ComboBox getComboBox(List<?> list, String labelField, String valueField, Object defaultValue, Integer comboBoxStatus, boolean hasDefaultOption) {
		return getComboBox(list, labelField, valueField, null, defaultValue, comboBoxStatus, hasDefaultOption);

	}

	/**
	 *
	 * @param list
	 * @param labelField
	 * @param valueField
	 * @param groupKeyField
	 * @param defaultValue
	 * @param comboBoxStatus
	 * @param hasDefaultOption
	 * @return ComboBox
	 */
	public ComboBox getComboBoxMyDataExtraInfo(List<?> list, String labelField, String valueField, String groupKeyField, Object defaultValue, Integer comboBoxStatus, boolean hasDefaultOption) {
		ComboBox groupBox = new ComboBox();
		// 是否要加入預設選項
		if (hasDefaultOption) {
			groupBox.addDefaultComboItem();
		}
		// 確認是否有提供資料
		if (ObjectUtils.isNotEmpty(list)) {
			list.forEach(l -> {
				try {
					String labelStr = checkClassFieldIsExist(l, labelField);
					String valueStr = checkClassFieldIsExist(l, valueField);
					String typeStr = checkClassFieldIsExistNullString(l, InternetApplyServiceHelper.MYDATA_COLUMN_COMBOBOX_TYPE_FIELD);
					String isNumOrDateStr = checkClassFieldIsExistNullString(l, InternetApplyServiceHelper.MYDATA_COLUMN_COMBOBOX_ISNUMORDATE_FIELD);
					String isHaveValueStr = checkClassFieldIsExistNullString(l, InternetApplyServiceHelper.MYDATA_COLUMN_COMBOBOX_ISHAVEVALUE_FIELD);
					String restrictedSourceValueStr = checkClassFieldIsExistNullString(l, InternetApplyServiceHelper.MYDATA_COLUMN_COMBOBOX_RESTRICTEDSOURCEVALUE_FIELD);
					String restrictedLengthStr = checkClassFieldIsExistNullString(l, InternetApplyServiceHelper.MYDATA_COLUMN_COMBOBOX_RESTRICTEDLENGTH_FIELD);
					String isNotNullStr = checkClassFieldIsExistNullString(l, InternetApplyServiceHelper.MYDATA_COLUMN_COMBOBOX_ISNOTNULL_FIELD);

					String groupKeyStr = StringUtils.isNotBlank(groupKeyField) ? checkClassFieldIsExist(l, groupKeyField) : StringUtils.EMPTY;

					if (comboBoxStatus.equals(ComboBoxStatusEnum.ALL.getCode())) {
						// 顯示所有下拉式選項
						boolean defaultValueFlag = false;
						if (StringUtils.equalsIgnoreCase(valueStr, String.valueOf(defaultValue))) {
							defaultValueFlag = true;
						}

						if (StringUtils.isBlank(groupKeyField)) {
							groupBox.add(labelStr, valueStr, defaultValueFlag, typeStr, isNumOrDateStr, isHaveValueStr, restrictedSourceValueStr, restrictedLengthStr, isNotNullStr);
						} else {
							groupBox.add(labelStr, valueStr, groupKeyStr, defaultValueFlag, typeStr, isNumOrDateStr, isHaveValueStr, restrictedSourceValueStr, restrictedLengthStr, isNotNullStr);
						}

					} else {
						// 顯示單一下拉式選項
						if (StringUtils.equalsIgnoreCase(valueStr, String.valueOf(defaultValue))) {
							if (StringUtils.isBlank(groupKeyField)) {
								groupBox.add(labelStr, valueStr, true, typeStr, isNumOrDateStr, isHaveValueStr, restrictedSourceValueStr, restrictedLengthStr, isNotNullStr);
							} else {
								groupBox.add(labelStr, valueStr, groupKeyStr, true, typeStr, isNumOrDateStr, isHaveValueStr, restrictedSourceValueStr, restrictedLengthStr, isNotNullStr);
							}

							groupBox.setIsShow(false);
							groupBox.setIsDisabled(true);
						}
					}
				} catch (SecurityException | IllegalArgumentException e) {
					throw new KgoApiException("getComboBox error " + e.getMessage(), e);
				}
			});
		}
		return groupBox;
	}

	/**
	 * 
	 * Generating ComboBox with given data list and following settings
	 * 
	 * @params list → Data of label & value of comboBox
	 * @params labelField → Lable column name ( value as comboBox label )
	 * @params valueField → Value column name ( value as comboBox value )
	 * @params groupKeyField → Value column name ( groupKey as comboBox groupKey )
	 * @params defaultValue → for setting default selected option
	 * @params comboBoxStatus → refer to ComboBoxStatus enum object
	 * @params defaultOption → has default option ? (ex: "-----請選擇-----")
	 * @return ComboBox
	 * @throws KgoApiException
	 */
	public ComboBox getComboBox(List<?> list, String labelField, String valueField, String groupKeyField, Object defaultValue, Integer comboBoxStatus, boolean hasDefaultOption) {
		ComboBox groupBox = new ComboBox();
		// 是否要加入預設選項
		if (hasDefaultOption) {
			groupBox.addDefaultComboItem();
		}
		// 確認是否有提供資料
		if (ObjectUtils.isNotEmpty(list)) {
			list.forEach(l -> {
				try {
					String labelStr = checkClassFieldIsExist(l, labelField);
					String valueStr = checkClassFieldIsExist(l, valueField);
					String groupKeyStr = StringUtils.isNotBlank(groupKeyField) ? checkClassFieldIsExist(l, groupKeyField) : StringUtils.EMPTY;

					if (comboBoxStatus.equals(ComboBoxStatusEnum.ALL.getCode())) {
						// 顯示所有下拉式選項
						boolean defaultValueFlag = false;
						if (StringUtils.equalsIgnoreCase(valueStr, String.valueOf(defaultValue))) {
							defaultValueFlag = true;
						}

						if (StringUtils.isBlank(groupKeyField)) {
							groupBox.add(labelStr, valueStr, defaultValueFlag);
						} else {
							groupBox.add(labelStr, valueStr, groupKeyStr, defaultValueFlag);
						}

					} else {
						// 顯示單一下拉式選項
						if (StringUtils.equalsIgnoreCase(valueStr, String.valueOf(defaultValue))) {
							if (StringUtils.isBlank(groupKeyField)) {
								groupBox.add(labelStr, valueStr, true);
							} else {
								groupBox.add(labelStr, valueStr, groupKeyStr, true);
							}

							groupBox.setIsShow(false);
							groupBox.setIsDisabled(true);
						}
					}
				} catch (SecurityException | IllegalArgumentException e) {
					throw new KgoApiException("getComboBox error " + e.getMessage(), e);
				}
			});
		}
		return groupBox;
	}

	/**
	 * 根據傳入的 data 來產生相對應的 CheckBox List
	 * 
	 * @param list
	 * @param labelField
	 * @param valueField
	 * @return
	 */
	public List<CheckBox> getCheckBox(List<?> list, String labelField, String valueField) {
		ComboBox groupBox = new ComboBox();
		List<CheckBox> checkBoxList = null;
		// 確認是否有提供資料
		if (ObjectUtils.isNotEmpty(list)) {
			checkBoxList = list.stream().map(l -> {
				String sLabel = checkClassFieldIsExist(l, labelField);
				String sValue = checkClassFieldIsExist(l, valueField);
				return new CheckBox(sLabel, sValue);
			}).collect(Collectors.toList());
		}
		return checkBoxList;
	}

	/**
	 * 取得 CheckBox 元件
	 * 
	 * @param sLabel 顯示字串
	 * @param sValue 顯示值
	 * @return
	 */
	public CheckBox getCheckBox(String sLabel, String sValue) {
		return new CheckBox(sLabel, sValue);
	}

	/**
	 * 取得 CheckBox 元件
	 * 
	 * @param sLabel  顯示字串
	 * @param sValue  顯示值
	 * @param isCheck 是否設定成已選擇
	 * @return
	 */
	public CheckBox getCheckBox(String sLabel, String sValue, boolean isCheck) {
		return new CheckBox(sLabel, sValue, isCheck);
	}

	/**
	 * 取得 RadioBox 元件
	 * 
	 * @param list
	 * @param labelField
	 * @param valueField
	 * @return
	 */
	public RadioBox getRadioBox(List<?> list, String labelField, String valueField) {
		RadioBox radioBox = new RadioBox();
		list.forEach(l -> {
			try {
				String sLabel = checkClassFieldIsExist(l, labelField);
				String sValue = checkClassFieldIsExist(l, valueField);
				RadioBoxItem item = new RadioBoxItem(sLabel, sValue);
				radioBox.add(item);
			} catch (SecurityException | IllegalArgumentException e) {
				throw new KgoApiException("getRadioBoxItem error " + e.getMessage(), e);
			}
		});
		return radioBox;
	}

	/**
	 * check class object has specific field ?
	 *
	 * if class object has PK column then looking deeper
	 *
	 * @param object
	 * @param fieldName
	 * @return String (null = not exits, not null = exist)
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private String checkClassFieldIsExistNullString(Object object, String fieldName) {

		try {
			Field f = getClassField(object, fieldName);
			if (ObjectUtils.isEmpty(f)) {
				Field fPK = getClassField(object, ENTITY_PK_NAME);
				if (ObjectUtils.isEmpty(fPK)) {
					return StringUtils.EMPTY;
				} else {
					fPK.setAccessible(true);
					return this.checkClassFieldIsExist(fPK.get(object), fieldName);
				}
			}
			f.setAccessible(true);
			Object o = f.get(object);
			if (null == o) {
				return (String) o;
			} else {
				return String.valueOf(o);
			}
		} catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			throw new KgoApiException("checkClassFieldIsExist error " + e.getMessage(), e);
		}

	}

	/**
	 * check class object has specific field ?
	 * 
	 * if class object has PK column then looking deeper
	 * 
	 * @param object
	 * @param fieldName
	 * @return String (null = not exits, not null = exist)
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private String checkClassFieldIsExist(Object object, String fieldName) {

		try {
			Field f = getClassField(object, fieldName);
			if (ObjectUtils.isEmpty(f)) {
				Field fPK = getClassField(object, ENTITY_PK_NAME);
				if (ObjectUtils.isEmpty(fPK)) {
					return StringUtils.EMPTY;
				} else {
					fPK.setAccessible(true);
					return this.checkClassFieldIsExist(fPK.get(object), fieldName);
				}
			}
			f.setAccessible(true);
			return String.valueOf(f.get(object));
		} catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			throw new KgoApiException("checkClassFieldIsExist error " + e.getMessage(), e);
		}

	}

	/**
	 * check field name is exist in Object, if size = 0 then not exist
	 * 
	 * @param object
	 * @param fieldName
	 * @return Field
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	private Field getClassField(Object object, String fieldName) throws SecurityException, NoSuchFieldException {
		List<Field> fieldSet = Arrays.asList(object.getClass().getDeclaredFields()).stream().filter(Field -> StringUtils.equalsIgnoreCase(fieldName, Field.getName())).collect(Collectors.toList());
		return fieldSet.size() > 0 ? fieldSet.get(0) : null;
	}

	/**
	 * 取得KGO_CASE_DETAIL columnValue 對應到 KGO_CASESET 的對應資料轉換.
	 *
	 * @param setColumnValue    the set column value
	 * @param detailColumnValue the detail column value
	 * @return the column mapping value
	 */
	public String getColumnMappingValue(String setColumnValue, String detailColumnValue) {
		String mappingValue = "";
		// fix CHECKBOX 多選項mapping
		List<String> list = new ArrayList<String>();
		if (StringUtils.contains(setColumnValue, ",") && StringUtils.contains(setColumnValue, "-")) {
			if (StringUtils.contains(detailColumnValue, ",")) {
				for (String detailColumnValues : detailColumnValue.split(",")) {
					Map<Object, Object> columnValueMap = Arrays.asList(setColumnValue.split(",")).stream().map(s -> s.split("-")).collect(Collectors.toMap(a -> a[0], a -> a[1]));
					list.add(StringUtils.defaultString((String) columnValueMap.get(detailColumnValues), ""));
				}
				mappingValue = StringUtils.join(list.toArray(), ", ");
			} else {
				Map<Object, Object> columnValueMap = Arrays.asList(setColumnValue.split(",")).stream().map(s -> s.split("-")).collect(Collectors.toMap(a -> a[0], a -> a[1]));
				mappingValue = StringUtils.defaultString((String) columnValueMap.get(detailColumnValue), "");
			}
		} else {
			LOGGER.info("setColumnValue = " + setColumnValue + " detailColumnValue = " + detailColumnValue);
			mappingValue = detailColumnValue;
		}
		return mappingValue;
	}

	public String getColumnMappingPDFValue(ColumnTypeEnum columnType, String setColumnValue, String detailColumnValue) {
		String mappingValue = "";
		// fix CHECKBOX 多選項mapping
		List<String> list = new ArrayList<String>();
		detailColumnValue = StringUtils.defaultString(detailColumnValue, "");
		if (StringUtils.contains(setColumnValue, ",") && StringUtils.contains(setColumnValue, "-")) {
			if (columnType == ColumnTypeEnum.CHECKBOX || columnType == ColumnTypeEnum.RADIO || columnType == ColumnTypeEnum.S_CHECKBOX || columnType == ColumnTypeEnum.S_RADIO) {
				for (String setValues : Arrays.asList(setColumnValue.split(","))) {
					if (StringUtils.contains(setValues, ",") && StringUtils.contains(setValues, "-")) {
						String setKey = StringUtils.defaultString((String) setValues.split("-")[0]);
						String setVel = StringUtils.defaultString((String) setValues.split("-")[1]);
						Boolean checkBoolean = Arrays.asList(detailColumnValue.split(",")).contains(setKey);
						list.add(getColumnTypeUnicode(columnType, checkBoolean) + setVel);
					}
				}

				mappingValue = StringUtils.join(list.toArray(), "  ");
			} else {
				Map<Object, Object> columnValueMap = Arrays.asList(setColumnValue.split(",")).stream().map(s -> s.split("-")).collect(Collectors.toMap(a -> a[0], a -> a[1]));
				mappingValue = StringUtils.defaultString((String) columnValueMap.get(detailColumnValue), "");
				mappingValue = getColumnTypeUnicode(columnType, StringUtils.isBlank(mappingValue)) + mappingValue;
			}
		} else {
			LOGGER.info("setColumnValue = " + setColumnValue + " detailColumnValue = " + detailColumnValue);
			mappingValue = getColumnTypeUnicode(columnType, detailColumnValue.equalsIgnoreCase("Y"));

			if (!(columnType == ColumnTypeEnum.S_CHECKBOX || columnType == ColumnTypeEnum.S_RADIO || columnType == ColumnTypeEnum.M)) {
				mappingValue = mappingValue + detailColumnValue;
			}

		}
		return mappingValue;
	}

	private String getColumnTypeUnicode(ColumnTypeEnum columnType, Boolean checkBoolean) {

		// return " 25c6 \u25c6" + " 2714 \u2714" + " 2715 \u2715" + " 2718 \u2718" + "
		// 2611 \u2611" + " 2610 \u2610" + " 25c9 \u25c9" + " 2299 \u2299" + " 25ce
		// \u25ce ";
		if (columnType == ColumnTypeEnum.CHECKBOX || columnType == ColumnTypeEnum.S_CHECKBOX) {
			return checkBoolean ? "\u2611" : "\u2610";
		}
		if (columnType == ColumnTypeEnum.RADIO || columnType == ColumnTypeEnum.S_RADIO) {
			// return checkBoolean ? "\u25c9" : "\u25ce";
			return checkBoolean ? "\u25c9" : "\u25cb";// u25ef
		}

		return "";
	}

	/**
	 * 取得限辦日期.
	 *
	 * @param applayDate    申請日期
	 * @param limitedPeriod 限辦期間
	 * @return the deadline date
	 * @throws ParseException the parse exception
	 */
	public Date getDeadlineDate(Date applayDate, Integer limitedPeriod) throws ParseException {
		Date deadlineDate = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(applayDate);
		cal.add(Calendar.DAY_OF_MONTH, limitedPeriod);

		// 取得申請日期 加總天數後日期 的該月最後一天 (抓大區間避免漏抓)
		Date periodDateOfLastMonthDay = DateUtil.getLastMonthDay(cal);

		KgoHolidayRepository kgoHolidayRepository = SpringUtil.getDao(KgoHolidayRepository.class);
		// 查詢區間內所有假日
		List<KgoHoliday> kgoHolidayList = kgoHolidayRepository.findHolidayList(applayDate, periodDateOfLastMonthDay);
		List<Date> holidayList = kgoHolidayList.stream().map(h -> h.getHolidayDate()).collect(Collectors.toList());

		Date tomorrow = DateUtil.getTomorrow(applayDate);
		int delay = 1;
		while (delay <= limitedPeriod) {
			// 當前日期+1,判斷是否是節假日,不是的同時要判斷是否是周末,都不是則 deadlineDate日期+1
			if (!DateUtil.isWeekend(tomorrow) && !DateUtil.isHoliday(tomorrow, holidayList)) {
				delay++;
				deadlineDate = tomorrow;
			} else if (DateUtil.isWeekend(applayDate)) {
				deadlineDate = tomorrow;
			} else if (DateUtil.isHoliday(applayDate, holidayList)) {
				deadlineDate = tomorrow;
			}

			tomorrow = DateUtil.getTomorrow(tomorrow);
		}
		return deadlineDate;
	}

	/**
	 * 由地址取得分處
	 * 
	 * @param address
	 * @return
	 */
	public String getF3Name(String address) {
		KgoZipRepository kgoZipRepository = SpringUtil.getDao(KgoZipRepository.class);
		List<KgoZipF3AreaDto> kgoZipF3AreaDtoList = kgoZipRepository.findByZipF3AreaList();
		return getF3Name(address, kgoZipF3AreaDtoList);
	}

	public String getF3Name(String[] addresss) {
		KgoZipRepository kgoZipRepository = SpringUtil.getDao(KgoZipRepository.class);
		List<KgoZipF3AreaDto> kgoZipF3AreaDtoList = kgoZipRepository.findByZipF3AreaList();
		String rtnF3Name = StringUtils.EMPTY;
		for (String address : addresss) {
			String f3Name = getF3Name(address, kgoZipF3AreaDtoList);
			if (StringUtils.isNoneBlank(f3Name))
				rtnF3Name = f3Name;
		}
		return rtnF3Name;
	}

	/**
	 * 由地址取得分處
	 * 
	 * @param address
	 * @param kgoZipF3AreaDtoList
	 * @return
	 */
	public String getF3Name(String address, List<KgoZipF3AreaDto> kgoZipF3AreaDtoList) {
		if (StringUtils.isBlank(address) || ObjectUtils.isEmpty(kgoZipF3AreaDtoList)) {
			return StringUtils.EMPTY;
		}

		List<String> f3Names = kgoZipF3AreaDtoList.stream().filter(x -> address.contains(x.getZIPName())).map(x -> x.getF3Name()).collect(Collectors.toList());
		return f3Names.size() > 0 ? f3Names.get(f3Names.size() - 1) : StringUtils.EMPTY;
	}

	/**
	 * 是否下架
	 * 
	 * @param status
	 * @return
	 */
	public Boolean getCaseSetStatusOff(String caseSetId, String status) {
		List<String> source = Arrays.asList(new String[] { "S2020122300002", "S2020112500003", "S2020121200004", "S2020112500004" });

		if (StringUtils.equalsIgnoreCase(PublishStateEnum.OFF.getValue(), status) && source.indexOf(caseSetId) == -1) {
			return true;
		}

		return false;
	}

	/**
	 * 是否為測試環境
	 * 
	 * @return
	 */
	public Boolean isDevMode() {
		String value = SpringUtil.getProperty("dev.mode");
		return Boolean.parseBoolean(value);
	}

	// TODO 共通邏輯方法
	private static class Loader {
		/** The Constant instance. */
		private static final KgoServiceHelper instance = new KgoServiceHelper();
	}

	/**
	 * Gets the single instance of KgoServiceHelper.
	 *
	 * @return single instance of KgoServiceHelper
	 */
	public static KgoServiceHelper getInstance() {
		return Loader.instance;
	}

}
