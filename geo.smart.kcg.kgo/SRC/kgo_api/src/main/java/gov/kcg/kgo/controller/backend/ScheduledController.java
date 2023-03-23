package gov.kcg.kgo.controller.backend;

import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.controller.base.BaseController;
import gov.kcg.kgo.enums.backend.KgoRoleEnum;
import gov.kcg.kgo.enums.error.KgoCommonApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.service.ScheduledService;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/backend/scheduled")
@Api(tags = {"手動更新府內人員，暫時開啟swagger"})
public class ScheduledController extends BaseController {

	@Autowired
	ScheduledService scheduledService;

	/**
	 * 更新機關、單位資料.
	 */
	@ApiOperation(value = "更新機關、單位資料.")
	@RequestMapping(value = { "/updateUnitData" }, method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ApiBaseResponse<BaseViewForm> updateUnitData() {
		if (checkUserRole())
			scheduledService.updateUnitData();

		return new ApiBaseResponse<BaseViewForm>();
	}

	/**
	 * 更新段小段
	 */
	@ApiOperation(value = "更新段小段")
	@RequestMapping(value = { "/updateKgoKcdTable" }, method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ApiBaseResponse<BaseViewForm> updateKgoKcdTable() {
		if (checkUserRole())
			scheduledService.updateKgoKcdTable();

		return new ApiBaseResponse<BaseViewForm>();
	}

	/**
	 * reTry 起案 Api
	 */
	@ApiOperation(value = "reTry 起案 Api")
	@RequestMapping(value = { "/reTryCallApiLog" }, method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ApiBaseResponse<BaseViewForm> reTryCallApiLog() {
		if (checkUserRole())
			scheduledService.reTryCallApiLog();

		return new ApiBaseResponse<BaseViewForm>();
	}

	/**
	 * KgoOrgan 來源 ：myaccount.kcg.gov.tw
	 */
	@ApiOperation(value = "KgoOrgan 來源 ：myaccount.kcg.gov.tw")
	@RequestMapping(value = { "/updateKgoOrganName" }, method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ApiBaseResponse<BaseViewForm> updateKgoOrganName() {
		if (checkUserRole())
			scheduledService.updateKgoOrganName();

		return new ApiBaseResponse<BaseViewForm>();
	}

	/**
	 * 更新 KgoUser 來源 ：myaccount.kcg.gov.tw
	 */
//	@ApiOperation(value = "* 手動更新暫時開啟：更新 KgoUser 來源 ：myaccount.kcg.gov.tw")
	@RequestMapping(value = { "/updateKgoUser" }, method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ApiBaseResponse<BaseViewForm> updateKgoUser(@RequestBody ApiRequest rq) {
		if (checkUserRole())
			scheduledService.updateKgoUser();

		return new ApiBaseResponse<BaseViewForm>();
	}

	/**
	 * 更新 KgoHoliday 來源 data.ntpc.gov.tw
	 */
	@ApiOperation(value = "更新 KgoHoliday 來源 data.ntpc.gov.tw")
	@RequestMapping(value = { "/updateKgoHoliday" }, method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ApiBaseResponse<BaseViewForm> updateKgoHoliday() {
		if (checkUserRole())
			scheduledService.updateKgoHoliday();
		return new ApiBaseResponse<BaseViewForm>();
	}

	/**
	 * GEO 20211201 Add
	 */
//	@ApiOperation(value = "* 手動更新暫時開啟： 刪除 myData附件檔案")
	@RequestMapping(value = { "/deleteKgoMyDataFile" }, method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ApiBaseResponse<BaseViewForm> deleteKgoMyDataFile(@RequestBody ApiRequest rq) {
		if (checkUserRole())
			scheduledService.deleteKgoMyDataFile();
		return new ApiBaseResponse<BaseViewForm>();
	}

	/**
	 * 檢核登入者權限
	 */
	@ApiOperation(value = "檢核登入者權限")
	private boolean checkUserRole() throws KgoApiException {
		BackendLoginUserInfo userInfo = KgoUtil.getBackendLoginUser();
//		boolean isPass = userInfo.getRoles().contains(new String(KgoRoleEnum.ADMIN.getValue()));
		boolean isPass = userInfo.getRoles().stream().anyMatch(new String(KgoRoleEnum.ADMIN.getValue())::equalsIgnoreCase);
		if (!isPass) {
			throw new KgoApiException(new ErrorResult(KgoCommonApiError.UNAUTHORIZED));
		}

		return isPass;
	}
}
