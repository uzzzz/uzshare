/*
+--------------------------------------------------------------------------
|   
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   
|
+---------------------------------------------------------------------------
*/
package uzblog.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import uzblog.base.context.AppContext;
import uzblog.base.upload.FileRepo;
import uzblog.base.utils.MD5;
import uzblog.modules.user.data.AccountProfile;
import uzblog.shiro.authc.AccountSubject;
import uzblog.web.formatter.StringEscapeEditor;

/**
 * Controller 基类
 * 
 * @author langhsu
 * 
 */
public class BaseController {
	@Autowired
	protected HttpSession session;
	@Autowired
	protected AppContext appContext;
	@Autowired
	protected FileRepo fileRepo;

	@Value("${site.theme:default}")
	private String theme;

	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		/**
		 * 自动转换日期类型的字段格式
		 */
		binder.registerCustomEditor(Date.class,
				new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));

		/**
		 * 防止XSS攻击
		 */
		binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false));
	}

	/**
	 * 获取登录信息
	 * 
	 * @return
	 */
	protected AccountSubject getSubject() {
		return (AccountSubject) SecurityUtils.getSubject();
	}

	protected void putProfile(AccountProfile profile) {
		SecurityUtils.getSubject().getSession(true).setAttribute("profile", profile);
	}

	protected AuthenticationToken createToken(String username, String password) {
		return new UsernamePasswordToken(username, MD5.md5(password));
	}

	protected Pageable wrapPageable() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		int pageSize = ServletRequestUtils.getIntParameter(request, "pageSize", 10);
		int pageNo = ServletRequestUtils.getIntParameter(request, "pn", 1);
		return new PageRequest(pageNo - 1, pageSize);
	}

	/**
	 * 包装分页对象
	 *
	 * @param pn 页码
	 * @param pn 页码
	 * @return
	 */
	protected Pageable wrapPageable(Integer pn, Integer pageSize) {
		if (pn == null || pn == 0) {
			pn = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 10;
		}
		return new PageRequest(pn - 1, pageSize);
	}

	protected String getSuffix(String name) {
		int pos = name.lastIndexOf(".");
		return name.substring(pos);
	}

	protected String view(String view) {
		return "/" + theme + view;
	}

}
