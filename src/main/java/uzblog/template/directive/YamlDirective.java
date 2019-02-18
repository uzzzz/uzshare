package uzblog.template.directive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import uzblog.template.DirectiveHandler;
import uzblog.template.TemplateDirective;

@Component
public class YamlDirective extends TemplateDirective {

	@Autowired
	private Environment env;

	@Override
	public String getName() {
		return "yaml";
	}

	@Override
	public void execute(DirectiveHandler handler) throws Exception {
		env.getProperty("server.host");
		handler.put(RESULTS, env).render();
	}
}
