package gov.kcg.kgo.geoservice;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import gov.kcg.kgo.dto.CaseMainQueryCaseViewListDto;
import gov.kcg.kgo.enums.backend.BackendFunctionCodeEnum;
import gov.kcg.kgo.enums.backend.CaseMainStatusEnum;
import gov.kcg.kgo.enums.common.ComboBoxStatusEnum;
import gov.kcg.kgo.enums.common.SysLogExeType;
import gov.kcg.kgo.enums.common.SystemTypeEnum;
import gov.kcg.kgo.enums.common.sso.LoginAuthTokenType;
import gov.kcg.kgo.enums.error.KgoFrontEndApiError;
import gov.kcg.kgo.enums.front.FrontendFunctionCodeEnum;
import gov.kcg.kgo.geoentity.*;
import gov.kcg.kgo.geoenum.CaseSetCategoryEnum;
import gov.kcg.kgo.geoenum.RentStatusEnum;
import gov.kcg.kgo.geomodel.*;
import gov.kcg.kgo.georepository.*;
import gov.kcg.kgo.geoviewmodel.backend.rq.*;
import gov.kcg.kgo.geoviewmodel.backend.rs.*;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.*;
import gov.kcg.kgo.geoviewmodel.frontend.rq.*;
import gov.kcg.kgo.geoviewmodel.frontend.rs.CaseRentServiceComboBoxRs;
import gov.kcg.kgo.geoviewmodel.frontend.rs.CaseSetSiteDetailRs;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.CaseRentServiceComboBoxViewForm;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoSiteDetailViewForm;
import gov.kcg.kgo.model.KgoCaseset;
import gov.kcg.kgo.model.KgoGroupLevel;
import gov.kcg.kgo.repository.KgoCasesetGroupRepository;
import gov.kcg.kgo.repository.KgoCasesetRepository;
import gov.kcg.kgo.repository.KgoGroupLevelRepository;
import gov.kcg.kgo.service.impl.CaseFormServiceImpl;
import gov.kcg.kgo.service.impl.helper.CommonServiceHelper;
import gov.kcg.kgo.service.impl.helper.KgoServiceHelper;
import gov.kcg.kgo.util.*;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.query.rq.CaseHandleCaseViewQueryRq;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.query.rs.CaseHandleCaseViewQueryRs;
import gov.kcg.kgo.viewModel.backend.caseHadle.caseView.query.rs.bean.CaseHandleCaseViewQueryViewForm;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.enums.common.SuccessMsg;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.georepository.custom.GeoCaseSetSiteReposCustom;
import gov.kcg.kgo.georepository.custom.GeoCaseSetSiteTimeReposCustom;
import gov.kcg.kgo.model.KgoCaseMain;
import gov.kcg.kgo.repository.KgoCaseMainRepository;
import gov.kcg.kgo.service.CommonService;
import gov.kcg.kgo.service.ExcelTempExportService;
import gov.kcg.kgo.service.impl.helper.CaseManagementServiceHelper;
import gov.kcg.kgo.service.impl.helper.OrganUnitManagementServiceHelper;
import gov.kcg.kgo.service.operationmemo.OperationApiMemo;
import gov.kcg.kgo.service.operationmemo.OperationColumn;
import gov.kcg.kgo.viewModel.backend.caseManagement.home.rs.SiteManagementHomeRs;
import gov.kcg.kgo.viewModel.backend.caseManagement.home.rs.bean.SiteManagementHomeViewForm;
import gov.kcg.kgo.viewModel.backend.taskMaintain.editHome.rs.bean.SiteMaintainEditHomeFileViewForm;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;


