package uzblog.crawlers;

import com.uzshare.sitemapparser.SitemapParser;
import com.uzshare.sitemapparser.SitemapParserCallback;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WoshipmCrawler {

	private static Log log = LogFactory.getLog(WoshipmCrawler.class);

	@Autowired
	private RefererDao refererSlaveDao;

	@Autowired
	private AsyncTask task;

	public void crawl_all() {
		task.asyncRun(() -> {
			log.warn("woshipm crawl_all start");
			SitemapParser sp = new SitemapParser(new SitemapParserCallback() {
				@Override
				public boolean ignoreSitemap(String sitemap) {
					if (sitemap.startsWith("http://www.woshipm.com/sitemap-pt-post")) {
						return false;
					} else {
						return true;
					}
				}

				@Override
				public void url(String url) {
					try {
						WoshipmCrawler.this.url(url);
					} catch (IOException e) {
						log.error(e.getMessage(), e);
					}
				}
			});
			sp.parseXml("http://www.woshipm.com/sitemap.xml");
			log.warn("woshipm crawl_all end");
		});
	}

	public void crawl_daily() {
		log.warn("woshipm crawl_daily start");
		SitemapParser sp = new SitemapParser(new SitemapParserCallback() {
			@Override
			public boolean ignoreSitemap(String sitemap) {
				if (sitemap.startsWith("http://www.woshipm.com/sitemap-pt-post")) {
					return false;
				} else {
					return true;
				}
			}

			@Override
			public void url(String url) {
				try {
					WoshipmCrawler.this.url(url);
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		});
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String template = "http://www.woshipm.com/sitemap-pt-post-%s.xml";
		String sitemapUrl = String.format(template, sdf.format(new Date()));
		log.warn("woshipm crawl_daily url : " + sitemapUrl);
		sp.parseXml(sitemapUrl);
		log.warn("woshipm crawl_daily end");
	}

	public long url(String url) throws IOException {
		long id = 0;
		Document _doc = Jsoup.connect(url).get();
		String title = _doc.select(".article--title").text();
		String tags = _doc.select(".taglist a").stream().map(e -> e.text()).collect(Collectors.joining(","));
		Elements article = _doc.select("div.grap");

		List<String> thumbnails = new ArrayList<>();
		article.select("img").stream().parallel().forEach(element -> {
			String src = imgUrl(element);
			thumbnails.add(src);
		});
		String c = article.html();

		// post uzshare
		id = task.postBlog(7, title, c, thumbnails.size() > 0 ? thumbnails.get(0) : "", tags);
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
