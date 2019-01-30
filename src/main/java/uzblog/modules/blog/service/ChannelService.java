/*
+--------------------------------------------------------------------------
|   
|   ========================================
|    
|   
|
+---------------------------------------------------------------------------
*/
package uzblog.modules.blog.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import uzblog.modules.blog.entity.Channel;

/**
 * 栏目管理
 * 
 * 
 *
 */
public interface ChannelService {
	List<Channel> findAll(int status);
	Map<Integer, Channel> findMapByIds(Collection<Integer> ids);
	Channel getById(int id);
	void update(Channel channel);
	void delete(int id);
}
