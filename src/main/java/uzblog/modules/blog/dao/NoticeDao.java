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
import uzblog.modules.blog.entity.Notice;

import java.util.List;

public interface NoticeDao extends JpaRepository<Notice, Long> {
	List<Notice> findAll();
}
