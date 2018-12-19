package uzblog.modules.user.service;

import java.util.List;

import uzblog.modules.user.entity.Secret;

public interface SecretService {

	List<Secret> secrets(long userId);

	void delete(long id);
}
