/**
 *
 */
package uzblog.template.directive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uzblog.modules.blog.data.PostVO;
import uzblog.modules.blog.service.PostService;
import uzblog.template.DirectiveHandler;
import uzblog.template.TemplateDirective;

import java.util.List;

/**
 * 推荐内容查询
 *
 * @author langhsu
 *
 */
@Component
public class BannerDirective extends TemplateDirective {
	@Autowired
    private PostService postService;

    @Override
    public String getName() {
        return "banner";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        List<PostVO> result = postService.findAllFeatured();
        handler.put(RESULTS, result).render();
    }
}
