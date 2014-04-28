package kz.trei.acs.filter;

import kz.trei.acs.user.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by alhen on 4/28/14.
 */
public class LoginFilter implements Filter {
    private String contextPath;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        contextPath = filterConfig.getServletContext().getContextPath();

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

//        User user = (User) session.getAttribute("user");
//        if(user==null){
//            request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request,response);
//        }else{
//            filterChain.doFilter(request, response);
//        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
