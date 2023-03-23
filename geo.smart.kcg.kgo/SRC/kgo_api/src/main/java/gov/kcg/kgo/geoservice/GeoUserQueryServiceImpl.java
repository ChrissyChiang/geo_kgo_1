package gov.kcg.kgo.geoservice;

import gov.kcg.kgo.exception.KgoApiException;
import gov.kcg.kgo.geomodel.GeoUserQueryModel;
import gov.kcg.kgo.geoviewmodel.frontend.rs.GeoUserQueryRs;
import gov.kcg.kgo.geoviewmodel.frontend.rs.bean.GeoUserQueryViewForm;
import gov.kcg.kgo.model.KgoUnit;
import gov.kcg.kgo.model.KgoUser;
import gov.kcg.kgo.repository.KgoOrganRepository;
import gov.kcg.kgo.repository.KgoUnitRepository;
import gov.kcg.kgo.repository.KgoUserRepository;
import gov.kcg.kgo.service.impl.KgoBaseServiceImpl;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GeoUserQueryServiceImpl extends KgoBaseServiceImpl implements GeoUserQueryService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoUserQueryServiceImpl.class);

    @Autowired
    KgoUserRepository kgoUserRepository;
    @Autowired
    KgoOrganRepository kgoOrganRepository;
    @Autowired
    KgoUnitRepository kgoUnitRepository;

    /**
     * GEO 20221014 add_Jim
     * 查詢使用者資訊
     *
     * @param organ
     * @param unit
     * @return
     */
    @Override
    public GeoUserQueryRs queryUsers(String organ, String unit, String account) {
        GeoUserQueryRs rs = new GeoUserQueryRs();
        GeoUserQueryViewForm viewForm = new GeoUserQueryViewForm();
        rs.setData(viewForm);
        try {
            List<GeoUserQueryModel> result = new ArrayList<>();
            List<KgoUser> users = kgoUserRepository.findByOrgan(organ);
            if (Strings.isNotBlank(unit))
                users = users.stream().filter(e -> unit.equalsIgnoreCase(e.getUnit())).collect(Collectors.toList());

            if (Strings.isNotBlank(account))
                users = users.stream().filter(e -> e.getUserId().contains(account)).collect(Collectors.toList());

            for (KgoUser user : users) {
                GeoUserQueryModel geoUserQueryModel = new GeoUserQueryModel();
                String organName = kgoOrganRepository.findByOrganId(user.getOrgan()).getOrganName();
                geoUserQueryModel.setOrganName(organName);
                KgoUnit kgoUnit = kgoUnitRepository.findByIdOrganIdAndIdUnitId(user.getOrgan(), user.getUnit());
                geoUserQueryModel.setUnitName(kgoUnit != null ? kgoUnit.getUnitName() : Strings.EMPTY);
                geoUserQueryModel.setUserId(user.getUserId());
                geoUserQueryModel.setUserName(user.getName());
                geoUserQueryModel.setAvailableCrossReview(user.getAvailableCrossReview());
                result.add(geoUserQueryModel);
            }
            viewForm.setUserList(result);
        } catch (KgoApiException apiE) {
            LOGGER.error(apiE.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return rs;
    }
}