package gov.kcg.kgo.service.impl.helper;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import gov.kcg.kgo.model.*;
import gov.kcg.kgo.repository.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import gov.kcg.kgo.dto.CasesetMydataQueryDto;
import gov.kcg.kgo.dto.MydataQueryDto;
import gov.kcg.kgo.enums.backend.CheckTypeEnum;
import gov.kcg.kgo.enums.backend.MyDataSTypeEnum;
import gov.kcg.kgo.enums.common.ComboBoxStatusEnum;
import gov.kcg.kgo.util.SpringUtil;
import gov.kcg.kgo.viewModel.compoent.checkBox.CheckBox;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import org.springframework.stereotype.Service;

@Service
public class InternetApplyServiceHelper extends KgoServiceHelper {

	private static final String MYDATA_COMBOBOX_VALUE_FIELD = "myDataId";
	private static final String MYDATA_COMBOBOX_LABEL_FIELD = "myDataName";

	private static final String MYDATA_COLUMN_COMBOBOX_LABEL_FIELD = "name";
	private static final String MYDATA_COLUMN_COMBOBOX_VALUE_FIELD = "myDataColumn";
	public static final String MYDATA_COLUMN_COMBOBOX_TYPE_FIELD = "type";
	public static final String MYDATA_COLUMN_COMBOBOX_ISNUMORDATE_FIELD = "isNumOrDate";
	public static final String MYDATA_COLUMN_COMBOBOX_ISHAVEVALUE_FIELD = "isHaveValue";
	public static final String MYDATA_COLUMN_COMBOBOX_RESTRICTEDSOURCEVALUE_FIELD = "restrictedSourceValue";
	public static final String MYDATA_COLUMN_COMBOBOX_RESTRICTEDLENGTH_FIELD = "restrictedLength";
	public static final String MYDATA_COLUMN_COMBOBOX_ISNOTNULL_FIELD = "isNotNull";

	private static final String MYDATA_SERVICE_COMBOBOX_LABEL_FIELD = "serviceName";
	private static final String MYDATA_SERVICE_COMBOBOX_VALUE_FIELD = "clientId";

	private static final String IS_SERVICE_HTML_LABEL = "啟用服務宣告";
	private static final String IS_SERVICE_HTML_VALUE = "T";

	/**
	 * Instantiates a new calss management service helper.
	 */
	public InternetApplyServiceHelper() {

	}

	// TODO 共通邏輯方法
	private static class Loader {
		/** The Constant instance. */
		private static final InternetApplyServiceHelper instance = new InternetApplyServiceHelper();
	}

	/**
	 * Gets the single instance of ClassManagementServiceHelper.
	 *
	 * @return single instance of ClassManagementServiceHelper
	 */
	public static InternetApplyServiceHelper getInstance() {
		return Loader.instance;
	}

	/**
	 * 
	 * @param caseSetId
	 * @return
	 */
	public ComboBox getMyDataCustomComboBox(String caseSetId) {
		KgoCasesetMydataRepository kgoCasesetMydataRepository = SpringUtil.getDao(KgoCasesetMydataRepository.class);
		List<CasesetMydataQueryDto> casesetMydataQueryDtoList = kgoCasesetMydataRepository.countMydataIdAndNameByCaseSetId(caseSetId);
		return getComboBox(casesetMydataQueryDtoList, MYDATA_COMBOBOX_LABEL_FIELD, MYDATA_COMBOBOX_VALUE_FIELD, StringUtils.EMPTY, ComboBoxStatusEnum.ALL.getCode(), false);
	}

	/**
	 * 
	 * @param myDataId
	 * @param defaultValue
	 * @return
	 */
	public ComboBox getMyDataColumnComboBox(String myDataId, String defaultValue) {
		KgoMydataColumnRepository kgoMydataColumnRepository = SpringUtil.getDao(KgoMydataColumnRepository.class);
		List<KgoMydataColumn> list = kgoMydataColumnRepository.findMyDataColumnAndNameByIdMyDataId(myDataId);
		return getComboBoxMyDataExtraInfo(list, MYDATA_COLUMN_COMBOBOX_LABEL_FIELD, MYDATA_COLUMN_COMBOBOX_VALUE_FIELD, null, defaultValue, ComboBoxStatusEnum.ALL.getCode(), false);
	}

	/**
	 * 
	 * @return
	 */
	public ComboBox getMyDataComboBox() {
		KgoMydataRepository kgoMydataRepository = SpringUtil.getDao(KgoMydataRepository.class);
		List<KgoMydata> myDataList = kgoMydataRepository.findAll();
		return getComboBox(myDataList, MYDATA_COMBOBOX_LABEL_FIELD, MYDATA_COMBOBOX_VALUE_FIELD, StringUtils.EMPTY, ComboBoxStatusEnum.ALL.getCode(), false);
	}

