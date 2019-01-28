package uzblog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import uzblog.modules.blog.service.PostService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class EhCacheTest {

	@Autowired
	private PostService postService;

	@Test
	public void testEhCache() {

		System.out.println("get 1");
		postService.get(313386);

		System.out.println("get 2");
		postService.get(313386);
	}

}
