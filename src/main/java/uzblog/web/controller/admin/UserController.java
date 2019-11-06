/*
+--------------------------------------------------------------------------
|   
|   ========================================
|    
|   
|
+---------------------------------------------------------------------------
*/
package uzblog.web.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import uzblog.base.data.Data;
import uzblog.base.lang.Consts;
import uzblog.modules.authc.entity.Role;
import uzblog.modules.authc.service.RoleService;
import uzblog.modules.authc.service.UserRoleService;
import uzblog.modules.blog.service.PostCacheableService;
import uzblog.modules.user.data.UserVO;
import uzblog.modules.user.service.UserService;
import uzblog.web.controller.BaseController;

/**
 * 
 *
 */
@Controller("adminUserController")
@RequestMapping("/admin/user")
public class UserController extends BaseController {
	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private PostCacheableService postService;

	@RequestMapping("/list")
	@RequiresPermissions("user:list")
	public String list(ModelMap model) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String key = ServletRequestUtils.getStringParameter(request, "key", null);

		Pageable pageable = wrapPageable(20);
		Page<UserVO> page = userService.paging(key, pageable);

		List<UserVO> users = page.getContent();
		List<Long> userIds = new ArrayList<>();

		users.forEach(item -> {
			userIds.add(item.getId());
		});

		Map<Long, List<Role>> map = userRoleService.findMapByUserIds(userIds);
		users.forEach(item -> {
			item.setRoles(map.get(item.getId()));
		});

		model.put("page", page);
		model.put("key", key);
		return "/admin/user/list";
	}

	@RequestMapping("/view")
	public String view(Long id, ModelMap model) {
		UserVO view = userService.get(id);
		view.setRoles(userRoleService.listRoles(view.getId()));
		model.put("view", view);
		model.put("roles", roleService.list());
		return "/admin/user/view";
	}

	@PostMapping("/update_role")
	@RequiresPermissions("user:role")
	public String postAuthc(Long id, @RequestParam(value = "roleIds", required = false) Set<Long> roleIds,
			ModelMap model) {
		userRoleService.updateRole(id, roleIds);
		model.put("data", Data.success());
		return "redirect:/admin/user/list";
	}

	@RequestMapping(value = "/pwd", method = RequestMethod.GET)
	@RequiresPermissions("user:pwd")
	public String pwsView(Long id, ModelMap model) {
		UserVO ret = userService.get(id);
		model.put("view", ret);
		return "/admin/user/pwd";
	}

	@RequestMapping(value = "/pwd", method = RequestMethod.POST)
	@RequiresPermissions("user:pwd")
	public String pwd(Long id, String newPassword, ModelMap model) {
		UserVO ret = userService.get(id);
		model.put("view", ret);

		try {
			userService.updatePassword(id, newPassword);
			model.put("message", "修改成功");
		} catch (IllegalArgumentException e) {
			model.put("message", e.getMessage());
		}
		return "/admin/user/pwd";
	}

	@RequestMapping("/open")
	@RequiresPermissions("user:open")
	public @ResponseBody Data open(Long id) {
		userService.updateStatus(id, Consts.STATUS_NORMAL);
		Data data = Data.success("操作成功", Data.NOOP);
		return data;
	}

	@RequestMapping("/close")
	@RequiresPermissions("user:close")
	public @ResponseBody Data close(Long id) {
		userService.updateStatus(id, Consts.STATUS_CLOSED);
		Data data = Data.success("操作成功", Data.NOOP);
		return data;
	}

	@RequestMapping("/close_delete_posts_by_user_id")
	@RequiresPermissions("user:close")
	public @ResponseBody Data close_delete_posts(Long id) {
		userService.updateStatus(id, Consts.STATUS_CLOSED);
		postService.deleteAuthorId(id);
		Data data = Data.success("操作成功", Data.NOOP);
		return data;
	}
}
