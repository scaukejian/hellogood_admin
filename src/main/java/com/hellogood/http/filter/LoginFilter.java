package com.hellogood.http.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.hellogood.utils.RSAUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hellogood.http.vo.EmployeeVO;
import com.hellogood.service.ActionService;
import com.hellogood.service.EmployeeService;

public class LoginFilter implements Filter {

    Logger logger = LoggerFactory.getLogger(LoginFilter.class);

	private FilterConfig filterConfig;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String url = req.getServletPath();
        logger.info("filter URL：" + url);
		if (url.endsWith(".css")
                || url.endsWith(".js")
                || url.contains("/img/")
                || url.contains("/images/")
                || url.contains("/pages/login.jsp")
                || url.contains("/verifyCode/get.do")
                || url.contains("/app/")//帮助、关于我们、联系我们
                || url.contains("/validate/doLogin.do")
                || url.contains("/activity/activity.jsp")//活动链接页面
                || url.contains("/activity/party-register.jsp")//活动链接页面
                || url.contains("/activity/register_success.jsp")//活动链接页面
                || url.contains("/activity/party-register-success.jsp")//活动链接页面
                || url.contains("/activity/uploadEditor.do")//ckeditor编辑器上传图片
                || url.contains("/activity/getCode.do")//获取验证码
                || url.contains("/register")//注册活动链接页面
                || url.contains("/official")//官网
                || url.contains("/photo/uploadWhenRegister.do")//邀请注册上传相片
				|| url.contains("/select/") //页面初始化
				|| url.contains("/editor/") //编辑器
				) {
			chain.doFilter(req, resp);
		} else {
            EmployeeVO employeeVO = (EmployeeVO) req.getSession().getAttribute("employee");
            //session 失效 获取cookie 中员工信息自动登录
            if(employeeVO == null){
            	employeeVO=autoLogin(req , resp);
            }
			if (employeeVO == null) {
					resp.sendRedirect(req.getContextPath() + "/pages/login.jsp");
                return;
            } else {
				chain.doFilter(req, resp);
			}
		}
	}

	private EmployeeVO autoLogin(HttpServletRequest request , HttpServletResponse response) {
		logger.info("自动登陆。。。");
		Cookie[] cookies = request.getCookies();
		if(cookies == null){
			logger.info("cookies 中没有员工信息");
			return null;
		}
		EmployeeVO employeeVO =null;
		for (Cookie cookie : cookies) {
			if(StringUtils.equals(cookie.getName(),"emp")){
				WebApplicationContext ctx =   
						WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext()); 
				EmployeeService employeeService = ctx.getBean(EmployeeService.class);
				String[] userParam = null;
				try {
					userParam = RSAUtil.decrypt(cookie.getValue()).split(",");
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("解密失败 --》 跳转至登录页面  ");
					return null;
				}
				String clientIp = userParam[2];
				if(!StringUtils.equals(clientIp, getRemoteHost(request))){
					logger.info("cookies IP不一致");
					return null;
				}
				employeeVO = employeeService.autoLogin(userParam[0] , userParam[1]);
				if(employeeVO != null){
					request.getSession().setAttribute("employee", employeeVO);
					// 当前用户拥有的一级菜单
					request.getSession().setAttribute("actions", 
							ctx.getBean(ActionService.class).getEmployeeOneActions("1", employeeVO.getId()));
				}
				logger.info(employeeVO.getName()+"登陆成功");
				break;
			}
		}
		return employeeVO;
	}

	@Override
	public void destroy() {

	}

	public String getRemoteHost(javax.servlet.http.HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getRemoteAddr();
		}
		return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	}

}
