package uzblog;

public class UsersTest {

	static String sql = "INSERT INTO `mto_users` (`id`, `created`, `last_login`, `password`, `status`, `username`, `name`, `avatar`, `gender`, `role_id`, `source`, `active_email`, `comments`, `fans`, `favors`, `follows`, `posts`) VALUES ('%d', '2018-12-06 11:38:02', '2018-12-06 18:23:49', '222222', '0', 'user%d', '雾夜皎月%d~)', 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3716172060,3839551880&fm=200&gp=0.jpg', '0', '1', '0', '0', '0', '0', '0', '1', '0');";

	public static void main(String[] args) {
		for (int i = 199; i < 201; i++) {
			String s = String.format(sql, i, i, i);
			System.out.println(s);
		}
	}
}
