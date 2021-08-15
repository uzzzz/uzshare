package uzblog.base.utils;

import java.util.function.Supplier;

public class Utils {

	public static String substring(String content, String prefix, String suffix) {
		if (content == null || prefix == null || suffix == null) {
			return "";
		}
		int start = content.indexOf(prefix);
		if (start >= 0) {
			int end = content.indexOf(suffix, start + prefix.length());
			if (end > start) {
				return content.substring(start + prefix.length(), end);
			}
		}
		return "";
	}

	public static void tryCatch(Runnable r) {
		try {
			r.run();
		} catch (Exception e) {
		}

	}

	public static <T> T tryCatch(Supplier<T> supplier) {
		try {
			return supplier.get();
		} catch (Exception e) {
		}
		return null;
	}
}
