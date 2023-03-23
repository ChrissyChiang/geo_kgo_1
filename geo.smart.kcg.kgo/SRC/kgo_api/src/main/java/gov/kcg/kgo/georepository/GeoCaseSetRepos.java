package gov.kcg.kgo.georepository; 

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import gov.kcg.kgo.model.KgoCaseset; 


public interface GeoCaseSetRepos extends JpaRepository<KgoCaseset, Long> {
	
	public List<KgoCaseset> findByCasesetCategoryAndStatus(String casesetCategory,String status);
	
} 
