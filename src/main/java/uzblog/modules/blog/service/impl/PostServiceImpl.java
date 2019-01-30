/*
+--------------------------------------------------------------------------
|   
|   ========================================
|    
|   
|
+---------------------------------------------------------------------------
*/
package uzblog.modules.blog.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uzblog.base.lang.Consts;
import uzblog.modules.blog.dao.PostDao;
import uzblog.modules.blog.data.PostVO;
import uzblog.modules.blog.entity.Channel;
import uzblog.modules.blog.entity.Post;
import uzblog.modules.blog.service.ChannelService;
import uzblog.modules.blog.service.FavorService;
import uzblog.modules.blog.service.PostService;
import uzblog.modules.user.data.UserVO;
import uzblog.modules.user.service.UserService;
import uzblog.modules.utils.BeanMapUtils;

/**
 * 
 *
 */
@Service
@Transactional
@CacheConfig(cacheNames = "posts_caches")
public class PostServiceImpl implements PostService {

	@Autowired
	private PostDao postDao;

	@Autowired
	private UserService userService;

	@Autowired
	private FavorService favorService;

	@Autowired
	private ChannelService channelService;

	@Override
	@Cacheable
	public Page<PostVO> paging(Pageable pageable, int channelId, Set<Integer> excludeChannelIds, String ord) {
		Page<Post> page = postDao.findAll((root, query, builder) -> {

			List<Order> orders = new ArrayList<>();

			if (Consts.order.FAVOR.equals(ord)) {
				orders.add(builder.desc(root.<Long>get("favors")));
			} else if (Consts.order.HOTTEST.equals(ord)) {
				orders.add(builder.desc(root.<Long>get("comments")));
			} else {
				orders.add(builder.desc(root.<Long>get("weight")));
			}
			orders.add(builder.desc(root.<Long>get("created")));

			Predicate predicate = builder.conjunction();

			if (channelId > Consts.ZERO) {
				predicate.getExpressions().add(builder.equal(root.get("channelId").as(Integer.class), channelId));
			}

			if (null != excludeChannelIds && !excludeChannelIds.isEmpty()) {
				predicate.getExpressions().add(builder.not(root.get("channelId").in(excludeChannelIds)));
			}

			if (Consts.order.HOTTEST.equals(ord)) {
				orders.add(builder.desc(root.<Long>get("views")));
			}

			// predicate.getExpressions().add(
			// builder.equal(root.get("featured").as(Integer.class),
			// Consts.FEATURED_DEFAULT));

			query.orderBy(orders);

			return predicate;
		}, pageable);

		return new PageImpl<>(toPosts(page.getContent()), pageable, page.getTotalElements());
	}

	@Override
	public Page<PostVO> paging4Admin(Pageable pageable, long id, String title, int channelId) {
		Page<Post> page = postDao.findAll((root, query, builder) -> {
			query.orderBy(builder.desc(root.<Long>get("weight")), builder.desc(root.<Long>get("created")));

			Predicate predicate = builder.conjunction();

			if (channelId > Consts.ZERO) {
				predicate.getExpressions().add(builder.equal(root.get("channelId").as(Integer.class), channelId));
			}

			if (StringUtils.isNotBlank(title)) {
				predicate.getExpressions().add(builder.like(root.get("title").as(String.class), "%" + title + "%"));
			}

			if (id > Consts.ZERO) {
				predicate.getExpressions().add(builder.equal(root.get("id").as(Integer.class), id));
			}

			return predicate;
		}, pageable);

		return new PageImpl<>(toPosts(page.getContent()), pageable, page.getTotalElements());
	}

	@Override
	@Cacheable
	public Page<PostVO> pagingByAuthorId(Pageable pageable, long userId) {
		Page<Post> page = postDao.findAllByAuthorIdOrderByCreatedDesc(pageable, userId);
		return new PageImpl<>(toPosts(page.getContent()), pageable, page.getTotalElements());
	}

