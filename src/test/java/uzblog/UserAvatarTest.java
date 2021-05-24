package uzblog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import uzblog.modules.user.data.AccountProfile;
import uzblog.modules.user.service.UserService;
import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@ActiveProfiles("prod")
@SpringBootTest(classes = BootApplication.class)
public class UserAvatarTest {

	@Autowired
	private UserService userService;

	@Test
	@Transactional
	@Rollback(false)
	public void testUserAvatars() {

		for(int i = 1 ; i <= 200; i++) {
			String avatar = String.format("/store/ava/02%03d.jpg", i);
			AccountProfile ap = userService.updateAvatar(i, avatar);
			System.out.println(ap.getAvatar());
		}
	}

}
