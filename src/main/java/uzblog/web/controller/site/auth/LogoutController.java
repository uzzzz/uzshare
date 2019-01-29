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

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import uzblog.web.controller.BaseController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author langhsu
 *
 */
@Controller
public class LogoutController extends BaseController {

	/**
	 * 退出登录
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletResponse response) {
		SecurityUtils.getSubject().logout();
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0); // Proxies.
		return "redirect:/index";
	}

}
