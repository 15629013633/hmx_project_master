package com.hmx.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.hmx.aop.SysContent;
import com.hmx.utils.logger.LogHelper;
import com.hmx.utils.logger.LogTool;


/**
 * web请求日志
 */
@Component
public class RequestLogFilter implements Filter {


	@Override
	public void destroy() { }

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	@Override
	public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession();

		SysContent.setRequest(request);
		SysContent.setResponse((HttpServletResponse) response);
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String requestURI = request.getRequestURI();

		if (requestURI.indexOf(".") > 0) {
			chain.doFilter(request, response);
			return;
		}
		;

		String[] spUrl = requestURI.split("/");

		request.setAttribute("path", requestURI);

		if (spUrl.length < 2) {
			chain.doFilter(request, response);
			return;
		}

		// 获取请求参数
		Map map = new HashMap();
		Enumeration paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				map.put(paramName, paramValues[0]);
			} else {
				StringBuffer sbuffer = new StringBuffer();
				for (String str : paramValues) {
					sbuffer.append(str).append(" , ");
				}
				map.put(paramName, sbuffer.toString());
			}

		}
		LogHelper.logger().debug("请求Action :" + requestURI);
		LogHelper.logger().debug("请求Ip :" + getRemortIP(request));
		LogHelper.logger().debug("请求参数 :" + LogTool.getParameter(map));
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		LogHelper.logger().info("web请求日志 初始化");
	}

	public String getRemortIP(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}

}
