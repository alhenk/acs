package kz.trei.acs.filter;

import kz.trei.acs.user.RoleType;
import kz.trei.acs.user.User;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class SecurityFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(SecurityFilter.class);
    private Map<String, EnumSet<RoleType>> groups = new HashMap<String, EnumSet<RoleType>>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        EnumSet<RoleType> all = EnumSet.of(RoleType.UNREGISTERED,
                RoleType.ADMINISTRATOR, RoleType.SUPERVISOR, RoleType.EMPLOYEE);
        EnumSet<RoleType> authorized = EnumSet.of(RoleType.ADMINISTRATOR,
                RoleType.SUPERVISOR, RoleType.EMPLOYEE);
        EnumSet<RoleType> administrator = EnumSet.of(RoleType.ADMINISTRATOR);
        EnumSet<RoleType> supervisor = EnumSet.of(RoleType.SUPERVISOR);
        EnumSet<RoleType> employee = EnumSet.of(RoleType.EMPLOYEE);

        groups.put("GET/main", all);
        groups.put("POST/set-language", all);
        groups.put("POST/sign-in", all);
        groups.put("POST/sign-up", administrator);
        groups.put("GET/sign-out", authorized);
        groups.put("GET/dashboard", authorized);
        groups.put("GET/create-account", administrator);
        groups.put("GET/user-list", administrator);
        groups.put("GET/error", all);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String pathInfo = request.getPathInfo();
        String action = request.getMethod() + request.getPathInfo();
        EnumSet<RoleType> group = groups.get(action);
        User user = (User) session.getAttribute("user");
        RoleType userRole = RoleType.UNREGISTERED;
        if (user != null) userRole = user.getRole();
        if (group == null || group.contains(userRole)) {
            filterChain.doFilter(request, response);
            return;
        }
        LOGGER.debug("Security filter make it forward to main page");
        request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
    }

    @Override
    public void destroy() {

    }
}
