/*
+--------------------------------------------------------------------------
|   
|   ========================================
|    
|   
|
+---------------------------------------------------------------------------
*/
package uzblog.base.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uzblog.base.lang.BlogException;

/**
 * 
 * 
 *
 */
public class HttpUtils {

	private static Log log = LogFactory.getLog(HttpUtils.class);

	public static HttpClient getClient() {
		HttpClient client = new HttpClient();
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8"); 
		return client;
	}
	
	public static String post(String url, Map<String, String> params) throws HttpException, IOException {
		HttpClient client = getClient();
		
    	PostMethod post = new PostMethod(url);
    	
    	for (Map.Entry<String, String> p : params.entrySet()) {
    		post.addParameter(p.getKey(), p.getValue());
    	}
    	
    	int status = client.executeMethod(post);

    	if (status != HttpStatus.SC_OK) {
    		throw new BlogException("该地址请求失败");
    	}
    	return post.getResponseBodyAsString();
	}

	public static HttpURLConnection autoRedirects(URL url, String ref) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestProperty("User-agent", "Mozilla/4.0");
		conn.setRequestProperty("referer", ref);
		int code = conn.getResponseCode();
		if (code == HttpURLConnection.HTTP_MOVED_PERM
				|| code == HttpURLConnection.HTTP_MOVED_TEMP) {
			String location = conn.getHeaderField("Location");
			conn.disconnect();
			String urlString = url.toString();
			log.info(code + " 跳转：" + urlString + " ---> " + location);
			return autoRedirects(new URL(location), urlString);
		} else {
			return conn;
		}
	}
}
