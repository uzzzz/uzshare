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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import uzblog.modules.blog.dao.custom.FeedsDaoCustom;
import uzblog.modules.blog.entity.Feeds;

/**
 * @author langhsu
 *
 */
public interface FeedsDao extends JpaRepository<Feeds, Long>, JpaSpecificationExecutor<Feeds>, FeedsDaoCustom {
	Page<Feeds> findAllByOwnIdOrderByIdDesc(Pageable pageable, long ownId);
	int deleteAllByOwnIdAndAuthorId(long ownId, long authorId);
	void deleteAllByPostId(long postId);
}