	@Override
	@Cacheable
	public List<PostVO> findAllFeatured() {
		List<Post> list = postDao.findTop5ByFeaturedGreaterThanOrderByCreatedDesc(Consts.FEATURED_DEFAULT);
		return toPosts(list);
	}

	@Override
	public Page<PostVO> search(Pageable pageable, String q) throws Exception {
		Page<PostVO> page = postDao.search(pageable, q);

		HashSet<Long> ids = new HashSet<>();
		HashSet<Long> uids = new HashSet<>();

		for (Post po : page.getContent()) {
			ids.add(po.getId());
			uids.add(po.getAuthorId());
		}

		// 加载用户信息
		buildUsers(page.getContent(), uids);

		return page;
	}

	@Override
	public Page<PostVO> searchByTag(Pageable pageable, String tag) {
		Page<PostVO> page = postDao.searchByTag(pageable, tag);

		HashSet<Long> ids = new HashSet<>();
		HashSet<Long> uids = new HashSet<>();

		for (Post po : page.getContent()) {
			ids.add(po.getId());
			uids.add(po.getAuthorId());
		}

		// 加载用户信息
		buildUsers(page.getContent(), uids);
		return page;
	}

	@Override
	@Cacheable
	public List<PostVO> findLatests(int maxResults, long ignoreUserId) {
		List<Post> list = postDao.findTop10ByOrderByCreatedDesc();
		List<PostVO> rets = new ArrayList<>();

		list.forEach(po -> rets.add(BeanMapUtils.copy(po, 0)));

		return rets;
	}

	@Override
	@Cacheable
	public List<PostVO> findHots(int maxResults, long ignoreUserId) {
		List<Post> list = postDao.findTop10ByOrderByViewsDesc();
		List<PostVO> rets = new ArrayList<>();

		list.forEach(po -> rets.add(BeanMapUtils.copy(po, 0)));
		return rets;
	}

	@Override
	public Map<Long, PostVO> findMapByIds(Set<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			return Collections.emptyMap();
		}

		List<Post> list = postDao.findAllByIdIn(ids);
		Map<Long, PostVO> rets = new HashMap<>();

		HashSet<Long> uids = new HashSet<>();

		list.forEach(po -> {
			rets.put(po.getId(), BeanMapUtils.copy(po, 0));
			uids.add(po.getAuthorId());
		});

		// 加载用户信息
		buildUsers(rets.values(), uids);

		return rets;
	}

	@Override
	@Transactional
	@CacheEvict(key = "'view_' + #postId")
	public void favor(long userId, long postId) {
		postDao.updateFavors(postId, Consts.IDENTITY_STEP);
		favorService.add(userId, postId);
	}

	@Override
	@Transactional
	@CacheEvict(key = "'view_' + #postId")
	public void unfavor(long userId, long postId) {
		postDao.updateFavors(postId, Consts.DECREASE_STEP);
		favorService.delete(userId, postId);
	}

	@Override
	@Transactional
	public void resetIndexs() {
		postDao.resetIndexs();
	}

	private List<PostVO> toPosts(List<Post> posts) {
		List<PostVO> rets = new ArrayList<>();

		HashSet<Long> pids = new HashSet<>();
		HashSet<Long> uids = new HashSet<>();
		HashSet<Integer> groupIds = new HashSet<>();

		posts.forEach(po -> {
			pids.add(po.getId());
			uids.add(po.getAuthorId());
			groupIds.add(po.getChannelId());

			rets.add(BeanMapUtils.copy(po, 0));
		});

		// 加载用户信息
		buildUsers(rets, uids);
		buildGroups(rets, groupIds);

		return rets;
	}

	private void buildUsers(Collection<PostVO> posts, Set<Long> uids) {
		Map<Long, UserVO> userMap = userService.findMapByIds(uids);

		posts.forEach(p -> p.setAuthor(userMap.get(p.getAuthorId())));
	}

	private void buildGroups(Collection<PostVO> posts, Set<Integer> groupIds) {
		Map<Integer, Channel> map = channelService.findMapByIds(groupIds);
		posts.forEach(p -> p.setChannel(map.get(p.getChannelId())));
	}
}
