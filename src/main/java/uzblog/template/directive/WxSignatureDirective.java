package uzblog.template.directive;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import uzblog.modules.weixin.WxProvider;
import uzblog.template.DirectiveHandler;
import uzblog.template.TemplateDirective;

@Component
public class WxSignatureDirective extends TemplateDirective {

	@Autowired
	private WxProvider wxProvider;

	@Override
	public String getName() {
		return "wx_signature";
	}

	@Override
	public void execute(DirectiveHandler handler) throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		StringBuffer url = request.getRequestURL();
		String query = request.getQueryString();
		if (query != null) {
			url.append("?").append(query);
		}
		handler.put(RESULT, wxProvider.wx_signature(url.toString())).render();
	}
}
