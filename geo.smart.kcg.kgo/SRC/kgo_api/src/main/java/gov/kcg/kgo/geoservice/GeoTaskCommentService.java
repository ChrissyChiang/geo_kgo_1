package gov.kcg.kgo.geoservice;

import gov.kcg.kgo.common.backend.BackendLoginUserInfo;
import gov.kcg.kgo.enums.common.ComboBoxStatusEnum;
import gov.kcg.kgo.enums.common.SuccessMsg;
import gov.kcg.kgo.enums.error.KgoBackEndApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.geoentity.GeoKgoAgent;
import gov.kcg.kgo.geoentity.GeoKgoTaskComment;
import gov.kcg.kgo.geoenum.GeoBooleanType;
import gov.kcg.kgo.geomodel.GeoKgoAgentListModel;
import gov.kcg.kgo.geomodel.GeoKgoAgentModel;
import gov.kcg.kgo.geomodel.GeoKgoUserInfoModel;
import gov.kcg.kgo.georepository.GeoKgoAgentRepository;
import gov.kcg.kgo.georepository.GeoKgoTaskCommentRepository;
import gov.kcg.kgo.georepository.custom.GeoAgentReposCustom;
import gov.kcg.kgo.geoutil.GeoStringUtil;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoAgentDeleteRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoAgentInsertRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoAgentQueryRq;
import gov.kcg.kgo.geoviewmodel.backend.rq.GeoAgentUserInfoQueryRq;
import gov.kcg.kgo.geoviewmodel.backend.rs.*;
import gov.kcg.kgo.geoviewmodel.backend.rs.bean.*;
import gov.kcg.kgo.service.impl.helper.OrganUnitManagementServiceHelper;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.KgoUtil;
import gov.kcg.kgo.viewModel.compoent.comboBox.ComboBox;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * GEO 20211101 add
 * 後台-簽核意見 API Service.
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class GeoTaskCommentService extends GeoBaseService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoTaskCommentService.class);

    @Autowired
    private GeoKgoTaskCommentRepository geoKgoTaskCommentRepository;

    /**
     * 儲存簽核意見
     */
    public GeoKgoTaskComment saveTaskComment(String commentId, String commentText, String editOrgan, String editUser) {
        LOGGER.info("saveTaskComment commentId = " + commentId);
        GeoKgoTaskComment taskCommentEntity = new GeoKgoTaskComment();
        taskCommentEntity.setCommentId(commentId);
        taskCommentEntity.setCommentText(commentText);
        taskCommentEntity.setEditOrgan(editOrgan);
        taskCommentEntity.setEditUser(editUser);
        taskCommentEntity.setEditTime(DateUtil.getCurrentTimestamp());
        return geoKgoTaskCommentRepository.save(taskCommentEntity);
    } //saveTaskComment
}