/**
 * *線上場地租借 Roy
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class GeoCaseSetSiteService extends GeoBaseService {
	private String  MAINID_DATE_FORMAT = "yyyymmddMMss";
	private static final String SITE_CATEGORY_PREFIX = "SITE";
	private static final String ACTIVITY_CATEGORY_PREFIX = "ACTI";

	private OrganUnitManagementServiceHelper organUnitManagementServiceHelper = OrganUnitManagementServiceHelper.getInstance();
	private CaseManagementServiceHelper caseManagementServiceHelper = CaseManagementServiceHelper.getInstance();
	private KgoServiceHelper kgoServiceHelper = KgoServiceHelper.getInstance();
	private CommonServiceHelper commonServiceHelper = CommonServiceHelper.getInstance();

	private static final Logger LOGGER = LoggerFactory.getLogger(GeoCaseSetSiteService.class);
	@Autowired
	private GeoCaseSetSiteTimeReposCustom geoCaseSetSiteTimeReposCustom;
	@Autowired
	private GeoCaseSetSiteRepos geoCaseSetSiteRepos;
	@Autowired
	private GeoCaseSetSiteReposCustom geoCaseSetSiteReposCustom;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private KgoCaseMainRepository kgoCaseMainRepository;
	@Autowired
	private ExcelTempExportService excelTempExportService;
	@Autowired
	private GeoCaseRentRelationRepos geoCaseRentRelationRepos;
	@Autowired
	private GeoCaseSetRepos geoCaseSetRepos;
	@Autowired
	private GeoCaseSetSiteMainRepos geoCaseSetSiteMainRepos;
	@Autowired
	private GeoCaseSetSiteMainFileRepos geoCaseSetSiteMainFileRepos;
	@Autowired
	private CommonService commonService;
	@Autowired
	private KgoGroupLevelRepository kgoGroupLevelRepository;
	@Autowired
	private KgoCasesetRepository kgoCasesetRepository;
	@Autowired
	private GeoKgoCasesetRentMainRepository geoKgoCasesetRentMainRepository;
	@Autowired
	private CaseFormServiceImpl caseFormService;
	@Autowired
	private KgoCasesetGroupRepository kgoCasesetGroupRepository;
	@Autowired
	GeoKgoRentPaymentRepos geoKgoRentPaymentRepos;

	@Value("${case.handle.pending.review.upload.file.extension}")
	private List<String> caseHandlePendingReviewUploadFileExtension;


	public GeoSiteEditUserComboBoxQueryRs getEditUserComboBox(GeoSiteEditUserComboBoxQueryRq rq){
		GeoSiteEditUserComboBoxQueryRs rs = new GeoSiteEditUserComboBoxQueryRs();
		GeoSiteEditUserComboxQueryViewForm viewForm = new GeoSiteEditUserComboxQueryViewForm();
		try {
			ComboBox userComboBox = getEditUserComboBox(rq.getUnitId());
			viewForm.setUserComboBox(userComboBox);
			rs.setMsg(SuccessMsg.UNKNOW.getMsg());
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error("query siteEditUserComboBox error,", e);
			throw new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SEARCH));
		}
		return rs;
	}

	/**
	 ** 後台-場地新增/編輯畫面
	 */
	public GeoSiteEditRs siteEdit(GeoSiteEditRq rq){
		LOGGER.info("siteEdit start :");
		LOGGER.info("siteMainId : "+rq.getSiteMainId());
		KgoApiException error = null;
		OperationApiMemo memo = null;
		GeoKgoCaseSetSiteMain siteMain = new GeoKgoCaseSetSiteMain();
		GeoSiteEditViewForm viewForm = new GeoSiteEditViewForm();
		GeoSiteEditRs rs = new GeoSiteEditRs();
		BackendLoginUserInfo loginUser = KgoUtil.getBackendLoginUser();
		String siteMainId = rq.getSiteMainId();
		try{
			SysLogExeType sysLogExeType = StringUtils.isBlank(siteMainId) ? SysLogExeType.TYPE_A
					: SysLogExeType.TYPE_U;
			memo = super.genOperationMemo(SystemTypeEnum.B, sysLogExeType, BackendFunctionCodeEnum.CaseM);
			String categroy = null;
			if(siteMainId != null) {
				siteMain = geoCaseSetSiteMainRepos.getById(siteMainId);
				categroy = siteMain.getSiteType();
				//附件檔
				List<GeoKgoCaseSetSiteMainFile> sitefile = geoCaseSetSiteMainFileRepos.findAllBySiteMainId(siteMainId);
				viewForm.setFiles(sitefile.stream().map(f->{
					Map<String,String> map = new HashMap<>();
					map.put("fileName",f.getFileName());
					map.put("fileId",f.getSiteFileId().toString());
					return map;
				}).collect(Collectors.toList()));
			}
			ComboBox unitComboBox = getUnitComboBox( siteMain.getOrganId() == null ? loginUser.getOrgan() : siteMain.getOrganId() ,
					siteMain.getUnitId() == null ? loginUser.getUnit() : siteMain.getUnitId());

			viewForm.setCategoryComboBox(createCategogryComboBox(categroy));
			LOGGER.info("siteEdit categroy="+categroy);
			viewForm.setOrganComboBox(getOrganComboBox());
			LOGGER.info("siteEdit getOrganComboBox()="+getOrganComboBox());
			viewForm.setUnitComboBox(unitComboBox);
			LOGGER.info("siteEdit unitComboBox="+unitComboBox);
			viewForm.setServiceHtml(siteMain.getServiceHtml());
			viewForm.setSiteAmount(siteMain.getSiteAmount());
			viewForm.setSiteName(siteMain.getSiteName());
			viewForm.setSiteMainId(siteMain.getSiteMainId());
			viewForm.setSiteStatus(siteMain.getSiteStatus());
			rs.setData(viewForm);
			LOGGER.info("siteEdit end");
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			error = apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SAVE.getErrorMsgKey());
			error = new KgoApiException(" siteMain edit error " + e.getMessage(), e);
		} finally {
			/** === 儲存操作紀錄 === */
			List<OperationColumn> memoList = new ArrayList<>();
			memoList.add(new OperationColumn("案件編號", rs.getData().getSiteMainId()));
			memo.setMemoList(memoList);
			super.saveOperationLog(memo);
			/** === 儲存操作紀錄 === */
			setResultMessage(rq, rs, error);
		}

		return rs;
	}


	/**
	 ** 後台-場地編輯後儲存
	 */
	@Transactional
	public GeoSiteSaveRs siteSave(GeoSiteSaveRq rq) {
		LOGGER.info("GeoCaseSetSiteService SiteSave...");
		LOGGER.info("rq :"+rq.toString());
		KgoApiException error = null;
		OperationApiMemo memo = null;
		GeoSiteSaveRs rs = new GeoSiteSaveRs();
		GeoSiteSaveViewForm viewForm = new GeoSiteSaveViewForm();
		GeoKgoCaseSetSiteMain siteMain = new GeoKgoCaseSetSiteMain();
		try {
			SysLogExeType sysLogExeType = StringUtils.isBlank(siteMain.getSiteMainId()) ? SysLogExeType.TYPE_A
					: SysLogExeType.TYPE_U;
			memo = super.genOperationMemo(SystemTypeEnum.B, sysLogExeType, BackendFunctionCodeEnum.CaseM);

//			存入[GEO_KGO_CASESET_SITE_MAIN]
			String loginUserId = KgoUtil.getBackendLoginUser().getUserId();
			KgoGroupLevel kgl = kgoGroupLevelRepository.getById(Integer.valueOf(rq.getOrgan()));
			LOGGER.info("query kgoGruopLevel db :"+kgl.toString());
			siteMain.setOrganId(kgl.getOrganId());
			PropertyUtils.copyProperties(siteMain,rq);
			if(StringUtils.isBlank(siteMain.getSiteMainId())){
				siteMain.setSiteMainId(createSitMainId());
			}else{
				//檢核是否已經提供預約
				List<GeoKgoCaseRentRelation> rentRelation = geoCaseRentRelationRepos.findBySiteMainId(siteMain.getSiteMainId());
				if(rentRelation.size() > 0 ){
					new KgoApiException(new ErrorResult(KgoBackEndApiError.SITE_EDIT_ERROR));
				}
			}

			Timestamp now = new Timestamp(System.currentTimeMillis());
			siteMain.setSiteType(rq.getCategory());
			siteMain.setEditTime(now);
			siteMain.setEditUser(loginUserId);
			siteMain.setCreateUser(loginUserId);
			siteMain.setCreateTime(now);
			GeoKgoCaseSetSiteMain saveCaseMain = geoCaseSetSiteRepos.saveAndFlush(siteMain);
			LOGGER.info("save site finish!!");
			if(rq.getFiles() != null) {
				List<SiteMaintainEditHomeFileViewForm> files = rq.getFiles();
				for (int i = 0; i < files.size(); i++) {
					//檔案上傳
					GeoKgoCaseSetSiteMainFile kgoCasesetSiteMainFile = new GeoKgoCaseSetSiteMainFile();
					String base64Str = files.get(i).getBase64Str();
					String[] dataString = files.get(i).getBase64Str().split(",");
					if (dataString.length > 1) {
						base64Str = dataString[1];
					}
					byte[] decoder = Base64.getDecoder().decode(base64Str);

					String filePath = KgoUtil.getCaseSetSiteDownloadUploadBasePath(saveCaseMain.getSiteMainId());
					LOGGER.info("file name: "+files.get(i).getName());
					LOGGER.info("filePath>>>" + filePath);
					File directory = new File(filePath);
					if (!directory.exists()) directory.mkdirs();
					FileUtil.createFile(directory, files.get(i).getName(), decoder);

					//存入[GEO_KGO_CASESET_SITE_MAIN_FILE]
					kgoCasesetSiteMainFile.setSiteMainId(saveCaseMain.getSiteMainId());
					kgoCasesetSiteMainFile.setFileName(files.get(i).getName());
					geoCaseSetSiteMainFileRepos.saveAndFlush(kgoCasesetSiteMainFile);
					LOGGER.info("save file detail finish");
				}
			}
			viewForm.setSiteMainId(saveCaseMain.getSiteMainId());
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			error = apiE;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SAVE.getErrorMsgKey());
			error = new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_EDIT));
		} finally {
			/** === 儲存操作紀錄 === */
			List<OperationColumn> memoList = new ArrayList<>();
			memoList.add(new OperationColumn( "案件編號", rs.getData().getSiteMainId()));
			memo.setMemoList(memoList);
			super.saveOperationLog(memo);
			/** === 儲存操作紀錄 === */
			setResultMessage(rq, rs, error);
		}

		return rs;
	}

	/**
	 * 前台-服務案件出借場地查詢(僅上架)  20221025 Oberyn
	 * caseSetId : 服務案件編號
	 * findAll   : 是否提供全部選項
	 */
	public CaseRentServiceComboBoxRs caseSetSiteComBox(@RequestBody CaseSetSiteComboBoxRq rq){
		CaseRentServiceComboBoxRs rs = new CaseRentServiceComboBoxRs();
		CaseRentServiceComboBoxViewForm viewForm = new CaseRentServiceComboBoxViewForm();
		KgoApiException error = null;
		String caseSetId = rq.getCaseSetId();
		try {
			List<GeoKgoCasesetRentMain> rentMainList = geoKgoCasesetRentMainRepository.findAllByCaseSetId(caseSetId);
			List<GeoKgoCaseSetSiteMain> caseSiteList = rentMainList.stream()
					.map(rm->geoCaseSetSiteRepos.getActiveSiteById(rm.getServiceId()))
					.filter(Objects::nonNull).collect(Collectors.toList());
			ComboBox CaseSiteComboBox = kgoServiceHelper.getComboBox(caseSiteList, "siteName", "siteMainId", StringUtils.EMPTY, ComboBoxStatusEnum.ALL.getCode(), true);
			if(caseSiteList.size()== 0 ){
				CaseSiteComboBox.setIsShow(false);
			}else if(rq.getFindAll()){
				String allSite = caseSiteList.stream().map(cs->cs.getSiteMainId()).collect(Collectors.joining(","));
				if(allSite.length()>0) CaseSiteComboBox.add("全部",allSite);
			}

			viewForm.setServiceComboBox(CaseSiteComboBox);
			rs.setData(viewForm);
		}catch (KgoApiException apiE) {
		LOGGER.error(apiE.getMessage());
		throw apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			error = new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SEARCH), e);
		} finally {
			setResultMessage(rq, rs, error);
		}
			return rs;
	}

	/**
	 ** 場地查詢
	 */
	public SiteQueryRs siteQuery(GeoSiteQueryRq rq) {

		KgoApiException error = null;
		SiteQueryRs rs = new SiteQueryRs();
		GeoSiteQueryViewForm viewForm = new GeoSiteQueryViewForm();
		rs.setData(viewForm);
		try {
			List<GeoCaseSetSiteMainModel> listModel= geoCaseSetSiteReposCustom.findSite(rq.getOrganId(),rq.getUnitId(),rq.getEditUser(),rq.getSiteName());
			viewForm.setDetailList(listModel);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			error = new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SEARCH), e);
		} finally {
			setResultMessage(rq, rs, error);
		}
		return rs;
	}


	public void siteDownloadFile(CaseSiteFileDownloadRq rq) {
		KgoApiException error = null;
		OperationApiMemo memo = null;
		try {
			GeoKgoCaseSetSiteMainFile fileEntity = geoCaseSetSiteMainFileRepos.getById(Long.valueOf(rq.getFileId()));
			if(fileEntity == null)throw new KgoApiException("siteDownloadFile : can not find file with fileId = "+rq.getFileId());
			String filePath = KgoUtil.getCaseSetSiteDownloadUploadBasePath(fileEntity.getSiteMainId());
			memo = super.genOperationMemo(SystemTypeEnum.F, SysLogExeType.TYPE_L, FrontendFunctionCodeEnum.RentalCase);
			Stream<Path> pathStream = Files.list(new File(filePath).toPath()).filter(f -> f.getFileName().toString().contains(fileEntity.getFileName()));
			File file = pathStream.findFirst().get().toFile();
			commonService.downloadFileAction(file);
		} catch (IOException e) {
			LOGGER.error("Download file error", e);
			error = new KgoApiException("Download file error, caseSetSiteMainFileId is = " + rq.getFileId());
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			error = apiE;
		} catch (Exception e) {
			LOGGER.error(">>>> siteDownloadFile >>>>>" + e.getMessage(), e);
			error = new KgoApiException("siteDownloadFile error " + e.getMessage(), e);
		} finally {
			List<OperationColumn> memoList = new ArrayList<>();
			memoList.add(new OperationColumn("場地附件", rq.getFileId()));
			memo.setMemoList(memoList);
			super.saveOperationLog(memo);
			if (error != null) {
				throw error;
			}
		}
	}

	/**
	 ** 後台-場地上下架
	 */
	public GeoSiteStatusUpdateRs siteStatusSave(GeoSiteStatusRq rq){
		KgoApiException error = null;
		OperationApiMemo memo = null;
		GeoSiteStatusUpdateRs rs = new GeoSiteStatusUpdateRs();
		SiteStatusUpdateViewForm viewForm = new SiteStatusUpdateViewForm();
		rs.setData(viewForm);
		try {
			memo = super.genOperationMemo(SystemTypeEnum.B, SysLogExeType.TYPE_U, BackendFunctionCodeEnum.SiteManagement);
			Timestamp now = new Timestamp(System.currentTimeMillis());
			String loginUserId = KgoUtil.getBackendLoginUser().getUserId();
			int successCnt = geoCaseSetSiteRepos.siteStatusUpdate(rq.getSiteMainId(),rq.getSiteStatus(),loginUserId,now);
			if (successCnt != 1) {
				throw new Exception("update failed! site_main_id = " + rq.getSiteMainId() );
			}
			viewForm.setMsg(SuccessMsg.UPDATE.getMsg());
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			error = new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SAVE), e);
		} finally {
			List<OperationColumn> memoList = new ArrayList<>();
			memoList.add(new OperationColumn("場地活動編號(上下架)", rq.getSiteMainId()));
			memo.setMemoList(memoList);
			super.saveOperationLog(memo);
			setResultMessage(rq, rs, error);
		}
		return rs;
	}


	/**
	 ** 後台-場地刪除
	 */
	public GeoSiteDeleteRs siteDelete(GeoSiteDeleteRq rq){
		KgoApiException error = null;
		OperationApiMemo memo = null;
		GeoSiteDeleteRs rs = new GeoSiteDeleteRs();
		SiteDeleteViewForm viewForm = new SiteDeleteViewForm();
		rs.setData(viewForm);
		try {
			memo = super.genOperationMemo(SystemTypeEnum.B, SysLogExeType.TYPE_D, BackendFunctionCodeEnum.SiteManagement);
			Timestamp now = new Timestamp(System.currentTimeMillis());
			String loginUserId = KgoUtil.getBackendLoginUser().getUserId();
			int successCnt = geoCaseSetSiteRepos.deleteBySiteMainId(rq.getSiteMainId(),loginUserId,now);
			if (successCnt != 1) {
				throw new Exception("update failed! site_main_id = " + rq.getSiteMainId() );
			}
			viewForm.setMsg(SuccessMsg.UPDATE.getMsg());
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_DELETE), e);
		} finally {
			List<OperationColumn> memoList = new ArrayList<>();
			memoList.add(new OperationColumn("場地活動編號(上下架)", rq.getSiteMainId()));
			memo.setMemoList(memoList);
			super.saveOperationLog(memo);
			setResultMessage(rq, rs, error);
		}
		return rs;
	}

	/** 場地案件編輯 - 刪除已上傳檔案*/
	public GeoSiteDeleteRs  siteDeleteFile(GeoSiteDeleteFileRq rq){
		GeoSiteDeleteRs rs = new GeoSiteDeleteRs();
		SiteDeleteViewForm viewForm = new SiteDeleteViewForm();
		KgoApiException error = null;
		rs.setData(viewForm);
		try {
			String fileId = rq.getFileId();
			geoCaseSetSiteMainFileRepos.deleteById(Long.valueOf(fileId));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			error = new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_DELETE), e);
		} finally {
			setResultMessage(rq, rs, error);
		}
		return rs;
	}


