/*
+--------------------------------------------------------------------------
|   
|   ========================================
|    
|   
|
+---------------------------------------------------------------------------
*/
package uzblog.modules.blog.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import uzblog.modules.blog.dao.custom.FeedsDaoCustom;
import uzblog.modules.blog.data.FeedsVO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.MessageFormat;

/**
 * 
 *
 */
public class FeedsDaoImpl implements FeedsDaoCustom {
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;

	String pattern = "INSERT INTO mto_feeds (own_id, type, post_id, author_id, created) SELECT user_id, {0}, {1,number,###}, {2,number,###}, now() FROM mto_follows WHERE follow_id = {3,number,###}";

	@Override
	@Transactional
	public int batchAdd(FeedsVO feeds) {
		String sql = MessageFormat.format(pattern, feeds.getType(), feeds.getPostId(), feeds.getAuthorId(), feeds.getAuthorId());
		return entityManager.createNativeQuery(sql).executeUpdate();
	}

}
