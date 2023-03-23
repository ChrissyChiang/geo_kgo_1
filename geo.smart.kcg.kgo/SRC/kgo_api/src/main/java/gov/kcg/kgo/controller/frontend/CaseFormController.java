package gov.kcg.kgo.controller.frontend;

import gov.kcg.kgo.controller.base.BaseController;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.ApplyFormExInfoForm;
import gov.kcg.kgo.geoviewmodel.frontend.rq.ApplyFormExInfoRq;
import gov.kcg.kgo.geoviewmodel.frontend.rq.MyDataGenDataRq;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoMyDataGenDataRs;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoMyDataModelRs;
import gov.kcg.kgo.geoservice.GeoCaseSetRentService;
import gov.kcg.kgo.geoviewmodel.backend.rs.ApplyFormInitRs;
import gov.kcg.kgo.geoviewmodel.frontend.rq.ApplyFormValidTimeRq;
import gov.kcg.kgo.service.impl.CaseFormServiceImpl;
import gov.kcg.kgo.viewModel.base.response.ApiBaseResponse;
import gov.kcg.kgo.viewModel.frontend.caseform.rq.*;
import gov.kcg.kgo.viewModel.frontend.caseform.rs.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import gov.kcg.kgo.viewModel.frontend.caseform.rq.DownloadMyDataAttachmentRq;
import gov.kcg.kgo.viewModel.frontend.caseform.rq.HomeActionRq;
import gov.kcg.kgo.viewModel.frontend.caseform.rq.KcgMydataRq;
import gov.kcg.kgo.viewModel.frontend.caseform.rq.MyDataActionUrlRq;
import gov.kcg.kgo.viewModel.frontend.caseform.rq.MyDataHomeActionRq;
import gov.kcg.kgo.viewModel.frontend.caseform.rq.SaveActionRq;
import gov.kcg.kgo.viewModel.frontend.caseform.rq.ValidationActionRq;



@Api(tags = "1.1.16 網路申辦說明頁ＯＫ (stap3、stap4、stap5)", value = "1.1.16 網路申辦說明頁ＯＫ (stap3、stap4、stap5)")
@Controller
@RequestMapping("/frontend/caseform")
public class CaseFormController extends BaseController {


    @Autowired
    private CaseFormServiceImpl caseFormService;
	@Autowired
	private GeoCaseSetRentService geoCaseSetRentService;

