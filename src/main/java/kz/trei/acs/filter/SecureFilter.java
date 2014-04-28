package kz.trei.acs.filter;

import kz.trei.acs.user.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SecureFilter implements Filter {
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
        String pathInfo = request.getPathInfo();
        if (!isSecure(pathInfo)) {
            filterChain.doFilter(request, response);
            return;
        }
        User user = (User) session.getAttribute("user");
        if (user == null) {
            request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean isSecure(String path) {
        if (path == null || path.equals("/main")
                || path.equals("/sign-in")
                || path.equals("/set-language")
                || path.equals("/sign-out")
                || path.equals("/error")) {
            return false;
        }
        return true;
    }

    @Override
    public void destroy() {

    }
}
