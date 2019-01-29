package uzblog.modules.blog.dao.custom;

import uzblog.modules.blog.data.FeedsVO;

/**
 *   on 2017/9/30.
 */
public interface FeedsDaoCustom {
    /**
     * 添加动态, 同时会分发给粉丝
     *
     * @param feeds
     * @return
     */
    int batchAdd(FeedsVO feeds);
}