//前台區塊


	public CaseSetSiteDetailRs siteDetailQuery( GeoSiteDetailQueryRq rq) {
		KgoApiException error = null;
		CaseSetSiteDetailRs rs = new CaseSetSiteDetailRs();
		GeoSiteDetailViewForm viewForm = new GeoSiteDetailViewForm();
		List<GeoCaseSetSiteDetailModel> siteModelList = new ArrayList<>();
		try{
			String[] siteMainIds = rq.getSiteMainId().split(",");
			for(String siteMainId : siteMainIds){
				GeoCaseSetSiteDetailModel rtnSiteModel = new GeoCaseSetSiteDetailModel();
				GeoKgoCaseSetSiteMain siteEntity = geoCaseSetSiteMainRepos.getById(siteMainId);
				rtnSiteModel.setSiteMainId(siteEntity.getSiteMainId());
				rtnSiteModel.setSiteAmount(siteEntity.getSiteAmount());
				rtnSiteModel.setSiteName(siteEntity.getSiteName());
				rtnSiteModel.setServiceHtml(siteEntity.getServiceHtml());
				//附件檔
				List<GeoKgoCaseSetSiteMainFile> sitefile = geoCaseSetSiteMainFileRepos.findAllBySiteMainId(siteEntity.getSiteMainId());
				rtnSiteModel.setFiles(sitefile.stream().map(f->{
					Map<String,String> map = new HashMap<>();
					map.put("fileName",f.getFileName());
					map.put("fileId",f.getSiteFileId().toString());
					return map;
				}).collect(Collectors.toList()));
				siteModelList.add(rtnSiteModel);
			}
			viewForm.setSiteDetail(siteModelList);
			rs.setMsg(SuccessMsg.UNKNOW.getMsg());
			rs.setData(viewForm);
		}catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			error = new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SEARCH), e);
		} finally {
			setResultMessage(rq, rs, error);
		}

		return rs;
	}

	/**取消預約驗證
	 *  已繳費: 繳費期限未逾期，退費前不可取消
	 *         繳費期限已過，再次通知使用者無法退費，是否取消
	 *  未繳費: 可取消預約       
	 */
	public CancelAppointmentRs cancelConfirm(CancelAppointmentRq rq) {
		if (StringUtils.isBlank(rq.getCaseId())) {
			throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
		}
		String caseId = rq.getCaseId();
		KgoApiException error = null;
		CancelAppointmentRs rs = new CancelAppointmentRs();
		CancelAppointmentViewForm viewForm = new CancelAppointmentViewForm();
		rs.setData(viewForm);
		viewForm.setValid(false);
		try{
			GeoKgoCaseRentPayment paymentEntity = geoKgoRentPaymentRepos.findByCaseId(caseId);
			GeoKgoCaseRentRelation relationEntity = geoCaseRentRelationRepos.findByCaseId(caseId);
			if( paymentEntity != null ){//需要繳費
				RentStatusEnum paymentStatus = RentStatusEnum.getRentStatusEnum(paymentEntity.getPaymentStatus());
				if(paymentStatus.equals(RentStatusEnum.FIN)){
					KgoCaseset caseset = kgoCasesetRepository.getById(relationEntity.getCasesetId());
					Integer refundDeadLine = caseset.getRefundDeadline();
					Timestamp now = DateUtil.getCurrentTimestamp();
					if( now.compareTo( DateUtil.modifyDate(relationEntity.getStartTime(),0,0,-refundDeadLine)) >= 0 ){
						//已超過退費期限
						viewForm.setValid(true);
						viewForm.setConfirmMsg("預約案件已超過退費期限，是否要取消預約 ?");
					}else{
						viewForm.setValid(false);
						viewForm.setConfirmMsg("預約案件尚未退費，請先進行退費申請。");
					}
				}else if( paymentStatus.equals(RentStatusEnum.YET) ){
					viewForm.setValid(true);
					viewForm.setConfirmMsg("是否要取消預約 ?");
				}
			}else{//不需要繳費 or 未確認繳費
				RentStatusEnum relationStatus = RentStatusEnum.getRentStatusEnum(relationEntity.getRentStatus());
				if(RentStatusEnum.PROC.equals(relationStatus) || RentStatusEnum.SUS.equals(relationStatus)){
					viewForm.setValid(true);
					viewForm.setConfirmMsg("是否要取消預約 ?");
				}
			}

		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			error = apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			error = new KgoApiException(new ErrorResult(KgoFrontEndApiError.RQ_NOT_COMPLETED), e);
		} finally {
			setResultMessage(rq, rs, error);
		}
		return rs;
	}
	
	/**
	 ** 前台取消預約
	 */
	@Transactional(rollbackFor = KgoApiException.class)
	public CancelAppointmentRs cancelAppointment(CancelAppointmentRq rq) {

		if (StringUtils.isBlank(rq.getCaseId())) {
			throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
		}
		String caseId = rq.getCaseId();
		OperationApiMemo memo = null;
		KgoApiException error = null;
		CancelAppointmentRs rs = new CancelAppointmentRs();
		CancelAppointmentViewForm viewForm = new CancelAppointmentViewForm();
		rs.setData(viewForm);

		try {
			//操作紀錄
			memo = super.genOperationMemo(SystemTypeEnum.F, SysLogExeType.TYPE_U, FrontendFunctionCodeEnum.RentalCase);

			KgoCaseMain caseMain = kgoCaseMainRepository.findByCaseId(caseId);
			String processId = caseMain.getProcessId();
			LOGGER.info("cancel Appointment caseId : "+caseId+" processId :" + processId);
			//activiti 取消流程
			if (processId != null) {
				//刪除流程實體
				ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
				if(processInstance != null){
					runtimeService.deleteProcessInstance(processId, "取消線上預約");
					caseMain.setStatus(CaseMainStatusEnum.S3.getValue());
				}
			}
			//儲存[KGO_CASE_MAIN]
			Timestamp currentTime = DateUtil.getCurrentTimestamp();
			caseMain.setResult("前台取消預約");
			caseMain.setUpdateTime(currentTime);
			caseMain.setUpdateUser("USER");
			kgoCaseMainRepository.save(caseMain);

			//更新預約狀態
			GeoKgoCaseRentRelation rentRelation = geoCaseRentRelationRepos.findByCaseId(caseId);
			rentRelation.setEditTime(currentTime);
			rentRelation.setRentStatus(RentStatusEnum.RCANL.getValue());

			geoCaseRentRelationRepos.save(rentRelation);

			//更新繳費狀態
			GeoKgoCaseRentPayment payEntity = geoKgoRentPaymentRepos.findByCaseId(caseId);
			if(payEntity!=null){
				payEntity.setEditTime(currentTime);
				payEntity.setPaymentStatus(RentStatusEnum.CANL.getValue());
				geoKgoRentPaymentRepos.save(payEntity);
			}
			GeoKgoCaseSetSiteMain siteMain = geoCaseSetSiteMainRepos.getById(rentRelation.getSiteMainId());
			String category = siteMain.getSiteType();
			//若為活動預約, 則釋放名額至 GEO_KGO_CASESET_RENT_TIME TODO:修正名額上限
			String[] rentTimeIds = rentRelation.getRentTimeId().split(",");
			for(String timeid : rentTimeIds) {
				Boolean isUpdate = true;
				if("activity".equals(category)){
					isUpdate = geoCaseSetSiteTimeReposCustom.cancelCaseRelease(timeid );
				}else if ("site".equals(category)){
					isUpdate = geoCaseSetSiteTimeReposCustom.cancelSiteCase(timeid );
				}
				if(!isUpdate) {
					LOGGER.info("caseId : "+caseId + " rentStatus :"+rentRelation.getRentStatus() +" cant update rentTime release ");
				}
			}


		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			error = apiE;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			error = new KgoApiException(new ErrorResult(KgoBackEndApiError.FAIL_TO_SAVE), e);
		} finally {
			List<OperationColumn> memoList = new ArrayList<>();
			memoList.add(new OperationColumn("取消預約，案件編號", rq.getCaseId()));
			memo.setMemoList(memoList);
			super.saveOperationLog(memo);
			setResultMessage(rq, rs, error);
		}
		return rs;
	}


	/**
	 ** 場地初始資訊
	 */
	public SiteManagementHomeRs siteManagementHome() {
		SiteManagementHomeViewForm viewForm = new SiteManagementHomeViewForm();
		SiteManagementHomeRs rs = new SiteManagementHomeRs();

		try {
			BackendLoginUserInfo userInfo = KgoUtil.getBackendLoginUser();
			viewForm.setCategoryComboBox(createCategogryComboBox(null));
			viewForm.setOrganComboBox(getOrganComboBox());
			viewForm.setUnitComboBox(getUnitComboBox(userInfo.getOrgan(),userInfo.getUnit()));
			/** 場地建立者 **/
			viewForm.setEditUserComboBox(getEditUserComboBox(userInfo.getUnit()));
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("caseManagementHome error " + e.getMessage(), e);
		}

		return rs;
	}
	/** Id格式 : prefix+yyyymmddMMss*/
	private String createSitMainId(){
		String prefix;
		prefix = SITE_CATEGORY_PREFIX;
		java.lang.String currentDateStr = DateUtil.timestampToString(DateUtil.getCurrentTimestamp(), MAINID_DATE_FORMAT);
		return  new StringBuilder().append(prefix).append(currentDateStr).toString();
	}

	private ComboBox getOrganComboBox(){
		BackendLoginUserInfo loginUser = KgoUtil.getBackendLoginUser();
		KgoGroupLevel kgl = kgoGroupLevelRepository.findByOrganId(loginUser.getOrgan());
		LOGGER.info("getOrganComboBox loginUser.kgl()="+kgl);
		LOGGER.info("getOrganComboBox loginUser.getOrgan()="+loginUser.getOrgan());
		ComboBox organComboBox = caseManagementServiceHelper.getCasesetOrganByLoginUserOrgan(loginUser.getOrgan(),kgl.getSeq().toString());
		return organComboBox;
	}

	private ComboBox getUnitComboBox(String organId,String defaultValue){
		return organUnitManagementServiceHelper.getUnitComboBoxByOrganId(organId, defaultValue,
				ComboBoxStatusEnum.ALL.getCode());
	}
	private ComboBox getEditUserComboBox(String unitId ){
		BackendLoginUserInfo loginUser = KgoUtil.getBackendLoginUser();
		List<GeoSiteEditUserModel> defaultUser = geoCaseSetSiteReposCustom.findSiteEditor(loginUser.getOrgan(),unitId);
		return  kgoServiceHelper.getComboBox(defaultUser,"userName","userId",null,ComboBoxStatusEnum.ALL.getCode(),true);
	}

	private ComboBox createCategogryComboBox(String category){
		List<CaseSetCategoryEnum> list = Arrays.asList(new CaseSetCategoryEnum[]{CaseSetCategoryEnum.ACTIVITY,CaseSetCategoryEnum.SITE});
		return KgoServiceHelper.getInstance().getComboBox(list, "label", "value", category, ComboBoxStatusEnum.ALL.getCode(), true);
	}
}	
