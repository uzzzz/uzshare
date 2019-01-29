package uzblog.modules.blog.dao.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import uzblog.modules.blog.data.PostVO;

/**
 *   on 2017/9/30.
 */
public interface PostDaoCustom {
    Page<PostVO> search(Pageable pageable, String q) throws Exception;
    Page<PostVO> searchByTag(Pageable pageable, String tag);
    void resetIndexs();
}
