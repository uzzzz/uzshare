/*
+--------------------------------------------------------------------------
|   
|   ========================================
|    
|   
|
+---------------------------------------------------------------------------
*/
package uzblog.web.controller.api;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import uzblog.base.data.Data;
import uzblog.modules.blog.data.PostVO;
import uzblog.modules.blog.service.PostCacheableService;
import uzblog.modules.user.data.AccountProfile;
import uzblog.modules.user.data.UserVO;
import uzblog.modules.user.service.UserService;
import uzblog.web.controller.BaseController;

/**
 * 侧边栏数据加载
 * 
 * 
 *
 */
@Controller
@RequestMapping("/api")
public class SidebarController extends BaseController {
	@Autowired
	private PostCacheableService postService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody Data login(String username, String password, ModelMap model) {
		Data data = Data.failure("操作失败");

		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			return data;
		}

		AuthenticationToken token = createToken(username, password);
		if (token == null) {
			data.setMessage("用户名或密码错误");
			return data;
		}

		try {
			SecurityUtils.getSubject().login(token);
			data = Data.success("登录成功", getSubject().getProfile());

		} catch (Exception e) {
			if (e instanceof UnknownAccountException) {
				data.setMessage("用户不存在");
			} else if (e instanceof LockedAccountException) {
				data.setMessage("用户被禁用");
			} else {
				data.setMessage("登录认证失败");
			}
		}
		return data;
	}

	@RequestMapping("/latests")
	public @ResponseBody List<PostVO> latests() {
		AccountProfile up = getSubject().getProfile();
		long ignoreUserId = 0;
		if (up != null) {
			ignoreUserId = up.getId();
		}
		List<PostVO> rets = postService.findLatests(6, ignoreUserId);
		return rets;
	}

	@RequestMapping("/hots")
	public @ResponseBody List<PostVO> hots() {
		AccountProfile up = getSubject().getProfile();
		long ignoreUserId = 0;
		if (up != null) {
			ignoreUserId = up.getId();
		}
		List<PostVO> rets = postService.findHots(6, ignoreUserId);
		return rets;
	}

	/**
	 * 热门用户
	 * 
	 * @return
	 */
	@RequestMapping(value = "/hotusers")
	public @ResponseBody List<UserVO> hotusers() {
		List<UserVO> rets = userService.findHotUserByfans();
		return rets;
	}
}
