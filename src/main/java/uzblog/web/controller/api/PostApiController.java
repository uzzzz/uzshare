package uzblog.web.controller.api;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
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
import com.redfin.sitemapgenerator.SitemapIndexGenerator;
import com.redfin.sitemapgenerator.W3CDateFormat;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;

import uzblog.base.lang.Consts;
import uzblog.base.print.Printer;
import uzblog.base.utils.AsyncTask;
import uzblog.modules.blog.data.PostVO;
import uzblog.modules.blog.service.PostService;
import uzblog.web.controller.BaseController;

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

	@GetMapping("/rewritesitemapxml")
	@ResponseBody
	public String rewritesitemapxml() throws IOException {
		String baseUrl = "https://blog.uzzz.org";

		WebSitemapGenerator wsgGzip = WebSitemapGenerator.builder(baseUrl, new File(sitestoreroot)).gzip(true).build();

		List<Long> ids = postService.findAllIds();
		for (Long id : ids) {
			WebSitemapUrl url = new WebSitemapUrl.Options(baseUrl + "/view/" + id).priority(0.9)
					.changeFreq(ChangeFreq.DAILY).build();
			wsgGzip.addUrl(url);
		}

		List<File> viewsGzip = wsgGzip.write();

		// 构造 sitemap_index 生成器
		W3CDateFormat dateFormat = new W3CDateFormat(W3CDateFormat.Pattern.DAY);
		SitemapIndexGenerator sitemapIndexGenerator = new SitemapIndexGenerator.Options(baseUrl,
				new File(sitestoreroot + "/sitemap_index.xml")).autoValidate(true).dateFormat(dateFormat).build();

		Printer.warn("sitemap size : " + viewsGzip.size());
		viewsGzip.forEach(e -> {
			try { // 组装 sitemap 文件 URL 地址
				String url = baseUrl + "/" + e.getName();
				Printer.warn("sitemap index url : " + url);
				sitemapIndexGenerator.addUrl(url);
			} catch (MalformedURLException mue) {
				mue.printStackTrace();
			}
		});
		// 生成 sitemap_index 文件
		sitemapIndexGenerator.write();
		return "OK";
	}
}
