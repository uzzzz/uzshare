package uzblog.web.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import uzblog.modules.authc.data.PermissionTree;
import uzblog.modules.authc.service.PermissionService;
import uzblog.web.controller.BaseController;

@Controller
@RequestMapping("/admin/permission")
public class PermissionController extends BaseController {
	@Autowired
	private PermissionService permissionService;

	@GetMapping("/tree")
	@ResponseBody
	public List<PermissionTree> tree() {
		return permissionService.tree();
	}
}
