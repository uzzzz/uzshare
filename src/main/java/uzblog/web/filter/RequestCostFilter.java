package uzblog.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

/**
 *  on 2015/7/10.
 */
public class RequestCostFilter implements Filter {
    private Logger log = LoggerFactory.getLogger(RequestCostFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		StopWatch stopWatch = new StopWatch(System.currentTimeMillis() + "");
		stopWatch.start();
		chain.doFilter(request, response);
		stopWatch.stop();

		log.debug(httpRequest.getRequestURI() + " -> request cost - " + stopWatch.getTotalTimeMillis());
    }

    @Override
    public void destroy() {

    }
}
