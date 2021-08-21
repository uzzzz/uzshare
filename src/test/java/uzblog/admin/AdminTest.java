package uzblog.admin;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import uzblog.BootApplication;

@SpringBootTest(classes = BootApplication.class)
public class AdminTest {

	@Autowired
	private RequestMappingHandlerMapping requestMappingHandlerMapping;

	@Test
	public void requestMapping() {

		String format = "%s %s %s %s";
		Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
		for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
			RequestMappingInfo info = m.getKey();
			HandlerMethod method = m.getValue();
			PatternsRequestCondition p = info.getPatternsCondition();
			String url = "";
			for (String u : p.getPatterns()) {
				url += (":" + u);
			}
			String className = method.getMethod().getDeclaringClass().getName();
			String methodName = method.getMethod().getName();
			RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
			String type = methodsCondition.toString();
			if (type != null && type.startsWith("[") && type.endsWith("]")) {
				type = type.substring(1, type.length() - 1);
			}
			System.out.println(String.format(format, url, type, className, methodName));
		}
	}
}
