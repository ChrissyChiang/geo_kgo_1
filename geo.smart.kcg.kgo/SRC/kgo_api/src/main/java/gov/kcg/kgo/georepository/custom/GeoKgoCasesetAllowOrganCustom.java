package gov.kcg.kgo.georepository.custom; 

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Query;

import gov.kcg.kgo.service.impl.ClassManagementServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import gov.kcg.kgo.geoentity.GeoKgoCasesetAllowOrgan;
import gov.kcg.kgo.model.KgoOrgan; 

/** 
 * 某服務允許特定機關進入
 */
@Repository
public class GeoKgoCasesetAllowOrganCustom extends  GeoBaseReposCustom {


    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoKgoCasesetAllowOrganCustom.class);

    public List<GeoKgoCasesetAllowOrgan> findAllowOrganByCasesetId(String casesetId, String myOrgan ,Boolean belongKgo){
        LOGGER.info("findAllowOrganByCasesetId..start");

        String sqlStr =  "select a.caseset_allow_organ_id as casesetAllowOrganId, a.caseset_id as casesetId, a.organ_id as organId, b.OrganName as organName" + 
                " from GEO_KGO_CASESET_ALLOW_ORGAN a inner join KGO_ORGAN b on a.organ_id = b.OrganId where a.caseset_id = :casesetId ";
        boolean byOrganId = false;
		if (StringUtils.isNotBlank(myOrgan)){
			if(!myOrgan.equals("397120000J") && !myOrgan.equals("397220100A")) {
				sqlStr += " and b.OrganId = :myOrgan ";
                byOrganId = true;
			}	
		}        
		LOGGER.info("下拉機關myOrgan:"+myOrgan);
		LOGGER.info("下拉機關:"+sqlStr);
        
        Query query = getEntityManager().createNativeQuery(sqlStr);
        query.setParameter("casesetId", casesetId);
        if (byOrganId || !belongKgo) {
            query.setParameter("myOrgan", myOrgan);
        }        
        @SuppressWarnings("rawtypes")
		List listData = query.getResultList();
        List<GeoKgoCasesetAllowOrgan> datas = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < listData.size(); i++) {
        	GeoKgoCasesetAllowOrgan dto = new GeoKgoCasesetAllowOrgan();
            Object[] recordArray = (Object[]) listData.get(i);
            dto.setCasesetAllowOrganId(((BigInteger)recordArray[0]).longValue());
            dto.setCasesetId((String) recordArray[1]);
            dto.setOrganId((String) recordArray[2]);
            dto.setOrganName((String) recordArray[3]);
            LOGGER.info("dto="+dto);
            datas.add(dto);
        }
        LOGGER.info("findAllowOrganByCasesetId..datas.size()="+datas.size());
        return datas;
    }    
} 
