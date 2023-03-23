package gov.kcg.kgo.repository.base;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * .
 * <p>
 * JPA BaseRepository 共同物件介面.
 */
@NoRepositoryBean
public interface BaseRepository<T, D extends Serializable> extends JpaRepository<T, D> {
	
	/**
	 * 大量查詢 findAll方法 
	 * 超出參數最大限制 改用批次查詢 (300).
	 *
	 * @param ids the ids
	 * @return the list
	 */
	List<T> findAllByIdBatch(Iterable<D> ids);
	
	/**
	 * 大量 save 方法 批次處理(超過2000筆).
	 *
	 * @param ids the ids
	 * @return the list
	 */
	<S extends T> List<S> saveAllBatch(Iterable<S> entities);
}
