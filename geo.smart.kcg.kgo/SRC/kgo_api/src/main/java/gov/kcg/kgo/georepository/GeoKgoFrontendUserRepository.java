package gov.kcg.kgo.georepository; 

import org.springframework.data.jpa.repository.JpaRepository; 
import javax.transaction.Transactional; 
import gov.kcg.kgo.geoentity.GeoKgoFrontendUser;
import java.util.List;

/** 
 * GEO 20211113 add 前台使用者註冊
 * Repository for 使用者帳號
 */
@Transactional
public interface GeoKgoFrontendUserRepository extends JpaRepository<GeoKgoFrontendUser, String> {
    List<GeoKgoFrontendUser> findByUserLoginTypeAndUserValidate(String userLoginType, String userValidate);
    GeoKgoFrontendUser findByUserLoginTypeAndUserValidateAndUserId(String userLoginType, String userValidate,String userId);

} 
