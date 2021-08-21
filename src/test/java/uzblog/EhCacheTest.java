package uzblog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import uzblog.modules.blog.service.PostCacheableService;

@SpringBootTest(classes = BootApplication.class)
public class EhCacheTest {

	@Autowired
	private PostCacheableService postService;

	@Test
	public void testEhCache() {

		System.out.println(System.getProperty("java.io.tmpdir"));
		
		System.out.println("get 1");
		postService.get(313386);

		System.out.println("get 2");
		postService.get(313386);
	}

}
