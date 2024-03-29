/**
 *
 */
package uzblog.template.directive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import uzblog.modules.blog.data.PostVO;
import uzblog.modules.blog.service.PostService;
import uzblog.template.DirectiveHandler;
import uzblog.template.TemplateDirective;

/**
 * 根据作者取文章列表
 *
 * 
 *
 */
@Component
public class AuthorContentsDirective extends TemplateDirective {
    @Autowired
	private PostService postService;

	@Override
	public String getName() {
		return "author_contents";
	}

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        int pn = handler.getInteger("pn", 1);
        long uid = handler.getInteger("uid", 0);

        Pageable pageable = PageRequest.of(pn - 1, 10);
        Page<PostVO> result = postService.pagingByAuthorId(pageable, uid);

        handler.put(RESULTS, result).render();
    }

}
