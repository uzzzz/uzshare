package uzblog.base.utils;

import com.redfin.sitemapgenerator.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uzblog.crawlers.CsdnCrawler;
import uzblog.crawlers.OschinaCrawler;
import uzblog.crawlers.WoshipmCrawler;
import uzblog.modules.blog.service.PostService;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Component
@Profile({"prod"})
public class ScheduledProdTask {

    private static Log log = LogFactory.getLog(ScheduledProdTask.class);

    @Value("${site.store.root}")
    private String uzshareStaticPath;

    @Value("${site.host}")
    private String host;

    @Autowired
    private CsdnCrawler csdnCrawler;

    @Autowired
    private WoshipmCrawler woshipmCrawler;

    @Autowired
    private OschinaCrawler oschinaCrawler;

    @Autowired
    private PostService postService;

    @Scheduled(initialDelay = 1000, fixedDelay = 1000 * 60 * 60 * 5)
    public void crawl_woshipm_daily() throws IOException {
        log.warn("crawl woshipm daily @Scheduled");
        woshipmCrawler.crawl_daily();
    }

    @Scheduled(initialDelay = 1000 * 60 * 56, fixedDelay = 1000 * 60 * 60)
    public void crawl_iot() throws IOException {
        log.warn("crawl iot @Scheduled");
        csdnCrawler.iot();
    }

    @Scheduled(initialDelay = 1000 * 60 * 8, fixedDelay = 1000 * 60 * 60)
    public void crawl_python() throws IOException {
        log.warn("crawl python @Scheduled");
        csdnCrawler.python();
    }

    @Scheduled(initialDelay = 1000 * 60 * 16, fixedDelay = 1000 * 60 * 60)
    public void crawl_5g() throws IOException {
        log.warn("crawl 5g @Scheduled");
        csdnCrawler._5g();
    }

    @Scheduled(initialDelay = 1000 * 60 * 24, fixedDelay = 1000 * 60 * 60)
    public void crawl_blockchain() throws IOException {
        log.warn("crawl blockchain @Scheduled");
        csdnCrawler.blockchain();
        oschinaCrawler.blockchain();
    }

    @Scheduled(initialDelay = 1000 * 60 * 32, fixedDelay = 1000 * 60 * 60)
    public void crawl_careerlife() throws IOException {
        log.warn("crawl careerlife @Scheduled");
        csdnCrawler.careerlife();
    }

    @Scheduled(initialDelay = 1000 * 60 * 40, fixedDelay = 1000 * 60 * 60)
    public void crawl_ai() throws IOException {
        log.warn("crawl ai @Scheduled");
        csdnCrawler.ai();
        oschinaCrawler.ai();
    }

    @Scheduled(initialDelay = 1000 * 60 * 48, fixedDelay = 1000 * 60 * 60)
    public void crawl_datacloud() throws IOException {
        log.warn("crawl datacloud @Scheduled");
        csdnCrawler.datacloud();
        oschinaCrawler.datacloud();
    }

    // sitemap
    @Scheduled(initialDelay = 1000, fixedDelay = 1000 * 60 * 60 * 12)
    public void rewritesitemapxml() throws IOException {
        log.warn("rewritesitemapxml start");

        List<Long> ids = postService.findAllIds();
        String baseUrl = "https://" + host;
        String localRoot = uzshareStaticPath;
        WebSitemapGenerator wsgGzip = WebSitemapGenerator.builder(baseUrl, new File(localRoot)).gzip(true).build();

        for (Long id : ids) {
            WebSitemapUrl url = new WebSitemapUrl.Options(baseUrl + "/view/" + id).priority(0.9)
                    .changeFreq(ChangeFreq.DAILY).build();
            wsgGzip.addUrl(url);
        }

        List<File> viewsGzip = wsgGzip.write();

        // 构造 sitemap_index 生成器
        W3CDateFormat dateFormat = new W3CDateFormat(W3CDateFormat.Pattern.DAY);
        SitemapIndexGenerator sitemapIndexGenerator = new SitemapIndexGenerator.Options(baseUrl,
                new File(localRoot + "/sitemap_index.xml")).autoValidate(true).dateFormat(dateFormat).build();

        viewsGzip.forEach(e -> {
            try { // 组装 sitemap 文件 URL 地址
                String url = baseUrl + "/" + e.getName();
                sitemapIndexGenerator.addUrl(url);
            } catch (MalformedURLException mue) {
                mue.printStackTrace();
            }
        });
        // 生成 sitemap_index 文件
        sitemapIndexGenerator.write();

        log.warn("rewritesitemapxml end : OK");
    }
}