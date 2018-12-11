/*
+--------------------------------------------------------------------------
|   
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   
|
+---------------------------------------------------------------------------
*/
package uzblog.modules.blog.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uzblog.modules.blog.dao.ConfigDao;
import uzblog.modules.blog.entity.Config;
import uzblog.modules.blog.service.ConfigService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author langhsu
 *
 */
@Service
public class ConfigServiceImpl implements ConfigService {
	@Autowired
	private ConfigDao configDao;

	@Override
	@Transactional(readOnly = true)
	public List<Config> findAll() {
		List<Config> list = configDao.findAll();
		List<Config> rets = new ArrayList<>();
		
		for (Config po : list) {
			Config r = new Config();
			BeanUtils.copyProperties(po, r);
			rets.add(r);
		}
		return rets;
	}

	@Override
	@Transactional
	public void update(List<Config> settings) {
		if (settings == null) {
			return;
		}
		
		for (Config st :  settings) {
			Config entity = configDao.findByKey(st.getKey());

			// 修改
			if (entity != null) {
				entity.setValue(st.getValue());
			}
			// 添加
			else {
				entity = new Config();
				BeanUtils.copyProperties(st, entity);
			}
			configDao.save(entity);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, Config> findAll2Map() {
		List<Config> list = findAll();
		Map<String, Config> ret = new LinkedHashMap<String, Config>();
		
		for (Config c : list) {
			ret.put(c.getKey(), c);
		}
		return ret;
	}

	public String findConfigValueByName(String key) {
		Config entity = configDao.findByKey(key);
		if (entity != null) {
			return entity.getValue();
		}
		return null;
	}
	
}
