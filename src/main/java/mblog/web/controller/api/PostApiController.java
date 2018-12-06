package mblog.web.controller.api;

import java.io.BufferedWriter;
import java.io.File;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redfin.sitemapgenerator.ChangeFreq;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;

import mblog.base.lang.Consts;
import mblog.base.utils.AsyncTask;
import mblog.modules.blog.data.PostVO;
import mblog.modules.blog.service.PostService;
import mblog.web.controller.BaseController;

@Controller
@RequestMapping("/api")
public class PostApiController extends BaseController {

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
	public long post(String title, String content, //
			@RequestParam(required = false, defaultValue = "2") long uid,
			@RequestParam(required = false, defaultValue = "2") int cid,
			@RequestParam(required = false, defaultValue = "") String tags) throws IOException {

		Assert.state(StringUtils.isNotBlank(title), "标题不能为空");
		Assert.state(StringUtils.isNotBlank(content), "内容不能为空");

		PostVO post = new PostVO();
		post.setTitle(title);
		post.setContent(content);
		post.setAuthorId(uid);
		post.setChannelId(cid);
		post.setTags(tags);
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

	@GetMapping("/rewritesitemapxml")
	@ResponseBody
	public String rewritesitemapxml() throws IOException {
		String baseUrl = "https://blog.uzzz.org";
		WebSitemapGenerator wsg = new WebSitemapGenerator(baseUrl, new File(sitestoreroot));
		List<Long> ids = postService.findAllIds();
		for (Long id : ids) {
			WebSitemapUrl url = new WebSitemapUrl.Options(baseUrl + "/view/" + id).priority(0.9)
					.changeFreq(ChangeFreq.DAILY).build();
			wsg.addUrl(url);
		}
		wsg.write();
		wsg.writeSitemapsWithIndex();

		return "OK";
	}
}
