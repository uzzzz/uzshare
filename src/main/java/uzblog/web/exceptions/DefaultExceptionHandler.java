/*
+--------------------------------------------------------------------------
|   
|   ========================================
|    
|   
|
+---------------------------------------------------------------------------
*/
package uzblog.web.exceptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import uzblog.base.data.Data;

/**
 * 异常处理
 * 
 *
 */
@Component
public class DefaultExceptionHandler implements HandlerExceptionResolver {

	private String errorView = "/error";

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {

		ModelAndView view = null;
		String ret = ex.getMessage();

		if (isAjax(handler)) {
			try {
				response.setContentType("application/json;charset=UTF-8");
				response.getWriter().print(JSON.toJSONString(Data.failure(ret)));
			} catch (IOException e) {
				// do something
			}

			view = new ModelAndView();
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("error", ret);
			map.put("base", request.getContextPath());
			view = new ModelAndView(errorView, map);
		}
		return view;
	}

	/**
	 * 判断是否 ajax 调用
	 * 
	 * @param handler
	 * @return
	 */
	private boolean isAjax(Object handler) {
		if (handler != null && handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			ResponseBody responseBodyAnn = AnnotationUtils.findAnnotation(handlerMethod.getMethod(),
					ResponseBody.class);
			return responseBodyAnn != null;
		}

		return false;
	}

}
