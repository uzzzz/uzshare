package uzblog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import uzblog.boot.RedisService;

/**
 * SprintBootApplication
 */
@SpringBootApplication
@EnableAsync
@EnableAutoConfiguration(exclude = { ErrorMvcAutoConfiguration.class })
@EnableCaching
public class BootApplication extends SpringBootServletInitializer implements ErrorPageRegistrar {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BootApplication.class);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(BootApplication.class, args);
	}

	@Autowired
	private RestTemplateBuilder builder;

	@Bean
	public RestTemplate restTemplate() {
		return builder.build();
	}

	@Bean
	public <V> RedisService<V> redisService(RedisConnectionFactory factory) {
		RedisService<V> redis = new RedisService<V>(factory);
		redis.afterPropertiesSet();
		return redis;
	}

	@Override
	public void registerErrorPages(ErrorPageRegistry registry) {
		ErrorPage[] errorPages = new ErrorPage[2];
		errorPages[0] = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500.html");
		errorPages[1] = new ErrorPage(HttpStatus.NOT_FOUND, "/error/404.html");
		errorPages[1] = new ErrorPage(HttpStatus.BAD_REQUEST, "/error/400.html");
		registry.addErrorPages(errorPages);
	}
}