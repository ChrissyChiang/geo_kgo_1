package gov.kcg.kgo.service.impl;

import static gov.kcg.kgo.enums.common.ColumnTypeEnum.FIL;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import gov.kcg.kgo.enums.error.KgoCommonApiError;
import gov.kcg.kgo.geoentity.*;
import gov.kcg.kgo.geomodel.OrganDiscountRatioModel;
import gov.kcg.kgo.georepository.GeoOrganDiscountRatioRepository;
import gov.kcg.kgo.georepository.custom.GeoOrganDiscountRatioReposCustom;
import gov.kcg.kgo.model.*;
import gov.kcg.kgo.repository.*;
import gov.kcg.kgo.service.impl.helper.OrganUnitManagementServiceHelper;
import gov.kcg.kgo.viewModel.backend.common.organComboBox.query.rs.OrganComboBoxQueryRs;
import gov.kcg.kgo.viewModel.backend.common.organComboBox.query.rs.bean.OrganComboBoxQueryViewForm;
import gov.kcg.kgo.viewModel.backend.internetApply.save.rq.OrganDiscountRatioQueryRq;
import gov.kcg.kgo.viewModel.backend.internetApply.save.rs.OrganDiscountRatioDeleteRs;
import gov.kcg.kgo.viewModel.backend.internetApply.save.rs.bean.OrganDiscountRatioDeleteViewForm;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import gov.kcg.kgo.dto.AcceptSetAreaQueryDto;
import gov.kcg.kgo.dto.AcceptSetOfficerQueryDto;
import gov.kcg.kgo.dto.AcceptSetUnitQueryDto;
import gov.kcg.kgo.dto.CasesetGroupQueryDataMaxVersionDto;
import gov.kcg.kgo.dto.CasesetOrganGroupQueryDataMaxVersionDto;
import gov.kcg.kgo.enums.backend.ApplyTypeEnum;
import gov.kcg.kgo.enums.backend.CodeTypeEnum;
import gov.kcg.kgo.enums.backend.IsCHeckFrequencyEnum;
import gov.kcg.kgo.enums.backend.IsFieldCheckEnum;
import gov.kcg.kgo.enums.backend.IsMustKeyEnum;
import gov.kcg.kgo.enums.backend.MyDataCheckTypeEnum;
import gov.kcg.kgo.enums.backend.MyDataSTypeEnum;
import gov.kcg.kgo.enums.backend.TemplateIsDefaultEnum;
import gov.kcg.kgo.enums.backend.TemplateSuspendEnum;
import gov.kcg.kgo.enums.common.AcceptSetEnum;
import gov.kcg.kgo.enums.common.ColumnTypeEnum;
import gov.kcg.kgo.enums.common.SuccessMsg;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.geoenum.GeoBooleanType;
import gov.kcg.kgo.geoenum.GeoDistrictOfficeType;
import gov.kcg.kgo.geomodel.Geo1999ItemsMainModel;
import gov.kcg.kgo.georepository.GeoKgoCasesetColumnChildOrganRepository;
import gov.kcg.kgo.georepository.GeoKgoCasesetColumnOrganRepository;
import gov.kcg.kgo.georepository.GeoKgoCasesetGroupOrganRepository;
import gov.kcg.kgo.georepository.custom.GeoKgoCasesetOrganGroupReposCustom;
import gov.kcg.kgo.geoservice.GeoCityExtService;
import gov.kcg.kgo.service.CommonService;
import gov.kcg.kgo.service.InternetApplyService;
import gov.kcg.kgo.service.impl.helper.CommonServiceHelper;
import gov.kcg.kgo.service.impl.helper.InternetApplyServiceHelper;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptDate.home.rq.InternetApplyAcceptDateHomeRq;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptDate.home.rs.InternetApplyAcceptDateHomeRs;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptDate.home.rs.bean.InternetApplyAcceptDateHomeViewForm;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.areaQuery.rq.InternetApplyAcceptSetAreaQueryRq;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.areaQuery.rs.InternetApplyAcceptSetAreaQueryRs;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.areaQuery.rs.bean.InternetApplyAcceptSetAreaQueryDataGrid;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.areaQuery.rs.bean.InternetApplyAcceptSetAreaQueryViewForm;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.areaSave.rq.InternetApplyAcceptSetAreaSaveRq;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.areaSave.rq.bean.InternetApplyAcceptSetAreaSaveDataGrid;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.areaSave.rs.InternetApplyAcceptSetAreaSaveRs;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.areaSave.rs.bean.InternetApplyAcceptSetAreaSaveViewForm;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.home.rq.InternetApplyAcceptSetHomeRq;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.home.rs.InternetApplyAcceptSetHomeRs;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.home.rs.bean.InternetApplyAcceptSetHomeViewForm;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.officerQuery.rq.InternetApplyAcceptSetOfficerQueryRq;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.officerQuery.rs.InternetApplyAcceptSetOfficerQueryRs;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.officerQuery.rs.bean.InternetApplyAcceptSetOfficerQueryViewForm;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.officerSave.rq.InternetApplyAcceptSetOfficerSaveRq;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.officerSave.rs.InternetApplyAcceptSetOfficerSaveRs;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.officerSave.rs.bean.InternetApplyAcceptSetOfficerSaveViewForm;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.unitQuery.rq.InternetApplyAcceptSetUnitQueryRq;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.unitQuery.rs.InternetApplyAcceptSetUnitQueryRs;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.unitQuery.rs.bean.InternetApplyAcceptSetUnitQueryViewForm;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.unitSave.rq.InternetApplyAcceptSetUnitSaveRq;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.unitSave.rs.InternetApplyAcceptSetUnitSaveRs;
import gov.kcg.kgo.viewModel.backend.internetApply.acceptSet.unitSave.rs.bean.InternetApplyAcceptSetUnitSaveViewForm;
import gov.kcg.kgo.viewModel.backend.internetApply.description.delete.rq.InternetApplyDescriptionDeleteRq;
import gov.kcg.kgo.viewModel.backend.internetApply.description.delete.rs.InternetApplyDescriptionDeleteRs;
import gov.kcg.kgo.viewModel.backend.internetApply.description.delete.rs.bean.InternetApplyDescriptionDeleteViewForm;
import gov.kcg.kgo.viewModel.backend.internetApply.description.home.rq.InternetApplyDescriptionHomeRq;
import gov.kcg.kgo.viewModel.backend.internetApply.description.home.rs.InternetApplyDescriptionHomeRs;
import gov.kcg.kgo.viewModel.backend.internetApply.description.home.rs.bean.InternetApplyDescriptionHomeDataGrid;
import gov.kcg.kgo.viewModel.backend.internetApply.description.home.rs.bean.InternetApplyDescriptionHomeViewForm;
import gov.kcg.kgo.viewModel.backend.internetApply.description.save.rq.InternetApplyDescriptionSaveRq;
import gov.kcg.kgo.viewModel.backend.internetApply.description.save.rs.InternetApplyDescriptionSaveRs;
import gov.kcg.kgo.viewModel.backend.internetApply.description.save.rs.bean.InternetApplyDescriptionSaveViewForm;
import gov.kcg.kgo.viewModel.backend.internetApply.description.update.rq.InternetApplyDescriptionUpdateRq;
import gov.kcg.kgo.viewModel.backend.internetApply.description.update.rs.InternetApplyDescriptionUpdateRs;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.columnHome.rq.InternetApplyFormSetColumnHomeRq;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.columnHome.rs.InternetApplyFormSetColumnHomeRs;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.columnHome.rs.bean.InternetApplyFormSetColumnHomeViewForm;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rq.CasesetColumnData;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rq.CasesetComplexColumnData;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rq.CasesetOrganColumnData;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rq.CasesetOrganComplexColumnData;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rq.GroupColumnData;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rq.InternetApplyFormSetGroupColumnSaveRq;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rq.InternetApplyFormSetOrganGroupColumnSaveRq;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rq.OrganGroupColumnData;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rs.InternetApplyFormSetGroupColumnSaveRs;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rs.bean.InternetApplyFormSetGroupColumnSaveViewForm;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupHome.rq.InternetApplyFormSetGroupHomeRq;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupHome.rs.InternetApplyFormSetGroupHomeRs;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupHome.rs.bean.InternetApplyFormSetGroupHomeViewForm;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.home.rq.InternetApplyFormSetHomeRq;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.home.rs.InternetApplyFormSetHomeRs;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.home.rs.bean.InternetApplyFormSetGroupColumnDataGrid;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.home.rs.bean.InternetApplyFormSetHomeDataGrid;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.home.rs.bean.InternetApplyFormSetHomeOrganDataGrid;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.home.rs.bean.InternetApplyFormSetHomeViewForm;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.home.rs.bean.InternetApplyFormSetOrganGroupColumnDataGrid;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataColumnComboBox.rq.InternetApplyFormSetMyDataColumnComboBoxRq;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataColumnComboBox.rs.InternetApplyFormSetMyDataColumnComboBoxRs;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataColumnComboBox.rs.bean.InternetApplyFormSetMyDataColumnComboBoxViewForm;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataComboBox.rq.InternetApplyFormSetMydataComboBoxRq;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataComboBox.rs.InternetApplyFormSetMydataComboBoxRs;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataComboBox.rs.bean.InternetApplyFormSetMydataComboBoxViewForm;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataDelete.rq.InternetApplyFormSetMydataDeleteRq;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataDelete.rs.InternetApplyFormSetMydataDeleteRs;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataDelete.rs.bean.InternetApplyFormSetMydataDeleteViewForm;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataHome.rq.InternetApplyFormSetMydataHomeRq;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataHome.rs.InternetApplyFormSetMydataHomeRs;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataHome.rs.bean.InternetApplyFormSetMydataHomeDataGrid;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataHome.rs.bean.InternetApplyFormSetMydataHomeViewForm;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataSave.rq.InternetApplyFormSetMydataSaveRq;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataSave.rs.InternetApplyFormSetMydataSaveRs;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.mydataSave.rs.bean.InternetApplyFormSetMydataSaveViewForm;
import gov.kcg.kgo.viewModel.backend.internetApply.identityVerify.home.rq.InternetApplyIdentityVerifyHomeRq;
import gov.kcg.kgo.viewModel.backend.internetApply.identityVerify.home.rs.InternetApplyIdentityVerifyHomeRs;
import gov.kcg.kgo.viewModel.backend.internetApply.identityVerify.home.rs.bean.InternetApplyIdentityVerifyHomeViewForm;
import gov.kcg.kgo.viewModel.backend.internetApply.limitPeriod.home.rq.InternetApplyLimitPeriodHomeRq;
import gov.kcg.kgo.viewModel.backend.internetApply.limitPeriod.home.rs.InternetApplyLimitPeriodHomeRs;
import gov.kcg.kgo.viewModel.backend.internetApply.limitPeriod.home.rs.bean.InternetApplyLimitPeriodHomeViewForm;
import gov.kcg.kgo.viewModel.backend.internetApply.save.rq.InternetApplySaveRq;
import gov.kcg.kgo.viewModel.backend.internetApply.save.rs.InternetApplySaveRs;
import gov.kcg.kgo.viewModel.backend.internetApply.save.rs.OrganDiscountRatioQueryRs;
import gov.kcg.kgo.viewModel.backend.internetApply.save.rs.OrganDiscountRatioSaveRs;
import gov.kcg.kgo.viewModel.backend.internetApply.save.rs.bean.InternetApplySaveViewForm;
import gov.kcg.kgo.viewModel.backend.internetApply.save.rs.bean.OrganDiscountRatioQueryViewForm;
import gov.kcg.kgo.viewModel.backend.internetApply.save.rs.bean.OrganDiscountRatioSaveViewForm;
import gov.kcg.kgo.viewModel.backend.internetApply.serviceHtml.home.rq.InternetApplyServiceHtmlHomeRq;
import gov.kcg.kgo.viewModel.backend.internetApply.serviceHtml.home.rs.InternetApplyServiceHtmlHomeRs;
import gov.kcg.kgo.viewModel.backend.internetApply.serviceHtml.home.rs.bean.InternetApplyServiceHtmlHomeViewForm;
import gov.kcg.kgo.viewModel.compoent.checkBox.CheckBox;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;

@Transactional(rollbackFor = Exception.class)
@Service("InternetApplyService")
public class InternetApplyServiceImpl extends KgoBackEndServiceImpl implements InternetApplyService {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(InternetApplyServiceImpl.class);

	@Autowired
	private KgoCasesetRepository kgoCasesetRepository;

	@Autowired
	private KgoCasesetCheckRepository kgoCasesetCheckRepository;

	@Autowired
	private KgoCasesetUnitRepository kgoCasesetUnitRepository;

	@Autowired
	private KgoCasesetOfficerRepository kgoCasesetOfficerRepository;

	@Autowired
	private KgoCasesetAreaRepository kgoCasesetAreaRepository;

	@Autowired
	private KgoCasesetMemoRepository kgoCasesetMemoRepository;

	@Autowired
	private KgoCasesetGroupRepository kgoCasesetGroupRepository;

	@Autowired
	private KgoCasesetColumnRepository kgoCasesetColumnRepository;

	@Autowired
	private KgoCasesetMydataRepository kgoCasesetMydataRepository;

	@Autowired
	private KgoCasesetColumnChildRepository kgoCasesetColumnChildRepository;

	@Autowired
	private KgoMydataServiceRepository kgoMydataServiceRepository;

	@Autowired
	private KgoZipRepository kgoZipRepository;

	@Autowired
	private CommonService commonService;

	@Autowired
	private KgoCasesetTemplateRepository kgoCasesetTemplateRepository;

	@Autowired
	private KgoCasesetColumnTemplateRepository kgoCasesetColumnTemplateRepository;

	@Autowired
	private KgoCasesetColumnChildTemplateRepository kgoCasesetColumnChildTemplateRepository;

	@Autowired
	GeoKgoCasesetGroupOrganRepository geoKgoCasesetGroupOrganRepository;

	@Autowired
	GeoKgoCasesetOrganGroupReposCustom geoKgoCasesetOrganGroupReposCustom;

	@Autowired
	private GeoKgoCasesetColumnOrganRepository geoKgoCasesetColumnOrganRepository;

	@Autowired
	private GeoKgoCasesetColumnChildOrganRepository geoKgoCasesetColumnChildOrganRepository;
	
	@Autowired
	KgoCasesetRefundRatioRepository kgoCasesetRefundRatioRepository;
	
	@Autowired
	OrganRefundRatioRepository organRefundRatioRepository;
	
	@Autowired
	GeoOrganDiscountRatioRepository geoOrganDiscountRatioRepository;

	@Autowired
	GeoOrganDiscountRatioReposCustom geoOrganDiscountRatioReposCustom;

	@Autowired
	KgoOrganRepository kgoOrganRepository;

	private CommonServiceHelper commonServiceHelper = CommonServiceHelper.getInstance();
	private InternetApplyServiceHelper internetApplyServiceHelper = InternetApplyServiceHelper.getInstance();
	private OrganUnitManagementServiceHelper organUnitManagementServiceHelper =
			OrganUnitManagementServiceHelper.getInstance();

	/** GEO 20210817 add **/
	@Autowired
    GeoCityExtService geoKcgCityExtService;


