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

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uzblog.modules.blog.dao.NoticeDao;
import uzblog.modules.blog.entity.Channel;
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

	@Override
	public Notice findById(long id) {
		return noticeDao.getOne(id);
	}

	@Override
	@Transactional
	public void update(Notice notice) {
		Notice po = noticeDao.getOne(notice.getId());
		if (po != null && po.getId() > 0) {
			BeanUtils.copyProperties(notice, po);
		} else {
			po = new Notice();
			BeanUtils.copyProperties(notice, po);
		}
		noticeDao.save(po);
	}

	@Override
	@Transactional
	public void delete(long id) {
		noticeDao.deleteById(id);
	}
}
