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
        //General
        groups.put("GET/main", all);
        groups.put("POST/sign-in", all);
        groups.put("GET/sign-out", authorized);
        groups.put("GET/dashboard", authorized);
        groups.put("POST/set-language", all);
        //User
        groups.put("GET/create-user", administrator);
        groups.put("POST/create-user", administrator);
        groups.put("GET/cancel-create-user", all);
        groups.put("GET/edit-user", administrator);
        groups.put("POST/edit-user", administrator);
        groups.put("GET/cancel-edit-user", all);
        groups.put("GET/delete-user", administrator);
        groups.put("GET/user-list", administrator);
        //RfidTag
        groups.put("GET/create-rfidtag", administrator);
        groups.put("POST/create-rfidtag", administrator);
        groups.put("GET/cancel-create-rfidtag", all);
        groups.put("GET/edit-rfidtag", administrator);
        groups.put("POST/edit-rfidtag", administrator);
        groups.put("GET/cancel-edit-rfidtag", all);
        groups.put("GET/delete-rfidtag", administrator);
        groups.put("GET/rfidtag-list", administrator);
        //Employee
        groups.put("GET/create-employee", administrator);
        groups.put("POST/create-employee", administrator);
        groups.put("GET/cancel-create-employee", all);
        groups.put("GET/edit-employee", authorized);
        groups.put("POST/edit-employee", authorized);
        groups.put("GET/cancel-edit-employee", all);
        groups.put("GET/delete-employee", administrator);
        groups.put("GET/employee-list", authorized);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String pathInfo = request.getPathInfo();
        String action = request.getMethod() + request.getPathInfo();
        EnumSet<RoleType> group = groups.get(action);
        User user = (User) session.getAttribute("user");
        RoleType role = RoleType.UNREGISTERED;

        if (user != null) role = user.getRole();
        if (group == null || group.contains(role)) {
            LOGGER.debug("all clear");
            filterChain.doFilter(request, response);
            return;
        }
        LOGGER.debug("Security filter makes it forward to main page");
        request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
    }
}
