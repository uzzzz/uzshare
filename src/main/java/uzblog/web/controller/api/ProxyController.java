package uzblog.web.controller.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import uzblog.web.controller.BaseController;

@Controller
@RequestMapping("/_p")
public class ProxyController extends BaseController {

	@RequestMapping()
	public void image(HttpServletRequest request, HttpServletResponse response) {
		String url = request.getQueryString();

		InputStream in = null;
		OutputStream out = null;
		try {
			URL u = new URL(url);
			String host = u.getHost();
			String protocol = u.getProtocol();
			String referer = protocol + "://csdn.net";
			System.out.println(referer);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			conn.setRequestProperty("referer", referer);
			in = conn.getInputStream();
			out = response.getOutputStream();
			IOUtils.copy(in, out);
			out.flush();
		} catch (Exception e) {
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
	}

}
