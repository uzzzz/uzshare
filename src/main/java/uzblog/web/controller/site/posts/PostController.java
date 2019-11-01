/**
 *
 */
package uzblog.web.controller.site.posts;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import uzblog.base.data.Data;
import uzblog.base.lang.Consts;
import uzblog.modules.blog.data.PostVO;
import uzblog.modules.blog.service.ChannelService;
import uzblog.modules.blog.service.PostCacheableService;
import uzblog.modules.user.data.AccountProfile;
import uzblog.web.controller.BaseController;
import uzblog.web.controller.site.Views;

/**
 * 文章操作
 * 
 *
 */
@Controller
@RequestMapping("/post")
public class PostController extends BaseController {
	@Autowired
	private PostCacheableService postService;
	@Autowired
	private ChannelService channelService;

	/**
	 * 发布文章页
	 * 
	 * @return
	 */
	@GetMapping("/editing")
	public String editing(Long id, ModelMap model) {
		model.put("channels", channelService.findAll(Consts.STATUS_NORMAL));

		if (null != id && id > 0) {
			AccountProfile profile = getSubject().getProfile();
			PostVO view = postService.get(id);

			Assert.notNull(view, "该文章已被删除 id : " + id);
			Assert.isTrue(view.getAuthorId() == profile.getId(), "该文章不属于你");
			model.put("view", view);
		}

		model.put("channels", channelService.findAll(Consts.STATUS_NORMAL));
		return view(Views.ROUTE_POST_EDITING);
	}

	/**
	 * 提交发布
	 * 
	 * @param post
	 * @return
	 */
	@PostMapping("/submit")
	public String post(PostVO post, @RequestParam(value = "file", required = false) MultipartFile file)
			throws IOException {
		Assert.notNull(post, "参数不完整");
		Assert.state(StringUtils.isNotBlank(post.getTitle()), "标题不能为空");
		Assert.state(StringUtils.isNotBlank(post.getContent()), "内容不能为空");

		AccountProfile profile = getSubject().getProfile();

		Assert.isTrue(profile.getStatus() != Consts.STATUS_CLOSED, "没有权限");

		post.setAuthorId(profile.getId());

		/**
		 * 保存预览图片
		 */
		if (file != null && !file.isEmpty()) {
			String thumbnail = fileRepo.store(file, appContext.getThumbsDir());

			if (StringUtils.isNotBlank(post.getThumbnail())) {
				fileRepo.deleteFile(post.getThumbnail());
			}

			post.setThumbnail(thumbnail);
		}

		// 修改时, 验证归属
		if (post.getId() > 0) {
			PostVO exist = postService.get(post.getId());
			Assert.notNull(exist, "文章不存在");
			Assert.isTrue(exist.getAuthorId() == profile.getId(), "该文章不属于你");

			postService.update(post);
		} else {
			postService.post(post);
		}
		return Views.REDIRECT_USER_POSTS;
	}

	/**
	 * 删除文章
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	public @ResponseBody Data delete(@PathVariable Long id) {
		Data data = Data.failure("操作失败");
		if (id != null) {
			AccountProfile up = getSubject().getProfile();
			try {
				postService.delete(id, up.getId());
				data = Data.success("操作成功", Data.NOOP);
			} catch (Exception e) {
				data = Data.failure(e.getMessage());
			}
		}
		return data;
	}

}
