package ccb.scontact.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import ccb.scontact.hibernate.dao.IAccountDao;
import ccb.scontact.hibernate.dao.impl.AccountDaoImpl;
import ccb.scontact.pojo.AccountInfo;
import ccb.scontact.utils.Bog;

public class RequestFilter implements Filter{

	public static boolean enableFilter = false;
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		String dispstr = "/services/error/message";
		Cookie[] cookies = null;
		String uri = null;
		HttpServletRequest req = null;
		String cookievalue = null;
		
		if ( request instanceof HttpServletRequest ){
			req = (HttpServletRequest) request;
			cookies = req.getCookies();
			uri = req.getRequestURI();
			Bog.info("<requesturi>"+uri+ "<cookie>"+getCookieString(cookies));
		}
		if ( !enableFilter ){
			chain.doFilter(request, response);
			return;
		} 
		if ( req != null ){
			if ( !uri.contains("login") ){
				if ( cookies != null && cookies.length > 0 ){
					String name = cookies[0].getName();
					cookievalue = cookies[0].getValue();
					String[] userinfo = cookievalue.split("_._");
					if ( userinfo != null && userinfo.length == 2 ){
						String uid = userinfo[0];
						String ucookie = userinfo[1];
						IAccountDao iad = new AccountDaoImpl();
						try{
							AccountInfo u = (AccountInfo) iad.getAccountInfo(Long.valueOf(uid));
							if ( u != null && ucookie.equals(u.getCookie()) ){
								chain.doFilter(request, response);
								return;
							}
						}catch (Exception e) {
							e.printStackTrace();
							Bog.error("<requesturi>"+uri+ "<cookie>"+getCookieString(cookies)+ "<exception>"+e.getMessage());
						}
					}
				}
			} else {//登录接口不拦截
				chain.doFilter(request, response);
				return;
			}
			if ( uri.contains("Filter") ){
				chain.doFilter(request, response);
				return;
			}
		}
		RequestDispatcher disp = req.getRequestDispatcher(dispstr);
		disp.forward(request, response);
	}

	String getCookieString (Cookie[] s){
		if ( s == null )return null;
		StringBuffer sb = new StringBuffer();
		for ( Cookie c : s ){
			sb.append(c.getValue());
		}
		return sb.toString();
	}
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

}
