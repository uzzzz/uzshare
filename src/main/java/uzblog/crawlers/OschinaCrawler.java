package uzblog.crawlers;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uzblog.base.utils.AsyncTask;
import uzblog.modules.blog.dao.RefererDao;
import uzblog.modules.blog.entity.Referer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OschinaCrawler {

	private static Log log = LogFactory.getLog(OschinaCrawler.class);

	@Autowired
	private RefererDao refererSlaveDao;

	@Autowired
	private AsyncTask task;

	public void crawl_all(int cid, String classification, int start, int end) throws IOException {
		// 页数：start ~ end
		log.error("crawl_all start classification : " + classification);
		String template = "https://www.oschina.net/blog/widgets/_blog_index_newest_list?classification=%s&p=%d&type=ajax";
		for (int i = start; i <= end; i++) {
			String url = String.format(template, classification, i);
			Document _doc = Jsoup.connect(url).get();
			_doc.select("a.header").parallelStream().forEach(a -> {
				String _url = a.attr("href");
				url(cid, _url);
			});
		}
		log.error("crawl_all end classification : " + classification);
	}

	public void blockchain() throws IOException {
		String url = "https://www.oschina.net/blog/widgets/_blog_index_newest_list?classification=5765988&type=ajax";
		Document _doc = Jsoup.connect(url).get();
		_doc.select("a.header").parallelStream().forEach(a -> {
			String _url = a.attr("href");
			url(2, _url);
		});
	}

	public void ai() throws IOException {
		String url = "https://www.oschina.net/blog/widgets/_blog_index_newest_list?classification=5611447&type=ajax";
		Document _doc = Jsoup.connect(url).get();
		_doc.select("a.header").parallelStream().forEach(a -> {
			String _url = a.attr("href");
			url(4, _url);
		});
	}

	public void datacloud() throws IOException {

		{ // 大数据
			String dataurl = "https://www.oschina.net/blog/widgets/_blog_index_newest_list?classification=5593654&type=ajax";
			Document _doc = Jsoup.connect(dataurl).get();
			_doc.select("a.header").parallelStream().forEach(a -> {
				String _url = a.attr("href");
				url(5, _url);
			});
		}

		{ // 云计算
			String cloudurl = "https://www.oschina.net/blog/widgets/_blog_index_newest_list?classification=428639&type=ajax";
			Document _doc = Jsoup.connect(cloudurl).get();
			_doc.select("a.header").parallelStream().forEach(a -> {
				String _url = a.attr("href");
				url(5, _url);
			});
		}
	}

	public long url(int cid, String url) {
		long id = 0;
		try {
			Document _doc = Jsoup.connect(url).get();
			Elements titleE = _doc.select("h2.header");
			titleE.select("div.label").remove();
			String title = titleE.text();
			Elements article = _doc.select("#articleContent");
			String tags = _doc.select("div.tags a").stream().map(e -> e.text()).collect(Collectors.joining(","));

			article.select("script, div.ad-wrap, a[data-traceid]").remove();

			List<String> thumbnails = new ArrayList<>();
			article.select("img").stream().parallel().forEach(element -> {
				String src = imgUrl(element);
				thumbnails.add(src);
			});

			String c = article.html();
			// post uzshare
			id = task.postBlog(cid, title, c, thumbnails.size() > 0 ? thumbnails.get(0) : "", tags);
		} catch (IOException ioe) {
		}
		return id;
	}

	private String imgUrl(Element element) {

		String src = element.absUrl("src");

		if (StringUtils.isNotBlank(src)) {
			List<Referer> referers = refererSlaveDao.findAll();
			boolean b = false;
			for (Referer r : referers) {
				String host = r.getHost();
				if (src.startsWith("http://" + host) //
						|| src.startsWith("https://" + host)) {
					b = true;
					break;
				}
			}
			if (b) {
				src = "https://uzshare.com/_p?" + src;
				element.attr("src", src);
			}
		}

		return src;
	}

}
