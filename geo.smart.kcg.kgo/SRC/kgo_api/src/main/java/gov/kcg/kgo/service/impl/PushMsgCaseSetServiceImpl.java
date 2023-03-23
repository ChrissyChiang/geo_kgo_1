package gov.kcg.kgo.service.impl;

import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.enums.backend.PushCaseStatusEnum;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.model.KgoCaseset;
import gov.kcg.kgo.model.KgoStatusMsg;
import gov.kcg.kgo.model.KgoStatusMsgCaseSet;
import gov.kcg.kgo.repository.KgoCasesetRepository;
import gov.kcg.kgo.repository.KgoStatusMsgCaseSetRepository;
import gov.kcg.kgo.repository.KgoStatusMsgRepository;
import gov.kcg.kgo.service.PushMsgCaseSetService;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.viewModel.backend.pushmsg.rq.PushMsgCaseSetEditRq;
import gov.kcg.kgo.viewModel.backend.pushmsg.rq.PushMsgCaseSetQueryRq;
import gov.kcg.kgo.viewModel.backend.pushmsg.rs.PushMsgCaseSetEditRs;
import gov.kcg.kgo.viewModel.backend.pushmsg.rs.PushMsgCaseSetQueryRs;
import gov.kcg.kgo.viewModel.backend.pushmsg.rs.bean.PushMsgCaseSetQueryDataGrid;
import gov.kcg.kgo.viewModel.backend.pushmsg.rs.bean.PushMsgCaseSetQueryViewForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PushMsgCaseSetServiceImpl implements PushMsgCaseSetService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PushMsgCaseSetServiceImpl.class);

    @Autowired
    private KgoCasesetRepository kgoCasesetRepository;

    @Autowired
    private KgoStatusMsgRepository kgoStatusMsgRepository;

    @Autowired
    private KgoStatusMsgCaseSetRepository kgoStatusMsgCaseSetRepository;

    @Override
    public PushMsgCaseSetQueryRs queryPushMsgCaseSetData(PushMsgCaseSetQueryRq pushMsgCaseSetQueryRq) {
        String caseSetId = pushMsgCaseSetQueryRq.getCaseSetId();

        String userId = KgoUtil.getLoginUserId();

        PushMsgCaseSetQueryRs pushMsgCaseSetQueryRs = new PushMsgCaseSetQueryRs();

        PushMsgCaseSetQueryViewForm pushMsgCaseSetQueryViewForm = new PushMsgCaseSetQueryViewForm();

        List<KgoStatusMsgCaseSet> kgoStatusMsgCaseSets = kgoStatusMsgCaseSetRepository.findAllByCaseSetId(caseSetId);

        Map<String, KgoStatusMsgCaseSet> map = kgoStatusMsgCaseSets.stream().collect(Collectors.toMap(c -> String.valueOf(c.getStatusMsgSeq()), c -> c));

        BackendLoginUserInfo backendLoginUser = KgoUtil.getBackendLoginUser();

        List<KgoStatusMsg> kgoStatusMsgList = kgoStatusMsgRepository.findAllByOrganId(backendLoginUser.getOrgan());

        List<PushMsgCaseSetQueryDataGrid> pushMsgManagementHomeDataGridList = new ArrayList<>();

        Optional<KgoCaseset> kgoCaseset = kgoCasesetRepository.findById(caseSetId);

        if (!kgoCaseset.isPresent())
            throw new KgoApiException("Can't find CaseSet, caseSetId is = " + caseSetId);

        String organId = kgoCaseset.get().getOwnerOrgan();

        if (!CollectionUtils.isEmpty(kgoStatusMsgList) && !CollectionUtils.isEmpty(kgoStatusMsgCaseSets)) {
            // List<Integer> kgoStatusMsgCaseSetSeq = new ArrayList<>();

            pushMsgCaseSetQueryViewForm.setCaseSetId(caseSetId);

            kgoStatusMsgList.stream().forEach(item -> {
                KgoStatusMsgCaseSet kgoStatusMsgCaseSet = map.get(String.valueOf(item.getSeq()));
                if (null != kgoStatusMsgCaseSet) {
                    PushMsgCaseSetQueryDataGrid pushMsgCaseSetQueryDataGrid = new PushMsgCaseSetQueryDataGrid();
                    pushMsgCaseSetQueryDataGrid.setStatusMsgSeq(item.getSeq());
                    pushMsgCaseSetQueryDataGrid.setCaseStatusName(item.getCaseStatusName());
                    pushMsgCaseSetQueryDataGrid.setIsEnable(kgoStatusMsgCaseSet.getIsEnable());
                    pushMsgManagementHomeDataGridList.add(pushMsgCaseSetQueryDataGrid);
                } else {
                    PushMsgCaseSetQueryDataGrid pushMsgCaseSetQueryDataGrid = new PushMsgCaseSetQueryDataGrid();
                    pushMsgCaseSetQueryDataGrid.setStatusMsgSeq(item.getSeq());
                    pushMsgCaseSetQueryDataGrid.setCaseStatusName(item.getCaseStatusName());
                    pushMsgCaseSetQueryDataGrid.setIsEnable(String.valueOf(Boolean.TRUE));
                    pushMsgManagementHomeDataGridList.add(pushMsgCaseSetQueryDataGrid);
                }
            });

            // kgoStatusMsgCaseSets.forEach(k -> {
            //     Optional<KgoStatusMsg> kgoStatusMsg = kgoStatusMsgRepository.findById(k.getStatusMsgSeq());

            //     if (kgoStatusMsg.isPresent()) {
            //         PushMsgCaseSetQueryDataGrid pushMsgCaseSetQueryDataGrid = new PushMsgCaseSetQueryDataGrid();

            //         pushMsgCaseSetQueryDataGrid.setStatusMsgSeq(k.getStatusMsgSeq());
            //         pushMsgCaseSetQueryDataGrid.setCaseStatusName(kgoStatusMsg.get().getCaseStatusName());
            //         pushMsgCaseSetQueryDataGrid.setIsEnable(k.getIsEnable());

            //         pushMsgManagementHomeDataGridList.add(pushMsgCaseSetQueryDataGrid);
            //     }

            //     kgoStatusMsgCaseSetSeq.add(k.getStatusMsgSeq());
            // });

            // List<KgoStatusMsg> kgoStatusMsgList = kgoStatusMsgRepository.findAllByOrganId(organId);

            // if (kgoStatusMsgList != null && !kgoStatusMsgList.isEmpty()) {
            //     List<Integer> kgoStatusMsgSeqList = new ArrayList<>();

            //     kgoStatusMsgList.forEach(x -> kgoStatusMsgSeqList.add(x.getSeq()));

            //     List<KgoStatusMsg> unavailable = kgoStatusMsgList.stream()
            //             .filter(e -> !kgoStatusMsgCaseSetSeq.contains(e.getSeq()))
            //             .collect(Collectors.toList());

            //     unavailable.forEach(x -> {
            //         PushMsgCaseSetQueryDataGrid pushMsgCaseSetQueryDataGrid = new PushMsgCaseSetQueryDataGrid();

            //         pushMsgCaseSetQueryDataGrid.setStatusMsgSeq(x.getSeq());
            //         pushMsgCaseSetQueryDataGrid.setCaseStatusName(x.getCaseStatusName());
            //         pushMsgCaseSetQueryDataGrid.setIsEnable(x.getIsEnable());

            //         pushMsgManagementHomeDataGridList.add(pushMsgCaseSetQueryDataGrid);
            //     });
            // }
        } else {
            List<KgoStatusMsg> kgoStatusMsgs = kgoStatusMsgRepository.findAllByOrganId(organId);

            if (kgoStatusMsgs != null && !kgoStatusMsgs.isEmpty()) {
                List<String> statusList = new ArrayList<>();

                kgoStatusMsgs.forEach(p -> statusList.add(p.getStatus()));

                List<PushCaseStatusEnum> unavailable = Stream.of(PushCaseStatusEnum.values())
                        .filter(e -> !statusList.contains(e.getValue()))
                        .collect(Collectors.toList());

                if (unavailable != null && !unavailable.isEmpty()) {
                    unavailable.forEach(w -> {
                        KgoStatusMsg kgoStatusMsg = new KgoStatusMsg();
                        kgoStatusMsg.setOrganId(organId);
                        kgoStatusMsg.setStatus(w.getValue());
                        kgoStatusMsg.setCaseStatusName(PushCaseStatusEnum.getLabelByValue(w.getValue()));
                        kgoStatusMsg.setMsgTitle(PushCaseStatusEnum.getDefaultMsgTitleByValue(w.getValue()));
                        kgoStatusMsg.setMsgBody(PushCaseStatusEnum.getDefaultMsgBodyByValue(w.getValue()));
                        kgoStatusMsg.setClickDetail(PushCaseStatusEnum.getDefaultClickDetailByValue(w.getValue()));
                        kgoStatusMsg.setIsEnable(String.valueOf(Boolean.TRUE));
                        kgoStatusMsg.setIsDefault(String.valueOf(Boolean.TRUE));
                        kgoStatusMsg.setCreateTime(DateUtil.getCurrentTimestamp());
                        kgoStatusMsg.setCreateUser(userId);

                        kgoStatusMsgRepository.save(kgoStatusMsg);
                    });
                }

                List<KgoStatusMsg> kgoStatusMsgAfterAdd = kgoStatusMsgRepository.findAllByOrganId(organId);

                kgoStatusMsgAfterAdd.forEach(p -> {
                    PushMsgCaseSetQueryDataGrid pushMsgCaseSetQueryDataGrid = new PushMsgCaseSetQueryDataGrid();
                    pushMsgCaseSetQueryDataGrid.setStatusMsgSeq(p.getSeq());
                    pushMsgCaseSetQueryDataGrid.setCaseStatusName(p.getCaseStatusName());
                    pushMsgCaseSetQueryDataGrid.setIsEnable(String.valueOf(Boolean.TRUE));

                    pushMsgManagementHomeDataGridList.add(pushMsgCaseSetQueryDataGrid);
                });
            } else {
                Stream.of(PushCaseStatusEnum.values()).forEach(pc -> {
                    KgoStatusMsg kgoStatusMsg = new KgoStatusMsg();

                    PushMsgCaseSetQueryDataGrid pushMsgCaseSetQueryDataGrid = new PushMsgCaseSetQueryDataGrid();

                    kgoStatusMsg.setOrganId(organId);
                    kgoStatusMsg.setStatus(pc.getValue());
                    kgoStatusMsg.setCaseStatusName(PushCaseStatusEnum.getLabelByValue(pc.getValue()));
                    kgoStatusMsg.setMsgTitle(PushCaseStatusEnum.getDefaultMsgTitleByValue(pc.getValue()));
                    kgoStatusMsg.setMsgBody(PushCaseStatusEnum.getDefaultMsgBodyByValue(pc.getValue()));
                    kgoStatusMsg.setClickDetail(PushCaseStatusEnum.getDefaultClickDetailByValue(pc.getValue()));
                    kgoStatusMsg.setIsEnable(String.valueOf(Boolean.TRUE));
                    kgoStatusMsg.setIsDefault(String.valueOf(Boolean.TRUE));
                    kgoStatusMsg.setCreateTime(DateUtil.getCurrentTimestamp());
                    kgoStatusMsg.setCreateUser(userId);

                    KgoStatusMsg kgoStatusMsgAfterAdd = kgoStatusMsgRepository.save(kgoStatusMsg);

                    pushMsgCaseSetQueryDataGrid.setStatusMsgSeq(kgoStatusMsgAfterAdd.getSeq());
                    pushMsgCaseSetQueryDataGrid.setCaseStatusName(kgoStatusMsgAfterAdd.getCaseStatusName());
                    pushMsgCaseSetQueryDataGrid.setIsEnable(String.valueOf(Boolean.TRUE));

                    pushMsgManagementHomeDataGridList.add(pushMsgCaseSetQueryDataGrid);
                });
            }
        }

        pushMsgCaseSetQueryViewForm.setPushMsgManagementHomeDataGridList(pushMsgManagementHomeDataGridList);

        pushMsgCaseSetQueryRs.setData(pushMsgCaseSetQueryViewForm);

        return pushMsgCaseSetQueryRs;
    }

    @Override
    public PushMsgCaseSetEditRs editPushMsgCaseSetData(PushMsgCaseSetEditRq pushMsgCaseSetEditRq) {
        String caseSetId = pushMsgCaseSetEditRq.getCaseSetId();

        String userId = KgoUtil.getLoginUserId();

        if (pushMsgCaseSetEditRq.getPushMsgCaseSetEditDataGridList() != null &&
                !pushMsgCaseSetEditRq.getPushMsgCaseSetEditDataGridList().isEmpty()) {
            pushMsgCaseSetEditRq.getPushMsgCaseSetEditDataGridList().forEach(p -> {
                KgoStatusMsgCaseSet kgoStatusMsgCaseSet = kgoStatusMsgCaseSetRepository.findByCaseSetIdAndStatusMsgSeq(caseSetId, p.getStatusMsgSeq());

                if (kgoStatusMsgCaseSet != null) {
                    kgoStatusMsgCaseSet.setStatusMsgSeq(p.getStatusMsgSeq());
                    kgoStatusMsgCaseSet.setIsEnable(p.getIsEnable());
                    kgoStatusMsgCaseSet.setCaseSetId(caseSetId);
                    kgoStatusMsgCaseSet.setUpdateTime(DateUtil.getCurrentTimestamp());
                    kgoStatusMsgCaseSet.setUpdateUser(userId);

                    kgoStatusMsgCaseSetRepository.save(kgoStatusMsgCaseSet);
                } else {
                    KgoStatusMsgCaseSet kgoStatusMsgCaseSetAdd = new KgoStatusMsgCaseSet();
                    kgoStatusMsgCaseSetAdd.setStatusMsgSeq(p.getStatusMsgSeq());
                    kgoStatusMsgCaseSetAdd.setIsEnable(p.getIsEnable());
                    kgoStatusMsgCaseSetAdd.setCaseSetId(caseSetId);
                    kgoStatusMsgCaseSetAdd.setUpdateTime(DateUtil.getCurrentTimestamp());
                    kgoStatusMsgCaseSetAdd.setUpdateUser(userId);

                    kgoStatusMsgCaseSetRepository.save(kgoStatusMsgCaseSetAdd);
                }
            });
        }

        return new PushMsgCaseSetEditRs();
    }

}
