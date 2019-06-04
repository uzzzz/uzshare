package uzblog.web.controller.site.auth;

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
import uzblog.web.controller.BaseController;
import uzblog.web.controller.site.Views;

@Controller
public class RegisterController extends BaseController {

	@Autowired
	private UserService userService;

	@GetMapping("/register")
	public String view() {
		AccountProfile profile = getSubject().getProfile();
		if (profile != null) {
			return "redirect:/home";
		}
		return view(Views.REGISTER);
	}

	@PostMapping("/register")
	public String register(UserVO userVo, ModelMap model) {
		Data data;
		String ret = view(Views.REGISTER);

		try {
			userVo.setAvatar(Consts.AVATAR);
			userService.register(userVo);

			data = Data.success("恭喜您! 注册成功!", Data.NOOP);
			data.addLink("login", "去登陆");
			ret = view(Views.REGISTER_RESULT);
		} catch (Exception e) {
			model.addAttribute("post", userVo);
			data = Data.failure(e.getMessage());
		}
		model.put("data", data);
		return ret;
	}
}