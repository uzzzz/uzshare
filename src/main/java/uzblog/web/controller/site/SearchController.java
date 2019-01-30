/*
+--------------------------------------------------------------------------
|   
|   ========================================
|    
|   
|
+---------------------------------------------------------------------------
*/
package uzblog.web.controller.site;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import uzblog.modules.blog.data.PostVO;
import uzblog.modules.blog.service.PostService;
import uzblog.web.controller.BaseController;

/**
 * 文章搜索
 * 
 *
 */
@Controller
public class SearchController extends BaseController {
	@Autowired
	private PostService postService;

	@RequestMapping("/search")
	public String search(String kw, ModelMap model) {
		Pageable pageable = wrapPageable();
		try {
			if (StringUtils.isNotEmpty(kw)) {
				Page<PostVO> page = postService.search(pageable, kw);
				model.put("page", page);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.put("kw", kw);
		return view(Views.BROWSE_SEARCH);
	}
	
}
