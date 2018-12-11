/*
+--------------------------------------------------------------------------
|   
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   
|
+---------------------------------------------------------------------------
*/
package uzblog.base.upload.impl;

import org.springframework.stereotype.Service;

/**
 * @author langhsu
 *
 */
@Service
public class FileRepoImpl extends AbstractFileRepo {
	@Override
	public String getRoot() {
		return appContext.getRoot();
	}
}
