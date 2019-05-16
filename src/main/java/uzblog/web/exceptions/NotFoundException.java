/*
+--------------------------------------------------------------------------
|   
|   ========================================
|    
|   
|
+---------------------------------------------------------------------------
*/
package uzblog.web.exceptions;

import org.springframework.stereotype.Component;

/**
 * 404 处理
 */
@Component
public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = -4723587494862436996L;

}
