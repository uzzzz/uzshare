package uzblog.boot;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

import uzblog.base.context.AppContext;
import uzblog.web.interceptor.BaseInterceptor;

/**
 *   on 2017/10/13.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private BaseInterceptor baseInterceptor;
    @Autowired
    private FastJsonHttpMessageConverter fastJsonHttpMessageConverter;
    @Autowired
    private AppContext appContext;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(false);
    }

    /**
     * Add intercepter
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(baseInterceptor).addPathPatterns("/**").excludePathPatterns("/dist/**", "/store/**", "/static/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/dist/**").addResourceLocations("classpath:/static/dist/");
        registry.addResourceHandler("/theme/**").addResourceLocations("classpath:/static/theme/");
        registry.addResourceHandler("/store/**").addResourceLocations(getStorePath());
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(fastJsonHttpMessageConverter);
    }

    private String getStorePath() {
        StringBuilder builder = new StringBuilder("file:");

        builder.append(appContext.getRoot());

        if (!appContext.getRoot().endsWith(File.separator)) {
            builder.append(File.separator);
        }

        builder.append("store/");

        return builder.toString();
    }
}
