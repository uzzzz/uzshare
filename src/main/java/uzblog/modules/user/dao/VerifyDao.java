/*
+--------------------------------------------------------------------------
|   
|   ========================================
|    
|   
|
+---------------------------------------------------------------------------
*/
package uzblog.modules.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import uzblog.modules.user.entity.Verify;

/**
 * @author langhsu on 2015/8/14.
 */
public interface VerifyDao extends JpaRepository<Verify, Long>, JpaSpecificationExecutor<Verify> {
    Verify findByUserIdAndType(long userId, int type);
    Verify findByUserId(long userId);
}
