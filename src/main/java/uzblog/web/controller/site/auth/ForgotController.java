package uzblog.web.controller.site.auth;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import uzblog.base.data.Data;
import uzblog.modules.user.data.UserVO;
import uzblog.modules.user.entity.Secret;
import uzblog.modules.user.service.SecretService;
import uzblog.modules.user.service.UserService;
import uzblog.web.controller.BaseController;
import uzblog.web.controller.site.Views;

/**
 * on 2015/8/14.
 */
@Controller
@RequestMapping("forgot")
public class ForgotController extends BaseController {

	@Autowired
	private UserService userService;

	@Autowired
	private SecretService secretService;

	@GetMapping("apply")
	public String get_apply() {
		return view(Views.FORGOT_APPLY);
	}

	@PostMapping("apply")
	public String post_apply(String username, ModelMap model) {
		Data data = null;
		if (StringUtils.isBlank(username)) {
			data = Data.failure("用户名不能为空");
			model.put("data", data);
			return view(Views.FORGOT_APPLY);
		} else {
			UserVO user = userService.getByUsername(username);
			if (user == null) {
				data = Data.failure("查无此用户");
				model.put("data", data);
				return view(Views.FORGOT_APPLY);
			} else {
				Secret secret = secretService.randomByUserId(user.getId());
				if (secret == null) {
					data = Data.failure("此用户未设置密码保护，不能使用此方式找回密码，请联系客服！");
					model.put("data", data);
					return view(Views.FORGOT_APPLY);
				} else {
					data = Data.success("请输入密保问题的答案，并设置新密码", Data.NOOP);
					model.put("data", data);
					model.put("user", user);
					model.put("secret", secret);
					return view(Views.FORGOT_RESET);
				}
			}
		}
	}

	@PostMapping("reset")
	public String reset(Long userId, Long secretId, String question, String answer, String password, ModelMap model) {
		Data data;
		try {
			Assert.notNull(userId, "非法操作");
			Assert.notNull(secretId, "非法操作");
			Assert.hasLength(question, "非法操作");

			Secret secret = secretService.findByIdAndUserId(secretId, userId);
			if (secret != null && secret.getQuestion().equals(question) //
					&& secret.getAnswer().equals(answer)) {
				userService.updatePassword(userId, password);
				data = Data.success("恭喜您! 密码重置成功。");
				data.addLink("login", "去登陆");
			} else {
				data = Data.failure("密保不正确");
			}
		} catch (Exception e) {
			data = Data.failure(e.getMessage());
		}
		model.put("data", data);
		return view(Views.REGISTER_RESULT);
	}
}
