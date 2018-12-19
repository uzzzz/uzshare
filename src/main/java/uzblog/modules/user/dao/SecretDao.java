package uzblog.modules.user.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import uzblog.modules.user.entity.Secret;

public interface SecretDao extends JpaRepository<Secret, Long> {

	List<Secret> findByUserIdAndDeleted(long userId, int deleted);

	@Modifying
	@Query("update Secret s set s.deleted = 1 where s.id in :ids")
	public void deleteByIds(@Param(value = "ids") long... ids);
}
