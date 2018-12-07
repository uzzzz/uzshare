/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package uzblog.modules.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import uzblog.modules.blog.entity.Config;

/**
 * @author langhsu
 *
 */
public interface ConfigDao extends JpaRepository<Config, Long>, JpaSpecificationExecutor<Config> {
	Config findByKey(String key);
}
