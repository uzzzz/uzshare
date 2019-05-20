package uzblog.modules.weixin;

import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import uzblog.boot.RedisService;
import uzblog.modules.utils.Utils;

@Component
public class WxProvider {

	// 公众号： fandyvon@163.com
	private final String appId = "wxaf621fab813e1088";

	private final String appSecret = "ddc295dc7e35bca7317fa08bbc55e29a";

	// 有效期7200秒，开发者必须在自己的服务全局缓存access_token
	private final String access_token_url = String.format(
			"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", appId,
			appSecret);

	// 有效期7200秒，开发者必须在自己的服务全局缓存jsapi_ticket
	private final String jsapi_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";

	@Autowired
	private RestTemplate restTemplate;

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisService redisService;

	private String _access_token() {
		String res = restTemplate.getForObject(access_token_url, String.class);
		Token token = new Gson().fromJson(res, Token.class);
		if (token.errcode == 0) {
			return token.access_token;
		} else {
			return null;
		}
	}

	private String _jsapi_ticket(String access_token) {
		String url = String.format(jsapi_ticket_url, access_token);
		String info = restTemplate.getForObject(url, String.class);
		Ticket ticket = new Gson().fromJson(info, Ticket.class);
		if (ticket.errcode == 0) {
			return ticket.ticket;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private String jsapi_ticket() {
		String token_key = "wx_access_token";
		String ticket_key = "wx_jsapi_ticket";
		String ticket = Utils.tryCatch(() -> (String) redisService.opsForValue().get(ticket_key));
		if (ticket == null) {
			String token = Utils.tryCatch(() -> (String) redisService.opsForValue().get(token_key));
			if (token == null) {
				token = _access_token();
				if (token != null) {
					final String _token = token;
					Utils.tryCatch(() -> redisService.opsForValue().set(token_key, _token, 7000, TimeUnit.SECONDS));
				}
			}
			ticket = _jsapi_ticket(token);
			if (ticket != null) {
				final String _ticket = ticket;
				Utils.tryCatch(() -> redisService.opsForValue().set(ticket_key, _ticket, 7000, TimeUnit.SECONDS));
			}
		}
		return ticket;
	}

	public WxSignature wx_signature(String url) {
		TreeMap<String, String> sortedMap = new TreeMap<String, String>();
		String noncestr = UUID.randomUUID().toString();
		String timestamp = String.valueOf(System.currentTimeMillis());
		sortedMap.put("noncestr", noncestr);
		sortedMap.put("jsapi_ticket", Utils.tryCatch(() -> jsapi_ticket()));
		sortedMap.put("timestamp", timestamp);
		sortedMap.put("url", url);
		Set<Entry<String, String>> set = sortedMap.entrySet();
		StringBuffer sb = new StringBuffer();
		for (Entry<String, String> entry : set) {
			sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		sb.deleteCharAt(sb.length() - 1);
		String signed = DigestUtils.sha1Hex(sb.toString());

		WxSignature signature = new WxSignature();
		signature.appId = appId;
		signature.noncestr = noncestr;
		signature.signature = signed;
		signature.timestamp = timestamp;
		signature.url = url;

		return signature;
	}

	public class Token {
		public int errcode;
		public String errmsg;
		public String access_token;
		public int expires_in;
	}

	public class Ticket {
		public int errcode;
		public String errmsg;
		public String ticket;
		public int expires_in;
	}

	public class WxSignature {
		private String appId;
		private String url;
		private String noncestr;
		private String timestamp;
		private String signature;

		public String getAppId() {
			return appId;
		}

		public void setAppId(String appId) {
			this.appId = appId;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getNoncestr() {
			return noncestr;
		}

		public void setNoncestr(String noncestr) {
			this.noncestr = noncestr;
		}

		public String getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(String timestamp) {
			this.timestamp = timestamp;
		}

		public String getSignature() {
			return signature;
		}

		public void setSignature(String signature) {
			this.signature = signature;
		}

	}
}
