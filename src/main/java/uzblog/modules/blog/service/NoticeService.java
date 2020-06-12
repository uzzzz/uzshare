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

import uzblog.modules.blog.entity.Notice;

import java.util.List;

public interface NoticeService {
	List<Notice> findAll();
}
