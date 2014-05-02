package kz.trei.acs.action;

import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.UserDao;
import kz.trei.acs.office.structure.Account1C;
import kz.trei.acs.office.structure.Account1CException;
import kz.trei.acs.user.RoleType;
import kz.trei.acs.user.User;
import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Signup implements Action {
    private static final Logger LOGGER = Logger.getLogger(Signup.class);

    static {
        PropertyManager.load("configure.properties");
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm-password");
        String userRole = request.getParameter("user-role");
        String tableId = request.getParameter("table-id");
        session.setAttribute("username", username);
        session.setAttribute("user-role", userRole);
        session.setAttribute("table-id", tableId);
        session.setAttribute("password", password);
        session.setAttribute("confirm-password", confirmPassword);
        session.removeAttribute("status");
        session.removeAttribute("username-error");
        session.removeAttribute("password-error");
        session.removeAttribute("confirm-password-error");
        session.removeAttribute("user-role-error");
        session.removeAttribute("table-id-error");

        Account1C account1CTableId;
        try {
            account1CTableId = Account1C.createId(tableId);
        } catch (Account1CException e) {
            LOGGER.error("Table ID is not valid: " + e.getMessage());
            session.setAttribute("table-id-error", "form.table-id-error");
            session.setAttribute("error", "form.incomplete");
            return new ActionResult(ActionType.REDIRECT, request.getHeader("referer"));
        }
        DaoFactory daoFactory = DaoFactory.getFactory();
        UserDao userDao = daoFactory.getUserDao();
        if (isPasswordWellFormed(request) && isFormComplete(request)) {
            try {
                User user = new User.Builder(username, password)
                        .tableId(account1CTableId)
                        .role(RoleType.valueOf(userRole.toUpperCase())).build();
                userDao.create(user);
            } catch (DaoException e) {
                LOGGER.error("SQL statement exception execute: " + e.getMessage());
                session.setAttribute("status", "status.create.account.fail");
                return new ActionResult(ActionType.REDIRECT, request.getHeader("referer"));
            }
            session.removeAttribute("username");
            session.removeAttribute("password");
            session.removeAttribute("confirm-password");
            session.removeAttribute("user-role");
            session.removeAttribute("table-id");
            session.setAttribute("status", "status.create.account.success");
            return new ActionResult(ActionType.REDIRECT, request.getHeader("referer"));
        }
        session.setAttribute("error", "form.incomplete");
        return new ActionResult(ActionType.REDIRECT, request.getHeader("referer"));
    }

    private boolean isPasswordWellFormed(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm-password");
        if (password == null || password.isEmpty()) {
            session.setAttribute("password-error", "form.empty");
            return false;
        }
        String passwordRegex = PropertyManager.getValue("form.passwordRegex");
        Pattern passwordPattern = Pattern.compile(passwordRegex);
        Matcher passwordMatcher = passwordPattern.matcher(password);
        if (!passwordMatcher.matches()) {
            session.setAttribute("password-error", "form.password.malformed");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            session.setAttribute("confirm-password-error", "form.password.not-confirmed");
            return false;
        }
        return true;
    }

    private boolean isFormComplete(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String userRole = request.getParameter("user-role");
        String tableId = request.getParameter("table-id");
        boolean isUserNameEmpty = false;
        boolean isPasswordEmpty = false;
        boolean isUserRoleEmpty = false;
        boolean isTableIdEmpty = false;
        if (username == null || username.isEmpty()) {
            isUserNameEmpty = true;
            session.setAttribute("username-error", "form.empty");
        }
        if (password == null || password.isEmpty()) {
            isPasswordEmpty = true;
            session.setAttribute("password-error", "form.empty");
        }
        if (userRole == null || userRole.isEmpty()) {
            isUserRoleEmpty = true;
            session.setAttribute("user-role-error", "form.empty");
        }
        if (tableId == null || tableId.isEmpty()) {
            isTableIdEmpty = true;
            session.setAttribute("table-id-error", "form.empty");
        }
        if (isUserNameEmpty || isPasswordEmpty || isUserRoleEmpty || isTableIdEmpty) {
            return false;
        }
        return true;
    }
}
