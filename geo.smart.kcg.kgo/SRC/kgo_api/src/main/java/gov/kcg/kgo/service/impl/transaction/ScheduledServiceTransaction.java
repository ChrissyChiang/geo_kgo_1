package gov.kcg.kgo.service.impl.transaction;

import java.util.List;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import gov.kcg.kgo.model.KgoOrgan;
import gov.kcg.kgo.model.KgoUnit;
import gov.kcg.kgo.model.KgoUser;
import gov.kcg.kgo.repository.KgoOrganRepository;
import gov.kcg.kgo.repository.KgoUnitRepository;
import gov.kcg.kgo.repository.KgoUserRepository;
import gov.kcg.kgo.util.SpringUtil;

@Named
public class ScheduledServiceTransaction {

    /**
     * 儲存機關資料.
     *
     */
    @Transactional(rollbackFor = {Exception.class})
    public void saveOrganList(List<KgoOrgan> saveOrganList) {
    	KgoOrganRepository kgoOrganRepository = SpringUtil.getDao(KgoOrganRepository.class);
        // 機關大量 save 方法.
        kgoOrganRepository.saveAll(saveOrganList);
    }
    
    /**
     * 儲存單位資料.
     *
     */
    @Transactional(rollbackFor = {Exception.class})
    public void saveUnitList(List<KgoUnit> saveUnitList) {
    	KgoUnitRepository kgoUnitRepository = SpringUtil.getDao(KgoUnitRepository.class);
    	 // 單位大量 save 方法.
        kgoUnitRepository.saveAllBatch(saveUnitList);
    }
    
//    /**
//     * 儲存員工資料.
//     *
//     */
//    @Transactional(rollbackFor = {Exception.class})
//    public void saveKgoUserList(List<KgoUser> list) {
//    	KgoUserRepository kgoUserRepository = SpringUtil.getDao(KgoUserRepository.class);
//    	 // 單位大量 save 方法.
//    	kgoUserRepository.saveAllBatch(list);
//    }
}
