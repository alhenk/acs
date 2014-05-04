package kz.trei.acs.action;

import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.UserDao;
import kz.trei.acs.office.structure.Account1C;
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
        String email = request.getParameter("email");
        String role = request.getParameter("role");
        String tableId = request.getParameter("table-id");
        session.setAttribute("username", username);
        session.setAttribute("password", password);
        session.setAttribute("confirm-password", confirmPassword);
        session.setAttribute("email", email);
        session.setAttribute("role", role);
        session.setAttribute("table-id", tableId);
        session.removeAttribute("status");
        session.removeAttribute("username-error");
        session.removeAttribute("password-error");
        session.removeAttribute("confirm-password-error");
        session.removeAttribute("role-error");
        session.removeAttribute("table-id-error");

        DaoFactory daoFactory = DaoFactory.getFactory();
        UserDao userDao = daoFactory.getUserDao();
        if (isFormValid(request)) {
            try {
                User user = new User.Builder(username, password)
                        .email(email)
                        .tableId(Account1C.createId(tableId))
                        .role(RoleType.valueOf(role.toUpperCase())).build();
                userDao.create(user);
            } catch (DaoException e) {
                LOGGER.error("SQL statement exception execute: " + e.getMessage());
                session.setAttribute("status", "form.user.create.fail");
                return new ActionResult(ActionType.REDIRECT, request.getHeader("referer"));
            }
            session.removeAttribute("username");
            session.removeAttribute("password");
            session.removeAttribute("email");
            session.removeAttribute("confirm-password");
            session.removeAttribute("user-role");
            session.removeAttribute("table-id");
            session.setAttribute("status", "form.user.create.success");
            return new ActionResult(ActionType.REDIRECT, request.getHeader("referer"));
        }
        session.setAttribute("error", "form.user.incomplete");
        return new ActionResult(ActionType.REDIRECT, request.getHeader("referer"));
    }

    private boolean isFormValid(HttpServletRequest request) {
        return isUserNameValid(request)
                & isPasswordValid(request)
                & isEmailValid(request)
                & isTableIdValid(request)
                & isRoleValid(request);
    }

    //USER NAME VALIDATION
    private boolean isUserNameValid(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        boolean isUserNameValid = true;
        Matcher userNameMatcher = null;
        if (username == null || username.isEmpty()) {
            isUserNameValid = false;
            session.setAttribute("username-error", "form.user.empty");
        } else {
            String userNameRegex = PropertyManager.getValue("form.user.name.regex");
            Pattern userNamePattern = Pattern.compile(userNameRegex,
                    Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
            userNameMatcher = userNamePattern.matcher(username);
            if (userNameMatcher != null && userNameMatcher.matches()) {
                isUserNameValid = true;
            } else {
                isUserNameValid = false;
                session.setAttribute("username-error", "form.user.name.malformed");
            }
        }
        return isUserNameValid;
    }

    //PASSWORD VALIDATION
    private boolean isPasswordValid(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm-password");
        boolean isPasswordValid = false;
        Matcher passwordMatcher = null;
        if (password == null || password.isEmpty()) {
            isPasswordValid = false;
            session.setAttribute("password-error", "form.user.empty");
        } else {
            String passwordRegex = PropertyManager.getValue("form.user.password.regex");
            Pattern passwordPattern = Pattern.compile(passwordRegex,
                    Pattern.UNICODE_CHARACTER_CLASS);
            passwordMatcher = passwordPattern.matcher(password);
            if (passwordMatcher != null && passwordMatcher.matches()) {
                isPasswordValid = true;
            } else {
                isPasswordValid = false;
                session.setAttribute("password-error", "form.user.password.malformed");
            }
            if (isPasswordValid && !password.equals(confirmPassword)) {
                session.setAttribute("confirm-password-error", "form.user.password.not-confirmed");
                isPasswordValid = false;
            }
        }
        return isPasswordValid;
    }

    //EMAIL VALIDATION
    private boolean isEmailValid(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        boolean isEmailValid = false;
        Matcher emailMatcher = null;
        if (email == null || email.isEmpty()) {
            isEmailValid = false;
            session.setAttribute("email-error", "form.user.empty");
        } else {
            String emailRegex = PropertyManager.getValue("form.user.email.regex");
            Pattern emailPattern = Pattern.compile(emailRegex,
                    Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
            emailMatcher = emailPattern.matcher(email);
            if (emailMatcher != null && emailMatcher.matches()) {
                isEmailValid = true;
            } else {
                isEmailValid = false;
                session.setAttribute("email-error", "form.user.email.malformed");
            }
        }
        return isEmailValid;
    }

    //ROLE MATCHER
    private boolean isRoleValid(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String role = request.getParameter("role");
        boolean isRoleValid = false;
        Matcher roleMatcher = null;
        if (role == null || role.isEmpty()) {
            isRoleValid = false;
            session.setAttribute("role-error", "form.user.empty");
        } else {
            String roleRegex = PropertyManager.getValue("form.user.role.regex");
            Pattern userRolePattern = Pattern.compile(roleRegex,
                    Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
            roleMatcher = userRolePattern.matcher(role);
            if (roleMatcher != null && roleMatcher.matches()) {
                isRoleValid = true;
            } else {
                isRoleValid = false;
                session.setAttribute("role-error", "form.user.role.malformed");
            }
        }
        return isRoleValid;
    }

    //TABLE_ID MATCHER
    private boolean isTableIdValid(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String tableId = request.getParameter("table-id");
        boolean isTableIdValid = false;
        Matcher tableIdMatcher = null;
        if (tableId == null || tableId.isEmpty()) {
            isTableIdValid = false;
            session.setAttribute("table-id-error", "form.user.empty");
        } else {
            String tableIdRegex = PropertyManager.getValue("form.user.table-id.regex");
            Pattern tableIdPattern = Pattern.compile(tableIdRegex,
                    Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
            tableIdMatcher = tableIdPattern.matcher(tableId);
            if (tableIdMatcher != null && tableIdMatcher.matches()) {
                isTableIdValid = true;
            } else {
                isTableIdValid = false;
                session.setAttribute("table-id-error", "form.user.table-id.malformed");
            }
        }
        return isTableIdValid;
    }
}
