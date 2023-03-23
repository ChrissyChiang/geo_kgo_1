package gov.kcg.kgo.service.impl;

import gov.kcg.kgo.enums.backend.PushCaseStatusEnum;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.model.KgoStatusMsg;
import gov.kcg.kgo.model.KgoUser;
import gov.kcg.kgo.repository.KgoStatusMsgRepository;
import gov.kcg.kgo.repository.KgoUserRepository;
import gov.kcg.kgo.service.PushMsgManagementService;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.viewModel.backend.pushmsg.rq.PushMsgManagementDeleteRq;
import gov.kcg.kgo.viewModel.backend.pushmsg.rq.PushMsgManagementEditRq;
import gov.kcg.kgo.viewModel.backend.pushmsg.rq.PushMsgManagementQueryRq;
import gov.kcg.kgo.viewModel.backend.pushmsg.rs.PushMsgManagementDeleteRs;
import gov.kcg.kgo.viewModel.backend.pushmsg.rs.PushMsgManagementEditRs;
import gov.kcg.kgo.viewModel.backend.pushmsg.rs.PushMsgManagementHomeRs;
import gov.kcg.kgo.viewModel.backend.pushmsg.rs.PushMsgManagementQueryRs;
import gov.kcg.kgo.viewModel.backend.pushmsg.rs.bean.PushMsgManagementHomeDataGrid;
import gov.kcg.kgo.viewModel.backend.pushmsg.rs.bean.PushMsgManagementHomeViewForm;
import gov.kcg.kgo.viewModel.backend.pushmsg.rs.bean.PushMsgManagementQueryViewForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class PushMsgManagementServiceImpl implements PushMsgManagementService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PushMsgManagementServiceImpl.class);

    @Autowired
    private KgoUserRepository kgoUserRepository;

    @Autowired
    private KgoStatusMsgRepository kgoStatusMsgRepository;

    @Override
    public PushMsgManagementHomeRs getUserIdAndOrganId() {
        String userId = KgoUtil.getLoginUserId();

        KgoUser kgoUser = kgoUserRepository.findByUserId(userId);

        if (kgoUser == null)
            throw new KgoApiException("Can't find user id");

        String organId = kgoUser.getOrgan();

        List<KgoStatusMsg> kgoStatusMsgList = kgoStatusMsgRepository.findAllByOrganId(organId);

        PushMsgManagementHomeViewForm pushMsgManagementHomeViewForm = new PushMsgManagementHomeViewForm();
        pushMsgManagementHomeViewForm.setUserId(userId);
        pushMsgManagementHomeViewForm.setOrganId(organId);

        List<PushMsgManagementHomeDataGrid> pushMsgManagementHomeDataGrids = new ArrayList<>();

        if (kgoStatusMsgList != null && !kgoStatusMsgList.isEmpty()) {
            kgoStatusMsgList.forEach(k -> {
                PushMsgManagementHomeDataGrid pushMsgManagementHomeDataGrid = new PushMsgManagementHomeDataGrid();

                Stream.of(PushCaseStatusEnum.values()).forEach(c -> {
                    if (!c.getValue().equals(k.getStatus())) {
                        pushMsgManagementHomeDataGrid.setStatus(k.getStatus());
                        pushMsgManagementHomeDataGrid.setCaseStatusName(k.getCaseStatusName());
                        pushMsgManagementHomeDataGrid.setIsDefault(k.getIsDefault());
                    }
                });

                pushMsgManagementHomeDataGrids.add(pushMsgManagementHomeDataGrid);
            });
        }

        pushMsgManagementHomeViewForm.setPushMsgManagementHomeDataGridList(pushMsgManagementHomeDataGrids);

        PushMsgManagementHomeRs pushMsgManagementHomeRs = new PushMsgManagementHomeRs();
        pushMsgManagementHomeRs.setData(pushMsgManagementHomeViewForm);

        return pushMsgManagementHomeRs;
    }

    @Override
    public PushMsgManagementQueryRs queryPushMsgData(PushMsgManagementQueryRq pushMsgManagementQueryRq) {
        KgoStatusMsg kgoStatusMsg = kgoStatusMsgRepository.findByOrganIdAndStatus(pushMsgManagementQueryRq.getOrganId(), pushMsgManagementQueryRq.getStatus());

        PushMsgManagementQueryViewForm pushMsgManagementQueryViewForm = new PushMsgManagementQueryViewForm();

        PushMsgManagementQueryRs pushMsgManagementQueryRs = new PushMsgManagementQueryRs();

        pushMsgManagementQueryViewForm.setUserId(pushMsgManagementQueryRq.getUserId());
        pushMsgManagementQueryViewForm.setOrganId(pushMsgManagementQueryRq.getOrganId());
        pushMsgManagementQueryViewForm.setStatus(pushMsgManagementQueryRq.getStatus());

        if (kgoStatusMsg != null) {
            pushMsgManagementQueryViewForm.setIsEnable(kgoStatusMsg.getIsEnable());
            pushMsgManagementQueryViewForm.setMsgTitle(kgoStatusMsg.getMsgTitle());
            pushMsgManagementQueryViewForm.setMsgBody(kgoStatusMsg.getMsgBody());
            pushMsgManagementQueryViewForm.setClickDetail(kgoStatusMsg.getClickDetail());
            pushMsgManagementQueryViewForm.setIsDefault(kgoStatusMsg.getIsDefault());
            pushMsgManagementQueryViewForm.setCaseStatusName(kgoStatusMsg.getCaseStatusName());
        } else {
            pushMsgManagementQueryViewForm.setIsEnable(String.valueOf(Boolean.TRUE));

            if (PushCaseStatusEnum.isLegalStatus(pushMsgManagementQueryRq.getStatus())) {
                pushMsgManagementQueryViewForm.setMsgTitle(PushCaseStatusEnum.getDefaultMsgTitleByValue(pushMsgManagementQueryRq.getStatus()));
                pushMsgManagementQueryViewForm.setMsgBody(PushCaseStatusEnum.getDefaultMsgBodyByValue(pushMsgManagementQueryRq.getStatus()));
                pushMsgManagementQueryViewForm.setClickDetail(PushCaseStatusEnum.getDefaultClickDetailByValue(pushMsgManagementQueryRq.getStatus()));
                pushMsgManagementQueryViewForm.setIsDefault(String.valueOf(Boolean.TRUE));
            }
            else
                pushMsgManagementQueryViewForm.setIsDefault(String.valueOf(Boolean.FALSE));
        }

        pushMsgManagementQueryRs.setData(pushMsgManagementQueryViewForm);

        return pushMsgManagementQueryRs;
    }

    @Override
    public PushMsgManagementEditRs editPushMsgData(PushMsgManagementEditRq pushMsgManagementEditRq) {
        String organId =  pushMsgManagementEditRq.getOrganId();
        String status = pushMsgManagementEditRq.getStatus();

//        if (!PushCaseStatusEnum.isLegalStatus(status))
//            throw new KgoApiException("Is not legal status = " + status);

        KgoUser kgoUser = kgoUserRepository.findByUserId(pushMsgManagementEditRq.getUserId());

        if (kgoUser == null)
            throw new KgoApiException("Can't find user id");

        KgoStatusMsg kgoStatusMsg = kgoStatusMsgRepository.findByOrganIdAndStatus(pushMsgManagementEditRq.getOrganId(), pushMsgManagementEditRq.getStatus());

        if (kgoStatusMsg == null) {
            LOGGER.info("Do add action, organId is = {}, status is = {}", organId, status);

            KgoStatusMsg kgoStatusMsgWithAdd = new KgoStatusMsg();
            kgoStatusMsgWithAdd.setOrganId(organId);
            kgoStatusMsgWithAdd.setStatus(status);
            kgoStatusMsgWithAdd.setCaseStatusName(pushMsgManagementEditRq.getCaseStatusName());
            kgoStatusMsgWithAdd.setMsgTitle(pushMsgManagementEditRq.getMsgTitle());
            kgoStatusMsgWithAdd.setMsgBody(pushMsgManagementEditRq.getMsgBody());
            kgoStatusMsgWithAdd.setClickDetail(pushMsgManagementEditRq.getClickDetail());
            kgoStatusMsgWithAdd.setIsEnable(pushMsgManagementEditRq.getIsEnable());
            kgoStatusMsgWithAdd.setCreateTime(DateUtil.getCurrentTimestamp());
            kgoStatusMsgWithAdd.setCreateUser(pushMsgManagementEditRq.getUserId());

            if (PushCaseStatusEnum.isLegalStatus(status)) {
                kgoStatusMsgWithAdd.setIsDefault(String.valueOf(Boolean.TRUE));
                kgoStatusMsgWithAdd.setCaseStatusName(PushCaseStatusEnum.getLabelByValue(status));
            }
            else {
                kgoStatusMsgWithAdd.setIsDefault(String.valueOf(Boolean.FALSE));
            }

            kgoStatusMsgRepository.save(kgoStatusMsgWithAdd);
        } else {
            LOGGER.info("Do edit action, organId is = {}, status is = {}", organId, status);

            kgoStatusMsg.setCaseStatusName(pushMsgManagementEditRq.getCaseStatusName());
            kgoStatusMsg.setMsgTitle(pushMsgManagementEditRq.getMsgTitle());
            kgoStatusMsg.setMsgBody(pushMsgManagementEditRq.getMsgBody());
            kgoStatusMsg.setClickDetail(pushMsgManagementEditRq.getClickDetail());
            kgoStatusMsg.setIsEnable(pushMsgManagementEditRq.getIsEnable());
            kgoStatusMsg.setUpdateTime(DateUtil.getCurrentTimestamp());
            kgoStatusMsg.setUpdateUser(pushMsgManagementEditRq.getUserId());
            kgoStatusMsg.setIsDefault(kgoStatusMsg.getIsDefault());

            kgoStatusMsgRepository.save(kgoStatusMsg);
        }

        return new PushMsgManagementEditRs();
    }

    @Override
    public PushMsgManagementDeleteRs deletePushMsgData(PushMsgManagementDeleteRq pushMsgManagementDeleteRq) {
        String organId = pushMsgManagementDeleteRq.getOrganId();
        String status = pushMsgManagementDeleteRq.getStatus();

        KgoStatusMsg kgoStatusMsg = kgoStatusMsgRepository.findByOrganIdAndStatus(organId, status);

        if (kgoStatusMsg == null)
            throw new KgoApiException("Can't find msg, organId is = " + organId + ", status is = " + status);

        kgoStatusMsgRepository.delete(kgoStatusMsg);

        return new PushMsgManagementDeleteRs();
    }

}
