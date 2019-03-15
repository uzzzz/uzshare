package uzblog.web.controller.api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

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

import uzblog.base.lang.Consts;
import uzblog.modules.blog.data.PostVO;
import uzblog.modules.blog.service.PostCacheableService;
import uzblog.web.controller.BaseController;

@Controller
@RequestMapping("/api")
public class PostApiController extends BaseController {

	@Autowired
	private PostCacheableService postService;

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
	public long post(String title, String content, //
			@RequestParam(required = false, defaultValue = "2") long uid,
			@RequestParam(required = false, defaultValue = "2") int cid,
			@RequestParam(required = false, defaultValue = "") String tags,
			@RequestParam(required = false, defaultValue = "") String thumbnail) throws IOException {

		Assert.state(StringUtils.isNotBlank(title), "标题不能为空");
		Assert.state(StringUtils.isNotBlank(content), "内容不能为空");

		PostVO post = new PostVO();
		post.setTitle(title);
		post.setContent(content);
		post.setAuthorId(uid);
		post.setChannelId(cid);
		post.setTags(tags);
		post.setThumbnail(thumbnail);
		long id = postService.post(post);

		return id;
	}
}
