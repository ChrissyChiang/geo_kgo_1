package gov.kcg.kgo.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.enums.backend.CodeTypeEnum;
import gov.kcg.kgo.enums.backend.KgoRoleEnum;
import gov.kcg.kgo.enums.backend.ParamSetEnum;
import gov.kcg.kgo.enums.backend.ParamSetTypeEnum;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.model.KgoCode;
import gov.kcg.kgo.model.KgoParamSet;
import gov.kcg.kgo.repository.KgoCodeRepository;
import gov.kcg.kgo.repository.KgoParamSetRepository;
import gov.kcg.kgo.service.BackEndParamSetService;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.util.MessageUtil;
import gov.kcg.kgo.viewModel.backend.paramSet.home.rs.ParamSetHomeActionRs;
import gov.kcg.kgo.viewModel.backend.paramSet.home.rs.bean.ParamSetGrid;
import gov.kcg.kgo.viewModel.backend.paramSet.home.rs.bean.ParamSetHomeActionViewForm;
import gov.kcg.kgo.viewModel.backend.paramSet.save.rq.ParamSetSaveActionRq;
import gov.kcg.kgo.viewModel.backend.paramSet.save.rq.bean.ParamSetSaveBean;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;

/**
 * 後台 - 參數設定 Service.
 */
@Transactional(rollbackFor = Exception.class)
@Service("BackEndParamSetService")
public class BackEndParamSetServiceImpl extends KgoBackEndServiceImpl implements BackEndParamSetService {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BackEndParamSetServiceImpl.class);
	
	@Autowired
	private KgoParamSetRepository kgoParamSetRepository;
	
	@Autowired
	private KgoCodeRepository kgoCodeRepository;
	
	@Autowired
	private MessageUtil messageUtil;

	/**
	 * 參數設定 - 畫面初始.
	 *
	 * @return the param set home action rs
	 */
	@Override
	public ParamSetHomeActionRs homeAction() {
		ParamSetHomeActionRs rs = new ParamSetHomeActionRs();
		ParamSetHomeActionViewForm viewFrom = new ParamSetHomeActionViewForm();
		rs.setData(viewFrom);
		try {
			List<ParamSetGrid> grid = new ArrayList<>();
			List<KgoParamSet> paramSetList = kgoParamSetRepository.findAllOrderByType();
			
			List<String> roles = KgoUtil.getBackendLoginUser().getRoles();

			if(!CollectionUtils.containsAny(roles, Arrays.asList(KgoRoleEnum.ADMIN.getValue(), KgoRoleEnum.CASE_M.getValue(),KgoRoleEnum.ADMIN.getValue().toLowerCase()))) {
				return rs;
			}

			// 角色為機關管理者 (非系統管理者 ) 僅顯示案件暫存區保存期限
			if(!CollectionUtils.containsAny(roles, Arrays.asList(KgoRoleEnum.ADMIN.getValue())) &&
					CollectionUtils.containsAny(roles, Arrays.asList(KgoRoleEnum.CASE_M.getValue()))&&
					CollectionUtils.containsAny(roles, Arrays.asList(KgoRoleEnum.CASE_M.getValue().toLowerCase()))) {
				paramSetList = paramSetList.stream().filter(p -> p.getType().equals(ParamSetEnum.TS.getType())).collect(Collectors.toList());
			}

			for (KgoParamSet kgoParamSet : paramSetList) {
				ParamSetEnum paramSetEnum = ParamSetEnum.getParamSetEnum(kgoParamSet.getType());
				ComboBox combox = new ComboBox();
				String value = kgoParamSet.getValue();

				/** === 為布林值類型 === */
				if(paramSetEnum.getIsBolean()) {
					for (ParamSetTypeEnum psType : paramSetEnum.getPsType()) {
						boolean select = false;
						String bitVal = String.valueOf(psType.getCode());
						if (bitVal.equals(kgoParamSet.getValue())) {
							select = true;
						}
						// label: 主動、被動  ; value: 1,0
						String label = messageUtil.getMessage(psType.getCodeI18n());	
						combox.add(label, bitVal, select);
					}
					
				/** === 非布林值類型 === */
					// FT : 表單附件檔案類型，資料源從KGO_CODE來
				} else if (ParamSetEnum.FT.equals(paramSetEnum)) {
					List<KgoCode> kgoCodeList = kgoCodeRepository.findByIdCodeType(CodeTypeEnum.FILE_TYPE.getValue());
					for (KgoCode code : kgoCodeList) {
						boolean select = false;
						if (StringUtils.contains(value, code.getId().getCodeId())) {
							select = true;
						}
						combox.add(code.getCodeName(), code.getId().getCodeId(), select);
					}
					// 其他
				} else {
					for (ParamSetTypeEnum type : paramSetEnum.getPsType()) {
						boolean select = false;
						if(StringUtils.equals(type.getCode(), kgoParamSet.getDetailType())) {
							select = true;
						}
						combox.add(messageUtil.getMessage(type.getCodeI18n()), type.getCode(), select);
					}
				} 
				ParamSetGrid vo = new ParamSetGrid(kgoParamSet.getType(), messageUtil.getMessage(paramSetEnum.getTypeNameI18n()), combox, value);
				grid.add(vo);
			}
			viewFrom.setGrid(grid);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new KgoApiException("homeAction error " + e.getMessage(), e);
		}
		return rs;
	}

	/**
	 * 參數設定 - 儲存.
	 *
	 * @param rq the rq
	 * @return the api base response
	 */
	@Override
	public ApiBaseResponse<BaseViewForm> saveAction(ParamSetSaveActionRq rq) {
		ApiBaseResponse<BaseViewForm> rs = new ApiBaseResponse<BaseViewForm>();
		try {
			Timestamp now = DateUtil.getCurrentTimestamp();
			List<ParamSetSaveBean> rqDataList = rq.getParamSetList();
			List<KgoParamSet> saveList = new ArrayList<>();
			BackendLoginUserInfo beUser = KgoUtil.getBackendLoginUser();
			// 案件儲存 to entity
			for (ParamSetSaveBean saveData: rqDataList) {
				KgoParamSet kgoParamSet = new KgoParamSet();
				kgoParamSet.setType(saveData.getType());
				kgoParamSet.setDetailType(saveData.getDetailType());
				kgoParamSet.setValue(saveData.getValue());
				kgoParamSet.setUpdateTime(now);
				kgoParamSet.setUpdateUser(beUser.getUserId());
				saveList.add(kgoParamSet);
			}
			kgoParamSetRepository.saveAll(saveList);
			
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new KgoApiException("saveAction error " + e.getMessage(), e);
		}
		return rs;
	}
}
