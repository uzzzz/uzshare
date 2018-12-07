package uzblog.web.controller.site;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import uzblog.base.lang.Consts;
import uzblog.web.controller.BaseController;

@Controller
public class IndexController extends BaseController {

	@RequestMapping(value = { "/", "/index" })
	public String root(ModelMap model, HttpServletRequest request) {
		String order = ServletRequestUtils.getStringParameter(request, "order", Consts.order.NEWEST);
		int pn = ServletRequestUtils.getIntParameter(request, "pn", 1);
		model.put("order", order);
		model.put("pn", pn);
		return view(Views.INDEX);
	}
}
