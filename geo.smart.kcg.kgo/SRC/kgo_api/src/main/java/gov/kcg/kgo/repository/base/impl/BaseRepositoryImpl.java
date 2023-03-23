package gov.kcg.kgo.repository.base.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import gov.kcg.kgo.repository.base.BaseRepository;

/**
 * JPA Repository 共同實作物件.
 */
public class BaseRepositoryImpl<T, D extends Serializable> extends SimpleJpaRepository<T, D> implements BaseRepository<T, D> {

	/** 查詢參數最大數. */
	private static final int QUERY_PARAMS_MAX_COUNT = 300;
	
	/** 儲存 最大筆數. */
	private static final int SAVE_MAX_COUNT = 2000;

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseRepositoryImpl.class);

	/** entity information. */
	private JpaEntityInformation<T, ?> entityInformation;

	private EntityManager entityManager;
	 
    public BaseRepositoryImpl(JpaEntityInformation<T, ?> 
      entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    /**
  	 * 大量查詢 findAll方法 
	 * 超出參數最大限制 改用批次查詢 (300).
	 *
     * @param ids the ids
     * @return the list
     */
    @Override
    public List<T> findAllByIdBatch(Iterable<D> ids) {
    	Iterator<D> iterator = ids.iterator();
    	List<D> queryIdlist = new ArrayList<D>();
		while (iterator.hasNext()) {
			queryIdlist.add(iterator.next());
		}
		// 超出參數最大限制 改用批次查詢
    	if(queryIdlist.size() > QUERY_PARAMS_MAX_COUNT) {
    		return findAllBatch(queryIdlist);
    	}
    	return super.findAllById(ids);
    }
    
	/**
	 * 大量查詢 findAll 方法.
	 *
	 * @param ids the ids
	 * @return the list
	 */
	private List<T> findAllBatch(List<D> queryIdlist) {
		List<List<D>> listGroup = groupData(queryIdlist, QUERY_PARAMS_MAX_COUNT);

		List<T> entitylist = new ArrayList<T>();
		for (List<D> queryIds : listGroup) {
			entitylist.addAll((Collection<? extends T>) super.findAllById(queryIds));
		}
		LOGGER.info("findAllBatch count : " + entitylist.size());
		return entitylist;
	}
    
    /**
         * 大量資料save 方法 (超過2000筆).
     *
     * @param <S> the generic type
     * @param entities the entities
     * @return the list
     */
    @Override
    public <S extends T> List<S> saveAllBatch(Iterable<S> entities) {
    	Iterator<S> iterator = entities.iterator();
    	List<S> entitylist = new ArrayList<S>();
		while (iterator.hasNext()) {
			entitylist.add(iterator.next());
		}
		// 超出參數最大限制 改用批次查詢
    	if(entitylist.size() > SAVE_MAX_COUNT) {
    		return saveAllBatch(entitylist);
    	}
    	return super.saveAll(entities);
    }

	/**
	 * 大量資料save 方法 (超過2000筆).
	 *
	 * @param ids the ids
	 * @return 
	 * @return the list
	 */
	private <S extends T> List<S> saveAllBatch(List<S> entitylist) {
		List<S> saveEntitylist = new ArrayList<>();
		List<List<S>> listGroup = groupData(entitylist, SAVE_MAX_COUNT);
		for (List<S> saveEntities : listGroup) {
			saveEntitylist.addAll((Collection<? extends S>) super.saveAll(saveEntities));
			
			LOGGER.info("saveAllBatch listGroup saveEntities count : " + saveEntities.size());
		}
		LOGGER.info("saveAllBatch count : " + saveEntitylist.size());
		return saveEntitylist;
	}

	/**
	 * 查詢條件拆分 by maxCount.
	 */
	private <S> List<List<S>> groupData(List<S> dataList, int maxCount) {

		List<List<S>> detailGroupList = new ArrayList<>();

		int size = dataList.size();

		int count = 0;
		List<S> groupDetails = null;

		for (int i = 0; i < size; i++) {

			if (count <= 0) {
				groupDetails = new ArrayList<>();
				detailGroupList.add(groupDetails);
			}

			groupDetails.add(dataList.get(i));

			count++;

			if (count >= maxCount) {
				count = 0;
			}
		}

		return detailGroupList;
	}

	public JpaEntityInformation<T, ?> getEntityInformation() {
		return entityInformation;
	}

	public void setEntityInformation(JpaEntityInformation<T, ?> entityInformation) {
		this.entityInformation = entityInformation;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
