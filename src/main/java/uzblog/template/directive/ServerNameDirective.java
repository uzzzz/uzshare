package uzblog.template.directive;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import uzblog.template.DirectiveHandler;
import uzblog.template.TemplateDirective;

@Component
public class ServerNameDirective extends TemplateDirective {

	@Override
	public String getName() {
		return "serverName";
	}

	@Override
	public void execute(DirectiveHandler handler) throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		handler.put(RESULT, request.getServerName()).render();
	}
}
