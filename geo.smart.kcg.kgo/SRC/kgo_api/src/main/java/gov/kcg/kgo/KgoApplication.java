package gov.kcg.kgo;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import gov.kcg.kgo.geoentity.GeoKgoAppointmentColumn;
import gov.kcg.kgo.geoentity.GeoKgoAppointmentColumnPK;
import gov.kcg.kgo.model.*;
import gov.kcg.kgo.service.ActivitiService;
import gov.kcg.kgo.service.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Map;

@SpringBootApplication
@EnableEncryptableProperties
@EnableCaching
@EnableAsync
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true)
@PropertySource({
        "classpath:kgo.properties"
})
public class KgoApplication implements ApplicationRunner {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(KgoApplication.class);

    @Autowired
    private CacheService cacheService;

    @Autowired
    private ActivitiService activitiService;

    @Value("${deploy.flow}")
    private Boolean isDeployFlow;

    public static void main(String[] args) {
        SpringApplication.run(KgoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        /** ---- load to cache  -----*/
        LOGGER.info(">>>>> run KgoApplication ---- load to cache  -----");

        // 取得表單欄位資料Map Cache.
        Map<KgoCasesetColumnPK, KgoCasesetColumn> allCaseSetColumnMap = cacheService.getAllCaseSetColumn();
        LOGGER.info(">>>>> 取得表單欄位資料Map Cache.  getAllCaseSetColumn() to cache");

        // 取得預約表單欄位資料Map Cache.
        Map<GeoKgoAppointmentColumnPK, GeoKgoAppointmentColumn> allAppointmentColumnMap = cacheService.getAllAppointmentColumn();
        LOGGER.info(">>>>> 取得預約表單欄位資料Map Cache.  getAllCaseSetColumn() to cache");

        // 取得MyData表單欄位資料Map Cache.
        Map<KgoMydataColumnPK, KgoMydataColumn> allMyDataColumnMap = cacheService.getAllMyDataColumn();
        LOGGER.info(">>>>> 取得MyData表單欄位資料Map Cache.  getAllMyDataColumn() to cache");

        // 取得機關Map Cache.
        Map<String, KgoOrgan> allOrganMap = cacheService.getAllOrganMap();
        LOGGER.info(">>>>> 取得取得機關Map Cache.  getAllOrganMap() to cache");

        /** ---- load to cache  ----- */

        // Activiti 流程部屬
        if (!isDeployFlow) {
            activitiService.deployFlow();
            LOGGER.info(">>>>> Activiti 流程部屬 Success");
        }
    }


//	/** transaction 設置  但在config 裡已經有 不確定要不要在設定*/
//	@Bean(name="tm1") 
//	@Autowired
//	DataSourceTransactionManager tm1(@Qualifier ("firstDataSource") DataSource datasource) {
//	    DataSourceTransactionManager txm  = new DataSourceTransactionManager(datasource);
//	    return txm;
//	}
//
//	@Bean(name="tm2") 
//	@Autowired
//	DataSourceTransactionManager tm2(@Qualifier ("secondDataSource") DataSource datasource) {
//	    DataSourceTransactionManager txm  = new DataSourceTransactionManager(datasource);
//	    return txm;
//	}
}
