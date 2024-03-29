/*
+--------------------------------------------------------------------------
|   
|   ========================================
|    
|   
|
+---------------------------------------------------------------------------
*/
package uzblog.modules.blog.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uzblog.modules.blog.dao.custom.PostDaoCustom;
import uzblog.modules.blog.entity.Post;

/**
 * 
 *
 */
public interface PostDao extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post>, PostDaoCustom {
	/**
	 * 查询指定用户
	 * 
	 * @param pageable
	 * @param authorId
	 * @return
	 */
	Page<Post> findAllByAuthorIdOrderByCreatedDesc(Pageable pageable, long authorId);

	List<Post> findAllByAuthorId(long authorId);

	// findLatests
	List<Post> findTop10ByOrderByCreatedDesc();

	// findHots
	List<Post> findTop10ByOrderByViewsDesc();

	List<Post> findAllByIdIn(Collection<Long> id);

	List<Post> findTop5ByFeaturedGreaterThanOrderByCreatedDesc(int featured);

	@Query("select coalesce(max(weight), 0) from Post")
	int maxWeight();

	@Modifying
	@Transactional
	@Query("update Post set views = views + :increment where id = :id")
	void updateViews(@Param("id") long id, @Param("increment") int increment);

	@Modifying
	@Transactional
	@Query("update Post set favors = favors + :increment where id = :id")
	void updateFavors(@Param("id") long id, @Param("increment") int increment);

	@Modifying
	@Transactional
	@Query("update Post set comments = comments + :increment where id = :id")
	void updateComments(@Param("id") long id, @Param("increment") int increment);

	@Query("select id from Post")
	List<Long> findAllIds();

	boolean existsByTitle(String title);
}
