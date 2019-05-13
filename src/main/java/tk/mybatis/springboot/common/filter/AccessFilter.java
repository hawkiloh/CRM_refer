package tk.mybatis.springboot.common.filter;

import tk.mybatis.springboot.modules.sys.vo.UserVo;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet Filter implementation class AccessFilter
 */
public class AccessFilter implements Filter {

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(
            ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //类型转换
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        //1. 获取登录用户信息
        //2. 如果没有登录用户信息，重定向到 login.html
        String path = req.getRequestURI();
        //System.out.println(path);

        if (path.contains("/toLogin") || path.equalsIgnoreCase("/") || path.endsWith("png")
                || path.endsWith("jpg") || path.endsWith("js") || path.endsWith("css")
                || path.endsWith("ico") || path.equalsIgnoreCase("/user/login")) {
            //设置HTTP协议头，避免浏览器缓存html页面
            res.addHeader("Cache-Control", "no-cache");
            chain.doFilter(request, response);
            return;
        }

        UserVo user = (UserVo) req.getSession().getAttribute("user");
        if (user == null) {
            //如果user为null则表示没有登录
            //重定向到 login.html
            //采用绝对路径重定向！可以避免错误
            String login = req.getContextPath() + "/";
            // /note/login.html
            res.sendRedirect(login);
            return;
        }
        //设置HTTP协议头，避免浏览器缓存html页面
        res.addHeader("Cache-Control", "no-cache");
        chain.doFilter(request, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
    }

}
