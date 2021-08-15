package uzblog.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uzblog.modules.blog.data.PostVO;
import uzblog.modules.blog.service.PostCacheableService;
import uzblog.modules.blog.service.PostService;

@Component
public class AsyncTask {

	private static Logger log = LoggerFactory.getLogger(AsyncTask.class);

	@Autowired
	private PostService postService;

	@Autowired
	private PostCacheableService postCacheableService;

	@Bean
	public TaskExecutor getTaskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setMaxPoolSize(10);
		taskExecutor.setCorePoolSize(5);
		taskExecutor.setQueueCapacity(20);
		return taskExecutor;
	}

	private int random(int min, int max) {
		int s = (int) min + (int) (Math.random() * (max - min));
		if (s == 88) {
			return random(min, max);
		}
		return s;
	}

	@Async
	public void asyncRun(Runnable r) {
		r.run();
	}

	public long postBlog(int cid, String title, String c, String thumbnail, String tags) {
		try {
			if (postService.existsByTitle(title)) {
				return 0;
			}
		} catch (Exception e) {
		}
		try {
			int uid = random(2, 200);
			PostVO post = new PostVO();
			post.setTitle(title);
			post.setContent(c);
			post.setAuthorId(uid);
			post.setChannelId(cid);
			post.setTags(tags);
			try {
				if (thumbnail != null && thumbnail.startsWith("http")) {
					post.setThumbnail(thumbnail);
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			long id = postCacheableService.post(post);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public long postBlog(int cid, String title, String c, String thumbnail) {
		return postBlog(cid, title, c, thumbnail, "");
	}

	public long postBlog(String title, String c, String thumbnail, String tags) {
		return postBlog(1, title, c, thumbnail, tags);
	}

}