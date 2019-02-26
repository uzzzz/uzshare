package uzblog.web.controller.site.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import uzblog.base.data.Data;
import uzblog.modules.user.data.AccountProfile;
import uzblog.modules.user.entity.Secret;
import uzblog.modules.user.service.SecretService;
import uzblog.web.controller.BaseController;
import uzblog.web.controller.site.Views;

@Controller
@RequestMapping("/user")
public class SecretController extends BaseController {

	@Autowired
	private SecretService secretService;

	@GetMapping(value = "/secret")
	public String list(ModelMap model) {
		AccountProfile profile = getSubject().getProfile();
		List<Secret> secrets = secretService.secrets(profile.getId());
		secrets.stream().forEach(s -> s.setAnswer(s.getAnswer().substring(0, 1) + "*****"));
		model.put("secrets", secrets);
		return view(Views.USER_SECRET);
	}

	@PostMapping(value = "/add_secret")
	public String post(RedirectAttributes attrs, Secret secret) {
		AccountProfile profile = getSubject().getProfile();
		secret.trim();
		if (StringUtils.isEmpty(secret.getAnswer()) || StringUtils.isEmpty(secret.getQuestion())) {
			attrs.addFlashAttribute("data", Data.failure("添加失败：问题和答案都不能为空"));
			return "redirect:/user/secret";
		} else {
			secret.setUserId(profile.getId());
			secretService.save(secret);
			attrs.addFlashAttribute("data", Data.success("添加成功", null));
			return "redirect:/user/secret";
		}

	}

	@GetMapping(value = "/delete_secret")
	public String delete(RedirectAttributes attrs, long id) {
		AccountProfile profile = getSubject().getProfile();
		if (secretService.existsByIdAndUserId(id, profile.getId())) {
			secretService.delete(id);
			attrs.addFlashAttribute("data", Data.success("删除成功", null));
		}
		return "redirect:/user/secret";
	}

}
