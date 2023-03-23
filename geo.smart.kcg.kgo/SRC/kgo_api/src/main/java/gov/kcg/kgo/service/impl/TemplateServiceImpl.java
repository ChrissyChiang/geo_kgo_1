package gov.kcg.kgo.service.impl;

import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.enums.backend.*;
import gov.kcg.kgo.enums.common.ColumnTypeEnum;
import gov.kcg.kgo.geomodel.GeoTemplateOrganViewColumnDataGrid;
import gov.kcg.kgo.geoviewmodel.backend.rs.GeoTemplateOrganViewActionRs;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.GeoTemplateOranViewViewForm;
import gov.kcg.kgo.model.KgoCasesetColumnChildTemplate;
import gov.kcg.kgo.model.KgoCasesetColumnTemplate;
import gov.kcg.kgo.model.KgoCasesetTemplate;
import gov.kcg.kgo.repository.KgoCasesetColumnChildTemplateRepository;
import gov.kcg.kgo.repository.KgoCasesetColumnTemplateRepository;
import gov.kcg.kgo.repository.KgoCasesetTemplateRepository;
import gov.kcg.kgo.service.TemplateService;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rq.CasesetColumnData;
import gov.kcg.kgo.viewModel.backend.internetApply.formSet.groupColumnSave.rq.CasesetComplexColumnData;
import gov.kcg.kgo.viewModel.backend.template.add.rq.TemplateAddActionRq;
import gov.kcg.kgo.viewModel.backend.template.add.rs.TemplateAddActionRs;
import gov.kcg.kgo.viewModel.backend.template.delete.rq.TemplateDeleteActionRq;
import gov.kcg.kgo.viewModel.backend.template.delete.rs.TemplateDeleteActionRs;
import gov.kcg.kgo.viewModel.backend.template.query.rq.TemplateQueryActionRq;
import gov.kcg.kgo.viewModel.backend.template.query.rs.TemplateQueryActionRs;
import gov.kcg.kgo.viewModel.backend.template.query.rs.bean.TemplateQueryActionDataGrid;
import gov.kcg.kgo.viewModel.backend.template.query.rs.bean.TemplateQueryActionViewForm;
import gov.kcg.kgo.viewModel.backend.template.update.rq.TemplateUpdateActionRq;
import gov.kcg.kgo.viewModel.backend.template.update.rq.bean.TemplateUpdateCasesetColumnData;
import gov.kcg.kgo.viewModel.backend.template.update.rq.bean.TemplateUpdateCasesetComplexColumnData;
import gov.kcg.kgo.viewModel.backend.template.update.rs.TemplateUpdateActionRs;
import gov.kcg.kgo.viewModel.backend.template.view.rq.TemplateViewActionRq;
import gov.kcg.kgo.viewModel.backend.template.view.rs.TemplateViewActionRs;
import gov.kcg.kgo.viewModel.backend.template.view.rs.TemplateViewColumnDataGrid;
import gov.kcg.kgo.viewModel.backend.template.view.rs.TemplateViewComplexColumnData;
import gov.kcg.kgo.viewModel.backend.template.view.rs.TemplateViewViewForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static gov.kcg.kgo.enums.common.ColumnTypeEnum.FIL;

