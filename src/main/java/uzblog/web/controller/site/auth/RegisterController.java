/**
 * 
 */
package uzblog.web.controller.site.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import uzblog.base.data.Data;
import uzblog.base.lang.Consts;
import uzblog.modules.user.data.AccountProfile;
import uzblog.modules.user.data.UserVO;
import uzblog.modules.user.service.UserService;
import uzblog.modules.user.service.VerifyService;
import uzblog.web.controller.BaseController;
import uzblog.web.controller.site.Views;

/**
 * @author langhsu
 *
 */
@Controller
public class RegisterController extends BaseController {

	@Autowired
	private UserService userService;

	@Autowired
	private VerifyService verifyService;

	@GetMapping("/register")
	public String view() {
		AccountProfile profile = getSubject().getProfile();
		if (profile != null) {
			return "redirect:/home";
		}
		return view(Views.REGISTER);
	}

	@PostMapping("/register")
	public String register(UserVO post, ModelMap model) {
		Data data;
		String ret = view(Views.REGISTER);

		try {
			post.setAvatar(Consts.AVATAR);
			UserVO user = userService.register(post);

			String code = verifyService.generateCode(user.getId(), Consts.VERIFY_BIND, user.getEmail());
			Map<String, Object> context = new HashMap<>();
			context.put("userId", user.getId());
			context.put("code", code);
			context.put("type", Consts.VERIFY_BIND);

			data = Data.success("恭喜您! 注册成功!", Data.NOOP);
			data.addLink("login", "去登陆");

			ret = view(Views.REGISTER_RESULT);

		} catch (Exception e) {
			model.addAttribute("post", post);
			data = Data.failure(e.getMessage());
		}
		model.put("data", data);
		return ret;
	}

}