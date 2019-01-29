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

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import uzblog.modules.blog.entity.Channel;

import java.util.Collection;
import java.util.List;

/**
 * @author langhsu
 *
 */
public interface ChannelDao extends JpaRepository<Channel, Integer>, JpaSpecificationExecutor<Channel> {
	List<Channel> findAllByStatus(int status);
	List<Channel> findAllByIdIn(Collection<Integer> id);
	Channel findByKey(String key);
}
