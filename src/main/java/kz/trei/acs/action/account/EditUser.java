package kz.trei.acs.action.account;

import kz.trei.acs.action.Action;
import kz.trei.acs.action.ActionResult;
import kz.trei.acs.action.ActionType;
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


public class EditUser implements Action {
    private static final Logger LOGGER = Logger.getLogger(EditUser.class);

    static {
        PropertyManager.load("configure.properties");
    }

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String id = request.getParameter("id");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String role = request.getParameter("role");
        String tableId = request.getParameter("table-id");
        DaoFactory daoFactory = DaoFactory.getFactory();
        UserDao userDao = daoFactory.getUserDao();
        User originalUser;
        String password = null;
        try {
            originalUser = userDao.findById(Long.valueOf(id));
        } catch (DaoException e) {
            LOGGER.error("Find by ID exception: " + e.getMessage());
            return new ActionResult(ActionType.REDIRECT, "error?error=error.db.find-by-id");
        }
        password = originalUser.getPassword();
        session.setAttribute("id", id);
        session.setAttribute("username", username);
        session.setAttribute("password", password);
        session.setAttribute("email", email);
        session.setAttribute("role", role);
        session.setAttribute("table-id", tableId);
        if (isFormValid(request)) {
            try {
                User user = new User.Builder(username, password)
                        .id(Long.valueOf(id))
                        .email(email)
                        .tableId(Account1C.createId(tableId))
                        .role(RoleType.valueOf(role.toUpperCase())).build();
                userDao.update(user);
                LOGGER.debug("user ->" + user);
            } catch (DaoException e) {
                LOGGER.error("SQL statement exception execute: " + e.getMessage());
                request.setAttribute("status", "form.user.create.fail");
                return new ActionResult(ActionType.REDIRECT, "edit-user" + fetchParameters(request));
            }
            session.removeAttribute("id");
            session.removeAttribute("username");
            session.removeAttribute("password");
            session.removeAttribute("email");
            session.removeAttribute("confirm-password");
            session.removeAttribute("user-role");
            session.removeAttribute("table-id");
            request.setAttribute("status", "form.user.edit.success");
            return new ActionResult(ActionType.REDIRECT, "user-list" + fetchParameters(request));
        }

