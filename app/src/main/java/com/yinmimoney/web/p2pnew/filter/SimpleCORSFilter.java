package com.yinmimoney.web.p2pnew.filter;

import cc.s2m.util.IpUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SimpleCORSFilter implements Filter {

	private static final Logger LOGGER = LogManager.getLogger(SimpleCORSFilter.class);

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        String method = ((HttpServletRequest) req).getMethod();
        if("HEAD".equals(method)){
            return;
        }
        try {
			HttpServletRequest hreq = (HttpServletRequest) req;
			HttpServletResponse hresp = (HttpServletResponse) res;
			hresp.setHeader("Access-Control-Allow-Credentials", "true");
			String origin = hreq.getHeader("Origin");
			if (StringUtils.isNotBlank(origin)) {
				hresp.setHeader("Access-Control-Allow-Origin", origin);
			}
			// 跨域 Header
			hresp.setHeader("Access-Control-Allow-Methods", "*");
			hresp.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, token, X-Requested-With");
			// 浏览器是会先发一次options请求，如果请求通过，则继续发送正式的post请求
			// 配置options的请求返回
			if (hreq.getMethod().equals("OPTIONS")) {
				hresp.setStatus(HttpStatus.SC_OK);
				hresp.getWriter().write("OPTIONS returns OK");
				return;
			}
			hresp.setHeader("Access-Control-Max-Age", "3600");
			// Filter 只是链式处理，请求依然转发到目的地址。
			chain.doFilter(req, res);
		} catch (Exception e) {
			LOGGER.error("异常：", e);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}
