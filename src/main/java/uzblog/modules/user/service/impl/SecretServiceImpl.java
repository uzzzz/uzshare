
package uzblog.modules.user.service.impl;

import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

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
	public Secret save(Secret secret) {
		return secretDao.save(secret);
	}

	@Override
	public List<Secret> secrets(long userId) {
		return secretDao.findByUserIdAndDeleted(userId, 0);
	}

	@Override
	@Transactional
	public void delete(long id) {
		secretDao.deleteByIds(id);
	}

	@Override
	public boolean existsByIdAndUserId(long id, long userId) {
		return secretDao.existsByIdAndUserId(id, userId);
	}

	@Override
	public boolean existsByUserIdAndQuestion(long userId, String question) {
		return secretDao.existsByUserIdAndQuestion(userId, question);
	}

	@Override
	public Secret randomByUserId(long userId) {
		List<Secret> secrets = secretDao.findByUserIdAndDeleted(userId, 0);
		if (secrets.size() == 0) {
			return null;
		}
		return secrets.get(new Random().nextInt(secrets.size()));
	}

	@Override
	public Secret findByIdAndUserId(long id, long userId) {
		return secretDao.findByIdAndUserId(id, userId);
	}
}
