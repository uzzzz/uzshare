package uzblog.modules.user.service;

import java.util.List;

import uzblog.modules.user.entity.Secret;

public interface SecretService {

	Secret save(Secret secret);

	List<Secret> secrets(long userId);

	void delete(long id);

	public Secret findByIdAndUserId(long id, long userId);

	boolean existsByIdAndUserId(long id, long userId);

	boolean existsByUserIdAndQuestion(long userId, String question);

	Secret randomByUserId(long userId);
}
