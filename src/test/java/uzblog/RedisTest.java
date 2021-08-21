package uzblog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import uzblog.boot.RedisService;

@SpringBootTest(classes = BootApplication.class)
public class RedisTest {

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisService redisService;

	@SuppressWarnings("unchecked")
	@Test
	public void testRedis() {
		redisService.opsForValue().set("aaa", "111");
	}

}