	/**
	 * 網路申辦-服務宣告設定-初始畫面
	 * 
	 */
	@Override
	public InternetApplyServiceHtmlHomeRs internetApplyServiceHtmlHome(InternetApplyServiceHtmlHomeRq rq) {
		InternetApplyServiceHtmlHomeViewForm viewForm = new InternetApplyServiceHtmlHomeViewForm();
		InternetApplyServiceHtmlHomeRs rs = new InternetApplyServiceHtmlHomeRs();

		try {
			String caseSetId = rq.getCaseSetId();
			KgoCaseset kgoCaseset = kgoCasesetRepository.getOne(caseSetId);

			String serviceHtml = StringUtils.EMPTY;
			boolean isServiceHtml = ObjectUtils.isEmpty(kgoCaseset.getIsServiceHtml()) ? false
					: kgoCaseset.getIsServiceHtml().booleanValue();
			serviceHtml = kgoCaseset.getServiceHtml();

			CheckBox isServiceHtmlCheckBox = internetApplyServiceHelper.getIsServiceHtmlCheckBox(isServiceHtml);

			viewForm.setServiceHtml(serviceHtml);
			viewForm.setIsServiceHtmlCheckBox(isServiceHtmlCheckBox);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("serviceHtmlHome error " + e.getMessage(), e);
		}

		return rs;
	}

	/**
	 * 網路申辦-身分驗證設定-初始畫面
	 */
	@Override
	public InternetApplyIdentityVerifyHomeRs internetApplyIdentityVerifyHome(InternetApplyIdentityVerifyHomeRq rq) {
		InternetApplyIdentityVerifyHomeViewForm viewForm = new InternetApplyIdentityVerifyHomeViewForm();
		InternetApplyIdentityVerifyHomeRs rs = new InternetApplyIdentityVerifyHomeRs();

		try {
			List<CheckBox> identityVerifyCheckBox = internetApplyServiceHelper.getVerifyCheckBox(rq.getCaseSetId());
			viewForm.setIdentityVerifyCheckBox(identityVerifyCheckBox);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("identityVerifyHome error " + e.getMessage(), e);
		}

		return rs;
	}

	/**
	 * 網路申辦-受理機關設定-初始畫面， 預設給[受理機關]資料
	 */
	@Override
	public InternetApplyAcceptSetHomeRs internetApplyAcceptSetHome(InternetApplyAcceptSetHomeRq rq) {
		InternetApplyAcceptSetHomeViewForm viewForm = new InternetApplyAcceptSetHomeViewForm();
		InternetApplyAcceptSetHomeRs rs = new InternetApplyAcceptSetHomeRs();

		try {
			String caseSetId = rq.getCaseSetId();
			List<AcceptSetUnitQueryDto> kgoCasesetUnitList = kgoCasesetUnitRepository.findByIdCaseSetId(caseSetId);

			// 如果案件有設定受理機關，則必須選取案件所設定之受理機關
			AcceptSetEnum defaultAcceptSet = AcceptSetEnum.UNIT;
			KgoCaseset kgoCaseset = kgoCasesetRepository.getOne(caseSetId);
			if (kgoCaseset != null && StringUtils.isNotBlank(kgoCaseset.getAcceptSet())) {
				defaultAcceptSet = AcceptSetEnum.getEnum(kgoCaseset.getAcceptSet());
			}
			ComboBox acceptSetComboBox =
					commonServiceHelper.getComboBoxWithEnum(AcceptSetEnum.class, defaultAcceptSet.getValue());

			viewForm.setAcceptSetComboBox(acceptSetComboBox);
			viewForm.setGrid(kgoCasesetUnitList);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("acceptSetHome error " + e.getMessage(), e);
		}

		return rs;
	}

	/**
	 * 網路申辦-受理機關設定-受理機關查詢
	 */
	@Override
	public InternetApplyAcceptSetUnitQueryRs internetApplyAcceptSetUnitQuery(InternetApplyAcceptSetUnitQueryRq rq) {
		InternetApplyAcceptSetUnitQueryViewForm viewForm = new InternetApplyAcceptSetUnitQueryViewForm();
		InternetApplyAcceptSetUnitQueryRs rs = new InternetApplyAcceptSetUnitQueryRs();

		try {
			String caseSetId = rq.getCaseSetId();
			List<AcceptSetUnitQueryDto> kgoCasesetUnitList = kgoCasesetUnitRepository.findByIdCaseSetId(caseSetId);

			viewForm.setGrid(kgoCasesetUnitList);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("acceptSetUnitQuery error " + e.getMessage(), e);
		}

		return rs;
	}

	/**
	 * 網路申辦-受理機關設定-承辦人查詢
	 */
	@Override
	public InternetApplyAcceptSetOfficerQueryRs internetApplyAcceptSetOfficerQuery(
			InternetApplyAcceptSetOfficerQueryRq rq) {
		InternetApplyAcceptSetOfficerQueryViewForm viewForm = new InternetApplyAcceptSetOfficerQueryViewForm();
		InternetApplyAcceptSetOfficerQueryRs rs = new InternetApplyAcceptSetOfficerQueryRs();

		try {
			String caseSetId = rq.getCaseSetId();
			List<AcceptSetOfficerQueryDto> kgoCasesetOfficerList =
					kgoCasesetOfficerRepository.getCasesetOfficerDataByCaseSetId(caseSetId);
			viewForm.setGrid(kgoCasesetOfficerList);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("acceptSetOfficerQuery error " + e.getMessage(), e);
		}

		return rs;
	}

	/**
	 * 網路申辦-受理機關設定-受理區機關查詢
	 * 
	 * @param rq
	 * @return
	 */
	@Override
	public InternetApplyAcceptSetAreaQueryRs internetApplyAcceptSetAreaQuery(InternetApplyAcceptSetAreaQueryRq rq) {
		InternetApplyAcceptSetAreaQueryViewForm viewForm = new InternetApplyAcceptSetAreaQueryViewForm();
		InternetApplyAcceptSetAreaQueryRs rs = new InternetApplyAcceptSetAreaQueryRs();

		try {
			String caseSetId = rq.getCaseSetId();
			List<AcceptSetAreaQueryDto> areaList = kgoCasesetAreaRepository.getAreaDataByCaseSetId(caseSetId);

			List<InternetApplyAcceptSetAreaQueryDataGrid> grid = areaList.stream().map(l -> {
				InternetApplyAcceptSetAreaQueryDataGrid dg = new InternetApplyAcceptSetAreaQueryDataGrid();

				List<String> zipList = Arrays.asList(l.getZip().split(","));
				String zipNameStr = kgoZipRepository.findAllById(zipList).stream().map(KgoZip::getZIPName)
						.collect(Collectors.joining(","));

				dg.setOrgan(l.getOrgan());
				dg.setOrganName(l.getOrganName());
				dg.setZip(l.getZip());
				dg.setZipName(zipNameStr);

				return dg;
			}).collect(Collectors.toList());

			viewForm.setGrid(grid);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("internetApplyAcceptSetAreaQuery error " + e.getMessage(), e);
		}

		return rs;
	}

	/**
	 * 網路申辦-受理機關設定-受理機關新增
	 */
	@Override
	public InternetApplyAcceptSetUnitSaveRs internetApplyAcceptSetUnitSave(InternetApplyAcceptSetUnitSaveRq rq) {
		InternetApplyAcceptSetUnitSaveViewForm viewForm = new InternetApplyAcceptSetUnitSaveViewForm();
		InternetApplyAcceptSetUnitSaveRs rs = new InternetApplyAcceptSetUnitSaveRs();

		try {
			String caseSetId = rq.getCaseSetId();
			List<String> grid = rq.getGrid();

			kgoCasesetUnitRepository.deleteByCaseSetId(caseSetId);

			grid.forEach(s -> {
				KgoCasesetUnit kgoCasesetUnit = new KgoCasesetUnit();
				KgoCasesetUnitPK id = new KgoCasesetUnitPK();
				id.setCaseSetId(caseSetId);
				id.setOrgan(s);
				kgoCasesetUnit.setId(id);
				kgoCasesetUnitRepository.save(kgoCasesetUnit);
			});

			viewForm.setMsg(SuccessMsg.SAVE.getMsg());
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SAVE.getErrorMsgKey());
			throw new KgoApiException("acceptSetUnitSave error " + e.getMessage(), e);
		}