	/**
	 * 
	 * @return
	 */
	public ComboBox getMydataResourceComboBoxByInside() {
		return getMydataResourceComboBox(MyDataSTypeEnum.INSIDE.getValue(), StringUtils.EMPTY);
	}

	/**
	 * 
	 * @param clientId
	 * @return
	 */
	public ComboBox getMydataResourceComboBoxByOutside(String clientId) {
		return getMydataResourceComboBox(MyDataSTypeEnum.OUTSIDE.getValue(), clientId);
	}

	/**
	 * 
	 * @param sType
	 * @param clientId
	 * @return
	 */
	private ComboBox getMydataResourceComboBox(String sType, String clientId) {
		KgoMydataResourceRepository kgoMydataResourceRepository = SpringUtil.getDao(KgoMydataResourceRepository.class);
		ComboBox myDataResourceComboBox = null;

		if (sType.equalsIgnoreCase(MyDataSTypeEnum.INSIDE.getValue())) {
			// 對內 - 高市府，直接查 KGO_MYDATA_RESOURCE
			List<KgoMydataResource> list = kgoMydataResourceRepository.findBySType(sType);
			myDataResourceComboBox = getComboBox(list, MYDATA_COMBOBOX_LABEL_FIELD, MYDATA_COMBOBOX_VALUE_FIELD, StringUtils.EMPTY, ComboBoxStatusEnum.ALL.getCode(), false);
		} else {
			// 對外 - 國發會，直接撈 KGO_MYDATA_SERVICE
			List<MydataQueryDto> list = kgoMydataResourceRepository.findByClientId(clientId);
			myDataResourceComboBox = getComboBox(list, MYDATA_COMBOBOX_LABEL_FIELD, MYDATA_COMBOBOX_VALUE_FIELD, StringUtils.EMPTY, ComboBoxStatusEnum.ALL.getCode(), false);
		}

		return myDataResourceComboBox;
	}

	/**
	 * MyData 服務下拉式選單
	 * 
	 * @return
	 */
	public ComboBox getMydataServiceComboBox() {
		KgoMydataServiceRepository kgoMydataServiceRepository = SpringUtil.getDao(KgoMydataServiceRepository.class);
		List<KgoMydataService> list = kgoMydataServiceRepository.findAll().stream().filter(x -> !x.getClientId().equalsIgnoreCase("KAPI.Disability")).collect(Collectors.toList());
		return getComboBox(list, MYDATA_SERVICE_COMBOBOX_LABEL_FIELD, MYDATA_SERVICE_COMBOBOX_VALUE_FIELD, StringUtils.EMPTY, ComboBoxStatusEnum.ALL.getCode(), false);
	}

	/**
	 * 
	 * @param caseSetId
	 * @return
	 */
	public List<CheckBox> getVerifyCheckBox(String caseSetId) {
		KgoCasesetCheckRepository kgoCasesetCheckRepository = SpringUtil.getDao(KgoCasesetCheckRepository.class);
		KgoCasesetRepository kgoCasesetRepository = SpringUtil.getDao(KgoCasesetRepository.class);
		List<KgoCasesetCheck> kgoCasesetCheckList = kgoCasesetCheckRepository.findAllByIdCaseSetId(caseSetId);
		KgoCaseset caseset = kgoCasesetRepository.getById(caseSetId);
		boolean needPay = ObjectUtils.defaultIfNull(caseset.getNeedPay(),false);
		List<String> casesetChecks = kgoCasesetCheckList.stream().map(check -> check == null ? "UNKNOWN_CHECK_TYPE" : check.getId().getCheckType()).collect(Collectors.toList());

		List<CheckBox> identityVerifyCheckBox = new LinkedList<CheckBox>();
		CheckBox checkBox = null;
		for (CheckTypeEnum checkType : CheckTypeEnum.values()) {
			if (casesetChecks.contains(checkType.getValue())) {
				checkBox = getCheckBox(checkType.getLabel(), checkType.getValue(), true);
			} else {
				checkBox = getCheckBox(checkType.getLabel(), checkType.getValue());
			}
			/**
			 * GEO 20221201 CaseSet新增繳費確認欄位 ， 繳費案件不提供免驗證選項
			 */
			if(needPay && checkType.equals(CheckTypeEnum.NAN)){
				continue;
			}
			identityVerifyCheckBox.add(checkBox);
		}

		return identityVerifyCheckBox;
	}

	/**
	 * 
	 * @param isServiceHtml
	 * @return
	 */
	public CheckBox getIsServiceHtmlCheckBox(boolean isServiceHtml) {
		return getCheckBox(IS_SERVICE_HTML_LABEL, IS_SERVICE_HTML_VALUE, isServiceHtml);
	}

}
