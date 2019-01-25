package uzblog.modules.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import uzblog.modules.blog.entity.Referer;

public interface RefererDao extends JpaRepository<Referer, Long> {

	Referer findByHost(String host);

}
