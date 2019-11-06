/*
+--------------------------------------------------------------------------
|   
|   ========================================
|    
|   
|
+---------------------------------------------------------------------------
*/
package uzblog.modules.user.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import uzblog.base.lang.EntityStatus;
import uzblog.base.utils.CheckUtils;
import uzblog.base.utils.DefaultRandomStringGenerator;
import uzblog.base.utils.MD5;
import uzblog.modules.user.dao.UserDao;
import uzblog.modules.user.data.AccountProfile;
import uzblog.modules.user.data.BadgesCount;
import uzblog.modules.user.data.UserVO;
import uzblog.modules.user.entity.User;
import uzblog.modules.user.service.NotifyService;
import uzblog.modules.user.service.UserService;
import uzblog.modules.utils.BeanMapUtils;

@Service
@Transactional(readOnly = true)
@CacheConfig(cacheNames = "users_caches")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private NotifyService notifyService;

	@Override
	@Transactional
	public AccountProfile login(String username, String password) {
		User po = userDao.findByUsername(username);
		AccountProfile u = null;

		Assert.notNull(po, "账户不存在");

//		Assert.state(po.getStatus() != Const.STATUS_CLOSED, "您的账户已被封禁");

		Assert.state(StringUtils.equals(po.getPassword(), password), "密码错误");

		po.setLastLogin(Calendar.getInstance().getTime());
		userDao.save(po);
		u = BeanMapUtils.copyPassport(po);

		BadgesCount badgesCount = new BadgesCount();
		badgesCount.setNotifies(notifyService.unread4Me(u.getId()));

		u.setBadgesCount(badgesCount);
		return u;
	}

	@Override
	@Transactional
	public AccountProfile getProfileByName(String username) {
		User po = userDao.findByUsername(username);
		AccountProfile u = null;

		Assert.notNull(po, "账户不存在");

//		Assert.state(po.getStatus() != Const.STATUS_CLOSED, "您的账户已被封禁");
		po.setLastLogin(Calendar.getInstance().getTime());

		u = BeanMapUtils.copyPassport(po);

		BadgesCount badgesCount = new BadgesCount();
		badgesCount.setNotifies(notifyService.unread4Me(u.getId()));

		u.setBadgesCount(badgesCount);

		return u;
	}

	@Override
	@Transactional
	public UserVO register(UserVO user) {
		Assert.notNull(user, "Parameter user can not be null!");

		Assert.hasLength(user.getUsername(), "用户名不能为空!");
		Assert.isTrue(CheckUtils.isValidUsername(user.getUsername()), "只能是6-20位字母或数字组合");
		Assert.hasLength(user.getPassword(), "密码不能为空!");

		User check = userDao.findByUsername(user.getUsername());
		Assert.isNull(check, "用户名已经存在!");

		User u = new User();

		BeanUtils.copyProperties(user, u);

		Date now = Calendar.getInstance().getTime();

		DefaultRandomStringGenerator gen = new DefaultRandomStringGenerator(8);
		String salt = gen.getNewString();
		u.setSalt(salt);
		u.setPassword(MD5.encryptPasswordMD5(user.getPassword(), salt));
		u.setStatus(EntityStatus.ENABLED);
		u.setCreated(now);

		userDao.save(u);

		return BeanMapUtils.copy(u, 0);
	}

	@Override
	@Transactional
	@CacheEvict(key = "#user.getId()")
	public AccountProfile update(UserVO user) {
		User po = userDao.getOne(user.getId());
		if (null != po) {
			po.setName(user.getName());
			po.setSignature(user.getSignature());
			userDao.save(po);
		}
		return BeanMapUtils.copyPassport(po);
	}

	@Override
	@Cacheable(key = "#userId")
	public UserVO get(long userId) {
		User po = userDao.findById(userId).get();
		UserVO ret = null;
		if (po != null) {
			ret = BeanMapUtils.copy(po, 0);
		}
		return ret;
	}

	@Override
	public List<UserVO> findHotUserByfans() {
		List<UserVO> rets = new ArrayList<>();
		List<User> list = userDao.findTop12ByOrderByFansDesc();
		for (User po : list) {
			UserVO u = BeanMapUtils.copy(po, 0);
			rets.add(u);
		}
		return rets;
	}

	@Override
	public UserVO getByUsername(String username) {
		User po = userDao.findByUsername(username);
		UserVO ret = null;
		if (po != null) {
			ret = BeanMapUtils.copy(po, 0);
		}
		return ret;
	}

	@Override
	@Transactional
	@CacheEvict(key = "#id")
	public AccountProfile updateAvatar(long id, String path) {
		User po = userDao.getOne(id);
		if (po != null) {
			po.setAvatar(path);
			userDao.save(po);
		}
		return BeanMapUtils.copyPassport(po);
	}

	@Override
	@Transactional
	public void updatePassword(long id, String newPassword) {
		User po = userDao.getOne(id);

		Assert.hasLength(newPassword, "密码不能为空!");

		if (null != po) {
			po.setPassword(MD5.encryptPasswordMD5(newPassword, po.getSalt()));
			userDao.save(po);
		}
	}

	@Override
	@Transactional
	public void updatePassword(long id, String oldPassword, String newPassword) {
		User po = userDao.getOne(id);

		Assert.hasLength(newPassword, "密码不能为空!");

		if (po != null) {
			Assert.isTrue(MD5.encryptPasswordMD5(oldPassword, po.getSalt()).equals(po.getPassword()), "当前密码不正确");

			DefaultRandomStringGenerator gen = new DefaultRandomStringGenerator(8);
			String salt = gen.getNewString();
			po.setSalt(salt);
			po.setPassword(MD5.encryptPasswordMD5(newPassword, salt));
			userDao.save(po);
		}
	}

	@Override
	@Transactional
	public void updateStatus(long id, int status) {
		User po = userDao.getOne(id);

		if (po != null) {
			po.setStatus(status);
			userDao.save(po);
		}
	}

	@Override
	public Page<UserVO> paging(Pageable pageable) {
		Page<User> page = userDao.findAllByOrderByIdDesc(pageable);
		List<UserVO> rets = new ArrayList<>();

		for (User po : page.getContent()) {
			UserVO u = BeanMapUtils.copy(po, 1);
			rets.add(u);
		}

		return new PageImpl<>(rets, pageable, page.getTotalElements());
	}

	@Override
	public Page<UserVO> paging(String key, Pageable pageable) {
		Page<User> page = null;
		if (StringUtils.isNotEmpty(key)) {
			key = "%" + key + "%";
			page = userDao.findByUsernameLikeOrNameLikeOrderByIdDesc(key, key, pageable);
		} else {
			page = userDao.findAllByOrderByIdDesc(pageable);
		}
		List<UserVO> rets = new ArrayList<>();
		for (User po : page.getContent()) {
			UserVO u = BeanMapUtils.copy(po, 1);
			rets.add(u);
		}
		return new PageImpl<>(rets, pageable, page.getTotalElements());
	}

	@Override
	public Map<Long, UserVO> findMapByIds(Set<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			return Collections.emptyMap();
		}
		List<User> list = userDao.findAllByIdIn(ids);
		Map<Long, UserVO> ret = new HashMap<>();

		list.forEach(po -> {
			ret.put(po.getId(), BeanMapUtils.copy(po, 0));
		});
		return ret;
	}

}
