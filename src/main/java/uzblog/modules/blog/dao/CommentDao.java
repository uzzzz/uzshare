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

import uzblog.modules.blog.entity.Comment;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 
 *
 */
public interface CommentDao extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {
	Page<Comment> findAll(Pageable pageable);
	Page<Comment> findAllByToIdOrderByCreatedDesc(Pageable pageable, long toId);
	Page<Comment> findAllByAuthorIdOrderByCreatedDesc(Pageable pageable, long authorId);
	List<Comment> findByIdIn(Set<Long> ids);
	List<Comment> findAllByAuthorIdAndToIdOrderByCreatedDesc(long authorId, long toId);

	int deleteAllByIdIn(Collection<Long> ids);
}
