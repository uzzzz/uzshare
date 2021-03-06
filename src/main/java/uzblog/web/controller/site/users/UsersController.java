/*
+--------------------------------------------------------------------------
|   
|   ========================================
|    
|   
|
+---------------------------------------------------------------------------
*/
package uzblog.web.controller.site.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import uzblog.modules.user.data.UserVO;
import uzblog.modules.user.service.UserService;
import uzblog.web.controller.BaseController;
import uzblog.web.controller.site.Views;

import javax.servlet.http.HttpServletRequest;

/**
 * 访问他人主页
 * 
 *
 */
@Controller
public class UsersController extends BaseController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/users/{uid}")
	public String home(@PathVariable Long uid, HttpServletRequest request, ModelMap model) {
		UserVO user = userService.get(uid);
		int pn = ServletRequestUtils.getIntParameter(request, "pn", 1);

		model.put("user", user);
		model.put("pn", pn);
		return view(Views.USERS_VIEW);
	}
}
