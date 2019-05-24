/*
+--------------------------------------------------------------------------
|   
|   ========================================
|    
|   
|
+---------------------------------------------------------------------------
*/
package uzblog.web.controller.site.auth;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import uzblog.web.controller.BaseController;
import uzblog.web.controller.site.Views;

/**
 * 登录页
 * 
 */
@Controller
public class LoginController extends BaseController {

	@GetMapping(value = "login")
	public String login() {
		return view(Views.LOGIN);
	}

	/**
	 * 提交登录
	 * 
	 * @param username
	 * @param password
	 * @param model
	 * @return
	 */
	@PostMapping(value = "login")
	public String do_login(String username, String password,
			@RequestParam(value = "rememberMe", defaultValue = "0") int rememberMe, ModelMap model) {
		String ret = view(Views.LOGIN);

		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			return ret;
		}

		AuthenticationToken token = createToken(username, password);
		if (token == null) {
			model.put("message", "用户名或密码错误");
			return ret;
		}

		if (rememberMe == 1) {
			((UsernamePasswordToken) token).setRememberMe(true);
		}

		try {
			SecurityUtils.getSubject().login(token);

			ret = Views.REDIRECT_USER;
		} catch (AuthenticationException e) {
			if (e instanceof UnknownAccountException) {
				model.put("message", "用户不存在");
			} else if (e instanceof LockedAccountException) {
				model.put("message", "用户被禁用");
			} else {
				model.put("message", "用户认证失败");
			}
		}
		return ret;
	}
}
