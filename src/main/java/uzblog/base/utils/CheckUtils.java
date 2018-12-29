package uzblog.base.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtils {

	// 请输入6-20位数字、字母组合的密码
	public static boolean isValidUsername(String username) {
		Pattern p = Pattern.compile("^[a-zA-Z0-9]{6,20}$");
		Matcher m = p.matcher(username);
		return m.matches();
	}
}
