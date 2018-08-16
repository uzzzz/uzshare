package mblog.web.controller.api;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import mblog.base.lang.Consts;
import mblog.base.utils.AsyncTask;
import mblog.modules.blog.data.PostVO;
import mblog.modules.blog.service.PostService;
import mblog.web.controller.BaseController;

@Controller
@RequestMapping("/api")
public class PostController extends BaseController {

	@Value("${site.store.root}")
	private String sitestoreroot;

	@Autowired
	private PostService postService;

	@Autowired
	private AsyncTask task;

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
	public long post(String title, String content) throws IOException {

		Assert.state(StringUtils.isNotBlank(title), "标题不能为空");
		Assert.state(StringUtils.isNotBlank(content), "内容不能为空");

		PostVO post = new PostVO();
		post.setTitle(title);
		post.setContent(content);
		post.setAuthorId(2);
		post.setChannelId(2);
		post.setThumbnail("");
		long id = postService.post(post);

		task.postBaiduForBlog(id);

		return id;
	}

	@GetMapping("/rewritesitemap")
	@ResponseBody
	public String rewritesitemap() throws IOException {
		String sitemappath = sitestoreroot + "sitemap.txt";
		List<Long> ids = postService.findAllIds();
		BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(sitemappath, false), "UTF-8"));
		writer.write("https://blog.uzzz.org/");
		for (Long id : ids) {
			writer.write("\nhttps://blog.uzzz.org/view/" + id);
		}
		writer.close();
		return "OK";
	}

}
