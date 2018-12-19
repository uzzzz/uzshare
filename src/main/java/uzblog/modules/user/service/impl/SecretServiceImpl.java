
package uzblog.modules.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uzblog.modules.user.dao.SecretDao;
import uzblog.modules.user.entity.Secret;
import uzblog.modules.user.service.SecretService;

@Service
public class SecretServiceImpl implements SecretService {

	@Autowired
	private SecretDao secretDao;

	@Override
	public List<Secret> secrets(long userId) {
		return secretDao.findByUserIdAndDeleted(userId, 0);
	}

	@Override
	public void delete(long id) {
		secretDao.deleteByIds(id);
	}
}
