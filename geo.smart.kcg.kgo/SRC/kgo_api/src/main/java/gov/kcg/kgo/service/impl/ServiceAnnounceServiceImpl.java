package gov.kcg.kgo.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import gov.kcg.kgo.common.frontend.FrontendLoginUserInfo;
import gov.kcg.kgo.enums.backend.CheckTypeEnum;
import gov.kcg.kgo.enums.error.KgoCommonApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.service.impl.helper.InternetApplyServiceHelper;
import gov.kcg.kgo.util.SpringUtil;
import gov.kcg.kgo.util.SsoUtil;
import gov.kcg.kgo.viewModel.compoent.checkBox.CheckBox;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.kcg.kgo.enums.error.KgoFrontEndApiError;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.model.KgoCaseset;
import gov.kcg.kgo.model.KgoCasesetCheck;
import gov.kcg.kgo.repository.KgoCasesetCheckRepository;
import gov.kcg.kgo.repository.KgoCasesetRepository;
import gov.kcg.kgo.service.ServiceAnnounceService;
import gov.kcg.kgo.viewModel.frontend.serviceAnnounce.rq.ServiceAnnounceHomeRq;
import gov.kcg.kgo.viewModel.frontend.serviceAnnounce.rs.ServiceAnnounceHomeRs;
import gov.kcg.kgo.viewModel.frontend.serviceAnnounce.rs.bean.ServiceAnnounceHomeViewForm;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

@Transactional(rollbackFor = Exception.class)
@Service("ServiceAnnounceService")
public class ServiceAnnounceServiceImpl extends KgoFrontEndServiceImpl implements ServiceAnnounceService {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceAnnounceServiceImpl.class);

	@Autowired
	private KgoCasesetRepository kgoCasesetRepository;

	@Autowired
	InternetApplyServiceHelper internetApplyServiceHelper;

	/**
	 * 服務宣告-初始畫面
	 *
	 * @param rq
	 * @return
	 */
	public ServiceAnnounceHomeRs serviceAnnounceHome(ServiceAnnounceHomeRq rq) {
		ServiceAnnounceHomeRs rs = new ServiceAnnounceHomeRs();
		ServiceAnnounceHomeViewForm viewForm = new ServiceAnnounceHomeViewForm();
		try {
			KgoCaseset kgoCaseset = kgoCasesetRepository.getOne(rq.getCaseSetId());
			String caseSetName = kgoCaseset.getCaseSetName();
			boolean isServiceHtml = kgoCaseset.getIsServiceHtml() != null && kgoCaseset.getIsServiceHtml();
			String serviceHtml = isServiceHtml ? kgoCaseset.getServiceHtml() : StringUtils.EMPTY;

			//獲取該服務身份驗證
			List<CheckBox> identityVerifyCheckBox = getVerifyCheckBox(rq.getCaseSetId());
			Boolean isLogin = false; //預設不可登入
			FrontendLoginUserInfo userInfo = getFrontendLoginUser();

			//有設定驗證方式時的判斷
			if (userInfo!=null){
				for (CheckBox c: identityVerifyCheckBox){
					if (c.getValue().equals(userInfo.getLoginAuthTokenType().getType())){
						if (c.getSelected()){
							isLogin = true;
							break;
						} //if (c.getSelected())
					} //if (c.getValue().equals
				} // for (CheckBox c: identityVerifyCheckBox)
			}else {
				for (CheckBox c: identityVerifyCheckBox){
					if (c.getValue().equals("NAN")){
						if (c.getSelected()){
							isLogin = true;
							break;
						} //if (c.getSelected())
					} //if (c.getValue().equals
				} // for (CheckBox c: identityVerifyCheckBox)
			} //if (userInfo!=null)

			LOGGER.info("serviceAnnounceHome isLogin="+isLogin);

			// 未設定驗證的方式的判斷
			Boolean isDefault = true;
			for (CheckBox c: identityVerifyCheckBox){
				if ( c.getSelected()){
					isDefault = false;
				} //if ( c.getSelected())
			} //for (CheckBox c: identityVerifyCheckBox)

			if (isDefault){
				viewForm.setLogin(true);
				for (CheckBox c: identityVerifyCheckBox){
					if (c.getLabel().equals("免驗證")){
						c.setSelected(true);
					} //if ( c.getSelected())
				} //for (CheckBox c: identityVerifyCheckBox)
				viewForm.setIdentityVerifyCheckBox(identityVerifyCheckBox);
			}else {
				viewForm.setLogin(isLogin);
				viewForm.setIdentityVerifyCheckBox(identityVerifyCheckBox);
			} //if (isDefault)

			viewForm.setCaseSetName(caseSetName);
			viewForm.setServiceHtml(serviceHtml);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoFrontEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("bidServiceMenuHome error " + e.getMessage(), e);
		}
		return rs;
	} //serviceAnnounceHome

	//身份驗證
	public List<CheckBox> getVerifyCheckBox(String caseSetId) {
		KgoCasesetCheckRepository kgoCasesetCheckRepository = SpringUtil.getDao(KgoCasesetCheckRepository.class);
		List<KgoCasesetCheck> kgoCasesetChecks = kgoCasesetCheckRepository.findAllByIdCaseSetId(caseSetId);
		List<String> casesetChecks = kgoCasesetChecks.stream().map(check -> check == null ? "UNKNOWN_CHECK_TYPE" : check.getId().getCheckType()).collect(Collectors.toList());
		List<CheckBox> identityVerifyCheckBox = new LinkedList<CheckBox>();
		CheckBox checkBox = null;
		for (CheckTypeEnum checkType : CheckTypeEnum.values()) {
			if (casesetChecks.contains(checkType.getValue())) {
				checkBox = internetApplyServiceHelper.getCheckBox(checkType.getLabel(), checkType.getValue(), true);
			} else {
				checkBox = internetApplyServiceHelper.getCheckBox(checkType.getLabel(), checkType.getValue());
			} //if (casesetChecks.contains(checkType.getValue()))
			identityVerifyCheckBox.add(checkBox);
		} //for (CheckTypeEnum checkType : CheckTypeEnum.values())
		return identityVerifyCheckBox;
	} //getVerifyCheckBox

	//獲取前臺登入者資訊
	public static FrontendLoginUserInfo getFrontendLoginUser() {
		LOGGER.info("KgoUtil getFrontendLoginUser...");
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			FrontendLoginUserInfo userInfo = (FrontendLoginUserInfo) WebUtils.getSessionAttribute(request, SsoUtil.FRONTEND_USER_INO_KEY);
			return userInfo;
		} catch (Exception e) {
			// 使用者未登入.
			throw new KgoApiException(new ErrorResult(KgoCommonApiError.USER_NOT_LOGIN));
		}
	} //getFrontendLoginUser

}
