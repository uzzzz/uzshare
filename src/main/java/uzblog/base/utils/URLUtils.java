/*
+--------------------------------------------------------------------------
|   
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   
|
+---------------------------------------------------------------------------
*/
package uzblog.base.utils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author langhsu
 *
 */
public class URLUtils {
	
	public static String getHost(String link) {
		URL url;
		String host = "";
		try {
			url = new URL(link);
			host = url.getHost();
		} catch (MalformedURLException e) {
		}
		return host;
	}
}
