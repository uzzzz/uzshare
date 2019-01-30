/*
+--------------------------------------------------------------------------
|   
|   ========================================
|    
|   
|
+---------------------------------------------------------------------------
*/
package uzblog.base.upload.impl;

import org.springframework.stereotype.Service;

/**
 * 
 *
 */
@Service
public class FileRepoImpl extends AbstractFileRepo {
	@Override
	public String getRoot() {
		return appContext.getRoot();
	}
}
