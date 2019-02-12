package uzblog;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import uzblog.boot.RedisService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class RedisTest {

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisService redisService;

	@SuppressWarnings("unchecked")
	@Test
	public void testRedis() {
		redisService.opsForValue().set("aaa", "111");
		assertEquals("111", redisService.opsForValue().get("aaa"));
	}

}
