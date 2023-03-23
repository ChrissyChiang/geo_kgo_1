package gov.kcg.kgo.service.impl.helper;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import gov.kcg.kgo.enums.backend.KgoRoleEnum;
import gov.kcg.kgo.enums.common.ComboBoxStatusEnum;
import gov.kcg.kgo.model.KgoUnit;
import gov.kcg.kgo.repository.KgoUnitRepository;
import gov.kcg.kgo.util.SpringUtil;
import gov.kcg.kgo.viewModel.compoent.checkBox.CheckBox;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;

public class AccountManagementServiceHelper extends KgoServiceHelper {

	/**
	 * Instantiates a new account management service helper.
	 */
	public AccountManagementServiceHelper() {

	}

	// TODO 共通邏輯方法
	private static class Loader {
		/** The Constant instance. */
		private static final AccountManagementServiceHelper instance = new AccountManagementServiceHelper();
	}

	/**
	 * Gets the single instance of AccountManagementServiceHelper.
	 *
	 * @return single instance of AccountManagementServiceHelper
	 */
	public static AccountManagementServiceHelper getInstance() {
		return Loader.instance;
	}

	/**
	 * Get role comboBox from KgoRoleEnum
	 * 
	 * @return
	 */
	public ComboBox getRoleComboBoxByUserRoles(List<String> userRoles) {
		ComboBox roleComboBox = new ComboBox();
		boolean isAdmin = userRoles.stream().anyMatch(new String(KgoRoleEnum.ADMIN.getValue())::equalsIgnoreCase);
		// set role comboBox options from KgoRoleEnum
		for (KgoRoleEnum e : KgoRoleEnum.values()) {
			//only Admin can see Admin
			if(e.getValue().equalsIgnoreCase(KgoRoleEnum.ADMIN.getValue())){
				if(isAdmin){
					roleComboBox.add(e.getLabel(), e.getValue());
				}
			}else{
				roleComboBox.add(e.getLabel(), e.getValue());
			}
		}

		return roleComboBox;
	}

	/**
	 * Get default unit comboBox
	 * 
	 * @return
	 */
	public ComboBox getDefaultUnitComboBox() {
		return getUnitComboBox(ComboBoxStatusEnum.INIT.getCode(), null, null);
	}

	/**
	 * Get unit comboBox with custom data
	 * 
	 * @return
	 */
	public ComboBox getCustomUnitComboBox(List<KgoUnit> unitList, String selectedKey) {
		return getUnitComboBox(ComboBoxStatusEnum.ALL.getCode(), unitList, selectedKey);
	}

	/**
	 * Get unit comboBox with custom data
	 * 
	 * @return
	 */
	public ComboBox getOneUnitComboBox(List<KgoUnit> unitList) {
		return getUnitComboBox(ComboBoxStatusEnum.ONE.getCode(), unitList, null);
	}

	/**
	 * Generating unit comboBox
	 * 
	 * ALL
	 * 
	 * @param comboBoxState ComboBoxStatusEnum enum (ALL/ONE/INIT), can't be null
	 * @param List<KgoUnit> data set for generating comboBox options, can be null
	 * @param selectedKey   KGO_ORGAN.ID.unitId, can be null
	 * @return
	 */
	public ComboBox getUnitComboBox(Integer comboBoxState, List<KgoUnit> unitList, String selectedKey) {
		KgoUnitRepository kgoUnitRepository = SpringUtil.getDao(KgoUnitRepository.class);
		ComboBox unitComboBox = new ComboBox();
		unitComboBox.addDefaultComboItem();

		if (comboBoxState.equals(ComboBoxStatusEnum.INIT.getCode())) {
			/** 初始畫面 **/
			// nothing to do ...
		} else {
			List<KgoUnit> dataList = ObjectUtils.isEmpty(unitList) ? kgoUnitRepository.findAll() : unitList;
			dataList.forEach(l -> {
				if (comboBoxState.equals(ComboBoxStatusEnum.ONE.getCode())
						|| StringUtils.equals(selectedKey, l.getId().getUnitId())) {
					// 單一選項，符合key值的選項
					unitComboBox.add(l.getUnitName(), l.getId().getUnitId(), true);
				} else {
					unitComboBox.add(l.getUnitName(), l.getId().getUnitId());
				}
			});
		}

		return unitComboBox;
	}

}