        request.setAttribute("error", "form.user.incomplete");
        return new ActionResult(ActionType.REDIRECT, "edit-user" + fetchParameters(request));
    }

    private String fetchParameters(HttpServletRequest request) {
        StringBuilder parameters = new StringBuilder("?");
        String id = request.getParameter("id");
        String userNameError = (String) request.getAttribute("username-error");
        String passwordError = (String) request.getAttribute("password-error");
        String confirmPasswordError = (String) request.getAttribute("confirm-password-error");
        String emailError = (String) request.getAttribute("email-error");
        String roleError = (String) request.getAttribute("role-error");
        String tableIdError = (String) request.getAttribute("table-id-error");
        String status = (String) request.getAttribute("status");
        String error = (String) request.getAttribute("error");
        if (id != null) {
            parameters.append("&id=" + id);
        }
        if (userNameError != null) {
            parameters.append("&username-error=" + userNameError);
        }
        if (passwordError != null) {
            parameters.append("&password-error=" + passwordError);
        }
        if (confirmPasswordError != null) {
            parameters.append("&confirm-password-error=" + confirmPasswordError);
        }
        if (emailError != null) {
            parameters.append("&email-error=" + emailError);
        }
        if (roleError != null) {
            parameters.append("&role-error=" + roleError);
        }
        if (tableIdError != null) {
            parameters.append("&table-id-error=" + tableIdError);
        }
        if (status != null) {
            parameters.append("&status=" + status);
        }
        if (error != null) {
            parameters.append("&error=" + error);
        }
        return parameters.toString();
    }

    private boolean isFormValid(HttpServletRequest request) {
        return isUserNameValid(request)
                & isEmailValid(request)
                & isTableIdValid(request)
                & isRoleValid(request);
    }

    //USER NAME VALIDATION
    private boolean isUserNameValid(HttpServletRequest request) {
        String username = request.getParameter("username");
        boolean isUserNameValid = true;
        Matcher userNameMatcher = null;
        if (username == null || username.isEmpty()) {
            isUserNameValid = false;
            request.setAttribute("username-error", "form.user.empty");
        } else {
            String userNameRegex = PropertyManager.getValue("form.user.name.regex");
            Pattern userNamePattern = Pattern.compile(userNameRegex,
                    Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
            userNameMatcher = userNamePattern.matcher(username);
            if (userNameMatcher != null && userNameMatcher.matches()) {
                isUserNameValid = true;
            } else {
                isUserNameValid = false;
                request.setAttribute("username-error", "username.malformed");
            }
        }
        return isUserNameValid;
    }

    //PASSWORD VALIDATION
    private boolean isPasswordValid(HttpServletRequest request) {
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm-password");
        boolean isPasswordValid = false;
        Matcher passwordMatcher = null;
        if (password == null || password.isEmpty()) {
            isPasswordValid = false;
            request.setAttribute("password-error", "form.user.empty");
        } else {
            String passwordRegex = PropertyManager.getValue("form.user.password.regex");
            Pattern passwordPattern = Pattern.compile(passwordRegex,
                    Pattern.UNICODE_CHARACTER_CLASS);
            passwordMatcher = passwordPattern.matcher(password);
            if (passwordMatcher != null && passwordMatcher.matches()) {
                isPasswordValid = true;
            } else {
                isPasswordValid = false;
                request.setAttribute("password-error", "form.user.password.malformed");
            }
            if (isPasswordValid && !password.equals(confirmPassword)) {
                request.setAttribute("confirm-password-error", "form.user.password.not-confirmed");
                isPasswordValid = false;
            }
        }
        return isPasswordValid;
    }

    //EMAIL VALIDATION
    private boolean isEmailValid(HttpServletRequest request) {
        String email = request.getParameter("email");
        boolean isEmailValid = false;
        Matcher emailMatcher = null;
        if (email == null || email.isEmpty()) {
            isEmailValid = false;
            request.setAttribute("email-error", "form.user.empty");
        } else {
            String emailRegex = PropertyManager.getValue("form.user.email.regex");
            Pattern emailPattern = Pattern.compile(emailRegex,
                    Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
            emailMatcher = emailPattern.matcher(email);
            if (emailMatcher != null && emailMatcher.matches()) {
                isEmailValid = true;
            } else {
                isEmailValid = false;
                request.setAttribute("email-error", "form.user.email.malformed");
            }
        }
        return isEmailValid;
    }

    //ROLE MATCHER
    private boolean isRoleValid(HttpServletRequest request) {
        String role = request.getParameter("role");
        boolean isRoleValid = false;
        Matcher roleMatcher = null;
        if (role == null || role.isEmpty()) {
            isRoleValid = false;
            request.setAttribute("role-error", "form.user.empty");
        } else {
            String roleRegex = PropertyManager.getValue("form.user.role.regex");
            Pattern rolePattern = Pattern.compile(roleRegex,
                    Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
            roleMatcher = rolePattern.matcher(role);
            if (roleMatcher != null && roleMatcher.matches()) {
                isRoleValid = true;
            } else {
                isRoleValid = false;
                request.setAttribute("role-error", "form.user.role.malformed");
            }
        }
        return isRoleValid;
    }

    /**
     * TABLE_ID MATCHER
     */
    private boolean isTableIdValid(HttpServletRequest request) {
        String tableId = request.getParameter("table-id");
        boolean isTableIdValid = false;
        Matcher tableIdMatcher = null;
        if (tableId == null || tableId.isEmpty()) {
            isTableIdValid = false;
            request.setAttribute("table-id-error", "form.user.empty");
        } else {
            String tableIdRegex = PropertyManager.getValue("form.user.table-id.regex");
            Pattern tableIdPattern = Pattern.compile(tableIdRegex,
                    Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
            tableIdMatcher = tableIdPattern.matcher(tableId);
            if (tableIdMatcher != null && tableIdMatcher.matches()) {
                isTableIdValid = true;
            } else {
                isTableIdValid = false;
                request.setAttribute("table-id-error", "form.user.table-id.malformed");
            }
        }
        return isTableIdValid;
    }
}
