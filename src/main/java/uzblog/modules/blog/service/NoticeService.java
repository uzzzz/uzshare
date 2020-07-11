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

import uzblog.modules.blog.entity.Channel;
import uzblog.modules.blog.entity.Notice;

import java.util.List;

public interface NoticeService {

	List<Notice> findAll();

	Notice findById(long id);

	void update(Notice notice);

	void delete(long id);

}
