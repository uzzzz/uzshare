/*
+--------------------------------------------------------------------------
|   
|   ========================================
|    
|   
|
+---------------------------------------------------------------------------
*/
package uzblog.modules.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uzblog.modules.blog.dao.NoticeDao;
import uzblog.modules.blog.entity.Notice;
import uzblog.modules.blog.service.NoticeService;

import java.util.List;

/**
 * 
 *
 */
@Service
@Transactional
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeDao noticeDao;

	@Override
	public List<Notice> findAll() {
		List<Notice> list = noticeDao.findAll();
		return list;
	}
}