		return rs;
	}

	/**
	 * 網路申辦-受理機關設定-承辦人新增
	 */
	@Override
	public InternetApplyAcceptSetOfficerSaveRs internetApplyAcceptSetOfficerSave(
			InternetApplyAcceptSetOfficerSaveRq rq) {
		InternetApplyAcceptSetOfficerSaveViewForm viewForm = new InternetApplyAcceptSetOfficerSaveViewForm();
		InternetApplyAcceptSetOfficerSaveRs rs = new InternetApplyAcceptSetOfficerSaveRs();

		try {
			String caseSetId = rq.getCaseSetId();
			List<String> grid = rq.getGrid();

			kgoCasesetOfficerRepository.deleteByCaseSetId(caseSetId);

			grid.forEach(s -> {
				KgoCasesetOfficer kgoCasesetOfficer = new KgoCasesetOfficer();
				KgoCasesetOfficerPK id = new KgoCasesetOfficerPK();
				id.setCaseSetId(caseSetId);
				id.setCaseOfficer(s);
				kgoCasesetOfficer.setId(id);
				kgoCasesetOfficerRepository.save(kgoCasesetOfficer);
			});

			viewForm.setMsg(SuccessMsg.SAVE.getMsg());
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SAVE.getErrorMsgKey());
			throw new KgoApiException("acceptSetOfficerSave error " + e.getMessage(), e);
		}

		return rs;
	}

	/**
	 * 網路申辦-受理機關設定-受理區機關新增
	 * 
	 * @param rq
	 * @return
	 */
	@Override
	public InternetApplyAcceptSetAreaSaveRs internetApplyAcceptSetAreaSave(InternetApplyAcceptSetAreaSaveRq rq) {
		InternetApplyAcceptSetAreaSaveViewForm viewForm = new InternetApplyAcceptSetAreaSaveViewForm();
		InternetApplyAcceptSetAreaSaveRs rs = new InternetApplyAcceptSetAreaSaveRs();

		try {
			String caseSetId = rq.getCaseSetId();
			List<InternetApplyAcceptSetAreaSaveDataGrid> grid = rq.getGrid();

			kgoCasesetAreaRepository.deleteByIdCaseSetId(caseSetId);

			List<KgoCasesetArea> kgoCasesetAreaList = grid.stream().map(l -> {
				KgoCasesetAreaPK id = new KgoCasesetAreaPK();
				id.setCaseSetId(caseSetId);
				id.setOrgan(l.getOrgan());
				KgoCasesetArea kgoCasesetArea = new KgoCasesetArea();
				kgoCasesetArea.setId(id);
				kgoCasesetArea.setZip(l.getZip());
				return kgoCasesetArea;
			}).collect(Collectors.toList());

			kgoCasesetAreaRepository.saveAll(kgoCasesetAreaList);
			viewForm.setMsg(SuccessMsg.SAVE.getMsg());
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SAVE.getErrorMsgKey());
			throw new KgoApiException("internetApplyAcceptSetAreaSave error " + e.getMessage(), e);
		}

		return rs;
	}

	/**
	 * 網路申辦-受理期間設定-初始畫面
	 */
	@Override
	public InternetApplyAcceptDateHomeRs internetApplyAcceptDateHome(InternetApplyAcceptDateHomeRq rq) {
		InternetApplyAcceptDateHomeViewForm viewForm = new InternetApplyAcceptDateHomeViewForm();
		InternetApplyAcceptDateHomeRs rs = new InternetApplyAcceptDateHomeRs();

		try {
			String caseSetId = rq.getCaseSetId();
			KgoCaseset kgoCaseset = kgoCasesetRepository.getOne(caseSetId);
			viewForm.setDateStart(kgoCaseset.getDateStart());
			viewForm.setDateEnd(kgoCaseset.getDateEnd());
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("acceptDateHome error " + e.getMessage(), e);
		}

		return rs;
	}

	/**
	 * 網路申辦-限辦期限設定-初始畫面
	 */
	@Override
	public InternetApplyLimitPeriodHomeRs internetApplyLimitPeriodHome(InternetApplyLimitPeriodHomeRq rq) {
		InternetApplyLimitPeriodHomeViewForm viewForm = new InternetApplyLimitPeriodHomeViewForm();
		InternetApplyLimitPeriodHomeRs rs = new InternetApplyLimitPeriodHomeRs();

		try {
			String caseSetId = rq.getCaseSetId();
			KgoCaseset kgoCaseset = kgoCasesetRepository.getOne(caseSetId);
			viewForm.setLimitPeriod(kgoCaseset.getLimitedPeriod());
			viewForm.setMail(kgoCaseset.getMail());
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("limitPeriodHome error " + e.getMessage(), e);
		}

		return rs;
	}

	/**
	 * 網路申辦-網路申請說明-初始畫面
	 * 
	 * @param rq
	 * @return
	 */
	@Override
	public InternetApplyDescriptionHomeRs internetApplyDescriptionHome(InternetApplyDescriptionHomeRq rq) {
		InternetApplyDescriptionHomeViewForm viewForm = new InternetApplyDescriptionHomeViewForm();
		InternetApplyDescriptionHomeRs rs = new InternetApplyDescriptionHomeRs();

		try {
			String caseSetId = rq.getCaseSetId();
			String applyType = ApplyTypeEnum.E.getValue();

			List<KgoCasesetMemo> KgoCasesetMemoList =
					kgoCasesetMemoRepository.findAllByCaseSetIdAndApplyType(caseSetId, applyType);
			List<InternetApplyDescriptionHomeDataGrid> internetApplyDescriptionHomeDataGridList =
					new LinkedList<InternetApplyDescriptionHomeDataGrid>();

			KgoCasesetMemoList.forEach(l -> {
				InternetApplyDescriptionHomeDataGrid grid = new InternetApplyDescriptionHomeDataGrid();
				grid.setContentHtml(l.getContentHtml());
				grid.setFileName(l.getFileName());
				grid.setTitle(l.getTitle());
				grid.setSeq(l.getSeq());
				internetApplyDescriptionHomeDataGridList.add(grid);
			});

			viewForm.setGrid(internetApplyDescriptionHomeDataGridList);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("internetApplyDescriptionHome error " + e.getMessage(), e);
		}

		return rs;
	}

	/**
	 * 網路申辦-申請說明資料儲存
	 */
	@Override
	public InternetApplyDescriptionSaveRs internetApplyDescriptionSave(InternetApplyDescriptionSaveRq rq) {
		InternetApplyDescriptionSaveViewForm viewForm = new InternetApplyDescriptionSaveViewForm();
		InternetApplyDescriptionSaveRs rs = new InternetApplyDescriptionSaveRs();
		try {
			KgoCasesetMemo kgoCasesetMemo = new KgoCasesetMemo();
			kgoCasesetMemo.setSeq(rq.getSeq());
			kgoCasesetMemo.setCaseSetId(rq.getCaseSetId());
			kgoCasesetMemo.setTitle( rq.getTitle());
			kgoCasesetMemo.setApplyType(ApplyTypeEnum.E.getValue());
			kgoCasesetMemo.setContentHtml(rq.getContentHtml());
			kgoCasesetMemo.setFileName(rq.getFileName());
			kgoCasesetMemoRepository.save(kgoCasesetMemo);

//			kgoCasesetMemoRepository.saveData(caseSetId, ApplyTypeEnum.E.getValue(), title, contentHtml, fileName);

			viewForm.setMsg(SuccessMsg.SAVE.getMsg());
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SAVE.getErrorMsgKey());
			throw new KgoApiException("internetApplyDescriptionSave error " + e.getMessage(), e);
		}
		return rs;
	}

	/**
	 * 網路申辦-申請說明資料-資料更新
	 */
	@Override
	public InternetApplyDescriptionUpdateRs internetApplyDescriptionUpdate(InternetApplyDescriptionUpdateRq rq) {
		InternetApplyDescriptionUpdateRs rs = new InternetApplyDescriptionUpdateRs();
		try {
			Integer seq = rq.getSeq();
			String caseSetId = rq.getCaseSetId();
			String title = rq.getTitle();
			String contentHtml = rq.getContentHtml();
			String fileName = rq.getFileName();
			AtomicBoolean updateResult = new AtomicBoolean(false);

			Optional<KgoCasesetMemo> casesetMemoById = kgoCasesetMemoRepository.findById(seq);
			casesetMemoById.ifPresent( cm -> {
				//若資料已存在, 且fileName不為空, 且檔名有更動時, 需刪除該筆資料附件
				if(StringUtils.isNotBlank(cm.getFileName()) && StringUtils.isNotBlank(fileName)){
					if(!fileName.equalsIgnoreCase(cm.getFileName())){
						String attachmentUploadBasePath =
								commonService.getAttachmentUploadBasePath(caseSetId, ApplyTypeEnum.E.getValue());
						Path path = Paths.get(attachmentUploadBasePath + cm.getFileName());
						try {
							LOGGER.debug("Delete old file [{}]", path);
							Files.deleteIfExists(path);
						} catch (IOException e) {
							LOGGER.error("IOException occurred when update data", e);
						}
					}
				}
				cm.setContentHtml(contentHtml);
				cm.setFileName(fileName);
				cm.setTitle(title);
				kgoCasesetMemoRepository.save(cm);
				rs.setMsg(SuccessMsg.UPDATE.getMsg());
				updateResult.set(true);
			});
			if(!updateResult.get()){
				rs.setError(new ErrorResult(KgoBackEndApiError.FAIL_TO_EDIT));
			}

		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_EDIT.getErrorMsgKey());
			throw new KgoApiException("internetApplyDescriptionUpdate error " + e.getMessage(), e);
		}
		return rs;
	}

	/**
	 * 網路申辦-申請說明資料刪除
	 */
	@Override
	public InternetApplyDescriptionDeleteRs internetApplyDescriptionDelete(InternetApplyDescriptionDeleteRq rq) {
		InternetApplyDescriptionDeleteViewForm viewForm = new InternetApplyDescriptionDeleteViewForm();
		InternetApplyDescriptionDeleteRs rs = new InternetApplyDescriptionDeleteRs();
		try {
			Integer seq = rq.getSeq();
			String fileName = rq.getFileName();
			// set attachment upload base path
			String attachmentUploadBasePath =
					commonService.getAttachmentUploadBasePath(rq.getCaseSetId(), ApplyTypeEnum.E.getValue());

			/** delete KGO_CASESET_MEMO data **/
			kgoCasesetMemoRepository.deleteById(seq);

			/** delete file **/
			// Delete file when fileName is not empty, modify by Jay 20201202
			if(StringUtils.isNotBlank(fileName)){
				Path path = Paths.get(attachmentUploadBasePath + fileName);
				LOGGER.debug("Remove File [{}]", path.toString());
				Files.deleteIfExists(path);
			}

			viewForm.setMsg(SuccessMsg.DELETE.getMsg());
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_DELETE.getErrorMsgKey());
			throw new KgoApiException("internetApplyDescriptionDelete error " + e.getMessage(), e);
		}
		return rs;
	}

	/**
	 * 網路申辦-表單設定-初始畫面
	 */
	@Override
	public InternetApplyFormSetHomeRs internetApplyFormSetHome(InternetApplyFormSetHomeRq rq) {
		InternetApplyFormSetHomeViewForm viewForm = new InternetApplyFormSetHomeViewForm();
		InternetApplyFormSetHomeRs rs = new InternetApplyFormSetHomeRs();
		try {
			List<InternetApplyFormSetHomeDataGrid> dataGridList = new LinkedList<InternetApplyFormSetHomeDataGrid>();
			/** GEO 20211109 add 機關審核表單*/
			List<InternetApplyFormSetHomeOrganDataGrid> organFormDataGridList = new LinkedList<InternetApplyFormSetHomeOrganDataGrid>();

			String caseSetId = rq.getCaseSetId();
			LOGGER.info("InternetApplyServiceImpl internetApplyFormSetHome caseSetId: "+caseSetId);
			addBasicGroupColumnData(caseSetId);

			List<CasesetGroupQueryDataMaxVersionDto> dtoList =
					kgoCasesetGroupRepository.findMaxVersionGroupData(caseSetId, StringUtils.EMPTY);
			if (dtoList != null ) {
				LOGGER.info("InternetApplyServiceImpl internetApplyFormSetHome dtoList.size(): "+dtoList.size());
			} else {
				LOGGER.info("InternetApplyServiceImpl internetApplyFormSetHome dtoList.size(): "+dtoList);
			}
			Integer version = ObjectUtils.isEmpty(dtoList) ? KgoUtil.DEFAULT_VERSION_NUMBER : dtoList.get(0).getVersion();

			/** GEO 20211109 add 機關審核表單*/
			List<CasesetOrganGroupQueryDataMaxVersionDto> organDtoList = geoKgoCasesetOrganGroupReposCustom.findMaxVersionGroupData(caseSetId, StringUtils.EMPTY);
			if (organDtoList != null ) {
				LOGGER.info("InternetApplyServiceImpl internetApplyFormSetHome dtoList.size(): "+dtoList.size());
			} else {
				LOGGER.info("InternetApplyServiceImpl internetApplyFormSetHome dtoList.size(): "+dtoList);
			}
			Integer organVersion = ObjectUtils.isEmpty(organDtoList) ? KgoUtil.DEFAULT_VERSION_NUMBER : organDtoList.get(0).getOrganFormVersion();

			if (ObjectUtils.isNotEmpty(dtoList)) {
				dtoList.forEach(l -> {
					List<KgoCasesetColumn> KgoCasesetColumnList = kgoCasesetColumnRepository
							.findByIdCaseSetIdAndGroupSeqOrderByOrderNumAsc(caseSetId, l.getGroupSeq());
					LOGGER.info("InternetApplyServiceImpl internetApplyFormSetHome KgoCasesetColumnList.size(): "+KgoCasesetColumnList.size());

					List<InternetApplyFormSetGroupColumnDataGrid> internetApplyFormSetGroupColumnQueryDtoList =
							KgoCasesetColumnList.stream().map(dl -> {

								List<List<CasesetComplexColumnData>> complexDataList = null;
								if (dl.getColumnType().equalsIgnoreCase(ColumnTypeEnum.M.getValue())) {
									Map<Integer, List<KgoCasesetColumnChild>> dataMap = kgoCasesetColumnChildRepository
											.findByIdCaseSetIdAndIdVersionAndIdColumnIdOrderByOrderNumAsc(caseSetId,
													version, dl.getId().getColumnId())
											.stream().collect(Collectors.groupingBy(KgoCasesetColumnChild::getRow,
													HashMap::new, Collectors.toCollection(LinkedList::new)));
									complexDataList = dataMap.keySet().stream().map(i -> {
										return dataMap.get(i).stream().map(cl -> {
											String vGroup = StringUtils.isBlank(cl.getVGroup()) ? StringUtils.EMPTY
													: cl.getVGroup();
											CasesetComplexColumnData complexData = new CasesetComplexColumnData();
											complexData.setbText(cl.getBText());
											complexData.setcColumnId(cl.getId().getCColumnId());
											complexData.setColumnType(cl.getColumnType());
											complexData.setColumnValue(cl.getColumnValue());
											complexData.setfText(cl.getFText());
											complexData.setLength(cl.getLength());
											complexData.setIsMustKey(
													IsMustKeyEnum.getIsMustKeyEnum(cl.getIsMustKey()).getValue());
											/**GEO 20211019 add */
											complexData.setIsCheckFrequency(cl.getIsCheckFrequency());
											/** GEO 20211102 add 欄位勾選*/
											complexData.setIsFieldCheck(cl.getIsFieldCheck());

											complexData.setOrderNum(cl.getOrderNum());
											complexData.setpColumnId(cl.getPColumnId());
											complexData.setRow(cl.getRow());
											complexData.setvGroup(vGroup);
											return complexData;
										}).collect(Collectors.toList());
									}).collect(Collectors.toList());
								}

								InternetApplyFormSetGroupColumnDataGrid dg =
										new InternetApplyFormSetGroupColumnDataGrid();
								dg.setColumnId(dl.getId().getColumnId());
								dg.setColumnName(dl.getColumnName());
								dg.setColumnType(dl.getColumnType());
								dg.setColumnValue(dl.getColumnValue());
								dg.setColumnTypeName(ColumnTypeEnum.getColumnTypeEnum(dl.getColumnType()).getLabel());
								IsMustKeyEnum aEnum = IsMustKeyEnum.getIsMustKeyEnum(dl.getIsMustKey());
								dg.setIsMustKey(aEnum.getValue());
								dg.setIsMustKeyStr(aEnum.getLabel());
								/**GEO 20211019 add */
								IsCHeckFrequencyEnum bEnum = IsCHeckFrequencyEnum.getIsCheckFrequencyEnum(dl.getIsCheckFrequency());
								dg.setIsCheckFrequency(bEnum.getValue());
								dg.setIsCheckFrequencyStr(bEnum.getLabel());
								/** GEO 20211102 add 欄位勾選*/
								IsFieldCheckEnum fieldCheckEnum = IsFieldCheckEnum.getIsFieldCheckEnum(dl.getIsFieldCheck());
								dg.setIsFieldCheck(fieldCheckEnum.getValue());
								dg.setIsFieldCheckStr(fieldCheckEnum.getLabel());

								dg.setLength(dl.getLength());
								dg.setMemo(dl.getMemo());
								dg.setMyDataCheckType(dl.getMyDataCheckType());
								dg.setMyDataId(dl.getMyDataId());
								dg.setMyDataCheckValue(dl.getMyDataCheckValue());
								dg.setMyDataColumn(dl.getMyDataColumn());
								dg.setOrderNum(dl.getOrderNum());
								if(FIL.getValue().equals(dl.getColumnType())){//if ColumnType is Fil, set FileType 20201208 By Jay
									dg.setFileType(dl.getFileType());
								}

								dg.setComplex(complexDataList);
								return dg;
							}).collect(Collectors.toList());

					InternetApplyFormSetHomeDataGrid dataGrid = new InternetApplyFormSetHomeDataGrid();
					dataGrid.setColumnData(internetApplyFormSetGroupColumnQueryDtoList);
					dataGrid.setGroupSeq(l.getGroupSeq());
					/** GEO 20211203 add 重複檢核 */
					dataGrid.setCheckFrequencyPeriod(l.getCheckFrequencyPeriod());
					dataGrid.setGroupName(l.getMemo());
					dataGrid.setOrderNum(l.getOrderNum());
					dataGridList.add(dataGrid);
				});
			}

			/** GEO 20211109 add 機關審核表單 */
			if (ObjectUtils.isNotEmpty(organDtoList)) {
				organDtoList.forEach(l -> {
					List<GeoKgoCasesetColumnOrgan> KgoCasesetOrganColumnList = geoKgoCasesetColumnOrganRepository
							.findByIdCaseSetIdAndGroupSeqOrderByOrderNumAsc(caseSetId, l.getGroupSeq());
					LOGGER.info("InternetApplyServiceImpl internetApplyFormSetHome KgoCasesetOrganColumnList.size(): "+KgoCasesetOrganColumnList.size());

					List<InternetApplyFormSetOrganGroupColumnDataGrid> internetApplyFormSetGroupColumnQueryDtoList =
							KgoCasesetOrganColumnList.stream().map(dl -> {
								List<List<CasesetOrganComplexColumnData>> complexDataList = null;
								if (dl.getColumnType().equalsIgnoreCase(ColumnTypeEnum.M.getValue())) {
									Map<Integer, List<GeoKgoCasesetColumnChildOrgan>> dataMap = geoKgoCasesetColumnChildOrganRepository
											.findByIdCaseSetIdAndIdVersionAndIdColumnIdAndIdCaseFormVersionOrderByOrderNumAsc(caseSetId, version, dl.getId().getColumnId(), organVersion)
											.stream().collect(Collectors.groupingBy(GeoKgoCasesetColumnChildOrgan::getRow, HashMap::new, Collectors.toCollection(LinkedList::new)));
									complexDataList = dataMap.keySet().stream().map(i -> {
										return dataMap.get(i).stream().map(cl -> {
											String vGroup = StringUtils.isBlank(cl.getVGroup()) ? StringUtils.EMPTY : cl.getVGroup();
											CasesetOrganComplexColumnData complexData = new CasesetOrganComplexColumnData();
											complexData.setbText(cl.getBText());
											complexData.setcColumnId(cl.getId().getCColumnId());
											complexData.setColumnType(cl.getColumnType());
											complexData.setColumnValue(cl.getColumnValue());
											complexData.setfText(cl.getFText());
											complexData.setLength(cl.getLength());
											complexData.setIsMustKey(IsMustKeyEnum.getIsMustKeyEnum(cl.getIsMustKey()).getValue());
											complexData.setIsCheckFrequency(cl.getIsCheckFrequency());
											complexData.setIsFieldCheck(cl.getIsFieldCheck());
											complexData.setOrderNum(cl.getOrderNum());
											complexData.setpColumnId(cl.getPColumnId());
											complexData.setRow(cl.getRow());
											complexData.setCaseFormVersion(cl.getId().getCaseFormVersion());
											complexData.setvGroup(vGroup);
											return complexData;
										}).collect(Collectors.toList()); //return dataMap.get(i).stream()
									}).collect(Collectors.toList()); //complexDataList = dataMap.keySet()
								} //if (dl.getColumnType()...

								InternetApplyFormSetOrganGroupColumnDataGrid dg = new InternetApplyFormSetOrganGroupColumnDataGrid();
								dg.setColumnId(dl.getId().getColumnId());
								dg.setColumnName(dl.getColumnName());
								dg.setColumnType(dl.getColumnType());
								dg.setColumnValue(dl.getColumnValue());
								dg.setColumnTypeName(ColumnTypeEnum.getColumnTypeEnum(dl.getColumnType()).getLabel());

								IsMustKeyEnum aEnum = IsMustKeyEnum.getIsMustKeyEnum(dl.getIsMustKey());
								dg.setIsMustKey(aEnum.getValue());
								dg.setIsMustKeyStr(aEnum.getLabel());

								IsCHeckFrequencyEnum bEnum = IsCHeckFrequencyEnum.getIsCheckFrequencyEnum(dl.getIsCheckFrequency());
								dg.setIsCheckFrequency(bEnum.getValue());
								dg.setIsCheckFrequencyStr(bEnum.getLabel());

								IsFieldCheckEnum fieldCheckEnum = IsFieldCheckEnum.getIsFieldCheckEnum(dl.getIsFieldCheck());
								dg.setIsFieldCheck(fieldCheckEnum.getValue());
								dg.setIsFieldCheckStr(fieldCheckEnum.getLabel());

								dg.setLength(dl.getLength());
								dg.setMemo(dl.getMemo());
								dg.setOrderNum(dl.getOrderNum());
								if(FIL.getValue().equals(dl.getColumnType())) dg.setFileType(dl.getFileType());
								dg.setComplex(complexDataList);
								return dg;
							}).collect(Collectors.toList()); //List<InternetApplyFormSetOrganGroupColumnDataGrid>

					InternetApplyFormSetHomeOrganDataGrid dataGrid = new InternetApplyFormSetHomeOrganDataGrid();
					dataGrid.setColumnData(internetApplyFormSetGroupColumnQueryDtoList);
					dataGrid.setGroupSeq(l.getGroupSeq());
					dataGrid.setGroupName(l.getMemo());
					dataGrid.setOrderNum(l.getOrderNum());
					dataGrid.setIsShow(l.getIsShow());
					organFormDataGridList.add(dataGrid);
				}); //organDtoList.forEach
			} //if (ObjectUtils.isNotEmpty(organDtoList))

			viewForm.setVersion(version);
			viewForm.setCaseSetId(caseSetId);
			viewForm.setGrid(dataGridList);
			/** GEO 20211109 add 機關審核表單 */
			viewForm.setOrganFormGrid(organFormDataGridList);
			viewForm.setOrganFormVersion(organVersion);

			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("internetApplyFormSetHome error " + e.getMessage(), e);
		}
		return rs;
	} //internetApplyFormSetHome

	/**
	 * 網路申辦-表單設定-群組維護-初始畫面
	 * 
	 * @param rq
	 * @return
	 */
	@Override
	public InternetApplyFormSetGroupHomeRs internetApplyFormSetGroupHome(InternetApplyFormSetGroupHomeRq rq) {
		InternetApplyFormSetGroupHomeViewForm viewForm = new InternetApplyFormSetGroupHomeViewForm();
		InternetApplyFormSetGroupHomeRs rs = new InternetApplyFormSetGroupHomeRs();
		try {
			Integer version = null;
			String caseSetId = rq.getCaseSetId();
			List<CasesetGroupQueryDataMaxVersionDto> dtoList =
					kgoCasesetGroupRepository.findMaxVersionGroupData(caseSetId, StringUtils.EMPTY);
			if (!ObjectUtils.isEmpty(dtoList)) {
				version = dtoList.get(0).getVersion();
			}
			viewForm.setGrid(dtoList);
			viewForm.setVerison(version);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("internetApplyFormSetGroupHome error " + e.getMessage(), e);
		}
		return rs;
	}

	/**
	 * 網路申辦-表單設定-欄位維護-初始畫面
	 * 
	 * @param rq
	 * @return
	 */
	@Override
	public InternetApplyFormSetColumnHomeRs internetApplyFormSetColumnHome(InternetApplyFormSetColumnHomeRq rq) {
		InternetApplyFormSetColumnHomeViewForm viewForm = new InternetApplyFormSetColumnHomeViewForm();
		InternetApplyFormSetColumnHomeRs rs = new InternetApplyFormSetColumnHomeRs();

		try {
			String caseSetId = rq.getCaseSetId();
			String myDataCheckValue = StringUtils.EMPTY;
			ComboBox columnTypeComboBox = commonServiceHelper.getComboBoxWithEnum(ColumnTypeEnum.class);
			ComboBox fileTypeComboBox = commonServiceHelper.getCodeTypeComboBox(CodeTypeEnum.FILE_TYPE.getValue());
			ComboBox isMustKeyComboBox = commonServiceHelper.getComboBoxWithEnum(IsMustKeyEnum.class);
			/**GEO 20211019 add */
			ComboBox isCheckFrequencyComboBox = commonServiceHelper.getComboBoxWithEnum(IsCHeckFrequencyEnum.class);
			/** GEO 20211102 add 欄位勾選*/
			ComboBox isFieldCheckComboBox = commonServiceHelper.getComboBoxWithEnum(IsFieldCheckEnum.class);

			ComboBox myDataIdComboBox = internetApplyServiceHelper.getMyDataCustomComboBox(caseSetId);
			ComboBox myDataCheckTypeComboBox = commonServiceHelper.getComboBoxWithEnum(MyDataCheckTypeEnum.class);
			ComboBox myDataColumnComboBox = commonServiceHelper.getDefaultComboBox();

			viewForm.setMyDataCheckValue(myDataCheckValue);
			viewForm.setColumnTypeComboBox(columnTypeComboBox);
			viewForm.setFileTypeComboBox(fileTypeComboBox);
			viewForm.setIsMustKeyComboBox(isMustKeyComboBox);
			viewForm.setMyDataIdComboBox(myDataIdComboBox);
			viewForm.setMyDataColumnComboBox(myDataColumnComboBox);
			viewForm.setMyDataCheckTypeComboBox(myDataCheckTypeComboBox);
			/**GEO 20211019 add */
			viewForm.setIsCheckFrequencyComboBox(isCheckFrequencyComboBox);
			/** GEO 20211102 add 欄位勾選*/
			viewForm.setIsFieldCheckComboBox(isFieldCheckComboBox);

			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("internetApplyFormSetColumnHome error " + e.getMessage(), e);
		}
		return rs;
	}

	/**
	 * 網路申辦-表單設定-欄位維護-MYDATA_COLUMN下拉式選單
	 * 
	 * @param rq
	 * @return
	 */
	@Override
	public InternetApplyFormSetMyDataColumnComboBoxRs internetApplyFormSetMyDataColumnComboBox(
			InternetApplyFormSetMyDataColumnComboBoxRq rq) {
		InternetApplyFormSetMyDataColumnComboBoxViewForm viewForm =
				new InternetApplyFormSetMyDataColumnComboBoxViewForm();
		InternetApplyFormSetMyDataColumnComboBoxRs rs = new InternetApplyFormSetMyDataColumnComboBoxRs();

		try {
			String myDataId = rq.getMyDataId();
			ComboBox myDataColumnComboBox =
					internetApplyServiceHelper.getMyDataColumnComboBox(myDataId, StringUtils.EMPTY);
			viewForm.setMyDataColumnComboBox(myDataColumnComboBox);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("internetApplyFormSetMyDataColumnComboBox error " + e.getMessage(), e);
		}
		return rs;
	}

	/**
	 * 網路申辦-表單設定-欄位群組維護-進版儲存
	 * 
	 * 群組 欄位 整包一起依照下一個版號來儲存
	 * 
	 * @param rq
	 * @return
	 */
	@Override
	public synchronized InternetApplyFormSetGroupColumnSaveRs internetApplyFormSetGroupColumnSave(
			InternetApplyFormSetGroupColumnSaveRq rq) {
		InternetApplyFormSetGroupColumnSaveViewForm viewForm = new InternetApplyFormSetGroupColumnSaveViewForm();
		InternetApplyFormSetGroupColumnSaveRs rs = new InternetApplyFormSetGroupColumnSaveRs();

		try {
			String caseSetId = rq.getCaseSetId();


			/** GEO 20210817 changed for 1999 service **/
			//String caseSetName = geoKcgCityExtService.getCaseSetNameByCaseSetId(caseSetId);
			//LOGGER.info("InternetApplyServiceImpl internetApplyFormSetGroupColumnSave caseSetId/caseSetName: "+caseSetId+"/"+caseSetName);


			List<GroupColumnData> groupColumnDataList = rq.getGroupColumnSet();
			int version = KgoUtil.getNextVersionValue(caseSetId);

			groupColumnDataList.forEach(groupColumnData -> {
				/** GEO 20211203 add 重複檢核 */
				kgoCasesetGroupRepository.saveData(caseSetId, version, groupColumnData.getGroupName(), groupColumnData.getOrderNum(), groupColumnData.getCheckFrequencyPeriod());
				int groupSeq = findGroupSeq(caseSetId, groupColumnData.getGroupName());

				List<CasesetColumnData> list = groupColumnData.getColumnData();
				list.forEach(casesetColumnData -> {

					String columnId = casesetColumnData.getColumnId();

					/**儲存表單跟服務關聯*/
					KgoCasesetColumnPK kgoCasesetColumnPK = new KgoCasesetColumnPK();
					kgoCasesetColumnPK.setCaseSetId(caseSetId);
					kgoCasesetColumnPK.setColumnId(columnId);
					kgoCasesetColumnPK.setVersion(version);

					/**該服務表單 */
					KgoCasesetColumn kgoCasesetColumn = new KgoCasesetColumn();
					kgoCasesetColumn.setId(kgoCasesetColumnPK);
					kgoCasesetColumn.setColumnName(casesetColumnData.getColumnName());
					kgoCasesetColumn.setColumnType(casesetColumnData.getColumnType());


					/** GEO 20210817 changed for 1999 service **//** GEO 20211115 add for 民政局五種服務轉成B流程 **/
					//LOGGER.info("InternetApplyServiceImpl internetApplyFormSetGroupColumnSave getColumnType/AREA_ADVICE/ADDR_1999: "+
					//		casesetColumnData.getColumnType()+"/"+ColumnTypeEnum.AREA_ADVICE.getValue()+"/"+ColumnTypeEnum.ADDR_1999.getValue());
					if (casesetColumnData.getColumnType().equals(ColumnTypeEnum.ADDR_1999.getValue()) ||
							casesetColumnData.getColumnType().equals(ColumnTypeEnum.AREA_ADVICE.getValue()) ||
							casesetColumnData.getColumnType().equals(ColumnTypeEnum.KAOHSIUNG_RESIDENT.getValue()) ||
							casesetColumnData.getColumnType().equals(ColumnTypeEnum.KAOHSIUNG_DISTRICT_OFFICE.getValue())) {
						List<Geo1999ItemsMainModel> mainList = null;
						if (casesetColumnData.getColumnType().equals(ColumnTypeEnum.ADDR_1999.getValue())) {
							//處理 1999 地址縣市(PS.後來改直接抓api)
							//mainList = geoKcgCityExtService.sendGet1999AddrCityApi();
						} else if (casesetColumnData.getColumnType().equals(ColumnTypeEnum.AREA_ADVICE.getValue())) {
							//處理 1999 建議行政區
							mainList = geoKcgCityExtService.sendGet1999AreaAdviceApi();
						} else if (casesetColumnData.getColumnType().equals(ColumnTypeEnum.KAOHSIUNG_RESIDENT.getValue())) {
							//處理 地區設定(戶籍地)
							mainList = geoKcgCityExtService.sendGetAreaAdviceApi();
						} else if (casesetColumnData.getColumnType().equals(ColumnTypeEnum.KAOHSIUNG_DISTRICT_OFFICE.getValue())) {
							//處理 高雄區公所
							mainList = new ArrayList<>();
							GeoDistrictOfficeType[] districtOfficeList = GeoDistrictOfficeType.values();
							for (GeoDistrictOfficeType districtOffice : districtOfficeList) {
								Geo1999ItemsMainModel item = new Geo1999ItemsMainModel();
								item.setItemId(String.valueOf(districtOffice.getCode()));
								item.setItemName(districtOffice.getLabel());
								mainList.add(item);
							} //for (GeoDistrictOfficeType districtOffice
						}
						if (mainList!=null) {
							if (!casesetColumnData.getColumnType().equals(ColumnTypeEnum.KAOHSIUNG_DISTRICT_OFFICE.getValue()))
								kgoCasesetColumn.setColumnType(ColumnTypeEnum.DRP.getValue()); //將欄位型態變更為下拉選單
							StringBuilder sb = new StringBuilder();
							for (int i = 0; i < mainList.size(); i++) {
								if (i == 0) {
									sb.append(mainList.get(i).getItemId() + "-" + mainList.get(i).getItemName());
								} else {
									sb.append("," + mainList.get(i).getItemId() + "-" + mainList.get(i).getItemName());
								}
							}
							String value = sb.toString();
							kgoCasesetColumn.setColumnValue(value);
						} else {
							kgoCasesetColumn.setColumnValue(casesetColumnData.getColumnValue());
						} //if (mainList!=null)
					} else {
						kgoCasesetColumn.setColumnValue(casesetColumnData.getColumnValue());
					} //if (casesetColumnData.getColumnType().equals..


					kgoCasesetColumn.setGroupSeq(groupSeq);
					kgoCasesetColumn.setIsMustKey(
							IsMustKeyEnum.getIsMustKeyEnum(casesetColumnData.getIsMustKey()).getBooleanValue());
					/**GEO 20211109 add 重複檢核/欄位勾選 */
					Integer isCheckFrequency = casesetColumnData.getIsCheckFrequency() == null ? 0 : casesetColumnData.getIsCheckFrequency();
					kgoCasesetColumn.setIsCheckFrequency(isCheckFrequency);
					Integer isFieldCheck = casesetColumnData.getIsFieldCheck() == null ? 0 : casesetColumnData.getIsFieldCheck();
					kgoCasesetColumn.setIsFieldCheck(isFieldCheck);

					kgoCasesetColumn.setLength(casesetColumnData.getLength());
					kgoCasesetColumn.setMemo(casesetColumnData.getMemo());
					kgoCasesetColumn.setMyDataCheckType(casesetColumnData.getMyDataCheckType());
					kgoCasesetColumn.setMyDataCheckValue(casesetColumnData.getMyDataCheckValue());
					kgoCasesetColumn.setMyDataClientId(casesetColumnData.getMyDataClientId());
					kgoCasesetColumn.setMyDataColumn(casesetColumnData.getMyDataColumn());
					kgoCasesetColumn.setMyDataId(casesetColumnData.getMyDataId());
					kgoCasesetColumn.setMyDataType(casesetColumnData.getMyDataType());
					kgoCasesetColumn.setOrderNum(casesetColumnData.getOrderNum());
					if(FIL.getValue().equals(casesetColumnData.getColumnType())){
						kgoCasesetColumn.setFileType(casesetColumnData.getFileType());// When ColumnType is Fil, save FileType 20201208 By Jay
					}

					kgoCasesetColumnRepository.save(kgoCasesetColumn);
					LOGGER.info("InternetApplyServiceImpl internetApplyFormSetGroupColumnSave save success: "+caseSetId);

					List<List<CasesetComplexColumnData>> casesetComplexColumnDataList = casesetColumnData.getComplex();
					casesetComplexColumnDataList.forEach(tempList -> {
						tempList.forEach(casesetComplexColumnData -> {

							KgoCasesetColumnChildPK kgoCasesetColumnChildPK = new KgoCasesetColumnChildPK();
							kgoCasesetColumnChildPK.setCaseSetId(caseSetId);
							kgoCasesetColumnChildPK.setCColumnId(casesetComplexColumnData.getcColumnId());
							kgoCasesetColumnChildPK.setVersion(version);
							kgoCasesetColumnChildPK.setColumnId(columnId);

							KgoCasesetColumnChild kgoCasesetColumnChild = new KgoCasesetColumnChild();
							kgoCasesetColumnChild.setId(kgoCasesetColumnChildPK);
							kgoCasesetColumnChild.setBText(casesetComplexColumnData.getbText());
							kgoCasesetColumnChild.setColumnType(casesetComplexColumnData.getColumnType());
							kgoCasesetColumnChild.setColumnValue(casesetComplexColumnData.getColumnValue());
							kgoCasesetColumnChild.setFText(casesetComplexColumnData.getfText());
							kgoCasesetColumnChild.setIsMustKey(IsMustKeyEnum
									.getIsMustKeyEnum(casesetComplexColumnData.getIsMustKey()).getBooleanValue());
							/**GEO 20211109 add 重複檢核/欄位勾選 */
							Integer isCheckFrequencyChild = casesetComplexColumnData.getIsCheckFrequency() == null ? 0 :casesetComplexColumnData.getIsCheckFrequency();
							kgoCasesetColumnChild.setIsCheckFrequency(isCheckFrequencyChild);
							Integer isFieldCheckChild = casesetComplexColumnData.getIsFieldCheck() == null ? 0 : casesetComplexColumnData.getIsFieldCheck();
							kgoCasesetColumnChild.setIsFieldCheck(isFieldCheckChild);

							kgoCasesetColumnChild.setLength(casesetComplexColumnData.getLength());
							kgoCasesetColumnChild.setMemo(casesetComplexColumnData.getMemo());
							kgoCasesetColumnChild.setOrderNum(casesetComplexColumnData.getOrderNum());
							kgoCasesetColumnChild.setPColumnId(casesetComplexColumnData.getpColumnId());
							kgoCasesetColumnChild.setRow(casesetComplexColumnData.getRow());
							kgoCasesetColumnChild.setVGroup(casesetComplexColumnData.getvGroup());

							kgoCasesetColumnChildRepository.save(kgoCasesetColumnChild);
						}); //tempList.forEach
					}); //casesetComplexColumnDataList.forEach

				}); //list.forEach

			}); //groupColumnDataList.forEach
			updateOrganForm(caseSetId, version);
			viewForm.setVersion(version);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SAVE.getErrorMsgKey());
			throw new KgoApiException("internetApplyFormSetGroupColumnSave error " + e.getMessage(), e);
		}
		return rs;
	} //internetApplyFormSetGroupColumnSave

	/**
	 * GEO 20211109 add 機關審核表單
	 * 網路申辦-表單設定-欄位群組維護-機關審核表單 進版儲存
	 * 群組 欄位 整包一起依照下一個版號來儲存
	 *
	 * @param rq
	 * @return
	 */
	@Override
	public synchronized InternetApplyFormSetGroupColumnSaveRs internetApplyFormSetOrganGroupColumnSave(InternetApplyFormSetOrganGroupColumnSaveRq rq) {
		InternetApplyFormSetGroupColumnSaveViewForm viewForm = new InternetApplyFormSetGroupColumnSaveViewForm();
		InternetApplyFormSetGroupColumnSaveRs rs = new InternetApplyFormSetGroupColumnSaveRs();

		try {
			String caseSetId = rq.getCaseSetId();
			updateCaseForm(caseSetId);

			List<OrganGroupColumnData> groupColumnDataList = rq.getGroupColumnSet();
			int caseFormVersion = KgoUtil.getNextVersionValue(caseSetId) - 1;
			int organFormVersion = KgoUtil.getOrganFormGroupNextVersionValue(caseSetId, caseFormVersion);

			groupColumnDataList.forEach(groupColumnData -> {
				geoKgoCasesetOrganGroupReposCustom.saveData(caseSetId, organFormVersion, groupColumnData.getGroupName(), groupColumnData.getOrderNum(), caseFormVersion, groupColumnData.getIsShow());
				int groupSeq = findOrganGroupSeq(caseSetId, groupColumnData.getGroupName());
				List<CasesetOrganColumnData> list = groupColumnData.getColumnData();
				list.forEach(casesetColumnData -> {
					String columnId = casesetColumnData.getColumnId();

					/**儲存表單跟服務關聯*/
					GeoKgoCasesetColumnOrganPK kgoCasesetColumnPK = new GeoKgoCasesetColumnOrganPK();
					kgoCasesetColumnPK.setCaseSetId(caseSetId);
					kgoCasesetColumnPK.setColumnId(columnId);
					kgoCasesetColumnPK.setVersion(organFormVersion);
					kgoCasesetColumnPK.setCaseFormVersion(caseFormVersion);

					/**該服務表單 */
					GeoKgoCasesetColumnOrgan kgoCasesetColumn = new GeoKgoCasesetColumnOrgan();
					kgoCasesetColumn.setId(kgoCasesetColumnPK);
					kgoCasesetColumn.setColumnName(casesetColumnData.getColumnName());
					kgoCasesetColumn.setColumnType(casesetColumnData.getColumnType());
					/** GEO 20210817 changed for 1999 service **//** GEO 20211115 add for 民政局五種服務轉成B流程 **/
					//LOGGER.info("InternetApplyServiceImpl internetApplyFormSetGroupColumnSave getColumnType/AREA_ADVICE/ADDR_1999: "+
					//		casesetColumnData.getColumnType()+"/"+ColumnTypeEnum.AREA_ADVICE.getValue()+"/"+ColumnTypeEnum.ADDR_1999.getValue());
					if (casesetColumnData.getColumnType().equals(ColumnTypeEnum.ADDR_1999.getValue()) ||
							casesetColumnData.getColumnType().equals(ColumnTypeEnum.AREA_ADVICE.getValue()) ||
							casesetColumnData.getColumnType().equals(ColumnTypeEnum.KAOHSIUNG_RESIDENT.getValue()) ||
							casesetColumnData.getColumnType().equals(ColumnTypeEnum.KAOHSIUNG_DISTRICT_OFFICE.getValue())) {
						List<Geo1999ItemsMainModel> mainList = null;
						if (casesetColumnData.getColumnType().equals(ColumnTypeEnum.ADDR_1999.getValue())) {
							//處理 1999 地址縣市(PS.後來改直接抓api)
							//mainList = geoKcgCityExtService.sendGet1999AddrCityApi();
						} else if (casesetColumnData.getColumnType().equals(ColumnTypeEnum.AREA_ADVICE.getValue())) {
							//處理 1999 建議行政區
							mainList = geoKcgCityExtService.sendGet1999AreaAdviceApi();
						} else if (casesetColumnData.getColumnType().equals(ColumnTypeEnum.KAOHSIUNG_RESIDENT.getValue())) {
							//處理 地區設定(戶籍地)
							mainList = geoKcgCityExtService.sendGetAreaAdviceApi();
						} else if (casesetColumnData.getColumnType().equals(ColumnTypeEnum.KAOHSIUNG_DISTRICT_OFFICE.getValue())) {
							//處理 高雄區公所
							mainList = new ArrayList<>();
							GeoDistrictOfficeType[] districtOfficeList = GeoDistrictOfficeType.values();
							for (GeoDistrictOfficeType districtOffice : districtOfficeList) {
								Geo1999ItemsMainModel item = new Geo1999ItemsMainModel();
								item.setItemId(String.valueOf(districtOffice.getCode()));
								item.setItemName(districtOffice.getLabel());
								mainList.add(item);
							} //for (GeoDistrictOfficeType districtOffice
						}
						if (mainList!=null) {
							if (!casesetColumnData.getColumnType().equals(ColumnTypeEnum.KAOHSIUNG_DISTRICT_OFFICE.getValue()))
								kgoCasesetColumn.setColumnType(ColumnTypeEnum.DRP.getValue()); //將欄位型態變更為下拉選單
							StringBuilder sb = new StringBuilder();
							for (int i = 0; i < mainList.size(); i++) {
								if (i == 0) {
									sb.append(mainList.get(i).getItemId() + "-" + mainList.get(i).getItemName());
								} else {
									sb.append("," + mainList.get(i).getItemId() + "-" + mainList.get(i).getItemName());
								}
							}
							String value = sb.toString();
							kgoCasesetColumn.setColumnValue(value);
						} else {
							kgoCasesetColumn.setColumnValue(casesetColumnData.getColumnValue());
						} //if (mainList!=null)
					} else {
						kgoCasesetColumn.setColumnValue(casesetColumnData.getColumnValue());
					} //if (casesetColumnData.getColumnType().equals..
					kgoCasesetColumn.setGroupSeq(groupSeq);
					kgoCasesetColumn.setIsMustKey(IsMustKeyEnum.getIsMustKeyEnum(casesetColumnData.getIsMustKey()).getBooleanValue());
					Integer isCheckFrequency = casesetColumnData.getIsCheckFrequency() == null ? 0 : casesetColumnData.getIsCheckFrequency();
					kgoCasesetColumn.setIsCheckFrequency(isCheckFrequency);
					Integer isFieldCheck = casesetColumnData.getIsFieldCheck() == null ? 0 : casesetColumnData.getIsFieldCheck();
					kgoCasesetColumn.setIsFieldCheck(isFieldCheck);
					kgoCasesetColumn.setLength(casesetColumnData.getLength());
					kgoCasesetColumn.setMemo(casesetColumnData.getMemo());
					kgoCasesetColumn.setOrderNum(casesetColumnData.getOrderNum());
					// When ColumnType is Fil, save FileType 20201208 By Jay
					if(FIL.getValue().equals(casesetColumnData.getColumnType())) kgoCasesetColumn.setFileType(casesetColumnData.getFileType());
					geoKgoCasesetColumnOrganRepository.save(kgoCasesetColumn);
					LOGGER.info("InternetApplyServiceImpl internetApplyFormSetOrganGroupColumnSave save success: "+caseSetId);

					List<List<CasesetOrganComplexColumnData>> casesetComplexColumnDataList = casesetColumnData.getComplex();
					casesetComplexColumnDataList.forEach(tempList -> {
						tempList.forEach(casesetComplexColumnData -> {
							GeoKgoCasesetColumnChildOrganPK kgoCasesetColumnChildPK = new GeoKgoCasesetColumnChildOrganPK();
							kgoCasesetColumnChildPK.setCaseSetId(caseSetId);
							kgoCasesetColumnChildPK.setCColumnId(casesetComplexColumnData.getcColumnId());
							kgoCasesetColumnChildPK.setVersion(organFormVersion);
							kgoCasesetColumnChildPK.setColumnId(columnId);
							kgoCasesetColumnChildPK.setCaseFormVersion(caseFormVersion);

							GeoKgoCasesetColumnChildOrgan kgoCasesetColumnChild = new GeoKgoCasesetColumnChildOrgan();
							kgoCasesetColumnChild.setId(kgoCasesetColumnChildPK);
							kgoCasesetColumnChild.setBText(casesetComplexColumnData.getbText());
							kgoCasesetColumnChild.setColumnType(casesetComplexColumnData.getColumnType());
							kgoCasesetColumnChild.setColumnValue(casesetComplexColumnData.getColumnValue());
							kgoCasesetColumnChild.setFText(casesetComplexColumnData.getfText());
							kgoCasesetColumnChild.setIsMustKey(IsMustKeyEnum.getIsMustKeyEnum(casesetComplexColumnData.getIsMustKey()).getBooleanValue());
							kgoCasesetColumnChild.setIsCheckFrequency(casesetComplexColumnData.getIsCheckFrequency());
							kgoCasesetColumnChild.setIsFieldCheck(casesetComplexColumnData.getIsFieldCheck());
							kgoCasesetColumnChild.setLength(casesetComplexColumnData.getLength());
							kgoCasesetColumnChild.setMemo(casesetComplexColumnData.getMemo());
							kgoCasesetColumnChild.setOrderNum(casesetComplexColumnData.getOrderNum());
							kgoCasesetColumnChild.setPColumnId(casesetComplexColumnData.getpColumnId());
							kgoCasesetColumnChild.setRow(casesetComplexColumnData.getRow());
							kgoCasesetColumnChild.setVGroup(casesetComplexColumnData.getvGroup());
							geoKgoCasesetColumnChildOrganRepository.save(kgoCasesetColumnChild);
						}); //tempList.forEach
					}); //casesetComplexColumnDataList.forEach
				}); //list.forEach
			}); //groupColumnDataList.forEach
			viewForm.setVersion(organFormVersion);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SAVE.getErrorMsgKey());
			throw new KgoApiException("internetApplyFormSetGroupColumnSave error " + e.getMessage(), e);
		}
		return rs;
	} //internetApplyFormSetGroupColumnSave

	private synchronized void updateOrganForm(String caseSetId, Integer caseFormVersion){
		int oldVersion = KgoUtil.getOrganFormGroupNextVersionValue(caseSetId, caseFormVersion -1) - 1;
		List<GeoKgoCasesetGroupOrgan> groupColumnDataList = geoKgoCasesetGroupOrganRepository.findByIdCaseSetIdAndIdVersionAndIdCaseFormVersionOrderByOrderNum(caseSetId, oldVersion, caseFormVersion - 1);
		int organFormVersion = KgoUtil.getOrganFormGroupNextVersionValue(caseSetId, caseFormVersion);

		groupColumnDataList.forEach(groupColumnData -> {
			geoKgoCasesetOrganGroupReposCustom.saveData(caseSetId, organFormVersion, groupColumnData.getMemo(), groupColumnData.getOrderNum(), caseFormVersion, groupColumnData.getIsShow());
			int groupSeq = findOrganGroupSeq(caseSetId, groupColumnData.getMemo());

			List<GeoKgoCasesetColumnOrgan> list = geoKgoCasesetColumnOrganRepository.findByIdCaseSetIdAndGroupSeqAndIdCaseFormVersion(caseSetId, groupColumnData.getId().getGroupSeq(), groupColumnData.getId().getCaseFormVersion());
			list.forEach(casesetColumnData -> {
				String columnId = casesetColumnData.getId().getColumnId();

				/**儲存表單跟服務關聯*/
				GeoKgoCasesetColumnOrganPK kgoCasesetColumnPK = new GeoKgoCasesetColumnOrganPK();
				kgoCasesetColumnPK.setCaseSetId(caseSetId);
				kgoCasesetColumnPK.setColumnId(columnId);
				kgoCasesetColumnPK.setVersion(organFormVersion);
				kgoCasesetColumnPK.setCaseFormVersion(caseFormVersion);

				/**該服務表單 */
				GeoKgoCasesetColumnOrgan kgoCasesetColumn = new GeoKgoCasesetColumnOrgan();
				kgoCasesetColumn.setId(kgoCasesetColumnPK);
				kgoCasesetColumn.setColumnName(casesetColumnData.getColumnName());
				kgoCasesetColumn.setColumnType(casesetColumnData.getColumnType());

				/** GEO 20210817 changed for 1999 service **//** GEO 20211115 add for 民政局五種服務轉成B流程 **/
				//LOGGER.info("InternetApplyServiceImpl internetApplyFormSetGroupColumnSave getColumnType/AREA_ADVICE/ADDR_1999: "+
				//		casesetColumnData.getColumnType()+"/"+ColumnTypeEnum.AREA_ADVICE.getValue()+"/"+ColumnTypeEnum.ADDR_1999.getValue());
				if (casesetColumnData.getColumnType().equals(ColumnTypeEnum.ADDR_1999.getValue()) ||
						casesetColumnData.getColumnType().equals(ColumnTypeEnum.AREA_ADVICE.getValue()) ||
						casesetColumnData.getColumnType().equals(ColumnTypeEnum.KAOHSIUNG_RESIDENT.getValue()) ||
						casesetColumnData.getColumnType().equals(ColumnTypeEnum.KAOHSIUNG_DISTRICT_OFFICE.getValue())) {
					List<Geo1999ItemsMainModel> mainList = null;
					if (casesetColumnData.getColumnType().equals(ColumnTypeEnum.ADDR_1999.getValue())) {
						//處理 1999 地址縣市(PS.後來改直接抓api)
						//mainList = geoKcgCityExtService.sendGet1999AddrCityApi();
					} else if (casesetColumnData.getColumnType().equals(ColumnTypeEnum.AREA_ADVICE.getValue())) {
						//處理 1999 建議行政區
						mainList = geoKcgCityExtService.sendGet1999AreaAdviceApi();
					} else if (casesetColumnData.getColumnType().equals(ColumnTypeEnum.KAOHSIUNG_RESIDENT.getValue())) {
						//處理 地區設定(戶籍地)
						mainList = geoKcgCityExtService.sendGetAreaAdviceApi();
					} else if (casesetColumnData.getColumnType().equals(ColumnTypeEnum.KAOHSIUNG_DISTRICT_OFFICE.getValue())) {
						//處理 高雄區公所
						mainList = new ArrayList<>();
						GeoDistrictOfficeType[] districtOfficeList = GeoDistrictOfficeType.values();
						for (GeoDistrictOfficeType districtOffice : districtOfficeList) {
							Geo1999ItemsMainModel item = new Geo1999ItemsMainModel();
							item.setItemId(String.valueOf(districtOffice.getCode()));
							item.setItemName(districtOffice.getLabel());
							mainList.add(item);
						} //for (GeoDistrictOfficeType districtOffice
					}
					if (mainList!=null) {
						if (!casesetColumnData.getColumnType().equals(ColumnTypeEnum.KAOHSIUNG_DISTRICT_OFFICE.getValue()))
							kgoCasesetColumn.setColumnType(ColumnTypeEnum.DRP.getValue()); //將欄位型態變更為下拉選單
						StringBuilder sb = new StringBuilder();
						for (int i = 0; i < mainList.size(); i++) {
							if (i == 0) {
								sb.append(mainList.get(i).getItemId() + "-" + mainList.get(i).getItemName());
							} else {
								sb.append("," + mainList.get(i).getItemId() + "-" + mainList.get(i).getItemName());
							}
						}
						String value = sb.toString();
						kgoCasesetColumn.setColumnValue(value);
					} else {
						kgoCasesetColumn.setColumnValue(casesetColumnData.getColumnValue());
					} //if (mainList!=null)
				} else {
					kgoCasesetColumn.setColumnValue(casesetColumnData.getColumnValue());
				} //if (casesetColumnData.getColumnType().equals..
				kgoCasesetColumn.setGroupSeq(groupSeq);
				kgoCasesetColumn.setIsMustKey(IsMustKeyEnum.getIsMustKeyEnum(casesetColumnData.getIsMustKey()).getBooleanValue());
				Integer isCheckFrequency = casesetColumnData.getIsCheckFrequency() == null ? 0 : casesetColumnData.getIsCheckFrequency();
				kgoCasesetColumn.setIsCheckFrequency(isCheckFrequency);
				Integer isFieldCheck = casesetColumnData.getIsFieldCheck() == null ? 0 : casesetColumnData.getIsFieldCheck();
				kgoCasesetColumn.setIsFieldCheck(isFieldCheck);
				kgoCasesetColumn.setLength(casesetColumnData.getLength());
				kgoCasesetColumn.setMemo(casesetColumnData.getMemo());
				kgoCasesetColumn.setOrderNum(casesetColumnData.getOrderNum());
				// When ColumnType is Fil, save FileType 20201208 By Jay
				if(FIL.getValue().equals(casesetColumnData.getColumnType())) kgoCasesetColumn.setFileType(casesetColumnData.getFileType());
				geoKgoCasesetColumnOrganRepository.save(kgoCasesetColumn);
				LOGGER.info("InternetApplyServiceImpl internetApplyFormSetOrganGroupColumnSave save success: "+caseSetId);
			}); //list.forEach
		}); //groupColumnDataList.forEach

		List<GeoKgoCasesetColumnChildOrgan> tempList = geoKgoCasesetColumnChildOrganRepository.findByIdCaseSetIdAndIdVersionAndIdCaseFormVersion(caseSetId, oldVersion, caseFormVersion - 1);
		tempList.forEach(casesetComplexColumnData -> {
			GeoKgoCasesetColumnChildOrganPK kgoCasesetColumnChildPK = new GeoKgoCasesetColumnChildOrganPK();
			kgoCasesetColumnChildPK.setCaseSetId(caseSetId);
			kgoCasesetColumnChildPK.setCColumnId(casesetComplexColumnData.getId().getCColumnId());
			kgoCasesetColumnChildPK.setVersion(organFormVersion);
			kgoCasesetColumnChildPK.setColumnId(casesetComplexColumnData.getId().getColumnId());
			kgoCasesetColumnChildPK.setCaseFormVersion(caseFormVersion);

			GeoKgoCasesetColumnChildOrgan kgoCasesetColumnChild = new GeoKgoCasesetColumnChildOrgan();
			kgoCasesetColumnChild.setId(kgoCasesetColumnChildPK);
			kgoCasesetColumnChild.setBText(casesetComplexColumnData.getBText());
			kgoCasesetColumnChild.setColumnType(casesetComplexColumnData.getColumnType());
			kgoCasesetColumnChild.setColumnValue(casesetComplexColumnData.getColumnValue());
			kgoCasesetColumnChild.setFText(casesetComplexColumnData.getFText());
			kgoCasesetColumnChild.setIsMustKey(IsMustKeyEnum.getIsMustKeyEnum(casesetComplexColumnData.getIsMustKey()).getBooleanValue());
			kgoCasesetColumnChild.setIsCheckFrequency(casesetComplexColumnData.getIsCheckFrequency());
			kgoCasesetColumnChild.setIsFieldCheck(casesetComplexColumnData.getIsFieldCheck());
			kgoCasesetColumnChild.setLength(casesetComplexColumnData.getLength());
			kgoCasesetColumnChild.setMemo(casesetComplexColumnData.getMemo());
			kgoCasesetColumnChild.setOrderNum(casesetComplexColumnData.getOrderNum());
			kgoCasesetColumnChild.setPColumnId(casesetComplexColumnData.getPColumnId());
			kgoCasesetColumnChild.setRow(casesetComplexColumnData.getRow());
			kgoCasesetColumnChild.setVGroup(casesetComplexColumnData.getVGroup());
			geoKgoCasesetColumnChildOrganRepository.save(kgoCasesetColumnChild);
		}); //tempList.forEach
	}

	private synchronized void updateCaseForm(String caseSetId) {
		int version = KgoUtil.getNextVersionValue(caseSetId);
		List<KgoCasesetGroup> groupList = kgoCasesetGroupRepository.findByIdCaseSetIdAndIdVersionOrderByOrderNumAsc(caseSetId, version - 1);
		groupList.forEach(groupColumnData -> {
			/** GEO 20211203 add 重複檢核 */
			kgoCasesetGroupRepository.saveData(caseSetId, version, groupColumnData.getMemo(), groupColumnData.getOrderNum(), groupColumnData.getCheckFrequencyPeriod());
			int groupSeq = findGroupSeq(caseSetId, groupColumnData.getMemo());

			List<KgoCasesetColumn> columnList = kgoCasesetColumnRepository.findByIdCaseSetIdAndGroupSeqAndIdVersion(caseSetId, groupColumnData.getId().getGroupSeq(), version -1);
			columnList.forEach(casesetColumnData -> {

				String columnId = casesetColumnData.getId().getColumnId();

				/**儲存表單跟服務關聯*/
				KgoCasesetColumnPK kgoCasesetColumnPK = new KgoCasesetColumnPK();
				kgoCasesetColumnPK.setCaseSetId(caseSetId);
				kgoCasesetColumnPK.setColumnId(columnId);
				kgoCasesetColumnPK.setVersion(version);

				/**該服務表單 */
				KgoCasesetColumn kgoCasesetColumn = new KgoCasesetColumn();
				kgoCasesetColumn.setId(kgoCasesetColumnPK);
				kgoCasesetColumn.setColumnName(casesetColumnData.getColumnName());
				kgoCasesetColumn.setColumnType(casesetColumnData.getColumnType());


				/** GEO 20210817 changed for 1999 service **//** GEO 20211115 add for 民政局五種服務轉成B流程 **/
				//LOGGER.info("InternetApplyServiceImpl internetApplyFormSetGroupColumnSave getColumnType/AREA_ADVICE/ADDR_1999: "+
				//		casesetColumnData.getColumnType()+"/"+ColumnTypeEnum.AREA_ADVICE.getValue()+"/"+ColumnTypeEnum.ADDR_1999.getValue());
				if (casesetColumnData.getColumnType().equals(ColumnTypeEnum.ADDR_1999.getValue()) ||
						casesetColumnData.getColumnType().equals(ColumnTypeEnum.AREA_ADVICE.getValue()) ||
						casesetColumnData.getColumnType().equals(ColumnTypeEnum.KAOHSIUNG_RESIDENT.getValue()) ||
						casesetColumnData.getColumnType().equals(ColumnTypeEnum.KAOHSIUNG_DISTRICT_OFFICE.getValue())) {
					List<Geo1999ItemsMainModel> mainList = null;
					if (casesetColumnData.getColumnType().equals(ColumnTypeEnum.ADDR_1999.getValue())) {
						//處理 1999 地址縣市(PS.後來改直接抓api)
						//mainList = geoKcgCityExtService.sendGet1999AddrCityApi();
					} else if (casesetColumnData.getColumnType().equals(ColumnTypeEnum.AREA_ADVICE.getValue())) {
						//處理 1999 建議行政區
						mainList = geoKcgCityExtService.sendGet1999AreaAdviceApi();
					} else if (casesetColumnData.getColumnType().equals(ColumnTypeEnum.KAOHSIUNG_RESIDENT.getValue())) {
						//處理 地區設定(戶籍地)
						mainList = geoKcgCityExtService.sendGetAreaAdviceApi();
					} else if (casesetColumnData.getColumnType().equals(ColumnTypeEnum.KAOHSIUNG_DISTRICT_OFFICE.getValue())) {
						//處理 高雄區公所
						mainList = new ArrayList<>();
						GeoDistrictOfficeType[] districtOfficeList = GeoDistrictOfficeType.values();
						for (GeoDistrictOfficeType districtOffice : districtOfficeList) {
							Geo1999ItemsMainModel item = new Geo1999ItemsMainModel();
							item.setItemId(String.valueOf(districtOffice.getCode()));
							item.setItemName(districtOffice.getLabel());
							mainList.add(item);
						} //for (GeoDistrictOfficeType districtOffice
					}
					if (mainList!=null) {
						if (!casesetColumnData.getColumnType().equals(ColumnTypeEnum.KAOHSIUNG_DISTRICT_OFFICE.getValue()))
							kgoCasesetColumn.setColumnType(ColumnTypeEnum.DRP.getValue()); //將欄位型態變更為下拉選單
						StringBuilder sb = new StringBuilder();
						for (int i = 0; i < mainList.size(); i++) {
							if (i == 0) {
								sb.append(mainList.get(i).getItemId() + "-" + mainList.get(i).getItemName());
							} else {
								sb.append("," + mainList.get(i).getItemId() + "-" + mainList.get(i).getItemName());
							}
						}
						String value = sb.toString();
						kgoCasesetColumn.setColumnValue(value);
					} else {
						kgoCasesetColumn.setColumnValue(casesetColumnData.getColumnValue());
					} //if (mainList!=null)
				} else {
					kgoCasesetColumn.setColumnValue(casesetColumnData.getColumnValue());
				} //if (casesetColumnData.getColumnType().equals..


				kgoCasesetColumn.setGroupSeq(groupSeq);
				kgoCasesetColumn.setIsMustKey(IsMustKeyEnum.getIsMustKeyEnum(casesetColumnData.getIsMustKey()).getBooleanValue());
				/**GEO 20211109 add 重複檢核/欄位勾選 */
				Integer isCheckFrequency = casesetColumnData.getIsCheckFrequency() == null ? 0 : casesetColumnData.getIsCheckFrequency();
				kgoCasesetColumn.setIsCheckFrequency(isCheckFrequency);
				Integer isFieldCheck = casesetColumnData.getIsFieldCheck() == null ? 0 : casesetColumnData.getIsFieldCheck();
				kgoCasesetColumn.setIsFieldCheck(isFieldCheck);

				kgoCasesetColumn.setLength(casesetColumnData.getLength());
				kgoCasesetColumn.setMemo(casesetColumnData.getMemo());
				kgoCasesetColumn.setMyDataCheckType(casesetColumnData.getMyDataCheckType());
				kgoCasesetColumn.setMyDataCheckValue(casesetColumnData.getMyDataCheckValue());
				kgoCasesetColumn.setMyDataClientId(casesetColumnData.getMyDataClientId());
				kgoCasesetColumn.setMyDataColumn(casesetColumnData.getMyDataColumn());
				kgoCasesetColumn.setMyDataId(casesetColumnData.getMyDataId());
				kgoCasesetColumn.setMyDataType(casesetColumnData.getMyDataType());
				kgoCasesetColumn.setOrderNum(casesetColumnData.getOrderNum());
				if(FIL.getValue().equals(casesetColumnData.getColumnType())){
					kgoCasesetColumn.setFileType(casesetColumnData.getFileType());// When ColumnType is Fil, save FileType 20201208 By Jay
				}
				kgoCasesetColumnRepository.save(kgoCasesetColumn);
				LOGGER.info("InternetApplyServiceImpl internetApplyFormSetGroupColumnSave save success: "+caseSetId);
			}); //list.forEach
		}); //groupColumnDataList.forEach

		List<KgoCasesetColumnChild> child = kgoCasesetColumnChildRepository.findByIdCaseSetIdAndIdVersion(caseSetId, version -1);
		child.forEach(casesetComplexColumnData -> {

				KgoCasesetColumnChildPK kgoCasesetColumnChildPK = new KgoCasesetColumnChildPK();
				kgoCasesetColumnChildPK.setCaseSetId(caseSetId);
				kgoCasesetColumnChildPK.setCColumnId(casesetComplexColumnData.getId().getCColumnId());
				kgoCasesetColumnChildPK.setVersion(version);
				kgoCasesetColumnChildPK.setColumnId(casesetComplexColumnData.getId().getColumnId());

				KgoCasesetColumnChild kgoCasesetColumnChild = new KgoCasesetColumnChild();
				kgoCasesetColumnChild.setId(kgoCasesetColumnChildPK);
				kgoCasesetColumnChild.setBText(casesetComplexColumnData.getBText());
				kgoCasesetColumnChild.setColumnType(casesetComplexColumnData.getColumnType());
				kgoCasesetColumnChild.setColumnValue(casesetComplexColumnData.getColumnValue());
				kgoCasesetColumnChild.setFText(casesetComplexColumnData.getFText());
				kgoCasesetColumnChild.setIsMustKey(IsMustKeyEnum
						.getIsMustKeyEnum(casesetComplexColumnData.getIsMustKey()).getBooleanValue());
				/**GEO 20211019 add */
				kgoCasesetColumnChild.setIsCheckFrequency(casesetComplexColumnData.getIsCheckFrequency());
				/** GEO 20211102 add 欄位勾選*/
				kgoCasesetColumnChild.setIsFieldCheck(casesetComplexColumnData.getIsFieldCheck());

				kgoCasesetColumnChild.setLength(casesetComplexColumnData.getLength());
				kgoCasesetColumnChild.setMemo(casesetComplexColumnData.getMemo());
				kgoCasesetColumnChild.setOrderNum(casesetComplexColumnData.getOrderNum());
				kgoCasesetColumnChild.setPColumnId(casesetComplexColumnData.getPColumnId());
				kgoCasesetColumnChild.setRow(casesetComplexColumnData.getRow());
				kgoCasesetColumnChild.setVGroup(casesetComplexColumnData.getVGroup());

				kgoCasesetColumnChildRepository.save(kgoCasesetColumnChild);
			}); //tempList.forEach
	}

	/**
	 * 網路申辦-表單設定-MYDAYA維護-初始畫面
	 * 
	 * 預設帶對外，第一筆服務
	 * 
	 * @param rq
	 * @return
	 */
	@Override
	public InternetApplyFormSetMydataHomeRs internetApplyFormSetMydataHome(InternetApplyFormSetMydataHomeRq rq) {
		InternetApplyFormSetMydataHomeViewForm viewForm = new InternetApplyFormSetMydataHomeViewForm();
		InternetApplyFormSetMydataHomeRs rs = new InternetApplyFormSetMydataHomeRs();
		try {
			String caseSetId = rq.getCaseSetId();
			ComboBox sTypeComboBox = commonServiceHelper.getComboBoxWithEnum(MyDataSTypeEnum.class);
			ComboBox serviceComboBox = internetApplyServiceHelper.getMydataServiceComboBox();
			ComboBox myDataSetComboBox = null;

			/** default setting **/
			sTypeComboBox.setSelectedVal(MyDataSTypeEnum.OUTSIDE.getValue());
			String defaultServiceValue = CollectionUtils.isEmpty(serviceComboBox.getOptions()) ? ""
					: serviceComboBox.getOptions().get(0).getValue();
			serviceComboBox.setSelectedVal(defaultServiceValue);
			myDataSetComboBox = internetApplyServiceHelper.getMydataResourceComboBoxByOutside(defaultServiceValue);

			List<InternetApplyFormSetMydataHomeDataGrid> grid =
					kgoCasesetMydataRepository.countMydataIdAndNameByCaseSetId(caseSetId).stream().map(l -> {
						InternetApplyFormSetMydataHomeDataGrid dg = new InternetApplyFormSetMydataHomeDataGrid();
						if (ObjectUtils.isNotEmpty(l)) {
							String sType = l.getsType();

							dg.setMyDataId(l.getMyDataId());
							dg.setMyDataName(l.getMyDataName());
							dg.setCanDelete(l.getCount() > 0 ? false : true);
							dg.setsType(sType);
							dg.setsTypeName(StringUtils.isBlank(sType) ? StringUtils.EMPTY
									: MyDataSTypeEnum.getMyDataSTypeEnum(sType).getLabel());

						}
						return dg;
					}).collect(Collectors.toList());

			viewForm.setsTypeComboBox(sTypeComboBox);
			viewForm.setServiceComboBox(serviceComboBox);
			viewForm.setMyDataSetComboBox(myDataSetComboBox);
			viewForm.setGrid(grid);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("internetApplyFormSetMydataHome error " + e.getMessage(), e);
		}
		return rs;
	}

	/**
	 * 網路申辦-表單設定-MYDAYA維護-取得Mydata資料集下拉式選單資料
	 * 
	 * @param rq
	 * @return
	 */
	@Override
	public InternetApplyFormSetMydataComboBoxRs internetApplyFormSetMydataComboBox(
			InternetApplyFormSetMydataComboBoxRq rq) {
		InternetApplyFormSetMydataComboBoxViewForm viewForm = new InternetApplyFormSetMydataComboBoxViewForm();
		InternetApplyFormSetMydataComboBoxRs rs = new InternetApplyFormSetMydataComboBoxRs();
		try {
			ComboBox mydataComboBox = null;
			String clientId = rq.getClientId();
			if (StringUtils.isEmpty(clientId)) {
				mydataComboBox = internetApplyServiceHelper.getMydataResourceComboBoxByInside();
			} else {
				mydataComboBox = internetApplyServiceHelper.getMydataResourceComboBoxByOutside(clientId);
			}

			viewForm.setMydataComboBox(mydataComboBox);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("internetApplyFormSetMydataComboBox error " + e.getMessage(), e);
		}
		return rs;
	}

	/**
	 * 網路申辦-表單設定-MYDAYA維護-資料集刪除
	 * 
	 * @param rq
	 * @return
	 */
	@Override
	public InternetApplyFormSetMydataDeleteRs internetApplyFormSetMydataDelete(InternetApplyFormSetMydataDeleteRq rq) {
		InternetApplyFormSetMydataDeleteViewForm viewForm = new InternetApplyFormSetMydataDeleteViewForm();
		InternetApplyFormSetMydataDeleteRs rs = new InternetApplyFormSetMydataDeleteRs();
		try {
			String caseSetId = rq.getCaseSetId();
			String myDataId = rq.getMydataId();

			KgoCasesetMydataPK id = new KgoCasesetMydataPK();
			id.setCaseSetId(caseSetId);
			id.setMyDataId(myDataId);
			KgoCasesetMydata kgoCasesetMydata = new KgoCasesetMydata();
			kgoCasesetMydata.setId(id);
			kgoCasesetMydataRepository.delete(kgoCasesetMydata);
			viewForm.setMsg(SuccessMsg.DELETE.getMsg());
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_DELETE.getErrorMsgKey());
			throw new KgoApiException("internetApplyFormSetMydataDelete error " + e.getMessage(), e);
		}
		return rs;
	}

	/**
	 * 網路申辦-表單設定-MYDAYA維護-資料集新增
	 * 
	 * @param rq
	 * @return
	 */
	@Override
	public InternetApplyFormSetMydataSaveRs internetApplyFormSetMydataSave(InternetApplyFormSetMydataSaveRq rq) {
		InternetApplyFormSetMydataSaveViewForm viewForm = new InternetApplyFormSetMydataSaveViewForm();
		InternetApplyFormSetMydataSaveRs rs = new InternetApplyFormSetMydataSaveRs();
		try {
			String caseSetId = rq.getCaseSetId();
			String myDataId = rq.getMydataId();
			String clientId = rq.getClientId();

			KgoCasesetMydataPK id = new KgoCasesetMydataPK();
			id.setCaseSetId(caseSetId);
			id.setMyDataId(myDataId);
			KgoCasesetMydata kgoCasesetMydata = new KgoCasesetMydata();
			kgoCasesetMydata.setId(id);
			kgoCasesetMydata.setMyDataClientId(clientId);
			kgoCasesetMydataRepository.save(kgoCasesetMydata);

			viewForm.setMsg(SuccessMsg.SAVE.getMsg());
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SAVE.getErrorMsgKey());
			throw new KgoApiException("internetApplyFormSetMydataSave error " + e.getMessage(), e);
		}
		return rs;
	}

	/**
	 * 網路申辦-案件儲存
	 * 
	 * @param rq
	 * @return
	 */
	@Override
	@Transactional
	public InternetApplySaveRs internetApplySave(InternetApplySaveRq rq) {
		InternetApplySaveViewForm viewForm = new InternetApplySaveViewForm();
		InternetApplySaveRs rs = new InternetApplySaveRs();
		try {
			String updateUser = KgoUtil.getLoginUserId();
			String caseSetId = rq.getCaseSetId();
			String acceptSet = rq.getAcceptSet();
			String dateEnd = rq.getDataEnd();
			String dateStart = rq.getDataStart();
			boolean isServiceHtml = rq.getIsServiceHtml();
			String serviceHtml = rq.getServiceHtml();
			int limitedPeriod = rq.getLimitedPeriod();
			String mail = rq.getMail();
			String checkTypeStr = rq.getCheckType();

			KgoCaseset kgoCaseset = kgoCasesetRepository.getOne(caseSetId);
			kgoCaseset.setAcceptSet(acceptSet);
			kgoCaseset.setDateEnd(dateEnd);
			kgoCaseset.setDateStart(dateStart);
			kgoCaseset.setIsServiceHtml(isServiceHtml);
			kgoCaseset.setServiceHtml(serviceHtml);
			kgoCaseset.setLimitedPeriod(limitedPeriod);
			kgoCaseset.setMail(mail);
			kgoCaseset.setUpdateTime(DateUtil.getCurrentTimestamp());
			kgoCaseset.setUpdateUser(updateUser);
			kgoCasesetRepository.save(kgoCaseset);
			

			kgoCasesetCheckRepository.deleteByIdCaseSetId(caseSetId);
			if (StringUtils.isNotBlank(checkTypeStr)) {

				List<KgoCasesetCheck> entityList = Arrays.asList(checkTypeStr.split(",")).stream().map(s -> {
					KgoCasesetCheckPK id = new KgoCasesetCheckPK();
					id.setCaseSetId(caseSetId);
					id.setCheckType(s);
					KgoCasesetCheck entity = new KgoCasesetCheck();
					entity.setId(id);
					return entity;
				}).collect(Collectors.toList());

				kgoCasesetCheckRepository.saveAllBatch(entityList);
			}

			viewForm.setMsg(SuccessMsg.SAVE.getMsg());
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SAVE.getErrorMsgKey());
			throw new KgoApiException("internetApplySave error " + e.getMessage(), e);
		}
		return rs;
	}

	/**
	 * 案件新增時，若申辦流程為服務申辦流程（即為有A、B、C流程的選項），表單設定自動長出兩列申請人證號、申請人Email為預設值，且唯讀
	 * 
	 * @param caseSetId
	 */
	private void addBasicGroupColumnData(String caseSetId) {
		LOGGER.info("InternetApplyServiceImpl addBasicGroupColumnData caseSetId: "+caseSetId);
		KgoCaseset kgoCaseset = kgoCasesetRepository.getOne(caseSetId);
//		String caseType = kgoCaseset.getCaseType();
		String caseFlowType = kgoCaseset.getCaseFlowType();

//		/** GEO 20211109 add for 機關審核表單 */
//		if (kgoCaseset.getIsOpenOrganForm() != null && kgoCaseset.getIsOpenOrganForm() == GeoBooleanType.ENABLED.getCode()) {
//			Integer organGroupSeq = findOrganGroupSeq(caseSetId, KgoUtil.MYDATA_BASIC_GROUP_NAME);
//			if (ObjectUtils.isEmpty(organGroupSeq) && !"A".equals(caseFlowType)) {
//				updateCaseForm(caseSetId);
//				Integer caseCaseFormVersion = KgoUtil.getNextVersionValue(caseSetId) - 1;
//				geoKgoCasesetOrganGroupReposCustom.saveData(caseSetId, KgoUtil.DEFAULT_VERSION_NUMBER, KgoUtil.MYDATA_BASIC_GROUP_NAME, KgoUtil.DEFAULT_ORDER_NUMBER, caseCaseFormVersion, GeoBooleanType.DISABLED.getCode());
//				int organGroupAfterSaveSeq = findOrganGroupSeq(caseSetId, KgoUtil.MYDATA_BASIC_GROUP_NAME);
//				saveOrganForm(caseSetId, organGroupAfterSaveSeq,caseCaseFormVersion);
//			} //if (ObjectUtils.isEmpty(organGroupSeq)
//		} //if (kgoCaseset.getIsOpenOrganForm()...

		Integer groupSeq = findGroupSeq(caseSetId, KgoUtil.MYDATA_BASIC_GROUP_NAME);
		if (ObjectUtils.isEmpty(groupSeq)
//				&& CaseTypeEnum.SA.getValue().equalsIgnoreCase(caseType)
				&& !"A".equals(caseFlowType)) {
			// 1.『基本設定』群組新增
			kgoCasesetGroupRepository.saveData(caseSetId, KgoUtil.DEFAULT_VERSION_NUMBER,
					KgoUtil.MYDATA_BASIC_GROUP_NAME, KgoUtil.DEFAULT_ORDER_NUMBER, null);
			groupSeq = findGroupSeq(caseSetId, KgoUtil.MYDATA_BASIC_GROUP_NAME);
			List<KgoCasesetTemplate> kgoCasesetTemplates = kgoCasesetTemplateRepository.findByIsDefault(TemplateIsDefaultEnum.ONE.getValue());
//			int order = KgoUtil.DEFAULT_ORDER_NUMBER;
			if (!CollectionUtils.isEmpty(kgoCasesetTemplates)) {
				KgoCasesetTemplate kgoCasesetTemplate = kgoCasesetTemplates.get(0);
				List<KgoCasesetColumnTemplate> kgoCasesetColumnTemplates = kgoCasesetColumnTemplateRepository.findByTemplateSeqAndSuspend(kgoCasesetTemplate.getSeq(), TemplateSuspendEnum.ZERO.getValue());
				List<Integer> seqs = kgoCasesetColumnTemplates.stream().map(KgoCasesetColumnTemplate::getSeq).collect(Collectors.toList());
				List<KgoCasesetColumnChildTemplate> kgoCasesetColumnChildTemplates = kgoCasesetColumnChildTemplateRepository.findByColumnSeqInAndSuspend(seqs, TemplateSuspendEnum.ZERO.getValue());
				Map<Integer, List<KgoCasesetColumnChildTemplate>> collect = kgoCasesetColumnChildTemplates.stream().collect(Collectors.groupingBy(KgoCasesetColumnChildTemplate::getColumnSeq));
				for (KgoCasesetColumnTemplate kgoCasesetColumnTemplate : kgoCasesetColumnTemplates) {
					KgoCasesetColumnPK kgoCasesetColumnPK = new KgoCasesetColumnPK();
					kgoCasesetColumnPK.setCaseSetId(caseSetId);
					kgoCasesetColumnPK.setColumnId(kgoCasesetColumnTemplate.getColumnId());
					kgoCasesetColumnPK.setVersion(KgoUtil.DEFAULT_VERSION_NUMBER);
					KgoCasesetColumn kgoCasesetColumn = new KgoCasesetColumn();
					kgoCasesetColumn.setId(kgoCasesetColumnPK);
					kgoCasesetColumn.setColumnName(kgoCasesetColumnTemplate.getColumnName());
					kgoCasesetColumn.setColumnType(kgoCasesetColumnTemplate.getColumnType());
					IsMustKeyEnum isMustKeyEnum = IsMustKeyEnum.getIsMustKeyEnum(kgoCasesetColumnTemplate.getIsMustKey());
					if (null != isMustKeyEnum) {
						kgoCasesetColumn.setIsMustKey(isMustKeyEnum.getBooleanValue());
					}
					/**GEO 20211019 add */
					IsCHeckFrequencyEnum isCHeckFrequencyEnum = IsCHeckFrequencyEnum.getIsCheckFrequencyEnum(kgoCasesetColumnTemplate.getIsCheckFrequency());
					if (null != isCHeckFrequencyEnum) {
						kgoCasesetColumn.setIsCheckFrequency(Integer.parseInt(isCHeckFrequencyEnum.getValue()));
					}
					/** GEO 20211102 add 欄位勾選*/
					IsFieldCheckEnum isFieldCheckEnum = IsFieldCheckEnum.getIsFieldCheckEnum(kgoCasesetColumnTemplate.getIsFieldCheck());
					if (null != isFieldCheckEnum) {
						kgoCasesetColumn.setIsFieldCheck(Integer.parseInt(isFieldCheckEnum.getValue()));
					}

					kgoCasesetColumn.setGroupSeq(groupSeq);
					kgoCasesetColumn.setLength(kgoCasesetColumnTemplate.getLength());
					kgoCasesetColumn.setOrderNum(kgoCasesetColumnTemplate.getOrderNum());
					kgoCasesetColumn.setColumnValue(kgoCasesetColumnTemplate.getColumnValue());
					kgoCasesetColumn.setMemo(kgoCasesetColumnTemplate.getMemo());
					kgoCasesetColumn.setMyDataCheckType(kgoCasesetColumnTemplate.getMyDataCheckType());
					kgoCasesetColumn.setMyDataCheckValue(kgoCasesetColumnTemplate.getMyDataCheckValue());
					kgoCasesetColumn.setMyDataClientId(kgoCasesetColumnTemplate.getMyDataClientId());
					kgoCasesetColumn.setMyDataColumn(kgoCasesetColumnTemplate.getMyDataColumn());
					kgoCasesetColumn.setMyDataId(kgoCasesetColumnTemplate.getMyDataId());
					kgoCasesetColumn.setMyDataType(kgoCasesetColumnTemplate.getMyDataType());
					kgoCasesetColumn.setOrderNum(kgoCasesetColumnTemplate.getOrderNum());
					kgoCasesetColumn.setFileType(kgoCasesetColumnTemplate.getFileType());
					List<KgoCasesetColumnChildTemplate> kgoCasesetColumnChildTemplatesList = collect.get(kgoCasesetColumnTemplate.getSeq());
					if (!CollectionUtils.isEmpty(kgoCasesetColumnChildTemplatesList)) {
						for (KgoCasesetColumnChildTemplate kgoCasesetColumnChildTemplate : kgoCasesetColumnChildTemplatesList) {
							KgoCasesetColumnChildPK kgoCasesetColumnChildPK = new KgoCasesetColumnChildPK();
							kgoCasesetColumnChildPK.setCaseSetId(caseSetId);
							kgoCasesetColumnChildPK.setCColumnId(kgoCasesetColumnChildTemplate.getCColumnId());
							kgoCasesetColumnChildPK.setVersion(KgoUtil.DEFAULT_VERSION_NUMBER);
							kgoCasesetColumnChildPK.setColumnId(kgoCasesetColumnChildTemplate.getColumnId());
							KgoCasesetColumnChild kgoCasesetColumnChild = new KgoCasesetColumnChild();
							kgoCasesetColumnChild.setId(kgoCasesetColumnChildPK);
							kgoCasesetColumnChild.setBText(kgoCasesetColumnChildTemplate.getBText());
							kgoCasesetColumnChild.setColumnType(kgoCasesetColumnChildTemplate.getColumnType());
							kgoCasesetColumnChild.setColumnValue(kgoCasesetColumnChildTemplate.getColumnValue());
							kgoCasesetColumnChild.setFText(kgoCasesetColumnChildTemplate.getFText());
							IsMustKeyEnum isMustKeyEnumInner = IsMustKeyEnum.getIsMustKeyEnum(kgoCasesetColumnChildTemplate.getIsMustKey());
							if (null != isMustKeyEnumInner) {
								kgoCasesetColumnChild.setIsMustKey(isMustKeyEnumInner.getBooleanValue());
							}
							/**GEO 20211019 add */
							IsCHeckFrequencyEnum isCHeckFrequencyEnumInner = IsCHeckFrequencyEnum.getIsCheckFrequencyEnum(kgoCasesetColumnChildTemplate.getIsCheckFrequency());
							if (null != isCHeckFrequencyEnumInner) {
								kgoCasesetColumnChild.setIsCheckFrequency(Integer.parseInt(isCHeckFrequencyEnumInner.getValue()));
							}
							/** GEO 20211102 add 欄位勾選*/
							IsFieldCheckEnum isFieldCheckEnumInner = IsFieldCheckEnum.getIsFieldCheckEnum(kgoCasesetColumnChildTemplate.getIsFieldCheck());
							if (null != isFieldCheckEnumInner) {
								kgoCasesetColumnChild.setIsFieldCheck(Integer.parseInt(isFieldCheckEnumInner.getValue()));
							}

							kgoCasesetColumnChild.setLength(kgoCasesetColumnChildTemplate.getLength());
							kgoCasesetColumnChild.setMemo(kgoCasesetColumnChildTemplate.getMemo());
							kgoCasesetColumnChild.setOrderNum(kgoCasesetColumnChildTemplate.getOrderNum());
							kgoCasesetColumnChild.setPColumnId(kgoCasesetColumnChildTemplate.getPColumnId());
							kgoCasesetColumnChild.setRow(kgoCasesetColumnChildTemplate.getRow());
							kgoCasesetColumnChild.setVGroup(kgoCasesetColumnChildTemplate.getVGroup());

							kgoCasesetColumnChildRepository.save(kgoCasesetColumnChild);
							LOGGER.info("InternetApplyServiceImpl addBasicGroupColumnData save success caseSetId: "+caseSetId);
						} //for (KgoCasesetColumnChildTemplate kgoCasesetColumnChildTemplate : kgoCasesetColumnChildTemplatesList)
					} //if (!CollectionUtils.isEmpty(kgoCasesetColumnChildTemplatesList))
					kgoCasesetColumnRepository.save(kgoCasesetColumn);
				} //for (KgoCasesetColumnTemplate kgoCasesetColumnTemplate : kgoCasesetColumnTemplates)
			} //if (!CollectionUtils.isEmpty(kgoCasesetTemplates))

//			groupSeq = findGroupSeq(caseSetId, KgoUtil.MYDATA_BASIC_GROUP_NAME);

//			int order = KgoUtil.DEFAULT_ORDER_NUMBER;
//
//			// 2. 『申請人證號』欄位新增
//			KgoCasesetColumnPK textId = new KgoCasesetColumnPK();
//			textId.setCaseSetId(caseSetId);
//			textId.setColumnId(BaseColumnEnum.ID.getColumnId());
//			textId.setVersion(KgoUtil.DEFAULT_VERSION_NUMBER);
//			KgoCasesetColumn textIdColumn = new KgoCasesetColumn();
//			textIdColumn.setId(textId);
//			textIdColumn.setColumnName(BaseColumnEnum.ID.getColumnName());
//			textIdColumn.setColumnType(ColumnTypeEnum.TEXT_ID.getValue());
//			textIdColumn.setIsMustKey(true);
//			textIdColumn.setGroupSeq(groupSeq);
//			textIdColumn.setOrderNum(order);
//
//			kgoCasesetColumnRepository.save(textIdColumn);
//
//			// 3. 『申請人Email』欄位新增
//			KgoCasesetColumnPK textEmail = new KgoCasesetColumnPK();
//			textEmail.setCaseSetId(caseSetId);
//			textEmail.setColumnId(BaseColumnEnum.EMAIL.getColumnId());
//			textEmail.setVersion(KgoUtil.DEFAULT_VERSION_NUMBER);
//			KgoCasesetColumn textEmailColumn = new KgoCasesetColumn();
//			textEmailColumn.setId(textEmail);
//			textEmailColumn.setColumnName(BaseColumnEnum.EMAIL.getColumnName());
//			textEmailColumn.setLength(50);
//			textEmailColumn.setColumnType(ColumnTypeEnum.TEXT_EMAIL.getValue());
//			textEmailColumn.setIsMustKey(true);
//			textEmailColumn.setGroupSeq(groupSeq);
//			textEmailColumn.setOrderNum(order++);
//			kgoCasesetColumnRepository.save(textEmailColumn);
//
//			// 4. 『申請人姓名』欄位新增
//			KgoCasesetColumnPK textName = new KgoCasesetColumnPK();
//			textName.setCaseSetId(caseSetId);
//			textName.setColumnId(BaseColumnEnum.NAME.getColumnId());
//			textName.setVersion(KgoUtil.DEFAULT_VERSION_NUMBER);
//			KgoCasesetColumn textNameColumn = new KgoCasesetColumn();
//			textNameColumn.setId(textName);
//			textNameColumn.setColumnName(BaseColumnEnum.NAME.getColumnName());
//			textNameColumn.setLength(50);
//			textNameColumn.setColumnType(ColumnTypeEnum.TEXT.getValue());
//			textNameColumn.setIsMustKey(true);
//			textNameColumn.setGroupSeq(groupSeq);
//			textNameColumn.setOrderNum(order++);
//			kgoCasesetColumnRepository.save(textNameColumn);
//
//			// 5. 『申請人手機』欄位新增
//			KgoCasesetColumnPK textPhone = new KgoCasesetColumnPK();
//			textPhone.setCaseSetId(caseSetId);
//			textPhone.setColumnId(BaseColumnEnum.CELL_PHONE.getColumnId());
//			textPhone.setVersion(KgoUtil.DEFAULT_VERSION_NUMBER);
//			KgoCasesetColumn textPhoneColumn = new KgoCasesetColumn();
//			textPhoneColumn.setId(textPhone);
//			textPhoneColumn.setColumnName(BaseColumnEnum.CELL_PHONE.getColumnName());
//			textPhoneColumn.setLength(50);
//			textPhoneColumn.setColumnType(ColumnTypeEnum.TEXT_PHONE.getValue());
//			textPhoneColumn.setIsMustKey(true);
//			textPhoneColumn.setGroupSeq(groupSeq);
//			textPhoneColumn.setOrderNum(order++);
//			kgoCasesetColumnRepository.save(textPhoneColumn);

			/** GEO 20211109 add for 機關審核表單 */
			if (kgoCaseset.getIsOpenOrganForm() != null && kgoCaseset.getIsOpenOrganForm() == GeoBooleanType.ENABLED.getCode()) {
				Integer organGroupSeq = findOrganGroupSeq(caseSetId, KgoUtil.MYDATA_BASIC_GROUP_NAME);
				if (ObjectUtils.isEmpty(organGroupSeq) && !"A".equals(caseFlowType)) {
					geoKgoCasesetOrganGroupReposCustom.saveData(caseSetId, KgoUtil.DEFAULT_VERSION_NUMBER, KgoUtil.MYDATA_BASIC_GROUP_NAME, KgoUtil.DEFAULT_ORDER_NUMBER, KgoUtil.DEFAULT_VERSION_NUMBER, GeoBooleanType.DISABLED.getCode());
					int organGroupAfterSaveSeq = findOrganGroupSeq(caseSetId, KgoUtil.MYDATA_BASIC_GROUP_NAME);
					saveOrganForm(caseSetId, organGroupAfterSaveSeq,KgoUtil.DEFAULT_VERSION_NUMBER);
				} //if (ObjectUtils.isEmpty(organGroupSeq)
			} //if (kgoCaseset.getIsOpenOrganForm()...
		}
	}

	private void saveOrganForm(String caseSetId, Integer seq, Integer caseFormVersion) {
		/**儲存表單跟服務關聯*/
		GeoKgoCasesetColumnOrganPK kgoCasesetColumnPK = new GeoKgoCasesetColumnOrganPK();
		kgoCasesetColumnPK.setCaseSetId(caseSetId);
		kgoCasesetColumnPK.setColumnId("單行文字欄位");
		kgoCasesetColumnPK.setVersion(KgoUtil.DEFAULT_VERSION_NUMBER);
		kgoCasesetColumnPK.setCaseFormVersion(caseFormVersion);

		/**該服務表單 */
		GeoKgoCasesetColumnOrgan kgoCasesetColumn = new GeoKgoCasesetColumnOrgan();
		kgoCasesetColumn.setId(kgoCasesetColumnPK);
		kgoCasesetColumn.setColumnName("單行文字欄位");
		kgoCasesetColumn.setColumnType(ColumnTypeEnum.TEXT.getValue());
		kgoCasesetColumn.setGroupSeq(seq);
		kgoCasesetColumn.setIsMustKey(IsMustKeyEnum.NO.getBooleanValue());
		kgoCasesetColumn.setIsCheckFrequency(GeoBooleanType.DISABLED.getCode());
		kgoCasesetColumn.setIsFieldCheck(GeoBooleanType.DISABLED.getCode());
		kgoCasesetColumn.setLength(50);
		kgoCasesetColumn.setMemo("");
		kgoCasesetColumn.setOrderNum(KgoUtil.DEFAULT_VERSION_NUMBER);
		geoKgoCasesetColumnOrganRepository.save(kgoCasesetColumn);
	}

	/**
	 * 
	 * @param caseSetId
	 * @param memo
	 * @return
	 */
	private Integer findGroupSeq(String caseSetId, String memo) {
		List<CasesetGroupQueryDataMaxVersionDto> dtoList =
				kgoCasesetGroupRepository.findMaxVersionGroupData(caseSetId, memo);
		return ObjectUtils.isEmpty(dtoList) ? null : dtoList.get(0).getGroupSeq();
	}

	/**
	 * GEO 20211109 add 機關審核表單
	 * @param caseSetId
	 * @param memo
	 * @return
	 */
	private Integer findOrganGroupSeq(String caseSetId, String memo) {
		List<CasesetOrganGroupQueryDataMaxVersionDto> dtoList =
				geoKgoCasesetOrganGroupReposCustom.findMaxVersionGroupData(caseSetId, memo);
		return ObjectUtils.isEmpty(dtoList) ? null : dtoList.get(0).getGroupSeq();
	} //findOrganGroupSeq


	/**
	 * 機關折扣比率儲存
	 * @param rq
	 */
	@Override
	public OrganDiscountRatioSaveRs organDiscountRatioSave(OrganDiscountRatioQueryRq rq) {
		OrganDiscountRatioSaveViewForm viewForm = new OrganDiscountRatioSaveViewForm();
		OrganDiscountRatioSaveRs rs = new OrganDiscountRatioSaveRs();
		
		try {
			Integer ratio = rq.getPercent();
			String organId = rq.getOrganId();
			if(ratio == null || StringUtils.isBlank(organId)) {
				throw new KgoApiException(new ErrorResult(KgoBackEndApiError.DATA_FORMAT_ERROR));
			}
			OrganDiscountRatio discountData = geoOrganDiscountRatioRepository.findByOrganIdAndDiscountRatio(organId, ratio);
			if(discountData == null){
				discountData = new OrganDiscountRatio();
			}
			discountData.setOrganId(organId);
			discountData.setDiscountRatio(ratio);
			discountData.setEditTime(new Timestamp(System.currentTimeMillis()));
			discountData.setEditUser(KgoUtil.getBackendLoginUser().getUserId());
			geoOrganDiscountRatioRepository.save(discountData);

		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoBackEndApiError.FAIL_TO_SAVE.getErrorMsgKey());
			throw new KgoApiException("organRefundRatioSave error " + e.getMessage(), e);
		}
		viewForm.setMsg(SuccessMsg.SAVE.getMsg());
		rs.setData(viewForm);		
		return rs;
	}
	
	/**
	 * 機關退費比率查詢
	 * @param rq
	 */
	@Override
	public OrganComboBoxQueryRs getDisocuntOrganComboBox(OrganDiscountRatioQueryRq rq ){
		OrganComboBoxQueryViewForm viewForm = new OrganComboBoxQueryViewForm();
		OrganComboBoxQueryRs rs = new OrganComboBoxQueryRs();

		try {
			String organId = rq.getOrganId();
			if(StringUtils.isBlank(organId)) organId = KgoUtil.getBackendLoginUser().getOrgan();
			ComboBox organComboBox = organUnitManagementServiceHelper.getOneOrganComboBox(organId);
			viewForm.setOrganComboBox(organComboBox);
			rs.setData(viewForm);
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoCommonApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("organComboBoxAction error " + e.getMessage(), e);
		}

		return rs;
	}
	
	/**
	 * 機關優惠折扣查詢
	 */
	@Override
	public OrganDiscountRatioQueryRs organDiscountRatioQuery(OrganDiscountRatioQueryRq rq) {
		OrganDiscountRatioQueryViewForm viewForm = new OrganDiscountRatioQueryViewForm();
		OrganDiscountRatioQueryRs rs = new OrganDiscountRatioQueryRs();
		try{
			if(StringUtils.isBlank(rq.getOrganId())){
				throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
			}
			List<OrganDiscountRatio> entitys = geoOrganDiscountRatioReposCustom.OrganDiscountQuery(rq.getOrganId(),rq.getPercent());
			List<OrganDiscountRatioModel> modelList = entitys.stream().map(e->{
				OrganDiscountRatioModel model = OrganDiscountRatioModel.entityToModel(e);
				KgoOrgan organ = kgoOrganRepository.findByOrganId(e.getOrganId());
				model.setOrganName(organ.getOrganName());
				return model;
			}).collect(Collectors.toList());
			viewForm.setGrid(modelList);
			viewForm.setMsg(SuccessMsg.SAVE.getMsg());
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoCommonApiError.FAIL_TO_SEARCH.getErrorMsgKey());
			throw new KgoApiException("organDiscountRatioQuery error " + e.getMessage(), e);
		}
		rs.setData(viewForm);
		return rs;
	}
	@Override
	public OrganDiscountRatioDeleteRs organDiscountRatioDelete(OrganDiscountRatioQueryRq rq) {
		OrganDiscountRatioDeleteRs rs = new OrganDiscountRatioDeleteRs();
		OrganDiscountRatioDeleteViewForm viewForm = new OrganDiscountRatioDeleteViewForm();
		try{
			if(rq.getOrganDiscountRatioId() == null){
				throw new KgoApiException(new ErrorResult(KgoBackEndApiError.WRONG_PARAMETER));
			}
			geoOrganDiscountRatioRepository.deleteById(rq.getOrganDiscountRatioId());
			viewForm.setMsg(SuccessMsg.DELETE.getMsg());
		} catch (KgoApiException apiE) {
			LOGGER.error(apiE.getMessage());
			throw apiE;
		} catch (Exception e) {
			LOGGER.error(KgoCommonApiError.COMMON_UNKNOWN_EXCEPTION.getErrorMsgKey());
			throw new KgoApiException("organDiscountRatioQuery error " + e.getMessage(), e);
		}
		rs.setData(viewForm);
		return rs;

	}
}
