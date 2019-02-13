package uzblog.base.context;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppContext {

	/*
	 * 访问的域名host
	 */
	@Value("${server.host}")
	private String host;

	/*
	 * 文件存储-根目录
	 */
	@Value("${site.store.root}")
	private String root;

	/*
	 * 文件存储-压缩目录
	 */
	private String thumbsDir = "/store/thumbs";

	/*
	 * 文件存储-头像目录
	 */
	private String avaDir = "/store/ava";

	/*
	 * 系统配置信息 - 在 StartupListener 类中加载
	 */
	public Map<String, String> config;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getThumbsDir() {
		return thumbsDir;
	}

	public void setThumbsDir(String thumbsDir) {
		this.thumbsDir = thumbsDir;
	}

	public String getAvaDir() {
		return avaDir;
	}

	public void setAvaDir(String avaDir) {
		this.avaDir = avaDir;
	}

	public Map<String, String> getConfig() {
		return config;
	}

	public void setConfig(Map<String, String> config) {
		this.config = config;
	}
}
