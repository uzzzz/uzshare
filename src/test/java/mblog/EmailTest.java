package mblog;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import uzblog.BootApplication;
import uzblog.base.lang.Consts;
import uzblog.base.print.Printer;
import uzblog.base.utils.MailHelper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class EmailTest {

	@Autowired
	private MailHelper mailHelper;

	@Test
	public void testSendMall() {

		Map<String, Object> context = new HashMap<>();
		context.put("userId", "1");
		context.put("code", "123456");
		context.put("type", Consts.VERIFY_BIND);
		String email = "fandyvon@163.com";
		Printer.error("sending email start");
		mailHelper.sendEmail(Consts.EMAIL_TEMPLATE_BIND, email, "邮箱绑定验证", context);
		Printer.error(email + " send success");
	}

}
