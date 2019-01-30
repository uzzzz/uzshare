/*
+--------------------------------------------------------------------------
|   
|   ========================================
|    
|   
|
+---------------------------------------------------------------------------
*/
package uzblog.modules.user.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import uzblog.modules.user.entity.Follow;
import uzblog.modules.user.entity.User;

import java.util.List;

/**
 * 
 * 
 */
public interface FollowDao extends JpaRepository<Follow, Long>, JpaSpecificationExecutor<Follow> {

	/**
	 * 检查是否已关注
	 * 
	 * @param user
	 * @param follow
	 * @return
	 */
	Follow findByUserAndFollow(User user, User follow);

	/**
	 * 查询我的关注
	 *
	 * @param pageable
	 * @param user
	 */
	Page<Follow> findAllByUser(Pageable pageable, User user);

	/**
	 * 查询关注我的 (我的粉丝)
	 * 
	 * @param pageable
	 * @param follow
	 */
	Page<Follow> findAllByFollow(Pageable pageable, User follow);

	/**
	 * 检查是否相互关注
	 * 
	 * @param userId
	 * @param followId
	 * @return
	 */
	@Query("from Follow f1 where f1.user.id = :userId and f1.follow.id = :followId and f1.user.id in (select f2.follow.id from Follow f2 where f2.user.id = :followId)")
	List<Follow> findAllCrossFollow(@Param("userId") long userId, @Param("followId") long followId);
	
	/**
	 * 取消关注
	 * 
	 * @param user
	 * @param follow
	 * @return
	 */
	int deleteByUserAndFollow(User user, User follow);
}