@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private KgoCasesetTemplateRepository kgoCasesetTemplateRepository;

    @Autowired
    private KgoCasesetColumnTemplateRepository kgoCasesetColumnTemplateRepository;

    @Autowired
    private KgoCasesetColumnChildTemplateRepository kgoCasesetColumnChildTemplateRepository;

    private final static String LIKE_CHARACTER = "%";

    @Override
    @Transactional
    public TemplateDeleteActionRs templateDeleteAction(TemplateDeleteActionRq rq) {
        Integer seq = rq.getSeq();
        Optional<KgoCasesetTemplate> kgoCasesetTemplateOptional = kgoCasesetTemplateRepository.findById(seq);
        if (kgoCasesetTemplateOptional.isPresent()) {
            KgoCasesetTemplate kgoCasesetTemplate = kgoCasesetTemplateOptional.get();
            kgoCasesetTemplate.setSuspend(TemplateSuspendEnum.ONE.getValue());
            kgoCasesetTemplateRepository.save(kgoCasesetTemplate);
        }
        return new TemplateDeleteActionRs();
    }


    @Override
    @Transactional
    public TemplateAddActionRs templateAddAction(TemplateAddActionRq rq) {
        KgoCasesetTemplate kgoCasesetTemplate = new KgoCasesetTemplate();
        BackendLoginUserInfo backendLoginUser = KgoUtil.getBackendLoginUser();
        String organ = backendLoginUser.getOrgan();
        kgoCasesetTemplate.setSuspend(TemplateSuspendEnum.ZERO.getValue());
        kgoCasesetTemplate.setName(rq.getName());
        kgoCasesetTemplate.setIsDefault(TemplateIsDefaultEnum.ZERO.getValue());
        kgoCasesetTemplate.setOrgan(organ);
        kgoCasesetTemplate = kgoCasesetTemplateRepository.save(kgoCasesetTemplate);
        List<KgoCasesetColumnChildTemplate> kgoCasesetColumnChildTemplateList = new ArrayList<>();
        for (CasesetColumnData casesetColumnData : rq.getColumnData()) {
            // 新增column
            String columnId = casesetColumnData.getColumnId();
            KgoCasesetColumnTemplate kgoCasesetColumnTemplate = new KgoCasesetColumnTemplate();
            kgoCasesetColumnTemplate.setColumnId(columnId);
            kgoCasesetColumnTemplate.setColumnName(casesetColumnData.getColumnName());
            kgoCasesetColumnTemplate.setColumnType(casesetColumnData.getColumnType());
            kgoCasesetColumnTemplate.setColumnValue(casesetColumnData.getColumnValue());
            IsMustKeyEnum isMustKeyEnum = IsMustKeyEnum.getIsMustKeyEnum(casesetColumnData.getIsMustKey());
            if (null != isMustKeyEnum) {
                kgoCasesetColumnTemplate.setIsMustKey(isMustKeyEnum.getBooleanValue());
            }
            /**GEO 20211019 add */
            IsCHeckFrequencyEnum isCHeckFrequencyEnum = IsCHeckFrequencyEnum.getIsCheckFrequencyEnum(casesetColumnData.getIsCheckFrequency());
            if (null != isCHeckFrequencyEnum) {
                kgoCasesetColumnTemplate.setIsCheckFrequency(Integer.parseInt(isCHeckFrequencyEnum.getValue()));
            }
            /** GEO 20211102 add 欄位勾選*/
            IsFieldCheckEnum isFieldCheckEnum = IsFieldCheckEnum.getIsFieldCheckEnum(casesetColumnData.getIsFieldCheck());
            if (null != isFieldCheckEnum) {
                kgoCasesetColumnTemplate.setIsFieldCheck(Integer.parseInt(isFieldCheckEnum.getValue()));
            }
            kgoCasesetColumnTemplate.setLength(casesetColumnData.getLength());
            kgoCasesetColumnTemplate.setMemo(casesetColumnData.getMemo());
            kgoCasesetColumnTemplate.setMyDataCheckType(casesetColumnData.getMyDataCheckType());
            kgoCasesetColumnTemplate.setMyDataCheckValue(casesetColumnData.getMyDataCheckValue());
            kgoCasesetColumnTemplate.setMyDataClientId(casesetColumnData.getMyDataClientId());
            kgoCasesetColumnTemplate.setMyDataColumn(casesetColumnData.getMyDataColumn());
            kgoCasesetColumnTemplate.setMyDataId(casesetColumnData.getMyDataId());
            kgoCasesetColumnTemplate.setMyDataType(casesetColumnData.getMyDataType());
            kgoCasesetColumnTemplate.setTemplateSeq(kgoCasesetTemplate.getSeq());
            kgoCasesetColumnTemplate.setSuspend(TemplateSuspendEnum.ZERO.getValue());
            kgoCasesetColumnTemplate.setOrderNum(casesetColumnData.getOrderNum());
            if (FIL.getValue().equals(casesetColumnData.getColumnType())) {
                kgoCasesetColumnTemplate.setFileType(casesetColumnData.getFileType());// When ColumnType is Fil, save FileType 20201208 By Jay
            }
            kgoCasesetColumnTemplate = kgoCasesetColumnTemplateRepository.save(kgoCasesetColumnTemplate);
            List<List<CasesetComplexColumnData>> casesetComplexColumnDataList = casesetColumnData.getComplex();
            if (!CollectionUtils.isEmpty(casesetComplexColumnDataList)) {
                for (List<CasesetComplexColumnData> tempList : casesetComplexColumnDataList) {
                    if (!CollectionUtils.isEmpty(tempList)) {
                        for (CasesetComplexColumnData casesetComplexColumnData : tempList) {
                            // 新增child column
                            KgoCasesetColumnChildTemplate kgoCasesetColumnChildTemplate = new KgoCasesetColumnChildTemplate();
                            kgoCasesetColumnChildTemplate.setColumnSeq(kgoCasesetColumnTemplate.getSeq());
                            kgoCasesetColumnChildTemplate.setCColumnId(casesetComplexColumnData.getcColumnId());
                            kgoCasesetColumnChildTemplate.setColumnId(columnId);
                            kgoCasesetColumnChildTemplate.setBText(casesetComplexColumnData.getbText());
                            kgoCasesetColumnChildTemplate.setColumnType(casesetComplexColumnData.getColumnType());
                            kgoCasesetColumnChildTemplate.setColumnValue(casesetComplexColumnData.getColumnValue());
                            kgoCasesetColumnChildTemplate.setFText(casesetComplexColumnData.getfText());
                            IsMustKeyEnum isMustKeyEnumInner = IsMustKeyEnum.getIsMustKeyEnum(casesetComplexColumnData.getIsMustKey());
                            if (null != isMustKeyEnumInner) {
                                kgoCasesetColumnChildTemplate.setIsMustKey(isMustKeyEnumInner.getBooleanValue());
                            }
                            /**GEO 20211019 add */
                            IsCHeckFrequencyEnum isCHeckFrequencyEnumInner = IsCHeckFrequencyEnum.getIsCheckFrequencyEnum(casesetComplexColumnData.getIsCheckFrequency());
                            if (null != isCHeckFrequencyEnumInner) {
                                kgoCasesetColumnChildTemplate.setIsCheckFrequency(Integer.parseInt(isCHeckFrequencyEnumInner.getValue()));
                            }
                            /** GEO 20211102 add 欄位勾選*/
                            IsFieldCheckEnum isFieldCheckEnumInner = IsFieldCheckEnum.getIsFieldCheckEnum(casesetComplexColumnData.getIsFieldCheck());
                            if (null != isFieldCheckEnumInner) {
                                kgoCasesetColumnChildTemplate.setIsFieldCheck(Integer.parseInt(isFieldCheckEnumInner.getValue()));
                            }
                            kgoCasesetColumnChildTemplate.setLength(casesetComplexColumnData.getLength());
                            kgoCasesetColumnChildTemplate.setMemo(casesetComplexColumnData.getMemo());
                            kgoCasesetColumnChildTemplate.setOrderNum(casesetComplexColumnData.getOrderNum());
                            kgoCasesetColumnChildTemplate.setPColumnId(casesetComplexColumnData.getpColumnId());
                            kgoCasesetColumnChildTemplate.setRow(casesetComplexColumnData.getRow());
                            kgoCasesetColumnChildTemplate.setVGroup(casesetComplexColumnData.getvGroup());
                            kgoCasesetColumnChildTemplate.setSuspend(TemplateSuspendEnum.ZERO.getValue());
                            kgoCasesetColumnChildTemplateList.add(kgoCasesetColumnChildTemplate);
                        }
                    }
                }
            }
        }
        kgoCasesetColumnChildTemplateRepository.saveAllBatch(kgoCasesetColumnChildTemplateList);
        return new TemplateAddActionRs();
    }

    @Override
    @Transactional
    public TemplateUpdateActionRs templateUpdateAction(TemplateUpdateActionRq rq) {
        Optional<KgoCasesetTemplate> kgoCasesetTemplateOptional = kgoCasesetTemplateRepository.findById(rq.getSeq());
        if (kgoCasesetTemplateOptional.isPresent()) {
            KgoCasesetTemplate kgoCasesetTemplate = kgoCasesetTemplateOptional.get();
            kgoCasesetTemplate.setName(rq.getName());
            kgoCasesetTemplate = kgoCasesetTemplateRepository.save(kgoCasesetTemplate);
            List<KgoCasesetColumnChildTemplate> kgoCasesetColumnChildTemplateList = new ArrayList<>();
            List<KgoCasesetColumnTemplate> kgoCasesetColumnTemplates = kgoCasesetColumnTemplateRepository.findByTemplateSeqAndSuspend(rq.getSeq(), TemplateSuspendEnum.ZERO.getValue());
            List<TemplateUpdateCasesetColumnData> columnData = rq.getColumnData();
            // 刪除childColumn
            List<Integer> oriColumnSeqs = kgoCasesetColumnTemplates.stream().map(KgoCasesetColumnTemplate::getSeq).collect(Collectors.toList());
            List<KgoCasesetColumnChildTemplate> kgoCasesetColumnChildTemplates = kgoCasesetColumnChildTemplateRepository.findByColumnSeqInAndSuspend(oriColumnSeqs, TemplateSuspendEnum.ZERO.getValue());
            List<Integer> childSeqs = columnData.stream().flatMap(item -> CollectionUtils.isEmpty(item.getComplex()) ? Stream.empty() : item.getComplex().stream().flatMap(innerItem -> innerItem.stream().map(TemplateUpdateCasesetComplexColumnData::getSeq))).collect(Collectors.toList());
            List<KgoCasesetColumnChildTemplate> deleteChildList = kgoCasesetColumnChildTemplates.stream().filter(item -> !childSeqs.contains(item.getSeq())).collect(Collectors.toList());
            deleteChildList.forEach(item -> item.setSuspend(TemplateSuspendEnum.ONE.getValue()));
            kgoCasesetColumnChildTemplateRepository.saveAllBatch(deleteChildList);
            // 刪除column
            List<Integer> columnSeqs = columnData.stream().map(TemplateUpdateCasesetColumnData::getSeq).collect(Collectors.toList());
            List<KgoCasesetColumnTemplate> deleteList = kgoCasesetColumnTemplates.stream().filter(item -> !columnSeqs.contains(item.getSeq())).collect(Collectors.toList());
            deleteList.forEach(item -> item.setSuspend(TemplateSuspendEnum.ONE.getValue()));
            kgoCasesetColumnTemplateRepository.saveAllBatch(deleteList);
            for (TemplateUpdateCasesetColumnData casesetColumnData : columnData) {
                // 新增or修改column
                KgoCasesetColumnTemplate kgoCasesetColumnTemplate = new KgoCasesetColumnTemplate();
                kgoCasesetColumnTemplate.setSeq(casesetColumnData.getSeq());
                kgoCasesetColumnTemplate.setTemplateSeq(kgoCasesetTemplate.getSeq());
                kgoCasesetColumnTemplate.setColumnId(casesetColumnData.getColumnId());
                kgoCasesetColumnTemplate.setColumnName(casesetColumnData.getColumnName());
                kgoCasesetColumnTemplate.setColumnType(casesetColumnData.getColumnType());
                kgoCasesetColumnTemplate.setColumnValue(casesetColumnData.getColumnValue());
                IsMustKeyEnum isMustKeyEnum = IsMustKeyEnum.getIsMustKeyEnum(casesetColumnData.getIsMustKey());
                if (null != isMustKeyEnum) {
                    kgoCasesetColumnTemplate.setIsMustKey(isMustKeyEnum.getBooleanValue());
                }
                /**GEO 20211019 add */
                IsCHeckFrequencyEnum isCHeckFrequencyEnum = IsCHeckFrequencyEnum.getIsCheckFrequencyEnum(casesetColumnData.getIsCheckFrequency());
                if (null != isCHeckFrequencyEnum) {
                    kgoCasesetColumnTemplate.setIsCheckFrequency(Integer.parseInt(isCHeckFrequencyEnum.getValue()));
                }
                /** GEO 20211102 add 欄位勾選*/
                IsFieldCheckEnum isFieldCheckEnum = IsFieldCheckEnum.getIsFieldCheckEnum(casesetColumnData.getIsFieldCheck());
                if (null != isFieldCheckEnum) {
                    kgoCasesetColumnTemplate.setIsFieldCheck(Integer.parseInt(isFieldCheckEnum.getValue()));
                }
                kgoCasesetColumnTemplate.setLength(casesetColumnData.getLength());
                kgoCasesetColumnTemplate.setMemo(casesetColumnData.getMemo());
                kgoCasesetColumnTemplate.setMyDataCheckType(casesetColumnData.getMyDataCheckType());
                kgoCasesetColumnTemplate.setMyDataCheckValue(casesetColumnData.getMyDataCheckValue());
                kgoCasesetColumnTemplate.setMyDataClientId(casesetColumnData.getMyDataClientId());
                kgoCasesetColumnTemplate.setMyDataColumn(casesetColumnData.getMyDataColumn());
                kgoCasesetColumnTemplate.setMyDataId(casesetColumnData.getMyDataId());
                kgoCasesetColumnTemplate.setMyDataType(casesetColumnData.getMyDataType());
                kgoCasesetColumnTemplate.setOrderNum(casesetColumnData.getOrderNum());
                kgoCasesetColumnTemplate.setSuspend(TemplateSuspendEnum.ZERO.getValue());
                if (FIL.getValue().equals(casesetColumnData.getColumnType())) {
                    kgoCasesetColumnTemplate.setFileType(casesetColumnData.getFileType());// When ColumnType is Fil, save FileType 20201208 By Jay
                }
                kgoCasesetColumnTemplate = kgoCasesetColumnTemplateRepository.save(kgoCasesetColumnTemplate);
                // 新增or修改child column
                List<List<TemplateUpdateCasesetComplexColumnData>> casesetComplexColumnDataList = casesetColumnData.getComplex();
                if (!CollectionUtils.isEmpty(casesetComplexColumnDataList)) {
                    for (List<TemplateUpdateCasesetComplexColumnData> tempList : casesetComplexColumnDataList) {
                        if (!CollectionUtils.isEmpty(tempList)) {
                            for (TemplateUpdateCasesetComplexColumnData casesetComplexColumnData : tempList) {
                                KgoCasesetColumnChildTemplate kgoCasesetColumnChildTemplate = new KgoCasesetColumnChildTemplate();
                                kgoCasesetColumnChildTemplate.setSeq(casesetComplexColumnData.getSeq());
                                kgoCasesetColumnChildTemplate.setColumnSeq(kgoCasesetColumnTemplate.getSeq());
                                kgoCasesetColumnChildTemplate.setCColumnId(casesetComplexColumnData.getcColumnId());
                                kgoCasesetColumnChildTemplate.setColumnId(casesetColumnData.getColumnId());
                                kgoCasesetColumnChildTemplate.setBText(casesetComplexColumnData.getbText());
                                kgoCasesetColumnChildTemplate.setColumnType(casesetComplexColumnData.getColumnType());
                                kgoCasesetColumnChildTemplate.setColumnValue(casesetComplexColumnData.getColumnValue());
                                kgoCasesetColumnChildTemplate.setFText(casesetComplexColumnData.getfText());
                                IsMustKeyEnum isMustKeyEnumInner = IsMustKeyEnum.getIsMustKeyEnum(casesetComplexColumnData.getIsMustKey());
                                if (null != isMustKeyEnumInner) {
                                    kgoCasesetColumnChildTemplate.setIsMustKey(isMustKeyEnumInner.getBooleanValue());
                                }
                                /**GEO 20211019 add */
                                IsCHeckFrequencyEnum isCHeckFrequencyEnumInner = IsCHeckFrequencyEnum.getIsCheckFrequencyEnum(casesetComplexColumnData.getIsCheckFrequency());
                                if (null != isCHeckFrequencyEnumInner) {
                                    kgoCasesetColumnChildTemplate.setIsCheckFrequency(Integer.parseInt(isCHeckFrequencyEnumInner.getValue()));
                                }
                                /** GEO 20211102 add 欄位勾選*/
                                IsFieldCheckEnum isFieldCheckEnumInner = IsFieldCheckEnum.getIsFieldCheckEnum(casesetComplexColumnData.getIsFieldCheck());
                                if (null != isFieldCheckEnumInner) {
                                    kgoCasesetColumnChildTemplate.setIsFieldCheck(Integer.parseInt(isFieldCheckEnumInner.getValue()));
                                }
                                kgoCasesetColumnChildTemplate.setLength(casesetComplexColumnData.getLength());
                                kgoCasesetColumnChildTemplate.setMemo(casesetComplexColumnData.getMemo());
                                kgoCasesetColumnChildTemplate.setOrderNum(casesetComplexColumnData.getOrderNum());
                                kgoCasesetColumnChildTemplate.setPColumnId(casesetComplexColumnData.getpColumnId());
                                kgoCasesetColumnChildTemplate.setRow(casesetComplexColumnData.getRow());
                                kgoCasesetColumnChildTemplate.setVGroup(casesetComplexColumnData.getvGroup());
                                kgoCasesetColumnChildTemplate.setSuspend(TemplateSuspendEnum.ZERO.getValue());
                                kgoCasesetColumnChildTemplateList.add(kgoCasesetColumnChildTemplate);
                            }
                        }
                    }
                }
            }
            kgoCasesetColumnChildTemplateRepository.saveAllBatch(kgoCasesetColumnChildTemplateList);
        }
        return new TemplateUpdateActionRs();
    }

    @Override
    @Transactional
    public TemplateQueryActionRs templateQueryAction(TemplateQueryActionRq rq) {
        String name = rq.getName();
        List<KgoCasesetTemplate> kgoCasesetTemplates = kgoCasesetTemplateRepository.findByIsDefault(TemplateIsDefaultEnum.ONE.getValue());
        BackendLoginUserInfo backendLoginUser = KgoUtil.getBackendLoginUser();
        String organ = backendLoginUser.getOrgan();
        if (!StringUtils.isEmpty(name)) {
            List<KgoCasesetTemplate> kgoCasesetTemplatesTemp = kgoCasesetTemplateRepository.findByNameLikeAndSuspendAndOrgan(LIKE_CHARACTER + name + LIKE_CHARACTER, TemplateSuspendEnum.ZERO.getValue(), organ);
            kgoCasesetTemplates.addAll(kgoCasesetTemplatesTemp);
        } else {
            List<KgoCasesetTemplate> kgoCasesetTemplatesTemp = kgoCasesetTemplateRepository.findBySuspendAndOrgan(TemplateSuspendEnum.ZERO.getValue(), organ);
            kgoCasesetTemplates.addAll(kgoCasesetTemplatesTemp);
        }
        TemplateQueryActionViewForm templateQueryActionViewForm = new TemplateQueryActionViewForm();
        List<TemplateQueryActionDataGrid> resultList = new ArrayList<>();
        for (KgoCasesetTemplate kgoCasesetTemplate : kgoCasesetTemplates) {
            TemplateQueryActionDataGrid templateQueryActionDataGrid = new TemplateQueryActionDataGrid();
            templateQueryActionDataGrid.setName(kgoCasesetTemplate.getName());
            templateQueryActionDataGrid.setSeq(kgoCasesetTemplate.getSeq());
            templateQueryActionDataGrid.setIsDefault(kgoCasesetTemplate.getIsDefault());
            resultList.add(templateQueryActionDataGrid);
        }
        templateQueryActionViewForm.setTemplateQueryActionDataGrids(resultList);
        TemplateQueryActionRs templateQueryActionRs = new TemplateQueryActionRs();
        templateQueryActionRs.setData(templateQueryActionViewForm);
        return templateQueryActionRs;
    }

    @Override
    @Transactional
    public TemplateViewActionRs templateViewAction(TemplateViewActionRq rq) {
        Optional<KgoCasesetTemplate> kgoCasesetTemplateOptional = kgoCasesetTemplateRepository.findBySeqAndSuspend(rq.getSeq(), TemplateSuspendEnum.ZERO.getValue());
        TemplateViewActionRs templateViewActionRs = new TemplateViewActionRs();
        TemplateViewViewForm templateViewViewForm = new TemplateViewViewForm();
        if (kgoCasesetTemplateOptional.isPresent()) {
            KgoCasesetTemplate kgoCasesetTemplate = kgoCasesetTemplateOptional.get();
            List<KgoCasesetColumnTemplate> kgoCasesetColumnTemplates = kgoCasesetColumnTemplateRepository.findByTemplateSeqAndSuspend(kgoCasesetTemplate.getSeq(), TemplateSuspendEnum.ZERO.getValue());
            List<Integer> seqs = kgoCasesetColumnTemplates.stream().map(KgoCasesetColumnTemplate::getSeq).collect(Collectors.toList());
            List<KgoCasesetColumnChildTemplate> kgoCasesetColumnChildTemplates = kgoCasesetColumnChildTemplateRepository.findByColumnSeqInAndSuspend(seqs, TemplateSuspendEnum.ZERO.getValue());
            Map<Integer, List<KgoCasesetColumnChildTemplate>> groupByMap = kgoCasesetColumnChildTemplates.stream().collect(Collectors.groupingBy(KgoCasesetColumnChildTemplate::getColumnSeq));
            List<TemplateViewColumnDataGrid> dataGridList =
                    kgoCasesetColumnTemplates.stream().map(dl -> {
                        List<List<TemplateViewComplexColumnData>> complexDataList = null;
                        if (dl.getColumnType().equalsIgnoreCase(ColumnTypeEnum.M.getValue())) {
                            List<KgoCasesetColumnChildTemplate> kgoCasesetColumnChildTemplatesItem = groupByMap.get(dl.getSeq());
                            Map<Integer, List<KgoCasesetColumnChildTemplate>> dataMap = kgoCasesetColumnChildTemplatesItem.stream()
                                    .collect(Collectors.groupingBy(
                                            KgoCasesetColumnChildTemplate::getRow,
                                            HashMap::new,
                                            Collectors.toCollection(LinkedList::new))
                                    );
                            complexDataList = dataMap.keySet()
                                    .stream()
                                    .map(i -> dataMap.get(i)
                                            .stream()
                                            .map(cl -> {
                                                String vGroup = StringUtils.isBlank(cl.getVGroup()) ? StringUtils.EMPTY : cl.getVGroup();
                                                TemplateViewComplexColumnData complexData = new TemplateViewComplexColumnData();
                                                complexData.setSeq(cl.getSeq());
                                                complexData.setbText(cl.getBText());
                                                complexData.setcColumnId(cl.getCColumnId());
                                                complexData.setColumnType(cl.getColumnType());
                                                complexData.setColumnValue(cl.getColumnValue());
                                                complexData.setfText(cl.getFText());
                                                complexData.setLength(cl.getLength());
                                                complexData.setIsMustKey(
                                                        IsMustKeyEnum.getIsMustKeyEnum(cl.getIsMustKey()).getValue());
                                                /**GEO 20211019 add */
                                                complexData.setIsCheckFrequency(
                                                        IsCHeckFrequencyEnum.getIsCheckFrequencyEnum(cl.getIsCheckFrequency()).getValue());
                                                /** GEO 20211102 add 欄位勾選*/
                                                complexData.setIsFieldCheck(
                                                        IsFieldCheckEnum.getIsFieldCheckEnum(cl.getIsFieldCheck()).getValue());
                                                complexData.setOrderNum(cl.getOrderNum());
                                                complexData.setpColumnId(cl.getPColumnId());
                                                complexData.setRow(cl.getRow());
                                                complexData.setvGroup(vGroup);
                                                return complexData;
                                            }).collect(Collectors.toList())
                                    ).collect(Collectors.toList());
                        }
                        TemplateViewColumnDataGrid dg = new TemplateViewColumnDataGrid();
                        dg.setSeq(dl.getSeq());
                        dg.setColumnId(dl.getColumnId());
                        dg.setColumnName(dl.getColumnName());
                        dg.setColumnType(dl.getColumnType());
                        dg.setColumnValue(dl.getColumnValue());
                        ColumnTypeEnum columnTypeEnum = ColumnTypeEnum.getColumnTypeEnum(dl.getColumnType());
                        if (null != columnTypeEnum) {
                            dg.setColumnTypeName(columnTypeEnum.getLabel());
                        }
                        IsMustKeyEnum aEnum = IsMustKeyEnum.getIsMustKeyEnum(dl.getIsMustKey());
                        dg.setIsMustKey(aEnum.getValue());
                        dg.setIsMustKeyStr(aEnum.getLabel());

                        /**GEO 20211019 add */
                        IsCHeckFrequencyEnum bEnum = IsCHeckFrequencyEnum.getIsCheckFrequencyEnum(dl.getIsCheckFrequency());
                        dg.setIsCheckFrequency(bEnum.getValue());
                        dg.setIsCheckFrequencyStr(bEnum.getLabel());
                        /** GEO 20211102 add 欄位勾選*/
                        IsFieldCheckEnum checkFieldEnum = IsFieldCheckEnum.getIsFieldCheckEnum(dl.getIsFieldCheck());
                        dg.setIsFieldCheck(checkFieldEnum.getValue());
                        dg.setIsFieldCheckStr(checkFieldEnum.getLabel());

                        dg.setLength(dl.getLength());
                        dg.setMemo(dl.getMemo());
                        dg.setMyDataCheckType(dl.getMyDataCheckType());
                        dg.setMyDataCheckValue(dl.getMyDataCheckValue());
                        dg.setMyDataColumn(dl.getMyDataColumn());
                        dg.setMyDataId(dl.getMyDataId());
                        dg.setOrderNum(dl.getOrderNum());
                        if (FIL.getValue().equals(dl.getColumnType())) {//if ColumnType is Fil, set FileType 20201208 By Jay
                            dg.setFileType(dl.getFileType());
                        }
                        dg.setComplex(complexDataList);
                        return dg;
                    }).collect(Collectors.toList());
            templateViewViewForm.setGrid(dataGridList);
        }
        templateViewActionRs.setData(templateViewViewForm);
        return templateViewActionRs;
    }

    /** GEO 20211108 add for 機關審核表單
     * 取得機關審核表單初始欄位
     */
    @Override
    @Transactional
    public GeoTemplateOrganViewActionRs templateOrganViewAction() {
        GeoTemplateOrganViewActionRs templateViewActionRs = new GeoTemplateOrganViewActionRs();
        GeoTemplateOranViewViewForm templateViewViewForm = new GeoTemplateOranViewViewForm();
        List<GeoTemplateOrganViewColumnDataGrid> dataGridList = new ArrayList<>();
        GeoTemplateOrganViewColumnDataGrid grid = new GeoTemplateOrganViewColumnDataGrid();
        grid.setColumnId("Name");
        grid.setColumnName("單行文字欄位");
        grid.setColumnId("單行文字欄位");
        grid.setColumnType(ColumnTypeEnum.TEXT.getValue());
        grid.setColumnTypeName(ColumnTypeEnum.TEXT.getLabel());
        grid.setColumnValue("");
        grid.setLength(50);
        grid.setOrderNum(1);
        grid.setMemo("");
        dataGridList.add(grid);
        templateViewViewForm.setGrid(dataGridList);
        templateViewActionRs.setData(templateViewViewForm);
        return templateViewActionRs;
    } //templateOrganViewAction
}
