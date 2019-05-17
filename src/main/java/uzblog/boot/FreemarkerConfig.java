package uzblog.boot;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;
import uzblog.shiro.tags.ShiroTags;
import uzblog.template.directive.AuthorContentsDirective;
import uzblog.template.directive.BannerDirective;
import uzblog.template.directive.ChannelDirective;
import uzblog.template.directive.ContentsDirective;
import uzblog.template.directive.NumberDirective;
import uzblog.template.directive.ResourceDirective;
import uzblog.template.directive.ServerNameDirective;
import uzblog.template.method.TimeAgoMethod;
import uzblog.web.menu.MenusDirective;

@Component
public class FreemarkerConfig {

	@Value("${cookie.free.domain}")
	private String cookieFreeDomain;
	
	@Value("${css.version}")
	private String cssVersion;

	@Autowired
	private Configuration configuration;

	@Autowired
	private ApplicationContext applicationContext;

	@PostConstruct
	public void setSharedVariable() throws TemplateModelException {
		configuration.setSharedVariable("author_contents", applicationContext.getBean(AuthorContentsDirective.class));
		configuration.setSharedVariable("channel", applicationContext.getBean(ChannelDirective.class));
		configuration.setSharedVariable("contents", applicationContext.getBean(ContentsDirective.class));
		configuration.setSharedVariable("num", applicationContext.getBean(NumberDirective.class));
		configuration.setSharedVariable("resource", applicationContext.getBean(ResourceDirective.class));
		configuration.setSharedVariable("menus", applicationContext.getBean(MenusDirective.class));
		configuration.setSharedVariable("banner", applicationContext.getBean(BannerDirective.class));
		configuration.setSharedVariable("server_name", applicationContext.getBean(ServerNameDirective.class));

		configuration.setSharedVariable("timeAgo", new TimeAgoMethod());
		configuration.setSharedVariable("shiro", new ShiroTags());

		configuration.setSharedVariable("cookieFreeDomain", cookieFreeDomain);
		configuration.setSharedVariable("cssVersion", cssVersion);
	}
}
