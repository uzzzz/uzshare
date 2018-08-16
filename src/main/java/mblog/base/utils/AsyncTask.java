package mblog.base.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AsyncTask {

	@Autowired
	private RestTemplate rest;

	@Async
	public void postBaiduForBlog(long id) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.TEXT_PLAIN);
			String content = "https://blog.uzzz.org/view/" + id;
			HttpEntity<String> requestEntity = new HttpEntity<String>(content, headers);
			// 执行HTTP请求
			String ret = rest.postForObject(
					"http://data.zz.baidu.com/urls?site=https://blog.uzzz.org&token=pJ67TFnK02hkMHlt", requestEntity,
					String.class);
			System.out.println("post baidu : " + ret + " (" + content + ")");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
