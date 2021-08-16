package uzblog.crawlers;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import uzblog.base.utils.HttpUtils;
import uzblog.modules.blog.dao.RefererDao;
import uzblog.modules.blog.entity.Referer;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

@Component
public class GithubFileUploader {

    private static Log log = LogFactory.getLog(GithubFileUploader.class);

    private String imgHost = "img3.ibz.bz";

    @Autowired
    private RefererDao refererDao;

    @Autowired
    private RestTemplate rest;

    public String upload(String imageOriginUrl) throws IOException {

        InputStream in = null;
        try {
            URL u = new URL(imageOriginUrl);

            String protocol = u.getProtocol();
            String host = u.getHost();
            String ref;
            Referer referer = refererDao.findByHost(host);
            if (referer != null && StringUtils.hasLength(referer.getReferer())) {
                ref = protocol + "://" + referer.getReferer();
            } else {
                ref = protocol + "://" + host;
            }
            HttpURLConnection conn = HttpUtils.autoRedirects(u, ref);
            in = conn.getInputStream();
            byte[] bytes = IOUtils.toByteArray(in);
            String content = Base64.getEncoder().encodeToString(bytes);
            String filename = DigestUtils.md5DigestAsHex(bytes) + ".png";
            String path = host + "/" + filename;
            String token = "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";
            // 用户名、库名、路径
            String url = "https://api.github.com/repos/xxcode/" + imgHost + "/contents/docs/" + path;
            GithubFileObject githubFileObject = createGithubFileObject(imageOriginUrl, content, "GithubFileUploader", "GithubFileUploader@local.mbp");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "token " + token);
            HttpEntity<GithubFileObject> entity = new HttpEntity<>(githubFileObject, headers);
            try {
                ResponseEntity<String> responseEntity = rest.exchange(url, HttpMethod.PUT, entity, String.class);
                String resp = responseEntity.getBody();
                log.info(resp);
                return "https://" + imgHost + "/" + path;
            } catch (HttpClientErrorException hee) {
                int code = hee.getStatusCode().value();
                if (code == 422) {
                    String body = hee.getResponseBodyAsString();
                    log.error("status code: " + code + " body: " + body);
                    return "https://" + imgHost + "/" + path;
                } else {
                    throw hee;
                }
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private GithubFileObject createGithubFileObject(String message, String content, String name, String email) {
        GithubFileObject object = new GithubFileObject();
        object.message = message;
        object.content = content;
        Committer committer = new Committer();
        committer.name = name;
        committer.email = email;
        object.committer = committer;
        return object;
    }

    class GithubFileObject implements Serializable {
        public String message;
        public String content;
        public Committer committer;
    }

    class Committer implements Serializable {
        public String name;
        public String email;
    }
}