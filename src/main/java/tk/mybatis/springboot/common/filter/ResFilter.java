package tk.mybatis.springboot.common.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by 秦建平 on 2018/2/27.
 */
public class ResFilter implements Filter {
    private final static double DEFAULT_USERID = Math.random() * 1000000.0;
    private Logger logger = Logger.getLogger(ResFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("filter======初始化");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        //MDC.put("loginName","hhh");
        //MDC.put("roleName","22");
        //MDC.put("ip", "33");//获取ip
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            //MDC.remove("loginName");
            // MDC.remove("roleName");
            //MDC.remove("ip");
        }
    }

    public void destroy() {
        System.out.println("filter======摧毁");
    }

}