    @ApiOperation(value = "網路申辦說明頁-stap3-初始畫面")
    @RequestMapping(value = {"/homeAction"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public HomeActionRs homeAction(@RequestBody HomeActionRq rq) {
        return this.caseFormService.homeAction(rq);
    }

    @ApiOperation(value = "MyData Model查詢")
    @RequestMapping(value = {"/myDataModel"}, method = {RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoMyDataModelRs myDataCaseQuery(@RequestParam(name = "caseSetId") String caseSetId) {
        GeoMyDataModelRs rs = caseFormService.queryMyDataModel(caseSetId);
        return rs;
    }

    @ApiOperation(value = "MyData Model 2 Gen Encrypted Data")
    @RequestMapping(value = {"/myDataGenEncryptedData"}, method = {RequestMethod.POST},
            consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public GeoMyDataGenDataRs myDataGenData(@RequestBody MyDataGenDataRq rq) throws Exception {
        GeoMyDataGenDataRs rs = caseFormService.genEncryptedData(rq);
        return rs;
    }

    @ApiOperation(value = "MyData Model 2-取得轉址MyData網址")
    @RequestMapping(value = {"/myDataModel2ActionUrl"}, method = {RequestMethod.POST},
            consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public MyDataActionUrlRs myDataModel2ActionUrl(@RequestBody MyDataModel2ActionUrlRq rq) throws Exception {
        return this.caseFormService.myDataModel2ActionUrl(rq);
    }

    @ApiOperation(value = "限定申請者機關者 roy")
    @RequestMapping(value = {"/checkOrganApply"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public CheckOrganApplyRs checkOrganApply(@RequestBody HomeActionRq rq) {
        return this.caseFormService.CheckOrganApply(rq);
    }

    @ApiOperation(value = "網路申辦說明頁-stap3-取得轉址MyData網址")
    @RequestMapping(value = {"/myDataActionUrl"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public MyDataActionUrlRs myDataActionUrl(@RequestBody MyDataActionUrlRq rq) {
        return this.caseFormService.myDataActionUrl(rq);
    }

//	@ApiOperation(value = "網路申辦說明頁-stap3-取得轉址MyData網址")
//	@RequestMapping(value = { "/myDataActionUrlTest" }, method = { RequestMethod.POST }, consumes = {
//			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
//	@ResponseBody
//	public MyDataActionUrlRs myDataActionUrlTest(@RequestBody MyDataTestRq rq) {
//		return caseFormService.myDataActionUrl(rq.getPid(), rq.getMyDataClientId());
//	}

    @ApiOperation(value = "網路申辦說明頁-stap3-取得 MyData 資料")
    @RequestMapping(value = {"/myDataHomeAction"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public MyDataHomeActionRs myDataHomeAction(@RequestBody MyDataHomeActionRq rq) {
        return this.caseFormService.myDataHomeAction(rq);
    }

//	@ApiOperation(value = "網路申辦說明頁-stap3-取得 MyData 資料")
//	@RequestMapping(value = { "/downloadMyDataTest" }, method = { RequestMethod.POST }, consumes = {
//			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
//	@ResponseBody
//	public MyDataServiceDownloadRs downloadMyDataTest(@RequestBody MyDataTestRq rq) {
//		MyDataServiceDownloadRs rs = caseFormService.downloadMyData(rq.getTxId(), rq.getMyDataClientId());
//
//		return rs;
//	}

//	@ApiOperation(value = "網路申辦說明頁-stap3-取得 MyData 合併資料")
//	@RequestMapping(value = { "/downloadMyDataMergeTest" }, method = { RequestMethod.POST }, consumes = {
//			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
//	@ResponseBody
//	public MyDataDownloadMageViewFromRs downloadMyDataMergeTest(@RequestBody MyDataTestRq rq) {
//		MyDataDownloadMageViewFromRs rs = caseFormService.downloadMyDataMerge(rq.getTxId(), rq.getMyDataClientId());
//
//		return rs;
//	}

    @ApiOperation(value = "網路申辦說明頁-stap3-下載 MyData 附件資料")
    @RequestMapping(value = {"/downloadMyDataAttachment"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public DownloadMyDataAttachmentRs downloadMyDataAttachment(@RequestBody DownloadMyDataAttachmentRq rq) {
        return this.caseFormService.downloadMyDataAttachment(rq);
    }

    @ApiOperation(value = "網路申辦說明頁-stap4-案件申請表單-檢核")
    @RequestMapping(value = {"/validationAction"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ValidationActionRs validationAction(@RequestBody ValidationActionRq rq) {
        return this.caseFormService.validationAction(rq);
    }

    @ApiOperation(value = "網路申辦說明頁-stap5-案件申請表單-存擋")
    @RequestMapping(value = {"/saveAction"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public SaveActionRs saveAction(@RequestBody SaveActionRq rq) {
        return this.caseFormService.saveAction(rq);
    }

    /**
     * GEO 20211202 add
     * 重複檢核檢查
     */
    @ApiOperation(value = "網路申辦說明頁-stap5-案件申請表單-存擋前重複檢核")
    @RequestMapping(value = {"/checkAction"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public CheckActionRs checkAction(@RequestBody SaveActionRq rq) {
        return this.caseFormService.checkAction(rq);
    }

    @ApiOperation(value = "網路申辦說明頁-stap3-取得市府MyData")
    @RequestMapping(value = {"/getKcgMydata"}, method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public KcgMydataRs getKcgMydata(@RequestBody KcgMydataRq rq) {
        return this.caseFormService.getKcgMydata(rq);
    }

	/**
	 *   申請表單初始畫面基本欄位
	 * */
	@ApiOperation(value = "stap3.網路申辦說明頁-線上租借場地-初始畫面")
	@RequestMapping(value = { "/caseRentHomeAction" }, method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public HomeActionRs caseRentHomeAction(@RequestBody HomeActionRq rq) {
		return caseFormService.caseRentHomeAction(rq);
	}
	/**
	 ** 申請表單初始加入場地時段資訊
	 */
	@ApiOperation(value = "step3.申請表單-線上租借場地-加入場地時段資訊")
	@RequestMapping(value = { "/timeFormInit" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ApplyFormInitRs applyFormInit(@RequestBody ApplyFormValidTimeRq rq) {
		ApplyFormInitRs rs = geoCaseSetRentService.applyFormValid(rq);
		return rs;
	}

    @ApiOperation(value = "step3.填入申請表單後,帶出額外資訊")
    @RequestMapping(value = { "/caseInfo" }, method = { RequestMethod.POST }, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ApiBaseResponse<ApplyFormExInfoForm> showCaseInfo(@RequestBody ApplyFormExInfoRq rq) {
        ApiBaseResponse<ApplyFormExInfoForm> rs = geoCaseSetRentService.applyCaseInfo(rq);
        return rs;
    }

}
