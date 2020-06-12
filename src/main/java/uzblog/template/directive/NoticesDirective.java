/**
 *
 */
package uzblog.template.directive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import uzblog.base.lang.Consts;
import uzblog.modules.blog.data.PostVO;
import uzblog.modules.blog.entity.Channel;
import uzblog.modules.blog.entity.Notice;
import uzblog.modules.blog.service.ChannelService;
import uzblog.modules.blog.service.NoticeService;
import uzblog.modules.blog.service.PostService;
import uzblog.template.DirectiveHandler;
import uzblog.template.TemplateDirective;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 文章内容查询
 *
 * 示例： 请求：/index 使用：@notices
 */
@Component
public class NoticesDirective extends TemplateDirective {

	@Autowired
	private NoticeService noticeService;

	@Override
	public String getName() {
		return "notices";
	}

	@Override
	public void execute(DirectiveHandler handler) throws Exception {
		List<Notice> result = noticeService.findAll();
		handler.put("notices_" + RESULTS, result).render();
	}
}
