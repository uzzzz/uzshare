package uzblog.base.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import uzblog.base.print.Printer;

@Component
public class AsyncTask {

	@Autowired
	private RestTemplate rest;

	@Bean
	public TaskExecutor getTaskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setMaxPoolSize(10);
		taskExecutor.setCorePoolSize(5);
		taskExecutor.setQueueCapacity(20);
		return taskExecutor;
	}

	@Async
	public void postBaiduForBlog(long id) {
//		try {
//			String postUrl = "http://data.zz.baidu.com/urls?site=https://blog.uzzz.org&token=pJ67TFnK02hkMHlt";
//			HttpHeaders headers = new HttpHeaders();
//			headers.setContentType(MediaType.TEXT_PLAIN);
//			String content = "https://blog.uzzz.org/view/" + id;
//			HttpEntity<String> requestEntity = new HttpEntity<String>(content, headers);
//			// 执行HTTP请求
//			String ret = rest.postForObject(postUrl, requestEntity, String.class);
//			Printer.info("post baidu : " + ret + " (" + content + ")");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

}
