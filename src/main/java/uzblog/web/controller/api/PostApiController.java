package uzblog.web.controller.api;

import java.io.IOException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import uzblog.base.context.AppContext;
import uzblog.base.data.Data;
import uzblog.base.lang.Consts;
import uzblog.base.upload.FileRepo;
import uzblog.core.event.NotifyEvent;
import uzblog.modules.blog.data.CommentVO;
import uzblog.modules.blog.data.PostVO;
import uzblog.modules.blog.service.CommentService;
import uzblog.modules.blog.service.PostCacheableService;
import uzblog.web.controller.BaseController;

@Controller
@RequestMapping("/api")
public class PostApiController extends BaseController {

	private static Log log = LogFactory.getLog(PostApiController.class);

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private PostCacheableService postService;

	@Autowired
	private CommentService commentService;

	@Autowired
	protected AppContext appContext;

	@Autowired
	protected FileRepo fileRepo;

	@Value("${cookie.free.domain}")
	private String cookieFreeDomain;

//	@RequestMapping("/posts")
//	@ResponseBody
//	public Page<PostVO> posts(HttpServletRequest request) {
//		String order = ServletRequestUtils.getStringParameter(request, "ord", Consts.order.NEWEST);
//		int channelId = ServletRequestUtils.getIntParameter(request, "channelId", 0);
//		Pageable pageable = wrapPageable();
//		Page<PostVO> page = postService.paging(pageable, channelId, null, order);
//		return page;
//	}

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
		try {
			if (thumbnail != null && thumbnail.startsWith("http")) {
				String t = "https://" + cookieFreeDomain
						+ fileRepo.storeScale(new URL(thumbnail), appContext.getThumbsDir(), 262, 80);
				post.setThumbnail(t);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		long id = postService.post(post);
		return id;
	}

	@PostMapping("/post_comment")
	@ResponseBody
	public Data post_comment( //
			@RequestParam(required = false, defaultValue = "2") long uid, //
			@RequestParam(required = false, defaultValue = "0") long pid, //
			Long toId, String text, HttpServletRequest request) {

		Data data = Data.failure("操作失败");

		if (toId > 0 && StringUtils.isNotEmpty(text)) {
			CommentVO c = new CommentVO();
			c.setToId(toId);
			c.setContent(HtmlUtils.htmlEscape(text));
			c.setAuthorId(uid);
			c.setPid(pid);
			long comment_id = commentService.post(c);
			sendNotify(uid, toId, pid);
			data = Data.success("发表成功!", comment_id);
		}
		return data;
	}

	private void sendNotify(long userId, long postId, long pid) {
		NotifyEvent event = new NotifyEvent("NotifyEvent");
		event.setFromUserId(userId);

		if (pid > 0) {
			event.setEvent(Consts.NOTIFY_EVENT_COMMENT_REPLY);
		} else {
			event.setEvent(Consts.NOTIFY_EVENT_COMMENT);
		}
		// 此处不知道文章作者, 让通知事件系统补全
		event.setPostId(postId);
		applicationContext.publishEvent(event);
	}
}
