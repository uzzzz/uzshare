package uzblog.web.controller.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import uzblog.modules.blog.dao.RefererDao;
import uzblog.modules.blog.entity.Referer;
import uzblog.web.controller.BaseController;

@Controller
@RequestMapping("/_p")
public class ProxyController extends BaseController {

	@Autowired
	private RefererDao refererDao;

	@RequestMapping()
	public void image(HttpServletRequest request, HttpServletResponse response) {
		String url = request.getQueryString();

		InputStream in = null;
		OutputStream out = null;
		try {
			URL u = new URL(url);
			String host = u.getHost();
			String protocol = u.getProtocol();
			String ref = null;
			Referer referer = refererDao.findByHost(host);
			if (referer != null && StringUtils.hasLength(referer.getReferer())) {
				ref = protocol + "://" + referer.getReferer();
			} else {
				ref = protocol + "://" + host;
			}
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			conn.setRequestProperty("referer", ref);
			in = conn.getInputStream();
			response.setContentType(conn.getContentType());
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
