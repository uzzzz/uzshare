/**
 * 
 */
package mblog.web.controller.api;

import mblog.base.lang.Consts;
import mblog.modules.blog.data.PostVO;
import mblog.modules.blog.service.PostService;
import mblog.modules.user.data.AccountProfile;
import mblog.web.controller.BaseController;
import mblog.web.controller.site.Views;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author langhsu
 *
 */
@Controller
@RequestMapping("/api")
public class PostJsonController extends BaseController {
	@Autowired
	private PostService postService;

	@RequestMapping("/posts")
	@ResponseBody
	public Page<PostVO> posts(HttpServletRequest request) {
		String order = ServletRequestUtils.getStringParameter(request, "ord", Consts.order.NEWEST);
		int channelId = ServletRequestUtils.getIntParameter(request, "channelId", 0);
		Pageable pageable = wrapPageable();
		Page<PostVO> page = postService.paging(pageable, channelId, null, order);
		return page;
	}

	@PostMapping("/post")
	@ResponseBody
	public String post(String title, String content) throws IOException {

		Assert.state(StringUtils.isNotBlank(title), "标题不能为空");
		Assert.state(StringUtils.isNotBlank(content), "内容不能为空");

		PostVO post = new PostVO();
		post.setTitle(title);
		post.setContent(content);
		post.setAuthorId(2);
		// 修改时, 验证归属
		if (post.getId() > 0) {
			PostVO exist = postService.get(post.getId());
			Assert.notNull(exist, "文章不存在");
			postService.update(post);
		} else {
			postService.post(post);
		}
		return "OK";
	}
}
