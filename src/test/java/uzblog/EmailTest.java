package uzblog;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import uzblog.base.lang.Consts;
import uzblog.base.print.Printer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class EmailTest {

	@Test
	public void testSendMall() {

		Map<String, Object> context = new HashMap<>();
		context.put("userId", "1");
		context.put("code", "123456");
		context.put("type", Consts.VERIFY_BIND);
		String email = "fandyvon@163.com";
		Printer.error("sending email start");
		Printer.error(email + " send success");
	}

}